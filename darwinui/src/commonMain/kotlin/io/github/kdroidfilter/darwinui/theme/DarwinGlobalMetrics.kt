package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ===========================================================================
// 1.3 — GlobalMetrics
//
// Theme-level sizing constants shared across components. Mirrors Jewel's
// GlobalMetrics. Exposed via DarwinTheme.globalMetrics.
// ===========================================================================

/**
 * Global sizing tokens for Darwin UI components.
 *
 * These values encode macOS Human Interface Guidelines sizing rules and
 * should not be overridden unless explicitly customising the theme.
 */
@Immutable
data class GlobalMetrics(
    // ---- Border & outline widths ----

    /** Width of a standard component border (1pt on all displays). */
    val borderWidth: Dp = 1.dp,

    /** Width of the thicker border used when a component is focused. */
    val focusedBorderWidth: Dp = 2.dp,

    /**
     * Width of the focus ring (the glow drawn outside a focused component).
     * Maps to macOS' 3pt keyboard-focus ring.
     */
    val outlineWidth: Dp = 3.dp,

    // ---- Component heights (macOS HIG) ----

    /**
     * Mini control height — used for very compact UIs (e.g. status bars).
     * Corresponds to NSControl.ControlSize.mini (~15pt).
     */
    val miniComponentHeight: Dp = 16.dp,

    /**
     * Small control height.
     * Corresponds to NSControl.ControlSize.small (~20pt).
     */
    val smallComponentHeight: Dp = 20.dp,

    /**
     * Regular (default) control height.
     * Corresponds to NSControl.ControlSize.regular (~22pt).
     */
    val defaultComponentHeight: Dp = 22.dp,

    /**
     * Large control height.
     * Corresponds to NSControl.ControlSize.large (~26pt on macOS 13+).
     */
    val largeComponentHeight: Dp = 26.dp,

    // ---- Row / list heights ----

    /** Standard row height for list and table rows. */
    val rowHeight: Dp = 22.dp,

    /** Large row height for sidebar items and icon-list rows. */
    val largeRowHeight: Dp = 32.dp,

    // ---- Corner radii ----

    /** Radius for mini/compact controls (buttons, badges). */
    val cornerRadiusMini: Dp = 3.dp,

    /** Radius for small controls (checkboxes, text fields). */
    val cornerRadiusSmall: Dp = 5.dp,

    /** Radius for regular controls and cards. */
    val cornerRadiusMedium: Dp = 8.dp,

    /** Radius for panels, dialogs, and popovers. */
    val cornerRadiusLarge: Dp = 10.dp,

    /** Radius for sheets and large surfaces. */
    val cornerRadiusExtraLarge: Dp = 14.dp,

    // ---- Spacing ----

    /** Minimum horizontal padding inside a control (e.g. button). */
    val horizontalPaddingSmall: Dp = 8.dp,

    /** Standard horizontal padding inside a control. */
    val horizontalPaddingMedium: Dp = 12.dp,

    /** Large horizontal padding for comfortable reading. */
    val horizontalPaddingLarge: Dp = 16.dp,

    /** Icon size used inside controls (leading/trailing icons in text fields). */
    val inlineIconSize: Dp = 16.dp,
)

val LocalDarwinGlobalMetrics = staticCompositionLocalOf { GlobalMetrics() }
