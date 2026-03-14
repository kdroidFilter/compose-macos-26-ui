package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.ToastState
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
        PushButton(
            text = "Notification",
            onClick = {
                toastState.show(
                    title = "Calendar",
                    message = "You have a meeting in 15 minutes",
                    timestamp = "now",
                )
            },
        )
        PushButton(
            text = "Message",
            onClick = {
                toastState.show(
                    title = "Messages",
                    message = "John: Hey, are you free for lunch today?",
                    timestamp = "2m ago",
                )
            },
        )
        PushButton(
            text = "Download",
            onClick = {
                toastState.show(
                    title = "Downloads",
                    message = "Your file has been downloaded successfully.",
                )
            },
        )
    }
}

@Composable
internal fun ToastPage(toastState: ToastState) {
    GalleryPage("Toast", "macOS-style notification banners that auto-dismiss.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Click to Show",
            description = "Trigger different notification banners",
            sourceCode = GallerySources.ToastClickToShowExample,
        ) { ToastClickToShowExample(toastState) }
    }
}
