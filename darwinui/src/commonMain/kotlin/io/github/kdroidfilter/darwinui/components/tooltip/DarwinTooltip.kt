package io.github.kdroidfilter.darwinui.components.tooltip

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.Zinc100
import io.github.kdroidfilter.darwinui.theme.Zinc900
import kotlinx.coroutines.delay

/**
 * A hover-triggered tooltip component for Darwin UI.
 *
 * Displays a small informational popup near the trigger element when the user
 * hovers over it (or long-presses on touch devices). The tooltip appears after
 * a configurable delay and dismisses when the pointer exits the trigger area.
 *
 * The tooltip uses an inverted color scheme relative to the current theme:
 * dark background with light text in light mode, and light background with
 * dark text in dark mode.
 *
 * @param text The text content to display in the tooltip.
 * @param delayMillis The delay in milliseconds before showing the tooltip after
 *   hover begins. Defaults to 500ms.
 * @param modifier Modifier applied to the trigger wrapper.
 * @param content The trigger element that activates the tooltip on hover.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DarwinTooltip(
    text: String,
    delayMillis: Long = 500L,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val shapes = DarwinTheme.shapes

    var isHovered by remember { mutableStateOf(false) }
    var showTooltip by remember { mutableStateOf(false) }

    // Delay before showing the tooltip
    LaunchedEffect(isHovered) {
        if (isHovered) {
            delay(delayMillis)
            showTooltip = true
        } else {
            showTooltip = false
        }
    }

    // Fade animation
    val alpha by animateFloatAsState(
        targetValue = if (showTooltip) 1f else 0f,
        animationSpec = tween(durationMillis = 100),
        label = "tooltipAlpha",
    )

    // Tooltip colors: inverted from current theme
    val tooltipBackground = if (colors.isDark) Zinc100 else Zinc900

    val tooltipTextColor = if (colors.isDark) Zinc900 else Zinc100

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Enter -> isHovered = true
                            PointerEventType.Exit -> isHovered = false
                        }
                    }
                }
            },
    ) {
        content()

        if (alpha > 0f) {
            Popup(
                alignment = Alignment.TopCenter,
                offset = IntOffset(0, -8),
                properties = PopupProperties(
                    focusable = false,
                ),
            ) {
                Box(
                    modifier = Modifier
                        .graphicsLayer { this.alpha = alpha }
                        .shadow(
                            elevation = 4.dp,
                            shape = shapes.small,
                        )
                        .clip(shapes.small)
                        .background(tooltipBackground, shapes.small)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    BasicText(
                        text = text,
                        style = typography.bodySmall.merge(
                            TextStyle(color = tooltipTextColor)
                        ),
                    )
                }
            }
        }
    }
}
