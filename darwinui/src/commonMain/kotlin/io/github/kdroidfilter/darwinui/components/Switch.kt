package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Zinc600
import io.github.kdroidfilter.darwinui.theme.darwinSpring

private val Emerald500 = Color(0xFF10B981)

// ===========================================================================
// SwitchColors — mirrors M3's SwitchColors
// ===========================================================================

@Immutable
class SwitchColors(
    val checkedThumbColor: Color,
    val checkedTrackColor: Color,
    val checkedBorderColor: Color,
    val uncheckedThumbColor: Color,
    val uncheckedTrackColor: Color,
    val uncheckedBorderColor: Color,
    val disabledCheckedThumbColor: Color,
    val disabledCheckedTrackColor: Color,
    val disabledUncheckedThumbColor: Color,
    val disabledUncheckedTrackColor: Color,
) {
    fun copy(
        checkedThumbColor: Color = this.checkedThumbColor,
        checkedTrackColor: Color = this.checkedTrackColor,
        checkedBorderColor: Color = this.checkedBorderColor,
        uncheckedThumbColor: Color = this.uncheckedThumbColor,
        uncheckedTrackColor: Color = this.uncheckedTrackColor,
        uncheckedBorderColor: Color = this.uncheckedBorderColor,
        disabledCheckedThumbColor: Color = this.disabledCheckedThumbColor,
        disabledCheckedTrackColor: Color = this.disabledCheckedTrackColor,
        disabledUncheckedThumbColor: Color = this.disabledUncheckedThumbColor,
        disabledUncheckedTrackColor: Color = this.disabledUncheckedTrackColor,
    ) = SwitchColors(
        checkedThumbColor, checkedTrackColor, checkedBorderColor,
        uncheckedThumbColor, uncheckedTrackColor, uncheckedBorderColor,
        disabledCheckedThumbColor, disabledCheckedTrackColor,
        disabledUncheckedThumbColor, disabledUncheckedTrackColor,
    )
}

// ===========================================================================
// SwitchDefaults — mirrors M3's SwitchDefaults
// ===========================================================================

object SwitchDefaults {
    @Composable
    fun colors(
        checkedThumbColor: Color = Color.White,
        checkedTrackColor: Color = DarwinTheme.colorScheme.accent,
        checkedBorderColor: Color = Color.Transparent,
        uncheckedThumbColor: Color = Color.White,
        uncheckedTrackColor: Color = Zinc600,
        uncheckedBorderColor: Color = Color.Transparent,
        disabledCheckedThumbColor: Color = checkedThumbColor.copy(alpha = 0.5f),
        disabledCheckedTrackColor: Color = checkedTrackColor.copy(alpha = 0.5f),
        disabledUncheckedThumbColor: Color = uncheckedThumbColor.copy(alpha = 0.5f),
        disabledUncheckedTrackColor: Color = uncheckedTrackColor.copy(alpha = 0.5f),
    ) = SwitchColors(
        checkedThumbColor, checkedTrackColor, checkedBorderColor,
        uncheckedThumbColor, uncheckedTrackColor, uncheckedBorderColor,
        disabledCheckedThumbColor, disabledCheckedTrackColor,
        disabledUncheckedThumbColor, disabledUncheckedTrackColor,
    )
}

// ===========================================================================
// Switch — M3-compatible
// ===========================================================================

@Composable
fun Switch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val trackWidth = 36.dp
    val trackHeight = 20.dp
    val thumbPadding = 2.dp

    val trackShape = RoundedCornerShape(50)

    val isPressed by interactionSource.collectIsPressedAsState()

    // Compute target thumb size based on state (before animation)
    val targetThumbSize = when {
        isPressed && enabled -> 18.dp
        checked -> 16.dp
        else -> 12.dp
    }

    // Thumb grows wider on press (M3-style), larger when checked
    val thumbSize by animateDpAsState(
        targetValue = targetThumbSize,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "switchThumbSize",
    )

    // Thumb slides to the right end when checked, using target size so the offset targets a stable position
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) (trackWidth - targetThumbSize - thumbPadding) else 0.dp,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "switchThumbOffset",
    )

    val trackColor by animateColorAsState(
        targetValue = if (checked) {
            if (enabled) colors.checkedTrackColor else colors.disabledCheckedTrackColor
        } else {
            if (enabled) colors.uncheckedTrackColor else colors.disabledUncheckedTrackColor
        },
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "switchTrackColor",
    )

    val thumbColor by animateColorAsState(
        targetValue = if (checked) {
            if (enabled) colors.checkedThumbColor else colors.disabledCheckedThumbColor
        } else {
            if (enabled) colors.uncheckedThumbColor else colors.disabledUncheckedThumbColor
        },
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "switchThumbColor",
    )

    val toggleModifier = if (onCheckedChange != null) {
        modifier.toggleable(
            value = checked,
            onValueChange = { if (enabled) onCheckedChange(it) },
            enabled = enabled,
            role = Role.Switch,
            interactionSource = interactionSource,
            indication = null,
        )
    } else modifier

    Box(
        modifier = toggleModifier
            .alpha(if (enabled) 1f else 0.5f)
            .size(width = trackWidth, height = trackHeight)
            .clip(trackShape)
            .background(trackColor, trackShape),
    ) {
        Box(
            modifier = Modifier
                .offset(x = thumbOffset + thumbPadding)
                .align(Alignment.CenterStart)
                .size(thumbSize) // animated: small when unchecked, larger when checked, widest on press
                .shadow(elevation = 1.dp, shape = CircleShape, clip = false)
                .clip(CircleShape)
                .background(thumbColor, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            thumbContent?.invoke()
        }
    }
}

// ===========================================================================
// Switcher — backward-compatible alias
// ===========================================================================

@Composable
fun Switcher(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
) {
    if (label != null) {
        androidx.compose.foundation.layout.Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            Switch(checked = checked, onCheckedChange = onCheckedChange, enabled = enabled)
            androidx.compose.foundation.layout.Spacer(Modifier.size(8.dp))
            androidx.compose.foundation.text.BasicText(
                text = label,
                style = DarwinTheme.typography.bodyMedium.merge(
                    androidx.compose.ui.text.TextStyle(color = DarwinTheme.colorScheme.textPrimary)
                ),
            )
        }
    } else {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier,
            enabled = enabled,
        )
    }
}

@Preview
@Composable
private fun SwitchPreview() {
    DarwinTheme {
        Switch(checked = true, onCheckedChange = {})
    }
}
