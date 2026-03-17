package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.hoverable
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ScrollbarStyle
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// ============================================================
// TrackClickBehavior — what happens on a click outside the thumb
// Meaningful on desktop/web; set to None on mobile touch surfaces.
// ============================================================

enum class TrackClickBehavior {
    /** Do nothing on track click (default on mobile). */
    None,
    /** Scroll one viewport toward the click position (macOS default). */
    Jump,
    /** Seek directly to the scroll position corresponding to the click. */
    Seek,
}

// ============================================================
// ScrollbarState — abstraction over concrete scroll states
// ============================================================

interface ScrollbarState {
    val scrollOffsetPx: Float
    val maxScrollPx: Float
    val isScrollInProgress: Boolean
    suspend fun scrollTo(px: Float)
}

private class ScrollStateAdapter(private val s: ScrollState) : ScrollbarState {
    override val scrollOffsetPx get() = s.value.toFloat()
    override val maxScrollPx get() = s.maxValue.toFloat()
    override val isScrollInProgress get() = s.isScrollInProgress
    override suspend fun scrollTo(px: Float) { s.scrollTo(px.roundToInt()) }
}

private class LazyListStateAdapter(private val s: LazyListState) : ScrollbarState {

    private val avgItemSizePx: Int
        get() {
            val items = s.layoutInfo.visibleItemsInfo
            return if (items.isEmpty()) 0 else items.sumOf { it.size } / items.size
        }

    override val scrollOffsetPx: Float
        get() {
            val avg = avgItemSizePx
            return if (avg == 0) 0f
            else s.firstVisibleItemIndex * avg + s.firstVisibleItemScrollOffset.toFloat()
        }

    override val maxScrollPx: Float
        get() {
            val avg = avgItemSizePx
            if (avg == 0) return 0f
            val info = s.layoutInfo
            val totalContent = info.totalItemsCount.toLong() * avg
            val viewport = (info.viewportEndOffset - info.viewportStartOffset).toLong()
            return (totalContent - viewport).coerceAtLeast(0L).toFloat()
        }

    override val isScrollInProgress get() = s.isScrollInProgress

    override suspend fun scrollTo(px: Float) {
        val avg = avgItemSizePx
        if (avg == 0) return
        val index = (px / avg).toInt().coerceAtLeast(0)
        val offset = (px % avg).roundToInt()
        s.scrollToItem(index, offset)
    }
}

@Composable
fun rememberScrollbarState(scrollState: ScrollState): ScrollbarState =
    remember(scrollState) { ScrollStateAdapter(scrollState) }

@Composable
fun rememberScrollbarState(lazyListState: LazyListState): ScrollbarState =
    remember(lazyListState) { LazyListStateAdapter(lazyListState) }

// ============================================================
// Public API
// ============================================================

/**
 * macOS-style overlay vertical scrollbar.
 *
 * @param state           Scroll state, created via [rememberScrollbarState].
 * @param modifier        Applied to the track container.
 * @param controlSize     Overrides the ambient [ControlSize] to change track breadth,
 *                        thumb thickness, and trailing padding.
 * @param showOnEdgeHover When true, the scrollbar appears as soon as the pointer
 *                        enters the track area — even before scrolling starts.
 * @param trackClickBehavior What happens when the user clicks on the track outside
 *                        the thumb. [TrackClickBehavior.Jump] scrolls one viewport;
 *                        [TrackClickBehavior.Seek] seeks to the click position.
 *                        Use [TrackClickBehavior.None] on touch-only platforms.
 * @param style           Optional style override; defaults to [MacosTheme.componentStyling.scrollbar].
 * @param topInset        Inset from the top edge before the thumb track starts.
 *                        Use this to keep the scrollbar below a fixed title bar or glass
 *                        overlay. Pass `contentPadding.calculateTopPadding()` from
 *                        [Scaffold][io.github.kdroidfilter.nucleus.ui.apple.macos.components.Scaffold]
 *                        for page-level scrollbars. Defaults to 0.dp.
 */
@Composable
fun VerticalScrollbar(
    state: ScrollbarState,
    modifier: Modifier = Modifier,
    controlSize: ControlSize = LocalControlSize.current,
    showOnEdgeHover: Boolean = true,
    trackClickBehavior: TrackClickBehavior = TrackClickBehavior.Jump,
    style: ScrollbarStyle = MacosTheme.componentStyling.scrollbar,
    topInset: Dp = 0.dp,
) {
    ScrollbarImpl(
        state = state,
        isVertical = true,
        modifier = modifier,
        controlSize = controlSize,
        showOnEdgeHover = showOnEdgeHover,
        trackClickBehavior = trackClickBehavior,
        style = style,
        topInset = topInset,
    )
}

/**
 * macOS-style overlay horizontal scrollbar.
 *
 * @param state           Scroll state, created via [rememberScrollbarState].
 * @param modifier        Applied to the track container.
 * @param controlSize     Overrides the ambient [ControlSize] to change track breadth,
 *                        thumb thickness, and trailing padding.
 * @param showOnEdgeHover When true, the scrollbar appears as soon as the pointer
 *                        enters the track area.
 * @param trackClickBehavior See [VerticalScrollbar].
 * @param style           Optional style override; defaults to [MacosTheme.componentStyling.scrollbar].
 */
@Composable
fun HorizontalScrollbar(
    state: ScrollbarState,
    modifier: Modifier = Modifier,
    controlSize: ControlSize = LocalControlSize.current,
    showOnEdgeHover: Boolean = true,
    trackClickBehavior: TrackClickBehavior = TrackClickBehavior.Jump,
    style: ScrollbarStyle = MacosTheme.componentStyling.scrollbar,
) {
    ScrollbarImpl(
        state = state,
        isVertical = false,
        modifier = modifier,
        controlSize = controlSize,
        showOnEdgeHover = showOnEdgeHover,
        trackClickBehavior = trackClickBehavior,
        style = style,
    )
}

// ============================================================
// Core implementation
// ============================================================

@Composable
private fun ScrollbarImpl(
    state: ScrollbarState,
    isVertical: Boolean,
    modifier: Modifier,
    controlSize: ControlSize,
    showOnEdgeHover: Boolean,
    trackClickBehavior: TrackClickBehavior,
    style: ScrollbarStyle,
    topInset: Dp = 0.dp,
) {
    val metrics = style.metrics
    val colors = style.colors
    val scope = rememberCoroutineScope()

    // ---- Resolved metrics for current size ----
    val trackBreadth: Dp = metrics.trackBreadthFor(controlSize)
    val thumbBreadthIdle: Dp = metrics.thumbBreadthIdleFor(controlSize)
    val trailingPad: Dp = metrics.trailingPadFor(controlSize)
    val cornerRadius: Dp = metrics.cornerRadiusFor(controlSize)
    val thumbMinLength: Dp = metrics.thumbMinLengthFor(controlSize)

    // Track length in px along the scroll axis — updated in the Layout measure pass
    var trackSizePx by remember { mutableStateOf(0) }

    // ---- Auto-hide ----
    var showThumb by remember { mutableStateOf(false) }
    var hideJob by remember { mutableStateOf<Job?>(null) }

    fun cancelHide() { hideJob?.cancel(); hideJob = null }
    fun scheduleHide() {
        hideJob?.cancel()
        hideJob = scope.launch {
            delay(metrics.hideDelayMs)
            showThumb = false
        }
    }

    LaunchedEffect(state.isScrollInProgress) {
        if (state.isScrollInProgress) {
            cancelHide()
            showThumb = true
        } else {
            scheduleHide()
        }
    }

    // ---- Thumb hover ----
    val thumbInteraction = remember { MutableInteractionSource() }
    val isThumbHovered by thumbInteraction.collectIsHoveredAsState()

    LaunchedEffect(isThumbHovered) {
        if (isThumbHovered) {
            cancelHide()
            showThumb = true
        } else if (!state.isScrollInProgress) {
            scheduleHide()
        }
    }

    // ---- Track (edge) hover — shows scrollbar before thumb is visible ----
    val trackInteraction = remember { MutableInteractionSource() }
    val isTrackHovered by trackInteraction.collectIsHoveredAsState()

    LaunchedEffect(isTrackHovered) {
        if (isTrackHovered && showOnEdgeHover) {
            cancelHide()
            showThumb = true
        } else if (!isTrackHovered && !isThumbHovered && !state.isScrollInProgress) {
            scheduleHide()
        }
    }

    // ---- Thumb breadth (fixed — no expansion on hover) ----
    val thumbBreadthDp: Dp = thumbBreadthIdle

    // ---- Color / alpha (show/hide only, no hover color change) ----
    val targetColor = when {
        !showThumb || trackSizePx == 0 || state.maxScrollPx <= 0f -> Color.Transparent
        else -> colors.thumbIdle
    }
    val thumbColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(if (targetColor != Color.Transparent) 150 else 300),
        label = "scrollbar_color",
    )

    // ---- Layout: sizes the track and places the thumb ----
    Layout(
        modifier = modifier
            .then(
                if (isVertical) Modifier.width(trackBreadth) else Modifier.height(trackBreadth),
            )
            .hoverable(trackInteraction)
            .pointerInput(state, isVertical, trackClickBehavior) {
                if (trackClickBehavior == TrackClickBehavior.None) return@pointerInput
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        if (event.type != PointerEventType.Press) continue
                        val down = event.changes.firstOrNull() ?: continue
                        val clickPos = if (isVertical) down.position.y else down.position.x
                        val minPx = thumbMinLength.toPx()
                        val (thumbLen, thumbOff) = computeThumb(
                            trackSizePx = trackSizePx.toFloat(),
                            scrollOffset = state.scrollOffsetPx,
                            maxScroll = state.maxScrollPx,
                            minLength = minPx,
                        )
                        val isOutsideThumb = clickPos < thumbOff || clickPos > thumbOff + thumbLen
                        if (isOutsideThumb && trackSizePx > 0) {
                            when (trackClickBehavior) {
                                TrackClickBehavior.Seek -> {
                                    val ratio = (clickPos / trackSizePx).coerceIn(0f, 1f)
                                    scope.launch { state.scrollTo(ratio * state.maxScrollPx) }
                                }
                                TrackClickBehavior.Jump -> {
                                    val direction = if (clickPos < thumbOff) -1f else 1f
                                    scope.launch {
                                        state.scrollTo(
                                            (state.scrollOffsetPx + direction * trackSizePx)
                                                .coerceIn(0f, state.maxScrollPx),
                                        )
                                    }
                                }
                                TrackClickBehavior.None -> Unit
                            }
                        }
                    }
                }
            },
        content = {
            // Single child: the thumb
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(thumbColor)
                    .hoverable(thumbInteraction)
                    .pointerInput(state, isVertical) {
                        awaitEachGesture {
                            val down = awaitFirstDown(requireUnconsumed = false)
                            cancelHide()
                            showThumb = true

                            // Capture geometry at drag start for a stable px↔scroll mapping
                            val startScroll = state.scrollOffsetPx
                            val minPx = thumbMinLength.toPx()
                            val (startThumbLength, _) = computeThumb(
                                trackSizePx = trackSizePx.toFloat(),
                                scrollOffset = startScroll,
                                maxScroll = state.maxScrollPx,
                                minLength = minPx,
                            )
                            val usable = (trackSizePx - startThumbLength).coerceAtLeast(1f)

                            var accumulated = 0f
                            drag(down.id) { change ->
                                val delta = change.position - change.previousPosition
                                accumulated += if (isVertical) delta.y else delta.x
                                val newScroll =
                                    (startScroll + accumulated / usable * state.maxScrollPx)
                                        .coerceIn(0f, state.maxScrollPx)
                                scope.launch { state.scrollTo(newScroll) }
                                change.consume()
                            }
                            scheduleHide()
                        }
                    },
            )
        },
    ) { measurables, constraints ->
        // Track length: fill all available space on the scroll axis, minus top inset
        val totalLength = if (isVertical) constraints.maxHeight else constraints.maxWidth
        val topInsetPx = if (isVertical) topInset.roundToPx() else 0
        val trackLength = (totalLength - topInsetPx).coerceAtLeast(0)
        trackSizePx = trackLength

        val minPx = thumbMinLength.toPx()
        val thumbBreadthPx = thumbBreadthDp.roundToPx()
        val (thumbLen, thumbOff) = computeThumb(
            trackSizePx = trackLength.toFloat(),
            scrollOffset = state.scrollOffsetPx,
            maxScroll = state.maxScrollPx,
            minLength = minPx,
        )
        val thumbLenPx = thumbLen.roundToInt().coerceAtLeast(0)
        val trailPadPx = trailingPad.roundToPx()
        val trackBreadthPx = trackBreadth.roundToPx()

        val placeable = if (state.maxScrollPx > 0f && trackLength > 0) {
            measurables.firstOrNull()?.measure(
                if (isVertical) Constraints.fixed(thumbBreadthPx, thumbLenPx)
                else Constraints.fixed(thumbLenPx, thumbBreadthPx),
            )
        } else {
            null
        }

        val layoutW = if (isVertical) trackBreadthPx else totalLength
        val layoutH = if (isVertical) totalLength else trackBreadthPx

        layout(layoutW, layoutH) {
            if (placeable != null) {
                val posAlong = (thumbOff.roundToInt() + topInsetPx)
                    .coerceIn(topInsetPx, (totalLength - thumbLenPx).coerceAtLeast(topInsetPx))
                val posAcross = (trackBreadthPx - trailPadPx - thumbBreadthPx).coerceAtLeast(0)

                if (isVertical) placeable.place(posAcross, posAlong)
                else placeable.place(posAlong, posAcross)
            }
        }
    }
}

// ============================================================
// Thumb geometry math
// ============================================================
//
// For overlay scrollbars: trackSize == viewportSize, so:
//   visiblePart  = viewport / (viewport + maxScroll)
//              = trackSize / (trackSize + maxScroll)
//   thumbLength  = max(minLength, trackSize × visiblePart)
//   scrollable   = trackSize - thumbLength
//   thumbOffset  = scrollable × scrollOffset / maxScroll
//
private fun computeThumb(
    trackSizePx: Float,
    scrollOffset: Float,
    maxScroll: Float,
    minLength: Float,
): Pair<Float, Float> {
    if (trackSizePx <= 0f || maxScroll <= 0f) return Pair(trackSizePx, 0f)
    val visiblePart = trackSizePx / (trackSizePx + maxScroll)
    val thumbLength = (trackSizePx * visiblePart).coerceAtLeast(minLength)
    val scrollable = (trackSizePx - thumbLength).coerceAtLeast(0f)
    val thumbOffset = if (maxScroll > 0f) scrollable * scrollOffset / maxScroll else 0f
    return Pair(thumbLength, thumbOffset.coerceIn(0f, scrollable))
}
