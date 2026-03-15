package io.github.kdroidfilter.darwinui.util

actual val isApplePlatform: Boolean =
    System.getProperty("os.name").orEmpty().lowercase().let { "mac" in it || "darwin" in it }

actual val isWebPlatform: Boolean = false
