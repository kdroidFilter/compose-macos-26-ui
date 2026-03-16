package io.github.kdroidfilter.nucleus.ui.apple.macos.window

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.LocalTitleBarRevalidate
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.LocalWindowControlInset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalColorScheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.util.isApplePlatform
import javax.swing.RootPaneContainer

/** Fallback traffic-light inset when JNI is not available. */
private val FALLBACK_INSET = 80.dp

/** Default title bar height matching TitleBarStyle.Unified. */
private const val DEFAULT_TITLE_BAR_HEIGHT = 52f

/**
 * A Compose Desktop window that replicates the SwiftUI full-size content
 * window style on macOS.
 *
 * When the native JNI bridge is available, this:
 * 1. Makes the title bar transparent with full-size content view
 * 2. Installs an invisible `NSToolbar` for the macOS 26pt corner radius
 * 3. Repositions traffic-light buttons via Auto Layout constraints
 *    (further from the edge, matching Finder / Safari)
 * 4. Syncs the AWT background color to prevent white flashes during resize
 *
 * Falls back to AWT client properties when JNI is unavailable.
 *
 * The [Scaffold][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Scaffold]
 * sidebar extends to the top of the window with the traffic lights floating
 * over its header area, exactly like SwiftUI's `NavigationSplitView`.
 */
@Suppress("FunctionNaming", "LongParameterList")
@Composable
fun MacosWindow(
    onCloseRequest: () -> Unit,
    state: WindowState = rememberWindowState(),
    visible: Boolean = true,
    title: String = "",
    icon: Painter? = null,
    resizable: Boolean = true,
    enabled: Boolean = true,
    focusable: Boolean = true,
    alwaysOnTop: Boolean = false,
    onPreviewKeyEvent: (KeyEvent) -> Boolean = { false },
    onKeyEvent: (KeyEvent) -> Boolean = { false },
    content: @Composable FrameWindowScope.() -> Unit,
) {
    Window(
        onCloseRequest = onCloseRequest,
        state = state,
        visible = visible,
        title = title,
        icon = icon,
        resizable = resizable,
        enabled = enabled,
        focusable = focusable,
        alwaysOnTop = alwaysOnTop,
        onPreviewKeyEvent = onPreviewKeyEvent,
        onKeyEvent = onKeyEvent,
    ) {
        var trafficLightInset: Dp by remember { mutableStateOf(FALLBACK_INSET) }
        var revalidateCallback: (() -> Unit)? by remember { mutableStateOf(null) }

        if (isApplePlatform) {
            if (MacosWindowBridge.isLoaded) {
                // JNI path: proper traffic-light repositioning
                DisposableEffect(Unit) {
                    val ptr = MacosWindowBridge.nativeGetNSWindowPtr(window)
                    if (ptr != 0L) {
                        (window as? RootPaneContainer)?.rootPane?.apply {
                            putClientProperty("apple.awt.fullWindowContent", true)
                            putClientProperty("apple.awt.transparentTitleBar", true)
                            putClientProperty("apple.awt.windowTitleVisible", false)
                        }
                        val inset = MacosWindowBridge.nativeApplyTitleBar(ptr, DEFAULT_TITLE_BAR_HEIGHT)
                        trafficLightInset = inset.dp
                        revalidateCallback = { MacosWindowBridge.nativeRevalidateTitleBar(ptr) }
                    }
                    onDispose {
                        revalidateCallback = null
                        if (ptr != 0L) {
                            MacosWindowBridge.nativeResetTitleBar(ptr)
                        }
                        (window as? RootPaneContainer)?.rootPane?.apply {
                            putClientProperty("apple.awt.fullWindowContent", false)
                            putClientProperty("apple.awt.transparentTitleBar", false)
                            putClientProperty("apple.awt.windowTitleVisible", true)
                        }
                    }
                }
            } else {
                // AWT-only fallback (traffic lights at default position)
                DisposableEffect(Unit) {
                    (window as? RootPaneContainer)?.rootPane?.apply {
                        putClientProperty("apple.awt.fullWindowContent", true)
                        putClientProperty("apple.awt.transparentTitleBar", true)
                        putClientProperty("apple.awt.windowTitleVisible", false)
                    }
                    onDispose {
                        (window as? RootPaneContainer)?.rootPane?.apply {
                            putClientProperty("apple.awt.fullWindowContent", false)
                            putClientProperty("apple.awt.transparentTitleBar", false)
                            putClientProperty("apple.awt.windowTitleVisible", true)
                        }
                    }
                }
            }
        }

        // Sync AWT background color with the Compose theme to prevent
        // white flashes during window resize. On macOS this sets the
        // NSWindow.backgroundColor at the compositor level.
        val background = LocalColorScheme.current.background
        LaunchedEffect(background) {
            val awtColor = java.awt.Color(background.toArgb(), true)
            fun applyRecursive(c: java.awt.Component) {
                c.background = awtColor
                if (c is java.awt.Container) {
                    c.components.forEach { applyRecursive(it) }
                }
            }
            applyRecursive(window)
            javax.swing.SwingUtilities.invokeLater { applyRecursive(window) }
        }

        CompositionLocalProvider(
            LocalWindowControlInset provides if (isApplePlatform) trafficLightInset else 0.dp,
            LocalTitleBarRevalidate provides revalidateCallback,
        ) {
            content()
        }
    }
}
