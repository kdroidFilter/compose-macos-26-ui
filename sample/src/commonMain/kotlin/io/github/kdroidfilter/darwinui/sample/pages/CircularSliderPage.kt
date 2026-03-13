package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.CircularSlider
import io.github.kdroidfilter.darwinui.components.CircularSliderSize
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Circular Slider", "Sizes")
@Composable
fun CircularSliderSizesExample() {
    var small by remember { mutableStateOf(0.3f) }
    var medium by remember { mutableStateOf(0.5f) }
    var large by remember { mutableStateOf(0.7f) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CircularSlider(value = small, onValueChange = { small = it }, size = CircularSliderSize.Small)
            Text("Small", style = DarwinTheme.typography.caption2, color = DarwinTheme.colorScheme.textTertiary)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CircularSlider(value = medium, onValueChange = { medium = it }, size = CircularSliderSize.Medium)
            Text("Medium", style = DarwinTheme.typography.caption2, color = DarwinTheme.colorScheme.textTertiary)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            CircularSlider(value = large, onValueChange = { large = it }, size = CircularSliderSize.Large)
            Text("Large", style = DarwinTheme.typography.caption2, color = DarwinTheme.colorScheme.textTertiary)
        }
    }
}

@GalleryExample("Circular Slider", "Disabled")
@Composable
fun CircularSliderDisabledExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircularSlider(value = 0.4f, onValueChange = {}, enabled = false)
        CircularSlider(value = 0.75f, onValueChange = {}, enabled = false, size = CircularSliderSize.Large)
    }
}

@Composable
internal fun CircularSliderPage() {
    GalleryPage("Circular Slider", "A circular dial slider for value selection.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Sizes",
            description = "Small, medium, and large circular sliders",
            sourceCode = GallerySources.CircularSliderSizesExample,
        ) { CircularSliderSizesExample() }
        ExampleCard(
            title = "Disabled",
            description = "Circular sliders in disabled state",
            sourceCode = GallerySources.CircularSliderDisabledExample,
        ) { CircularSliderDisabledExample() }
    }
}
