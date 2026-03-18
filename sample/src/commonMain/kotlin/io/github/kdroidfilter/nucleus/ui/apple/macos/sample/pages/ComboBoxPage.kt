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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ComboBox
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ComboBoxDefaults
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.MenuPlacement
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("ComboBox", "Sizes")
@Composable
fun ComboBoxSizesExample() {
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
                    var selected by remember { mutableStateOf(0) }
                    ComboBox(
                        items = listOf("Option A", "Option B", "Option C"),
                        selected = selected,
                        onSelectionChange = { index, _ -> selected = index },
                    )
                }
            }
        }
    }
}

@GalleryExample("ComboBox", "Basic")
@Composable
fun ComboBoxBasicExample() {
    var selected by remember { mutableStateOf<Int?>(null) }
    val items = listOf("macOS Sequoia", "macOS Sonoma", "macOS Ventura", "macOS Monterey")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ComboBox(
            items = items,
            selected = selected,
            onSelectionChange = { index, _ -> selected = index },
            placeholder = "Select a version",
        )
        if (selected != null) {
            Text(
                text = "Selected: ${items[selected!!]}",
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textSecondary,
            )
        }
    }
}

@GalleryExample("ComboBox", "With Header")
@Composable
fun ComboBoxHeaderExample() {
    var selected by remember { mutableStateOf<Int?>(0) }

    ComboBox(
        items = listOf("Light", "Dark", "Auto"),
        selected = selected,
        onSelectionChange = { index, _ -> selected = index },
        header = "Appearance",
    )
}

@GalleryExample("ComboBox", "Disabled")
@Composable
fun ComboBoxDisabledExample() {
    ComboBox(
        items = listOf("Option A", "Option B", "Option C"),
        selected = 1,
        onSelectionChange = { _, _ -> },
        disabled = true,
    )
}

@GalleryExample("ComboBox", "Placement")
@Composable
fun ComboBoxPlacementExample() {
    val items = listOf("Light", "Dark", "Auto")
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
                var selected by remember { mutableStateOf(0) }
                ComboBox(
                    items = items,
                    selected = selected,
                    onSelectionChange = { index, _ -> selected = index },
                    placement = placement,
                )
            }
        }
    }
}

@GalleryExample("ComboBox", "Custom Colors")
@Composable
fun ComboBoxCustomColorsExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.widthIn(max = 400.dp).fillMaxWidth()) {
        var s1 by remember { mutableStateOf<Int?>(null) }
        ComboBox(
            items = listOf("Apple", "Banana", "Cherry"),
            selected = s1,
            onSelectionChange = { i, _ -> s1 = i },
            header = "Green accent",
            colors = ComboBoxDefaults.colors(
                selectedItemColor = Color(0xFF34C759),
                selectedItemTextColor = Color.White,
            ),
        )
        var s2 by remember { mutableStateOf<Int?>(null) }
        ComboBox(
            items = listOf("Small", "Medium", "Large"),
            selected = s2,
            onSelectionChange = { i, _ -> s2 = i },
            header = "Custom border & background",
            colors = ComboBoxDefaults.colors(
                backgroundColor = Color(0xFFAF52DE).copy(alpha = 0.08f),
                borderColor = Color(0xFFAF52DE).copy(alpha = 0.30f),
                chevronColor = Color(0xFFAF52DE),
            ),
        )
    }
}

@Composable
internal fun ComboBoxPage() {
    GalleryPage("Combo Box", "A dropdown combo box for selecting from a list of options.") {
        SectionHeader("Sizes")
        ExampleCard(
            title = "All Sizes",
            description = "ComboBox at each ControlSize level",
            sourceCode = GallerySources.ComboBoxSizesExample,
        ) { ComboBoxSizesExample() }
        SectionHeader("Examples")
        ExampleCard(
            title = "Basic",
            description = "Simple combo box with placeholder",
            sourceCode = GallerySources.ComboBoxBasicExample,
        ) { ComboBoxBasicExample() }
        ExampleCard(
            title = "With Header",
            description = "Combo box with a header label",
            sourceCode = GallerySources.ComboBoxHeaderExample,
        ) { ComboBoxHeaderExample() }
        ExampleCard(
            title = "Disabled",
            description = "Combo box in disabled state",
            sourceCode = GallerySources.ComboBoxDisabledExample,
        ) { ComboBoxDisabledExample() }
        SectionHeader("Custom Colors")
        ExampleCard(
            title = "Custom Colors",
            description = "ComboBox with custom selection and border colors",
            sourceCode = GallerySources.ComboBoxCustomColorsExample,
        ) { ComboBoxCustomColorsExample() }
        SectionHeader("Placement")
        ExampleCard(
            title = "Menu Placement",
            description = "Control where the dropdown appears relative to the trigger",
            sourceCode = GallerySources.ComboBoxPlacementExample,
        ) { ComboBoxPlacementExample() }
    }
}
