package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Surface
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Surface", "Variants")
@Composable
fun SurfaceVariantsExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Surface(
            modifier = Modifier.size(80.dp),
            color = DarwinTheme.colorScheme.surface,
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                Text("Surface", style = DarwinTheme.typography.labelSmall)
            }
        }
        Surface(
            modifier = Modifier.size(80.dp),
            color = DarwinTheme.colorScheme.surfaceVariant,
            shape = DarwinTheme.shapes.medium,
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                Text("Variant", style = DarwinTheme.typography.labelSmall)
            }
        }
        Surface(
            modifier = Modifier.size(80.dp),
            color = DarwinTheme.colorScheme.primaryContainer,
            shape = DarwinTheme.shapes.large,
            shadowElevation = 4.dp,
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                Text("Elevated", style = DarwinTheme.typography.labelSmall)
            }
        }
        Surface(
            modifier = Modifier.size(80.dp),
            color = DarwinTheme.colorScheme.surface,
            shape = DarwinTheme.shapes.extraLarge,
            border = BorderStroke(1.dp, DarwinTheme.colorScheme.outline),
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                Text("Outlined", style = DarwinTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
internal fun SurfacePage() {
    GalleryPage("Surface", "A foundational container component that maps to different color roles and elevations.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Variants",
            description = "Surface with different colors, shapes, and elevations",
            sourceCode = GallerySources.SurfaceVariantsExample,
        ) { SurfaceVariantsExample() }
    }
}
