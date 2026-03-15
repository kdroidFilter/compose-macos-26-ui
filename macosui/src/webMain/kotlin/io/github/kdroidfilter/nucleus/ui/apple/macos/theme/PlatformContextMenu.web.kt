@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.foundation.text.contextmenu.data.TextContextMenuComponent
import androidx.compose.foundation.text.contextmenu.data.TextContextMenuItemWithComposableLeadingIcon

internal actual fun TextContextMenuComponent.toItemInfo(): ContextMenuItemInfo? {
    if (this !is TextContextMenuItemWithComposableLeadingIcon) return null
    return ContextMenuItemInfo(
        key = key,
        label = label,
        enabled = enabled,
        onClick = onClick,
    )
}
