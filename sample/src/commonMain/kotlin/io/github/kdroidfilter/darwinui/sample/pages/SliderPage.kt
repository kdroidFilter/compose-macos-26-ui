package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Slider
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Slider", "Volume")
@Composable
fun SliderVolumeExample() {
    var value by remember { mutableStateOf(50f) }
    Column(modifier = Modifier.fillMaxWidth(0.5f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Volume",
                style = DarwinTheme.typography.caption1,
                color = DarwinTheme.colorScheme.textTertiary,
            )
            Text(
                text = "${value.toInt()}%",
                style = DarwinTheme.typography.caption1,
                color = Color(0xFF60A5FA), // blue-400
            )
        }
        Slider(value = value, onValueChange = { value = it }, valueRange = 0f..100f)
    }
}

@GalleryExample("Slider", "With Value Display")
@Composable
fun SliderWithValueExample() {
    var value by remember { mutableStateOf(50f) }
    Column(modifier = Modifier.fillMaxWidth(0.5f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "With value display",
            style = DarwinTheme.typography.caption1,
            color = DarwinTheme.colorScheme.textTertiary,
        )
        Slider(value = value, onValueChange = { value = it }, valueRange = 0f..100f, showValue = true)
    }
}

@Composable
internal fun SliderPage() {
    GalleryPage("Slider", "An input where the user selects a value from within a given range.") {
        SectionHeader("Examples")
        ExampleCard(title = "Volume", sourceCode = GallerySources.SliderVolumeExample) { SliderVolumeExample() }
        ExampleCard(title = "With Value Display", sourceCode = GallerySources.SliderWithValueExample) { SliderWithValueExample() }
    }
}
