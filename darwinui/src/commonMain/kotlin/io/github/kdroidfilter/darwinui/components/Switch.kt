package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
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
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalControlSize
import io.github.kdroidfilter.darwinui.theme.LocalWindowActive
import io.github.kdroidfilter.darwinui.theme.SwitchStyle
import io.github.kdroidfilter.darwinui.theme.darwinSpring

// ===========================================================================
// SwitchColors — all color tokens for every visual state
// ===========================================================================

@Immutable
class SwitchColors(
    // Active window — track colors
    val onTrackColor: Color,
    val offTrackColor: Color,
    val mixedTrackColor: Color,

    // Inactive window — track colors
    val inactiveOnTrackColor: Color,
    val inactiveOffTrackColor: Color,
    val inactiveMixedTrackColor: Color,

    // Thumb
    val thumbColor: Color,

    // Track-level state indicators (macOS 26)
    val onIndicatorColor: Color,
    val offIndicatorColor: Color,
    val mixedIndicatorColor: Color,
) {
    fun trackColor(
        toggleState: ToggleableState,
        isWindowActive: Boolean,
    ): Color = if (isWindowActive) {
        when (toggleState) {
            ToggleableState.On -> onTrackColor
            ToggleableState.Off -> offTrackColor
            ToggleableState.Indeterminate -> mixedTrackColor
        }
    } else {
        when (toggleState) {
            ToggleableState.On -> inactiveOnTrackColor
            ToggleableState.Off -> inactiveOffTrackColor
            ToggleableState.Indeterminate -> inactiveMixedTrackColor
        }
    }
}

// ===========================================================================
// SwitchDefaults
// ===========================================================================

object SwitchDefaults {

    @Composable
    fun colors(
        onTrackColor: Color = Color.Unspecified,
        offTrackColor: Color = Color.Unspecified,
        mixedTrackColor: Color = Color.Unspecified,
        inactiveOnTrackColor: Color = Color.Unspecified,
        inactiveOffTrackColor: Color = Color.Unspecified,
        inactiveMixedTrackColor: Color = Color.Unspecified,
        thumbColor: Color = Color.Unspecified,
        onIndicatorColor: Color = Color.Unspecified,
        offIndicatorColor: Color = Color.Unspecified,
        mixedIndicatorColor: Color = Color.Unspecified,
    ): SwitchColors {
        val style = DarwinTheme.componentStyling.switch.colors
        return SwitchColors(
            onTrackColor = onTrackColor.takeOrElse(style.onTrack),
            offTrackColor = offTrackColor.takeOrElse(style.offTrack),
            mixedTrackColor = mixedTrackColor.takeOrElse(style.mixedTrack),
            inactiveOnTrackColor = inactiveOnTrackColor.takeOrElse(style.inactiveOnTrack),
            inactiveOffTrackColor = inactiveOffTrackColor.takeOrElse(style.inactiveOffTrack),
            inactiveMixedTrackColor = inactiveMixedTrackColor.takeOrElse(style.inactiveMixedTrack),
            thumbColor = thumbColor.takeOrElse(style.thumb),
            onIndicatorColor = onIndicatorColor.takeOrElse(style.onIndicator),
            offIndicatorColor = offIndicatorColor.takeOrElse(style.offIndicator),
            mixedIndicatorColor = mixedIndicatorColor.takeOrElse(style.mixedIndicator),
        )
    }
}

private fun Color.takeOrElse(other: Color): Color =
    if (this != Color.Unspecified) this else other

// ===========================================================================
// Switch — two-state convenience wrapper
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
    TriStateSwitch(
        state = if (checked) ToggleableState.On else ToggleableState.Off,
        onClick = if (onCheckedChange != null) {
            { onCheckedChange(!checked) }
        } else null,
        modifier = modifier,
        thumbContent = thumbContent,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
    )
}

// ===========================================================================
// TriStateSwitch — full three-state implementation (macOS 26)
// ===========================================================================

@Composable
fun TriStateSwitch(
    state: ToggleableState,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val controlSize = LocalControlSize.current
    val isWindowActive = LocalWindowActive.current
    val metrics = DarwinTheme.componentStyling.switch.metrics

    val trackWidth = metrics.trackWidthFor(controlSize)
    val trackHeight = metrics.trackHeightFor(controlSize)
    val thumbWidth = metrics.thumbWidthFor(controlSize)
    val thumbHeight = metrics.thumbHeightFor(controlSize)
    val thumbPadding = metrics.thumbPaddingFor(controlSize)
    val pillShape = RoundedCornerShape(50)

    val isPressed by interactionSource.collectIsPressedAsState()

    // Thumb X offset: Off → left, On → right, Mixed → center
    val thumbTargetOffset = when (state) {
        ToggleableState.Off -> thumbPadding
        ToggleableState.On -> trackWidth - thumbWidth - thumbPadding
        ToggleableState.Indeterminate -> (trackWidth - thumbWidth) / 2
    }

    val thumbOffset by animateDpAsState(
        targetValue = thumbTargetOffset,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "switchThumbOffset",
    )

    val trackColor by animateColorAsState(
        targetValue = colors.trackColor(state, isWindowActive),
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "switchTrackColor",
    )

    // macOS 26: thumb becomes transparent on press
    val thumbAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0f else 1f,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "switchThumbAlpha",
    )

    val toggleModifier = if (onClick != null) {
        modifier.triStateToggleable(
            state = state,
            onClick = { if (enabled) onClick() },
            enabled = enabled,
            role = Role.Switch,
            interactionSource = interactionSource,
            indication = null,
        )
    } else modifier

    val contentAlpha = if (enabled) 1f else metrics.disabledAlpha

    // Indicator sizes (proportional to track height)
    val indicatorBarThickness = trackHeight * 0.125f
    val onIndicatorHeight = trackHeight * 0.5f
    val mixedIndicatorHeight = trackHeight * 0.4375f
    val offIndicatorSize = trackHeight * 0.375f
    val indicatorCenterOffset = trackHeight / 2

    Box(
        modifier = toggleModifier
            .alpha(contentAlpha)
            .size(width = trackWidth, height = trackHeight)
            .clip(pillShape)
            .background(trackColor, pillShape),
    ) {
        // On-state indicator "|" on the left
        if (state == ToggleableState.On) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = indicatorCenterOffset - indicatorBarThickness / 2)
                    .size(width = indicatorBarThickness, height = onIndicatorHeight)
                    .background(colors.onIndicatorColor, pillShape),
            )
        }

        // Mixed-state indicator "|" (shorter) on the left
        if (state == ToggleableState.Indeterminate) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = indicatorCenterOffset - indicatorBarThickness / 2)
                    .size(width = indicatorBarThickness, height = mixedIndicatorHeight)
                    .background(colors.mixedIndicatorColor, pillShape),
            )
        }

        // Off-state indicator "○" on the right
        if (state == ToggleableState.Off) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = -(indicatorCenterOffset - offIndicatorSize / 2))
                    .size(offIndicatorSize)
                    .border(indicatorBarThickness, colors.offIndicatorColor, CircleShape),
            )
        }

        // Pill-shaped thumb (macOS 26: wider than tall)
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .align(Alignment.CenterStart)
                .size(width = thumbWidth, height = thumbHeight)
                .alpha(thumbAlpha)
                .shadow(elevation = 2.dp, shape = pillShape, clip = false)
                .clip(pillShape)
                .background(colors.thumbColor, pillShape),
            contentAlignment = Alignment.Center,
        ) {
            thumbContent?.invoke()
        }
    }
}

// ===========================================================================
// Switcher — convenience wrapper with optional label
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
                style = DarwinTheme.typography.subheadline.merge(
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
