package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColorWell
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColorWellDefaults
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.PreviewContainer
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources

@Composable
private fun ColorWellPreview() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        ColorWell(color = null, onClick = {})
        ColorWell(color = Color(0xFF3B82F6), onClick = {})
        ColorWell(color = Color(0xFF22C55E), onClick = {})
        ColorWell(color = Color(0xFFEF4444), onClick = {})
        ColorWell(color = Color(0xFFF97316), onClick = {})
        ColorWell(color = Color(0xFF8B5CF6), onClick = {})
    }
}

@GalleryExample("ColorWell", "Wheel")
@Composable
fun ColorWellWheelExample() {
    ColorWell(color = null, onClick = {})
}

@GalleryExample("ColorWell", "Solid Color")
@Composable
fun ColorWellSolidExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ColorWell(color = Color(0xFF3B82F6), onClick = {})
        ColorWell(color = Color(0xFF22C55E), onClick = {})
        ColorWell(color = Color(0xFFEF4444), onClick = {})
    }
}

@GalleryExample("ColorWell", "Stateful")
@Composable
fun ColorWellStatefulExample() {
    val palette = listOf(
        Color(0xFF3B82F6),
        Color(0xFF22C55E),
        Color(0xFFEF4444),
        Color(0xFFF97316),
        Color(0xFF8B5CF6),
        Color(0xFFEC4899),
    )
    var selected by remember { mutableStateOf<Color?>(null) }
    val defaultColors = ColorWellDefaults.colors()
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ColorWell(color = selected, onClick = { selected = if (selected == null) palette.first() else null })
        palette.forEach { color ->
            ColorWell(
                color = color,
                onClick = { selected = color },
                colors = if (selected == color) {
                    ColorWellDefaults.colors(borderColor = color.copy(alpha = 0.8f))
                } else {
                    defaultColors
                },
            )
        }
    }
}

@GalleryExample("ColorWell", "Disabled")
@Composable
fun ColorWellDisabledExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ColorWell(color = null, onClick = {}, enabled = false)
        ColorWell(color = Color(0xFF3B82F6), onClick = {}, enabled = false)
    }
}

@Composable
internal fun ColorWellPage() {
    GalleryPage("Color Well", "A circular control that displays a selected color and opens a color picker on tap.") {
        PreviewContainer { ColorWellPreview() }

        SectionHeader("Examples")
        ExampleCard(
            title = "Hue Wheel",
            description = "No color selected — shows the full hue spectrum.",
            sourceCode = GallerySources.ColorWellWheelExample,
        ) { ColorWellWheelExample() }
        ExampleCard(
            title = "Solid Color",
            description = "Displays the currently selected color.",
            sourceCode = GallerySources.ColorWellSolidExample,
        ) { ColorWellSolidExample() }
        ExampleCard(
            title = "Stateful",
            description = "Tap a swatch to select it; the well reflects the active color.",
            sourceCode = GallerySources.ColorWellStatefulExample,
        ) { ColorWellStatefulExample() }
        ExampleCard(
            title = "Disabled",
            description = "Disabled state — non-interactive with reduced opacity.",
            sourceCode = GallerySources.ColorWellDisabledExample,
        ) { ColorWellDisabledExample() }
    }
}
