package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.theme.Blue500
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Zinc600
import io.github.kdroidfilter.darwinui.theme.Zinc800
import io.github.kdroidfilter.darwinui.theme.darwinSpring

// ===========================================================================
// CheckboxColors — mirrors M3's CheckboxColors
// ===========================================================================

@Immutable
class CheckboxColors(
    val checkedCheckmarkColor: Color,
    val uncheckedCheckmarkColor: Color,
    val checkedBoxColor: Color,
    val uncheckedBoxColor: Color,
    val disabledCheckedBoxColor: Color,
    val disabledUncheckedBoxColor: Color,
    val checkedBorderColor: Color,
    val uncheckedBorderColor: Color,
    val disabledBorderColor: Color,
    val disabledIndeterminateBorderColor: Color,
    val disabledIndeterminateBoxColor: Color,
) {
    fun copy(
        checkedCheckmarkColor: Color = this.checkedCheckmarkColor,
        uncheckedCheckmarkColor: Color = this.uncheckedCheckmarkColor,
        checkedBoxColor: Color = this.checkedBoxColor,
        uncheckedBoxColor: Color = this.uncheckedBoxColor,
        disabledCheckedBoxColor: Color = this.disabledCheckedBoxColor,
        disabledUncheckedBoxColor: Color = this.disabledUncheckedBoxColor,
        checkedBorderColor: Color = this.checkedBorderColor,
        uncheckedBorderColor: Color = this.uncheckedBorderColor,
        disabledBorderColor: Color = this.disabledBorderColor,
        disabledIndeterminateBorderColor: Color = this.disabledIndeterminateBorderColor,
        disabledIndeterminateBoxColor: Color = this.disabledIndeterminateBoxColor,
    ) = CheckboxColors(
        checkedCheckmarkColor, uncheckedCheckmarkColor,
        checkedBoxColor, uncheckedBoxColor,
        disabledCheckedBoxColor, disabledUncheckedBoxColor,
        checkedBorderColor, uncheckedBorderColor,
        disabledBorderColor, disabledIndeterminateBorderColor, disabledIndeterminateBoxColor,
    )
}

// ===========================================================================
// CheckboxDefaults — mirrors M3's CheckboxDefaults
// ===========================================================================

object CheckboxDefaults {
    @Composable
    fun colors(
        checkedCheckmarkColor: Color = Color.White,
        uncheckedCheckmarkColor: Color = Color.Transparent,
        checkedBoxColor: Color = DarwinTheme.colorScheme.accent,
        uncheckedBoxColor: Color = if (DarwinTheme.colorScheme.isDark) Zinc800 else Color.White,
        disabledCheckedBoxColor: Color = checkedBoxColor.copy(alpha = 0.5f),
        disabledUncheckedBoxColor: Color = uncheckedBoxColor.copy(alpha = 0.5f),
        checkedBorderColor: Color = DarwinTheme.colorScheme.accent,
        uncheckedBorderColor: Color = if (DarwinTheme.colorScheme.isDark) Zinc600 else Color.Black.copy(alpha = 0.20f),
        disabledBorderColor: Color = uncheckedBorderColor.copy(alpha = 0.5f),
        disabledIndeterminateBorderColor: Color = checkedBorderColor.copy(alpha = 0.5f),
        disabledIndeterminateBoxColor: Color = disabledCheckedBoxColor,
    ) = CheckboxColors(
        checkedCheckmarkColor, uncheckedCheckmarkColor,
        checkedBoxColor, uncheckedBoxColor,
        disabledCheckedBoxColor, disabledUncheckedBoxColor,
        checkedBorderColor, uncheckedBorderColor,
        disabledBorderColor, disabledIndeterminateBorderColor, disabledIndeterminateBoxColor,
    )
}

// ===========================================================================
// Checkbox — M3-compatible
// ===========================================================================

@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val animationProgress by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "checkboxAnimation",
    )

    val boxBackground by animateColorAsState(
        targetValue = when {
            !enabled && checked -> colors.disabledCheckedBoxColor
            !enabled -> colors.disabledUncheckedBoxColor
            checked -> colors.checkedBoxColor
            else -> colors.uncheckedBoxColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "checkboxBoxColor",
    )
    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.disabledBorderColor
            checked -> colors.checkedBorderColor
            else -> colors.uncheckedBorderColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "checkboxBorderColor",
    )

    val toggleModifier = if (onCheckedChange != null) {
        modifier.toggleable(
            value = checked,
            onValueChange = { if (enabled) onCheckedChange(it) },
            enabled = enabled,
            role = Role.Checkbox,
            interactionSource = interactionSource,
            indication = null,
        )
    } else modifier

    Box(
        contentAlignment = Alignment.Center,
        modifier = toggleModifier
            .alpha(if (enabled) 1f else 0.5f)
            .size(16.dp)
            .clip(CircleShape)
            .background(boxBackground, CircleShape)
            .border(width = 1.dp, color = borderColor, shape = CircleShape),
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
                drawCheckmark(colors.checkedCheckmarkColor, animationProgress)
            }
        }
    }
}

// ===========================================================================
// TriStateCheckbox — M3-compatible (supports indeterminate state)
// ===========================================================================

@Composable
fun TriStateCheckbox(
    state: androidx.compose.ui.state.ToggleableState,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val isIndeterminate = state == androidx.compose.ui.state.ToggleableState.Indeterminate
    val isChecked = state == androidx.compose.ui.state.ToggleableState.On
    val isActive = isChecked || isIndeterminate

    val animationProgress by animateFloatAsState(
        targetValue = if (isActive) 1f else 0f,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "triStateCheckboxAnimation",
    )

    val boxBackground by animateColorAsState(
        targetValue = when {
            !enabled && isActive -> colors.disabledIndeterminateBoxColor
            !enabled -> colors.disabledUncheckedBoxColor
            isActive -> colors.checkedBoxColor
            else -> colors.uncheckedBoxColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "triStateBoxColor",
    )
    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled && isIndeterminate -> colors.disabledIndeterminateBorderColor
            !enabled -> colors.disabledBorderColor
            isActive -> colors.checkedBorderColor
            else -> colors.uncheckedBorderColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "triStateBorderColor",
    )

    val clickModifier = if (onClick != null) {
        modifier.toggleable(
            value = isChecked,
            onValueChange = { if (enabled) onClick() },
            enabled = enabled,
            role = Role.Checkbox,
            interactionSource = interactionSource,
            indication = null,
        )
    } else modifier

    Box(
        contentAlignment = Alignment.Center,
        modifier = clickModifier
            .alpha(if (enabled) 1f else 0.5f)
            .size(16.dp)
            .clip(CircleShape)
            .background(boxBackground, CircleShape)
            .border(width = 1.dp, color = borderColor, shape = CircleShape),
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
                if (isIndeterminate) drawIndeterminateDash(colors.checkedCheckmarkColor)
                else drawCheckmark(colors.checkedCheckmarkColor, animationProgress)
            }
        }
    }
}

// ===========================================================================
// CheckBox — backward-compatible alias with label support
// ===========================================================================

@Composable
fun CheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    indeterminate: Boolean = false,
    label: String? = null,
    enabled: Boolean = true,
) {
    if (indeterminate || label != null) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            if (indeterminate) {
                TriStateCheckbox(
                    state = if (indeterminate) androidx.compose.ui.state.ToggleableState.Indeterminate
                    else if (checked) androidx.compose.ui.state.ToggleableState.On
                    else androidx.compose.ui.state.ToggleableState.Off,
                    onClick = { onCheckedChange(!checked) },
                    enabled = enabled,
                )
            } else {
                Checkbox(checked = checked, onCheckedChange = onCheckedChange, enabled = enabled)
            }
            if (label != null) {
                Spacer(modifier = Modifier.width(8.dp))
                BasicText(
                    text = label,
                    style = DarwinTheme.typography.bodyMedium.merge(
                        TextStyle(color = DarwinTheme.colorScheme.textPrimary, fontSize = 13.sp)
                    ),
                )
            }
        }
    } else {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange, modifier = modifier, enabled = enabled)
    }
}

// ===========================================================================
// Canvas draw helpers
// ===========================================================================

private fun DrawScope.drawCheckmark(color: Color, progress: Float) {
    val w = size.width
    val h = size.height
    val startX = w * 0.219f; val startY = h * 0.531f
    val midX = w * 0.406f; val midY = h * 0.719f
    val endX = w * 0.781f; val endY = h * 0.281f
    val strokeWidth = w * (1.8f / 16f)
    val path = Path().apply {
        moveTo(startX, startY)
        val firstSegmentEnd = (progress * 2f).coerceAtMost(1f)
        lineTo(startX + (midX - startX) * firstSegmentEnd, startY + (midY - startY) * firstSegmentEnd)
        if (progress > 0.5f) {
            val p2 = ((progress - 0.5f) * 2f).coerceAtMost(1f)
            lineTo(midX + (endX - midX) * p2, midY + (endY - midY) * p2)
        }
    }
    drawPath(path, color, style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round))
}

private fun DrawScope.drawIndeterminateDash(color: Color) {
    val dashWidth = size.width * 0.72f
    val dashHeight = size.height * 0.18f
    val left = (size.width - dashWidth) / 2f
    val top = (size.height - dashHeight) / 2f
    drawRoundRect(color, Offset(left, top), Size(dashWidth, dashHeight), CornerRadius(dashHeight / 2f))
}

@Preview
@Composable
private fun CheckboxPreview() {
    DarwinTheme {
        Checkbox(checked = true, onCheckedChange = {})
    }
}
