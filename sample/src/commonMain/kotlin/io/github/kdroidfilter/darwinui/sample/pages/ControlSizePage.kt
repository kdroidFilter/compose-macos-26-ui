package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.CheckBox
import io.github.kdroidfilter.darwinui.components.ComboBox
import io.github.kdroidfilter.darwinui.components.LinearProgress
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.RadioButton
import io.github.kdroidfilter.darwinui.components.SegmentedControl
import io.github.kdroidfilter.darwinui.components.Slider
import io.github.kdroidfilter.darwinui.components.Switcher
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TextField
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("ControlSize", "Cascade")
@Composable
fun ControlSizeCascadeExample() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = size.name,
                        style = DarwinTheme.typography.headline,
                        color = DarwinTheme.colorScheme.textPrimary,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        PushButton(text = "Button", onClick = {})
                        var checked by remember { mutableStateOf(true) }
                        CheckBox(checked = checked, onCheckedChange = { checked = it }, label = "Check")
                        var switched by remember { mutableStateOf(true) }
                        Switcher(checked = switched, onCheckedChange = { switched = it })
                        RadioButton(selected = true, onClick = {})
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        var text by remember { mutableStateOf("") }
                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            placeholder = { Text("Input...") },
                            modifier = Modifier.width(160.dp),
                        )
                        ComboBox(
                            items = listOf("Option A", "Option B"),
                            selected = 0,
                            onSelectionChange = { _, _ -> },
                            modifier = Modifier.width(140.dp),
                        )
                    }
                    var sliderVal by remember { mutableStateOf(50f) }
                    Slider(
                        value = sliderVal,
                        onValueChange = { sliderVal = it },
                        valueRange = 0f..100f,
                        modifier = Modifier.fillMaxWidth(0.5f),
                    )
                    LinearProgress(
                        value = 65f,
                        max = 100f,
                        modifier = Modifier.fillMaxWidth(0.5f),
                    )
                    var seg by remember { mutableStateOf(0) }
                    SegmentedControl(
                        options = listOf("Day", "Week", "Month"),
                        selectedIndex = seg,
                        onSelectedIndexChange = { seg = it },
                    )
                }
            }
        }
    }
}

@Composable
internal fun ControlSizePage() {
    GalleryPage("Control Size", "Unified sizing system that propagates via CompositionLocal.") {
        SectionHeader("Cascade Demo")
        ExampleCard(
            title = "All Sizes",
            description = "Wrapping components in ControlSize() automatically sizes all children",
            sourceCode = GallerySources.ControlSizeCascadeExample,
        ) { ControlSizeCascadeExample() }
    }
}
