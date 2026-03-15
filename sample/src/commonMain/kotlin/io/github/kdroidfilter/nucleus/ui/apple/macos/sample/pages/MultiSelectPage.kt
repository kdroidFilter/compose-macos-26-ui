package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.MenuPlacement
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.MultiSelectComboBox
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("MultiSelect", "Sizes")
@Composable
fun MultiSelectSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = MacosTheme.typography.caption1,
                        color = MacosTheme.colorScheme.textSecondary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    var selected by remember { mutableStateOf(listOf(0, 1)) }
                    MultiSelectComboBox(
                        items = listOf("React", "Vue", "Angular", "Svelte"),
                        selectedIndices = selected,
                        onSelectionChange = { selected = it },
                        showTags = false,
                    )
                }
            }
        }
    }
}

@GalleryExample("MultiSelect", "Default")
@Composable
fun MultiSelectDefaultExample() {
    val items = listOf("React", "Vue", "Angular", "Svelte", "SolidJS")
    var selected by remember { mutableStateOf(emptyList<Int>()) }
    MultiSelectComboBox(
        items = items,
        selectedIndices = selected,
        onSelectionChange = { selected = it },
        header = "Technologies",
        placeholder = "Pick frameworks",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("MultiSelect", "Pre-selected")
@Composable
fun MultiSelectPreselectedExample() {
    val items = listOf("React", "Vue", "Angular", "Svelte", "SolidJS")
    var selected by remember { mutableStateOf(listOf(0, 1)) }
    MultiSelectComboBox(
        items = items,
        selectedIndices = selected,
        onSelectionChange = { selected = it },
        header = "Favorites",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("MultiSelect", "Disabled")
@Composable
fun MultiSelectDisabledExample() {
    MultiSelectComboBox(
        items = listOf("React", "Vue", "Angular", "Svelte"),
        selectedIndices = listOf(0, 2),
        onSelectionChange = {},
        disabled = true,
    )
}

@GalleryExample("MultiSelect", "Placement")
@Composable
fun MultiSelectPlacementExample() {
    val items = listOf("React", "Vue", "Angular", "Svelte")
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (placement in MenuPlacement.entries) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = placement.name,
                    style = MacosTheme.typography.caption1,
                    color = MacosTheme.colorScheme.textSecondary,
                    modifier = Modifier.widthIn(min = 100.dp),
                )
                var selected by remember { mutableStateOf(listOf(0)) }
                MultiSelectComboBox(
                    items = items,
                    selectedIndices = selected,
                    onSelectionChange = { selected = it },
                    placement = placement,
                    showTags = false,
                )
            }
        }
    }
}

@Composable
internal fun MultiSelectPage() {
    GalleryPage("Multi Select", "Allows selecting multiple options from a list.") {
        SectionHeader("Sizes")
        ExampleCard(
            title = "All Sizes",
            description = "MultiSelect at each ControlSize level",
            sourceCode = GallerySources.MultiSelectSizesExample,
        ) { MultiSelectSizesExample() }
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.MultiSelectDefaultExample) { MultiSelectDefaultExample() }
        ExampleCard(title = "Pre-selected", sourceCode = GallerySources.MultiSelectPreselectedExample) { MultiSelectPreselectedExample() }
        ExampleCard(title = "Disabled", sourceCode = GallerySources.MultiSelectDisabledExample) { MultiSelectDisabledExample() }
        SectionHeader("Placement")
        ExampleCard(
            title = "Menu Placement",
            description = "Control where the dropdown appears relative to the trigger",
            sourceCode = GallerySources.MultiSelectPlacementExample,
        ) { MultiSelectPlacementExample() }
    }
}
