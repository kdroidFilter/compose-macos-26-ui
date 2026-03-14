package io.github.kdroidfilter.darwinui.theme

import androidx.compose.foundation.text.contextmenu.data.TextContextMenuComponent
import androidx.compose.foundation.text.contextmenu.data.TextContextMenuItem

internal actual fun TextContextMenuComponent.toItemInfo(): ContextMenuItemInfo? {
    if (this !is TextContextMenuItem) return null
    return ContextMenuItemInfo(
        label = label,
        enabled = true,
        onClick = onClick,
    )
}
