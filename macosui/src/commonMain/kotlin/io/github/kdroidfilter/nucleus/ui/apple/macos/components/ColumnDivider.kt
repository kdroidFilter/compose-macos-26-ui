package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

/**
 * A draggable vertical separator between columns in [Scaffold].
 *
 * Renders as a thin line with an invisible 12.dp hit area for easy dragging.
 * Double-click resets the adjacent column to its ideal width.
 *
 * @param onDrag Called with the horizontal drag delta in [Dp].
 * @param onReset Called on double-click to reset the column width.
 * @param modifier Modifier applied to the hit area wrapper.
 * @param showLine Whether to render the visible separator line.
 */
@Composable
fun ColumnDivider(
    onDrag: (Dp) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
    showLine: Boolean = true,
) {
    val density = LocalDensity.current
    val isDark = MacosTheme.colorScheme.isDark

    val lineColor = if (showLine) {
        if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)
    } else {
        Color.Transparent
    }

    // 12.dp invisible hit area with col-resize cursor
    Box(
        modifier = modifier
            .width(12.dp)
            .fillMaxHeight()
            .pointerHoverIcon(pointerIconResizeHorizontal)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { deltaPx ->
                    with(density) { onDrag(deltaPx.toDp()) }
                },
            )
            .pointerInput(Unit) {
                detectTapGestures(onDoubleTap = { onReset() })
            },
        contentAlignment = Alignment.Center,
    ) {
        // Visible line centered in the hit area
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(lineColor),
        )
    }
}
