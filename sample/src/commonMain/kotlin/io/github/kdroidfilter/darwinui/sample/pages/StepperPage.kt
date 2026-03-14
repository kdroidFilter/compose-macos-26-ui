package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Stepper
import io.github.kdroidfilter.darwinui.components.StepperField
import io.github.kdroidfilter.darwinui.components.StepperFieldLayout
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinSurface
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Stepper", "No Field Sizes")
@Composable
fun StepperNoFieldSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = DarwinTheme.typography.caption1,
                        color = DarwinTheme.colorScheme.textTertiary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    var value by remember { mutableIntStateOf(0) }
                    Stepper(
                        onIncrement = { value++ },
                        onDecrement = { value-- },
                    )
                    Text(
                        text = "$value",
                        style = DarwinTheme.typography.body,
                        color = DarwinTheme.colorScheme.textPrimary,
                    )
                }
            }
        }
    }
}

@GalleryExample("Stepper", "Outside Field Sizes")
@Composable
fun StepperOutsideFieldSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = DarwinTheme.typography.caption1,
                        color = DarwinTheme.colorScheme.textTertiary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    var value by remember { mutableIntStateOf(0) }
                    StepperField(
                        value = value.toString(),
                        onValueChange = { value = it.toIntOrNull() ?: value },
                        onIncrement = { value++ },
                        onDecrement = { value-- },
                        layout = StepperFieldLayout.Outside,
                        modifier = Modifier.widthIn(max = 180.dp),
                    )
                }
            }
        }
    }
}

@GalleryExample("Stepper", "Inside Field Sizes")
@Composable
fun StepperInsideFieldSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = DarwinTheme.typography.caption1,
                        color = DarwinTheme.colorScheme.textTertiary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    var value by remember { mutableIntStateOf(0) }
                    StepperField(
                        value = value.toString(),
                        onValueChange = { value = it.toIntOrNull() ?: value },
                        onIncrement = { value++ },
                        onDecrement = { value-- },
                        layout = StepperFieldLayout.Inside,
                        modifier = Modifier.widthIn(max = 180.dp),
                    )
                }
            }
        }
    }
}

@GalleryExample("Stepper", "Surface")
@Composable
fun StepperSurfaceExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
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
                    var v1 by remember { mutableIntStateOf(0) }
                    StepperField(
                        value = v1.toString(),
                        onValueChange = { v1 = it.toIntOrNull() ?: v1 },
                        onIncrement = { v1++ },
                        onDecrement = { v1-- },
                        layout = StepperFieldLayout.Outside,
                    )
                    StepperField(
                        value = "10",
                        onValueChange = {},
                        onIncrement = {},
                        onDecrement = {},
                        layout = StepperFieldLayout.Outside,
                        enabled = false,
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
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
                    var v1 by remember { mutableIntStateOf(0) }
                    StepperField(
                        value = v1.toString(),
                        onValueChange = { v1 = it.toIntOrNull() ?: v1 },
                        onIncrement = { v1++ },
                        onDecrement = { v1-- },
                        layout = StepperFieldLayout.Outside,
                    )
                    StepperField(
                        value = "10",
                        onValueChange = {},
                        onIncrement = {},
                        onDecrement = {},
                        layout = StepperFieldLayout.Outside,
                        enabled = false,
                    )
                }
            }
        }
    }
}

@GalleryExample("Stepper", "States")
@Composable
fun StepperStatesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var value by remember { mutableIntStateOf(5) }
            Stepper(onIncrement = { value++ }, onDecrement = { value-- })
            Text("Enabled ($value)", style = DarwinTheme.typography.body, color = DarwinTheme.colorScheme.textPrimary)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Stepper(onIncrement = {}, onDecrement = {}, enabled = false)
            Text("Disabled", style = DarwinTheme.typography.body, color = DarwinTheme.colorScheme.textSecondary)
        }
    }
}

@Composable
internal fun StepperPage() {
    GalleryPage("Stepper", "A control that increments or decrements a value.") {
        SectionHeader("No Field")
        ExampleCard(title = "All Sizes", sourceCode = GallerySources.StepperNoFieldSizesExample) { StepperNoFieldSizesExample() }

        SectionHeader("Outside Field")
        ExampleCard(title = "All Sizes", sourceCode = GallerySources.StepperOutsideFieldSizesExample) { StepperOutsideFieldSizesExample() }

        SectionHeader("Inside Field")
        ExampleCard(title = "All Sizes", sourceCode = GallerySources.StepperInsideFieldSizesExample) { StepperInsideFieldSizesExample() }

        SectionHeader("Surface Appearance")
        ExampleCard(title = "Content Area vs Over Glass", sourceCode = GallerySources.StepperSurfaceExample) { StepperSurfaceExample() }

        SectionHeader("States")
        ExampleCard(title = "Enabled & Disabled", sourceCode = GallerySources.StepperStatesExample) { StepperStatesExample() }
    }
}
