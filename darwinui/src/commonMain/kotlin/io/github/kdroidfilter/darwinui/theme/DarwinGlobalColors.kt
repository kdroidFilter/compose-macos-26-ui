package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ===========================================================================
// 1.3 — GlobalColors
//
// Theme-level semantic colors that cut across all components: borders,
// focus outlines, and semantic content tints (info / success / warning / error).
// Exposed via DarwinTheme.globalColors.
// ===========================================================================

@Immutable
data class GlobalColors(
    /** Border colors used as default stroke for component outlines. */
    val borders: Borders,
    /** Outline (focus ring / validation ring) color definitions. */
    val outlines: Outlines,
    /** Foreground color for informational content. */
    val infoContent: Color,
    /** Foreground color for success content. */
    val successContent: Color,
    /** Foreground color for warning content. */
    val warningContent: Color,
    /** Foreground color for error / destructive content. */
    val errorContent: Color,
) {
    @Immutable
    data class Borders(
        /** Default border used when a component is in its normal, unfocused state. */
        val normal: Color,
        /** Border used when a component is disabled. */
        val disabled: Color,
        /** Border used when a component is focused. Equal to the accent color. */
        val focused: Color,
        /** Stronger border for elevated surfaces and cards. */
        val strong: Color,
    )

    @Immutable
    data class Outlines(
        /** Focus ring drawn outside a component when it has keyboard focus. */
        val focused: Color,
        /** Focus ring variant when the focused component is in a warning state. */
        val focusedWarning: Color,
        /** Focus ring variant when the focused component is in an error state. */
        val focusedError: Color,
        /** Warning validation outline. */
        val warning: Color,
        /** Error validation outline. */
        val error: Color,
    )

    companion object {
        fun light(accent: Color): GlobalColors = GlobalColors(
            borders = Borders(
                normal = Color(0x1A000000),
                disabled = Color(0x0D000000),
                focused = accent,
                strong = Color(0x33000000),
            ),
            outlines = Outlines(
                focused = accent.copy(alpha = 0.35f),
                focusedWarning = Color(0xFFCA8A04).copy(alpha = 0.35f),
                focusedError = Color(0xFFEF4444).copy(alpha = 0.35f),
                warning = Color(0xFFCA8A04),
                error = Color(0xFFEF4444),
            ),
            infoContent = accent,
            successContent = Color(0xFF16A34A),
            warningContent = Color(0xFFCA8A04),
            errorContent = Color(0xFFEF4444),
        )

        fun dark(accent: Color): GlobalColors = GlobalColors(
            borders = Borders(
                normal = Color(0x33FFFFFF),
                disabled = Color(0x1AFFFFFF),
                focused = accent,
                strong = Color(0x4DFFFFFF),
            ),
            outlines = Outlines(
                focused = accent.copy(alpha = 0.45f),
                focusedWarning = Color(0xFFFFD60A).copy(alpha = 0.45f),
                focusedError = Color(0xFFFF453A).copy(alpha = 0.45f),
                warning = Color(0xFFFFD60A),
                error = Color(0xFFFF453A),
            ),
            infoContent = accent,
            successContent = Color(0xFF30D158),
            warningContent = Color(0xFFFFD60A),
            errorContent = Color(0xFFFF453A),
        )
    }
}

val LocalDarwinGlobalColors = staticCompositionLocalOf {
    GlobalColors.light(accent = Color(0xFF007AFF))
}
