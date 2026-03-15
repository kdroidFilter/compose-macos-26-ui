package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalSurface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring

/**
 * A macOS-style stepper control with increment (up) and decrement (down) buttons.
 *
 * The stepper is a vertical pair of chevron buttons separated by a thin line.
 * It supports all five [ControlSize] presets and disabled state.
 *
 * @param onIncrement called when the up (increment) button is clicked
 * @param onDecrement called when the down (decrement) button is clicked
 * @param modifier the [Modifier] to be applied to this stepper
 * @param enabled whether the stepper is enabled
 */
@Composable
fun Stepper(
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val controlSize = LocalControlSize.current
    val surface = LocalSurface.current
    val style = MacosTheme.componentStyling.stepper
    val metrics = style.metrics
    val colors = style.colors

    val isOverGlass = surface == Surface.OverGlass
    val bgIdle = if (isOverGlass) colors.overGlassBackground else colors.background
    val bgPressed = if (isOverGlass) colors.overGlassBackgroundPressed else colors.backgroundPressed
    val bgDisabled = if (isOverGlass) colors.overGlassBackgroundDisabled else colors.backgroundDisabled

    val width = metrics.widthFor(controlSize)
    val height = metrics.heightFor(controlSize)
    val cornerRadius = metrics.cornerRadiusFor(controlSize)

    val incrementInteraction = remember { MutableInteractionSource() }
    val decrementInteraction = remember { MutableInteractionSource() }
    val isIncrementPressed by incrementInteraction.collectIsPressedAsState()
    val isDecrementPressed by decrementInteraction.collectIsPressedAsState()

    val incrementBg by animateColorAsState(
        targetValue = when {
            !enabled -> bgDisabled
            isIncrementPressed -> bgPressed
            else -> bgIdle
        },
        animationSpec = macosSpring(SpringPreset.Snappy),
    )
    val decrementBg by animateColorAsState(
        targetValue = when {
            !enabled -> bgDisabled
            isDecrementPressed -> bgPressed
            else -> bgIdle
        },
        animationSpec = macosSpring(SpringPreset.Snappy),
    )

    val arrowColor = if (enabled) colors.arrow else colors.arrowDisabled

    val shape = RoundedCornerShape(cornerRadius)

    Column(
        modifier = modifier
            .size(width, height)
            .clip(shape),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Increment (up) button
        Box(
            modifier = Modifier
                .width(width)
                .weight(1f)
                .background(incrementBg)
                .clickable(
                    interactionSource = incrementInteraction,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onIncrement,
                ),
            contentAlignment = Alignment.Center,
        ) {
            ChevronUp(
                color = arrowColor,
                controlSize = controlSize,
            )
        }

        // Separator
        Separator(
            color = colors.separator,
            width = width,
            controlSize = controlSize,
        )

        // Decrement (down) button
        Box(
            modifier = Modifier
                .width(width)
                .weight(1f)
                .background(decrementBg)
                .clickable(
                    interactionSource = decrementInteraction,
                    indication = null,
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onDecrement,
                ),
            contentAlignment = Alignment.Center,
        ) {
            ChevronDown(
                color = arrowColor,
                controlSize = controlSize,
            )
        }
    }
}

/**
 * Layout mode for [StepperField].
 */
enum class StepperFieldLayout {
    /** TextField and Stepper are placed side by side with a 4dp gap. */
    Outside,
    /** Stepper is overlaid inside the trailing edge of the TextField. */
    Inside,
}

/**
 * A macOS-style stepper combined with a text field for numeric input.
 *
 * Matches the Sketch "Outside Field" and "Inside Field" composite variants.
 *
 * @param value the current text value displayed in the field
 * @param onValueChange called when the text value changes (from keyboard input)
 * @param onIncrement called when the up (increment) button is clicked
 * @param onDecrement called when the down (decrement) button is clicked
 * @param modifier the [Modifier] to be applied to this component
 * @param enabled whether the component is enabled
 * @param layout whether the stepper sits outside or inside the text field
 * @param placeholder optional placeholder composable
 * @param keyboardOptions keyboard options for the text field (defaults to numeric)
 * @param keyboardActions keyboard actions for the text field
 */
@Composable
fun StepperField(
    value: String,
    onValueChange: (String) -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    layout: StepperFieldLayout = StepperFieldLayout.Outside,
    placeholder: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val controlSize = LocalControlSize.current
    val stepperMetrics = MacosTheme.componentStyling.stepper.metrics
    val cornerRadius = MacosTheme.componentStyling.textField.metrics.cornerRadiusFor(controlSize)

    when (layout) {
        StepperFieldLayout.Outside -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.width(stepperMetrics.fieldWidth),
                    enabled = enabled,
                    singleLine = true,
                    placeholder = placeholder,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                )
                Spacer(Modifier.width(stepperMetrics.fieldGap))
                Stepper(
                    onIncrement = onIncrement,
                    onDecrement = onDecrement,
                    enabled = enabled,
                )
            }
        }

        StepperFieldLayout.Inside -> {
            val totalWidth = stepperMetrics.fieldWidth + stepperMetrics.fieldGap + stepperMetrics.widthFor(controlSize)
            val shape = RoundedCornerShape(cornerRadius)
            Box(
                modifier = modifier
                    .width(totalWidth)
                    .clip(shape),
            ) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = enabled,
                    singleLine = true,
                    placeholder = placeholder,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                )
                Stepper(
                    onIncrement = onIncrement,
                    onDecrement = onDecrement,
                    enabled = enabled,
                    modifier = Modifier.align(Alignment.CenterEnd),
                )
            }
        }
    }
}

// Separator line inset from each side
@Composable
private fun Separator(
    color: Color,
    width: Dp,
    controlSize: ControlSize,
) {
    val inset = separatorInsetFor(controlSize)
    val separatorWidth = width - inset * 2
    Spacer(
        modifier = Modifier
            .width(separatorWidth)
            .height(1.dp)
            .background(color, RoundedCornerShape(0.5.dp)),
    )
}

private fun separatorInsetFor(controlSize: ControlSize): Dp = when (controlSize) {
    ControlSize.Mini -> 2.dp
    ControlSize.Small -> 3.dp
    ControlSize.Regular -> 3.dp
    ControlSize.Large -> 4.dp
    ControlSize.ExtraLarge -> 5.dp
}

// Chevron sizes per control size (from Sketch arrow path dimensions)
private fun chevronWidthFor(controlSize: ControlSize): Dp = when (controlSize) {
    ControlSize.Mini -> 6.8.dp
    ControlSize.Small -> 8.dp
    ControlSize.Regular -> 10.6.dp
    ControlSize.Large -> 10.6.dp
    ControlSize.ExtraLarge -> 10.6.dp
}

private fun chevronHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
    ControlSize.Mini -> 3.8.dp
    ControlSize.Small -> 4.6.dp
    ControlSize.Regular -> 6.dp
    ControlSize.Large -> 6.dp
    ControlSize.ExtraLarge -> 6.dp
}

@Composable
private fun ChevronUp(
    color: Color,
    controlSize: ControlSize,
) {
    val w = chevronWidthFor(controlSize)
    val h = chevronHeightFor(controlSize)
    Spacer(
        modifier = Modifier
            .size(w, h)
            .drawWithCache {
                val path = buildUpChevronPath(size)
                onDrawBehind {
                    drawPath(path, color)
                }
            },
    )
}

@Composable
private fun ChevronDown(
    color: Color,
    controlSize: ControlSize,
) {
    val w = chevronWidthFor(controlSize)
    val h = chevronHeightFor(controlSize)
    Spacer(
        modifier = Modifier
            .size(w, h)
            .drawWithCache {
                val path = buildDownChevronPath(size)
                onDrawBehind {
                    drawPath(path, color)
                }
            },
    )
}

// Up chevron path — extracted from Sketch vector points (normalized)
// The chevron points upward: tip at top-center, two arms going down-left and down-right
// with rounded endpoints
private fun buildUpChevronPath(size: Size): Path = Path().apply {
    val w = size.width
    val h = size.height

    // Normalized coordinates from Sketch (Arrow 1 = up arrow at top half)
    // tip at center-top, arms sweep down to left and right
    moveTo(0.500f * w, 0.000f * h) // tip top center

    // Right arm curve from tip
    cubicTo(
        0.515f * w, 0.000f * h,
        0.529f * w, 0.011f * h,
        0.539f * w, 0.031f * h,
    )

    // Right arm going down-right
    lineTo(0.984f * w, 0.840f * h)

    // Right arm bottom-right rounded end
    cubicTo(
        0.994f * w, 0.858f * h,
        1.000f * w, 0.879f * h,
        1.000f * w, 0.907f * h,
    )
    cubicTo(
        1.000f * w, 0.941f * h,
        0.985f * w, 0.973f * h,
        0.948f * w, 1.000f * h,
    )
    cubicTo(
        0.934f * w, 1.000f * h,
        0.920f * w, 0.995f * h,
        0.910f * w, 0.973f * h,
    )

    // Left arm back to tip via center
    lineTo(0.500f * w, 0.229f * h)
    lineTo(0.090f * w, 0.973f * h)

    // Left arm bottom-left rounded end
    cubicTo(
        0.080f * w, 0.995f * h,
        0.066f * w, 1.000f * h,
        0.052f * w, 1.000f * h,
    )
    cubicTo(
        0.015f * w, 0.973f * h,
        0.000f * w, 0.941f * h,
        0.000f * w, 0.907f * h,
    )
    cubicTo(
        0.000f * w, 0.879f * h,
        0.006f * w, 0.858f * h,
        0.016f * w, 0.840f * h,
    )

    // Left arm going up to tip
    lineTo(0.461f * w, 0.031f * h)
    cubicTo(
        0.471f * w, 0.011f * h,
        0.485f * w, 0.000f * h,
        0.500f * w, 0.000f * h,
    )

    close()
}

// Down chevron path — mirror of up
private fun buildDownChevronPath(size: Size): Path = Path().apply {
    val w = size.width
    val h = size.height

    moveTo(0.500f * w, 1.000f * h) // tip bottom center

    // Right arm curve from tip going up-right
    cubicTo(
        0.515f * w, 1.000f * h,
        0.529f * w, 0.989f * h,
        0.539f * w, 0.970f * h,
    )
    lineTo(0.984f * w, 0.160f * h)

    // Right arm top-right rounded end
    cubicTo(
        0.994f * w, 0.142f * h,
        1.000f * w, 0.120f * h,
        1.000f * w, 0.094f * h,
    )
    cubicTo(
        1.000f * w, 0.059f * h,
        0.985f * w, 0.027f * h,
        0.948f * w, 0.000f * h,
    )
    cubicTo(
        0.934f * w, 0.000f * h,
        0.920f * w, 0.010f * h,
        0.910f * w, 0.027f * h,
    )

    // Left arm back to tip via center
    lineTo(0.500f * w, 0.771f * h)
    lineTo(0.090f * w, 0.027f * h)

    // Left arm top-left rounded end
    cubicTo(
        0.080f * w, 0.010f * h,
        0.066f * w, 0.000f * h,
        0.052f * w, 0.000f * h,
    )
    cubicTo(
        0.015f * w, 0.027f * h,
        0.000f * w, 0.059f * h,
        0.000f * w, 0.094f * h,
    )
    cubicTo(
        0.000f * w, 0.120f * h,
        0.006f * w, 0.142f * h,
        0.016f * w, 0.161f * h,
    )

    // Left arm going down to tip
    lineTo(0.461f * w, 0.970f * h)
    cubicTo(
        0.471f * w, 0.989f * h,
        0.485f * w, 1.000f * h,
        0.500f * w, 1.000f * h,
    )

    close()
}
