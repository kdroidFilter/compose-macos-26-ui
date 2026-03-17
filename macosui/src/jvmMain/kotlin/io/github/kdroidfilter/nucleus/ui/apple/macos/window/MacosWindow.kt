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
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.LocalTitleBarDoubleClick
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.LocalTitleBarRevalidate
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.LocalWindowControlInset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalColorScheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalWindowActive
import io.github.kdroidfilter.nucleus.ui.apple.macos.util.isApplePlatform
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.RootPaneContainer

/** Default title bar height matching TitleBarStyle.Unified. */
private const val DEFAULT_TITLE_BAR_HEIGHT = 52f

/**
 * A Compose Desktop window that replicates the SwiftUI full-size content
 * window style on macOS.
 *
 * Uses the native JNI bridge to:
 * 1. Make the title bar transparent with full-size content view
 * 2. Install an invisible `NSToolbar` for the macOS 26pt corner radius
 * 3. Reposition traffic-light buttons via Auto Layout constraints
 * 4. Sync the AWT background color to prevent white flashes during resize
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
        var trafficLightInset: Dp by remember { mutableStateOf(0.dp) }
        var revalidateCallback: (() -> Unit)? by remember { mutableStateOf(null) }
        var doubleClickCallback: (() -> Unit)? by remember { mutableStateOf(null) }

        // Track window focus for inactive appearance (grayed-out controls, etc.)
        var isWindowActive by remember { mutableStateOf(window.isFocused) }
        DisposableEffect(window) {
            val listener = object : WindowAdapter() {
                override fun windowActivated(e: WindowEvent?) { isWindowActive = true }
                override fun windowDeactivated(e: WindowEvent?) { isWindowActive = false }
            }
            window.addWindowListener(listener)
            onDispose { window.removeWindowListener(listener) }
        }

        if (isApplePlatform && MacosWindowBridge.isLoaded) {
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
                    doubleClickCallback = { MacosWindowBridge.nativePerformTitleBarDoubleClick(ptr) }
                }
                onDispose {
                    revalidateCallback = null
                    doubleClickCallback = null
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
            LocalTitleBarDoubleClick provides doubleClickCallback,
            LocalWindowActive provides isWindowActive,
        ) {
            content()
        }
    }
}
