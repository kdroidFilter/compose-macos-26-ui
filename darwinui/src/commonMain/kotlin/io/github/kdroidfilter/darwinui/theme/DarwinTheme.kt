package io.github.kdroidfilter.darwinui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState

/**
 * Darwin UI Theme — macOS-inspired design system for Compose Multiplatform.
 *
 * @param liquidGlass When true, overlay components render with a frosted backdrop blur effect.
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
    globalMetrics: GlobalMetrics = GlobalMetrics(),
    componentStyling: ComponentStyling = defaultComponentStyling(colorScheme),
    liquidGlass: Boolean = true,
    glassType: GlassType = GlassType.Regular,
    content: @Composable () -> Unit,
) {
    val manrope = ManropeFontFamily()
    val resolvedTypography = typography.withFontFamily(manrope)
    val liquidState = rememberLiquidState()
    val accentColorValue = if (darkTheme) accentColor.dark else accentColor.light
    val globalColors = if (darkTheme) GlobalColors.dark(accentColorValue) else GlobalColors.light(accentColorValue)
    val vibrantColors = if (darkTheme) VibrantColors.dark() else VibrantColors.light()

    CompositionLocalProvider(
        LocalDarwinColors provides colorScheme,
        LocalDarwinTypography provides resolvedTypography,
        LocalDarwinShapes provides shapes,
        LocalDarwinAnimations provides animations,
        LocalDarwinGlobalColors provides globalColors,
        LocalDarwinVibrantColors provides vibrantColors,
        LocalDarwinGlobalMetrics provides globalMetrics,
        LocalDarwinComponentStyling provides componentStyling,
        LocalDarwinTextStyle provides resolvedTypography.body,
        LocalDarwinContentColor provides colorScheme.textPrimary,
        LocalDarwinGlassType provides glassType,
        LocalDarwinLiquidState provides if (liquidGlass) liquidState else null,
    ) {
        PlatformContextMenuOverride {
            // liquefiable must precede any draw modifiers so all content is captured
            Box(modifier = if (liquidGlass) Modifier.liquefiable(liquidState) else Modifier) {
                content()
            }
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

    /** Global semantic colors: borders, outline rings, semantic content tints. */
    val globalColors: GlobalColors
        @Composable
        @ReadOnlyComposable
        get() = LocalDarwinGlobalColors.current

    /** Opaque vibrant colors for vibrancy surfaces (blend mode compositing). */
    val vibrantColors: VibrantColors
        @Composable
        @ReadOnlyComposable
        get() = LocalDarwinVibrantColors.current

    /** Global sizing metrics shared across all components. */
    val globalMetrics: GlobalMetrics
        @Composable
        @ReadOnlyComposable
        get() = LocalDarwinGlobalMetrics.current

    /** Default styles for all Darwin UI components. */
    val componentStyling: ComponentStyling
        @Composable
        @ReadOnlyComposable
        get() = LocalDarwinComponentStyling.current

    /** Current control size propagated via [ControlSize] wrapper. */
    val controlSize: ControlSize
        @Composable
        @ReadOnlyComposable
        get() = LocalControlSize.current

    /** Current glass type (Regular or Tinted). */
    val glassType: GlassType
        @Composable
        @ReadOnlyComposable
        get() = LocalDarwinGlassType.current

    /** Current surface appearance (ContentArea or OverGlass). */
    val surface: DarwinSurface
        @Composable
        @ReadOnlyComposable
        get() = LocalDarwinSurface.current
}
