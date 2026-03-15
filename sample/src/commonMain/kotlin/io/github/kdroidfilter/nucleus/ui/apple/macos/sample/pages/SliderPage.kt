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
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Slider
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("Slider", "Sizes")
@Composable
fun SliderSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = MacosTheme.typography.caption1,
                        color = MacosTheme.colorScheme.textTertiary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    var value by remember { mutableStateOf(50f) }
                    Slider(
                        value = value,
                        onValueChange = { value = it },
                        valueRange = 0f..100f,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

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
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textTertiary,
            )
            Text(
                text = "${value.toInt()}%",
                style = MacosTheme.typography.caption1,
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
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textTertiary,
        )
        Slider(value = value, onValueChange = { value = it }, valueRange = 0f..100f, showValue = true)
    }
}

@GalleryExample("Slider", "Surface")
@Composable
fun SliderSurfaceExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Content Area (default)
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
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var value by remember { mutableStateOf(50f) }
                            Slider(value = value, onValueChange = { value = it }, valueRange = 0f..100f)
                        }
                    }
                }
            }
        }

        // Over Glass
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
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var value by remember { mutableStateOf(50f) }
                            Slider(value = value, onValueChange = { value = it }, valueRange = 0f..100f)
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun SliderPage() {
    GalleryPage("Slider", "An input where the user selects a value from within a given range.") {
        SectionHeader("Sizes")
        ExampleCard(title = "All Sizes", sourceCode = GallerySources.SliderSizesExample) { SliderSizesExample() }
        SectionHeader("Surface Appearance")
        ExampleCard(title = "Content Area vs Over Glass", sourceCode = GallerySources.SliderSurfaceExample) { SliderSurfaceExample() }
        SectionHeader("Examples")
        ExampleCard(title = "Volume", sourceCode = GallerySources.SliderVolumeExample) { SliderVolumeExample() }
        ExampleCard(title = "With Value Display", sourceCode = GallerySources.SliderWithValueExample) { SliderWithValueExample() }
    }
}
