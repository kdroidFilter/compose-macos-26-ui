package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosDuration
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosTween

/**
 * A draggable vertical separator between columns in [Scaffold].
 *
 * Renders as a thin line with an invisible 8.dp hit area for easy dragging.
 * On hover the line widens and tints with the accent color.
 * Double-click resets the adjacent column to its ideal width.
 *
 * @param onDrag Called with the horizontal drag delta in [Dp].
 * @param onReset Called on double-click to reset the column width.
 * @param modifier Modifier applied to the hit area wrapper.
 */
@Composable
fun ColumnDivider(
    onDrag: (Dp) -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val isDark = MacosTheme.colorScheme.isDark
    val accentColor = MacosTheme.colorScheme.accent

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val lineColor by animateColorAsState(
        targetValue = if (isHovered) {
            accentColor.copy(alpha = 0.5f)
        } else {
            if (isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)
        },
        animationSpec = macosTween(MacosDuration.Fast),
        label = "divider_color",
    )
    val lineWidth by animateDpAsState(
        targetValue = if (isHovered) 2.dp else 1.dp,
        animationSpec = macosTween(MacosDuration.Fast),
        label = "divider_width",
    )

    // 8.dp invisible hit area
    Box(
        modifier = modifier
            .width(8.dp)
            .fillMaxHeight()
            .hoverable(interactionSource)
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
                .width(lineWidth)
                .fillMaxHeight()
                .background(lineColor),
        )
    }
}
