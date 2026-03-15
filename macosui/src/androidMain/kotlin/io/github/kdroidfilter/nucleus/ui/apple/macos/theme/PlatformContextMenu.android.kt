package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.foundation.text.contextmenu.data.TextContextMenuComponent
import androidx.compose.foundation.text.contextmenu.data.TextContextMenuItem

internal actual fun TextContextMenuComponent.toItemInfo(): ContextMenuItemInfo? {
    if (this !is TextContextMenuItem) return null
    return ContextMenuItemInfo(
        key = key,
        label = label,
        enabled = true,
        onClick = onClick,
    )
}
