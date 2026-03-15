package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

// ==================== macOS System Colors ====================

// Legacy Zinc palette — kept for backward compatibility with components
// that still reference these. Will be removed as components are migrated.
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

/**
 * Color scheme for macOS UI — aligns with Material3's ColorScheme API
 * while adding macOS-specific design tokens.
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

    // ---- macOS extended tokens ----
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

fun darkColorScheme(accentColor: AccentColor = AccentColor.Blue): ColorScheme {
    val accent = accentColor.dark
    val accentContainer = accentColor.containerDark

    return ColorScheme(
        // M3 standard — primary is neutral gray (Apple buttons are not accent-colored)
        primary = Color(0xFFF5F5F5),
        onPrimary = Color(0xFF1C1C1E),
        primaryContainer = Color(0xFF2C2C2E),
        onPrimaryContainer = Color(0xFFF5F5F5),
        secondary = Color(0xFF2C2C2E),
        onSecondary = Color(0xFFF5F5F5),
        secondaryContainer = accentContainer,
        onSecondaryContainer = Color(0xFFF5F5F5),
        tertiary = accent,
        onTertiary = Color.White,
        tertiaryContainer = accentContainer,
        onTertiaryContainer = Color(0xFFF5F5F5),
        error = Color(0xFFFF4245),
        onError = Color.White,
        errorContainer = Color(0xFF73191B),
        onErrorContainer = Color(0xFFFF4245),
        background = Color(0xFF1C1C1E),
        onBackground = Color.White,
        surface = Color(0xFF1C1C1E),
        onSurface = Color.White,
        surfaceVariant = Color(0xFF2C2C2E),
        onSurfaceVariant = Color(0xFFAEAEB2),
        surfaceTint = accent,
        inverseSurface = Color(0xFFF2F2F7),
        inverseOnSurface = Color(0xFF1C1C1E),
        inversePrimary = Color(0xFF3A3A3C),
        surfaceDim = Color(0xFF1C1C1E),
        surfaceBright = Color(0xFF3A3A3C),
        surfaceContainerLowest = Color(0xFF141416),
        surfaceContainerLow = Color(0xFF1C1C1E),
        surfaceContainer = Color(0xFF2C2C2E),
        surfaceContainerHigh = Color(0xFF3A3A3C),
        surfaceContainerHighest = Color(0xFF48484A),
        outline = Color(0xFF3A3A3C),
        // Label Quaternary (White 10%)
        outlineVariant = Color(0x1AFFFFFF),
        scrim = Color(0xCC000000),

        // macOS extended — macOS 26 dark palette
        backgroundElevated = Color(0xFF2C2C2E),
        backgroundSubtle = Color(0xFF242426),
        // Label colors from Apple macOS 26 (standard, translucent)
        textPrimary = Color.White,                // Label Dark Primary: White 100%
        textSecondary = Color(0x8CFFFFFF),        // Label Dark Secondary: White 55%
        textTertiary = Color(0x40FFFFFF),          // Label Dark Tertiary: White 25%
        textQuaternary = Color(0x1AFFFFFF),        // Label Dark Quaternary: White 10%
        accent = accent,
        onAccent = Color.White,
        // System Colors from Apple macOS 26
        destructive = Color(0xFFFF4245),           // System Red Dark
        onDestructive = Color.White,
        success = Color(0xFF30D158),               // System Green Dark
        onSuccess = Color.White,
        warning = Color(0xFFFFD600),               // System Yellow Dark
        onWarning = Color.Black,
        info = accent,
        onInfo = Color.White,
        borderStrong = Color(0x4DFFFFFF),
        // Fill colors from Apple macOS 26 (Dark)
        inputBackground = Color(0x1AFFFFFF),       // Fills Dark Primary: White 10%
        inputFocusBackground = Color(0xFF3A3A3C),
        inputBorder = Color(0x14FFFFFF),           // Fills Dark Secondary: White 8%
        inputFocusBorder = accent,
        card = Color(0xFF2C2C2E),
        cardForeground = Color.White,
        overlay = Color(0x80000000),
        ring = accent,
        muted = Color(0xFF3A3A3C),
        mutedForeground = Color(0x8CFFFFFF),       // Label Dark Secondary
        popover = Color(0xFF2C2C2E),
        popoverForeground = Color.White,
        isDark = true,
    )
}

fun lightColorScheme(accentColor: AccentColor = AccentColor.Blue): ColorScheme {
    val accent = accentColor.light
    val accentContainer = accentColor.containerLight

    return ColorScheme(
        // M3 standard — primary is neutral (Apple buttons are not accent-colored)
        primary = Color(0xFF1C1C1E),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFF2F2F7),
        onPrimaryContainer = Color(0xFF1C1C1E),
        secondary = Color(0xFFF2F2F7),
        onSecondary = Color(0xFF1C1C1E),
        secondaryContainer = accentContainer,
        onSecondaryContainer = Color(0xFF1C1C1E),
        tertiary = accent,
        onTertiary = Color.White,
        tertiaryContainer = accentContainer,
        onTertiaryContainer = Color(0xFF1C1C1E),
        error = Color(0xFFFF383C),
        onError = Color.White,
        errorContainer = Color(0xFFFFD6D7),
        onErrorContainer = Color(0xFFFF383C),
        background = Color.White,
        onBackground = Color(0xD9000000),
        surface = Color.White,
        onSurface = Color(0xD9000000),
        surfaceVariant = Color(0xFFF2F2F7),
        onSurfaceVariant = Color(0x80000000),
        surfaceTint = accent,
        inverseSurface = Color(0xFF1C1C1E),
        inverseOnSurface = Color.White,
        inversePrimary = Color(0xFFF5F5F5),
        surfaceDim = Color(0xFFE5E5EA),
        surfaceBright = Color.White,
        surfaceContainerLowest = Color.White,
        surfaceContainerLow = Color(0xFFF2F2F7),
        surfaceContainer = Color(0xFFE5E5EA),
        surfaceContainerHigh = Color(0xFFD1D1D6),
        surfaceContainerHighest = Color(0xFFC7C7CC),
        outline = Color(0xFFD1D1D6),
        // Label Quaternary (Black 10%)
        outlineVariant = Color(0x1A000000),
        scrim = Color(0x99000000),

        // macOS extended — macOS 26 light palette
        backgroundElevated = Color(0xFFF2F2F7),
        backgroundSubtle = Color(0xFFE5E5EA),
        // Label colors from Apple macOS 26 (standard, translucent)
        textPrimary = Color(0xD9000000),           // Label Light Primary: Black 85%
        textSecondary = Color(0x80000000),          // Label Light Secondary: Black 50%
        textTertiary = Color(0x40000000),           // Label Light Tertiary: Black 25%
        textQuaternary = Color(0x1A000000),         // Label Light Quaternary: Black 10%
        accent = accent,
        onAccent = Color.White,
        // System Colors from Apple macOS 26
        destructive = Color(0xFFFF383C),            // System Red Light
        onDestructive = Color.White,
        success = Color(0xFF34C759),                // System Green Light
        onSuccess = Color.White,
        warning = Color(0xFFFFCC00),                // System Yellow Light
        onWarning = Color.Black,
        info = accent,
        onInfo = Color.White,
        borderStrong = Color(0x33000000),
        // Fill colors from Apple macOS 26 (Light)
        inputBackground = Color(0x1A000000),        // Fills Light Primary: Black 10%
        inputFocusBackground = Color(0xE6FFFFFF),
        inputBorder = Color(0x14000000),            // Fills Light Secondary: Black 8%
        inputFocusBorder = accent,
        card = Color(0xFFF2F2F7),
        cardForeground = Color(0xD9000000),
        overlay = Color(0x40000000),
        ring = accent,
        muted = Color(0xFFF2F2F7),
        mutedForeground = Color(0x80000000),        // Label Light Secondary
        popover = Color.White,
        popoverForeground = Color(0xD9000000),
        isDark = false,
    )
}

/**
 * Applies vibrant system color overrides to the given [ColorScheme].
 *
 * On macOS, vibrant colors are opaque equivalents used on vibrancy surfaces
 * with blend modes ("plus lighter" / "plus darker"). Since Compose doesn't
 * support these blend modes, only the system colors (accent, destructive, etc.)
 * are swapped — label and fill overrides are skipped as they appear incorrect
 * without the native blend modes.
 *
 * @param vibrantColors Vibrant system color definitions.
 * @param accentColor The current accent color, used to resolve its vibrant variant.
 */
fun ColorScheme.vibrant(vibrantColors: VibrantColors, accentColor: AccentColor = AccentColor.Blue): ColorScheme {
    val vibrantAccent = if (isDark) accentColor.vibrantDark else accentColor.vibrantLight
    return copy(
        // System colors → vibrant opaque equivalents
        accent = vibrantAccent,
        info = vibrantAccent,
        ring = vibrantAccent,
        inputFocusBorder = vibrantAccent,
        surfaceTint = vibrantAccent,
        tertiary = vibrantAccent,
        destructive = vibrantColors.systemColors.red,
        error = vibrantColors.systemColors.red,
        success = vibrantColors.systemColors.green,
        warning = vibrantColors.systemColors.yellow,
    )
}

val LocalColorScheme = staticCompositionLocalOf { darkColorScheme() }
