package io.github.kdroidfilter.darwinui.components.popover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.glassBorderOrDefault
import io.github.kdroidfilter.darwinui.theme.glassOrDefault

/**
 * A click-triggered popover component for Darwin UI.
 *
 * Displays an arbitrary content panel anchored to a trigger element. The popover
 * appears when [expanded] is true and dismisses when the user clicks outside of it.
 *
 * The popover uses the card color scheme by default, or a glass-morphism effect
 * when [glass] is enabled.
 *
 * @param expanded Whether the popover is currently visible.
 * @param onDismissRequest Callback invoked when the user clicks outside the popover
 *   to dismiss it.
 * @param glass When true, applies a glass-morphism background and border instead of
 *   the standard card colors.
 * @param modifier Modifier applied to the root container.
 * @param trigger The composable element that acts as the popover anchor. Typically
 *   a button that toggles [expanded].
 * @param content The composable content displayed inside the popover panel.
 */
@Composable
fun DarwinPopover(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    glass: Boolean = false,
    modifier: Modifier = Modifier,
    trigger: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes

    val backgroundColor = glassOrDefault(glass, colors.card)
    val borderColor = glassBorderOrDefault(glass, colors.border)

    Box(modifier = modifier) {
        trigger()

        if (expanded) {
            Popup(
                alignment = Alignment.TopStart,
                onDismissRequest = onDismissRequest,
                properties = PopupProperties(
                    focusable = true,
                ),
            ) {
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn(animationSpec = tween(150)) +
                            scaleIn(
                                initialScale = 0.95f,
                                transformOrigin = TransformOrigin(0.5f, 0f),
                                animationSpec = tween(150),
                            ),
                    exit = fadeOut(animationSpec = tween(100)) +
                            scaleOut(
                                targetScale = 0.95f,
                                transformOrigin = TransformOrigin(0.5f, 0f),
                                animationSpec = tween(100),
                            ),
                ) {
                    Box(
                        modifier = Modifier
                            .shadow(
                                elevation = 8.dp,
                                shape = shapes.large,
                            )
                            .clip(shapes.large)
                            .background(backgroundColor, shapes.large)
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = shapes.large,
                            )
                            .widthIn(min = 200.dp)
                            .padding(16.dp),
                    ) {
                        content()
                    }
                }
            }
        }
    }
}
