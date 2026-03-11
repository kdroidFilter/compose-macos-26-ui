package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Button
import io.github.kdroidfilter.darwinui.components.ElevatedButton
import io.github.kdroidfilter.darwinui.components.FilledTonalButton
import io.github.kdroidfilter.darwinui.components.OutlinedButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TextButton
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("M3 Buttons", "Variants")
@Composable
fun M3ButtonVariantsExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(onClick = {}) { Text("Filled") }
        OutlinedButton(onClick = {}) { Text("Outlined") }
        TextButton(onClick = {}) { Text("Text") }
        ElevatedButton(onClick = {}) { Text("Elevated") }
        FilledTonalButton(onClick = {}) { Text("Filled Tonal") }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("M3 Buttons", "Disabled")
@Composable
fun M3ButtonDisabledExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(onClick = {}, enabled = false) { Text("Filled") }
        OutlinedButton(onClick = {}, enabled = false) { Text("Outlined") }
        TextButton(onClick = {}, enabled = false) { Text("Text") }
        ElevatedButton(onClick = {}, enabled = false) { Text("Elevated") }
        FilledTonalButton(onClick = {}, enabled = false) { Text("Filled Tonal") }
    }
}

@Composable
internal fun M3ButtonsPage() {
    GalleryPage("M3 Buttons", "Material 3-aligned button variants: Filled, Outlined, Text, Elevated, and FilledTonal.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Variants",
            description = "All M3-style button variants",
            sourceCode = GallerySources.M3ButtonVariantsExample,
        ) { M3ButtonVariantsExample() }
        ExampleCard(
            title = "Disabled",
            description = "Disabled state for each variant",
            sourceCode = GallerySources.M3ButtonDisabledExample,
        ) { M3ButtonDisabledExample() }
    }
}
