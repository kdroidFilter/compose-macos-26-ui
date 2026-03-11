package io.github.kdroidfilter.darwinui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*

/**
 * Darwin UI Theme — a macOS-inspired design system for Compose Multiplatform.
 * The API mirrors Material3's MaterialTheme for familiarity.
 *
 * Usage:
 * ```
 * DarwinTheme {
 *     // Access tokens via DarwinTheme.colorScheme, DarwinTheme.typography, etc.
 * }
 * ```
 */
@Composable
fun DarwinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accentColor: AccentColor = AccentColor.Blue,
    colorScheme: ColorScheme = if (darkTheme) darkColorScheme(accentColor) else lightColorScheme(accentColor),
    typography: DarwinTypography = DarwinTypography(),
    shapes: DarwinShapes = DarwinShapes(),
    animations: DarwinAnimations = DarwinAnimations(),
    content: @Composable () -> Unit,
) {
    val manrope = ManropeFontFamily()
    val resolvedTypography = typography.withFontFamily(manrope)

    CompositionLocalProvider(
        LocalDarwinColors provides colorScheme,
        LocalDarwinTypography provides resolvedTypography,
        LocalDarwinShapes provides shapes,
        LocalDarwinAnimations provides animations,
        LocalDarwinTextStyle provides resolvedTypography.bodyMedium,
        LocalDarwinContentColor provides colorScheme.textPrimary,
    ) {
        PlatformContextMenuOverride {
            content()
        }
    }
}

/**
 * Entry point for accessing Darwin UI design tokens.
 * Mirrors Material3's MaterialTheme object API.
 */
object DarwinTheme {
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalDarwinColors.current

    /** Backward-compatible alias for [colorScheme]. */
    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = colorScheme

    val typography: DarwinTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalDarwinTypography.current

    val shapes: DarwinShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalDarwinShapes.current

    val animations: DarwinAnimations
        @Composable
        @ReadOnlyComposable
        get() = LocalDarwinAnimations.current
}
