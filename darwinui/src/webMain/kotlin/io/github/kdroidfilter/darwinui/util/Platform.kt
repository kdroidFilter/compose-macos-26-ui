package io.github.kdroidfilter.darwinui.util

import kotlinx.browser.window

actual val isApplePlatform: Boolean =
    window.navigator.platform.let { "Mac" in it || "iPhone" in it || "iPad" in it || "iPod" in it }
