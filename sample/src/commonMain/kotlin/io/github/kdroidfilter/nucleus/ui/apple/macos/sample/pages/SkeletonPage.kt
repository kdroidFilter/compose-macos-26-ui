package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Skeleton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SkeletonCircle
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.CodeBlock
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("Skeleton", "Card Skeleton")
@Composable
fun SkeletonCardExample() {

    Column(
        modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Avatar row: circle h-12 w-12 + text lines
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SkeletonCircle(size = 48.dp) // h-12 w-12
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Skeleton(modifier = Modifier.fillMaxWidth(0.75f).height(16.dp)) // h-4 w-3/4
                Skeleton(modifier = Modifier.fillMaxWidth(0.5f).height(12.dp)) // h-3 w-1/2
            }
        }
        // Content block: h-24 w-full rounded-lg
        Skeleton(modifier = Modifier.fillMaxWidth().height(96.dp)) // h-24
        // Button row
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Skeleton(modifier = Modifier.width(80.dp).height(32.dp), shape = MacosTheme.shapes.small) // h-8 w-20 rounded-md
            Skeleton(modifier = Modifier.width(80.dp).height(32.dp), shape = MacosTheme.shapes.small) // h-8 w-20 rounded-md
        }
    }
}

@Composable
internal fun SkeletonPage() {
    GalleryPage("Skeleton", "Used to show a placeholder while content is loading.") {
        SectionHeader("Usage")
        CodeBlock("""Skeleton(modifier = Modifier.fillMaxWidth().height(16.dp))
SkeletonCircle(size = 48.dp)""")

        SectionHeader("Examples")
        ExampleCard(
            title = "Card Skeleton",
            description = "Avatar, text lines, content block, and button placeholders",
            sourceCode = GallerySources.SkeletonCardExample,
        ) { SkeletonCardExample() }
    }
}
