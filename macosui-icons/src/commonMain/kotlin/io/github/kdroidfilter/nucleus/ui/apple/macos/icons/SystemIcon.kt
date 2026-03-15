package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Describes an icon with a platform-native SF Symbol name and a Lucide fallback.
 *
 * On macOS JVM, the SF Symbol is loaded natively via AppKit.
 * On all other platforms, the [fallback] Lucide vector is used.
 */
data class SystemIcon(
    val sfSymbolName: String,
    val fallback: ImageVector,
) {
    constructor(fallback: ImageVector) : this("", fallback)
}

expect fun loadPlatformSymbol(name: String): ImageBitmap?
