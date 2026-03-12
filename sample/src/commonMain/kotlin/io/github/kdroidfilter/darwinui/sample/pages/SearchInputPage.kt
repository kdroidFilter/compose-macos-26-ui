package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.AddressBar
import io.github.kdroidfilter.darwinui.components.SearchField
import io.github.kdroidfilter.darwinui.components.SearchSuggestionHeader
import io.github.kdroidfilter.darwinui.components.SearchSuggestionItem
import io.github.kdroidfilter.darwinui.components.SearchSuggestionSeparator
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.ToolbarSearchField
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideSearch
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

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
        SectionHeader("Search Field")
        ExampleCard(
            title = "Default",
            description = "Pill-shaped search input with magnifier icon",
            sourceCode = GallerySources.SearchInputDefaultExample,
        ) { SearchInputDefaultExample() }

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
