package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ContextMenu
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ContextMenuItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ContextMenuSeparator
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ContextMenuShortcut
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideCopy
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideChevronsLeft
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSearch
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideTrash2
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTextStyle

@GalleryExample("ContextMenu", "Default")
@Composable
fun ContextMenuDefaultExample() {
    ContextMenu(
        trigger = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MacosTheme.colorScheme.border,
                        shape = MacosTheme.shapes.large,
                    )
                    .clip(MacosTheme.shapes.large)
                    .padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Right-click here to open context menu",
                    style = MacosTheme.typography.subheadline,
                    color = MacosTheme.colorScheme.mutedForeground,
                )
            }
        },
    ) {
        ContextMenuItem(onSelect = {}) { Text("Cut") }
        ContextMenuItem(onSelect = {}) { Text("Copy") }
        ContextMenuItem(onSelect = {}) { Text("Paste") }
        ContextMenuSeparator()
        ContextMenuItem(onSelect = {}, destructive = true) { Text("Delete") }
    }
}

@GalleryExample("ContextMenu", "With Icons")
@Composable
fun ContextMenuWithIconsExample() {
    ContextMenu(
        trigger = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MacosTheme.colorScheme.border,
                        shape = MacosTheme.shapes.large,
                    )
                    .clip(MacosTheme.shapes.large)
                    .padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Right-click here to open context menu",
                    style = MacosTheme.typography.subheadline,
                    color = MacosTheme.colorScheme.mutedForeground,
                )
            }
        },
    ) {
        ContextMenuItem(
            onSelect = {},
            leadingContent = {
                val tint = LocalTextStyle.current.color
                Image(
                    imageVector = LucideChevronsLeft,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    colorFilter = ColorFilter.tint(tint),
                )
            },
            trailingContent = { ContextMenuShortcut("Z", command = true) },
        ) { Text("Undo") }
        ContextMenuItem(
            onSelect = {},
            enabled = false,
            leadingContent = {
                val tint = LocalTextStyle.current.color
                Image(
                    imageVector = LucideChevronsLeft,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    colorFilter = ColorFilter.tint(tint),
                )
            },
            trailingContent = { ContextMenuShortcut("Z", command = true, shift = true) },
        ) { Text("Redo") }
        ContextMenuSeparator()
        ContextMenuItem(
            onSelect = {},
            leadingContent = {
                val tint = LocalTextStyle.current.color
                Image(
                    imageVector = LucideCopy,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    colorFilter = ColorFilter.tint(tint),
                )
            },
            trailingContent = { ContextMenuShortcut("C", command = true) },
        ) { Text("Copy") }
        ContextMenuItem(
            onSelect = {},
            leadingContent = {
                val tint = LocalTextStyle.current.color
                Image(
                    imageVector = LucideSearch,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    colorFilter = ColorFilter.tint(tint),
                )
            },
            trailingContent = { ContextMenuShortcut("F", command = true) },
        ) { Text("Find…") }
        ContextMenuSeparator()
        ContextMenuItem(
            onSelect = {},
            destructive = true,
            leadingContent = {
                val tint = LocalTextStyle.current.color
                Image(
                    imageVector = LucideTrash2,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    colorFilter = ColorFilter.tint(tint),
                )
            },
            trailingContent = { ContextMenuShortcut("⌫") },
        ) { Text("Delete") }
    }
}

@Composable
internal fun ContextMenuPage() {
    GalleryPage("Context Menu", "A menu triggered by right-clicking on an element.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.ContextMenuDefaultExample) { ContextMenuDefaultExample() }
        ExampleCard(title = "With Icons", sourceCode = GallerySources.ContextMenuWithIconsExample) { ContextMenuWithIconsExample() }
    }
}
