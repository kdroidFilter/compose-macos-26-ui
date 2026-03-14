package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.SegmentedControl
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.LocalWindowActive
import io.github.kdroidfilter.darwinui.theme.SegmentedControlVariant
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader

@Composable
internal fun SegmentedControlPage() {
    GalleryPage("Segmented Control", "macOS-style segmented control with accent-colored indicator.") {
        val trio = listOf("1", "2", "3")
        val duo = listOf("1", "2")

        SectionHeader("Content Area")
        ExampleCard(
            title = "Trio — Active window",
            description = "3 segments across all sizes, last segment selected",
            sourceCode = """
                ControlSize(size) {
                    SegmentedControl(
                        options = listOf("1", "2", "3"),
                        selectedIndex = 2,
                        onSelectedIndexChange = { },
                    )
                }
            """.trimIndent(),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (size in ControlSize.entries) {
                    ControlSize(size) {
                        var sel by remember { mutableStateOf(2) }
                        SegmentedControl(
                            options = trio,
                            selectedIndex = sel,
                            onSelectedIndexChange = { sel = it },
                        )
                    }
                }
            }
        }

        ExampleCard(
            title = "Trio — Inactive window",
            description = "Same controls with inactive window state",
            sourceCode = """
                CompositionLocalProvider(LocalWindowActive provides false) {
                    SegmentedControl(...)
                }
            """.trimIndent(),
        ) {
            CompositionLocalProvider(LocalWindowActive provides false) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var sel by remember { mutableStateOf(2) }
                            SegmentedControl(
                                options = trio,
                                selectedIndex = sel,
                                onSelectedIndexChange = { sel = it },
                            )
                        }
                    }
                }
            }
        }

        ExampleCard(
            title = "Duo — Active window",
            description = "2 segments across all sizes",
            sourceCode = """
                ControlSize(size) {
                    SegmentedControl(
                        options = listOf("1", "2"),
                        selectedIndex = 0,
                        onSelectedIndexChange = { },
                    )
                }
            """.trimIndent(),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (size in ControlSize.entries) {
                    ControlSize(size) {
                        var sel by remember { mutableStateOf(0) }
                        SegmentedControl(
                            options = duo,
                            selectedIndex = sel,
                            onSelectedIndexChange = { sel = it },
                        )
                    }
                }
            }
        }

        ExampleCard(
            title = "Duo — Inactive window",
            description = "Same controls with inactive window state",
            sourceCode = """
                CompositionLocalProvider(LocalWindowActive provides false) {
                    SegmentedControl(...)
                }
            """.trimIndent(),
        ) {
            CompositionLocalProvider(LocalWindowActive provides false) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var sel by remember { mutableStateOf(0) }
                            SegmentedControl(
                                options = duo,
                                selectedIndex = sel,
                                onSelectedIndexChange = { sel = it },
                            )
                        }
                    }
                }
            }
        }

        SectionHeader("Over-glass")
        ExampleCard(
            title = "Trio — Active window",
            description = "Over-glass variant for use on translucent surfaces",
            sourceCode = """
                SegmentedControl(
                    options = listOf("1", "2", "3"),
                    selectedIndex = 2,
                    onSelectedIndexChange = { },
                    variant = SegmentedControlVariant.OverGlass,
                )
            """.trimIndent(),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (size in ControlSize.entries) {
                    ControlSize(size) {
                        var sel by remember { mutableStateOf(2) }
                        SegmentedControl(
                            options = trio,
                            selectedIndex = sel,
                            onSelectedIndexChange = { sel = it },
                            variant = SegmentedControlVariant.OverGlass,
                        )
                    }
                }
            }
        }

        ExampleCard(
            title = "Trio — Inactive window",
            description = "Over-glass with inactive window state",
            sourceCode = """
                CompositionLocalProvider(LocalWindowActive provides false) {
                    SegmentedControl(
                        ...,
                        variant = SegmentedControlVariant.OverGlass,
                    )
                }
            """.trimIndent(),
        ) {
            CompositionLocalProvider(LocalWindowActive provides false) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var sel by remember { mutableStateOf(2) }
                            SegmentedControl(
                                options = trio,
                                selectedIndex = sel,
                                onSelectedIndexChange = { sel = it },
                                variant = SegmentedControlVariant.OverGlass,
                            )
                        }
                    }
                }
            }
        }

        ExampleCard(
            title = "Duo — Active window",
            description = "Over-glass duo variant",
            sourceCode = """
                SegmentedControl(
                    options = listOf("1", "2"),
                    selectedIndex = 0,
                    onSelectedIndexChange = { },
                    variant = SegmentedControlVariant.OverGlass,
                )
            """.trimIndent(),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                for (size in ControlSize.entries) {
                    ControlSize(size) {
                        var sel by remember { mutableStateOf(0) }
                        SegmentedControl(
                            options = duo,
                            selectedIndex = sel,
                            onSelectedIndexChange = { sel = it },
                            variant = SegmentedControlVariant.OverGlass,
                        )
                    }
                }
            }
        }

        ExampleCard(
            title = "Duo — Inactive window",
            description = "Over-glass duo with inactive window state",
            sourceCode = """
                CompositionLocalProvider(LocalWindowActive provides false) {
                    SegmentedControl(
                        ...,
                        variant = SegmentedControlVariant.OverGlass,
                    )
                }
            """.trimIndent(),
        ) {
            CompositionLocalProvider(LocalWindowActive provides false) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    for (size in ControlSize.entries) {
                        ControlSize(size) {
                            var sel by remember { mutableStateOf(0) }
                            SegmentedControl(
                                options = duo,
                                selectedIndex = sel,
                                onSelectedIndexChange = { sel = it },
                                variant = SegmentedControlVariant.OverGlass,
                            )
                        }
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
