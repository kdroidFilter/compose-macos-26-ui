package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

// ==================== Base Palette ====================

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

val Blue500 = Color(0xFF3B82F6)
val Blue600 = Color(0xFF2563EB)
val Purple500 = Color(0xFF8B5CF6)
val Violet500 = Color(0xFF8B5CF6)
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

val Emerald400 = Color(0xFF34D399)
val Emerald500 = Color(0xFF10B981)
val Emerald600 = Color(0xFF059669)

val Sky400 = Color(0xFF38BDF8)
val Sky500 = Color(0xFF0EA5E9)
val Sky600 = Color(0xFF0284C7)

val Red400 = Color(0xFFF87171)

/**
 * Color scheme for Darwin UI — aligns with Material3's ColorScheme API
 * while adding Darwin-specific design tokens.
 */
@Stable
class ColorScheme(
    // ---- Material3 standard properties ----
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val surfaceTint: Color,
    val inverseSurface: Color,
    val inverseOnSurface: Color,
    val inversePrimary: Color,
    val surfaceDim: Color,
    val surfaceBright: Color,
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val outline: Color,
    val outlineVariant: Color,
    val scrim: Color,

    // ---- Darwin extended tokens ----
    val backgroundElevated: Color,
    val backgroundSubtle: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textQuaternary: Color,
    val accent: Color,
    val onAccent: Color,
    val destructive: Color,
    val onDestructive: Color,
    val success: Color,
    val onSuccess: Color,
    val warning: Color,
    val onWarning: Color,
    val info: Color,
    val onInfo: Color,
    val borderStrong: Color,
    val inputBackground: Color,
    val inputFocusBackground: Color,
    val inputBorder: Color,
    val inputFocusBorder: Color,
    val card: Color,
    val cardForeground: Color,
    val overlay: Color,
    val ring: Color,
    val muted: Color,
    val mutedForeground: Color,
    val popover: Color,
    val popoverForeground: Color,
    val isDark: Boolean,
) {
    /** Convenience alias: maps to [outline]. */
    val border: Color get() = outline

    /** Convenience alias: maps to [outlineVariant]. */
    val borderSubtle: Color get() = outlineVariant

    fun copy(
        primary: Color = this.primary,
        onPrimary: Color = this.onPrimary,
        primaryContainer: Color = this.primaryContainer,
        onPrimaryContainer: Color = this.onPrimaryContainer,
        secondary: Color = this.secondary,
        onSecondary: Color = this.onSecondary,
        secondaryContainer: Color = this.secondaryContainer,
        onSecondaryContainer: Color = this.onSecondaryContainer,
        tertiary: Color = this.tertiary,
        onTertiary: Color = this.onTertiary,
        tertiaryContainer: Color = this.tertiaryContainer,
        onTertiaryContainer: Color = this.onTertiaryContainer,
        error: Color = this.error,
        onError: Color = this.onError,
        errorContainer: Color = this.errorContainer,
        onErrorContainer: Color = this.onErrorContainer,
        background: Color = this.background,
        onBackground: Color = this.onBackground,
        surface: Color = this.surface,
        onSurface: Color = this.onSurface,
        surfaceVariant: Color = this.surfaceVariant,
        onSurfaceVariant: Color = this.onSurfaceVariant,
        surfaceTint: Color = this.surfaceTint,
        inverseSurface: Color = this.inverseSurface,
        inverseOnSurface: Color = this.inverseOnSurface,
        inversePrimary: Color = this.inversePrimary,
        surfaceDim: Color = this.surfaceDim,
        surfaceBright: Color = this.surfaceBright,
        surfaceContainerLowest: Color = this.surfaceContainerLowest,
        surfaceContainerLow: Color = this.surfaceContainerLow,
        surfaceContainer: Color = this.surfaceContainer,
        surfaceContainerHigh: Color = this.surfaceContainerHigh,
        surfaceContainerHighest: Color = this.surfaceContainerHighest,
        outline: Color = this.outline,
        outlineVariant: Color = this.outlineVariant,
        scrim: Color = this.scrim,
        backgroundElevated: Color = this.backgroundElevated,
        backgroundSubtle: Color = this.backgroundSubtle,
        textPrimary: Color = this.textPrimary,
        textSecondary: Color = this.textSecondary,
        textTertiary: Color = this.textTertiary,
        textQuaternary: Color = this.textQuaternary,
        accent: Color = this.accent,
        onAccent: Color = this.onAccent,
        destructive: Color = this.destructive,
        onDestructive: Color = this.onDestructive,
        success: Color = this.success,
        onSuccess: Color = this.onSuccess,
        warning: Color = this.warning,
        onWarning: Color = this.onWarning,
        info: Color = this.info,
        onInfo: Color = this.onInfo,
        borderStrong: Color = this.borderStrong,
        inputBackground: Color = this.inputBackground,
        inputFocusBackground: Color = this.inputFocusBackground,
        inputBorder: Color = this.inputBorder,
        inputFocusBorder: Color = this.inputFocusBorder,
        card: Color = this.card,
        cardForeground: Color = this.cardForeground,
        overlay: Color = this.overlay,
        ring: Color = this.ring,
        muted: Color = this.muted,
        mutedForeground: Color = this.mutedForeground,
        popover: Color = this.popover,
        popoverForeground: Color = this.popoverForeground,
        isDark: Boolean = this.isDark,
    ) = ColorScheme(
        primary, onPrimary, primaryContainer, onPrimaryContainer,
        secondary, onSecondary, secondaryContainer, onSecondaryContainer,
        tertiary, onTertiary, tertiaryContainer, onTertiaryContainer,
        error, onError, errorContainer, onErrorContainer,
        background, onBackground, surface, onSurface,
        surfaceVariant, onSurfaceVariant, surfaceTint,
        inverseSurface, inverseOnSurface, inversePrimary,
        surfaceDim, surfaceBright,
        surfaceContainerLowest, surfaceContainerLow, surfaceContainer,
        surfaceContainerHigh, surfaceContainerHighest,
        outline, outlineVariant, scrim,
        backgroundElevated, backgroundSubtle,
        textPrimary, textSecondary, textTertiary, textQuaternary,
        accent, onAccent,
        destructive, onDestructive,
        success, onSuccess,
        warning, onWarning,
        info, onInfo,
        borderStrong,
        inputBackground, inputFocusBackground, inputBorder, inputFocusBorder,
        card, cardForeground,
        overlay, ring, muted, mutedForeground, popover, popoverForeground,
        isDark,
    )
}

/** Backward-compatible alias. */
typealias DarwinColors = ColorScheme

fun darkColorScheme(): ColorScheme = ColorScheme(
    // M3 standard
    primary = Color(0xFFFAFAFA),
    onPrimary = Color(0xFF0A0A0B),
    primaryContainer = Color(0xFF27272A),
    onPrimaryContainer = Color(0xFFFAFAFA),
    secondary = Color(0xFF27272A),
    onSecondary = Color(0xFFFAFAFA),
    secondaryContainer = Color(0xFF3F3F46),
    onSecondaryContainer = Color(0xFFFAFAFA),
    tertiary = Color(0xFF8B5CF6),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF4C1D95),
    onTertiaryContainer = Color(0xFFF5F3FF),
    error = Color(0xFF7F1D1D),
    onError = Color(0xFFFAFAFA),
    errorContainer = Color(0xFF450A0A),
    onErrorContainer = Color(0xFFFCA5A5),
    background = Color(0xFF0A0A0B),
    onBackground = Color(0xFFFAFAFA),
    surface = Color(0xFF111113),
    onSurface = Color(0xFFFAFAFA),
    surfaceVariant = Color(0xFF1C1C1F),
    onSurfaceVariant = Color(0xFFA1A1AA),
    surfaceTint = Color(0xFF3B82F6),
    inverseSurface = Color(0xFFFAFAFA),
    inverseOnSurface = Color(0xFF0A0A0B),
    inversePrimary = Color(0xFF18181B),
    surfaceDim = Color(0xFF0A0A0B),
    surfaceBright = Color(0xFF27272A),
    surfaceContainerLowest = Color(0xFF000000),
    surfaceContainerLow = Color(0xFF0D0D0F),
    surfaceContainer = Color(0xFF111113),
    surfaceContainerHigh = Color(0xFF1C1C1F),
    surfaceContainerHighest = Color(0xFF27272A),
    outline = Color(0xFF2E2E33),
    outlineVariant = Color(0x1AFFFFFF),
    scrim = Color(0xCC000000),

    // Darwin extended
    backgroundElevated = Color(0xFF111113),
    backgroundSubtle = Color(0xFF161618),
    textPrimary = Color(0xFFFAFAFA),
    textSecondary = Color(0xFFA1A1AA),
    textTertiary = Color(0xFF71717A),
    textQuaternary = Color(0xFF52525B),
    accent = Color(0xFF3B82F6),
    onAccent = Color.White,
    destructive = Color(0xFF7F1D1D),
    onDestructive = Color(0xFFFAFAFA),
    success = Color(0xFF22C55E),
    onSuccess = Color.White,
    warning = Color(0xFFF59E0B),
    onWarning = Color.Black,
    info = Color(0xFF3B82F6),
    onInfo = Color.White,
    borderStrong = Color(0x33FFFFFF),
    inputBackground = Color(0x0DFFFFFF),
    inputFocusBackground = Color(0xE618181B),
    inputBorder = Color(0x1AFFFFFF),
    inputFocusBorder = Blue500,
    card = Color(0xFF111113),
    cardForeground = Color(0xFFFAFAFA),
    overlay = Color(0x80000000),
    ring = Blue500,
    muted = Color(0xFF27272A),
    mutedForeground = Color(0xFFA1A1AA),
    popover = Color(0xFF111113),
    popoverForeground = Color(0xFFFAFAFA),
    isDark = true,
)

fun lightColorScheme(): ColorScheme = ColorScheme(
    // M3 standard
    primary = Color(0xFF18181B),
    onPrimary = Color(0xFFFAFAFA),
    primaryContainer = Color(0xFFF4F4F5),
    onPrimaryContainer = Color(0xFF18181B),
    secondary = Color(0xFFF4F4F5),
    onSecondary = Color(0xFF18181B),
    secondaryContainer = Color(0xFFE4E4E7),
    onSecondaryContainer = Color(0xFF18181B),
    tertiary = Color(0xFF7C3AED),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFEDE9FE),
    onTertiaryContainer = Color(0xFF4C1D95),
    error = Color(0xFFEF4444),
    onError = Color.White,
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = Color(0xFF7F1D1D),
    background = Color.White,
    onBackground = Color(0xFF0A0A0B),
    surface = Color(0xFFFAFAFA),
    onSurface = Color(0xFF0A0A0B),
    surfaceVariant = Color(0xFFF4F4F5),
    onSurfaceVariant = Color(0xFF52525B),
    surfaceTint = Color(0xFF2563EB),
    inverseSurface = Color(0xFF18181B),
    inverseOnSurface = Color(0xFFFAFAFA),
    inversePrimary = Color(0xFFF4F4F5),
    surfaceDim = Color(0xFFE4E4E7),
    surfaceBright = Color.White,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color(0xFFFAFAFA),
    surfaceContainer = Color(0xFFF4F4F5),
    surfaceContainerHigh = Color(0xFFE4E4E7),
    surfaceContainerHighest = Color(0xFFD4D4D8),
    outline = Color(0xFFE4E4E7),
    outlineVariant = Color(0x1A000000),
    scrim = Color(0x99000000),

    // Darwin extended
    backgroundElevated = Color(0xFFFAFAFA),
    backgroundSubtle = Color(0xFFF4F4F5),
    textPrimary = Color(0xFF18181B),
    textSecondary = Color(0xFF52525B),
    textTertiary = Color(0xFF71717A),
    textQuaternary = Color(0xFFA1A1AA),
    accent = Color(0xFF2563EB),
    onAccent = Color.White,
    destructive = Color(0xFFEF4444),
    onDestructive = Color.White,
    success = Color(0xFF16A34A),
    onSuccess = Color.White,
    warning = Color(0xFFCA8A04),
    onWarning = Color.White,
    info = Color(0xFF2563EB),
    onInfo = Color.White,
    borderStrong = Color(0x33000000),
    inputBackground = Color(0x0D000000),
    inputFocusBackground = Color(0xE6FFFFFF),
    inputBorder = Color(0x1A000000),
    inputFocusBorder = Blue500,
    card = Color(0xFFFAFAFA),
    cardForeground = Color(0xFF0A0A0B),
    overlay = Color(0x40000000),
    ring = Blue500,
    muted = Color(0xFFF4F4F5),
    mutedForeground = Color(0xFF71717A),
    popover = Color.White,
    popoverForeground = Color(0xFF0A0A0B),
    isDark = false,
)

/** Backward-compatible factory function. */
fun darkDarwinColors(): ColorScheme = darkColorScheme()

/** Backward-compatible factory function. */
fun lightDarwinColors(): ColorScheme = lightColorScheme()

val LocalDarwinColors = staticCompositionLocalOf { darkColorScheme() }
