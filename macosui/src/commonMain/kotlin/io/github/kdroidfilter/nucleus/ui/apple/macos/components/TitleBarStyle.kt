package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Visual style for a [TitleBar], matching macOS window toolbar variants.
 *
 * Each style defines a consistent set of dimensions so the title bar
 * and its child components (buttons, spacing, icons) scale together.
 *
 * @param height Total height of the title bar.
 * @param horizontalPadding Start/end padding inside the title bar.
 * @param navMinWidth Minimum width reserved for [navigationActions].
 * @param actionsMinWidth Minimum width reserved for [actions].
 * @param actionSpacing Horizontal spacing between action buttons.
 * @param buttonHeight Height of [TitleBarButtonGroup] buttons.
 * @param iconSize Default icon size inside toolbar buttons.
 */
enum class TitleBarStyle(
    val height: Int,
    val horizontalPadding: Dp,
    val navMinWidth: Dp,
    val actionsMinWidth: Dp,
    val actionSpacing: Dp,
    val buttonHeight: Dp,
    val buttonPadding: Dp,
    val iconSize: Dp,
) {
    /** Standard macOS toolbar (Safari, Finder). Sketch: Toolbar/XL — 36px buttons, 28px icons. */
    Unified(
        height = 52,
        horizontalPadding = 12.dp,
        navMinWidth = 80.dp,
        actionsMinWidth = 80.dp,
        actionSpacing = 8.dp,
        buttonHeight = 36.dp,
        buttonPadding = 4.dp,
        iconSize = 14.dp,
    ),

    /** Compact toolbar (Finder compact mode). Sketch: Toolbar/Medium — 24px buttons, 20px icons. */
    UnifiedCompact(
        height = 38,
        horizontalPadding = 10.dp,
        navMinWidth = 60.dp,
        actionsMinWidth = 60.dp,
        actionSpacing = 6.dp,
        buttonHeight = 24.dp,
        buttonPadding = 2.dp,
        iconSize = 10.dp,
    ),

    /** Expanded toolbar for creative/content-heavy apps. Same button size as Unified. */
    Expanded(
        height = 60,
        horizontalPadding = 16.dp,
        navMinWidth = 100.dp,
        actionsMinWidth = 100.dp,
        actionSpacing = 10.dp,
        buttonHeight = 36.dp,
        buttonPadding = 4.dp,
        iconSize = 14.dp,
    ),
}

/**
 * Provides the active [TitleBarStyle] to descendant composables.
 * Set by [TitleBar], read by [TitleBarButtonGroup] and similar components
 * so they can adapt their dimensions to the current style.
 */
val LocalTitleBarStyle = compositionLocalOf { TitleBarStyle.Unified }
