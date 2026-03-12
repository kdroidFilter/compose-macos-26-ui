@file:OptIn(ExperimentalComposeUiApi::class)

package io.github.kdroidfilter.darwinui.theme

import androidx.compose.foundation.ContextMenuRepresentation
import androidx.compose.foundation.ContextMenuState
import androidx.compose.foundation.LocalContextMenuRepresentation
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.rememberPopupPositionProviderAtPosition

private class DarwinContextMenuRepresentation : ContextMenuRepresentation {
    @Composable
    override fun Representation(
        state: ContextMenuState,
        items: () -> List<androidx.compose.foundation.ContextMenuItem>,
    ) {
        val status = state.status
        if (status is ContextMenuState.Status.Open) {
            val colors = DarwinTheme.colorScheme
            val shapes = DarwinTheme.shapes

            val bgColor = if (colors.isDark) Zinc900.copy(alpha = 0.95f)
            else Color.White.copy(alpha = 0.95f)
            val borderColor = if (colors.isDark) Color.White.copy(alpha = 0.10f)
            else Color.Black.copy(alpha = 0.10f)
            val shape = shapes.large // rounded-xl = 12dp

            val positionProvider = rememberPopupPositionProviderAtPosition(status.rect.center)

            Popup(
                popupPositionProvider = positionProvider,
                onDismissRequest = { state.status = ContextMenuState.Status.Closed },
                properties = PopupProperties(focusable = true),
            ) {
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .widthIn(min = 180.dp) // min-w-45
                        .shadow(elevation = 8.dp, shape = shape)
                        .clip(shape)
                        .background(bgColor, shape)
                        .border(1.dp, borderColor, shape)
                        .padding(vertical = 4.dp) // p-1 vertical
                        .verticalScroll(rememberScrollState()),
                ) {
                    items().forEach { item ->
                        DarwinContextMenuItemRow(
                            label = item.label,
                            onClick = {
                                state.status = ContextMenuState.Status.Closed
                                item.onClick()
                            },
                            colors = colors,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DarwinContextMenuItemRow(
    label: String,
    onClick: () -> Unit,
    colors: ColorScheme,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    // hover:bg-black/10 dark:hover:bg-white/10
    val itemBg = if (isHovered) {
        if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)
    } else {
        Color.Transparent
    }

    // text-zinc-700 dark:text-zinc-300 / hover:text-zinc-900 dark:hover:text-zinc-100
    val textColor = if (isHovered) {
        if (colors.isDark) Zinc100 else Zinc900
    } else {
        if (colors.isDark) Zinc300 else Zinc700
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp) // p-1 horizontal
            .clip(DarwinTheme.shapes.small) // rounded-lg = 8dp
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .background(itemBg)
            .padding(horizontal = 8.dp, vertical = 6.dp), // px-2 py-1.5
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicText(
            text = label,
            style = DarwinTheme.typography.subheadline.merge(
                TextStyle(color = textColor)
            ),
        )
    }
}

@Composable
internal actual fun PlatformContextMenuOverride(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalContextMenuRepresentation provides remember { DarwinContextMenuRepresentation() },
    ) {
        content()
    }
}
