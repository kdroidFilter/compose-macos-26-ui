package io.github.kdroidfilter.darwinui.components

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
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinSurface
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalControlSize
import io.github.kdroidfilter.darwinui.theme.LocalDarwinSurface
import io.github.kdroidfilter.darwinui.theme.LocalWindowActive
import io.github.kdroidfilter.darwinui.theme.RadioButtonStyle
import io.github.kdroidfilter.darwinui.theme.darwinSpring

// ===========================================================================
// RadioButton — macOS 26 style (no borders, translucent fills)
// ===========================================================================

@Composable
fun RadioButton(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val controlSize = LocalControlSize.current
    val isWindowActive = LocalWindowActive.current
    val surface = LocalDarwinSurface.current
    val style = DarwinTheme.componentStyling.radioButton
    val colors = style.colors
    val metrics = style.metrics

    val isPressed by interactionSource.collectIsPressedAsState()

    // Resolve fill color based on state + surface
    val fillTarget = resolveFillColor(selected, enabled, isPressed, isWindowActive, surface, colors)

    val radioFill by animateColorAsState(
        targetValue = fillTarget,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "radioButtonFill",
    )

    // Pressed overlay for checked state (accent + darkening)
    // Over-glass uses stronger darkening for depth
    val pressedOverlayStrength = when (surface) {
        DarwinSurface.ContentArea -> 0.15f
        DarwinSurface.OverGlass -> 0.17f
    }
    val pressedOverlayAlpha by animateFloatAsState(
        targetValue = if (isPressed && selected && enabled && isWindowActive) pressedOverlayStrength else 0f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "radioButtonPressedOverlay",
    )

    // Dot animation
    val dotAnimationProgress by animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = darwinSpring(preset = DarwinSpringPreset.Snappy),
        label = "radioButtonDotAnimation",
    )

    // Dot color
    val dotColor = when {
        !isWindowActive && !enabled -> colors.inactiveDisabledDot
        !isWindowActive -> colors.inactiveDot
        else -> colors.dot
    }

    // Disabled alpha
    val contentAlpha = when {
        !enabled && selected -> colors.disabledAlpha
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
                    .background(dotColor, CircleShape),
            )
        }
    }
}

private fun resolveFillColor(
    selected: Boolean,
    enabled: Boolean,
    isPressed: Boolean,
    isWindowActive: Boolean,
    surface: DarwinSurface,
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
        DarwinSurface.ContentArea -> colors.inactiveCheckedPressedFill
        DarwinSurface.OverGlass -> colors.inactiveCheckedPressedFill.copy(alpha = 0.17f)
    }
    selected -> when (surface) {
        DarwinSurface.ContentArea -> colors.inactiveCheckedFill
        DarwinSurface.OverGlass -> colors.inactiveCheckedFill.copy(alpha = 0.10f)
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
                interactionSource = interactionSource,
            )
            Spacer(modifier = Modifier.width(DarwinTheme.componentStyling.radioButton.metrics.labelSpacingFor(LocalControlSize.current)))
            BasicText(
                text = label,
                style = DarwinTheme.typography.subheadline.merge(
                    TextStyle(color = DarwinTheme.colorScheme.textPrimary),
                ),
            )
        }
    } else {
        RadioButton(
            selected = selected,
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
        )
    }
}

@Preview
@Composable
private fun RadioButtonPreview() {
    DarwinTheme {
        RadioButton(selected = true, onClick = {})
    }
}
