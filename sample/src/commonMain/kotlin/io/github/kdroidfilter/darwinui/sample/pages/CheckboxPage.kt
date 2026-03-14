package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.CheckBox
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinSurface
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
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
        CheckBox(checked = c1, onCheckedChange = { c1 = it }, label = "Unchecked option")
        CheckBox(checked = c2, onCheckedChange = { c2 = it }, label = "Checked option")
        CheckBox(checked = c3, onCheckedChange = { c3 = it }, indeterminate = !c3, label = "Indeterminate")
    }
}

@GalleryExample("Checkbox", "Sizes")
@Composable
fun CheckboxSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = DarwinTheme.typography.caption1,
                        color = DarwinTheme.colorScheme.textSecondary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    var c by remember { mutableStateOf(true) }
                    CheckBox(checked = c, onCheckedChange = { c = it }, label = "Checked")
                }
            }
        }
    }
}

@GalleryExample("Checkbox", "States")
@Composable
fun CheckboxStatesExample() {
    var checked1 by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CheckBox(checked = checked1, onCheckedChange = { checked1 = it }, label = "Accept terms")
        CheckBox(checked = checked2, onCheckedChange = { checked2 = it }, label = "Enabled and checked")
        CheckBox(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false)
        CheckBox(checked = true, onCheckedChange = {}, indeterminate = true, label = "Indeterminate")
    }
}

@GalleryExample("Checkbox", "Surface")
@Composable
fun CheckboxSurfaceExample() {
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
                style = DarwinTheme.typography.caption1,
                color = DarwinTheme.colorScheme.textSecondary,
            )
            DarwinSurface(DarwinSurface.ContentArea) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    var c1 by remember { mutableStateOf(true) }
                    var c2 by remember { mutableStateOf(false) }
                    CheckBox(checked = c1, onCheckedChange = { c1 = it }, label = "Checked")
                    CheckBox(checked = c2, onCheckedChange = { c2 = it }, label = "Unchecked")
                    CheckBox(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false)
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
                style = DarwinTheme.typography.caption1,
                color = DarwinTheme.colorScheme.textSecondary,
            )
            DarwinSurface(DarwinSurface.OverGlass) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    var c1 by remember { mutableStateOf(true) }
                    var c2 by remember { mutableStateOf(false) }
                    CheckBox(checked = c1, onCheckedChange = { c1 = it }, label = "Checked")
                    CheckBox(checked = c2, onCheckedChange = { c2 = it }, label = "Unchecked")
                    CheckBox(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false)
                }
            }
        }
    }
}

@GalleryExample("Checkbox", "Select All")
@Composable
fun CheckboxSelectAllExample() {
    var items by remember { mutableStateOf(listOf(true, false, true)) }
    val allChecked = items.all { it }
    val someChecked = items.any { it } && !allChecked

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CheckBox(
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
                CheckBox(
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

        SectionHeader("Sizes")
        ExampleCard(
            title = "All Sizes",
            description = "Checkbox at each ControlSize level",
            sourceCode = GallerySources.CheckboxSizesExample,
        ) { CheckboxSizesExample() }

        SectionHeader("Surface Appearance")
        ExampleCard(title = "Content Area vs Over Glass", sourceCode = GallerySources.CheckboxSurfaceExample) { CheckboxSurfaceExample() }

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
