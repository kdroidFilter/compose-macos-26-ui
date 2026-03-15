package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.github.fletchmckee.liquid.LiquidState
import io.github.fletchmckee.liquid.liquefiable
import io.github.fletchmckee.liquid.liquid
import io.github.fletchmckee.liquid.rememberLiquidState
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.SpringPreset
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalLiquidState
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosSpring
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
        thumbColor: Color = MacosTheme.componentStyling.slider.colors.thumb,
        activeTrackColor: Color = MacosTheme.componentStyling.slider.colors.activeTrack,
        activeTickColor: Color = activeTrackColor.copy(alpha = 0.5f),
        inactiveTrackColor: Color = MacosTheme.componentStyling.slider.colors.inactiveTrack,
        inactiveTickColor: Color = inactiveTrackColor.copy(alpha = 0.5f),
        disabledThumbColor: Color = MacosTheme.componentStyling.slider.colors.thumbDisabled,
        disabledActiveTrackColor: Color = MacosTheme.componentStyling.slider.colors.disabledActiveTrack,
        disabledActiveTickColor: Color = activeTickColor.copy(0.38f),
        disabledInactiveTrackColor: Color = MacosTheme.componentStyling.slider.colors.disabledInactiveTrack,
        disabledInactiveTickColor: Color = inactiveTickColor.copy(0.38f),
    ) = SliderColors(thumbColor, activeTrackColor, activeTickColor, inactiveTrackColor, inactiveTickColor, disabledThumbColor, disabledActiveTrackColor, disabledActiveTickColor, disabledInactiveTrackColor, disabledInactiveTickColor)
}

// ===========================================================================
// Slider — M3-compatible
// ===========================================================================

/**
 * Applies pure liquid refraction to the slider thumb.
 * Uses a dedicated [LiquidState] whose liquefiable scope covers only the
 * track — the thumb itself is outside that scope, preventing recursive capture.
 */
@Composable
private fun Modifier.sliderThumbGlass(
    shape: RoundedCornerShape,
    liquidState: LiquidState?,
): Modifier {
    return if (liquidState != null) {
        this.liquid(liquidState) {
            this.shape = shape
            this.tint = Color.Transparent
            this.frost = 0.dp
            refraction = 0.15f
            curve = 0.8f
            edge = 0.3f
            saturation = 1.0f
            contrast = 1.0f
        }
    } else {
        this.clip(shape).background(Color.Transparent, shape)
    }
}

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
    // macOS extensions
    showValue: Boolean = false,
) {
    val controlSize = LocalControlSize.current
    val metrics = MacosTheme.componentStyling.slider.metrics
    val thumbShape = RoundedCornerShape(metrics.thumbCornerRadiusFor(controlSize))

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
    var isClicked by remember { mutableStateOf(false) }
    var isDragging by remember { mutableStateOf(false) }
    val isActive = isClicked || isDragging

    val animatedFraction by animateFloatAsState(
        targetValue = fraction,
        animationSpec = if (isActive) tween(0) else macosSpring(SpringPreset.Snappy),
    )

    val density = LocalDensity.current
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    val thumbWidthPx = with(density) { metrics.thumbWidthFor(controlSize).toPx() }

    val isHovered by interactionSource.collectIsHoveredAsState()
    val thumbScale by animateFloatAsState(
        targetValue = if (isActive || isHovered) 1.1f else 1f,
        animationSpec = macosSpring(SpringPreset.Snappy),
    )

    fun valueFromPosition(x: Float): Float {
        val rawFraction = (x / containerSize.width.toFloat()).coerceIn(0f, 1f)
        return snapToStep(min + rawFraction * (max - min))
    }

    val trackColor = if (enabled) colors.inactiveTrackColor else colors.disabledInactiveTrackColor
    val fillColor = if (enabled) colors.activeTrackColor else colors.disabledActiveTrackColor

    val thumbColor = if (enabled) colors.thumbColor else colors.disabledThumbColor

    // Dedicated liquid state: liquefiable wraps only the track,
    // thumb is a sibling outside it — no recursive capture.
    val parentLiquidState = LocalLiquidState.current
    val sliderLiquidState = if (parentLiquidState != null) rememberLiquidState() else null

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(metrics.totalHeightFor(controlSize))
                .onSizeChanged { containerSize = it }
                .then(if (enabled) Modifier
                    .pointerInput(min, max, steps) {
                        detectTapGestures(
                            onPress = {
                                isClicked = true
                                tryAwaitRelease()
                                isClicked = false
                            },
                        ) { offset ->
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
            // Track layer — wrapped in liquefiable so the thumb can sample it
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .then(if (sliderLiquidState != null) Modifier.liquefiable(sliderLiquidState) else Modifier),
            ) {
                if (track != null) {
                    track(animatedFraction)
                } else {
                    Canvas(modifier = Modifier.fillMaxWidth().height(metrics.trackHeightFor(controlSize)).align(Alignment.Center)) {
                        val cornerRadius = CornerRadius(this.size.height / 2f)
                        drawRoundRect(color = trackColor, cornerRadius = cornerRadius, size = this.size)
                        val fillWidth = animatedFraction * this.size.width
                        if (fillWidth > 0f) {
                            drawRoundRect(color = fillColor, cornerRadius = cornerRadius, size = Size(fillWidth, this.size.height))
                        }
                    }
                }
            }

            // Thumb — outside the liquefiable scope, uses liquid() to sample the track
            val trackWidthPx = containerSize.width.toFloat() - thumbWidthPx
            val thumbOffsetPx = animatedFraction * trackWidthPx
            val thumbOffsetDp = with(density) { thumbOffsetPx.toDp() }

            if (thumb != null) {
                Box(modifier = Modifier.offset(x = thumbOffsetDp)) { thumb(value) }
            } else {
                Box(
                    modifier = Modifier
                        .offset(x = thumbOffsetDp)
                        .size(width = metrics.thumbWidthFor(controlSize), height = metrics.thumbHeightFor(controlSize))
                        .graphicsLayer { scaleX = thumbScale; scaleY = thumbScale }
                        .hoverable(interactionSource)
                        .shadow(1.dp, thumbShape, clip = false)
                        .then(
                            if (isActive && enabled) {
                                Modifier.sliderThumbGlass(thumbShape, sliderLiquidState)
                            } else {
                                Modifier
                                    .clip(thumbShape)
                                    .background(thumbColor)
                            },
                        ),
                )
            }
        }

        if (showValue) {
            Text(
                text = if (steps > 0) value.roundToInt().toString() else "${round(value * 10f) / 10f}",
                style = MacosTheme.typography.caption2,
                color = if (MacosTheme.colorScheme.isDark) Color(0xFFA1A1AA) else Color(0xFF71717A),
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
) {
    val controlSize = LocalControlSize.current
    val metrics = MacosTheme.componentStyling.slider.metrics
    val thumbShape = RoundedCornerShape(metrics.thumbCornerRadiusFor(controlSize))

    val min = valueRange.start
    val max = valueRange.endInclusive

    val startFraction = ((value.start - min) / (max - min)).coerceIn(0f, 1f)
    val endFraction = ((value.endInclusive - min) / (max - min)).coerceIn(0f, 1f)

    var isPressedStart by remember { mutableStateOf(false) }
    var isPressedEnd by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    val thumbWidthPx = with(density) { metrics.thumbWidthFor(controlSize).toPx() }

    fun valueFromFraction(f: Float): Float = (min + f * (max - min)).coerceIn(min, max)
    fun fractionFromX(x: Float) = (x / containerSize.width.toFloat()).coerceIn(0f, 1f)

    val trackColor = if (enabled) colors.inactiveTrackColor else colors.disabledInactiveTrackColor
    val fillColor = if (enabled) colors.activeTrackColor else colors.disabledActiveTrackColor
    val thumbColor = if (enabled) colors.thumbColor else colors.disabledThumbColor

    val parentLiquidState = LocalLiquidState.current
    val sliderLiquidState = if (parentLiquidState != null) rememberLiquidState() else null

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(metrics.totalHeightFor(controlSize))
            .onSizeChanged { containerSize = it }
            .then(if (!enabled) Modifier.graphicsLayer { alpha = 0.5f } else Modifier),
        contentAlignment = Alignment.CenterStart,
    ) {
        // Track layer — inside liquefiable scope for thumb glass sampling
        Box(
            modifier = Modifier
                .matchParentSize()
                .then(if (sliderLiquidState != null) Modifier.liquefiable(sliderLiquidState) else Modifier),
        ) {
            if (track != null) {
                track(value)
            } else {
                Canvas(modifier = Modifier.fillMaxWidth().height(metrics.trackHeightFor(controlSize)).align(Alignment.Center)) {
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
        }

        // Thumbs — outside liquefiable scope
        val trackWidthPx = containerSize.width.toFloat() - thumbWidthPx

        // Start thumb
        val startOffsetDp = with(density) { (startFraction * trackWidthPx).toDp() }
        if (startThumb != null) {
            Box(modifier = Modifier.offset(x = startOffsetDp)
                .then(if (enabled) Modifier.pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { isPressedStart = true },
                        onDragEnd = { isPressedStart = false; onValueChangeFinished?.invoke() },
                        onDragCancel = { isPressedStart = false },
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
                    .size(width = metrics.thumbWidthFor(controlSize), height = metrics.thumbHeightFor(controlSize))
                    .shadow(1.dp, thumbShape, clip = false)
                    .then(
                        if (isPressedStart && enabled) {
                            Modifier.sliderThumbGlass(thumbShape, sliderLiquidState)
                        } else {
                            Modifier.clip(thumbShape).background(thumbColor)
                        },
                    ),
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
                    .size(width = metrics.thumbWidthFor(controlSize), height = metrics.thumbHeightFor(controlSize))
                    .shadow(1.dp, thumbShape, clip = false)
                    .then(
                        if (isPressedEnd && enabled) {
                            Modifier.sliderThumbGlass(thumbShape, sliderLiquidState)
                        } else {
                            Modifier.clip(thumbShape).background(thumbColor)
                        },
                    ),
            )
        }
    }
}

@Preview
@Composable
private fun SliderPreview() {
    MacosTheme {
        Slider(value = 0.5f, onValueChange = {})
    }
}
