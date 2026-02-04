package io.github.kdroidfilter.darwinui.components.slider

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import kotlin.math.round
import kotlin.math.roundToInt

/**
 * Size variants for the Darwin slider.
 *
 * Matches React Tailwind classes:
 * - sm: track h-1 (4px), thumb h-3 w-3 (12px)
 * - md: track h-2 (8px), thumb h-4 w-4 (16px)
 * - lg: track h-3 (12px), thumb h-5 w-5 (20px)
 */
enum class DarwinSliderSize(val trackHeight: Dp, val thumbSize: Dp) {
    Sm(trackHeight = 4.dp, thumbSize = 12.dp),
    Md(trackHeight = 8.dp, thumbSize = 16.dp),
    Lg(trackHeight = 12.dp, thumbSize = 20.dp),
}

private val Blue500 = Color(0xFF3B82F6)

/**
 * Darwin UI Slider — a range slider component mirroring the React darwin-ui Slider.
 *
 * Features a rounded track with a filled portion indicating current progress,
 * a draggable circular thumb with a blue ring, hover/tap scale animations,
 * and an optional value label shown below the track (right-aligned).
 *
 * @param value Current value of the slider.
 * @param onValueChange Callback invoked when the value changes during drag or tap.
 * @param onValueChangeFinished Callback invoked when the user finishes dragging.
 * @param min Minimum value of the slider range.
 * @param max Maximum value of the slider range.
 * @param step Step increment for snapping.
 * @param size Size variant controlling track height and thumb diameter.
 * @param showValue Whether to display the current value below the slider.
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
    val disabledAlpha = if (enabled) 1f else 0.5f

    // React: bg-black/10 dark:bg-white/10
    val trackColor = when {
        glass -> if (colors.isDark) Color(0xFF18181B).copy(alpha = 0.40f) else Color.White.copy(alpha = 0.40f)
        else -> if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)
    }

    // React: bg-blue-500
    val filledColor = Blue500

    // Snap value to step
    fun snapToStep(raw: Float): Float {
        val clamped = raw.coerceIn(min, max)
        if (step <= 0f) return clamped
        val steps = ((clamped - min) / step).roundToInt()
        return (min + steps * step).coerceIn(min, max)
    }

    val fraction = ((value - min) / (max - min)).coerceIn(0f, 1f)
    var isDragging by remember { mutableStateOf(false) }

    // Instant during drag, animated otherwise (React: duration 0 during drag, 0.1 otherwise)
    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = if (isDragging) tween(durationMillis = 0) else tween(durationMillis = 100),
    )

    val density = LocalDensity.current
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    val thumbSizePx = with(density) { size.thumbSize.toPx() }

    // Hover / press state for thumb scale
    val thumbInteractionSource = remember { MutableInteractionSource() }
    val isThumbHovered by thumbInteractionSource.collectIsHoveredAsState()

    // React: whileHover scale 1.1, whileTap scale 0.95, dragging scale 1.1
    val thumbScale by animateFloatAsState(
        targetValue = when {
            isDragging -> 1.1f
            isThumbHovered -> 1.1f
            else -> 1f
        },
        animationSpec = tween(durationMillis = 150),
    )

    fun valueFromPosition(x: Float): Float {
        val trackWidth = containerSize.width.toFloat()
        val rawFraction = (x / trackWidth).coerceIn(0f, 1f)
        return snapToStep(min + rawFraction * (max - min))
    }

    Column(modifier = modifier) {
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
                                    val newValue = valueFromPosition(change.position.x)
                                    onValueChange(newValue)
                                }
                            }
                    } else {
                        Modifier
                    }
                )
                .then(
                    if (!enabled) Modifier.graphicsLayer { alpha = 0.5f } else Modifier
                ),
            contentAlignment = Alignment.CenterStart,
        ) {
            // Track background + filled portion
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(size.trackHeight)
                    .align(Alignment.Center),
            ) {
                val cornerRadius = CornerRadius(this.size.height / 2f)

                // Background track
                drawRoundRect(
                    color = trackColor,
                    cornerRadius = cornerRadius,
                    size = this.size,
                )

                // Filled track
                val filledWidth = animatedFraction * this.size.width
                if (filledWidth > 0f) {
                    drawRoundRect(
                        color = filledColor,
                        cornerRadius = cornerRadius,
                        size = Size(filledWidth, this.size.height),
                    )
                }
            }

            // Thumb — React: white, shadow-md, ring-2 ring-blue-500/50
            val trackWidthPx = containerSize.width.toFloat() - thumbSizePx
            val thumbOffsetPx = animatedFraction * trackWidthPx
            val thumbOffsetDp = with(density) { thumbOffsetPx.toDp() }

            Box(
                modifier = Modifier
                    .offset(x = thumbOffsetDp)
                    .size(size.thumbSize)
                    .graphicsLayer {
                        scaleX = thumbScale
                        scaleY = thumbScale
                    }
                    .hoverable(thumbInteractionSource)
                    .shadow(
                        elevation = 4.dp,
                        shape = CircleShape,
                        clip = false,
                    )
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(
                        width = 2.dp,
                        color = Blue500.copy(alpha = 0.5f),
                        shape = CircleShape,
                    ),
            )
        }

        // Value display — React: mt-1 text-right text-xs text-zinc-500 dark:text-zinc-400
        if (showValue) {
            DarwinText(
                text = if (step >= 1f) value.roundToInt().toString()
                else "${(round(value * 10f) / 10f)}",
                style = DarwinTheme.typography.labelSmall,
                color = if (colors.isDark) Color(0xFFA1A1AA) else Color(0xFF71717A),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.End,
            )
        }
    }
}
