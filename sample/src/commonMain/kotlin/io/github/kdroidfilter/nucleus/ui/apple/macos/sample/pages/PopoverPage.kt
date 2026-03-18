package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Popover
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PopoverPlacement
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PushButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("Popover", "Default")
@Composable
fun PopoverDefaultExample() {
    var popoverExpanded by remember { mutableStateOf(false) }
    Popover(
        expanded = popoverExpanded,
        onDismissRequest = { popoverExpanded = false },
        trigger = {
            PushButton(
                text = "Toggle Popover",
                onClick = { popoverExpanded = !popoverExpanded },
            )
        },
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Popover Content", fontWeight = FontWeight.SemiBold, color = MacosTheme.colorScheme.textPrimary)
            Text("This is a popover panel with rich content.", color = MacosTheme.colorScheme.textSecondary)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PushButton(
                    text = "Edit",
                    onClick = { popoverExpanded = false },
                )
                PushButton(
                    text = "Copy",
                    onClick = { popoverExpanded = false },
                )
            }
        }
    }
}

@GalleryExample("Popover", "Sizes")
@Composable
fun PopoverSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = MacosTheme.typography.caption1,
                        color = MacosTheme.colorScheme.textSecondary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    var expanded by remember { mutableStateOf(false) }
                    Popover(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        trigger = {
                            PushButton(
                                text = "Open",
                                onClick = { expanded = !expanded },
                            )
                        },
                    ) {
                        Text("${size.name} popover", color = MacosTheme.colorScheme.textPrimary)
                    }
                }
            }
        }
    }
}

@GalleryExample("Popover", "Placement")
@Composable
fun PopoverPlacementExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        PopoverPlacement.entries.filter { it != PopoverPlacement.Auto }.forEach { placement ->
            var expanded by remember { mutableStateOf(false) }
            Popover(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                placement = placement,
                trigger = {
                    PushButton(
                        text = placement.name,
                        onClick = { expanded = !expanded },
                    )
                },
            ) {
                Text("Placed ${placement.name}", color = MacosTheme.colorScheme.textPrimary)
            }
        }
    }
}

@Composable
internal fun PopoverPage() {
    GalleryPage("Popover", "Displays rich content in a portal, triggered by a button.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default (Auto)", sourceCode = GallerySources.PopoverDefaultExample) { PopoverDefaultExample() }
        ExampleCard(title = "Placement", sourceCode = GallerySources.PopoverPlacementExample) { PopoverPlacementExample() }
    }
}
