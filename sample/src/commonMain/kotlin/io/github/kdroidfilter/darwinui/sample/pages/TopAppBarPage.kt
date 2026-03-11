package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.ArrowLeft
import com.composables.icons.lucide.EllipsisVertical
import io.github.kdroidfilter.darwinui.components.CenterAlignedTopAppBar
import io.github.kdroidfilter.darwinui.components.IconButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TopAppBar
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Top App Bar", "Standard")
@Composable
fun TopAppBarStandardExample() {
    Box(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(
            title = { Text("Page Title") },
            navigationIcon = {
                IconButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
            },
            actions = {
                IconButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
            },
        )
    }
}

@GalleryExample("Top App Bar", "Center Aligned")
@Composable
fun TopAppBarCenterAlignedExample() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CenterAlignedTopAppBar(
            title = { Text("Centered Title") },
            navigationIcon = {
                IconButton(onClick = {}) { Icon(Lucide.ArrowLeft) }
            },
            actions = {
                IconButton(onClick = {}) { Icon(Lucide.EllipsisVertical) }
            },
        )
    }
}

@Composable
internal fun TopAppBarPage() {
    GalleryPage("Top App Bar", "A toolbar placed at the top of the screen that provides navigation and actions.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Standard",
            description = "Left-aligned title with navigation icon and actions",
            sourceCode = GallerySources.TopAppBarStandardExample,
        ) { TopAppBarStandardExample() }
        ExampleCard(
            title = "Center Aligned",
            description = "Centered title layout",
            sourceCode = GallerySources.TopAppBarCenterAlignedExample,
        ) { TopAppBarCenterAlignedExample() }
    }
}
