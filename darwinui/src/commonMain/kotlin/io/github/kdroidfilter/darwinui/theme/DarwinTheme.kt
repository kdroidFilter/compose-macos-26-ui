package io.github.kdroidfilter.darwinui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*

/**
 * Darwin UI Theme — a macOS-inspired design system for Compose Multiplatform.
 *
 * Usage:
 * ```
 * DarwinTheme(darkTheme = true) {
 *     // Your content here
 *     // Access tokens via DarwinTheme.colors, DarwinTheme.typography, etc.
 * }
 * ```
 */
@Composable
fun DarwinTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colors: DarwinColors = if (darkTheme) darkDarwinColors() else lightDarwinColors(),
    typography: DarwinTypography = DarwinTypography(),
    shapes: DarwinShapes = DarwinShapes(),
    animations: DarwinAnimations = DarwinAnimations(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalDarwinColors provides colors,
        LocalDarwinTypography provides typography,
        LocalDarwinShapes provides shapes,
        LocalDarwinAnimations provides animations,
        LocalDarwinTextStyle provides typography.bodyMedium,
        LocalDarwinContentColor provides colors.textPrimary,
    ) {
        content()
    }
}

/**
 * Entry point for accessing Darwin UI design tokens.
 */
object DarwinTheme {
    val colors: DarwinColors
        @Composable
        @ReadOnlyComposable
        get() = LocalDarwinColors.current

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
