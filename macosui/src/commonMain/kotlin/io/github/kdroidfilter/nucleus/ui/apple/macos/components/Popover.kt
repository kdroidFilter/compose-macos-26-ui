package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosDuration
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosGlass
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring

// =============================================================================
// PopoverPlacement — edge where the popover appears relative to the anchor
// =============================================================================

/**
 * Placement of the popover relative to its trigger.
 *
 * The arrow always points toward the anchor. For example, [Bottom] places the
 * popover below the anchor with the arrow pointing up.
 */
enum class PopoverPlacement {
    /** Popover above anchor, arrow pointing down. */
    Top,

    /** Popover below anchor, arrow pointing up. */
    Bottom,

    /** Popover to the start (left in LTR) of anchor, arrow pointing end. */
    Start,

    /** Popover to the end (right in LTR) of anchor, arrow pointing start. */
    End,

    /**
     * Automatically pick the edge with the most available space.
     * Prefers [Bottom], then [Top], then [End], then [Start].
     */
    Auto,
}

// =============================================================================
// Constants
// =============================================================================

private val ArrowHeight = 10.dp
private val ArrowWidth = 24.dp
private val CornerRadius = 10.dp
private val PopoverSpacing = 4.dp

// =============================================================================
// PopoverPositionProvider — positions popup and resolves auto placement
// =============================================================================

/**
 * Calculates popup position and resolves [PopoverPlacement.Auto].
 * Also computes the arrow's offset along the edge so it points at the anchor center.
 */
private class PopoverPositionProvider(
    private val requestedPlacement: PopoverPlacement,
    private val spacingPx: Float,
    private val arrowHeightPx: Float,
    private val onResolved: (resolvedPlacement: PopoverPlacement, arrowOffsetPx: Float) -> Unit,
) : PopupPositionProvider {

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset {
        val spacing = spacingPx.toInt()

        // Available space on each edge
        val spaceTop = anchorBounds.top
        val spaceBottom = windowSize.height - anchorBounds.bottom
        val spaceStart = if (layoutDirection == LayoutDirection.Ltr) anchorBounds.left else windowSize.width - anchorBounds.right
        val spaceEnd = if (layoutDirection == LayoutDirection.Ltr) windowSize.width - anchorBounds.right else anchorBounds.left

        val placement = if (requestedPlacement == PopoverPlacement.Auto) {
            // Pick the edge with the most space, preferring bottom > top > end > start
            val candidates = listOf(
                PopoverPlacement.Bottom to spaceBottom,
                PopoverPlacement.Top to spaceTop,
                PopoverPlacement.End to spaceEnd,
                PopoverPlacement.Start to spaceStart,
            )
            val best = candidates.maxByOrNull { it.second }!!
            // Only choose an edge if the popup fits; fall back to bottom
            if (best.second >= popupContentSize.height || best.second >= popupContentSize.width) {
                best.first
            } else {
                PopoverPlacement.Bottom
            }
        } else {
            requestedPlacement
        }

        val pw = popupContentSize.width
        val ph = popupContentSize.height
        val anchorCenterX = anchorBounds.left + anchorBounds.width / 2
        val anchorCenterY = anchorBounds.top + anchorBounds.height / 2

        var x: Int
        var y: Int
        var arrowOffset: Float

        when (placement) {
            PopoverPlacement.Bottom -> {
                // Center horizontally on anchor, clamp to window
                x = (anchorCenterX - pw / 2).coerceIn(0, (windowSize.width - pw).coerceAtLeast(0))
                y = anchorBounds.bottom + spacing
                // Arrow offset along the top edge
                arrowOffset = (anchorCenterX - x).toFloat()
            }

            PopoverPlacement.Top -> {
                x = (anchorCenterX - pw / 2).coerceIn(0, (windowSize.width - pw).coerceAtLeast(0))
                y = anchorBounds.top - ph - spacing
                // Arrow offset along the bottom edge
                arrowOffset = (anchorCenterX - x).toFloat()
            }

            PopoverPlacement.End -> {
                val ltr = layoutDirection == LayoutDirection.Ltr
                x = if (ltr) anchorBounds.right + spacing else anchorBounds.left - pw - spacing
                y = (anchorCenterY - ph / 2).coerceIn(0, (windowSize.height - ph).coerceAtLeast(0))
                // Arrow offset along the start edge (vertical)
                arrowOffset = (anchorCenterY - y).toFloat()
            }

            PopoverPlacement.Start -> {
                val ltr = layoutDirection == LayoutDirection.Ltr
                x = if (ltr) anchorBounds.left - pw - spacing else anchorBounds.right + spacing
                y = (anchorCenterY - ph / 2).coerceIn(0, (windowSize.height - ph).coerceAtLeast(0))
                arrowOffset = (anchorCenterY - y).toFloat()
            }

            PopoverPlacement.Auto -> {
                // Should not happen — resolved above
                x = anchorBounds.left
                y = anchorBounds.bottom + spacing
                arrowOffset = anchorBounds.width / 2f
            }
        }

        // Clamp arrow offset to valid range (within rounded corners)
        val minArrow = arrowHeightPx + CornerRadius.value * LocalDensity.value
        val isHorizontal = placement == PopoverPlacement.Top || placement == PopoverPlacement.Bottom
        val maxArrow = if (isHorizontal) pw.toFloat() - minArrow else ph.toFloat() - minArrow
        arrowOffset = arrowOffset.coerceIn(minArrow, maxArrow.coerceAtLeast(minArrow))

        onResolved(placement, arrowOffset)

        return IntOffset(x, y)
    }

    // Fallback density value — real clamping uses pixel values anyway
    private object LocalDensity {
        val value: Float = 2f
    }
}

// =============================================================================
// PopoverBubbleShape — rounded rect with arrow on the resolved edge
// =============================================================================

/**
 * Rounded rectangle with a smooth arrow/tail on one edge.
 *
 * @param placement Which edge the arrow is on (arrow points outward from this edge toward the anchor).
 * @param arrowOffsetPx Center of the arrow along the edge, in pixels.
 * @param cornerRadius Corner radius of the bubble.
 * @param arrowWidth Base width of the arrow.
 * @param arrowHeight Height of the arrow (pointing outward).
 */
private class PopoverBubbleShape(
    private val placement: PopoverPlacement,
    private val arrowOffsetPx: Float,
    private val cornerRadius: Dp,
    private val arrowWidth: Dp,
    private val arrowHeight: Dp,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline = Outline.Generic(buildPath(size, density))

    private fun buildPath(size: Size, density: Density): Path = with(density) {
        val r = cornerRadius.toPx()
        val ah = arrowHeight.toPx()
        val aw = arrowWidth.toPx()
        val halfAw = aw / 2f

        // Body rect inset by arrow height on the arrow edge
        val bodyLeft: Float
        val bodyTop: Float
        val bodyRight: Float
        val bodyBottom: Float

        when (placement) {
            PopoverPlacement.Top -> {
                // Arrow on bottom edge, pointing down
                bodyLeft = 0f; bodyTop = 0f; bodyRight = size.width; bodyBottom = size.height - ah
            }
            PopoverPlacement.Bottom -> {
                // Arrow on top edge, pointing up
                bodyLeft = 0f; bodyTop = ah; bodyRight = size.width; bodyBottom = size.height
            }
            PopoverPlacement.Start -> {
                // Arrow on end edge, pointing right (in LTR)
                bodyLeft = 0f; bodyTop = 0f; bodyRight = size.width - ah; bodyBottom = size.height
            }
            PopoverPlacement.End -> {
                // Arrow on start edge, pointing left (in LTR)
                bodyLeft = ah; bodyTop = 0f; bodyRight = size.width; bodyBottom = size.height
            }
            PopoverPlacement.Auto -> {
                bodyLeft = 0f; bodyTop = 0f; bodyRight = size.width; bodyBottom = size.height
            }
        }

        val bw = bodyRight - bodyLeft
        val bh = bodyBottom - bodyTop

        Path().apply {
            // Draw the rounded rectangle body with arrow integrated into the correct edge.
            // We walk clockwise starting from top-left of the body.

            // Top-left corner
            moveTo(bodyLeft + r, bodyTop)

            // === Top edge ===
            if (placement == PopoverPlacement.Bottom) {
                // Arrow on top edge pointing up
                val arrowCenter = arrowOffsetPx.coerceIn(bodyLeft + r + halfAw, bodyRight - r - halfAw)
                val arrowLeft = arrowCenter - halfAw
                val arrowRight = arrowCenter + halfAw
                lineTo(arrowLeft, bodyTop)
                // Smooth curve up to tip
                cubicTo(
                    x1 = arrowLeft + halfAw * 0.3f, y1 = bodyTop,
                    x2 = arrowCenter - 2f,           y2 = bodyTop - ah + 1f,
                    x3 = arrowCenter,                 y3 = bodyTop - ah,
                )
                // Smooth curve back down
                cubicTo(
                    x1 = arrowCenter + 2f,           y1 = bodyTop - ah + 1f,
                    x2 = arrowRight - halfAw * 0.3f, y2 = bodyTop,
                    x3 = arrowRight,                  y3 = bodyTop,
                )
            }
            lineTo(bodyRight - r, bodyTop)

            // Top-right corner
            arcTo(
                rect = Rect(bodyRight - 2 * r, bodyTop, bodyRight, bodyTop + 2 * r),
                startAngleDegrees = -90f, sweepAngleDegrees = 90f, forceMoveTo = false,
            )

            // === Right edge ===
            if (placement == PopoverPlacement.Start) {
                // Arrow on right edge pointing right
                val arrowCenter = arrowOffsetPx.coerceIn(bodyTop + r + halfAw, bodyBottom - r - halfAw)
                val arrowTop = arrowCenter - halfAw
                val arrowBottom = arrowCenter + halfAw
                lineTo(bodyRight, arrowTop)
                cubicTo(
                    x1 = bodyRight,                  y1 = arrowTop + halfAw * 0.3f,
                    x2 = bodyRight + ah - 1f,        y2 = arrowCenter - 2f,
                    x3 = bodyRight + ah,              y3 = arrowCenter,
                )
                cubicTo(
                    x1 = bodyRight + ah - 1f,        y1 = arrowCenter + 2f,
                    x2 = bodyRight,                  y2 = arrowBottom - halfAw * 0.3f,
                    x3 = bodyRight,                   y3 = arrowBottom,
                )
            }
            lineTo(bodyRight, bodyBottom - r)

            // Bottom-right corner
            arcTo(
                rect = Rect(bodyRight - 2 * r, bodyBottom - 2 * r, bodyRight, bodyBottom),
                startAngleDegrees = 0f, sweepAngleDegrees = 90f, forceMoveTo = false,
            )

            // === Bottom edge ===
            if (placement == PopoverPlacement.Top) {
                // Arrow on bottom edge pointing down
                val arrowCenter = arrowOffsetPx.coerceIn(bodyLeft + r + halfAw, bodyRight - r - halfAw)
                val arrowRight = arrowCenter + halfAw
                val arrowLeft = arrowCenter - halfAw
                lineTo(arrowRight, bodyBottom)
                cubicTo(
                    x1 = arrowRight - halfAw * 0.3f, y1 = bodyBottom,
                    x2 = arrowCenter + 2f,            y2 = bodyBottom + ah - 1f,
                    x3 = arrowCenter,                  y3 = bodyBottom + ah,
                )
                cubicTo(
                    x1 = arrowCenter - 2f,            y1 = bodyBottom + ah - 1f,
                    x2 = arrowLeft + halfAw * 0.3f,   y2 = bodyBottom,
                    x3 = arrowLeft,                    y3 = bodyBottom,
                )
            }
            lineTo(bodyLeft + r, bodyBottom)

            // Bottom-left corner
            arcTo(
                rect = Rect(bodyLeft, bodyBottom - 2 * r, bodyLeft + 2 * r, bodyBottom),
                startAngleDegrees = 90f, sweepAngleDegrees = 90f, forceMoveTo = false,
            )

            // === Left edge ===
            if (placement == PopoverPlacement.End) {
                // Arrow on left edge pointing left
                val arrowCenter = arrowOffsetPx.coerceIn(bodyTop + r + halfAw, bodyBottom - r - halfAw)
                val arrowBottom = arrowCenter + halfAw
                val arrowTop = arrowCenter - halfAw
                lineTo(bodyLeft, arrowBottom)
                cubicTo(
                    x1 = bodyLeft,                   y1 = arrowBottom - halfAw * 0.3f,
                    x2 = bodyLeft - ah + 1f,         y2 = arrowCenter + 2f,
                    x3 = bodyLeft - ah,               y3 = arrowCenter,
                )
                cubicTo(
                    x1 = bodyLeft - ah + 1f,         y1 = arrowCenter - 2f,
                    x2 = bodyLeft,                   y2 = arrowTop + halfAw * 0.3f,
                    x3 = bodyLeft,                    y3 = arrowTop,
                )
            }
            lineTo(bodyLeft, bodyTop + r)

            // Top-left corner
            arcTo(
                rect = Rect(bodyLeft, bodyTop, bodyLeft + 2 * r, bodyTop + 2 * r),
                startAngleDegrees = 180f, sweepAngleDegrees = 90f, forceMoveTo = false,
            )

            close()
        }
    }
}

// =============================================================================
// Popover — public API
// =============================================================================

/**
 * A macOS-style popover component.
 *
 * Displays a floating panel with a smooth arrow pointing toward its trigger.
 * The popover automatically positions itself and adjusts its arrow to always
 * point at the anchor center, matching NSPopover behavior.
 *
 * @param expanded Whether the popover is currently visible.
 * @param onDismissRequest Callback invoked when the user clicks outside.
 * @param modifier Modifier applied to the root container.
 * @param placement Where the popover appears relative to the trigger.
 *   [PopoverPlacement.Auto] picks the best edge based on available space.
 * @param trigger The composable that acts as the popover anchor.
 * @param content The composable content displayed inside the popover panel.
 */
@Composable
fun Popover(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    placement: PopoverPlacement = PopoverPlacement.Auto,
    trigger: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val colors = MacosTheme.colorScheme
    val density = LocalDensity.current
    val fallbackBg = colors.card
    val borderColor = colors.border

    val spacingPx = with(density) { PopoverSpacing.toPx() }
    val arrowHeightPx = with(density) { ArrowHeight.toPx() }

    // Resolved placement and arrow offset (updated by position provider)
    var resolvedPlacement by remember { mutableStateOf(PopoverPlacement.Bottom) }
    var arrowOffsetPx by remember { mutableStateOf(0f) }
    var popupSize by remember { mutableStateOf(IntSize.Zero) }

    val bubbleShape = remember(resolvedPlacement, arrowOffsetPx) {
        PopoverBubbleShape(
            placement = resolvedPlacement,
            arrowOffsetPx = arrowOffsetPx,
            cornerRadius = CornerRadius,
            arrowWidth = ArrowWidth,
            arrowHeight = ArrowHeight,
        )
    }

    // Padding insets: extra space on the arrow edge for the arrow
    val arrowPadding = when (resolvedPlacement) {
        PopoverPlacement.Top -> androidx.compose.foundation.layout.PaddingValues(bottom = ArrowHeight)
        PopoverPlacement.Bottom -> androidx.compose.foundation.layout.PaddingValues(top = ArrowHeight)
        PopoverPlacement.Start -> androidx.compose.foundation.layout.PaddingValues(end = ArrowHeight)
        PopoverPlacement.End -> androidx.compose.foundation.layout.PaddingValues(start = ArrowHeight)
        PopoverPlacement.Auto -> androidx.compose.foundation.layout.PaddingValues()
    }

    // Animation
    val animatedScale by animateFloatAsState(
        targetValue = if (expanded) 1f else 0.92f,
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "popover_scale",
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "popover_alpha",
    )

    // Transform origin based on arrow direction
    val transformOrigin = when (resolvedPlacement) {
        PopoverPlacement.Bottom -> TransformOrigin(
            pivotFractionX = if (popupSize.width > 0) arrowOffsetPx / popupSize.width else 0.5f,
            pivotFractionY = 0f,
        )
        PopoverPlacement.Top -> TransformOrigin(
            pivotFractionX = if (popupSize.width > 0) arrowOffsetPx / popupSize.width else 0.5f,
            pivotFractionY = 1f,
        )
        PopoverPlacement.End -> TransformOrigin(
            pivotFractionX = 0f,
            pivotFractionY = if (popupSize.height > 0) arrowOffsetPx / popupSize.height else 0.5f,
        )
        PopoverPlacement.Start -> TransformOrigin(
            pivotFractionX = 1f,
            pivotFractionY = if (popupSize.height > 0) arrowOffsetPx / popupSize.height else 0.5f,
        )
        PopoverPlacement.Auto -> TransformOrigin.Center
    }

    Box(modifier = modifier) {
        trigger()

        if (expanded) {
            val positionProvider = remember(placement, spacingPx, arrowHeightPx) {
                PopoverPositionProvider(
                    requestedPlacement = placement,
                    spacingPx = spacingPx,
                    arrowHeightPx = arrowHeightPx,
                    onResolved = { p, offset ->
                        resolvedPlacement = p
                        arrowOffsetPx = offset
                    },
                )
            }

            Popup(
                popupPositionProvider = positionProvider,
                onDismissRequest = onDismissRequest,
                properties = PopupProperties(focusable = true),
            ) {
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { popupSize = it.size }
                        .scale(animatedScale)
                        .alpha(animatedAlpha)
                        .shadow(
                            elevation = 12.dp,
                            shape = bubbleShape,
                            ambientColor = Color.Black.copy(alpha = 0.4f),
                            spotColor = Color.Black.copy(alpha = 0.12f),
                        )
                        .macosGlass(shape = bubbleShape, fallbackColor = fallbackBg)
                        .border(
                            width = 0.5.dp,
                            color = borderColor,
                            shape = bubbleShape,
                        )
                        .widthIn(min = 180.dp)
                        .padding(arrowPadding)
                        .padding(16.dp),
                ) {
                    content()
                }
            }
        }
    }
}

// =============================================================================
// Preview
// =============================================================================

@Preview
@Composable
private fun PopoverPreview() {
    MacosTheme {
        var expanded by remember { mutableStateOf(false) }
        Popover(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            trigger = {
                PushButton(onClick = { expanded = !expanded }) { Text("Open Popover") }
            },
        ) {
            Text("Popover content")
        }
    }
}
