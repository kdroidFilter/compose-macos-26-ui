package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.progress.DarwinCircularProgress
import io.github.kdroidfilter.darwinui.components.progress.DarwinLinearProgress
import io.github.kdroidfilter.darwinui.components.progress.DarwinProgressVariant
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.CodeBlock
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@Composable
private fun ProgressPreview() {
    Column(
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DarwinLinearProgress(value = 65f, max = 100f, showValue = true)
        DarwinLinearProgress(value = 40f, max = 100f, variant = DarwinProgressVariant.Success)
        DarwinLinearProgress(value = 80f, max = 100f, variant = DarwinProgressVariant.Gradient)
        DarwinLinearProgress(indeterminate = true)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            DarwinCircularProgress(value = 75f, max = 100f, showValue = true)
            DarwinCircularProgress(value = 45f, max = 100f, variant = DarwinProgressVariant.Success, showValue = true)
            DarwinCircularProgress(indeterminate = true)
        }
    }
}

@GalleryExample("Progress", "Linear Default")
@Composable
fun ProgressLinearDefaultExample() {
    DarwinLinearProgress(
        value = 65f,
        max = 100f,
        showValue = true,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Linear Success")
@Composable
fun ProgressLinearSuccessExample() {
    DarwinLinearProgress(
        value = 40f,
        max = 100f,
        variant = DarwinProgressVariant.Success,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Linear Gradient")
@Composable
fun ProgressLinearGradientExample() {
    DarwinLinearProgress(
        value = 80f,
        max = 100f,
        variant = DarwinProgressVariant.Gradient,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Linear Indeterminate")
@Composable
fun ProgressLinearIndeterminateExample() {
    DarwinLinearProgress(
        indeterminate = true,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Circular")
@Composable
fun ProgressCircularExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        DarwinCircularProgress(value = 75f, max = 100f, showValue = true)
        DarwinCircularProgress(value = 45f, max = 100f, variant = DarwinProgressVariant.Success, showValue = true)
        DarwinCircularProgress(indeterminate = true)
    }
}

@Composable
internal fun ProgressPage() {
    GalleryPage("Progress", "Displays an indicator showing the completion progress of a task.") {
        PreviewContainer { ProgressPreview() }

        SectionHeader("Usage")
        CodeBlock("""DarwinLinearProgress(value = 65f, max = 100f, showValue = true)
DarwinCircularProgress(value = 75f, max = 100f, showValue = true)""")

        SectionHeader("Examples")
        ExampleCard(title = "Linear - Default", sourceCode = GallerySources.ProgressLinearDefaultExample) { ProgressLinearDefaultExample() }
        ExampleCard(title = "Linear - Success", sourceCode = GallerySources.ProgressLinearSuccessExample) { ProgressLinearSuccessExample() }
        ExampleCard(
            title = "Linear - Gradient",
            sourceCode = GallerySources.ProgressLinearGradientExample,
        ) { ProgressLinearGradientExample() }
        ExampleCard(
            title = "Linear - Indeterminate",
            sourceCode = GallerySources.ProgressLinearIndeterminateExample,
        ) { ProgressLinearIndeterminateExample() }
        ExampleCard(title = "Circular", sourceCode = GallerySources.ProgressCircularExample) { ProgressCircularExample() }
    }
}
