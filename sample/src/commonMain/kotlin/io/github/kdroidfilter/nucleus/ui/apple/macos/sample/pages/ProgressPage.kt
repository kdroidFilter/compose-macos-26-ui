package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.LinearProgress
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Spinner
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.CodeBlock
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.PreviewContainer
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@Composable
private fun ProgressPreview() {
    Column(
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LinearProgress(value = 65f, max = 100f)
        LinearProgress(value = 65f, max = 100f, enabled = false)
        LinearProgress(indeterminate = true)
        LinearProgress(indeterminate = true, enabled = false)
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spinner()
            ControlSize(ControlSize.Large) {
                Spinner()
            }
            Spinner(label = "Checking for Update\u2026")
        }
    }
}

@GalleryExample("Progress", "Sizes")
@Composable
fun ProgressSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = MacosTheme.typography.caption1,
                        color = MacosTheme.colorScheme.textSecondary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    LinearProgress(value = 65f, max = 100f, modifier = Modifier.weight(1f))
                    Spinner()
                }
            }
        }
    }
}

@GalleryExample("Progress", "Determinate")
@Composable
fun ProgressDeterminateExample() {
    Column(
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LinearProgress(value = 65f, max = 100f)
        LinearProgress(value = 65f, max = 100f, enabled = false)
    }
}

@GalleryExample("Progress", "Indeterminate")
@Composable
fun ProgressIndeterminateExample() {
    Column(
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        LinearProgress(indeterminate = true)
        LinearProgress(indeterminate = true, enabled = false)
    }
}

@GalleryExample("Progress", "Spinner")
@Composable
fun ProgressSpinnerExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spinner()
        ControlSize(ControlSize.Large) {
            Spinner()
        }
        Spinner(label = "Checking for Update\u2026")
    }
}

@Composable
internal fun ProgressPage() {
    GalleryPage("Progress", "Displays an indicator showing the completion progress of a task.") {
        PreviewContainer { ProgressPreview() }

        SectionHeader("Usage")
        CodeBlock("""LinearProgress(value = 65f, max = 100f)
LinearProgress(indeterminate = true)
Spinner()
Spinner(label = "Loading…")""")

        SectionHeader("Sizes")
        ExampleCard(
            title = "All Sizes",
            description = "LinearProgress and Spinner at each ControlSize level",
            sourceCode = GallerySources.ProgressSizesExample,
        ) { ProgressSizesExample() }

        SectionHeader("Examples")
        ExampleCard(
            title = "Determinate",
            description = "Active and inactive determinate progress bars",
            sourceCode = GallerySources.ProgressDeterminateExample,
        ) { ProgressDeterminateExample() }
        ExampleCard(
            title = "Indeterminate",
            description = "Active and inactive indeterminate progress bars",
            sourceCode = GallerySources.ProgressIndeterminateExample,
        ) { ProgressIndeterminateExample() }
        ExampleCard(
            title = "Spinner",
            description = "Indeterminate spinner with optional label",
            sourceCode = GallerySources.ProgressSpinnerExample,
        ) { ProgressSpinnerExample() }
    }
}
