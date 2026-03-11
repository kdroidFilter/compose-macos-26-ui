package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.Blue500
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Zinc600
import io.github.kdroidfilter.darwinui.theme.Zinc800
import io.github.kdroidfilter.darwinui.theme.darwinSpring

// ===========================================================================
// RadioButtonColors — mirrors M3's RadioButtonColors
// ===========================================================================

@Immutable
class RadioButtonColors(
    val selectedColor: Color,
    val unselectedColor: Color,
    val disabledSelectedColor: Color,
    val disabledUnselectedColor: Color,
) {
    fun copy(
        selectedColor: Color = this.selectedColor,
        unselectedColor: Color = this.unselectedColor,
        disabledSelectedColor: Color = this.disabledSelectedColor,
        disabledUnselectedColor: Color = this.disabledUnselectedColor,
    ) = RadioButtonColors(selectedColor, unselectedColor, disabledSelectedColor, disabledUnselectedColor)
}

// ===========================================================================
// RadioButtonDefaults — mirrors M3's RadioButtonDefaults
// ===========================================================================

object RadioButtonDefaults {
    @Composable
    fun colors(
        selectedColor: Color = Blue500,
        unselectedColor: Color = if (DarwinTheme.colorScheme.isDark) Zinc600 else Color.Black.copy(alpha = 0.30f),
        disabledSelectedColor: Color = selectedColor.copy(alpha = 0.5f),
        disabledUnselectedColor: Color = unselectedColor.copy(alpha = 0.5f),
    ) = RadioButtonColors(selectedColor, unselectedColor, disabledSelectedColor, disabledUnselectedColor)
}

// ===========================================================================
// RadioButton — M3-compatible
// ===========================================================================

@Composable
fun RadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: RadioButtonColors = RadioButtonDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val dotScale by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "radioDotScale",
    )

    // Animate ring/dot color transition between selected and unselected states
    val ringColor by animateColorAsState(
        targetValue = if (selected) {
            if (enabled) colors.selectedColor else colors.disabledSelectedColor
        } else {
            if (enabled) colors.unselectedColor else colors.disabledUnselectedColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "radioRingColor",
    )

    val clickModifier = if (onClick != null) {
        modifier.selectable(
            selected = selected,
            onClick = { if (enabled) onClick() },
            enabled = enabled,
            role = Role.RadioButton,
            interactionSource = interactionSource,
            indication = null,
        )
    } else modifier

    Box(
        contentAlignment = Alignment.Center,
        modifier = clickModifier
            .alpha(if (enabled) 1f else 0.5f)
            .size(20.dp),
    ) {
        Canvas(modifier = Modifier.size(20.dp)) {
            val radius = size.minDimension / 2f
            val strokeWidth = size.minDimension * 0.1f
            drawCircle(
                color = ringColor,
                radius = radius - strokeWidth / 2f,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth),
            )
            if (dotScale > 0f) {
                drawCircle(
                    color = ringColor,
                    radius = (radius - strokeWidth * 2f) * dotScale,
                    style = Fill,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RadioButtonPreview() {
    DarwinTheme {
        RadioButton(selected = true, onClick = {})
    }
}
