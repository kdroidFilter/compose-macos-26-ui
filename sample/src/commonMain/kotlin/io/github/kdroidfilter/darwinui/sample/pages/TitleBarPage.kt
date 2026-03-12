package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.AddressBar
import io.github.kdroidfilter.darwinui.components.DropdownMenuItem
import io.github.kdroidfilter.darwinui.components.NavigationButtons
import io.github.kdroidfilter.darwinui.components.SidebarButton
import io.github.kdroidfilter.darwinui.components.TitleBar
import io.github.kdroidfilter.darwinui.components.TitleBarButtonGroup
import io.github.kdroidfilter.darwinui.components.TitleBarGroupButton
import io.github.kdroidfilter.darwinui.components.TitleBarGroupDivider
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideCopy
import io.github.kdroidfilter.darwinui.icons.LucideDownload
import io.github.kdroidfilter.darwinui.icons.LucidePlus
import io.github.kdroidfilter.darwinui.icons.LucideSearch
import io.github.kdroidfilter.darwinui.icons.LucideUpload
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("TitleBar", "Browser")
@Composable
fun TitleBarBrowserExample() {
    var urlText by remember { mutableStateOf("apple.com") }
    TitleBar(
        navigationActions = {
            SidebarButton(
                onClick = {},
                menuContent = {
                    DropdownMenuItem(onClick = {}) { Text("Bookmarks") }
                    DropdownMenuItem(onClick = {}) { Text("Reading List") }
                    DropdownMenuItem(onClick = {}) { Text("Shared with You") }
                },
            )
            Spacer(modifier = Modifier.width(8.dp))
            NavigationButtons(
                onBack = {},
                onForward = {},
                backEnabled = true,
                forwardEnabled = false,
            )
        },
        title = {
            AddressBar(
                value = urlText,
                onValueChange = { urlText = it },
                onGo = {},
                leadingIcon = { Icon(LucideSearch, modifier = Modifier.size(13.dp)) },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        actions = {
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
                    Icon(LucidePlus, modifier = Modifier.size(14.dp))
                }
                TitleBarGroupDivider()
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideCopy, modifier = Modifier.size(14.dp))
                }
            }
        },
    )
}

@Composable
internal fun TitleBarPage() {
    GalleryPage("TitleBar", "macOS-style title bar with window controls, navigation, and grouped toolbar actions.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Browser",
            description = "Safari-style toolbar: back/forward navigation, URL field, and action group",
            sourceCode = GallerySources.TitleBarBrowserExample,
        ) { TitleBarBrowserExample() }
    }
}
