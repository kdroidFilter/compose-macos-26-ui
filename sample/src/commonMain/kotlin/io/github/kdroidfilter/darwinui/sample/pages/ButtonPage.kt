package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.button.DarwinButton
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonSize
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonVariant
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.DarwinIcon
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
            DarwinText("TEXT BUTTONS", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DarwinButton(text = "Primary", onClick = {}, variant = DarwinButtonVariant.Primary)
                DarwinButton(text = "Secondary", onClick = {}, variant = DarwinButtonVariant.Secondary)
                DarwinButton(text = "Outline", onClick = {}, variant = DarwinButtonVariant.Outline)
                DarwinButton(text = "Ghost", onClick = {}, variant = DarwinButtonVariant.Ghost)
                DarwinButton(text = "Destructive", onClick = {}, variant = DarwinButtonVariant.Destructive)
                DarwinButton(
                    text = if (loading) "Loading..." else "Click me",
                    onClick = { loading = true },
                    variant = DarwinButtonVariant.Primary,
                    loading = loading,
                )
            }
        }
        // Icon Buttons
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DarwinText("ICON BUTTONS", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DarwinButton(onClick = {}, variant = DarwinButtonVariant.Primary, size = DarwinButtonSize.Icon) { DarwinIcon(LucidePlus) }
                DarwinButton(onClick = {}, variant = DarwinButtonVariant.Secondary, size = DarwinButtonSize.Icon) { DarwinIcon(LucideSettings) }
                DarwinButton(onClick = {}, variant = DarwinButtonVariant.Outline, size = DarwinButtonSize.Icon) { DarwinIcon(LucideHeart) }
                DarwinButton(onClick = {}, variant = DarwinButtonVariant.Ghost, size = DarwinButtonSize.Icon) { DarwinIcon(LucideShare2) }
                DarwinButton(onClick = {}, variant = DarwinButtonVariant.Destructive, size = DarwinButtonSize.Icon) { DarwinIcon(LucideTrash2) }
            }
        }
        // With Icons
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DarwinText("WITH ICONS", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DarwinButton(text = "Create New", onClick = {}, variant = DarwinButtonVariant.Primary, leftIcon = { DarwinIcon(LucidePlus, modifier = Modifier.padding(end = 4.dp)) })
                DarwinButton(text = "Download", onClick = {}, variant = DarwinButtonVariant.Secondary, leftIcon = { DarwinIcon(LucideDownload, modifier = Modifier.padding(end = 4.dp)) })
                DarwinButton(text = "Share", onClick = {}, variant = DarwinButtonVariant.Outline, leftIcon = { DarwinIcon(LucideShare2, modifier = Modifier.padding(end = 4.dp)) })
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
        DarwinButton(text = "Default", onClick = {})
        DarwinButton(text = "Primary", onClick = {}, variant = DarwinButtonVariant.Primary)
        DarwinButton(text = "Secondary", onClick = {}, variant = DarwinButtonVariant.Secondary)
        DarwinButton(text = "Accent", onClick = {}, variant = DarwinButtonVariant.Accent)
        DarwinButton(text = "Success", onClick = {}, variant = DarwinButtonVariant.Success)
        DarwinButton(text = "Warning", onClick = {}, variant = DarwinButtonVariant.Warning)
        DarwinButton(text = "Info", onClick = {}, variant = DarwinButtonVariant.Info)
        DarwinButton(text = "Destructive", onClick = {}, variant = DarwinButtonVariant.Destructive)
        DarwinButton(text = "Outline", onClick = {}, variant = DarwinButtonVariant.Outline)
        DarwinButton(text = "Ghost", onClick = {}, variant = DarwinButtonVariant.Ghost)
        DarwinButton(text = "Link", onClick = {}, variant = DarwinButtonVariant.Link)
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
        DarwinButton(text = "Small", onClick = {}, size = DarwinButtonSize.Small)
        DarwinButton(text = "Default", onClick = {}, size = DarwinButtonSize.Default)
        DarwinButton(text = "Large", onClick = {}, size = DarwinButtonSize.Large)
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
        DarwinButton(text = "Disabled", onClick = {}, enabled = false)
        DarwinButton(text = "Loading", onClick = {}, loading = true)
        DarwinButton(text = "Loading + text", onClick = {}, loading = true, loadingText = "Saving...")
        DarwinButton(text = "Default", onClick = {})
    }
}

@Composable
internal fun ButtonPage() {
    GalleryPage("Button", "A beautiful, accessible button component with native macOS styling.") {
        PreviewContainer { ButtonPreview() }

        SectionHeader("Usage")
        CodeBlock("""DarwinButton(text = "Primary", onClick = {}, variant = DarwinButtonVariant.Primary)
DarwinButton(text = "Secondary", onClick = {}, variant = DarwinButtonVariant.Secondary)
DarwinButton(text = "Destructive", onClick = {}, variant = DarwinButtonVariant.Destructive)""")

        SectionHeader("Examples")
        ExampleCard(title = "Variants", description = "All available button variants", sourceCode = GallerySources.ButtonVariantsExample) { ButtonVariantsExample() }
        ExampleCard(title = "Sizes", description = "Small, default, and large button sizes", sourceCode = GallerySources.ButtonSizesExample) { ButtonSizesExample() }
        ExampleCard(title = "States", description = "Disabled and loading states", sourceCode = GallerySources.ButtonStatesExample) { ButtonStatesExample() }
    }
}
