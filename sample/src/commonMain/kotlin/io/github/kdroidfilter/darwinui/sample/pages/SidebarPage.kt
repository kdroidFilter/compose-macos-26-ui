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
import io.github.kdroidfilter.darwinui.components.card.DarwinCard
import io.github.kdroidfilter.darwinui.components.sidebar.DarwinSidebar
import io.github.kdroidfilter.darwinui.components.sidebar.DarwinSidebarItem
import io.github.kdroidfilter.darwinui.components.switchcomponent.DarwinSwitch
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.LucideBarChart3
import io.github.kdroidfilter.darwinui.icons.LucideFolder
import io.github.kdroidfilter.darwinui.icons.LucideHome
import io.github.kdroidfilter.darwinui.icons.LucideSettings
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Sidebar", "Preview")
@Composable
fun SidebarPreviewExample() {
    var activeItem by remember { mutableStateOf("Dashboard") }
    var isCollapsed by remember { mutableStateOf(false) }
    val sidebarItems = remember {
        listOf(
            DarwinSidebarItem("Dashboard", onClick = { activeItem = "Dashboard" }, icon = LucideHome),
            DarwinSidebarItem("Projects", onClick = { activeItem = "Projects" }, icon = LucideFolder),
            DarwinSidebarItem("Analytics", onClick = { activeItem = "Analytics" }, icon = LucideBarChart3),
            DarwinSidebarItem("Settings", onClick = { activeItem = "Settings" }, icon = LucideSettings),
        )
    }
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DarwinSwitch(checked = isCollapsed, onCheckedChange = { isCollapsed = it })
            DarwinText(text = if (isCollapsed) "Collapsed" else "Expanded", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.textSecondary)
        }
        DarwinCard(modifier = Modifier.fillMaxWidth().height(320.dp)) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.fillMaxHeight().background(DarwinTheme.colors.muted)) {
                    DarwinSidebar(items = sidebarItems, activeItem = activeItem, onLogout = { activeItem = "Logged out" }, collapsed = isCollapsed, onCollapsedChange = { isCollapsed = it }, collapsible = true)
                }
                Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(DarwinTheme.colors.border))
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        DarwinText(text = "Current page:", color = DarwinTheme.colors.textSecondary, style = DarwinTheme.typography.bodySmall)
                        DarwinText(text = activeItem, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
        DarwinText(text = "Use the toggle or click \"Collapse\" in the sidebar.", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary.copy(alpha = 0.70f), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    }
}

@GalleryExample("Sidebar", "Collapsible")
@Composable
fun SidebarCollapsibleExample() {
    var active by remember { mutableStateOf("Dashboard") }
    var collapsed by remember { mutableStateOf(false) }
    val items = remember {
        listOf(
            DarwinSidebarItem("Dashboard", onClick = { active = "Dashboard" }, icon = LucideHome),
            DarwinSidebarItem("Projects", onClick = { active = "Projects" }, icon = LucideFolder),
            DarwinSidebarItem("Settings", onClick = { active = "Settings" }, icon = LucideSettings),
        )
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinSwitch(checked = collapsed, onCheckedChange = { collapsed = it }, label = "Collapsed")
        DarwinCard(modifier = Modifier.fillMaxWidth().height(192.dp)) {
            Row(modifier = Modifier.fillMaxSize()) {
                DarwinSidebar(items = items, activeItem = active, onLogout = {}, collapsed = collapsed, onCollapsedChange = { collapsed = it }, collapsible = true)
                Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp), contentAlignment = Alignment.Center) {
                    DarwinText(text = "Current: $active", color = DarwinTheme.colors.textSecondary)
                }
            }
        }
    }
}

@Composable
internal fun SidebarPage() {
    GalleryPage("Sidebar", "A macOS-style navigation sidebar with collapsible state.") {
        SectionHeader("Examples")
        ExampleCard(title = "Preview", sourceCode = GallerySources.SidebarPreviewExample) { SidebarPreviewExample() }
        ExampleCard(title = "Collapsible", description = "Sidebar with collapse toggle", sourceCode = GallerySources.SidebarCollapsibleExample) { SidebarCollapsibleExample() }
    }
}
