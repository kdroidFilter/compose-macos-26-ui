/**
 * Adaptive requestAnimationFrame polyfill.
 *
 * Detects when the browser throttles rAF (e.g. Chrome Energy Saver mode
 * dropping to ~30 Hz) and transparently supplements it with setTimeout-based
 * frame scheduling so that Canvas-based renderers (Compose/Skiko) keep running
 * at a higher frame rate when the display can still support it.
 *
 * Must be loaded BEFORE any application code that calls requestAnimationFrame.
 */
(function () {
    'use strict';

    // --- configuration ---------------------------------------------------
    var SLOW_FRAME_MS          = 20;   // >20 ms between rAF = probably throttled
    var DETECTION_FRAMES       = 3;    // consecutive slow frames to confirm
    var FALLBACK_DELAY_MS      = 8;    // setTimeout delay when throttled
    var RECOVERY_CHECK_INTERVAL = 2000; // ms between "is rAF back to normal?" probes

    // --- state -----------------------------------------------------------
    var nativeRAF     = window.requestAnimationFrame.bind(window);
    var nativeCancel  = window.cancelAnimationFrame
                          ? window.cancelAnimationFrame.bind(window)
                          : function () {};

    var lastRafTime   = -1;
    var slowCount     = 0;
    var throttled     = false;
    var recoveryTimer = null;

    // --- throttle detection (runs inside rAF callbacks) -------------------
    function detectThrottle(timestamp) {
        if (lastRafTime > 0) {
            var delta = timestamp - lastRafTime;
            if (delta > SLOW_FRAME_MS) {
                slowCount++;
                if (slowCount >= DETECTION_FRAMES && !throttled) {
                    throttled = true;
                    startRecoveryProbe();
                }
            } else {
                if (throttled && slowCount === 0) {
                    throttled = false;
                    stopRecoveryProbe();
                }
                slowCount = 0;
            }
        }
        lastRafTime = timestamp;
    }

    // --- recovery probe: periodically check if rAF is fast again ---------
    function startRecoveryProbe() {
        if (recoveryTimer) return;
        recoveryTimer = setInterval(function () {
            nativeRAF(function (ts) {
                detectThrottle(ts);
            });
        }, RECOVERY_CHECK_INTERVAL);
    }

    function stopRecoveryProbe() {
        if (recoveryTimer) {
            clearInterval(recoveryTimer);
            recoveryTimer = null;
        }
    }

    // --- patched requestAnimationFrame -----------------------------------
    var nextId = 1;
    var pending = {}; // id -> { raf, timeout, done }

    window.requestAnimationFrame = function adaptiveRAF(callback) {
        var id = nextId++;
        var entry = { raf: 0, timeout: 0, done: false };
        pending[id] = entry;

        // Always schedule the real rAF for vsync alignment + detection
        entry.raf = nativeRAF(function (timestamp) {
            detectThrottle(timestamp);
            if (!entry.done) {
                entry.done = true;
                clearTimeout(entry.timeout);
                delete pending[id];
                callback(timestamp);
            }
        });

        // When throttled, also schedule setTimeout — whichever fires first wins
        if (throttled) {
            entry.timeout = setTimeout(function () {
                if (!entry.done) {
                    entry.done = true;
                    // don't cancel the rAF — let it run for detection only
                    delete pending[id];
                    callback(performance.now());
                }
            }, FALLBACK_DELAY_MS);
        }

        return id;
    };

    window.cancelAnimationFrame = function adaptiveCancelRAF(id) {
        var entry = pending[id];
        if (entry) {
            entry.done = true;
            nativeCancel(entry.raf);
            clearTimeout(entry.timeout);
            delete pending[id];
        } else {
            nativeCancel(id);
        }
    };

})();
