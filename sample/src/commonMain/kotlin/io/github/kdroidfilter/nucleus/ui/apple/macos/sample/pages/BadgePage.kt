package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Badge
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.BadgeVariant
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.PreviewContainer
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BadgePreview() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Badge(variant = BadgeVariant.Default) { Text("Default") }
        Badge(variant = BadgeVariant.Secondary) { Text("Secondary") }
        Badge(variant = BadgeVariant.Outline) { Text("Outline") }
        Badge(variant = BadgeVariant.Success) { Text("Success") }
        Badge(variant = BadgeVariant.Warning) { Text("Warning") }
        Badge(variant = BadgeVariant.Destructive) { Text("Error") }
        Badge(variant = BadgeVariant.Info) { Text("Info") }
        Badge(variant = BadgeVariant.Published) { Text("Published") }
        Badge(variant = BadgeVariant.Draft) { Text("Draft") }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Badge", "Variants")
@Composable
fun BadgeVariantsExample() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Badge(variant = BadgeVariant.Default) { Text("Default") }
        Badge(variant = BadgeVariant.Secondary) { Text("Secondary") }
        Badge(variant = BadgeVariant.Outline) { Text("Outline") }
        Badge(variant = BadgeVariant.Success) { Text("Success") }
        Badge(variant = BadgeVariant.Warning) { Text("Warning") }
        Badge(variant = BadgeVariant.Destructive) { Text("Error") }
        Badge(variant = BadgeVariant.Info) { Text("Info") }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Badge", "Semantic Variants")
@Composable
fun BadgeSemanticVariantsExample() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Badge(variant = BadgeVariant.Published) { Text("Published") }
        Badge(variant = BadgeVariant.Draft) { Text("Draft") }
        Badge(variant = BadgeVariant.Archived) { Text("Archived") }
        Badge(variant = BadgeVariant.NewBadge) { Text("New") }
        Badge(variant = BadgeVariant.Responded) { Text("Responded") }
    }
}

@Composable
internal fun BadgePage() {
    GalleryPage("Badge", "Displays a badge or a component that looks like a badge.") {
        PreviewContainer { BadgePreview() }

        SectionHeader("Examples")
        ExampleCard(
            title = "Variants",
            description = "Core badge variants",
            sourceCode = GallerySources.BadgeVariantsExample,
        ) { BadgeVariantsExample() }
        ExampleCard(
            title = "Semantic Variants",
            description = "Status and category badges",
            sourceCode = GallerySources.BadgeSemanticVariantsExample,
        ) { BadgeSemanticVariantsExample() }
    }
}
