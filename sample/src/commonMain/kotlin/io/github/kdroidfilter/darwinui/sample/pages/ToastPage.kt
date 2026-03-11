package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.DestructiveButton
import io.github.kdroidfilter.darwinui.components.InfoButton
import io.github.kdroidfilter.darwinui.components.SuccessButton
import io.github.kdroidfilter.darwinui.components.WarningButton
import io.github.kdroidfilter.darwinui.components.ToastState
import io.github.kdroidfilter.darwinui.components.ToastType
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Toast", "Click to Show")
@Composable
fun ToastClickToShowExample(toastState: ToastState) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        InfoButton(
            text = "Info Toast",
            onClick = {
                toastState.show("This is an info message", title = "Info", type = ToastType.Info)
            },
        )
        SuccessButton(
            text = "Success Toast",
            onClick = {
                toastState.show("Operation completed!", title = "Success", type = ToastType.Success)
            },
        )
        WarningButton(
            text = "Warning Toast",
            onClick = {
                toastState.show("Proceed with caution", title = "Warning", type = ToastType.Warning)
            },
        )
        DestructiveButton(
            text = "Error Toast",
            onClick = {
                toastState.show("Something went wrong", title = "Error", type = ToastType.Error)
            },
        )
    }
}

@Composable
internal fun ToastPage(toastState: ToastState) {
    GalleryPage("Toast", "A succinct message that is displayed temporarily.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Click to Show",
            description = "Trigger different toast types",
            sourceCode = GallerySources.ToastClickToShowExample,
        ) { ToastClickToShowExample(toastState) }
    }
}
