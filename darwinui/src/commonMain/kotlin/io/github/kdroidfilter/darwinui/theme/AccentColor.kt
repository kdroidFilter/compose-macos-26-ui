package io.github.kdroidfilter.darwinui.theme

import androidx.compose.ui.graphics.Color

/**
 * Predefined accent color palette inspired by macOS system accent colors.
 * Each accent defines light and dark variants used to derive theme colors.
 */
enum class AccentColor(
    /** Accent used in light theme. */
    val light: Color,
    /** Accent used in dark theme. */
    val dark: Color,
    /** Lighter container shade for dark theme. */
    val containerDark: Color,
    /** Lighter container shade for light theme. */
    val containerLight: Color,
) {
    Blue(
        light = Color(0xFF2563EB),
        dark = Color(0xFF3B82F6),
        containerDark = Color(0xFF1E3A5F),
        containerLight = Color(0xFFDBEAFE),
    ),
    Purple(
        light = Color(0xFF7C3AED),
        dark = Color(0xFF8B5CF6),
        containerDark = Color(0xFF3B1F6E),
        containerLight = Color(0xFFEDE9FE),
    ),
    Pink(
        light = Color(0xFFDB2777),
        dark = Color(0xFFF472B6),
        containerDark = Color(0xFF5C1A3D),
        containerLight = Color(0xFFFCE7F3),
    ),
    Red(
        light = Color(0xFFDC2626),
        dark = Color(0xFFEF4444),
        containerDark = Color(0xFF5C1A1A),
        containerLight = Color(0xFFFEE2E2),
    ),
    Orange(
        light = Color(0xFFEA580C),
        dark = Color(0xFFF97316),
        containerDark = Color(0xFF5C2D0E),
        containerLight = Color(0xFFFFF7ED),
    ),
    Yellow(
        light = Color(0xFFCA8A04),
        dark = Color(0xFFEAB308),
        containerDark = Color(0xFF5C4A0E),
        containerLight = Color(0xFFFEFCE8),
    ),
    Green(
        light = Color(0xFF16A34A),
        dark = Color(0xFF22C55E),
        containerDark = Color(0xFF14532D),
        containerLight = Color(0xFFDCFCE7),
    ),
    Teal(
        light = Color(0xFF0D9488),
        dark = Color(0xFF14B8A6),
        containerDark = Color(0xFF134E4A),
        containerLight = Color(0xFFCCFBF1),
    ),
    Graphite(
        light = Color(0xFF52525B),
        dark = Color(0xFF71717A),
        containerDark = Color(0xFF3F3F46),
        containerLight = Color(0xFFE4E4E7),
    ),
}
