package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.RadioButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Radio Button", "Group")
@Composable
fun RadioButtonGroupExample() {
    val options = listOf("Option A", "Option B", "Option C")
    var selected by remember { mutableStateOf("Option A") }

    Column(
        modifier = Modifier.selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                RadioButton(
                    selected = selected == option,
                    onClick = { selected = option },
                )
                Text(option)
            }
        }
    }
}

@GalleryExample("Radio Button", "Disabled")
@Composable
fun RadioButtonDisabledExample() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            RadioButton(selected = true, onClick = {}, enabled = false)
            Text("Selected (disabled)")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            RadioButton(selected = false, onClick = {}, enabled = false)
            Text("Unselected (disabled)")
        }
    }
}

@Composable
internal fun RadioButtonPage() {
    GalleryPage("Radio Button", "Allows the user to select one option from a set.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Group",
            description = "Single-select radio button group",
            sourceCode = GallerySources.RadioButtonGroupExample,
        ) { RadioButtonGroupExample() }
        ExampleCard(
            title = "Disabled",
            description = "Disabled selected and unselected states",
            sourceCode = GallerySources.RadioButtonDisabledExample,
        ) { RadioButtonDisabledExample() }
    }
}
