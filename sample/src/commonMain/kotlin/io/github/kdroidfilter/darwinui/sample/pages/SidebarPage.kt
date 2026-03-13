package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Card
import io.github.kdroidfilter.darwinui.components.Sidebar
import io.github.kdroidfilter.darwinui.components.SidebarItem
import io.github.kdroidfilter.darwinui.components.Switcher
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.LucideBarChart3
import io.github.kdroidfilter.darwinui.icons.LucideCalendar
import io.github.kdroidfilter.darwinui.icons.LucideDownload
import io.github.kdroidfilter.darwinui.icons.LucideFolder
import io.github.kdroidfilter.darwinui.icons.LucideHome
import io.github.kdroidfilter.darwinui.icons.LucideSettings
import io.github.kdroidfilter.darwinui.icons.LucideStar
import io.github.kdroidfilter.darwinui.icons.LucideTrash2
import io.github.kdroidfilter.darwinui.icons.LucideUpload
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Sidebar", "Preview")
@Composable
fun SidebarPreviewExample() {
    var activeItem by remember { mutableStateOf("Favorites") }
    var isCollapsed by remember { mutableStateOf(false) }
    val sidebarItems = remember {
        listOf(
            SidebarItem("Favorites", onClick = { activeItem = "Favorites" }, icon = LucideStar, group = "Favorites"),
            SidebarItem("Recents", onClick = { activeItem = "Recents" }, icon = LucideCalendar, group = "Favorites"),
            SidebarItem("iCloud Shared", onClick = { activeItem = "iCloud Shared" }, icon = LucideFolder, group = "Favorites"),
            SidebarItem("Documents", onClick = { activeItem = "Documents" }, icon = LucideFolder, group = "Favorites"),
            SidebarItem("Downloads", onClick = { activeItem = "Downloads" }, icon = LucideDownload, group = "Favorites"),
            SidebarItem("iCloud Drive", onClick = { activeItem = "iCloud Drive" }, icon = LucideUpload, group = "Locations"),
            SidebarItem("Home", onClick = { activeItem = "Home" }, icon = LucideHome, group = "Locations"),
            SidebarItem("Mac HD", onClick = { activeItem = "Mac HD" }, icon = LucideBarChart3, group = "Locations"),
            SidebarItem("Trash", onClick = { activeItem = "Trash" }, icon = LucideTrash2, group = "Locations"),
        )
    }
    val pinnedItems = remember {
        listOf(SidebarItem("Settings", onClick = { activeItem = "Settings" }, icon = LucideSettings))
    }
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Switcher(checked = isCollapsed, onCheckedChange = { isCollapsed = it })
            Text(
                text = if (isCollapsed) "Collapsed" else "Expanded",
                style = DarwinTheme.typography.caption1,
                color = DarwinTheme.colorScheme.textSecondary,
            )
        }
        Card(modifier = Modifier.fillMaxWidth().height(320.dp)) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxHeight().background(DarwinTheme.colorScheme.muted)) {
                    Sidebar(
                        items = sidebarItems,
                        pinnedItems = pinnedItems,
                        activeItem = activeItem,
                        onLogout = { activeItem = "Logged out" },
                        collapsed = isCollapsed,
                        onCollapsedChange = { isCollapsed = it },
                        collapsible = true,
                    )
                }
                Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(DarwinTheme.colorScheme.border))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Current page:",
                            color = DarwinTheme.colorScheme.textSecondary,
                            style = DarwinTheme.typography.caption1,
                        )
                        Text(text = activeItem, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
        Text(
            text = "Use the toggle or click \"Collapse\" in the sidebar.",
            style = DarwinTheme.typography.caption2,
            color = DarwinTheme.colorScheme.textTertiary.copy(alpha = 0.70f),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }
}

@GalleryExample("Sidebar", "Collapsible")
@Composable
fun SidebarCollapsibleExample() {
    var active by remember { mutableStateOf("Favorites") }
    var collapsed by remember { mutableStateOf(false) }
    val items = remember {
        listOf(
            SidebarItem("Favorites", onClick = { active = "Favorites" }, icon = LucideStar, group = "Favorites"),
            SidebarItem("Recents", onClick = { active = "Recents" }, icon = LucideCalendar, group = "Favorites"),
            SidebarItem("Home", onClick = { active = "Home" }, icon = LucideHome, group = "Locations"),
            SidebarItem("Trash", onClick = { active = "Trash" }, icon = LucideTrash2, group = "Locations"),
        )
    }
    val pinnedItems = remember {
        listOf(SidebarItem("Settings", onClick = { active = "Settings" }, icon = LucideSettings))
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Switcher(checked = collapsed, onCheckedChange = { collapsed = it }, label = "Collapsed")
        Card(modifier = Modifier.fillMaxWidth().height(192.dp)) {
            Row(modifier = Modifier.fillMaxSize()) {
                Sidebar(
                    items = items,
                    pinnedItems = pinnedItems,
                    activeItem = active,
                    onLogout = {},
                    collapsed = collapsed,
                    onCollapsedChange = { collapsed = it },
                    collapsible = true,
                )
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp), contentAlignment = Alignment.Center) {
                    Text(text = "Current: $active", color = DarwinTheme.colorScheme.textSecondary)
                }
            }
        }
    }
}

@GalleryExample("Sidebar", "Disclosure Groups")
@Composable
fun SidebarDisclosureExample() {
    var active by remember { mutableStateOf("Inbox") }
    val items = remember {
        listOf(
            SidebarItem(
                "Mailboxes", onClick = {}, icon = LucideFolder, group = "Mail",
                children = listOf(
                    SidebarItem("Inbox", onClick = { active = "Inbox" }, icon = LucideStar),
                    SidebarItem("Sent", onClick = { active = "Sent" }, icon = LucideCalendar),
                    SidebarItem("Trash", onClick = { active = "Trash" }, icon = LucideTrash2),
                ),
            ),
            SidebarItem(
                "Smart Mailboxes", onClick = {}, icon = LucideSettings, group = "Mail",
                children = listOf(
                    SidebarItem("Unread", onClick = { active = "Unread" }, icon = LucideHome),
                    SidebarItem("Flagged", onClick = { active = "Flagged" }, icon = LucideStar),
                ),
            ),
            SidebarItem("Downloads", onClick = { active = "Downloads" }, icon = LucideDownload, group = "Locations"),
            SidebarItem("Documents", onClick = { active = "Documents" }, icon = LucideFolder, group = "Locations"),
        )
    }
    Card(modifier = Modifier.fillMaxWidth().height(350.dp)) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxHeight().background(DarwinTheme.colorScheme.muted)) {
                Sidebar(
                    items = items,
                    activeItem = active,
                )
            }
            Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(DarwinTheme.colorScheme.border))
            Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp), contentAlignment = Alignment.Center) {
                Text(text = "Selected: $active", color = DarwinTheme.colorScheme.textSecondary)
            }
        }
    }
}

@Composable
internal fun SidebarPage() {
    GalleryPage("Sidebar", "A macOS-style navigation sidebar with collapsible state.") {
        SectionHeader("Examples")
        ExampleCard(title = "Preview", sourceCode = GallerySources.SidebarPreviewExample) { SidebarPreviewExample() }
        ExampleCard(
            title = "Collapsible",
            description = "Sidebar with collapse toggle",
            sourceCode = GallerySources.SidebarCollapsibleExample,
        ) { SidebarCollapsibleExample() }
        ExampleCard(
            title = "Disclosure Groups",
            description = "Sidebar with collapsible children using disclosure chevrons",
            sourceCode = GallerySources.SidebarDisclosureExample,
        ) { SidebarDisclosureExample() }
    }
}
