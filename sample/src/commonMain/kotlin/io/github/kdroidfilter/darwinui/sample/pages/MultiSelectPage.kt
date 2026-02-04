package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.kdroidfilter.darwinui.components.select.DarwinMultiSelect
import io.github.kdroidfilter.darwinui.components.select.DarwinSelectOption
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("MultiSelect", "Default")
@Composable
fun MultiSelectDefaultExample() {
    val options = listOf(
        DarwinSelectOption("react", "React"),
        DarwinSelectOption("vue", "Vue"),
        DarwinSelectOption("angular", "Angular"),
        DarwinSelectOption("svelte", "Svelte"),
        DarwinSelectOption("solid", "SolidJS"),
    )
    var selected by remember { mutableStateOf(listOf<String>()) }
    DarwinMultiSelect(
        options = options,
        selectedValues = selected,
        onValuesChange = { selected = it },
        label = "Technologies",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("MultiSelect", "Pre-selected")
@Composable
fun MultiSelectPreselectedExample() {
    val options = listOf(
        DarwinSelectOption("react", "React"),
        DarwinSelectOption("vue", "Vue"),
        DarwinSelectOption("angular", "Angular"),
        DarwinSelectOption("svelte", "Svelte"),
        DarwinSelectOption("solid", "SolidJS"),
    )
    var selected by remember { mutableStateOf(listOf("react", "vue")) }
    DarwinMultiSelect(
        options = options,
        selectedValues = selected,
        onValuesChange = { selected = it },
        label = "Favorites",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@Composable
internal fun MultiSelectPage() {
    GalleryPage("Multi Select", "Allows selecting multiple options from a list.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.MultiSelectDefaultExample) { MultiSelectDefaultExample() }
        ExampleCard(title = "Pre-selected", sourceCode = GallerySources.MultiSelectPreselectedExample) { MultiSelectPreselectedExample() }
    }
}
