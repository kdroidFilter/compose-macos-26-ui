package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.components.window.DarwinWindow
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Window", "Default")
@Composable
fun WindowDefaultExample() {
    DarwinWindow(title = "Terminal", onClose = {}, modifier = Modifier.fillMaxWidth(0.6f).height(200.dp)) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            DarwinText("$ npm install @pikoloo/darwin-ui", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.textSecondary)
            DarwinText("added 42 packages in 2.3s", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.textTertiary)
            DarwinText("$ npm run dev", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.textSecondary)
            DarwinText("Ready on http://localhost:3000", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.accent)
        }
    }
}

@Composable
internal fun WindowPage() {
    GalleryPage("Window", "A macOS-style window container with traffic light buttons.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.WindowDefaultExample) { WindowDefaultExample() }
    }
}
