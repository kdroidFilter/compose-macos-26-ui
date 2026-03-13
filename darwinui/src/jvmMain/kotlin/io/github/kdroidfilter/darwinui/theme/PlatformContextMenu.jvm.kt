@file:OptIn(ExperimentalComposeUiApi::class)

package io.github.kdroidfilter.darwinui.theme

import androidx.compose.foundation.ContextMenuRepresentation
import androidx.compose.foundation.ContextMenuState
import androidx.compose.foundation.LocalContextMenuRepresentation
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
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
            val fallbackBg = if (colors.isDark) Color(0xFF262626) else Color(0xFFF5F5F5)
            val menuShape = RoundedCornerShape(13.dp)

            val positionProvider = rememberPopupPositionProviderAtPosition(status.rect.center)

            Popup(
                popupPositionProvider = positionProvider,
                onDismissRequest = { state.status = ContextMenuState.Status.Closed },
                properties = PopupProperties(focusable = true),
            ) {
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .widthIn(min = 200.dp)
                        .shadow(
                            elevation = 25.dp,
                            shape = menuShape,
                            ambientColor = Color.Black.copy(alpha = 0.16f),
                            spotColor = Color.Black.copy(alpha = 0.16f),
                        )
                        .darwinGlass(shape = menuShape, fallbackColor = fallbackBg)
                        .padding(vertical = 5.dp)
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
    val accentColor = colors.accent
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHighlighted = isHovered || isPressed

    val itemBg = when {
        isHighlighted -> accentColor
        else -> Color.Transparent
    }

    val textColor = when {
        isHighlighted -> Color.White
        else -> if (colors.isDark) Color.White.copy(alpha = 0.85f)
        else Color(0xFF1A1A1A)
    }

    val itemShape = RoundedCornerShape(12.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(24.dp)
            .clip(itemShape)
            .hoverable(interactionSource)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick,
            )
            .background(itemBg)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicText(
            text = label,
            style = TextStyle(
                fontSize = 13.sp,
                color = textColor,
                letterSpacing = 0.sp,
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
