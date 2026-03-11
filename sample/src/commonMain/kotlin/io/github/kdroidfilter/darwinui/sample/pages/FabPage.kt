package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Plus
import com.composables.icons.lucide.Pencil
import io.github.kdroidfilter.darwinui.components.ExtendedFloatingActionButton
import io.github.kdroidfilter.darwinui.components.FloatingActionButton
import io.github.kdroidfilter.darwinui.components.LargeFloatingActionButton
import io.github.kdroidfilter.darwinui.components.SmallFloatingActionButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("FAB", "Sizes")
@Composable
fun FabSizesExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        SmallFloatingActionButton(onClick = {}) { Icon(Lucide.Plus) }
        FloatingActionButton(onClick = {}) { Icon(Lucide.Plus) }
        LargeFloatingActionButton(onClick = {}) { Icon(Lucide.Plus) }
    }
}

@GalleryExample("FAB", "Extended")
@Composable
fun FabExtendedExample() {
    ExtendedFloatingActionButton(
        onClick = {},
        icon = { Icon(Lucide.Pencil) },
        text = { Text("New note") },
    )
}

@Composable
internal fun FabPage() {
    GalleryPage("Floating Action Button", "A button that floats above the UI to promote a primary action.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Sizes",
            description = "Small, standard, and large FAB",
            sourceCode = GallerySources.FabSizesExample,
        ) { FabSizesExample() }
        ExampleCard(
            title = "Extended",
            description = "Extended FAB with icon and label",
            sourceCode = GallerySources.FabExtendedExample,
        ) { FabExtendedExample() }
    }
}
