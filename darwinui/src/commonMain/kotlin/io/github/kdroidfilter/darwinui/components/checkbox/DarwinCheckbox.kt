package io.github.kdroidfilter.darwinui.components.checkbox

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset

/**
 * A macOS-inspired checkbox component for Darwin UI.
 *
 * Supports checked, unchecked, and indeterminate states with animated
 * checkmark drawing. Optionally displays a text label beside the checkbox.
 *
 * @param checked Whether the checkbox is currently checked.
 * @param onCheckedChange Callback invoked when the user toggles the checkbox.
 * @param modifier Modifier applied to the root row container.
 * @param indeterminate When true, displays a dash icon instead of a checkmark
 *   (used for "select all" scenarios where only some items are selected).
 * @param label Optional text label displayed to the right of the checkbox.
 * @param enabled Whether the checkbox is interactive.
 * @param glass When true, applies a glass-morphism style to the checkbox background.
 */
@Composable
fun DarwinCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    indeterminate: Boolean = false,
    label: String? = null,
    enabled: Boolean = true,
    glass: Boolean = false,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    // Determine the visual state: indeterminate takes precedence over checked
    val isActive = indeterminate || checked

    // Animation: scale + alpha for the checkmark / dash icon
    val animationProgress by animateFloatAsState(
        targetValue = if (isActive) 1f else 0f,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "checkboxAnimation",
    )

    val disabledAlpha = if (enabled) 1f else 0.5f

    // Colors
    val boxBackground = when {
        glass && isActive -> colors.glassBackground
        isActive -> colors.accent
        glass -> colors.glassBackground
        else -> Color.Transparent
    }

    val borderColor = when {
        isFocused -> colors.ring
        glass -> colors.glassBorder
        isActive -> colors.accent
        else -> colors.borderSubtle
    }

    val iconColor = if (isActive) colors.onAccent else Color.Transparent

    // Focus ring
    val focusRingColor = if (isFocused) colors.ring.copy(alpha = 0.5f) else Color.Transparent

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .alpha(disabledAlpha)
            .toggleable(
                value = checked,
                onValueChange = { newValue ->
                    if (enabled) onCheckedChange(newValue)
                },
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = null,
            ),
    ) {
        // Outer focus ring wrapper
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(22.dp) // slightly larger than the checkbox to accommodate focus ring
        ) {
            // Focus ring
            if (isFocused) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(shapes.extraSmall)
                        .border(
                            width = 2.dp,
                            color = focusRingColor,
                            shape = shapes.extraSmall,
                        )
                )
            }

            // Checkbox box
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(18.dp)
                    .clip(shapes.extraSmall)
                    .background(boxBackground, shapes.extraSmall)
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = shapes.extraSmall,
                    ),
            ) {
                // Draw the check mark or dash
                if (animationProgress > 0f) {
                    Canvas(
                        modifier = Modifier
                            .size(12.dp)
                            .graphicsLayer {
                                scaleX = animationProgress
                                scaleY = animationProgress
                                alpha = animationProgress
                            },
                    ) {
                        if (indeterminate) {
                            drawIndeterminateDash(iconColor)
                        } else {
                            drawCheckmark(iconColor, animationProgress)
                        }
                    }
                }
            }
        }

        // Optional label
        if (label != null) {
            Spacer(modifier = Modifier.width(8.dp))
            BasicText(
                text = label,
                style = typography.bodyMedium.merge(
                    TextStyle(color = colors.textPrimary)
                ),
            )
        }
    }
}

/**
 * Draws a checkmark path inside the Canvas draw scope.
 * The path traces from lower-left, down to the bottom point, then up to the upper-right.
 */
private fun DrawScope.drawCheckmark(color: Color, progress: Float) {
    val strokeWidth = size.width * 0.15f
    val w = size.width
    val h = size.height

    // Checkmark path points (normalized within the canvas)
    // Start: left-center, Mid: bottom-center, End: right-top
    val startX = w * 0.15f
    val startY = h * 0.50f
    val midX = w * 0.40f
    val midY = h * 0.75f
    val endX = w * 0.85f
    val endY = h * 0.25f

    val path = Path().apply {
        moveTo(startX, startY)

        // First segment: start -> mid (the short downward stroke)
        val firstSegmentEnd = (progress * 2f).coerceAtMost(1f)
        val currentMidX = startX + (midX - startX) * firstSegmentEnd
        val currentMidY = startY + (midY - startY) * firstSegmentEnd
        lineTo(currentMidX, currentMidY)

        // Second segment: mid -> end (the long upward stroke)
        if (progress > 0.5f) {
            val secondSegmentProgress = ((progress - 0.5f) * 2f).coerceAtMost(1f)
            val currentEndX = midX + (endX - midX) * secondSegmentProgress
            val currentEndY = midY + (endY - midY) * secondSegmentProgress
            lineTo(currentEndX, currentEndY)
        }
    }

    drawPath(
        path = path,
        color = color,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round,
        ),
    )
}

/**
 * Draws a horizontal dash for the indeterminate state.
 */
private fun DrawScope.drawIndeterminateDash(color: Color) {
    val strokeWidth = size.width * 0.15f
    val y = size.height / 2f
    val startX = size.width * 0.2f
    val endX = size.width * 0.8f

    drawLine(
        color = color,
        start = Offset(startX, y),
        end = Offset(endX, y),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round,
    )
}
