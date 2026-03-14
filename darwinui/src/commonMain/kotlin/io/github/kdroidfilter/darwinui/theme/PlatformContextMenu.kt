@file:OptIn(ExperimentalFoundationApi::class)

package io.github.kdroidfilter.darwinui.theme

import androidx.compose.foundation.ComposeFoundationFlags
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.text.contextmenu.data.TextContextMenuComponent
import androidx.compose.foundation.text.contextmenu.data.TextContextMenuSeparator
import androidx.compose.foundation.text.contextmenu.data.TextContextMenuSession
import androidx.compose.foundation.text.contextmenu.provider.LocalTextContextMenuDropdownProvider
import androidx.compose.foundation.text.contextmenu.provider.TextContextMenuDataProvider
import androidx.compose.foundation.text.contextmenu.provider.TextContextMenuProvider
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.channels.Channel

/**
 * Extracted info from a platform-specific [TextContextMenuComponent] subtype.
 */
internal data class ContextMenuItemInfo(
    val label: String,
    val enabled: Boolean,
    val onClick: (TextContextMenuSession) -> Unit,
)

/**
 * Extracts [ContextMenuItemInfo] from a platform-specific [TextContextMenuComponent].
 * Returns null for separators or unknown component types.
 */
internal expect fun TextContextMenuComponent.toItemInfo(): ContextMenuItemInfo?

private class OpenMenuState(
    val dataProvider: TextContextMenuDataProvider,
    val onDismiss: () -> Unit,
)

/**
 * Provides a Darwin-styled context menu for text fields on all platforms.
 *
 * Enables the new Compose Foundation context menu system via
 * [ComposeFoundationFlags.isNewContextMenuEnabled] and overrides
 * [LocalTextContextMenuDropdownProvider] with a custom provider that
 * renders a macOS-inspired context menu popup.
 */
@Composable
internal fun PlatformContextMenuOverride(content: @Composable () -> Unit) {
    ComposeFoundationFlags.isNewContextMenuEnabled = true

    var openMenu by remember { mutableStateOf<OpenMenuState?>(null) }
    var rootCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }

    val provider = remember {
        object : TextContextMenuProvider {
            override suspend fun showTextContextMenu(
                dataProvider: TextContextMenuDataProvider,
            ) {
                val closeSignal = Channel<Unit>(Channel.RENDEZVOUS)
                openMenu = OpenMenuState(
                    dataProvider = dataProvider,
                    onDismiss = { closeSignal.trySend(Unit) },
                )
                try {
                    closeSignal.receive()
                } finally {
                    openMenu = null
                }
            }
        }
    }

    CompositionLocalProvider(
        LocalTextContextMenuDropdownProvider provides provider,
    ) {
        Box(
            modifier = Modifier.onGloballyPositioned { rootCoordinates = it },
        ) {
            content()
        }
    }

    val currentMenu = openMenu
    val currentCoordinates = rootCoordinates
    if (currentMenu != null && currentCoordinates != null) {
        DarwinContextMenuPopup(currentMenu, currentCoordinates)
    }
}

@Composable
private fun DarwinContextMenuPopup(
    menuState: OpenMenuState,
    coordinates: LayoutCoordinates,
) {
    val colors = DarwinTheme.colorScheme
    val fallbackBg = if (colors.isDark) Color(0xFF262626) else Color(0xFFF5F5F5)
    val menuShape = RoundedCornerShape(13.dp)
    val position = menuState.dataProvider.position(coordinates)
    val data = menuState.dataProvider.data()

    val positionProvider = remember(position) {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize,
            ): IntOffset = IntOffset(
                x = position.x.toInt(),
                y = position.y.toInt(),
            )
        }
    }

    val session = remember(menuState) {
        object : TextContextMenuSession {
            override fun close() {
                menuState.onDismiss()
            }
        }
    }

    Popup(
        popupPositionProvider = positionProvider,
        onDismissRequest = menuState.onDismiss,
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
            for (component in data.components) {
                if (component is TextContextMenuSeparator) continue
                val itemInfo = component.toItemInfo() ?: continue
                DarwinContextMenuItemRow(
                    label = itemInfo.label,
                    enabled = itemInfo.enabled,
                    onClick = { itemInfo.onClick(session) },
                    colors = colors,
                )
            }
        }
    }
}

@Composable
private fun DarwinContextMenuItemRow(
    label: String,
    enabled: Boolean,
    onClick: () -> Unit,
    colors: ColorScheme,
) {
    val accentColor = colors.accent
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHighlighted = enabled && (isHovered || isPressed)

    val itemBg = when {
        isHighlighted -> accentColor
        else -> Color.Transparent
    }

    val textColor = when {
        !enabled -> if (colors.isDark) Color.White.copy(alpha = 0.3f) else Color(0xFF1A1A1A).copy(alpha = 0.3f)
        isHighlighted -> Color.White
        else -> if (colors.isDark) Color.White.copy(alpha = 0.85f) else Color(0xFF1A1A1A)
    }

    val itemShape = RoundedCornerShape(12.dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .height(24.dp)
            .clip(itemShape)
            .hoverable(interactionSource, enabled = enabled)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
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
