package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColorGrid
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColorPickerDot
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColorWell
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColorWellStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.OpacitySlider
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Popover
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.PreviewContainer
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalControlSize

private val Green = Color(0xFF34C759)
private val Blue = Color(0xFF3B82F6)
private val Red = Color(0xFFEF4444)
private val Orange = Color(0xFFF97316)
private val Purple = Color(0xFF8B5CF6)

private val AllSizes = listOf(
    ControlSize.Mini,
    ControlSize.Small,
    ControlSize.Regular,
    ControlSize.Large,
    ControlSize.ExtraLarge,
)

// =========================================================================
// Preview
// =========================================================================

@Composable
private fun ColorWellPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ColorWell(color = Green, onClick = {}, selected = true)
            ColorWell(color = Blue, onClick = {})
            ColorWell(color = Red, onClick = {})
            ColorWell(color = Orange, onClick = {})
            ColorWell(color = Purple, onClick = {})
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ColorWell(color = Green, onClick = {}, selected = true, style = ColorWellStyle.Minimal)
            ColorWell(color = Blue, onClick = {}, style = ColorWellStyle.Minimal)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ColorWell(color = Green, onClick = {}, selected = true, style = ColorWellStyle.Expanded)
        }
    }
}

// =========================================================================
// Color Well examples
// =========================================================================

@GalleryExample("ColorWell", "Standard")
@Composable
fun ColorWellStandardExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ColorWell(color = Blue, onClick = {}, selected = true)
        ColorWell(color = Green, onClick = {})
        ColorWell(color = Red, onClick = {}, enabled = false)
    }
}

@GalleryExample("ColorWell", "Minimal")
@Composable
fun ColorWellMinimalExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ColorWell(color = Blue, onClick = {}, selected = true, style = ColorWellStyle.Minimal)
        ColorWell(color = Green, onClick = {}, style = ColorWellStyle.Minimal)
        ColorWell(color = Red, onClick = {}, style = ColorWellStyle.Minimal, enabled = false)
    }
}

@GalleryExample("ColorWell", "Expanded")
@Composable
fun ColorWellExpandedExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        ColorWell(color = Green, onClick = {}, selected = true, style = ColorWellStyle.Expanded)
        ColorWell(color = Blue, onClick = {}, style = ColorWellStyle.Expanded)
    }
}

@GalleryExample("ColorWell", "Sizes")
@Composable
fun ColorWellSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        AllSizes.forEach { size ->
            CompositionLocalProvider(LocalControlSize provides size) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ColorWell(color = Green, onClick = {}, selected = true)
                    ColorWell(
                        color = Green,
                        onClick = {},
                        selected = true,
                        style = ColorWellStyle.Expanded,
                    )
                }
            }
        }
    }
}

@GalleryExample("ColorWell", "Stateful")
@Composable
fun ColorWellStatefulExample() {
    val palette = listOf(Blue, Green, Red, Orange, Purple)
    var selectedColor by remember { mutableStateOf(Blue) }
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        palette.forEach { color ->
            ColorWell(
                color = color,
                onClick = { selectedColor = color },
                selected = selectedColor == color,
            )
        }
    }
}

// =========================================================================
// Color Picker examples
// =========================================================================

@GalleryExample("ColorPicker", "Picker Dot")
@Composable
fun ColorPickerDotExample() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AllSizes.forEach { size ->
                CompositionLocalProvider(LocalControlSize provides size) {
                    ColorPickerDot(color = null, onClick = {})
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AllSizes.forEach { size ->
                CompositionLocalProvider(LocalControlSize provides size) {
                    ColorPickerDot(color = Blue, onClick = {})
                }
            }
        }
    }
}

@GalleryExample("ColorPicker", "Color Grid")
@Composable
fun ColorPickerGridExample() {
    var color by remember { mutableStateOf<Color?>(null) }
    ColorGrid(
        selectedColor = color,
        onColorSelected = { color = it },
    )
}

@GalleryExample("ColorPicker", "Opacity Slider")
@Composable
fun ColorPickerOpacityExample() {
    var opacity by remember { mutableStateOf(0.75f) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        AllSizes.forEach { size ->
            CompositionLocalProvider(LocalControlSize provides size) {
                OpacitySlider(
                    opacity = opacity,
                    onOpacityChange = { opacity = it },
                    color = Purple,
                )
            }
        }
    }
}

@GalleryExample("ColorPicker", "Picker Popover")
@Composable
fun ColorPickerPopoverExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        AllSizes.forEach { size ->
            CompositionLocalProvider(LocalControlSize provides size) {
                ColorPickerPopover()
            }
        }
    }
}

@Composable
private fun ColorPickerPopover() {
    var color by remember { mutableStateOf(Blue) }
    var opacity by remember { mutableStateOf(1f) }
    var expanded by remember { mutableStateOf(false) }
    Popover(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        trigger = {
            ColorWell(
                color = color.copy(alpha = opacity),
                onClick = { expanded = !expanded },
                selected = expanded,
                style = ColorWellStyle.Minimal,
            )
        },
    ) {
        Column(modifier = Modifier.width(IntrinsicSize.Max).padding(8.dp)) {
            ColorGrid(
                selectedColor = color,
                onColorSelected = { color = it },
            )
            Spacer(Modifier.height(12.dp))
            OpacitySlider(
                opacity = opacity,
                onOpacityChange = { opacity = it },
                color = color,
            )
        }
    }
}

// =========================================================================
// Page
// =========================================================================

@Composable
internal fun ColorWellPage() {
    GalleryPage(
        "Color Wells & Pickers",
        "Color wells display the selected color. " +
            "Color pickers let the user choose a colour from a grid, a hue wheel, or an opacity slider.",
    ) {
        PreviewContainer { ColorWellPreview() }

        SectionHeader("Color Wells")
        ExampleCard(
            title = "Standard",
            description = "Bordered swatch inside a subtle container.",
            sourceCode = GallerySources.ColorWellStandardExample,
        ) { ColorWellStandardExample() }
        ExampleCard(
            title = "Minimal",
            description = "Same layout — hover reveals a chevron menu indicator.",
            sourceCode = GallerySources.ColorWellMinimalExample,
        ) { ColorWellMinimalExample() }
        ExampleCard(
            title = "Expanded",
            description = "Full-bleed swatch with a trailing color-wheel button.",
            sourceCode = GallerySources.ColorWellExpandedExample,
        ) { ColorWellExpandedExample() }
        ExampleCard(
            title = "Stateful",
            description = "Tap a swatch to select it — the active one gets a darker background.",
            sourceCode = GallerySources.ColorWellStatefulExample,
        ) { ColorWellStatefulExample() }

        SectionHeader("Color Pickers")
        ExampleCard(
            title = "Picker Dot",
            description = "Circular picker button — hue wheel or selected color ring.",
            sourceCode = GallerySources.ColorPickerDotExample,
        ) { ColorPickerDotExample() }
        ExampleCard(
            title = "Color Grid",
            description = "12 x 10 swatch grid. Click to select a colour.",
            sourceCode = GallerySources.ColorPickerGridExample,
        ) { ColorPickerGridExample() }
        ExampleCard(
            title = "Opacity Slider",
            description = "Checkerboard-to-color gradient track with percentage field.",
            sourceCode = GallerySources.ColorPickerOpacityExample,
        ) { ColorPickerOpacityExample() }
        ExampleCard(
            title = "Picker Popover",
            description = "Click the well to open a color grid + opacity popover.",
            sourceCode = GallerySources.ColorPickerPopoverExample,
        ) { ColorPickerPopoverExample() }
    }
}
