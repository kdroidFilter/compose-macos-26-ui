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
import io.github.kdroidfilter.darwinui.components.IconButton
import io.github.kdroidfilter.darwinui.components.NavigationButtons
import io.github.kdroidfilter.darwinui.components.SearchSuggestionHeader
import io.github.kdroidfilter.darwinui.components.SearchSuggestionItem
import io.github.kdroidfilter.darwinui.components.SearchSuggestionSeparator
import io.github.kdroidfilter.darwinui.components.SidebarButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TitleBar
import io.github.kdroidfilter.darwinui.components.TitleBarButtonGroup
import io.github.kdroidfilter.darwinui.components.TitleBarGroupButton
import io.github.kdroidfilter.darwinui.components.TitleBarGroupDivider
import io.github.kdroidfilter.darwinui.components.ToolbarSearchField
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideChevronDown
import io.github.kdroidfilter.darwinui.icons.LucideCopy
import io.github.kdroidfilter.darwinui.icons.LucideDownload
import io.github.kdroidfilter.darwinui.icons.LucideEllipsis
import io.github.kdroidfilter.darwinui.icons.LucideLayoutGrid
import io.github.kdroidfilter.darwinui.icons.LucideList
import io.github.kdroidfilter.darwinui.icons.LucidePlus
import io.github.kdroidfilter.darwinui.icons.LucideSearch
import io.github.kdroidfilter.darwinui.icons.LucideShare2
import io.github.kdroidfilter.darwinui.icons.LucideTag
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

@GalleryExample("TitleBar", "Finder")
@Composable
fun TitleBarFinderExample() {
    var searchQuery by remember { mutableStateOf("") }
    var searchExpanded by remember { mutableStateOf(false) }

    TitleBar(
        navigationActions = {
            NavigationButtons(
                onBack = {},
                onForward = {},
                backEnabled = true,
                forwardEnabled = false,
            )
        },
        title = {
            Text("Documents")
        },
        actions = {
            // View mode group
            TitleBarButtonGroup {
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideLayoutGrid, modifier = Modifier.size(14.dp))
                }
                TitleBarGroupDivider()
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideList, modifier = Modifier.size(14.dp))
                }
                TitleBarGroupDivider()
                TitleBarGroupButton(onClick = {}) {
                    Icon(LucideChevronDown, modifier = Modifier.size(10.dp))
                }
            }

            // Action buttons
            IconButton(onClick = {}) {
                Icon(LucideShare2, modifier = Modifier.size(14.dp))
            }
            IconButton(onClick = {}) {
                Icon(LucideTag, modifier = Modifier.size(14.dp))
            }
            IconButton(onClick = {}) {
                Icon(LucideEllipsis, modifier = Modifier.size(14.dp))
            }

            // Expandable search
            ToolbarSearchField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                expanded = searchExpanded,
                onExpandedChange = { searchExpanded = it },
                expandedWidth = 220.dp,
                suggestions = {
                    SearchSuggestionHeader("File names")
                    SearchSuggestionItem(onClick = {}) {
                        Text("Name contains \"$searchQuery\"")
                    }
                    SearchSuggestionSeparator()
                    SearchSuggestionHeader("Content")
                    SearchSuggestionItem(onClick = {}) {
                        Text("Contains \"$searchQuery\"")
                    }
                    SearchSuggestionSeparator()
                    SearchSuggestionHeader("Types")
                    SearchSuggestionItem(onClick = {}) { Text("SVG Document") }
                    SearchSuggestionItem(onClick = {}) { Text("Source Code") }
                    SearchSuggestionItem(onClick = {}) { Text("PDF Document") }
                },
            )
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
        ExampleCard(
            title = "Finder",
            description = "Finder-style toolbar: navigation, title, view mode group, actions, and expandable search with suggestions",
            sourceCode = GallerySources.TitleBarFinderExample,
        ) { TitleBarFinderExample() }
    }
}
