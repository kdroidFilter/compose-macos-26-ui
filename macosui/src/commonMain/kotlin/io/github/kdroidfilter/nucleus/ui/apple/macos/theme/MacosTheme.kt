package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.rememberLiquidState

/**
 * macOS UI Theme — macOS-inspired design system for Compose Multiplatform.
 *
 * @param liquidGlass When true, overlay components render with a frosted backdrop blur effect.
 *
 * Usage:
 * ```
 * MacosTheme {
 *     // Access tokens via MacosTheme.colorScheme, MacosTheme.typography, etc.
 * }
 * ```
 */
@Composable
fun MacosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accentColor: AccentColor = AccentColor.Blue,
    colorScheme: ColorScheme = if (darkTheme) darkColorScheme(accentColor) else lightColorScheme(accentColor),
    typography: Typography = Typography(),
    shapes: Shapes = Shapes(),
    animations: Animations = Animations(),
    globalMetrics: GlobalMetrics = GlobalMetrics(),
    componentStyling: ComponentStyling = defaultComponentStyling(colorScheme),
    liquidGlass: Boolean = true,
    glassType: GlassType = GlassType.Regular,
    content: @Composable () -> Unit,
) {
    val fontFamily = MacosFontFamily()
    val resolvedTypography = typography.withFontFamily(fontFamily)
    val liquidState = rememberLiquidState()
    val accentColorValue = if (darkTheme) accentColor.dark else accentColor.light
    val globalColors = if (darkTheme) GlobalColors.dark(accentColorValue) else GlobalColors.light(accentColorValue)
    val vibrantColors = if (darkTheme) VibrantColors.dark() else VibrantColors.light()

    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        LocalTypography provides resolvedTypography,
        LocalShapes provides shapes,
        LocalAnimations provides animations,
        LocalGlobalColors provides globalColors,
        LocalVibrantColors provides vibrantColors,
        LocalGlobalMetrics provides globalMetrics,
        LocalComponentStyling provides componentStyling,
        LocalTextStyle provides resolvedTypography.body,
        LocalContentColor provides colorScheme.textPrimary,
        LocalGlassType provides glassType,
        LocalLiquidState provides if (liquidGlass) liquidState else null,
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
 * Entry point for accessing macOS UI design tokens.
 * Mirrors Material3's MaterialTheme object API.
 */
object MacosTheme {
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val animations: Animations
        @Composable
        @ReadOnlyComposable
        get() = LocalAnimations.current

    /** Global semantic colors: borders, outline rings, semantic content tints. */
    val globalColors: GlobalColors
        @Composable
        @ReadOnlyComposable
        get() = LocalGlobalColors.current

    /** Opaque vibrant colors for vibrancy surfaces (blend mode compositing). */
    val vibrantColors: VibrantColors
        @Composable
        @ReadOnlyComposable
        get() = LocalVibrantColors.current

    /** Global sizing metrics shared across all components. */
    val globalMetrics: GlobalMetrics
        @Composable
        @ReadOnlyComposable
        get() = LocalGlobalMetrics.current

    /** Default styles for all macOS UI components. */
    val componentStyling: ComponentStyling
        @Composable
        @ReadOnlyComposable
        get() = LocalComponentStyling.current

    /** Current control size propagated via [ControlSize] wrapper. */
    val controlSize: ControlSize
        @Composable
        @ReadOnlyComposable
        get() = LocalControlSize.current

    /** Current glass type (Regular or Tinted). */
    val glassType: GlassType
        @Composable
        @ReadOnlyComposable
        get() = LocalGlassType.current

    /** Current surface appearance (ContentArea or OverGlass). */
    val surface: Surface
        @Composable
        @ReadOnlyComposable
        get() = LocalSurface.current
}
