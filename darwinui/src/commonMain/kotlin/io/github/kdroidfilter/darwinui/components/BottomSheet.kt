package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

object BottomSheetDefaults {
    val containerColor: Color @Composable get() = DarwinTheme.colorScheme.surfaceContainerLow
    val contentColor: Color @Composable get() = DarwinTheme.colorScheme.onSurface
    val scrimColor: Color @Composable get() = Color.Black.copy(alpha = 0.32f)
    val TonalElevation: Dp = 2.dp
    val shape: Shape get() = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)

    @Composable
    fun DragHandle(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .padding(top = 12.dp, bottom = 8.dp)
                .size(width = 32.dp, height = 4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(DarwinTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)),
        )
    }
}

/**
 * A modal bottom sheet that slides up from the bottom of the screen.
 * Properly handles enter/exit animations, scrim, and vertical drag to dismiss.
 */
@Composable
fun ModalBottomSheet(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = BottomSheetDefaults.shape,
    containerColor: Color = BottomSheetDefaults.containerColor,
    contentColor: Color = BottomSheetDefaults.contentColor,
    tonalElevation: Dp = BottomSheetDefaults.TonalElevation,
    scrimColor: Color = BottomSheetDefaults.scrimColor,
    dragHandle: @Composable ColumnScope.() -> Unit = {
        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            BottomSheetDefaults.DragHandle()
        }
    },
    content: @Composable ColumnScope.() -> Unit,
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    // Sheet offset: 0 = fully shown, sheetHeight = fully hidden (off-screen below)
    val offsetY = remember { Animatable(1f) }
    var sheetHeightPx by remember { mutableStateOf(0f) }
    var dismissing by remember { mutableStateOf(false) }

    // Velocity threshold for fling-to-dismiss (px/s)
    val velocityThreshold = with(density) { 300.dp.toPx() }

    // Animate in on first composition
    LaunchedEffect(Unit) {
        offsetY.animateTo(0f, animationSpec = darwinSpring<Float>(DarwinSpringPreset.Gentle))
    }

    // Dismiss animation helper
    fun animateDismiss() {
        if (dismissing) return
        dismissing = true
        scope.launch {
            offsetY.animateTo(1f, animationSpec = darwinSpring<Float>(DarwinSpringPreset.Snappy))
            onDismissRequest()
        }
    }

    // Scrim alpha follows sheet position
    val scrimAlpha by animateFloatAsState(
        targetValue = if (dismissing) 0f else 1f - offsetY.value,
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "scrimAlpha",
    )

    Popup(
        alignment = Alignment.BottomCenter,
        onDismissRequest = { animateDismiss() },
        properties = PopupProperties(focusable = true),
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val maxHeightPx = with(density) { maxHeight.toPx() }

            // Scrim
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(scrimColor.copy(alpha = scrimColor.alpha * scrimAlpha))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { animateDismiss() },
                    ),
            )

            // Draggable state for the sheet
            val draggableState = rememberDraggableState { delta ->
                scope.launch {
                    val newOffset = (offsetY.value + delta / sheetHeightPx.coerceAtLeast(1f))
                        .coerceAtLeast(0f) // Don't allow dragging above fully expanded
                    offsetY.snapTo(newOffset)
                }
            }

            // Sheet content
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .onSizeChanged { size ->
                        sheetHeightPx = size.height.toFloat()
                    }
                    .offset {
                        IntOffset(0, (offsetY.value * sheetHeightPx).roundToInt())
                    }
                    .align(Alignment.BottomCenter)
                    .clip(shape)
                    .background(containerColor, shape)
                    .draggable(
                        state = draggableState,
                        orientation = Orientation.Vertical,
                        onDragStopped = { velocity ->
                            // Dismiss if dragged past 40% or flung downward fast
                            if (offsetY.value > 0.4f || velocity > velocityThreshold) {
                                animateDismiss()
                            } else {
                                // Snap back to fully expanded
                                scope.launch {
                                    offsetY.animateTo(
                                        0f,
                                        animationSpec = darwinSpring<Float>(DarwinSpringPreset.Snappy),
                                    )
                                }
                            }
                        },
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}, // Prevent scrim click-through
                    ),
            ) {
                dragHandle()
                content()
            }
        }
    }
}
