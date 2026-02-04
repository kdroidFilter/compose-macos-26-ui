package io.github.kdroidfilter.darwinui.components.slider

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.theme.*
import kotlin.math.roundToInt
import kotlin.math.round

/**
 * Size variants for the Darwin slider.
 *
 * @property trackHeight Height of the slider track.
 * @property thumbSize Diameter of the slider thumb.
 */
enum class DarwinSliderSize(val trackHeight: Dp, val thumbSize: Dp) {
    Sm(trackHeight = 4.dp, thumbSize = 14.dp),
    Md(trackHeight = 6.dp, thumbSize = 18.dp),
    Lg(trackHeight = 8.dp, thumbSize = 22.dp),
}

/**
 * Darwin UI Slider -- a range slider component mirroring the React darwin-ui Slider.
 *
 * Features a rounded track with a filled portion indicating current progress,
 * a draggable circular thumb, and an optional value label shown above the thumb.
 *
 * @param value Current value of the slider.
 * @param onValueChange Callback invoked when the value changes during drag or tap.
 * @param onValueChangeFinished Callback invoked when the user finishes dragging.
 * @param min Minimum value of the slider range.
 * @param max Maximum value of the slider range.
 * @param step Step increment for snapping.
 * @param size Size variant controlling track height and thumb diameter.
 * @param showValue Whether to display the current value above the thumb.
 * @param enabled Whether the slider is interactive.
 * @param glass Whether to use semi-transparent glass styling on the track.
 * @param modifier Modifier applied to the root layout.
 */
@Composable
fun DarwinSlider(
    value: Float = 0f,
    onValueChange: (Float) -> Unit,
    onValueChangeFinished: (() -> Unit)? = null,
    min: Float = 0f,
    max: Float = 100f,
    step: Float = 1f,
    size: DarwinSliderSize = DarwinSliderSize.Md,
    showValue: Boolean = false,
    enabled: Boolean = true,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors

    val trackColor = when {
        glass -> colors.glassBackground
        colors.isDark -> Zinc700
        else -> Zinc300
    }
    val filledColor = colors.accent
    val thumbColor = Color.White
    val disabledAlpha = if (enabled) 1f else 0.5f

    // Snap value to step
    fun snapToStep(raw: Float): Float {
        val clamped = raw.coerceIn(min, max)
        if (step <= 0f) return clamped
        val steps = ((clamped - min) / step).roundToInt()
        return (min + steps * step).coerceIn(min, max)
    }

    // Animated fraction for smooth thumb movement
    val fraction = ((value - min) / (max - min)).coerceIn(0f, 1f)
    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
    )

    val density = LocalDensity.current
    var containerSize by remember { mutableStateOf(IntSize.Zero) }

    val thumbSizePx = with(density) { size.thumbSize.toPx() }

    // Calculate value from an x position within the track area
    fun valueFromPosition(x: Float): Float {
        val trackWidth = containerSize.width.toFloat() - thumbSizePx
        val rawFraction = ((x - thumbSizePx / 2f) / trackWidth).coerceIn(0f, 1f)
        return snapToStep(min + rawFraction * (max - min))
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Value label above thumb
        if (showValue) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth(),
            ) {
                val labelOffset = with(density) {
                    val trackWidth = containerSize.width.toFloat() - thumbSizePx
                    val thumbCenter = thumbSizePx / 2f + animatedFraction * trackWidth
                    thumbCenter.toDp()
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopStart,
                ) {
                    Box(
                        modifier = Modifier
                            .offset(x = labelOffset - 16.dp)
                            .width(32.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        DarwinText(
                            text = if (step >= 1f) value.roundToInt().toString()
                            else "${(round(value * 10f) / 10f)}",
                            style = DarwinTheme.typography.labelSmall,
                            color = colors.textSecondary.copy(alpha = disabledAlpha),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        // Slider track + thumb
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(size.thumbSize)
                .onSizeChanged { containerSize = it }
                .then(
                    if (enabled) {
                        Modifier
                            .pointerInput(min, max, step) {
                                detectTapGestures { offset ->
                                    val newValue = valueFromPosition(offset.x)
                                    onValueChange(newValue)
                                    onValueChangeFinished?.invoke()
                                }
                            }
                            .pointerInput(min, max, step) {
                                detectDragGestures(
                                    onDragEnd = { onValueChangeFinished?.invoke() },
                                    onDragCancel = { onValueChangeFinished?.invoke() },
                                ) { change, _ ->
                                    change.consume()
                                    val newValue = valueFromPosition(change.position.x)
                                    onValueChange(newValue)
                                }
                            }
                    } else {
                        Modifier
                    }
                ),
            contentAlignment = Alignment.CenterStart,
        ) {
            // Track background + filled portion drawn with Canvas
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(size.trackHeight)
                    .align(Alignment.Center),
            ) {
                val cornerRadius = CornerRadius(this.size.height / 2f)

                // Background track
                drawRoundRect(
                    color = trackColor.copy(alpha = trackColor.alpha * disabledAlpha),
                    cornerRadius = cornerRadius,
                    size = this.size,
                )

                // Filled track
                val filledWidth = animatedFraction * this.size.width
                if (filledWidth > 0f) {
                    drawRoundRect(
                        color = filledColor.copy(alpha = disabledAlpha),
                        cornerRadius = cornerRadius,
                        size = Size(filledWidth, this.size.height),
                    )
                }
            }

            // Thumb
            val trackWidthPx = containerSize.width.toFloat() - thumbSizePx
            val thumbOffsetPx = animatedFraction * trackWidthPx
            val thumbOffsetDp = with(density) { thumbOffsetPx.toDp() }

            Box(
                modifier = Modifier
                    .offset(x = thumbOffsetDp)
                    .size(size.thumbSize)
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape,
                        clip = false,
                    )
                    .clip(CircleShape)
                    .background(thumbColor.copy(alpha = disabledAlpha)),
            )
        }
    }
}
