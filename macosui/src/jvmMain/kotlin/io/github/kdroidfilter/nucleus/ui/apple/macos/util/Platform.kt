package io.github.kdroidfilter.nucleus.ui.apple.macos.util

actual val isApplePlatform: Boolean =
    System.getProperty("os.name").orEmpty().lowercase().let { "mac" in it || "darwin" in it }

actual val isWebPlatform: Boolean = false
