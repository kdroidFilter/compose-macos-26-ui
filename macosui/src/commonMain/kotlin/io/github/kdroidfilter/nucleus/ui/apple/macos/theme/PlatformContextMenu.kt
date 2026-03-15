@file:OptIn(ExperimentalFoundationApi::class)

package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.foundation.ComposeFoundationFlags
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.contextmenu.data.TextContextMenuComponent
import androidx.compose.foundation.text.contextmenu.data.TextContextMenuKeys
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
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.nucleus.ui.apple.macos.util.isApplePlatform
import io.github.kdroidfilter.nucleus.ui.apple.macos.util.isWebPlatform
import kotlinx.coroutines.channels.Channel

/**
 * Extracted info from a platform-specific [TextContextMenuComponent] subtype.
 */
internal data class ContextMenuItemInfo(
    val key: Any,
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
 * Provides a macOS-styled context menu for text fields on all platforms.
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
        ContextMenuPopup(currentMenu, currentCoordinates)
    }
}

@Composable
private fun ContextMenuPopup(
    menuState: OpenMenuState,
    coordinates: LayoutCoordinates,
) {
    val colors = MacosTheme.colorScheme
    val fallbackBg = if (colors.isDark) Color(0xFF262626) else Color(0xFFFAFAFA)
    val borderColor = if (colors.isDark) Color.White.copy(alpha = 0.12f) else Color.Black.copy(alpha = 0.08f)
    val menuShape = RoundedCornerShape(13.dp)
    val position = menuState.dataProvider.position(coordinates)
    val data = menuState.dataProvider.data()

    val session = remember(menuState) {
        object : TextContextMenuSession {
            override fun close() {
                menuState.onDismiss()
            }
        }
    }

    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var menuSize by remember { mutableStateOf(IntSize.Zero) }

    Popup(
        onDismissRequest = menuState.onDismiss,
        properties = PopupProperties(focusable = true),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { containerSize = it }
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = menuState.onDismiss,
                ),
        ) {
            val rawOffset = IntOffset(position.x.toInt(), position.y.toInt())
            val clampedOffset = IntOffset(
                x = rawOffset.x.coerceIn(0, (containerSize.width - menuSize.width).coerceAtLeast(0)),
                y = rawOffset.y.coerceIn(0, (containerSize.height - menuSize.height).coerceAtLeast(0)),
            )
            Column(
                modifier = Modifier
                    .onSizeChanged { menuSize = it }
                    .offset { clampedOffset }
                    .width(IntrinsicSize.Max)
                    .widthIn(min = 200.dp)
                    .shadow(
                        elevation = 25.dp,
                        shape = menuShape,
                        ambientColor = Color.Black.copy(alpha = 0.10f),
                        spotColor = Color.Black.copy(alpha = 0.16f),
                    )
                    .macosGlass(shape = menuShape, fallbackColor = fallbackBg)
                    .border(0.5.dp, borderColor, menuShape)
                    .padding(vertical = 5.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                for (component in data.components) {
                    if (component is TextContextMenuSeparator) continue
                    val itemInfo = component.toItemInfo() ?: continue
                    ContextMenuItemRow(
                        label = itemInfo.label,
                        shortcut = shortcutForKey(itemInfo.key),
                        enabled = itemInfo.enabled,
                        onClick = { itemInfo.onClick(session) },
                        colors = colors,
                    )
                }
            }
        }
    }
}

private fun shortcutForKey(key: Any): String? {
    val letter = when (key) {
        TextContextMenuKeys.CutKey -> "X"
        TextContextMenuKeys.CopyKey -> "C"
        TextContextMenuKeys.PasteKey -> "V"
        TextContextMenuKeys.SelectAllKey -> "A"
        else -> return null
    }
    return when {
        isApplePlatform && !isWebPlatform -> "⌘$letter"
        isApplePlatform -> "Cmd+$letter"
        else -> "Ctrl+$letter"
    }
}

@Composable
private fun ContextMenuItemRow(
    label: String,
    shortcut: String?,
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
        !enabled -> if (colors.isDark) Color.White.copy(alpha = 0.35f) else Color(0xFFBFBFBF)
        isHighlighted -> Color.White
        else -> if (colors.isDark) Color.White.copy(alpha = 0.85f) else Color(0xFF1A1A1A)
    }

    val itemShape = RoundedCornerShape(8.dp)
    val textStyle = TextStyle(
        fontSize = 13.sp,
        color = textColor,
        letterSpacing = (-0.2).sp,
    )

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
            style = textStyle,
            modifier = Modifier.weight(1f),
        )
        if (shortcut != null) {
            Spacer(modifier = Modifier.width(16.dp))
            BasicText(
                text = shortcut,
                style = textStyle.copy(letterSpacing = 0.sp),
            )
        }
    }
}
