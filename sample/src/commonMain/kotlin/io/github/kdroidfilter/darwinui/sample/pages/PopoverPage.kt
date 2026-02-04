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
import io.github.kdroidfilter.darwinui.components.button.DarwinButton
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonSize
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonVariant
import io.github.kdroidfilter.darwinui.components.popover.DarwinPopover
import io.github.kdroidfilter.darwinui.components.text.DarwinText
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
    DarwinPopover(
        expanded = popoverExpanded,
        onDismissRequest = { popoverExpanded = false },
        trigger = {
            DarwinButton(
                text = "Toggle Popover",
                onClick = { popoverExpanded = !popoverExpanded },
                variant = DarwinButtonVariant.Outline,
            )
        },
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DarwinText("Popover Content", fontWeight = FontWeight.SemiBold, color = DarwinTheme.colors.textPrimary)
            DarwinText("This is a popover panel with rich content.", color = DarwinTheme.colors.textSecondary)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DarwinButton(
                    text = "Edit",
                    onClick = { popoverExpanded = false },
                    variant = DarwinButtonVariant.Secondary,
                    size = DarwinButtonSize.Small,
                )
                DarwinButton(
                    text = "Copy",
                    onClick = { popoverExpanded = false },
                    variant = DarwinButtonVariant.Ghost,
                    size = DarwinButtonSize.Small,
                )
            }
        }
    }
}

@Composable
internal fun PopoverPage() {
    GalleryPage("Popover", "Displays rich content in a portal, triggered by a button.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.PopoverDefaultExample) { PopoverDefaultExample() }
    }
}
