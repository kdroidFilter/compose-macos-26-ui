package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.GlassType
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalGlassType
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTitleBarHeight
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalWindowActive
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

/**
 * macOS-style inspector panel for the right side of a [Scaffold].
 *
 * Handles its own background (window-active aware + tinted glass overlay),
 * left separator line (drawn on top, immune to glass refraction), and
 * title bar spacing — mirroring the [Sidebar] pattern.
 *
 * @param modifier Modifier applied to the root container.
 * @param content The inspector content.
 */
@Composable
fun Inspector(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = MacosTheme.colorScheme
    val isDark = colors.isDark
    val isWindowActive = LocalWindowActive.current
    val glassType = LocalGlassType.current
    val titleBarHeight = LocalTitleBarHeight.current

    // Opaque background when the window is inactive (matches Sidebar pattern)
    val inactiveOverlay = if (isWindowActive) {
        Color.Transparent
    } else {
        if (isDark) Color(0xFF282828) else Color(0xFFF4F4F4)
    }

    // Tinted glass overlay
    val tintedOverlay = if (glassType == GlassType.Tinted && isWindowActive) {
        if (isDark) Color.Black.copy(alpha = 0.25f) else Color.Black.copy(alpha = 0.08f)
    } else {
        Color.Transparent
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(if (isWindowActive) colors.background else inactiveOverlay)
            .background(tintedOverlay),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Spacer to push content below the title bar
            Spacer(Modifier.height(titleBarHeight))
            Box(modifier = Modifier.weight(1f)) {
                content()
            }
        }
    }
}
