package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.AddressBar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SearchField
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SearchSuggestionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SearchSuggestionItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SearchSuggestionSeparator
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ToolbarSearchField
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSearch
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources

@GalleryExample("SearchInput", "Sizes")
@Composable
fun SearchInputSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(0.6f),
                ) {
                    Text(
                        text = size.name,
                        style = MacosTheme.typography.caption1,
                        color = MacosTheme.colorScheme.textSecondary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    var query by remember { mutableStateOf("") }
                    SearchField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = "Search...",
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@GalleryExample("SearchInput", "Default")
@Composable
fun SearchInputDefaultExample() {
    var query by remember { mutableStateOf("") }
    SearchField(value = query, onValueChange = { query = it }, placeholder = "Search...", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("SearchInput", "Address Bar")
@Composable
fun SearchInputAddressBarExample() {
    var url by remember { mutableStateOf("apple.com") }
    AddressBar(
        value = url,
        onValueChange = { url = it },
        onGo = {},
        modifier = Modifier.fillMaxWidth(0.7f),
    )
}

@GalleryExample("SearchInput", "Address Bar with Icon")
@Composable
fun SearchInputAddressBarWithIconExample() {
    var url by remember { mutableStateOf("") }
    AddressBar(
        value = url,
        onValueChange = { url = it },
        placeholder = "Search or enter website name",
        onGo = {},
        leadingIcon = { Icon(LucideSearch, modifier = Modifier.size(13.dp)) },
        modifier = Modifier.fillMaxWidth(0.7f),
    )
}

@GalleryExample("SearchInput", "Surface Variants")
@Composable
fun SearchInputSurfaceVariantsExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Content Area (default)
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Content Area",
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textSecondary,
            )
            Surface(Surface.ContentArea) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var text by remember { mutableStateOf("") }
                            SearchField(value = text, onValueChange = { text = it })
                        }
                    }
                    SearchField(value = "With value", onValueChange = {})
                    SearchField(value = "", onValueChange = {}, enabled = false)
                }
            }
        }

        // Over Glass
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Over Glass",
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textSecondary,
            )
            Surface(Surface.OverGlass) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var text by remember { mutableStateOf("") }
                            SearchField(value = text, onValueChange = { text = it })
                        }
                    }
                    SearchField(value = "With value", onValueChange = {})
                    SearchField(value = "", onValueChange = {}, enabled = false)
                }
            }
        }
    }
}

@GalleryExample("SearchInput", "Toolbar Search")
@Composable
fun SearchInputToolbarSearchExample() {
    var query by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text("Click the icon to expand")
        ToolbarSearchField(
            value = query,
            onValueChange = { query = it },
            expanded = expanded,
            onExpandedChange = { expanded = it },
            expandedWidth = 240.dp,
            suggestions = {
                SearchSuggestionHeader("File names")
                SearchSuggestionItem(onClick = {}) { Text("Name contains \"$query\"") }
                SearchSuggestionSeparator()
                SearchSuggestionHeader("Types")
                SearchSuggestionItem(onClick = {}) { Text("PDF Document") }
                SearchSuggestionItem(onClick = {}) { Text("Source Code") }
            },
        )
    }
}

@Composable
internal fun SearchInputPage() {
    GalleryPage("Search Input", "Text inputs designed for search and URL navigation.") {
        SectionHeader("Sizes")
        ExampleCard(
            title = "All Sizes",
            description = "SearchField at each ControlSize level",
            sourceCode = GallerySources.SearchInputSizesExample,
        ) { SearchInputSizesExample() }

        SectionHeader("Search Field")
        ExampleCard(
            title = "Default",
            description = "Pill-shaped search input with magnifier icon",
            sourceCode = GallerySources.SearchInputDefaultExample,
        ) { SearchInputDefaultExample() }

        SectionHeader("Surface Variants")
        ExampleCard(
            title = "Content Area vs Over Glass",
            description = "SearchField adapts its appearance based on Surface",
            sourceCode = GallerySources.SearchInputSurfaceVariantsExample,
        ) { SearchInputSurfaceVariantsExample() }

        SectionHeader("Address Bar")
        ExampleCard(
            title = "Address Bar",
            description = "Safari-style URL bar: pill background, centered text, trailing Go button",
            sourceCode = GallerySources.SearchInputAddressBarExample,
        ) { SearchInputAddressBarExample() }
        ExampleCard(
            title = "With Leading Icon",
            description = "Address bar with a customisable leading icon slot",
            sourceCode = GallerySources.SearchInputAddressBarWithIconExample,
        ) { SearchInputAddressBarWithIconExample() }

        SectionHeader("Toolbar Search")
        ExampleCard(
            title = "Expandable Toolbar Search",
            description = "Finder-style: icon button that expands into a search field with categorized suggestions",
            sourceCode = GallerySources.SearchInputToolbarSearchExample,
        ) { SearchInputToolbarSearchExample() }
    }
}
