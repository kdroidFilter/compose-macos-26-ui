package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.button.DarwinButton
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonSize
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonVariant
import io.github.kdroidfilter.darwinui.components.toast.DarwinToastState
import io.github.kdroidfilter.darwinui.components.toast.DarwinToastType
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Toast", "Click to Show")
@Composable
fun ToastClickToShowExample(toastState: DarwinToastState) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinButton(
            text = "Info Toast",
            onClick = {
                toastState.show("This is an info message", title = "Info", type = DarwinToastType.Info)
            },
            variant = DarwinButtonVariant.Info,
            size = DarwinButtonSize.Small,
        )
        DarwinButton(
            text = "Success Toast",
            onClick = {
                toastState.show("Operation completed!", title = "Success", type = DarwinToastType.Success)
            },
            variant = DarwinButtonVariant.Success,
            size = DarwinButtonSize.Small,
        )
        DarwinButton(
            text = "Warning Toast",
            onClick = {
                toastState.show("Proceed with caution", title = "Warning", type = DarwinToastType.Warning)
            },
            variant = DarwinButtonVariant.Warning,
            size = DarwinButtonSize.Small,
        )
        DarwinButton(
            text = "Error Toast",
            onClick = {
                toastState.show("Something went wrong", title = "Error", type = DarwinToastType.Error)
            },
            variant = DarwinButtonVariant.Destructive,
            size = DarwinButtonSize.Small,
        )
    }
}

@Composable
internal fun ToastPage(toastState: DarwinToastState) {
    GalleryPage("Toast", "A succinct message that is displayed temporarily.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Click to Show",
            description = "Trigger different toast types",
            sourceCode = GallerySources.ToastClickToShowExample,
        ) { ToastClickToShowExample(toastState) }
    }
}
