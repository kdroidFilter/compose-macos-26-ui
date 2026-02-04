package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.kdroidfilter.darwinui.components.input.DarwinSearchField
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.CodeBlock
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("SearchInput", "Default")
@Composable
fun SearchInputDefaultExample() {
    var query by remember { mutableStateOf("") }
    DarwinSearchField(value = query, onValueChange = { query = it }, placeholder = "Search...", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("SearchInput", "With Label")
@Composable
fun SearchInputWithLabelExample() {
    var query by remember { mutableStateOf("") }
    DarwinSearchField(
        value = query,
        onValueChange = { query = it },
        placeholder = "Search components...",
        label = "Search",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@Composable
internal fun SearchInputPage() {
    GalleryPage("Search Input", "A text input with a built-in search icon.") {
        SectionHeader("Usage")
        CodeBlock("""var query by remember { mutableStateOf("") }
DarwinSearchField(
    value = query,
    onValueChange = { query = it },
    placeholder = "Search...",
)""")

        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.SearchInputDefaultExample) { SearchInputDefaultExample() }
        ExampleCard(title = "With Label", sourceCode = GallerySources.SearchInputWithLabelExample) { SearchInputWithLabelExample() }
    }
}
