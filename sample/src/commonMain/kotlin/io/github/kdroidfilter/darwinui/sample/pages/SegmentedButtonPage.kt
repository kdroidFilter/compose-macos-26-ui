package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.SegmentedControl
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader

@Composable
internal fun SegmentedControlPage() {
    GalleryPage("Segmented Control", "macOS-style segmented control with sliding pill indicator.") {
        val options = listOf("Day", "Week", "Month")

        SectionHeader("Single Choice")
        ExampleCard(
            title = "Single Choice",
            description = "Select one option from a group",
            sourceCode = """
                var selected by remember { mutableStateOf(0) }
                SegmentedControl(
                    options = listOf("Day", "Week", "Month"),
                    selectedIndex = selected,
                    onSelectedIndexChange = { selected = it },
                )
            """.trimIndent(),
        ) {
            var selected by remember { mutableStateOf(0) }
            SegmentedControl(
                options = options,
                selectedIndex = selected,
                onSelectedIndexChange = { selected = it },
            )
        }

        SectionHeader("Sizes")
        ExampleCard(
            title = "Sizes",
            description = "All ControlSize variants",
            sourceCode = """
                ControlSize(ControlSize.Small) {
                    SegmentedControl(
                        options = options,
                        selectedIndex = selected,
                        onSelectedIndexChange = { selected = it },
                    )
                }
            """.trimIndent(),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                for (size in ControlSize.entries) {
                    var selected by remember { mutableStateOf(0) }
                    Text(size.name)
                    ControlSize(size) {
                        SegmentedControl(
                            options = options,
                            selectedIndex = selected,
                            onSelectedIndexChange = { selected = it },
                        )
                    }
                }
            }
        }

        SectionHeader("Disabled")
        ExampleCard(
            title = "Disabled",
            description = "Non-interactive disabled state",
            sourceCode = """
                SegmentedControl(
                    options = listOf("On", "Off"),
                    selectedIndex = 0,
                    onSelectedIndexChange = {},
                    enabled = false,
                )
            """.trimIndent(),
        ) {
            SegmentedControl(
                options = listOf("On", "Off"),
                selectedIndex = 0,
                onSelectedIndexChange = {},
                enabled = false,
            )
        }
    }
}
