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
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.RadioButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.PreviewContainer
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources

@Composable
private fun RadioButtonPreview() {
    var selected by remember { mutableStateOf("Option 1") }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        RadioButton(selected = selected == "Option 1", onClick = { selected = "Option 1" }, label = "Option 1")
        RadioButton(selected = selected == "Option 2", onClick = { selected = "Option 2" }, label = "Option 2")
        RadioButton(selected = selected == "Option 3", onClick = { selected = "Option 3" }, label = "Option 3")
    }
}

@GalleryExample("RadioButton", "Sizes")
@Composable
fun RadioButtonSizesExample() {
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
                    RadioButton(selected = true, onClick = {}, label = "Selected")
                    RadioButton(selected = false, onClick = {}, label = "Unselected")
                }
            }
        }
    }
}

@GalleryExample("RadioButton", "Basic Usage")
@Composable
fun RadioButtonBasicExample() {
    var selectedOption by remember { mutableStateOf(1) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        RadioButton(selected = selectedOption == 1, onClick = { selectedOption = 1 }, label = "First Choice")
        RadioButton(selected = selectedOption == 2, onClick = { selectedOption = 2 }, label = "Second Choice")
        RadioButton(selected = false, onClick = {}, label = "Disabled Option", enabled = false)
        RadioButton(selected = true, onClick = {}, label = "Disabled Selected", enabled = false)
    }
}

@GalleryExample("RadioButton", "Surface")
@Composable
fun RadioButtonSurfaceExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
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
                    var s by remember { mutableStateOf(1) }
                    RadioButton(selected = s == 1, onClick = { s = 1 }, label = "Selected")
                    RadioButton(selected = s == 2, onClick = { s = 2 }, label = "Unselected")
                    RadioButton(selected = false, onClick = {}, label = "Disabled", enabled = false)
                }
            }
        }

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
                    var s by remember { mutableStateOf(1) }
                    RadioButton(selected = s == 1, onClick = { s = 1 }, label = "Selected")
                    RadioButton(selected = s == 2, onClick = { s = 2 }, label = "Unselected")
                    RadioButton(selected = false, onClick = {}, label = "Disabled", enabled = false)
                }
            }
        }
    }
}

@Composable
internal fun RadioButtonPage() {
    GalleryPage("RadioButton", "A control that allows the user to select a single option from a set.") {
        PreviewContainer { RadioButtonPreview() }

        SectionHeader("Sizes")
        ExampleCard(
            title = "All Sizes",
            description = "RadioButton at each ControlSize level",
            sourceCode = GallerySources.RadioButtonSizesExample,
        ) { RadioButtonSizesExample() }

        SectionHeader("Surface Appearance")
        ExampleCard(title = "Content Area vs Over Glass", sourceCode = GallerySources.RadioButtonSurfaceExample) { RadioButtonSurfaceExample() }

        SectionHeader("Examples")
        ExampleCard(
            title = "Basic Usage",
            description = "Selected, unselected, and disabled states",
            sourceCode = GallerySources.RadioButtonBasicExample,
        ) { RadioButtonBasicExample() }
    }
}
