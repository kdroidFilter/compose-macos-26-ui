package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ===========================================================================
// Vibrant Colors — macOS 26
//
// Opaque equivalents of the standard translucent label/fill colors, intended
// for use on vibrancy (NSVisualEffectView) surfaces with "plus lighter"
// (dark) or "plus darker" (light) blend modes.
//
// Exposed via MacosTheme.vibrantColors.
// ===========================================================================

@Immutable
data class VibrantColors(
    val labels: Labels,
    val fills: Fills,
    val systemColors: SystemColors,
    val gray: Color,
) {
    @Immutable
    data class Labels(
        val primary: Color,
        val secondary: Color,
        val tertiary: Color,
        val quaternary: Color,
        val quinary: Color,
    )

    @Immutable
    data class Fills(
        val primary: Color,
        val secondary: Color,
        val tertiary: Color,
        val quaternary: Color,
        val quinary: Color,
    )

    @Immutable
    data class SystemColors(
        val red: Color,
        val orange: Color,
        val yellow: Color,
        val green: Color,
        val mint: Color,
        val teal: Color,
        val cyan: Color,
        val blue: Color,
        val indigo: Color,
        val purple: Color,
        val pink: Color,
        val brown: Color,
    )

    companion object {
        // "use plus darker" blend mode on light vibrancy surfaces
        fun light(): VibrantColors = VibrantColors(
            labels = Labels(
                primary = Color(0xFF1A1A1A),
                secondary = Color(0xFF727272),
                tertiary = Color(0xFFBFBFBF),
                quaternary = Color(0xFFD9D9D9),
                quinary = Color(0xFFE6E6E6),
            ),
            fills = Fills(
                primary = Color(0xFFD9D9D9),
                secondary = Color(0xFFE6E6E6),
                tertiary = Color(0xFFF2F2F2),
                quaternary = Color(0xFFF7F7F7),
                quinary = Color(0xFFFBFBFB),
            ),
            systemColors = SystemColors(
                red = Color(0xFFF52F32),
                orange = Color(0xFFF58625),
                yellow = Color(0xFFF5C200),
                green = Color(0xFF26BF4D),
                mint = Color(0xFF00BDA9),
                teal = Color(0xFF00B3BF),
                cyan = Color(0xFF00ABCF),
                blue = Color(0xFF0078F0),
                indigo = Color(0xFF5C50E6),
                purple = Color(0xFFB72BC9),
                pink = Color(0xFFF5234B),
                brown = Color(0xFF9E7354),
            ),
            gray = Color(0xFF848489),
        )

        // "use plus lighter" blend mode on dark vibrancy surfaces
        fun dark(): VibrantColors = VibrantColors(
            labels = Labels(
                primary = Color(0xFFF5F5F5),
                secondary = Color(0xFF8A8A8A),
                tertiary = Color(0xFF404040),
                quaternary = Color(0xFF262626),
                quinary = Color(0xFF111111),
            ),
            fills = Fills(
                primary = Color(0xFF242424),
                secondary = Color(0xFF141414),
                tertiary = Color(0xFF0D0D0D),
                quaternary = Color(0xFF090909),
                quinary = Color(0xFF070707),
            ),
            systemColors = SystemColors(
                red = Color(0xFFFF4747),
                orange = Color(0xFFFF9E33),
                yellow = Color(0xFFFFE014),
                green = Color(0xFF3BDB63),
                mint = Color(0xFF2DE0CD),
                teal = Color(0xFF2DD7E0),
                cyan = Color(0xFF47D8FC),
                blue = Color(0xFF0A99FF),
                indigo = Color(0xFF7163FF),
                purple = Color(0xFFE647FC),
                pink = Color(0xFFFF4169),
                brown = Color(0xFFC29672),
            ),
            gray = Color(0xFFA2A2A7),
        )
    }
}

val LocalVibrantColors = staticCompositionLocalOf { VibrantColors.light() }
