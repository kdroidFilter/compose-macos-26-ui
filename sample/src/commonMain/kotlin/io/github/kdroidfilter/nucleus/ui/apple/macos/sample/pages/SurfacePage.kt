package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SurfaceDefaults
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.PreviewContainer
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.GlassMaterialSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@Composable
private fun SurfacePreview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Liquid Glass Surface", style = MacosTheme.typography.headline)
                Text("Default Medium material", style = MacosTheme.typography.subheadline, color = MacosTheme.colorScheme.textSecondary)
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            materialSize = null,
            colors = SurfaceDefaults.cardColors(),
            border = SurfaceDefaults.outlinedBorder(),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Solid Surface", style = MacosTheme.typography.headline)
                Text("No glass, plain background", style = MacosTheme.typography.subheadline, color = MacosTheme.colorScheme.textSecondary)
            }
        }
    }
}

@GalleryExample("Surface", "Glass Material Sizes")
@Composable
fun SurfaceGlassSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        GlassMaterialSize.entries.forEach { size ->
            Surface(
                modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth(),
                materialSize = size,
            ) {
                Text(
                    "${size.name} glass",
                    modifier = Modifier.padding(16.dp),
                    style = MacosTheme.typography.subheadline,
                )
            }
        }
    }
}

@GalleryExample("Surface", "Tinted Glass")
@Composable
fun SurfaceTintedExample() {
    Surface(
        modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth(),
        tintColor = MacosTheme.colorScheme.accent,
    ) {
        Text(
            "Accent tinted glass",
            modifier = Modifier.padding(16.dp),
            style = MacosTheme.typography.subheadline,
        )
    }
}

@GalleryExample("Surface", "Solid Fallback")
@Composable
fun SurfaceSolidExample() {
    Surface(
        modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth(),
        materialSize = null,
        colors = SurfaceDefaults.cardColors(),
        border = SurfaceDefaults.outlinedBorder(),
    ) {
        Text(
            "Solid surface (no glass)",
            modifier = Modifier.padding(16.dp),
        )
    }
}

@GalleryExample("Surface", "Content Alignment")
@Composable
fun SurfaceAlignmentExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Surface(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text("Center", style = MacosTheme.typography.caption1)
        }

        Surface(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Text("BottomEnd", modifier = Modifier.padding(8.dp), style = MacosTheme.typography.caption1)
        }

        Surface(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            Text("TopCenter", modifier = Modifier.padding(8.dp), style = MacosTheme.typography.caption1)
        }
    }
}

@GalleryExample("Surface", "Nested Surfaces")
@Composable
fun SurfaceNestedExample() {
    Surface(
        modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth(),
        materialSize = GlassMaterialSize.Large,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text("Outer (Large glass)", style = MacosTheme.typography.headline)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                materialSize = GlassMaterialSize.Small,
                shape = MacosTheme.shapes.medium,
            ) {
                Text(
                    "Inner (Small glass)",
                    modifier = Modifier.padding(12.dp),
                    style = MacosTheme.typography.subheadline,
                )
            }
        }
    }
}

@Composable
internal fun SurfacePage() {
    GalleryPage("Surface", "A styled container with Liquid Glass material rendering.") {
        PreviewContainer { SurfacePreview() }

        SectionHeader("Glass")
        ExampleCard(
            title = "Tinted Glass",
            description = "Glass with accent color overlay",
            sourceCode = GallerySources.SurfaceTintedExample,
        ) { SurfaceTintedExample() }

        SectionHeader("Solid")
        ExampleCard(
            title = "Solid Fallback",
            description = "Plain background without glass (materialSize = null)",
            sourceCode = GallerySources.SurfaceSolidExample,
        ) { SurfaceSolidExample() }

        SectionHeader("Layout")
        ExampleCard(
            title = "Content Alignment",
            description = "Positioning content within the surface",
            sourceCode = GallerySources.SurfaceAlignmentExample,
        ) { SurfaceAlignmentExample() }

        SectionHeader("Composition")
        ExampleCard(
            title = "Nested Surfaces",
            description = "Surfaces with different glass tiers composed together",
            sourceCode = GallerySources.SurfaceNestedExample,
        ) { SurfaceNestedExample() }
    }
}
