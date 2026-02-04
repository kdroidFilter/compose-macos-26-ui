package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.button.DarwinButton
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonSize
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonVariant
import io.github.kdroidfilter.darwinui.components.card.DarwinCard
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.components.topbar.DarwinTopbar
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("TopBar", "Default")
@Composable
fun TopBarDefaultExample() {
    DarwinCard(modifier = Modifier.fillMaxWidth(0.7f)) {
        Column {
            DarwinTopbar(title = "Dashboard", actions = { DarwinButton(text = "Settings", onClick = {}, variant = DarwinButtonVariant.Ghost, size = DarwinButtonSize.Small); DarwinButton(text = "Profile", onClick = {}, variant = DarwinButtonVariant.Ghost, size = DarwinButtonSize.Small) })
            Box(modifier = Modifier.fillMaxWidth().height(100.dp).padding(16.dp)) { DarwinText("Main content area", color = DarwinTheme.colors.textSecondary) }
        }
    }
}

@Composable
internal fun TopBarPage() {
    GalleryPage("Top Bar", "A macOS-style top navigation bar with title and actions.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.TopBarDefaultExample) { TopBarDefaultExample() }
    }
}
