package io.github.kdroidfilter.darwinui.components

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.Blue500
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import kotlin.math.round
import kotlin.math.roundToInt

// ===========================================================================
// SliderColors — mirrors M3's SliderColors
// ===========================================================================

@Immutable
class SliderColors(
    val thumbColor: Color,
    val activeTrackColor: Color,
    val activeTickColor: Color,
    val inactiveTrackColor: Color,
    val inactiveTickColor: Color,
    val disabledThumbColor: Color,
    val disabledActiveTrackColor: Color,
    val disabledActiveTickColor: Color,
    val disabledInactiveTrackColor: Color,
    val disabledInactiveTickColor: Color,
) {
    fun copy(
        thumbColor: Color = this.thumbColor,
        activeTrackColor: Color = this.activeTrackColor,
        activeTickColor: Color = this.activeTickColor,
        inactiveTrackColor: Color = this.inactiveTrackColor,
        inactiveTickColor: Color = this.inactiveTickColor,
        disabledThumbColor: Color = this.disabledThumbColor,
        disabledActiveTrackColor: Color = this.disabledActiveTrackColor,
        disabledActiveTickColor: Color = this.disabledActiveTickColor,
        disabledInactiveTrackColor: Color = this.disabledInactiveTrackColor,
        disabledInactiveTickColor: Color = this.disabledInactiveTickColor,
    ) = SliderColors(thumbColor, activeTrackColor, activeTickColor, inactiveTrackColor, inactiveTickColor, disabledThumbColor, disabledActiveTrackColor, disabledActiveTickColor, disabledInactiveTrackColor, disabledInactiveTickColor)
}

// ===========================================================================
// SliderDefaults — mirrors M3's SliderDefaults
// ===========================================================================

object SliderDefaults {
    @Composable
    fun colors(
        thumbColor: Color = DarwinTheme.colorScheme.accent,
        activeTrackColor: Color = DarwinTheme.colorScheme.accent,
        activeTickColor: Color = DarwinTheme.colorScheme.accent.copy(alpha = 0.5f),
        inactiveTrackColor: Color = if (DarwinTheme.colorScheme.isDark) Color.White.copy(0.10f) else Color.Black.copy(0.10f),
        inactiveTickColor: Color = inactiveTrackColor.copy(alpha = 0.5f),
        disabledThumbColor: Color = thumbColor.copy(0.38f),
        disabledActiveTrackColor: Color = activeTrackColor.copy(0.38f),
        disabledActiveTickColor: Color = activeTickColor.copy(0.38f),
        disabledInactiveTrackColor: Color = inactiveTrackColor.copy(0.38f),
        disabledInactiveTickColor: Color = inactiveTickColor.copy(0.38f),
    ) = SliderColors(thumbColor, activeTrackColor, activeTickColor, inactiveTrackColor, inactiveTickColor, disabledThumbColor, disabledActiveTrackColor, disabledActiveTickColor, disabledInactiveTrackColor, disabledInactiveTickColor)
}

// ===========================================================================
// SliderSize — Darwin extension (not in M3)
// ===========================================================================

enum class SliderSize(val trackHeight: Dp, val thumbSize: Dp) {
    Sm(trackHeight = 4.dp, thumbSize = 12.dp),
    Md(trackHeight = 8.dp, thumbSize = 16.dp),
    Lg(trackHeight = 12.dp, thumbSize = 20.dp),
}

// ===========================================================================
// Slider — M3-compatible
// ===========================================================================

@Composable
fun Slider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    colors: SliderColors = SliderDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    thumb: @Composable ((Float) -> Unit)? = null,
    track: @Composable ((Float) -> Unit)? = null,
    // Darwin extensions
    sliderSize: SliderSize = SliderSize.Md,
    showValue: Boolean = false,
) {
    val min = valueRange.start
    val max = valueRange.endInclusive

    fun snapToStep(raw: Float): Float {
        val clamped = raw.coerceIn(min, max)
        if (steps <= 0) return clamped
        val stepSize = (max - min) / (steps + 1)
        val stepIndex = ((clamped - min) / stepSize).roundToInt()
        return (min + stepIndex * stepSize).coerceIn(min, max)
    }

    val fraction = ((value - min) / (max - min)).coerceIn(0f, 1f)
    var isDragging by remember { mutableStateOf(false) }

    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = if (isDragging) tween(0) else tween(100),
    )

    val density = LocalDensity.current
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    val thumbSizePx = with(density) { sliderSize.thumbSize.toPx() }

    val isHovered by interactionSource.collectIsHoveredAsState()
    val thumbScale by animateFloatAsState(
        targetValue = if (isDragging || isHovered) 1.1f else 1f,
        animationSpec = tween(150),
    )

    fun valueFromPosition(x: Float): Float {
        val rawFraction = (x / containerSize.width.toFloat()).coerceIn(0f, 1f)
        return snapToStep(min + rawFraction * (max - min))
    }

    val trackColor = if (enabled) colors.inactiveTrackColor else colors.disabledInactiveTrackColor
    val fillColor = if (enabled) colors.activeTrackColor else colors.disabledActiveTrackColor
    val thumbColor = if (enabled) colors.thumbColor else colors.disabledThumbColor

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(sliderSize.thumbSize)
                .onSizeChanged { containerSize = it }
                .then(if (enabled) Modifier
                    .pointerInput(min, max, steps) {
                        detectTapGestures { offset ->
                            onValueChange(valueFromPosition(offset.x))
                            onValueChangeFinished?.invoke()
                        }
                    }
                    .pointerInput(min, max, steps) {
                        detectDragGestures(
                            onDragStart = { isDragging = true },
                            onDragEnd = { isDragging = false; onValueChangeFinished?.invoke() },
                            onDragCancel = { isDragging = false; onValueChangeFinished?.invoke() },
                        ) { change, _ ->
                            change.consume()
                            onValueChange(valueFromPosition(change.position.x))
                        }
                    } else Modifier)
                .then(if (!enabled) Modifier.graphicsLayer { alpha = 0.5f } else Modifier),
            contentAlignment = Alignment.CenterStart,
        ) {
            if (track != null) {
                track(animatedFraction)
            } else {
                Canvas(modifier = Modifier.fillMaxWidth().height(sliderSize.trackHeight).align(Alignment.Center)) {
                    val cornerRadius = CornerRadius(this.size.height / 2f)
                    drawRoundRect(color = trackColor, cornerRadius = cornerRadius, size = this.size)
                    val fillWidth = animatedFraction * this.size.width
                    if (fillWidth > 0f) {
                        drawRoundRect(color = fillColor, cornerRadius = cornerRadius, size = Size(fillWidth, this.size.height))
                    }
                }
            }

            val trackWidthPx = containerSize.width.toFloat() - thumbSizePx
            val thumbOffsetPx = animatedFraction * trackWidthPx
            val thumbOffsetDp = with(density) { thumbOffsetPx.toDp() }

            if (thumb != null) {
                Box(modifier = Modifier.offset(x = thumbOffsetDp)) { thumb(value) }
            } else {
                Box(
                    modifier = Modifier
                        .offset(x = thumbOffsetDp)
                        .size(sliderSize.thumbSize)
                        .graphicsLayer { scaleX = thumbScale; scaleY = thumbScale }
                        .hoverable(interactionSource)
                        .shadow(4.dp, CircleShape, clip = false)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(2.dp, thumbColor.copy(0.5f), CircleShape),
                )
            }
        }

        if (showValue) {
            Text(
                text = if (steps > 0) value.roundToInt().toString() else "${round(value * 10f) / 10f}",
                style = DarwinTheme.typography.labelSmall,
                color = if (DarwinTheme.colorScheme.isDark) Color(0xFFA1A1AA) else Color(0xFF71717A),
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.End,
            )
        }
    }
}

// ===========================================================================
// RangeSlider — mirrors M3's RangeSlider
// ===========================================================================

@Composable
fun RangeSlider(
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    colors: SliderColors = SliderDefaults.colors(),
    startInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    endInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    startThumb: @Composable ((ClosedFloatingPointRange<Float>) -> Unit)? = null,
    endThumb: @Composable ((ClosedFloatingPointRange<Float>) -> Unit)? = null,
    track: @Composable ((ClosedFloatingPointRange<Float>) -> Unit)? = null,
    // Darwin extension
    sliderSize: SliderSize = SliderSize.Md,
) {
    val min = valueRange.start
    val max = valueRange.endInclusive

    val startFraction = ((value.start - min) / (max - min)).coerceIn(0f, 1f)
    val endFraction = ((value.endInclusive - min) / (max - min)).coerceIn(0f, 1f)

    var isDraggingStart by remember { mutableStateOf(false) }
    var isDraggingEnd by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    val thumbSizePx = with(density) { sliderSize.thumbSize.toPx() }

    fun valueFromFraction(f: Float): Float = (min + f * (max - min)).coerceIn(min, max)
    fun fractionFromX(x: Float) = (x / containerSize.width.toFloat()).coerceIn(0f, 1f)

    val trackColor = if (enabled) colors.inactiveTrackColor else colors.disabledInactiveTrackColor
    val fillColor = if (enabled) colors.activeTrackColor else colors.disabledActiveTrackColor
    val thumbColor = if (enabled) colors.thumbColor else colors.disabledThumbColor

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(sliderSize.thumbSize)
            .onSizeChanged { containerSize = it }
            .then(if (!enabled) Modifier.graphicsLayer { alpha = 0.5f } else Modifier),
        contentAlignment = Alignment.CenterStart,
    ) {
        if (track != null) {
            track(value)
        } else {
            Canvas(modifier = Modifier.fillMaxWidth().height(sliderSize.trackHeight).align(Alignment.Center)) {
                val cornerRadius = CornerRadius(this.size.height / 2f)
                drawRoundRect(color = trackColor, cornerRadius = cornerRadius, size = this.size)
                val startX = startFraction * this.size.width
                val fillWidth = (endFraction - startFraction) * this.size.width
                if (fillWidth > 0f) {
                    drawRoundRect(
                        color = fillColor,
                        topLeft = androidx.compose.ui.geometry.Offset(startX, 0f),
                        size = Size(fillWidth, this.size.height),
                        cornerRadius = cornerRadius,
                    )
                }
            }
        }

        val trackWidthPx = containerSize.width.toFloat() - thumbSizePx

        // Start thumb
        val startOffsetDp = with(density) { (startFraction * trackWidthPx).toDp() }
        if (startThumb != null) {
            Box(modifier = Modifier.offset(x = startOffsetDp)
                .then(if (enabled) Modifier.pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { isDraggingStart = true },
                        onDragEnd = { isDraggingStart = false; onValueChangeFinished?.invoke() },
                        onDragCancel = { isDraggingStart = false },
                    ) { change, _ ->
                        change.consume()
                        val newStart = valueFromFraction(fractionFromX(change.position.x + startOffsetDp.value * density.density)).coerceAtMost(value.endInclusive)
                        onValueChange(newStart..value.endInclusive)
                    }
                } else Modifier)
            ) { startThumb(value) }
        } else {
            Box(
                modifier = Modifier
                    .offset(x = startOffsetDp)
                    .size(sliderSize.thumbSize)
                    .shadow(4.dp, CircleShape, clip = false)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, thumbColor.copy(0.5f), CircleShape),
            )
        }

        // End thumb
        val endOffsetDp = with(density) { (endFraction * trackWidthPx).toDp() }
        if (endThumb != null) {
            Box(modifier = Modifier.offset(x = endOffsetDp)) { endThumb(value) }
        } else {
            Box(
                modifier = Modifier
                    .offset(x = endOffsetDp)
                    .size(sliderSize.thumbSize)
                    .shadow(4.dp, CircleShape, clip = false)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, thumbColor.copy(0.5f), CircleShape),
            )
        }
    }
}

@Preview
@Composable
private fun SliderPreview() {
    DarwinTheme {
        Slider(value = 0.5f, onValueChange = {})
    }
}
