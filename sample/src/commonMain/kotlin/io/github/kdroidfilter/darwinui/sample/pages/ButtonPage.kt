package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.AccentButton
import io.github.kdroidfilter.darwinui.components.Button
import io.github.kdroidfilter.darwinui.components.DestructiveButton
import io.github.kdroidfilter.darwinui.components.HyperlinkButton
import io.github.kdroidfilter.darwinui.components.InfoButton
import io.github.kdroidfilter.darwinui.components.OutlineButton
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.SecondaryButton
import io.github.kdroidfilter.darwinui.components.SubtleButton
import io.github.kdroidfilter.darwinui.components.SuccessButton
import io.github.kdroidfilter.darwinui.components.WarningButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideDownload
import io.github.kdroidfilter.darwinui.icons.LucideHeart
import io.github.kdroidfilter.darwinui.icons.LucidePlus
import io.github.kdroidfilter.darwinui.icons.LucideSettings
import io.github.kdroidfilter.darwinui.icons.LucideShare2
import io.github.kdroidfilter.darwinui.icons.LucideTrash2
import io.github.kdroidfilter.darwinui.sample.gallery.CodeBlock
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ButtonPreview() {
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(loading) {
        if (loading) {
            delay(2000)
            loading = false
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Text Buttons
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("TEXT BUTTONS", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PrimaryButton(text = "Primary", onClick = {})
                SecondaryButton(text = "Secondary", onClick = {})
                OutlineButton(text = "Outline", onClick = {})
                SubtleButton(text = "Ghost", onClick = {})
                DestructiveButton(text = "Destructive", onClick = {})
                PrimaryButton(
                    text = if (loading) "Loading..." else "Click me",
                    onClick = { loading = true },
                    loading = loading,
                )
            }
        }
        // Icon Buttons
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("ICON BUTTONS", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PrimaryButton(onClick = {}) { Icon(LucidePlus) }
                SecondaryButton(onClick = {}) { Icon(LucideSettings) }
                OutlineButton(onClick = {}) { Icon(LucideHeart) }
                SubtleButton(onClick = {}) { Icon(LucideShare2) }
                DestructiveButton(onClick = {}) { Icon(LucideTrash2) }
            }
        }
        // With Icons
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("WITH ICONS", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                PrimaryButton(
                    text = "Create New",
                    onClick = {},
                    leftIcon = { Icon(LucidePlus, modifier = Modifier.padding(end = 4.dp)) },
                )
                SecondaryButton(
                    text = "Download",
                    onClick = {},
                    leftIcon = { Icon(LucideDownload, modifier = Modifier.padding(end = 4.dp)) },
                )
                OutlineButton(
                    text = "Share",
                    onClick = {},
                    leftIcon = { Icon(LucideShare2, modifier = Modifier.padding(end = 4.dp)) },
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Variants")
@Composable
fun ButtonVariantsExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(onClick = {}) { Text("Default") }
        PrimaryButton(text = "Primary", onClick = {})
        SecondaryButton(text = "Secondary", onClick = {})
        AccentButton(text = "Accent", onClick = {})
        SuccessButton(text = "Success", onClick = {})
        WarningButton(text = "Warning", onClick = {})
        InfoButton(text = "Info", onClick = {})
        DestructiveButton(text = "Destructive", onClick = {})
        OutlineButton(text = "Outline", onClick = {})
        SubtleButton(text = "Ghost", onClick = {})
        HyperlinkButton(text = "Link", onClick = {})
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Sizes")
@Composable
fun ButtonSizesExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(onClick = {}) { Text("Small") }
        Button(onClick = {}) { Text("Default") }
        Button(onClick = {}) { Text("Large") }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "States")
@Composable
fun ButtonStatesExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(onClick = {}, enabled = false) { Text("Disabled") }
        PrimaryButton(text = "Loading", onClick = {}, loading = true)
        PrimaryButton(text = "Loading + text", onClick = {}, loading = true, loadingText = "Saving...")
        Button(onClick = {}) { Text("Default") }
    }
}

@Composable
internal fun ButtonPage() {
    GalleryPage("Button", "A beautiful, accessible button component with native macOS styling.") {
        PreviewContainer { ButtonPreview() }

        SectionHeader("Usage")
        CodeBlock("""PrimaryButton(text = "Primary", onClick = {})
SecondaryButton(text = "Secondary", onClick = {})
DestructiveButton(text = "Destructive", onClick = {})""")

        SectionHeader("Examples")
        ExampleCard(
            title = "Variants",
            description = "All available button variants",
            sourceCode = GallerySources.ButtonVariantsExample,
        ) { ButtonVariantsExample() }
        ExampleCard(
            title = "Sizes",
            description = "Small, default, and large button sizes",
            sourceCode = GallerySources.ButtonSizesExample,
        ) { ButtonSizesExample() }
        ExampleCard(
            title = "States",
            description = "Disabled and loading states",
            sourceCode = GallerySources.ButtonStatesExample,
        ) { ButtonStatesExample() }
    }
}
