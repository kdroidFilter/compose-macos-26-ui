package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Heart
import com.composables.icons.lucide.Settings
import com.composables.icons.lucide.Star
import io.github.kdroidfilter.darwinui.components.FilledIconButton
import io.github.kdroidfilter.darwinui.components.IconButton
import io.github.kdroidfilter.darwinui.components.OutlinedIconButton
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("Icon Button", "Variants")
@Composable
fun IconButtonVariantsExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        IconButton(onClick = {}) { Icon(Lucide.Settings) }
        FilledIconButton(onClick = {}) { Icon(Lucide.Heart) }
        OutlinedIconButton(onClick = {}) { Icon(Lucide.Star) }
    }
}

@GalleryExample("Icon Button", "Disabled")
@Composable
fun IconButtonDisabledExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        IconButton(onClick = {}, enabled = false) { Icon(Lucide.Settings) }
        FilledIconButton(onClick = {}, enabled = false) { Icon(Lucide.Heart) }
        OutlinedIconButton(onClick = {}, enabled = false) { Icon(Lucide.Star) }
    }
}

@Composable
internal fun IconButtonPage() {
    GalleryPage("Icon Button", "Compact buttons that display only an icon, in standard, filled, and outlined styles.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Variants",
            description = "IconButton, FilledIconButton, OutlinedIconButton",
            sourceCode = GallerySources.IconButtonVariantsExample,
        ) { IconButtonVariantsExample() }
        ExampleCard(
            title = "Disabled",
            description = "Disabled state for each icon button variant",
            sourceCode = GallerySources.IconButtonDisabledExample,
        ) { IconButtonDisabledExample() }
    }
}
