package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.checkbox.DarwinCheckbox
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@Composable
private fun CheckboxPreview() {
    var c1 by remember { mutableStateOf(false) }
    var c2 by remember { mutableStateOf(true) }
    var c3 by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        DarwinCheckbox(checked = c1, onCheckedChange = { c1 = it }, label = "Unchecked option")
        DarwinCheckbox(checked = c2, onCheckedChange = { c2 = it }, label = "Checked option")
        DarwinCheckbox(checked = c3, onCheckedChange = { c3 = it }, indeterminate = !c3, label = "Indeterminate")
    }
}

@GalleryExample("Checkbox", "States")
@Composable
fun CheckboxStatesExample() {
    var checked1 by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinCheckbox(checked = checked1, onCheckedChange = { checked1 = it }, label = "Accept terms")
        DarwinCheckbox(checked = checked2, onCheckedChange = { checked2 = it }, label = "Enabled and checked")
        DarwinCheckbox(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false)
        DarwinCheckbox(checked = true, onCheckedChange = {}, indeterminate = true, label = "Indeterminate")
    }
}

@GalleryExample("Checkbox", "Select All")
@Composable
fun CheckboxSelectAllExample() {
    var items by remember { mutableStateOf(listOf(true, false, true)) }
    val allChecked = items.all { it }
    val someChecked = items.any { it } && !allChecked

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinCheckbox(
            checked = allChecked,
            onCheckedChange = { checked -> items = List(3) { checked } },
            indeterminate = someChecked,
            label = "Select all",
        )
        Column(
            modifier = Modifier.padding(start = 24.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items.forEachIndexed { i, checked ->
                DarwinCheckbox(
                    checked = checked,
                    onCheckedChange = { newVal ->
                        items = items.toMutableList().also { it[i] = newVal }
                    },
                    label = "Item ${i + 1}",
                )
            }
        }
    }
}

@Composable
internal fun CheckboxPage() {
    GalleryPage("Checkbox", "A control that allows the user to toggle between checked and unchecked.") {
        PreviewContainer { CheckboxPreview() }

        SectionHeader("Examples")
        ExampleCard(
            title = "States",
            description = "Checked, unchecked, indeterminate, and disabled",
            sourceCode = GallerySources.CheckboxStatesExample,
        ) { CheckboxStatesExample() }
        ExampleCard(
            title = "Select All",
            description = "Parent checkbox with indeterminate state",
            sourceCode = GallerySources.CheckboxSelectAllExample,
        ) { CheckboxSelectAllExample() }
    }
}
