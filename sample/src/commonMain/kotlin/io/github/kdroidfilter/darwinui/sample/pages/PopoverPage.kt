package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Popover
import io.github.kdroidfilter.darwinui.components.PopoverPlacement
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

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
            Text("Popover Content", fontWeight = FontWeight.SemiBold, color = DarwinTheme.colorScheme.textPrimary)
            Text("This is a popover panel with rich content.", color = DarwinTheme.colorScheme.textSecondary)
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
                Text("Placed ${placement.name}", color = DarwinTheme.colorScheme.textPrimary)
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
