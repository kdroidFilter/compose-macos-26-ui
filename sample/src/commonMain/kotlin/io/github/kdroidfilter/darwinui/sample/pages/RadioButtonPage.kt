package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.RadioButton
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader

@Composable
private fun RadioButtonPreview() {
    var selected by remember { mutableStateOf("Option 1") }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        RadioButton(selected = selected == "Option 1", onClick = { selected = "Option 1" }, label = "Option 1")
        RadioButton(selected = selected == "Option 2", onClick = { selected = "Option 2" }, label = "Option 2")
        RadioButton(selected = selected == "Option 3", onClick = { selected = "Option 3" }, label = "Option 3")
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

@Composable
internal fun RadioButtonPage() {
    GalleryPage("RadioButton", "A control that allows the user to select a single option from a set.") {
        PreviewContainer { RadioButtonPreview() }

        SectionHeader("Examples")
        ExampleCard(
            title = "Basic Usage",
            description = "Selected, unselected, and disabled states",
            sourceCode = "" 
        ) { RadioButtonBasicExample() }
    }
}
