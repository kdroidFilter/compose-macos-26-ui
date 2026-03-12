package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Card
import io.github.kdroidfilter.darwinui.components.CardContent
import io.github.kdroidfilter.darwinui.components.TabSize
import io.github.kdroidfilter.darwinui.components.Tabs
import io.github.kdroidfilter.darwinui.components.TabsContent
import io.github.kdroidfilter.darwinui.components.TabsList
import io.github.kdroidfilter.darwinui.components.TabsTrigger
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideSearch
import io.github.kdroidfilter.darwinui.icons.LucideSettings
import io.github.kdroidfilter.darwinui.icons.LucideStar
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Tabs", "Default")
@Composable
fun TabsDefaultExample() {
    var selectedTab by remember { mutableStateOf("account") }
    Tabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) {
        TabsList {
            TabsTrigger(value = "account") { Text("Account") }
            TabsTrigger(value = "settings") { Text("Settings") }
            TabsTrigger(value = "notifications") { Text("Notifications") }
        }
        TabsContent(value = "account") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Account settings and profile information.", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
        TabsContent(value = "settings") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Application preferences and configuration.", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
        TabsContent(value = "notifications") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Notification preferences and history.", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
    }
}

@GalleryExample("Tabs", "Small")
@Composable
fun TabsSmallExample() {
    var selectedTab by remember { mutableStateOf("account") }
    Tabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) {
        TabsList(size = TabSize.Sm) {
            TabsTrigger(value = "account") { Text("Account") }
            TabsTrigger(value = "settings") { Text("Settings") }
            TabsTrigger(value = "notifications") { Text("Notifications") }
        }
        TabsContent(value = "account") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Account settings and profile information.", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
        TabsContent(value = "settings") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Application preferences and configuration.", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
        TabsContent(value = "notifications") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Notification preferences and history.", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
    }
}

@GalleryExample("Tabs", "Large")
@Composable
fun TabsLargeExample() {
    var selectedTab by remember { mutableStateOf("search") }
    Tabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) {
        TabsList(size = TabSize.Lg) {
            TabsTrigger(value = "search", icon = { Icon(LucideSearch) }) { Text("Search") }
            TabsTrigger(value = "favorites", icon = { Icon(LucideStar) }) { Text("Favorites") }
            TabsTrigger(value = "settings", icon = { Icon(LucideSettings) }) { Text("Settings") }
        }
        TabsContent(value = "search") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Search across all your content.", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
        TabsContent(value = "favorites") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Your starred and bookmarked items.", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
        TabsContent(value = "settings") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Manage your preferences.", color = DarwinTheme.colorScheme.textSecondary)
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
                    Text("Search across all your content.", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
        TabsContent(value = "favorites") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Your starred and bookmarked items.", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
        TabsContent(value = "settings") {
            Card {
                CardContent(modifier = Modifier.padding(top = 24.dp)) {
                    Text("Manage your preferences.", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
    }
}

@Composable
internal fun TabsPage() {
    GalleryPage("Tabs", "A set of layered sections of content, known as tab panels.") {
        SectionHeader("Sizes")
        ExampleCard(title = "Small", sourceCode = GallerySources.TabsSmallExample) { TabsSmallExample() }
        ExampleCard(title = "Default (Medium)", sourceCode = GallerySources.TabsDefaultExample) { TabsDefaultExample() }
        ExampleCard(title = "Large", sourceCode = GallerySources.TabsLargeExample) { TabsLargeExample() }
        SectionHeader("With Icons")
        ExampleCard(title = "With Icons", sourceCode = GallerySources.TabsWithIconsExample) { TabsWithIconsExample() }
    }
}
