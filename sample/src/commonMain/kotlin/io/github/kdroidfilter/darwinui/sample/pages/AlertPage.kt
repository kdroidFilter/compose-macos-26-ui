package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.AlertBanner
import io.github.kdroidfilter.darwinui.components.AlertType
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@Composable
private fun AlertPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        AlertBanner(message = "This is an informational alert.", title = "Information", type = AlertType.Info)
        AlertBanner(message = "Operation completed successfully!", title = "Success", type = AlertType.Success)
        AlertBanner(message = "Please review before proceeding.", title = "Warning", type = AlertType.Warning)
        AlertBanner(message = "An error occurred.", title = "Error", type = AlertType.Error)
    }
}

@GalleryExample("Alert", "Info")
@Composable
fun AlertInfoExample() {
    AlertBanner(
        message = "This is an informational alert.",
        title = "Information",
        type = AlertType.Info,
    )
}

@GalleryExample("Alert", "Success")
@Composable
fun AlertSuccessExample() {
    AlertBanner(
        message = "Operation completed successfully!",
        title = "Success",
        type = AlertType.Success,
    )
}

@GalleryExample("Alert", "Warning")
@Composable
fun AlertWarningExample() {
    AlertBanner(
        message = "Please review before proceeding.",
        title = "Warning",
        type = AlertType.Warning,
    )
}

@GalleryExample("Alert", "Error")
@Composable
fun AlertErrorExample() {
    AlertBanner(
        message = "An error occurred while processing.",
        title = "Error",
        type = AlertType.Error,
        onDismiss = {},
    )
}

@Composable
internal fun AlertPage() {
    GalleryPage("Alert", "Inline banners for user attention.") {
        PreviewContainer { AlertPreview() }

        SectionHeader("Variants")
        ExampleCard(title = "Info", sourceCode = GallerySources.AlertInfoExample) { AlertInfoExample() }
        ExampleCard(title = "Success", sourceCode = GallerySources.AlertSuccessExample) { AlertSuccessExample() }
        ExampleCard(title = "Warning", sourceCode = GallerySources.AlertWarningExample) { AlertWarningExample() }
        ExampleCard(title = "Error", sourceCode = GallerySources.AlertErrorExample) { AlertErrorExample() }
    }
}
