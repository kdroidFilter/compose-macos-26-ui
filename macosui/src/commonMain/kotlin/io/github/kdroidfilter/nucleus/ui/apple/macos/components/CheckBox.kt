package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.CheckboxStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSurface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalWindowActive
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring

// ===========================================================================
// CheckboxColors — customizable color overrides
// ===========================================================================

@Immutable
class CheckboxColors(
    val checkedFillColor: Color,
    val uncheckedFillColor: Color,
    val checkmarkColor: Color,
)

object CheckboxDefaults {
    @Composable
    fun colors(
        checkedFillColor: Color = Color.Unspecified,
        uncheckedFillColor: Color = Color.Unspecified,
        checkmarkColor: Color = Color.Unspecified,
    ) = CheckboxColors(checkedFillColor, uncheckedFillColor, checkmarkColor)
}

// ===========================================================================
// Checkbox — macOS 26 style (no borders, translucent fills)
// ===========================================================================

@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    TriStateCheckbox(
        state = if (checked) ToggleableState.On else ToggleableState.Off,
        onClick = if (onCheckedChange != null) {
            { onCheckedChange(!checked) }
        } else null,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
    )
}

// ===========================================================================
// TriStateCheckbox — full three-state implementation (macOS 26)
// ===========================================================================

@Composable
fun TriStateCheckbox(
    state: ToggleableState,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val controlSize = LocalControlSize.current
    val isWindowActive = LocalWindowActive.current
    val surface = LocalSurface.current
    val style = MacosTheme.componentStyling.checkbox
    val themeColors = style.colors
    val metrics = style.metrics

    val isPressed by interactionSource.collectIsPressedAsState()
    val isActive = state != ToggleableState.Off

    // Resolve fill color
    val fillTarget = resolveCheckboxFillColor(isActive, enabled, isPressed, isWindowActive, surface, themeColors)

    // Apply custom color overrides
    val customFillTarget = when {
        colors?.checkedFillColor != null && colors.checkedFillColor != Color.Unspecified && isActive && isWindowActive && enabled -> colors.checkedFillColor
        colors?.uncheckedFillColor != null && colors.uncheckedFillColor != Color.Unspecified && !isActive -> colors.uncheckedFillColor
        else -> fillTarget
    }

    val boxFill by animateColorAsState(
        targetValue = customFillTarget,
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "checkboxFill",
    )

    // Pressed overlay for checked/mixed state
    val pressedOverlayStrength = when (surface) {
        Surface.ContentArea -> 0.15f
        Surface.OverGlass -> 0.17f
    }
    val pressedOverlayAlpha by animateFloatAsState(
        targetValue = if (isPressed && isActive && enabled && isWindowActive) pressedOverlayStrength else 0f,
        animationSpec = macosSpring(SpringPreset.Snappy),
        label = "checkboxPressedOverlay",
    )

    // Checkmark/dash animation
    val animationProgress by animateFloatAsState(
        targetValue = if (isActive) 1f else 0f,
        animationSpec = macosSpring(preset = SpringPreset.Snappy),
        label = "checkboxAnimation",
    )

    // Checkmark color
    val resolvedCheckmarkColor = if (colors?.checkmarkColor != null && colors.checkmarkColor != Color.Unspecified) {
        colors.checkmarkColor
    } else {
        when {
            !isWindowActive && !enabled -> themeColors.inactiveDisabledCheckmark
            !isWindowActive -> themeColors.inactiveCheckmark
            else -> themeColors.checkmark
        }
    }

    // Disabled alpha
    val contentAlpha = when {
        !enabled && isActive -> themeColors.disabledAlpha
        !enabled -> 1f
        else -> 1f
    }

    val toggleModifier = if (onClick != null) {
        modifier.toggleable(
            value = state == ToggleableState.On,
            onValueChange = { if (enabled) onClick() },
            enabled = enabled,
            role = Role.Checkbox,
            interactionSource = interactionSource,
            indication = null,
        )
    } else modifier

    val checkboxShape = RoundedCornerShape(metrics.cornerSizeFor(controlSize))

    Box(
        contentAlignment = Alignment.Center,
        modifier = toggleModifier
            .alpha(contentAlpha)
            .size(metrics.sizeFor(controlSize))
            .clip(checkboxShape)
            .background(boxFill, checkboxShape),
    ) {
        // Pressed darkening overlay
        if (pressedOverlayAlpha > 0f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = pressedOverlayAlpha), checkboxShape),
            )
        }

        // Checkmark or dash indicator
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
                if (state == ToggleableState.Indeterminate) {
                    drawIndeterminateDash(resolvedCheckmarkColor)
                } else {
                    drawCheckmark(resolvedCheckmarkColor)
                }
            }
        }
    }
}

private fun resolveCheckboxFillColor(
    isActive: Boolean,
    enabled: Boolean,
    isPressed: Boolean,
    isWindowActive: Boolean,
    surface: Surface,
    colors: CheckboxStyle.Colors,
): Color = when {
    // Disabled states
    !enabled && isActive && isWindowActive -> colors.disabledCheckedFill
    !enabled && isActive -> colors.inactiveDisabledFill
    !enabled -> colors.uncheckedFill

    // Active window
    isWindowActive && isActive -> colors.checkedFill
    isWindowActive && isPressed -> colors.pressedOverlay
    isWindowActive -> colors.uncheckedFill

    // Inactive window — Over-glass uses slightly lighter fills
    isActive && isPressed -> when (surface) {
        Surface.ContentArea -> colors.inactiveCheckedPressedFill
        Surface.OverGlass -> colors.inactiveCheckedPressedFill.copy(alpha = 0.17f)
    }
    isActive -> when (surface) {
        Surface.ContentArea -> colors.inactiveCheckedFill
        Surface.OverGlass -> colors.inactiveCheckedFill.copy(alpha = 0.10f)
    }
    isPressed -> colors.pressedOverlay
    else -> colors.uncheckedFill
}

// ===========================================================================
// CheckBox — convenience wrapper with label support
// ===========================================================================

@Composable
fun CheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    indeterminate: Boolean = false,
    label: String? = null,
    enabled: Boolean = true,
    colors: CheckboxColors? = null,
) {
    if (indeterminate || label != null) {
        val interactionSource = remember { MutableInteractionSource() }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.toggleable(
                value = checked,
                onValueChange = { if (enabled) onCheckedChange(it) },
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = null,
            ),
        ) {
            if (indeterminate) {
                TriStateCheckbox(
                    state = ToggleableState.Indeterminate,
                    onClick = null,
                    enabled = enabled,
                    colors = colors,
                    interactionSource = interactionSource,
                )
            } else {
                Checkbox(
                    checked = checked,
                    onCheckedChange = null,
                    enabled = enabled,
                    colors = colors,
                    interactionSource = interactionSource,
                )
            }
            if (label != null) {
                Spacer(modifier = Modifier.width(MacosTheme.componentStyling.checkbox.metrics.labelSpacingFor(LocalControlSize.current)))
                BasicText(
                    text = label,
                    style = MacosTheme.typography.subheadline.merge(
                        TextStyle(color = MacosTheme.colorScheme.textPrimary),
                    ),
                )
            }
        }
    } else {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange, modifier = modifier, enabled = enabled, colors = colors)
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
    MacosTheme {
        Checkbox(checked = true, onCheckedChange = {})
    }
}
