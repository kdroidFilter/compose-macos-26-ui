package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.ContextMenu
import io.github.kdroidfilter.darwinui.components.ContextMenuItem
import io.github.kdroidfilter.darwinui.components.ContextMenuSeparator
import io.github.kdroidfilter.darwinui.components.ContextMenuShortcut
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.LucideCopy
import io.github.kdroidfilter.darwinui.icons.LucideChevronsLeft
import io.github.kdroidfilter.darwinui.icons.LucideSearch
import io.github.kdroidfilter.darwinui.icons.LucideTrash2
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.LocalDarwinTextStyle

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
                        color = DarwinTheme.colorScheme.border,
                        shape = DarwinTheme.shapes.large,
                    )
                    .clip(DarwinTheme.shapes.large)
                    .padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Right-click here to open context menu",
                    style = DarwinTheme.typography.subheadline,
                    color = DarwinTheme.colorScheme.mutedForeground,
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
                        color = DarwinTheme.colorScheme.border,
                        shape = DarwinTheme.shapes.large,
                    )
                    .clip(DarwinTheme.shapes.large)
                    .padding(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Right-click here to open context menu",
                    style = DarwinTheme.typography.subheadline,
                    color = DarwinTheme.colorScheme.mutedForeground,
                )
            }
        },
    ) {
        ContextMenuItem(
            onSelect = {},
            leadingContent = {
                val tint = LocalDarwinTextStyle.current.color
                Image(
                    imageVector = LucideChevronsLeft,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    colorFilter = ColorFilter.tint(tint),
                )
            },
            trailingContent = { ContextMenuShortcut("⌘Z") },
        ) { Text("Undo") }
        ContextMenuItem(
            onSelect = {},
            enabled = false,
            leadingContent = {
                val tint = LocalDarwinTextStyle.current.color
                Image(
                    imageVector = LucideChevronsLeft,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    colorFilter = ColorFilter.tint(tint),
                )
            },
            trailingContent = { ContextMenuShortcut("⌘⇧Z") },
        ) { Text("Redo") }
        ContextMenuSeparator()
        ContextMenuItem(
            onSelect = {},
            leadingContent = {
                val tint = LocalDarwinTextStyle.current.color
                Image(
                    imageVector = LucideCopy,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    colorFilter = ColorFilter.tint(tint),
                )
            },
            trailingContent = { ContextMenuShortcut("⌘C") },
        ) { Text("Copy") }
        ContextMenuItem(
            onSelect = {},
            leadingContent = {
                val tint = LocalDarwinTextStyle.current.color
                Image(
                    imageVector = LucideSearch,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    colorFilter = ColorFilter.tint(tint),
                )
            },
            trailingContent = { ContextMenuShortcut("⌘F") },
        ) { Text("Find…") }
        ContextMenuSeparator()
        ContextMenuItem(
            onSelect = {},
            destructive = true,
            leadingContent = {
                val tint = LocalDarwinTextStyle.current.color
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
