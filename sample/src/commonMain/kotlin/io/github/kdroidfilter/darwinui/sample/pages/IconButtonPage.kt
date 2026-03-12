package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.DropdownMenuItem
import io.github.kdroidfilter.darwinui.components.HelpButton
import io.github.kdroidfilter.darwinui.components.IconButton
import io.github.kdroidfilter.darwinui.components.NavigationButtons
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.SidebarButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TitleBarButtonGroup
import io.github.kdroidfilter.darwinui.components.TitleBarGroupButton
import io.github.kdroidfilter.darwinui.components.TitleBarGroupDivider
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideCopy
import io.github.kdroidfilter.darwinui.icons.LucideDownload
import io.github.kdroidfilter.darwinui.icons.LucideSettings
import io.github.kdroidfilter.darwinui.icons.LucideShare2
import io.github.kdroidfilter.darwinui.icons.LucideUpload
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("IconButton", "Help")
@Composable
fun IconButtonHelpExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PushButton(text = "Avancé...", onClick = {})
        HelpButton(onClick = {})
    }
}

@GalleryExample("IconButton", "Icon")
@Composable
fun IconButtonIconExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = {}) {
            Icon(
                imageVector = LucideSettings,
                modifier = Modifier.size(14.dp),
            )
        }
        IconButton(onClick = {}, enabled = false) {
            Icon(
                imageVector = LucideSettings,
                modifier = Modifier.size(14.dp),
            )
        }
    }
}

@GalleryExample("IconButton", "NavigationButtons")
@Composable
fun IconButtonNavigationButtonsExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        NavigationButtons(onBack = {}, onForward = {}, backEnabled = true, forwardEnabled = false)
        NavigationButtons(onBack = {}, onForward = {}, backEnabled = true, forwardEnabled = true)
        NavigationButtons(onBack = {}, onForward = {}, backEnabled = false, forwardEnabled = false)
    }
}

@GalleryExample("IconButton", "Button Group")
@Composable
fun IconButtonButtonGroupExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TitleBarButtonGroup {
            TitleBarGroupButton(onClick = {}) {
                Icon(LucideDownload, modifier = Modifier.size(14.dp))
            }
            TitleBarGroupDivider()
            TitleBarGroupButton(onClick = {}) {
                Icon(LucideUpload, modifier = Modifier.size(14.dp))
            }
            TitleBarGroupDivider()
            TitleBarGroupButton(onClick = {}) {
                Icon(LucideCopy, modifier = Modifier.size(14.dp))
            }
        }
        TitleBarButtonGroup {
            TitleBarGroupButton(onClick = {}) {
                Icon(LucideShare2, modifier = Modifier.size(14.dp))
            }
        }
        TitleBarButtonGroup {
            TitleBarGroupButton(onClick = {}, enabled = false) {
                Icon(LucideCopy, modifier = Modifier.size(14.dp))
            }
            TitleBarGroupDivider()
            TitleBarGroupButton(onClick = {}, enabled = false) {
                Icon(LucideShare2, modifier = Modifier.size(14.dp))
            }
        }
    }
}

@GalleryExample("IconButton", "Sidebar Button")
@Composable
fun IconButtonSidebarButtonExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SidebarButton(onClick = {})
        SidebarButton(
            onClick = {},
            menuContent = {
                DropdownMenuItem(onClick = {}) { Text("Bookmarks") }
                DropdownMenuItem(onClick = {}) { Text("Reading List") }
                DropdownMenuItem(onClick = {}) { Text("Shared with You") }
            },
        )
    }
}

@Composable
internal fun IconButtonPage() {
    GalleryPage("Icon Button", "macOS-native circular icon buttons with idiomatic Compose APIs.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Help Button",
            description = "Circular button with ? label, matching NSButton .helpButton bezel style",
            sourceCode = GallerySources.IconButtonHelpExample,
        ) { IconButtonHelpExample() }
        ExampleCard(
            title = "Icon Button",
            description = "Circular button wrapping any icon content",
            sourceCode = GallerySources.IconButtonIconExample,
        ) { IconButtonIconExample() }

        SectionHeader("Grouped")
        ExampleCard(
            title = "NavigationButtons",
            description = "Combined back/forward pill — forward disabled, both enabled, both disabled",
            sourceCode = GallerySources.IconButtonNavigationButtonsExample,
        ) { IconButtonNavigationButtonsExample() }
        ExampleCard(
            title = "Button Group",
            description = "Pill container grouping icon buttons with dividers, including disabled state",
            sourceCode = GallerySources.IconButtonButtonGroupExample,
        ) { IconButtonButtonGroupExample() }
        ExampleCard(
            title = "Sidebar Button",
            description = "Sidebar toggle with dropdown menu — icon only and with chevron menu",
            sourceCode = GallerySources.IconButtonSidebarButtonExample,
        ) { IconButtonSidebarButtonExample() }
    }
}
