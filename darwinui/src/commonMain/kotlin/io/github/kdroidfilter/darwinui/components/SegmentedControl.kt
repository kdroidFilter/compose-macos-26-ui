package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalControlSize
import io.github.kdroidfilter.darwinui.theme.LocalDarwinContentColor
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle
import io.github.kdroidfilter.darwinui.theme.Zinc300
import io.github.kdroidfilter.darwinui.theme.Zinc600
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// =============================================================================
// SegmentedControlColors
// =============================================================================

@Immutable
class SegmentedControlColors(
    val trackColor: Color,
    val indicatorColor: Color,
    val indicatorShadowElevation: Dp,
    val dividerColor: Color,
    val contentColor: Color,
    val selectedContentColor: Color,
    val disabledTrackColor: Color,
    val disabledIndicatorColor: Color,
    val disabledContentColor: Color,
)

// =============================================================================
// SegmentedControlDefaults
// =============================================================================

object SegmentedControlDefaults {

    @Composable
    fun colors(
        trackColor: Color = if (DarwinTheme.colorScheme.isDark)
            Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.06f),
        indicatorColor: Color = if (DarwinTheme.colorScheme.isDark)
            Color.White.copy(alpha = 0.25f) else Color.White,
        indicatorShadowElevation: Dp = if (DarwinTheme.colorScheme.isDark) 0.dp else 1.dp,
        dividerColor: Color = if (DarwinTheme.colorScheme.isDark)
            Zinc600 else Zinc300,
        contentColor: Color = DarwinTheme.colorScheme.onSurface,
        selectedContentColor: Color = DarwinTheme.colorScheme.onSurface,
        disabledTrackColor: Color = trackColor.copy(alpha = trackColor.alpha * 0.5f),
        disabledIndicatorColor: Color = indicatorColor.copy(alpha = indicatorColor.alpha * 0.5f),
        disabledContentColor: Color = contentColor.copy(alpha = 0.5f),
    ) = SegmentedControlColors(
        trackColor = trackColor,
        indicatorColor = indicatorColor,
        indicatorShadowElevation = indicatorShadowElevation,
        dividerColor = dividerColor,
        contentColor = contentColor,
        selectedContentColor = selectedContentColor,
        disabledTrackColor = disabledTrackColor,
        disabledIndicatorColor = disabledIndicatorColor,
        disabledContentColor = disabledContentColor,
    )
}

// =============================================================================
// SegmentedControl — slot-based API
// =============================================================================

/**
 * macOS-style segmented control with a sliding pill indicator.
 *
 * A single capsule track with equal-width segments. The selected segment
 * is indicated by an elevated white pill that slides between positions
 * with a spring animation.
 *
 * @param segmentCount Total number of segments.
 * @param selectedIndex Index of the currently selected segment.
 * @param onSelectedIndexChange Callback when a segment is tapped.
 * @param modifier Modifier for the outer track.
 * @param enabled Whether the control accepts interaction.
 * @param colors Color configuration.
 * @param segment Content composable for each segment by index.
 */
@Composable
fun SegmentedControl(
    segmentCount: Int,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SegmentedControlColors = SegmentedControlDefaults.colors(),
    segment: @Composable (index: Int) -> Unit,
) {
    require(segmentCount > 0) { "segmentCount must be > 0" }

    val controlSize = LocalControlSize.current
    val metrics = DarwinTheme.componentStyling.segmentedControl.metrics
    val density = LocalDensity.current
    val isDark = DarwinTheme.colorScheme.isDark
    val trackShape = RoundedCornerShape(50)
    val pillShape = RoundedCornerShape(50)

    val trackColor = if (enabled) colors.trackColor else colors.disabledTrackColor
    val indicatorColor = if (enabled) colors.indicatorColor else colors.disabledIndicatorColor
    val textColor = if (enabled) colors.contentColor else colors.disabledContentColor
    val selectedTextColor = if (enabled) colors.selectedContentColor else colors.disabledContentColor

    // Segment measurements
    val segmentOffsets = remember { FloatArray(segmentCount) }
    val segmentWidths = remember { FloatArray(segmentCount) }
    var measurementReady by remember { mutableStateOf(false) }

    // Animated indicator
    val indicatorOffsetAnim = remember { Animatable(0f) }
    val indicatorWidthAnim = remember { Animatable(0f) }
    var hasInitialized by remember { mutableStateOf(false) }
    val springSpec = darwinSpring<Float>(DarwinSpringPreset.Snappy)

    LaunchedEffect(selectedIndex, measurementReady) {
        if (!measurementReady) return@LaunchedEffect
        val targetOffset = segmentOffsets.getOrElse(selectedIndex) { 0f }
        val targetWidth = segmentWidths.getOrElse(selectedIndex) { 0f }
        if (targetWidth > 0f) {
            if (!hasInitialized) {
                indicatorOffsetAnim.snapTo(targetOffset)
                indicatorWidthAnim.snapTo(targetWidth)
                hasInitialized = true
            } else {
                launch { indicatorOffsetAnim.animateTo(targetOffset, springSpec) }
                launch { indicatorWidthAnim.animateTo(targetWidth, springSpec) }
            }
        }
    }

    val trackHeight = metrics.containerHeightFor(controlSize)
    val trackPadding = metrics.trackPadding
    val indicatorHeight = trackHeight - trackPadding * 2

    // Track
    Box(
        modifier = modifier
            .height(trackHeight)
            .alpha(if (enabled) 1f else 0.6f)
            .clip(trackShape)
            .background(trackColor, trackShape)
            .padding(trackPadding),
        contentAlignment = Alignment.CenterStart,
    ) {
        // Sliding pill indicator
        if (indicatorWidthAnim.value > 0f) {
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = indicatorOffsetAnim.value.roundToInt(),
                            y = 0,
                        )
                    }
                    .size(
                        width = with(density) { indicatorWidthAnim.value.toDp() },
                        height = indicatorHeight,
                    )
                    .shadow(colors.indicatorShadowElevation, pillShape, clip = false)
                    .background(indicatorColor, pillShape),
            )
        }

        // Segments row
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for (index in 0 until segmentCount) {
                // Divider before this segment (not before first)
                if (index > 0) {
                    SegmentDivider(
                        visible = index != selectedIndex && index - 1 != selectedIndex,
                        color = colors.dividerColor,
                        height = indicatorHeight * 0.5f,
                    )
                }

                Segment(
                    index = index,
                    isSelected = index == selectedIndex,
                    enabled = enabled,
                    isDark = isDark,
                    textColor = if (index == selectedIndex) selectedTextColor else textColor,
                    controlSize = controlSize,
                    onMeasured = { idx, offset, width ->
                        segmentOffsets[idx] = offset
                        segmentWidths[idx] = width
                        if (idx == segmentCount - 1) {
                            measurementReady = true
                        }
                    },
                    onClick = { onSelectedIndexChange(index) },
                    content = { segment(index) },
                )
            }
        }
    }
}

// =============================================================================
// SegmentedControl — convenience text-only API
// =============================================================================

/**
 * macOS-style segmented control with string labels.
 *
 * Convenience overload that accepts a list of text labels.
 *
 * @param options List of segment label strings.
 * @param selectedIndex Index of the currently selected segment.
 * @param onSelectedIndexChange Callback when a segment is tapped.
 * @param modifier Modifier for the outer track.
 * @param enabled Whether the control accepts interaction.
 * @param colors Color configuration.
 */
@Composable
fun SegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: SegmentedControlColors = SegmentedControlDefaults.colors(),
) {
    SegmentedControl(
        segmentCount = options.size,
        selectedIndex = selectedIndex,
        onSelectedIndexChange = onSelectedIndexChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
    ) { index ->
        Text(options[index])
    }
}

// =============================================================================
// Internal: Segment
// =============================================================================

@Composable
private fun RowScope.Segment(
    index: Int,
    isSelected: Boolean,
    enabled: Boolean,
    isDark: Boolean,
    textColor: Color,
    controlSize: ControlSize,
    onMeasured: (index: Int, offset: Float, width: Float) -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    val metrics = DarwinTheme.componentStyling.segmentedControl.metrics
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    // Subtle hover overlay on unselected segments only
    val hoverAlpha by animateFloatAsState(
        targetValue = when {
            !enabled || isSelected || !isHovered -> 0f
            isDark -> 0.04f
            else -> 0.03f
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "segmentHover",
    )

    // Subtle press scale on unselected segments
    val pressAlpha by animateFloatAsState(
        targetValue = when {
            !enabled || isSelected || !isPressed -> 0f
            isDark -> 0.06f
            else -> 0.04f
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "segmentPress",
    )

    val textStyle = DarwinTheme.typography.caption1
        .merge(TextStyle(color = textColor, fontWeight = FontWeight.Medium))

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .onGloballyPositioned { coordinates ->
                onMeasured(
                    index,
                    coordinates.positionInParent().x,
                    coordinates.size.width.toFloat(),
                )
            }
            .clip(RoundedCornerShape(50))
            .then(
                if (hoverAlpha > 0f || pressAlpha > 0f) {
                    val overlayColor = if (isDark) Color.White else Color.Black
                    Modifier
                        .background(overlayColor.copy(alpha = hoverAlpha))
                        .background(overlayColor.copy(alpha = pressAlpha))
                } else {
                    Modifier
                }
            )
            .hoverable(interactionSource, enabled)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(
                horizontal = metrics.segmentHorizontalPaddingFor(controlSize),
                vertical = metrics.segmentVerticalPaddingFor(controlSize),
            ),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(
            LocalDarwinContentColor provides textColor,
            LocalDarwinTextStyle provides textStyle,
        ) {
            content()
        }
    }
}

// =============================================================================
// Internal: Segment divider
// =============================================================================

@Composable
private fun SegmentDivider(
    visible: Boolean,
    color: Color,
    height: Dp,
) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "dividerAlpha",
    )
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(height)
            .alpha(alpha)
            .background(color),
    )
}

// =============================================================================
// Preview
// =============================================================================

@Preview
@Composable
private fun SegmentedControlPreview() {
    DarwinTheme {
        var selected by remember { mutableStateOf(0) }
        SegmentedControl(
            options = listOf("Day", "Week", "Month"),
            selectedIndex = selected,
            onSelectedIndexChange = { selected = it },
        )
    }
}
