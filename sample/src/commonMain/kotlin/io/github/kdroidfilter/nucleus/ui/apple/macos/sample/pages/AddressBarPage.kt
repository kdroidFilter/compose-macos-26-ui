package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.AddressBar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Card
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Scaffold
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.NavigationButtons
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBar
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideInfo
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.LocalTitleBarStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBarStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("AddressBar", "Safari Style")
@Composable
fun AddressBarSafariExample() {
    var url by remember { mutableStateOf("https://developer.apple.com") }

    Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
        Scaffold(
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
                    style = MacosTheme.typography.subheadline,
                    color = MacosTheme.colorScheme.textSecondary,
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

@GalleryExample("AddressBar", "Surface Variants")
@Composable
fun AddressBarSurfaceVariantsExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Content Area (default)
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Content Area",
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textSecondary,
            )
            Surface(Surface.ContentArea) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (style in listOf(TitleBarStyle.Unified, TitleBarStyle.UnifiedCompact)) {
                        CompositionLocalProvider(LocalTitleBarStyle provides style) {
                            var url by remember { mutableStateOf("") }
                            AddressBar(value = url, onValueChange = { url = it }, onGo = {})
                        }
                    }
                    AddressBar(value = "apple.com", onValueChange = {}, onGo = {})
                    AddressBar(value = "", onValueChange = {}, enabled = false, onGo = {})
                }
            }
        }

        // Over Glass
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Over Glass",
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textSecondary,
            )
            Surface(Surface.OverGlass) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (style in listOf(TitleBarStyle.Unified, TitleBarStyle.UnifiedCompact)) {
                        CompositionLocalProvider(LocalTitleBarStyle provides style) {
                            var url by remember { mutableStateOf("") }
                            AddressBar(value = url, onValueChange = { url = it }, onGo = {})
                        }
                    }
                    AddressBar(value = "apple.com", onValueChange = {}, onGo = {})
                    AddressBar(value = "", onValueChange = {}, enabled = false, onGo = {})
                }
            }
        }
    }
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

        SectionHeader("Surface Variants")
        ExampleCard(
            title = "Content Area vs Over Glass",
            description = "AddressBar adapts its appearance based on Surface",
            sourceCode = GallerySources.AddressBarSurfaceVariantsExample,
        ) { AddressBarSurfaceVariantsExample() }
    }
}
