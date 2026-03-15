package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ===========================================================================
// 1.4 — Outline system (validation + focus ring)
//
// Outline enum models the three validation states exposed by interactive inputs.
// The Modifier extensions render a macOS-style focus ring or validation ring.
// ===========================================================================

/**
 * Validation outline state for input components.
 *
 * - [None]    — no outline, component is in its normal state
 * - [Warning] — amber ring, indicates a soft validation issue
 * - [Error]   — red ring, indicates a hard validation failure
 *
 * Use [Modifier.outline] to render the appropriate outline on any component.
 */
enum class Outline {
    None,
    Warning,
    Error,
}

/**
 * Draws a macOS-style focus ring around the composable.
 *
 * The ring is rendered *behind* the component's own content so it does not
 * overlap inner elements. It expands slightly beyond the component's bounds,
 * matching the macOS accessibility focus ring behaviour.
 *
 * @param shape        The shape whose contour the ring follows.
 * @param color        The ring color. Pass [Color.Transparent] to suppress.
 * @param outlineWidth Stroke width of the ring. Defaults to 3dp (macOS standard).
 */
fun Modifier.focusOutline(
    shape: Shape,
    color: Color,
    outlineWidth: Dp = 3.dp,
): Modifier = drawBehind {
    if (color == Color.Transparent || color == Color.Unspecified) return@drawBehind

    val strokePx = outlineWidth.toPx()
    val halfStroke = strokePx / 2f

    // Expand the shape by half the stroke so the ring hugs the component edge
    val expandedSize = Size(
        width = size.width + strokePx,
        height = size.height + strokePx,
    )
    val outline = shape.createOutline(expandedSize, layoutDirection, this)

    translate(-halfStroke, -halfStroke) {
        drawOutline(
            outline = outline,
            color = color,
            style = Stroke(width = strokePx),
        )
    }
}

/**
 * Draws a validation outline around the composable based on an [Outline] state.
 *
 * Uses warning / error colors from the provided [GlobalColors.Outlines] token set
 * so the ring automatically adapts to the current theme and accent color.
 *
 * @param outline      The validation state. [Outline.None] draws nothing.
 * @param shape        The shape whose contour the ring follows.
 * @param outlines     The outline color tokens (from [GlobalColors.outlines]).
 * @param outlineWidth Stroke width. Defaults to 3dp.
 */
fun Modifier.outline(
    outline: Outline,
    shape: Shape,
    outlines: GlobalColors.Outlines,
    outlineWidth: Dp = 3.dp,
): Modifier = when (outline) {
    Outline.None -> this
    Outline.Warning -> focusOutline(shape, outlines.warning, outlineWidth)
    Outline.Error -> focusOutline(shape, outlines.error, outlineWidth)
}

/**
 * Draws both a component border and an optional focus/validation ring.
 *
 * Convenience overload that combines [Modifier.focusOutline] with an
 * [Outline] check: when [outline] is not [Outline.None], the validation
 * ring is drawn; when [isFocused] is true and [outline] is [Outline.None],
 * the focused ring color is used instead.
 *
 * @param isFocused    Whether the component currently has keyboard focus.
 * @param outline      The validation state.
 * @param shape        The shape whose contour the rings follow.
 * @param outlines     The outline color tokens from [GlobalColors.outlines].
 * @param outlineWidth Stroke width. Defaults to 3dp.
 */
fun Modifier.focusOrValidationOutline(
    isFocused: Boolean,
    outline: Outline,
    shape: Shape,
    outlines: GlobalColors.Outlines,
    outlineWidth: Dp = 3.dp,
): Modifier = when {
    outline == Outline.Error -> focusOutline(
        shape = shape,
        color = if (isFocused) outlines.focusedError else outlines.error,
        outlineWidth = outlineWidth,
    )
    outline == Outline.Warning -> focusOutline(
        shape = shape,
        color = if (isFocused) outlines.focusedWarning else outlines.warning,
        outlineWidth = outlineWidth,
    )
    isFocused -> focusOutline(
        shape = shape,
        color = outlines.focused,
        outlineWidth = outlineWidth,
    )
    else -> this
}
