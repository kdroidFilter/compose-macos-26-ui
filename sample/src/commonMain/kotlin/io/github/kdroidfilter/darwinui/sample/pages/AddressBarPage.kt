package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.AddressBar
import io.github.kdroidfilter.darwinui.components.Card
import io.github.kdroidfilter.darwinui.components.DarwinScaffold
import io.github.kdroidfilter.darwinui.components.NavigationButtons
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TitleBar
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideInfo
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("AddressBar", "Safari Style")
@Composable
fun AddressBarSafariExample() {
    var url by remember { mutableStateOf("https://developer.apple.com") }

    Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
        DarwinScaffold(
            titleBar = {
                TitleBar(
                    navigationActions = {
                        NavigationButtons(onBack = {}, onForward = {})
                    },
                    title = {
                        AddressBar(
                            value = url,
                            onValueChange = { url = it },
                            onGo = {},
                            leadingIcon = {
                                Icon(LucideInfo, modifier = Modifier.size(12.dp))
                            },
                        )
                    },
                )
            },
        ) { contentPadding ->
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(contentPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "Safari-style address bar in the title slot.",
                    style = DarwinTheme.typography.subheadline,
                    color = DarwinTheme.colorScheme.textSecondary,
                )
            }
        }
    }
}

@GalleryExample("AddressBar", "Standalone")
@Composable
fun AddressBarStandaloneExample() {
    var query by remember { mutableStateOf("") }

    AddressBar(
        value = query,
        onValueChange = { query = it },
        placeholder = "Search or enter website name",
        onGo = {},
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
internal fun AddressBarPage() {
    GalleryPage("Address Bar", "macOS Safari-style address bar with pill shape.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Safari Style",
            description = "Address bar in a title bar with navigation buttons",
            sourceCode = GallerySources.AddressBarSafariExample,
        ) { AddressBarSafariExample() }
        ExampleCard(
            title = "Standalone",
            description = "Address bar used outside of a title bar",
            sourceCode = GallerySources.AddressBarStandaloneExample,
        ) { AddressBarStandaloneExample() }
    }
}
