package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Card
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CardContent
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Tabs
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TabsContent
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TabsList
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TabsTrigger
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSearch
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSettings
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideStar
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("Tabs", "Sizes")
@Composable
fun TabsSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        for (size in ControlSize.entries) {
            var selectedTab by remember { mutableStateOf("account") }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = size.name,
                    style = MacosTheme.typography.caption1,
                    color = MacosTheme.colorScheme.textSecondary,
                )
                ControlSize(size) {
                    Tabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) {
                        TabsList {
                            TabsTrigger(value = "account") { Text("Account") }
                            TabsTrigger(value = "settings") { Text("Settings") }
                            TabsTrigger(value = "notifications") { Text("Notifications") }
                        }
                    }
                }
            }
        }
    }
}

@GalleryExample("Tabs", "With Icons")
@Composable
fun TabsWithIconsExample() {
    var selectedTab by remember { mutableStateOf("search") }
    Tabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) {
        TabsList {
            TabsTrigger(value = "search", icon = { Icon(LucideSearch) }) { Text("Search") }
            TabsTrigger(value = "favorites", icon = { Icon(LucideStar) }) { Text("Favorites") }
            TabsTrigger(value = "settings", icon = { Icon(LucideSettings) }) { Text("Settings") }
        }
        TabsContent(value = "search") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Search across all your content.", color = MacosTheme.colorScheme.textSecondary)
                }
            }
        }
        TabsContent(value = "favorites") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Your starred and bookmarked items.", color = MacosTheme.colorScheme.textSecondary)
                }
            }
        }
        TabsContent(value = "settings") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Manage your preferences.", color = MacosTheme.colorScheme.textSecondary)
                }
            }
        }
    }
}

@Composable
internal fun TabsPage() {
    GalleryPage("Tabs", "A set of layered sections of content, known as tab panels.") {
        SectionHeader("With Icons")
        ExampleCard(title = "With Icons", sourceCode = GallerySources.TabsWithIconsExample) { TabsWithIconsExample() }
    }
}
