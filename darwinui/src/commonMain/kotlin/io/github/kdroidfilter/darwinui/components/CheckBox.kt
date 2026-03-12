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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.drawscope.DrawScope
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

    val checkboxShape = RoundedCornerShape(4.dp)
    Box(
        contentAlignment = Alignment.Center,
        modifier = toggleModifier
            .alpha(if (enabled) 1f else 0.5f)
            .size(16.dp)
            .clip(checkboxShape)
            .background(boxBackground, checkboxShape)
            .border(width = 1.dp, color = borderColor, shape = checkboxShape),
    ) {
        if (animationProgress > 0f) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = animationProgress
                        scaleY = animationProgress
                        alpha = animationProgress
                    },
            ) {
                drawCheckmark(colors.checkedCheckmarkColor)
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

    val checkboxShape = RoundedCornerShape(4.dp)
    Box(
        contentAlignment = Alignment.Center,
        modifier = clickModifier
            .alpha(if (enabled) 1f else 0.5f)
            .size(16.dp)
            .clip(checkboxShape)
            .background(boxBackground, checkboxShape)
            .border(width = 1.dp, color = borderColor, shape = checkboxShape),
    ) {
        if (animationProgress > 0f) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = animationProgress
                        scaleY = animationProgress
                        alpha = animationProgress
                    },
            ) {
                if (isIndeterminate) drawIndeterminateDash(colors.checkedCheckmarkColor)
                else drawCheckmark(colors.checkedCheckmarkColor)
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
                    style = DarwinTheme.typography.subheadline.merge(
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

private fun DrawScope.drawCheckmark(color: Color) {
    val sx = size.width / 16f
    val sy = size.height / 16f
    val path = Path().apply {
        moveTo(7.02105f * sx, 12.465f * sy)
        cubicTo(
            7.39065f * sx, 12.465f * sy,
            7.67035f * sx, 12.3248f * sy,
            7.86015f * sx, 12.0345f * sy,
        )
        lineTo(12.4552f * sx, 4.99664f * sy)
        cubicTo(
            12.5951f * sx, 4.77639f * sy,
            12.655f * sx, 4.58618f * sy,
            12.655f * sx, 4.39596f * sy,
        )
        cubicTo(
            12.655f * sx, 3.8954f * sy,
            12.2754f * sx, 3.535f * sy,
            11.756f * sx, 3.535f * sy,
        )
        cubicTo(
            11.4063f * sx, 3.535f * sy,
            11.1966f * sx, 3.66515f * sy,
            10.9768f * sx, 3.99552f * sy,
        )
        lineTo(6.99108f * sx, 10.3126f * sy)
        lineTo(4.95327f * sx, 7.75973f * sy)
        cubicTo(
            4.75349f * sx, 7.51946f * sy,
            4.54371f * sx, 7.40934f * sy,
            4.24403f * sx, 7.40934f * sy,
        )
        cubicTo(
            3.72459f * sx, 7.40934f * sy,
            3.345f * sx, 7.77975f * sy,
            3.345f * sx, 8.29032f * sy,
        )
        cubicTo(
            3.345f * sx, 8.51057f * sy,
            3.41492f * sx, 8.7108f * sy,
            3.60472f * sx, 8.93104f * sy,
        )
        lineTo(6.18195f * sx, 12.0746f * sy)
        cubicTo(
            6.40172f * sx, 12.3449f * sy,
            6.66144f * sx, 12.465f * sy,
            7.01106f * sx, 12.465f * sy,
        )
        close()
    }
    drawPath(path, color)
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
