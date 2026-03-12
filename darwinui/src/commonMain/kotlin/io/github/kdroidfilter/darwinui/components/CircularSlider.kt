package io.github.kdroidfilter.darwinui.components

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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import kotlin.math.PI
import kotlin.math.atan2

// ===========================================================================
// CircularSliderColors
// ===========================================================================

@Immutable
class CircularSliderColors(
    val backgroundColor: Color,
    val trackColor: Color,
    val activeTrackColor: Color,
    val indicatorColor: Color,
    val disabledBackgroundColor: Color,
    val disabledTrackColor: Color,
    val disabledActiveTrackColor: Color,
    val disabledIndicatorColor: Color,
) {
    fun copy(
        backgroundColor: Color = this.backgroundColor,
        trackColor: Color = this.trackColor,
        activeTrackColor: Color = this.activeTrackColor,
        indicatorColor: Color = this.indicatorColor,
        disabledBackgroundColor: Color = this.disabledBackgroundColor,
        disabledTrackColor: Color = this.disabledTrackColor,
        disabledActiveTrackColor: Color = this.disabledActiveTrackColor,
        disabledIndicatorColor: Color = this.disabledIndicatorColor,
    ) = CircularSliderColors(
        backgroundColor, trackColor, activeTrackColor, indicatorColor,
        disabledBackgroundColor, disabledTrackColor, disabledActiveTrackColor, disabledIndicatorColor,
    )
}

// ===========================================================================
// CircularSliderDefaults
// ===========================================================================

object CircularSliderDefaults {
    @Composable
    fun colors(
        backgroundColor: Color = if (DarwinTheme.colorScheme.isDark) Color.White.copy(alpha = 0.05f) else Color.Black.copy(alpha = 0.05f),
        trackColor: Color = if (DarwinTheme.colorScheme.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f),
        activeTrackColor: Color = DarwinTheme.colorScheme.accent,
        indicatorColor: Color = if (DarwinTheme.colorScheme.isDark) Color.White.copy(alpha = 0.85f) else Color.Black.copy(alpha = 0.85f),
        disabledBackgroundColor: Color = backgroundColor.copy(alpha = backgroundColor.alpha * 0.38f),
        disabledTrackColor: Color = trackColor.copy(alpha = trackColor.alpha * 0.38f),
        disabledActiveTrackColor: Color = activeTrackColor.copy(alpha = 0.38f),
        disabledIndicatorColor: Color = indicatorColor.copy(alpha = indicatorColor.alpha * 0.38f),
    ) = CircularSliderColors(
        backgroundColor, trackColor, activeTrackColor, indicatorColor,
        disabledBackgroundColor, disabledTrackColor, disabledActiveTrackColor, disabledIndicatorColor,
    )
}

// ===========================================================================
// CircularSliderSize
// ===========================================================================

object CircularSliderSize {
    val Large = 64.dp
    val Medium = 48.dp
    val Small = 32.dp
}

// ===========================================================================
// CircularSlider
// ===========================================================================

/**
 * macOS-style circular slider / knob control.
 *
 * Displays a circular track with an accent-colored arc showing the current
 * value and a rotating tick indicator. The user can tap or drag anywhere on
 * the control to change the value. The tick indicator follows the proportions
 * of a standard macOS knob indicator.
 *
 * @param value Current value within [valueRange].
 * @param onValueChange Called when the value changes during interaction.
 * @param modifier Modifier applied to the control.
 * @param enabled Whether the control is interactive.
 * @param valueRange The range of valid values.
 * @param onValueChangeFinished Called once when the user finishes a gesture.
 * @param colors Color configuration via [CircularSliderDefaults.colors].
 * @param size Diameter of the control. See [CircularSliderSize] for presets.
 * @param trackWidth Width of the circular track stroke.
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
    size: Dp = CircularSliderSize.Medium,
    trackWidth: Dp = size * 3 / 32,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val min = valueRange.start
    val max = valueRange.endInclusive
    val fraction = ((value - min) / (max - min)).coerceIn(0f, 1f)

    var isDragging by remember { mutableStateOf(false) }

    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = if (isDragging) tween(0) else darwinSpring(DarwinSpringPreset.Snappy),
    )

    val isHovered by interactionSource.collectIsHoveredAsState()
    val scale by animateFloatAsState(
        targetValue = if (enabled && (isDragging || isHovered)) 1.05f else 1f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
    )

    val bgColor = if (enabled) colors.backgroundColor else colors.disabledBackgroundColor
    val trackColor = if (enabled) colors.trackColor else colors.disabledTrackColor
    val activeColor = if (enabled) colors.activeTrackColor else colors.disabledActiveTrackColor
    val indicatorColor = if (enabled) colors.indicatorColor else colors.disabledIndicatorColor

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

            // Track ring
            val trackStroke = Stroke(width = trackWidth.toPx(), cap = StrokeCap.Round)
            val arcDiameter = canvasSize.minDimension - trackStroke.width
            val arcSize = Size(arcDiameter, arcDiameter)
            val arcTopLeft = Offset(trackStroke.width / 2f, trackStroke.width / 2f)

            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = trackStroke,
            )

            // Active arc (from top, clockwise)
            val sweepAngle = animatedFraction * 360f
            if (sweepAngle > 0f) {
                drawArc(
                    color = activeColor,
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = arcTopLeft,
                    size = arcSize,
                    style = trackStroke,
                )
            }

            // Indicator tick — proportions from the SVG (2×6 in a 24×24 circle)
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
