package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val dotColor: Color,
    val disabledSelectedColor: Color,
    val disabledUnselectedColor: Color,
    val disabledDotColor: Color,
    val unselectedBorderColor: Color,
    val disabledBorderColor: Color,
) {
    fun copy(
        selectedColor: Color = this.selectedColor,
        unselectedColor: Color = this.unselectedColor,
        dotColor: Color = this.dotColor,
        disabledSelectedColor: Color = this.disabledSelectedColor,
        disabledUnselectedColor: Color = this.disabledUnselectedColor,
        disabledDotColor: Color = this.disabledDotColor,
        unselectedBorderColor: Color = this.unselectedBorderColor,
        disabledBorderColor: Color = this.disabledBorderColor,
    ) = RadioButtonColors(
        selectedColor, unselectedColor, dotColor,
        disabledSelectedColor, disabledUnselectedColor, disabledDotColor,
        unselectedBorderColor, disabledBorderColor
    )
}

// ===========================================================================
// RadioButtonDefaults — mirrors M3's RadioButtonDefaults
// ===========================================================================

object RadioButtonDefaults {
    @Composable
    fun colors(
        selectedColor: Color = DarwinTheme.colorScheme.accent,
        unselectedColor: Color = if (DarwinTheme.colorScheme.isDark) Zinc800 else Color.White,
        dotColor: Color = Color.White,
        disabledSelectedColor: Color = selectedColor.copy(alpha = 0.5f),
        disabledUnselectedColor: Color = unselectedColor.copy(alpha = 0.5f),
        disabledDotColor: Color = dotColor.copy(alpha = 0.5f),
        unselectedBorderColor: Color = if (DarwinTheme.colorScheme.isDark) Zinc600 else Color.Black.copy(alpha = 0.20f),
        disabledBorderColor: Color = unselectedBorderColor.copy(alpha = 0.5f),
    ) = RadioButtonColors(
        selectedColor, unselectedColor, dotColor,
        disabledSelectedColor, disabledUnselectedColor, disabledDotColor,
        unselectedBorderColor, disabledBorderColor
    )
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
    val dotAnimationProgress by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "radioButtonAnimation",
    )

    val radioBackground by animateColorAsState(
        targetValue = when {
            !enabled && selected -> colors.disabledSelectedColor
            !enabled -> colors.disabledUnselectedColor
            selected -> colors.selectedColor
            else -> colors.unselectedColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "radioButtonBackgroundColor",
    )

    val borderColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.disabledBorderColor
            selected -> colors.selectedColor
            else -> colors.unselectedBorderColor
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "radioButtonBorderColor",
    )

    val selectableModifier = if (onClick != null) {
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
        modifier = selectableModifier
            .alpha(if (enabled) 1f else 0.5f)
            .size(16.dp)
            .clip(CircleShape)
            .background(radioBackground, CircleShape)
            .border(width = 1.dp, color = borderColor, shape = CircleShape),
    ) {
        if (dotAnimationProgress > 0f) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .graphicsLayer {
                        scaleX = dotAnimationProgress
                        scaleY = dotAnimationProgress
                        alpha = dotAnimationProgress
                    }
                    .background(if (enabled) colors.dotColor else colors.disabledDotColor, CircleShape)
            )
        }
    }
}

// ===========================================================================
// RadioButton — With Label support
// ===========================================================================

@Composable
fun RadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
) {
    if (label != null) {
        val interactionSource = remember { MutableInteractionSource() }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.selectable(
                selected = selected,
                onClick = { if (enabled) onClick() },
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = interactionSource,
                indication = null
            ),
        ) {
            RadioButton(
                selected = selected,
                onClick = null,
                enabled = enabled,
                interactionSource = interactionSource
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicText(
                text = label,
                style = DarwinTheme.typography.bodyMedium.merge(
                    TextStyle(color = DarwinTheme.colorScheme.textPrimary, fontSize = 13.sp)
                ),
            )
        }
    } else {
        RadioButton(selected = selected, onClick = onClick, modifier = modifier, enabled = enabled)
    }
}

@Preview
@Composable
private fun RadioButtonPreview() {
    DarwinTheme {
        RadioButton(selected = true, onClick = {})
    }
}
