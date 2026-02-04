package io.github.kdroidfilter.darwinui.theme

import androidx.compose.animation.core.*
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Darwin UI Animation Configuration
 * Mirrors the React library's animation-config.ts:
 *   instant: 0ms, fast: 100ms, normal: 150ms, slow: 200ms, reveal: 400ms
 *   snappy: {stiffness: 400, damping: 25}
 *   smooth: {stiffness: 200, damping: 25}
 *   gentle: {stiffness: 120, damping: 20}
 */

enum class DarwinDuration(val millis: Int) {
    Instant(0),
    Fast(100),
    Normal(150),
    Slow(200),
    Reveal(400),
}

enum class DarwinSpringPreset(val stiffness: Float, val dampingRatio: Float) {
    Snappy(400f, 0.65f),
    Smooth(200f, 0.75f),
    Gentle(120f, 0.8f),
}

@Immutable
data class DarwinAnimations(
    val instant: Int = DarwinDuration.Instant.millis,
    val fast: Int = DarwinDuration.Fast.millis,
    val normal: Int = DarwinDuration.Normal.millis,
    val slow: Int = DarwinDuration.Slow.millis,
    val reveal: Int = DarwinDuration.Reveal.millis,
)

/**
 * Get a tween animation spec for a given duration key.
 */
fun <T> darwinTween(
    duration: DarwinDuration = DarwinDuration.Normal,
    easing: Easing = FastOutSlowInEasing,
): TweenSpec<T> = tween(
    durationMillis = duration.millis,
    easing = easing,
)

/**
 * Get a spring animation spec for a given spring preset.
 */
fun <T> darwinSpring(
    preset: DarwinSpringPreset = DarwinSpringPreset.Smooth,
    visibilityThreshold: T? = null,
): SpringSpec<T> = spring(
    dampingRatio = preset.dampingRatio,
    stiffness = preset.stiffness,
    visibilityThreshold = visibilityThreshold,
)

val LocalDarwinAnimations = staticCompositionLocalOf { DarwinAnimations() }
