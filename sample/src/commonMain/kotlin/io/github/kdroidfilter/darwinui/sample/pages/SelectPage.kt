package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.github.kdroidfilter.darwinui.components.select.DarwinSelect
import io.github.kdroidfilter.darwinui.components.select.DarwinSelectOption
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Select", "Default")
@Composable
fun SelectDefaultExample() {
    val options = listOf(
        DarwinSelectOption("react", "React"),
        DarwinSelectOption("vue", "Vue"),
        DarwinSelectOption("angular", "Angular"),
        DarwinSelectOption("svelte", "Svelte"),
    )
    var selected by remember { mutableStateOf<String?>(null) }
    DarwinSelect(
        options = options,
        selectedValue = selected,
        onValueChange = { selected = it },
        label = "Framework",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("Select", "Pre-selected")
@Composable
fun SelectPreselectedExample() {
    val options = listOf(
        DarwinSelectOption("react", "React"),
        DarwinSelectOption("vue", "Vue"),
        DarwinSelectOption("angular", "Angular"),
        DarwinSelectOption("svelte", "Svelte"),
    )
    var selected by remember { mutableStateOf<String?>("react") }
    DarwinSelect(
        options = options,
        selectedValue = selected,
        onValueChange = { selected = it },
        label = "Default Framework",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@Composable
internal fun SelectPage() {
    GalleryPage("Select", "Displays a list of options for the user to pick from.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.SelectDefaultExample) { SelectDefaultExample() }
        ExampleCard(title = "Pre-selected", sourceCode = GallerySources.SelectPreselectedExample) { SelectPreselectedExample() }
    }
}
