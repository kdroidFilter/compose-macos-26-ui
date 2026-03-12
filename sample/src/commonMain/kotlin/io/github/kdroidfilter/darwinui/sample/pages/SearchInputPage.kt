package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.kdroidfilter.darwinui.components.AddressBar
import io.github.kdroidfilter.darwinui.components.SearchField
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideSearch
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp

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
    }
}
