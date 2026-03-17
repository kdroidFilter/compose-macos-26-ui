package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSurface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalWindowActive
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.RadioButtonStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring

// ===========================================================================
// RadioButtonColors — customizable color overrides
// ===========================================================================

@Immutable
class RadioButtonColors(
    val selectedFillColor: Color,
    val unselectedFillColor: Color,
    val dotColor: Color,
)

object RadioButtonDefaults {
    @Composable
    fun colors(
        selectedFillColor: Color = Color.Unspecified,
        unselectedFillColor: Color = Color.Unspecified,
        dotColor: Color = Color.Unspecified,
    ) = RadioButtonColors(selectedFillColor, unselectedFillColor, dotColor)
}

// ===========================================================================
// RadioButton — macOS 26 style (no borders, translucent fills)
// ===========================================================================

@Composable
fun RadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: RadioButtonColors? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val controlSize = LocalControlSize.current
    val isWindowActive = LocalWindowActive.current
    val surface = LocalSurface.current
    val style = MacosTheme.componentStyling.radioButton
    val themeColors = style.colors
    val metrics = style.metrics

    val isPressed by interactionSource.collectIsPressedAsState()

    // Resolve fill color based on state + surface
    val fillTarget = resolveFillColor(selected, enabled, isPressed, isWindowActive, surface, themeColors)

    // Apply custom color overrides
    val customFillTarget = when {
        colors?.selectedFillColor != null && colors.selectedFillColor != Color.Unspecified && selected && isWindowActive && enabled -> colors.selectedFillColor
        colors?.unselectedFillColor != null && colors.unselectedFillColor != Color.Unspecified && !selected -> colors.unselectedFillColor
        else -> fillTarget
    }

    val radioFill by animateColorAsState(
        targetValue = customFillTarget,
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "radioButtonFill",
    )

    // Pressed overlay for checked state (accent + darkening)
    // Over-glass uses stronger darkening for depth
    val pressedOverlayStrength = when (surface) {
        Surface.ContentArea -> 0.15f
        Surface.OverGlass -> 0.17f
    }
    val pressedOverlayAlpha by animateFloatAsState(
        targetValue = if (isPressed && selected && enabled && isWindowActive) pressedOverlayStrength else 0f,
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "radioButtonPressedOverlay",
    )

    // Dot animation
    val dotAnimationProgress by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = macosSpring(preset = SpringPreset.Snappy),
        label = "radioButtonDotAnimation",
    )

    // Dot color
    val resolvedDotColor = if (colors?.dotColor != null && colors.dotColor != Color.Unspecified) {
        colors.dotColor
    } else {
        when {
            !isWindowActive && !enabled -> themeColors.inactiveDisabledDot
            !isWindowActive -> themeColors.inactiveDot
            else -> themeColors.dot
        }
    }

    // Disabled alpha
    val contentAlpha = when {
        !enabled && selected -> themeColors.disabledAlpha
        !enabled -> 1f
        else -> 1f
    }

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
            .alpha(contentAlpha)
            .size(metrics.sizeFor(controlSize))
            .clip(CircleShape)
            .background(radioFill, CircleShape),
    ) {
        // Pressed darkening overlay (only for active-window checked state)
        if (pressedOverlayAlpha > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = pressedOverlayAlpha), CircleShape),
            )
        }

        // Dot indicator
        if (dotAnimationProgress > 0f) {
            Box(
                modifier = Modifier
                    .size(metrics.dotSizeFor(controlSize))
                    .graphicsLayer {
                        scaleX = dotAnimationProgress
                        scaleY = dotAnimationProgress
                        alpha = dotAnimationProgress
                    }
                    .background(resolvedDotColor, CircleShape),
            )
        }
    }
}

private fun resolveFillColor(
    selected: Boolean,
    enabled: Boolean,
    isPressed: Boolean,
    isWindowActive: Boolean,
    surface: Surface,
    colors: RadioButtonStyle.Colors,
): Color = when {
    // Disabled states
    !enabled && selected && isWindowActive -> colors.disabledCheckedFill
    !enabled && selected -> colors.inactiveDisabledFill
    !enabled -> colors.uncheckedFill

    // Active window
    isWindowActive && selected -> colors.checkedFill
    isWindowActive && isPressed -> colors.pressedOverlay
    isWindowActive -> colors.uncheckedFill

    // Inactive window — Over-glass uses slightly lighter fills
    selected && isPressed -> when (surface) {
        Surface.ContentArea -> colors.inactiveCheckedPressedFill
        Surface.OverGlass -> colors.inactiveCheckedPressedFill.copy(alpha = 0.17f)
    }
    selected -> when (surface) {
        Surface.ContentArea -> colors.inactiveCheckedFill
        Surface.OverGlass -> colors.inactiveCheckedFill.copy(alpha = 0.10f)
    }
    isPressed -> colors.pressedOverlay
    else -> colors.uncheckedFill
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
    colors: RadioButtonColors? = null,
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
                indication = null,
            ),
        ) {
            RadioButton(
                selected = selected,
                onClick = null,
                enabled = enabled,
                colors = colors,
                interactionSource = interactionSource,
            )
            Spacer(modifier = Modifier.width(MacosTheme.componentStyling.radioButton.metrics.labelSpacingFor(LocalControlSize.current)))
            BasicText(
                text = label,
                style = MacosTheme.typography.subheadline.merge(
                    TextStyle(color = MacosTheme.colorScheme.textPrimary),
                ),
            )
        }
    } else {
        RadioButton(
            selected = selected,
            onClick = onClick as (() -> Unit)?,
            modifier = modifier,
            enabled = enabled,
            colors = colors,
        )
    }
}

@Preview
@Composable
private fun RadioButtonPreview() {
    MacosTheme {
        RadioButton(selected = true, onClick = {})
    }
}
