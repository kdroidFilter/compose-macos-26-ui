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
import io.github.kdroidfilter.darwinui.components.CircularSlider
import io.github.kdroidfilter.darwinui.components.CircularSliderSize
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
                style = DarwinTheme.typography.bodySmall,
                color = DarwinTheme.colors.textTertiary,
            )
            Text(
                text = "${value.toInt()}%",
                style = DarwinTheme.typography.bodySmall,
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
            style = DarwinTheme.typography.bodySmall,
            color = DarwinTheme.colors.textTertiary,
        )
        Slider(value = value, onValueChange = { value = it }, valueRange = 0f..100f, showValue = true)
    }
}

@GalleryExample("Slider", "Circular Knob")
@Composable
fun SliderCircularKnobExample() {
    var value by remember { mutableStateOf(0.35f) }
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CircularSlider(
            value = value,
            onValueChange = { value = it },
            size = CircularSliderSize.Large,
        )
        Text(
            text = "${(value * 100).toInt()}%",
            style = DarwinTheme.typography.bodySmall,
            color = DarwinTheme.colors.textTertiary,
        )
    }
}

@GalleryExample("Slider", "Circular Sizes")
@Composable
fun SliderCircularSizesExample() {
    var small by remember { mutableStateOf(0.25f) }
    var medium by remember { mutableStateOf(0.5f) }
    var large by remember { mutableStateOf(0.75f) }
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CircularSlider(value = small, onValueChange = { small = it }, size = CircularSliderSize.Small)
            Text("S", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CircularSlider(value = medium, onValueChange = { medium = it }, size = CircularSliderSize.Medium)
            Text("M", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CircularSlider(value = large, onValueChange = { large = it }, size = CircularSliderSize.Large)
            Text("L", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
        }
    }
}

@Composable
internal fun SliderPage() {
    GalleryPage("Slider", "An input where the user selects a value from within a given range.") {
        SectionHeader("Linear")
        ExampleCard(title = "Volume", sourceCode = GallerySources.SliderVolumeExample) { SliderVolumeExample() }
        ExampleCard(title = "With Value Display", sourceCode = GallerySources.SliderWithValueExample) { SliderWithValueExample() }

        SectionHeader("Circular")
        ExampleCard(
            title = "Circular Knob",
            description = "macOS-style circular slider with rotating tick indicator",
            sourceCode = GallerySources.SliderCircularKnobExample,
        ) { SliderCircularKnobExample() }
        ExampleCard(
            title = "Sizes",
            description = "Small (32dp), Medium (48dp), and Large (64dp) presets",
            sourceCode = GallerySources.SliderCircularSizesExample,
        ) { SliderCircularSizesExample() }
    }
}
