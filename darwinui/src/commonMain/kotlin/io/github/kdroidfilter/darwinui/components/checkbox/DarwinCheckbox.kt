package io.github.kdroidfilter.darwinui.components.checkbox

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.theme.Blue500
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Zinc600
import io.github.kdroidfilter.darwinui.theme.Zinc800
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset

private val CheckboxShape = CircleShape

@Composable
fun DarwinCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    indeterminate: Boolean = false,
    label: String? = null,
    enabled: Boolean = true,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography

    val interactionSource = remember { MutableInteractionSource() }

    val isActive = indeterminate || checked

    // Animation for checkmark / dash
    val animationProgress by animateFloatAsState(
        targetValue = if (isActive) 1f else 0f,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "checkboxAnimation",
    )

    val disabledAlpha = if (enabled) 1f else 0.5f

    // active:  bg-blue-500 border-blue-500
    // default: bg-white dark:bg-zinc-800 border-black/20 dark:border-zinc-600
    val boxBackground = if (isActive) Blue500 else if (colors.isDark) Zinc800 else Color.White

    val borderColor = if (isActive) Blue500 else if (colors.isDark) Zinc600 else Color.Black.copy(alpha = 0.20f)

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

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(16.dp)
                .clip(CheckboxShape)
                .background(boxBackground, CheckboxShape)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = CheckboxShape,
                ),
        ) {
            if (animationProgress > 0f) {
                Canvas(
                    modifier = Modifier
                        .size(11.dp)
                        .graphicsLayer {
                            scaleX = animationProgress
                            scaleY = animationProgress
                            alpha = animationProgress
                        },
                ) {
                    if (indeterminate) {
                        drawIndeterminateDash(Color.White)
                    } else {
                        drawCheckmark(Color.White, animationProgress)
                    }
                }
            }
        }

        if (label != null) {
            Spacer(modifier = Modifier.width(8.dp))
            BasicText(
                text = label,
                style = typography.bodyMedium.merge(
                    TextStyle(
                        color = colors.textPrimary,
                        fontSize = 13.sp,
                    )
                ),
            )
        }
    }
}

private fun DrawScope.drawCheckmark(color: Color, progress: Float) {
    val w = size.width
    val h = size.height

    // Normalized: start(0.219, 0.531) mid(0.406, 0.719) end(0.781, 0.281)
    val startX = w * 0.219f
    val startY = h * 0.531f
    val midX = w * 0.406f
    val midY = h * 0.719f
    val endX = w * 0.781f
    val endY = h * 0.281f

    val strokeWidth = w * (1.8f / 16f)

    val path = Path().apply {
        moveTo(startX, startY)

        val firstSegmentEnd = (progress * 2f).coerceAtMost(1f)
        val currentMidX = startX + (midX - startX) * firstSegmentEnd
        val currentMidY = startY + (midY - startY) * firstSegmentEnd
        lineTo(currentMidX, currentMidY)

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

private fun DrawScope.drawIndeterminateDash(color: Color) {

    val dashWidth = size.width * 0.72f  // ~8px in 11px canvas
    val dashHeight = size.height * 0.18f // ~2px in 11px canvas
    val left = (size.width - dashWidth) / 2f
    val top = (size.height - dashHeight) / 2f
    val cornerRadius = dashHeight / 2f

    drawRoundRect(
        color = color,
        topLeft = Offset(left, top),
        size = Size(dashWidth, dashHeight),
        cornerRadius = CornerRadius(cornerRadius, cornerRadius),
    )
}
