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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.components.Card
import io.github.kdroidfilter.darwinui.components.DarwinScaffold
import io.github.kdroidfilter.darwinui.components.IconButton
import io.github.kdroidfilter.darwinui.components.NavigationButtons
import io.github.kdroidfilter.darwinui.components.Sidebar
import io.github.kdroidfilter.darwinui.components.SidebarItem
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TitleBar
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideCalendar
import io.github.kdroidfilter.darwinui.icons.LucideDownload
import io.github.kdroidfilter.darwinui.icons.LucideFolder
import io.github.kdroidfilter.darwinui.icons.LucideHome
import io.github.kdroidfilter.darwinui.icons.LucideMoon
import io.github.kdroidfilter.darwinui.icons.LucidePanelLeft
import io.github.kdroidfilter.darwinui.icons.LucideSettings
import io.github.kdroidfilter.darwinui.icons.LucideStar
import io.github.kdroidfilter.darwinui.icons.LucideSun
import io.github.kdroidfilter.darwinui.icons.LucideTrash2
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Scaffold", "Full Layout")
@Composable
fun ScaffoldFullLayoutExample() {
    var selectedPage by remember { mutableStateOf("Favorites") }
    var sidebarVisible by remember { mutableStateOf(true) }

    val sidebarItems = remember {
        listOf(
            SidebarItem("Favorites", onClick = { selectedPage = "Favorites" }, icon = LucideStar, group = "Favorites"),
            SidebarItem("Recents", onClick = { selectedPage = "Recents" }, icon = LucideCalendar, group = "Favorites"),
            SidebarItem("Documents", onClick = { selectedPage = "Documents" }, icon = LucideFolder, group = "Favorites"),
            SidebarItem("Downloads", onClick = { selectedPage = "Downloads" }, icon = LucideDownload, group = "Locations"),
            SidebarItem("Home", onClick = { selectedPage = "Home" }, icon = LucideHome, group = "Locations"),
            SidebarItem("Trash", onClick = { selectedPage = "Trash" }, icon = LucideTrash2, group = "Locations"),
        )
    }

    Card(modifier = Modifier.fillMaxWidth().height(400.dp)) {
        DarwinScaffold(
            sidebarVisible = sidebarVisible,
            onSidebarVisibleChange = { sidebarVisible = it },
            sidebar = {
                Sidebar(
                    items = sidebarItems,
                    activeItem = selectedPage,
                    showBorder = false,
                    header = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Finder",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarwinTheme.colorScheme.textPrimary,
                                )
                                Text(
                                    text = "File Browser",
                                    style = DarwinTheme.typography.caption1,
                                    color = DarwinTheme.colorScheme.textTertiary,
                                )
                            }
                            IconButton(onClick = { sidebarVisible = false }) {
                                Icon(LucidePanelLeft, modifier = Modifier.size(18.dp))
                            }
                        }
                    },
                )
            },
            titleBar = {
                TitleBar(
                    navigationActions = {
                        NavigationButtons(
                            onBack = {},
                            onForward = {},
                            backEnabled = true,
                            forwardEnabled = false,
                        )
                    },
                    title = { Text(selectedPage) },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(LucideSettings, modifier = Modifier.size(16.dp))
                        }
                    },
                )
            },
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = selectedPage,
                    style = DarwinTheme.typography.callout,
                    fontWeight = FontWeight.Bold,
                    color = DarwinTheme.colorScheme.textPrimary,
                )
                repeat(8) { i ->
                    Text(
                        text = "Content item ${i + 1} — scroll to see glass blur on title bar.",
                        style = DarwinTheme.typography.subheadline,
                        color = DarwinTheme.colorScheme.textSecondary,
                    )
                }
            }
        }
    }
}

@GalleryExample("Scaffold", "No Sidebar")
@Composable
fun ScaffoldNoSidebarExample() {
    var isDark by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth().height(300.dp)) {
        DarwinScaffold(
            titleBar = {
                TitleBar(
                    title = { Text("My App") },
                    actions = {
                        IconButton(onClick = { isDark = !isDark }) {
                            Icon(
                                if (isDark) LucideSun else LucideMoon,
                                modifier = Modifier.size(16.dp),
                            )
                        }
                    },
                )
            },
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(contentPadding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "Scaffold without sidebar",
                    style = DarwinTheme.typography.subheadline,
                    fontWeight = FontWeight.SemiBold,
                    color = DarwinTheme.colorScheme.textPrimary,
                )
                repeat(6) { i ->
                    Text(
                        text = "Line ${i + 1} — title bar overlays content with frosted glass.",
                        style = DarwinTheme.typography.subheadline,
                        color = DarwinTheme.colorScheme.textSecondary,
                    )
                }
            }
        }
    }
}

@Composable
internal fun ScaffoldPage() {
    GalleryPage("Scaffold", "macOS-style scaffold layout with optional sidebar, title bar, and glass blur.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Full Layout",
            description = "Scaffold with sidebar, title bar, navigation buttons, and scrollable content",
            sourceCode = GallerySources.ScaffoldFullLayoutExample,
        ) { ScaffoldFullLayoutExample() }
        ExampleCard(
            title = "No Sidebar",
            description = "Minimal scaffold with only a title bar overlay",
            sourceCode = GallerySources.ScaffoldNoSidebarExample,
        ) { ScaffoldNoSidebarExample() }
    }
}
