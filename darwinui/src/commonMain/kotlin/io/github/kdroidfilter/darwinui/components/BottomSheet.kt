package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

object BottomSheetDefaults {
    val containerColor: Color @Composable get() = DarwinTheme.colorScheme.surfaceContainer
    val contentColor: Color @Composable get() = DarwinTheme.colorScheme.onSurface
    val scrimColor: Color @Composable get() = DarwinTheme.colorScheme.scrim
    val TonalElevation: Dp = 2.dp
    val shape: Shape get() = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
}

/**
 * A modal bottom sheet that slides up from the bottom of the screen.
 * Mirrors Material3's ModalBottomSheet.
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
    content: @Composable ColumnScope.() -> Unit,
) {
    Popup(
        alignment = Alignment.BottomCenter,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(tween(200)),
            exit = fadeOut(tween(150)),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(scrimColor)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onDismissRequest,
                    ),
            )
        }

        AnimatedVisibility(
            visible = true,
            enter = slideInVertically(tween(300)) { it },
            exit = slideOutVertically(tween(200)) { it },
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(containerColor, shape)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {},
                    ),
            ) {
                // Drag handle
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 12.dp, bottom = 8.dp)
                        .size(width = 32.dp, height = 4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(DarwinTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)),
                )
                content()
            }
        }
    }
}
