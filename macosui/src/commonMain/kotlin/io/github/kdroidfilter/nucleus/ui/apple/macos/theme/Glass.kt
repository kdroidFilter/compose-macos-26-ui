package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquid

/**
 * Provides the height of the enclosing title bar so descendant composables
 * (e.g. [Sidebar][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Sidebar]) can
 * add an appropriate top inset when positioned behind it.
 *
 * Set by [Scaffold][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Scaffold].
 * Defaults to 0.dp (no title bar).
 */
val LocalTitleBarHeight = compositionLocalOf { 0.dp }

/**
 * Provides the current expanded width of the sidebar column.
 * Set by [Scaffold][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Scaffold]
 * when a resizable sidebar is used. The [Sidebar][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Sidebar]
 * reads this to synchronize its expanded width with the scaffold's drag state.
 *
 * Defaults to [Dp.Unspecified] (sidebar uses its own [width] parameter).
 */
val LocalSidebarWidth = compositionLocalOf { Dp.Unspecified }

/**
 * Callbacks for resizing the sidebar by dragging its right edge.
 * Null when the sidebar is not resizable (i.e. [ColumnWidth.Fixed]).
 */
class SidebarResizeCallbacks(
    val onDrag: (Dp) -> Unit,
    val onReset: () -> Unit,
)

/**
 * Provides resize callbacks for the sidebar's right edge drag handle.
 * Set by [Scaffold][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Scaffold]
 * when [sidebarWidth] is [ColumnWidth.Flexible].
 * Null when the sidebar is not resizable.
 */
val LocalSidebarResize = compositionLocalOf<SidebarResizeCallbacks?> { null }

/**
 * Callback to hide (close) the sidebar.
 * Set by [Scaffold][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Scaffold]
 * when the sidebar is toggleable. The [Sidebar][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Sidebar]
 * reads this to automatically show a hide button in its header area.
 * Null when the sidebar is not toggleable.
 */
val LocalSidebarHide = compositionLocalOf<(() -> Unit)?> { null }

/**
 * Whether the sidebar is currently visible (not animating out).
 * Set by [Scaffold][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Scaffold]
 * so the [Sidebar][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Sidebar]
 * can instantly hide its toggle button when the close animation starts.
 * Defaults to true.
 */
val LocalSidebarVisible = compositionLocalOf { true }

/**
 * The two glass appearance variants matching macOS 26.
 *
 * - [Regular] — neutral translucent glass with a lighter tint.
 * - [Tinted] — darker, more opaque glass with a stronger tint overlay.
 */
enum class GlassType {
    Regular,
    Tinted,
}

/**
 * Provides the current [GlassType] for all descendant glass components.
 * Defaults to [GlassType.Regular].
 */
val LocalGlassType = compositionLocalOf { GlassType.Regular }

/**
 * Provides a [LiquidState] for backdrop glass effects via [Modifier.macosGlass].
 * Null when liquid glass is disabled in [MacosTheme].
 */
val LocalLiquidState = compositionLocalOf<LiquidState?> { null }

/**
 * Provides a [LiquidState] for toolbar-level glass effects on buttons.
 * Set by [Scaffold][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Scaffold]
 * inside the title bar area so toolbar buttons can render with frosted glass.
 * Null when outside a scaffold title bar or when glass is unavailable.
 */
val LocalToolbarGlassState = compositionLocalOf<LiquidState?> { null }

// ===========================================================================
// Liquid Glass Materials — macOS 26
//
// Apple defines three material sizes (Small, Medium, Large) with different
// blur radii, tint opacities, and shadow styles.  Each has a light and dark
// variant, plus "Tinted" versions with a stronger, more opaque tint.
//
// Values extracted from Apple macOS 26 UI Kit (Sketch shared layer styles
// named "Liquid Glass/{Light,Dark}/Regular - {Small,Medium,Large}").
// ===========================================================================

/**
 * Predefined Liquid Glass material sizes matching macOS 26.
 *
 * Each size defines blur radius, tint color, and shadow parameters
 * for both light and dark appearances.
 */
enum class GlassMaterialSize {
    /** Small pill-shaped controls (toolbar buttons). Blur r=6. */
    Small,
    /** Medium panels (popovers, floating panels). Blur r=8–10. */
    Medium,
    /** Large panels (sheets, sidebars, dialogs). Blur r=15–16. */
    Large,
}

/**
 * Resolved parameters for a liquid glass material.
 */
@Immutable
data class GlassMaterialSpec(
    /** Translucent overlay tint (Black 40% dark, White 40–70% light). */
    val tint: Color,
    /** Opaque base color behind the blur. */
    val baseColor: Color,
    /** Backdrop blur radius in dp. */
    val blurRadius: Dp,
    /** Primary drop shadow color. */
    val shadowColor: Color,
    /** Primary shadow Y offset. */
    val shadowY: Dp,
    /** Primary shadow blur radius. */
    val shadowBlur: Dp,
    /** Secondary (edge) shadow color. */
    val edgeShadowColor: Color,
    /** Secondary shadow blur radius. */
    val edgeShadowBlur: Dp,
)

/**
 * Resolves a [GlassMaterialSpec] for the given [size] and dark/light mode.
 *
 * Values extracted from Apple macOS 26 UI Kit Sketch shared layer styles
 * named "Liquid Glass/{Light,Dark}/Regular - {Small,Medium,Large}".
 */
fun resolveGlassMaterial(size: GlassMaterialSize, isDark: Boolean): GlassMaterialSpec = when {
    isDark -> when (size) {
        GlassMaterialSize.Small -> GlassMaterialSpec(
            tint = Color.Black.copy(alpha = 0.40f),           // #00000066
            baseColor = Color(0xFF1A1A1A),
            blurRadius = 6.dp,
            shadowColor = Color.Black.copy(alpha = 0.16f),    // #00000029
            shadowY = 2.dp,
            shadowBlur = 25.dp,
            edgeShadowColor = Color.Black.copy(alpha = 0.10f), // #0000001a
            edgeShadowBlur = 2.dp,
        )
        GlassMaterialSize.Medium -> GlassMaterialSpec(
            tint = Color.Black.copy(alpha = 0.40f),           // #00000066
            baseColor = Color(0xFF121212),
            blurRadius = 8.dp,
            shadowColor = Color.Black.copy(alpha = 0.12f),    // #0000001f
            shadowY = 8.dp,
            shadowBlur = 40.dp,
            edgeShadowColor = Color.Black.copy(alpha = 0.10f),
            edgeShadowBlur = 2.dp,
        )
        GlassMaterialSize.Large -> GlassMaterialSpec(
            tint = Color.Black.copy(alpha = 0.40f),           // #00000066
            baseColor = Color(0xFF1A1A1A),
            blurRadius = 16.dp,
            shadowColor = Color.Black.copy(alpha = 0.12f),    // #0000001f
            shadowY = 8.dp,
            shadowBlur = 40.dp,
            edgeShadowColor = Color.Black.copy(alpha = 0.10f),
            edgeShadowBlur = 2.dp,
        )
    }
    else -> when (size) {
        GlassMaterialSize.Small -> GlassMaterialSpec(
            tint = Color.White.copy(alpha = 0.40f),           // #ffffff66
            baseColor = Color(0xFFFAFAFA),
            blurRadius = 6.dp,
            shadowColor = Color.Black.copy(alpha = 0.12f),    // #0000001f
            shadowY = 8.dp,
            shadowBlur = 40.dp,
            edgeShadowColor = Color.Transparent,
            edgeShadowBlur = 0.dp,
        )
        GlassMaterialSize.Medium -> GlassMaterialSpec(
            tint = Color.White.copy(alpha = 0.70f),           // #ffffffb3
            baseColor = Color(0xFFFAFAFA),
            blurRadius = 10.dp,
            shadowColor = Color.Black.copy(alpha = 0.12f),    // #0000001f
            shadowY = 8.dp,
            shadowBlur = 40.dp,
            edgeShadowColor = Color.Black.copy(alpha = 0.10f),
            edgeShadowBlur = 2.dp,
        )
        GlassMaterialSize.Large -> GlassMaterialSpec(
            tint = Color.White.copy(alpha = 0.70f),           // #ffffffb3
            baseColor = Color(0xFFFAFAFA),
            blurRadius = 15.dp,
            shadowColor = Color.Black.copy(alpha = 0.12f),    // #0000001f
            shadowY = 8.dp,
            shadowBlur = 40.dp,
            edgeShadowColor = Color.Black.copy(alpha = 0.10f),
            edgeShadowBlur = 2.dp,
        )
    }
}

/**
 * Applies a liquid glass (frosted backdrop blur) effect to overlay composables.
 *
 * When [LocalLiquidState] is available ([MacosTheme] created with `liquidGlass = true`),
 * samples the captured background, applies frost blur + tint and clips to [shape].
 *
 * Falls back to a plain clipped [fallbackColor] background when glass is unavailable.
 *
 * @param shape The clipping/effect shape.
 * @param fallbackColor Background used when blur is unavailable.
 * @param frost Blur radius for the frost effect (default 18.dp).
 */
@Composable
fun Modifier.macosGlass(
    shape: Shape,
    fallbackColor: Color,
    frost: Dp = 18.dp,
): Modifier {
    val liquidState = LocalLiquidState.current
    val isDark = LocalColorScheme.current.isDark
    val glassType = LocalGlassType.current
    return if (liquidState != null) {
        // Tinted = more opaque tint → glass surface appears denser
        val tint = when {
            glassType == GlassType.Tinted && isDark -> Color.Black.copy(alpha = 0.50f)
            glassType == GlassType.Tinted -> Color.White.copy(alpha = 0.65f)
            isDark -> Color.Black.copy(alpha = 0.30f)
            else -> Color.White.copy(alpha = 0.40f)
        }
        this.liquid(liquidState) {
            this.frost = frost
            this.shape = shape
            this.tint = tint
            refraction = 0.10f
            curve = 0.15f
            edge = 0f
            saturation = 1.15f
        }
    } else {
        this
            .clip(shape)
            .background(fallbackColor, shape)
    }
}

/**
 * Applies a macOS 26 Liquid Glass material effect with the correct
 * blur radius, tint, and shadow for the given [materialSize].
 *
 * Renders with the material's tint color, base color, and shadow parameters.
 * Unlike [macosGlass] (which uses backdrop blur for overlays like title bars),
 * material panels are content elements and use opaque rendering to avoid
 * recursive capture issues with the root [liquefiable] tree.
 *
 * @param shape The clipping shape for the material.
 * @param materialSize The material size tier (Small, Medium, Large).
 * @param tintColor Optional color overlay for custom tinting.
 */
@Composable
fun Modifier.macosGlassMaterial(
    shape: Shape,
    materialSize: GlassMaterialSize,
    tintColor: Color? = null,
    drawShadow: Boolean = true,
): Modifier {
    val isDark = LocalColorScheme.current.isDark
    val glassType = LocalGlassType.current
    val spec = resolveGlassMaterial(materialSize, isDark)

    // Tinted = more opaque tint overlay → denser glass surface
    val effectiveTint = if (glassType == GlassType.Tinted) {
        spec.tint.copy(alpha = (spec.tint.alpha * 1.5f).coerceAtMost(1f))
    } else {
        spec.tint
    }

    var result = this
    if (drawShadow) {
        result = result.shadow(
            elevation = spec.shadowBlur / 2,
            shape = shape,
            ambientColor = spec.edgeShadowColor,
            spotColor = spec.shadowColor,
        )
    }
    result = result
        .clip(shape)
        .background(effectiveTint, shape)
        .background(spec.baseColor.copy(alpha = 0.6f), shape)
    if (tintColor != null) {
        result = result.background(tintColor.copy(alpha = 0.15f), shape)
    }
    return result
}

/**
 * Applies a liquid glass effect with a progressive bottom-edge fade.
 *
 * The frost is full-strength over most of the surface and fades out
 * over the bottom 15%, giving a smooth transition instead of a hard line.
 *
 * Must be used together with a [liquefiable][io.github.fletchmckee.liquid.liquefiable]
 * sibling that captures the content underneath.
 */
fun Modifier.liquidGlassFade(
    state: LiquidState,
    shape: Shape,
    frost: Dp = 16.dp,
    tint: Color = Color.Transparent,
    saturation: Float = 1.05f,
): Modifier = this
    .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
    .drawWithContent {
        drawContent()
        drawRect(
            brush = Brush.verticalGradient(
                colorStops = arrayOf(
                    0.0f to Color.Transparent,
                    0.5f to Color.Black.copy(alpha = 0.4f),
                    1.0f to Color.Black,
                ),
                startY = size.height * 0.85f,
                endY = size.height,
            ),
            blendMode = BlendMode.DstOut,
        )
    }
    .liquid(state) {
        this.frost = frost
        this.shape = shape
        this.tint = tint
        this.saturation = saturation
    }
