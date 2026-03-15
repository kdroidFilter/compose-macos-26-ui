package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

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
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CircularSlider
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("Circular Slider", "Sizes")
@Composable
fun CircularSliderSizesExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    var value by remember { mutableStateOf(0.5f) }
                    CircularSlider(value = value, onValueChange = { value = it })
                    Text(
                        size.name,
                        style = MacosTheme.typography.caption2,
                        color = MacosTheme.colorScheme.textTertiary,
                    )
                }
            }
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
        ControlSize(ControlSize.Regular) {
            CircularSlider(value = 0.4f, onValueChange = {}, enabled = false)
        }
        ControlSize(ControlSize.Large) {
            CircularSlider(value = 0.75f, onValueChange = {}, enabled = false)
        }
    }
}

@Composable
internal fun CircularSliderPage() {
    GalleryPage("Circular Slider", "A circular dial slider for value selection.") {
        SectionHeader("Sizes")
        ExampleCard(
            title = "All Sizes",
            description = "Circular slider at each ControlSize level",
            sourceCode = GallerySources.CircularSliderSizesExample,
        ) { CircularSliderSizesExample() }
        ExampleCard(
            title = "Disabled",
            description = "Circular sliders in disabled state",
            sourceCode = GallerySources.CircularSliderDisabledExample,
        ) { CircularSliderDisabledExample() }
    }
}
