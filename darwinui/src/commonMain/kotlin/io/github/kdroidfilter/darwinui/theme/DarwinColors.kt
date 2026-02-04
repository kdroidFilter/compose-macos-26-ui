package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

// ==================== Base Palette ====================

// Zinc palette (used extensively)
val Zinc50 = Color(0xFFFAFAFA)
val Zinc100 = Color(0xFFF4F4F5)
val Zinc200 = Color(0xFFE4E4E7)
val Zinc300 = Color(0xFFD4D4D8)
val Zinc400 = Color(0xFFA1A1AA)
val Zinc500 = Color(0xFF71717A)
val Zinc600 = Color(0xFF52525B)
val Zinc700 = Color(0xFF3F3F46)
val Zinc800 = Color(0xFF27272A)
val Zinc900 = Color(0xFF18181B)
val Zinc950 = Color(0xFF09090B)

// Accent colors
val Blue500 = Color(0xFF3B82F6)
val Blue600 = Color(0xFF2563EB)
val Purple500 = Color(0xFF8B5CF6)
val Violet500 = Color(0xFF8B5CF6)  // Tailwind violet-500 (same hex as Purple500)
val Green500 = Color(0xFF22C55E)
val Green600 = Color(0xFF16A34A)
val Orange500 = Color(0xFFF97316)
val Red500 = Color(0xFFEF4444)
val Red600 = Color(0xFFDC2626)
val Red700 = Color(0xFFB91C1C)
val Yellow500 = Color(0xFFEAB308)
val Yellow600 = Color(0xFFCA8A04)
val Amber400 = Color(0xFFFBBF24)
val Amber500 = Color(0xFFF59E0B)
val Amber600 = Color(0xFFD97706)
val Cyan500 = Color(0xFF06B6D4)
val Pink500 = Color(0xFFEC4899)
val Teal500 = Color(0xFF14B8A6)

// Emerald palette (used for success/published badges)
val Emerald400 = Color(0xFF34D399)
val Emerald500 = Color(0xFF10B981)
val Emerald600 = Color(0xFF059669)

// Sky palette (used for info/new badges)
val Sky400 = Color(0xFF38BDF8)
val Sky500 = Color(0xFF0EA5E9)
val Sky600 = Color(0xFF0284C7)

// Additional reds
val Red400 = Color(0xFFF87171)

@Stable
class DarwinColors(
    // Surface colors
    val background: Color,
    val backgroundElevated: Color,
    val backgroundSubtle: Color,
    val surface: Color,
    val surfaceVariant: Color,

    // Content colors
    val onBackground: Color,
    val onSurface: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textQuaternary: Color,

    // Primary / Accent
    val primary: Color,
    val onPrimary: Color,
    val accent: Color,
    val onAccent: Color,

    // Secondary
    val secondary: Color,
    val onSecondary: Color,

    // Semantic colors
    val destructive: Color,
    val onDestructive: Color,
    val success: Color,
    val onSuccess: Color,
    val warning: Color,
    val onWarning: Color,
    val info: Color,
    val onInfo: Color,

    // Border colors
    val border: Color,
    val borderSubtle: Color,
    val borderStrong: Color,

    // Input
    val inputBackground: Color,
    val inputFocusBackground: Color,
    val inputBorder: Color,
    val inputFocusBorder: Color,

    // Card
    val card: Color,
    val cardForeground: Color,

    // Overlay
    val overlay: Color,
    val scrim: Color,

    // Miscellaneous
    val ring: Color,
    val muted: Color,
    val mutedForeground: Color,
    val popover: Color,
    val popoverForeground: Color,

    val isDark: Boolean,
) {
    fun copy(
        background: Color = this.background,
        backgroundElevated: Color = this.backgroundElevated,
        backgroundSubtle: Color = this.backgroundSubtle,
        surface: Color = this.surface,
        surfaceVariant: Color = this.surfaceVariant,
        onBackground: Color = this.onBackground,
        onSurface: Color = this.onSurface,
        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
        textTertiary: Color = this.textTertiary,
        textQuaternary: Color = this.textQuaternary,
        primary: Color = this.primary,
        onPrimary: Color = this.onPrimary,
        accent: Color = this.accent,
        onAccent: Color = this.onAccent,
        secondary: Color = this.secondary,
        onSecondary: Color = this.onSecondary,
        destructive: Color = this.destructive,
        onDestructive: Color = this.onDestructive,
        success: Color = this.success,
        onSuccess: Color = this.onSuccess,
        warning: Color = this.warning,
        onWarning: Color = this.onWarning,
        info: Color = this.info,
        onInfo: Color = this.onInfo,
        border: Color = this.border,
        borderSubtle: Color = this.borderSubtle,
        borderStrong: Color = this.borderStrong,
        inputBackground: Color = this.inputBackground,
        inputFocusBackground: Color = this.inputFocusBackground,
        inputBorder: Color = this.inputBorder,
        inputFocusBorder: Color = this.inputFocusBorder,
        card: Color = this.card,
        cardForeground: Color = this.cardForeground,
        overlay: Color = this.overlay,
        scrim: Color = this.scrim,
        ring: Color = this.ring,
        muted: Color = this.muted,
        mutedForeground: Color = this.mutedForeground,
        popover: Color = this.popover,
        popoverForeground: Color = this.popoverForeground,
        isDark: Boolean = this.isDark,
    ) = DarwinColors(
        background, backgroundElevated, backgroundSubtle, surface, surfaceVariant,
        onBackground, onSurface, textPrimary, textSecondary, textTertiary, textQuaternary,
        primary, onPrimary, accent, onAccent, secondary, onSecondary,
        destructive, onDestructive, success, onSuccess, warning, onWarning, info, onInfo,
        border, borderSubtle, borderStrong, inputBackground, inputFocusBackground, inputBorder, inputFocusBorder,
        card, cardForeground, overlay, scrim,
        ring, muted, mutedForeground, popover, popoverForeground, isDark,
    )
}

/**
 * Dark color scheme — the default for Darwin UI (macOS dark mode inspired)
 */
fun darkDarwinColors(): DarwinColors = DarwinColors(
    background = Color(0xFF0A0A0B),        // 240 10% 3.9%
    backgroundElevated = Color(0xFF111113),
    backgroundSubtle = Color(0xFF161618),
    surface = Color(0xFF111113),
    surfaceVariant = Color(0xFF1C1C1F),

    onBackground = Color(0xFFFAFAFA),      // 0 0% 98%
    onSurface = Color(0xFFFAFAFA),
    textPrimary = Color(0xFFFAFAFA),
    textSecondary = Color(0xFFA1A1AA),     // zinc-400
    textTertiary = Color(0xFF71717A),      // zinc-500
    textQuaternary = Color(0xFF52525B),    // zinc-600

    primary = Color(0xFFFAFAFA),           // 0 0% 98%
    onPrimary = Color(0xFF0A0A0B),
    accent = Color(0xFF3B82F6),            // blue-500
    onAccent = Color.White,

    secondary = Color(0xFF27272A),         // 240 3.7% 15.9%
    onSecondary = Color(0xFFFAFAFA),

    destructive = Color(0xFF7F1D1D),       // 0 62.8% 30.6%
    onDestructive = Color(0xFFFAFAFA),
    success = Color(0xFF22C55E),
    onSuccess = Color.White,
    warning = Color(0xFFF59E0B),
    onWarning = Color.Black,
    info = Color(0xFF3B82F6),
    onInfo = Color.White,

    border = Color(0xFF2E2E33),            // 240 3.7% 20%
    borderSubtle = Color(0x1AFFFFFF),      // white/10
    borderStrong = Color(0x33FFFFFF),      // white/20

    inputBackground = Color(0x0DFFFFFF),         // white/5
    inputFocusBackground = Color(0xE618181B),    // zinc-900/90 (dark:focus:bg-zinc-900/90)
    inputBorder = Color(0x1AFFFFFF),
    inputFocusBorder = Blue500,

    card = Color(0xFF111113),              // 240 5% 6%
    cardForeground = Color(0xFFFAFAFA),

    overlay = Color(0x80000000),
    scrim = Color(0xCC000000),

    ring = Blue500,
    muted = Color(0xFF27272A),
    mutedForeground = Color(0xFFA1A1AA),
    popover = Color(0xFF111113),
    popoverForeground = Color(0xFFFAFAFA),

    isDark = true,
)

/**
 * Light color scheme
 */
fun lightDarwinColors(): DarwinColors = DarwinColors(
    background = Color.White,
    backgroundElevated = Color(0xFFFAFAFA),
    backgroundSubtle = Color(0xFFF4F4F5),
    surface = Color(0xFFFAFAFA),
    surfaceVariant = Color(0xFFF4F4F5),

    onBackground = Color(0xFF0A0A0B),
    onSurface = Color(0xFF0A0A0B),
    textPrimary = Color(0xFF18181B),       // zinc-900
    textSecondary = Color(0xFF52525B),     // zinc-600
    textTertiary = Color(0xFF71717A),      // zinc-500
    textQuaternary = Color(0xFFA1A1AA),    // zinc-400

    primary = Color(0xFF18181B),           // 240 5.9% 10%
    onPrimary = Color(0xFFFAFAFA),
    accent = Color(0xFF2563EB),            // blue-600
    onAccent = Color.White,

    secondary = Color(0xFFF4F4F5),         // 240 4.8% 95.9%
    onSecondary = Color(0xFF18181B),

    destructive = Color(0xFFEF4444),
    onDestructive = Color.White,
    success = Color(0xFF16A34A),
    onSuccess = Color.White,
    warning = Color(0xFFCA8A04),
    onWarning = Color.White,
    info = Color(0xFF2563EB),
    onInfo = Color.White,

    border = Color(0xFFE4E4E7),            // 240 5.9% 90%
    borderSubtle = Color(0x1A000000),      // black/10
    borderStrong = Color(0x33000000),      // black/20

    inputBackground = Color(0x0D000000),         // black/5
    inputFocusBackground = Color(0xE6FFFFFF),    // white/90 (focus:bg-white/90)
    inputBorder = Color(0x1A000000),
    inputFocusBorder = Blue500,

    card = Color(0xFFFAFAFA),              // 0 0% 98%
    cardForeground = Color(0xFF0A0A0B),

    overlay = Color(0x40000000),
    scrim = Color(0x99000000),

    ring = Blue500,
    muted = Color(0xFFF4F4F5),
    mutedForeground = Color(0xFF71717A),
    popover = Color.White,
    popoverForeground = Color(0xFF0A0A0B),

    isDark = false,
)

val LocalDarwinColors = staticCompositionLocalOf { darkDarwinColors() }
