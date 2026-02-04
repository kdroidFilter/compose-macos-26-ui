package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.badge.DarwinBadge
import io.github.kdroidfilter.darwinui.components.badge.DarwinBadgeVariant
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BadgePreview() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinBadge(variant = DarwinBadgeVariant.Default) { DarwinText("Default") }
        DarwinBadge(variant = DarwinBadgeVariant.Secondary) { DarwinText("Secondary") }
        DarwinBadge(variant = DarwinBadgeVariant.Outline) { DarwinText("Outline") }
        DarwinBadge(variant = DarwinBadgeVariant.Success) { DarwinText("Success") }
        DarwinBadge(variant = DarwinBadgeVariant.Warning) { DarwinText("Warning") }
        DarwinBadge(variant = DarwinBadgeVariant.Destructive) { DarwinText("Error") }
        DarwinBadge(variant = DarwinBadgeVariant.Info) { DarwinText("Info") }
        DarwinBadge(variant = DarwinBadgeVariant.Published) { DarwinText("Published") }
        DarwinBadge(variant = DarwinBadgeVariant.Draft) { DarwinText("Draft") }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Badge", "Variants")
@Composable
fun BadgeVariantsExample() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinBadge(variant = DarwinBadgeVariant.Default) { DarwinText("Default") }
        DarwinBadge(variant = DarwinBadgeVariant.Secondary) { DarwinText("Secondary") }
        DarwinBadge(variant = DarwinBadgeVariant.Outline) { DarwinText("Outline") }
        DarwinBadge(variant = DarwinBadgeVariant.Success) { DarwinText("Success") }
        DarwinBadge(variant = DarwinBadgeVariant.Warning) { DarwinText("Warning") }
        DarwinBadge(variant = DarwinBadgeVariant.Destructive) { DarwinText("Error") }
        DarwinBadge(variant = DarwinBadgeVariant.Info) { DarwinText("Info") }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Badge", "Semantic Variants")
@Composable
fun BadgeSemanticVariantsExample() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinBadge(variant = DarwinBadgeVariant.Published) { DarwinText("Published") }
        DarwinBadge(variant = DarwinBadgeVariant.Draft) { DarwinText("Draft") }
        DarwinBadge(variant = DarwinBadgeVariant.Archived) { DarwinText("Archived") }
        DarwinBadge(variant = DarwinBadgeVariant.NewBadge) { DarwinText("New") }
        DarwinBadge(variant = DarwinBadgeVariant.Responded) { DarwinText("Responded") }
    }
}

@Composable
internal fun BadgePage() {
    GalleryPage("Badge", "Displays a badge or a component that looks like a badge.") {
        PreviewContainer { BadgePreview() }

        SectionHeader("Examples")
        ExampleCard(title = "Variants", description = "Core badge variants", sourceCode = GallerySources.BadgeVariantsExample) { BadgeVariantsExample() }
        ExampleCard(title = "Semantic Variants", description = "Status and category badges", sourceCode = GallerySources.BadgeSemanticVariantsExample) { BadgeSemanticVariantsExample() }
    }
}
