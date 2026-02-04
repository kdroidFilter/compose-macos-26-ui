package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.card.DarwinCard
import io.github.kdroidfilter.darwinui.components.card.DarwinCardContent
import io.github.kdroidfilter.darwinui.components.tabs.DarwinTabs
import io.github.kdroidfilter.darwinui.components.tabs.DarwinTabsContent
import io.github.kdroidfilter.darwinui.components.tabs.DarwinTabsList
import io.github.kdroidfilter.darwinui.components.tabs.DarwinTabsTrigger
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.DarwinIcon
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
    DarwinTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) {
        DarwinTabsList {
            DarwinTabsTrigger(value = "account") { DarwinText("Account") }
            DarwinTabsTrigger(value = "settings") { DarwinText("Settings") }
            DarwinTabsTrigger(value = "notifications") { DarwinText("Notifications") }
        }
        DarwinTabsContent(value = "account") { DarwinCard { DarwinCardContent(modifier = Modifier.padding(top = 24.dp)) { DarwinText("Account settings and profile information.", color = DarwinTheme.colors.textSecondary) } } }
        DarwinTabsContent(value = "settings") { DarwinCard { DarwinCardContent(modifier = Modifier.padding(top = 24.dp)) { DarwinText("Application preferences and configuration.", color = DarwinTheme.colors.textSecondary) } } }
        DarwinTabsContent(value = "notifications") { DarwinCard { DarwinCardContent(modifier = Modifier.padding(top = 24.dp)) { DarwinText("Notification preferences and history.", color = DarwinTheme.colors.textSecondary) } } }
    }
}

@GalleryExample("Tabs", "With Icons")
@Composable
fun TabsWithIconsExample() {
    var selectedTab by remember { mutableStateOf("search") }
    DarwinTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) {
        DarwinTabsList {
            DarwinTabsTrigger(value = "search", icon = { DarwinIcon(LucideSearch) }) { DarwinText("Search") }
            DarwinTabsTrigger(value = "favorites", icon = { DarwinIcon(LucideStar) }) { DarwinText("Favorites") }
            DarwinTabsTrigger(value = "settings", icon = { DarwinIcon(LucideSettings) }) { DarwinText("Settings") }
        }
        DarwinTabsContent(value = "search") { DarwinCard { DarwinCardContent(modifier = Modifier.padding(top = 24.dp)) { DarwinText("Search across all your content.", color = DarwinTheme.colors.textSecondary) } } }
        DarwinTabsContent(value = "favorites") { DarwinCard { DarwinCardContent(modifier = Modifier.padding(top = 24.dp)) { DarwinText("Your starred and bookmarked items.", color = DarwinTheme.colors.textSecondary) } } }
        DarwinTabsContent(value = "settings") { DarwinCard { DarwinCardContent(modifier = Modifier.padding(top = 24.dp)) { DarwinText("Manage your preferences.", color = DarwinTheme.colors.textSecondary) } } }
    }
}

@Composable
internal fun TabsPage() {
    GalleryPage("Tabs", "A set of layered sections of content, known as tab panels.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.TabsDefaultExample) { TabsDefaultExample() }
        ExampleCard(title = "With Icons", sourceCode = GallerySources.TabsWithIconsExample) { TabsWithIconsExample() }
    }
}
