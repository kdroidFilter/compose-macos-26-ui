package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring
import kotlin.math.PI
import kotlin.math.atan2

// ===========================================================================
// CircularSliderColors
// ===========================================================================

@Immutable
class CircularSliderColors(
    val background: Color,
    val backgroundPressed: Color,
    val backgroundDisabled: Color,
    val indicator: Color,
    val indicatorDisabled: Color,
)

// ===========================================================================
// CircularSliderDefaults
// ===========================================================================

object CircularSliderDefaults {
    @Composable
    fun colors(
        background: Color = if (MacosTheme.colorScheme.isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.05f),
        backgroundPressed: Color = if (MacosTheme.colorScheme.isDark) Color.White.copy(alpha = 0.15f) else Color.Black.copy(alpha = 0.15f),
        backgroundDisabled: Color = if (MacosTheme.colorScheme.isDark) Color.White.copy(alpha = 0.04f) else Color.Black.copy(alpha = 0.04f),
        indicator: Color = if (MacosTheme.colorScheme.isDark) Color.White.copy(alpha = 0.85f) else Color.Black.copy(alpha = 0.85f),
        indicatorDisabled: Color = indicator.copy(alpha = indicator.alpha * 0.38f),
    ) = CircularSliderColors(background, backgroundPressed, backgroundDisabled, indicator, indicatorDisabled)
}

// ===========================================================================
// CircularSliderSize
// ===========================================================================

/**
 * Resolves the circular slider diameter for the given [ControlSize].
 */
fun circularSliderSizeFor(controlSize: ControlSize): Dp = when (controlSize) {
    ControlSize.Mini -> 16.dp
    ControlSize.Small -> 20.dp
    ControlSize.Regular -> 24.dp
    ControlSize.Large -> 28.dp
    ControlSize.ExtraLarge -> 36.dp
}

// ===========================================================================
// CircularSlider
// ===========================================================================

/**
 * macOS-style circular slider / knob control.
 *
 * A simple filled circle with a rotating tick indicator. The background
 * darkens on press. The user can tap or drag anywhere to change the value.
 *
 * @param value Current value within [valueRange].
 * @param onValueChange Called when the value changes during interaction.
 * @param modifier Modifier applied to the control.
 * @param enabled Whether the control is interactive.
 * @param valueRange The range of valid values.
 * @param onValueChangeFinished Called once when the user finishes a gesture.
 * @param colors Color configuration via [CircularSliderDefaults.colors].
 * @param size Diameter of the control, defaults to ControlSize-based sizing.
 * @param interactionSource Interaction source for hover/press tracking.
 */
@Composable
fun CircularSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChangeFinished: (() -> Unit)? = null,
    colors: CircularSliderColors = CircularSliderDefaults.colors(),
    size: Dp = circularSliderSizeFor(LocalControlSize.current),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val min = valueRange.start
    val max = valueRange.endInclusive
    val fraction = ((value - min) / (max - min)).coerceIn(0f, 1f)

    var isDragging by remember { mutableStateOf(false) }

    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = if (isDragging) tween(0) else macosSpring(SpringPreset.Snappy),
    )

    val isHovered by interactionSource.collectIsHoveredAsState()
    val scale by animateFloatAsState(
        targetValue = if (enabled && (isDragging || isHovered)) 1.05f else 1f,
        animationSpec = macosSpring(SpringPreset.Snappy),
    )

    val bgColor = when {
        !enabled -> colors.backgroundDisabled
        isDragging -> colors.backgroundPressed
        else -> colors.background
    }
    val indicatorColor = if (enabled) colors.indicator else colors.indicatorDisabled

    fun angleToValue(position: Offset, center: Offset): Float {
        val delta = position - center
        val angle = atan2(delta.x.toDouble(), -delta.y.toDouble())
        val normalized = if (angle < 0) angle + 2 * PI else angle
        val f = (normalized / (2 * PI)).toFloat().coerceIn(0f, 1f)
        return min + f * (max - min)
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .size(size)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .then(
                    if (enabled) {
                        Modifier
                            .hoverable(interactionSource)
                            .pointerInput(valueRange) {
                                detectTapGestures { offset ->
                                    val center = Offset(
                                        this.size.width / 2f,
                                        this.size.height / 2f,
                                    )
                                    onValueChange(angleToValue(offset, center))
                                    onValueChangeFinished?.invoke()
                                }
                            }
                            .pointerInput(valueRange) {
                                detectDragGestures(
                                    onDragStart = { isDragging = true },
                                    onDragEnd = {
                                        isDragging = false
                                        onValueChangeFinished?.invoke()
                                    },
                                    onDragCancel = {
                                        isDragging = false
                                        onValueChangeFinished?.invoke()
                                    },
                                ) { change, _ ->
                                    change.consume()
                                    val center = Offset(
                                        this.size.width / 2f,
                                        this.size.height / 2f,
                                    )
                                    onValueChange(angleToValue(change.position, center))
                                }
                            }
                    } else {
                        Modifier
                    },
                ),
        ) {
            val canvasSize = this.size
            val center = Offset(canvasSize.width / 2f, canvasSize.height / 2f)
            val radius = canvasSize.minDimension / 2f

            // Background circle
            drawCircle(color = bgColor, radius = radius, center = center)

            // Indicator tick — proportions from the SVG (2x6 in a 24x24 circle)
            val indicatorW = canvasSize.minDimension * (2f / 24f)
            val indicatorH = canvasSize.minDimension * (6f / 24f)
            val indicatorInset = canvasSize.minDimension * (3f / 24f)
            val indicatorCornerRadius = CornerRadius(indicatorW / 2f)

            rotate(degrees = animatedFraction * 360f, pivot = center) {
                drawRoundRect(
                    color = indicatorColor,
                    topLeft = Offset(center.x - indicatorW / 2f, indicatorInset),
                    size = Size(indicatorW, indicatorH),
                    cornerRadius = indicatorCornerRadius,
                )
            }
        }
    }
}
