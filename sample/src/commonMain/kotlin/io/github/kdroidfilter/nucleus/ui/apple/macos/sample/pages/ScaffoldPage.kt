package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Card
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColumnVisibility
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColumnWidth
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Scaffold
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.NavigationButtons
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBarButtonGroup
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBarGroupButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Sidebar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SidebarItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBarStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideCalendar
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideDownload
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideFolder
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideHome
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideMoon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucidePanelLeft
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSettings
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideStar
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSun
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideTrash2
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideInfo
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("Scaffold", "Full Layout")
@Composable
fun ScaffoldFullLayoutExample() {
    var selectedPage by remember { mutableStateOf("Favorites") }
    var columnVisibility by remember { mutableStateOf(ColumnVisibility.All) }

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
        Scaffold(
            columnVisibility = columnVisibility,
            onColumnVisibilityChange = { columnVisibility = it },
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
                                    color = MacosTheme.colorScheme.textPrimary,
                                )
                                Text(
                                    text = "File Browser",
                                    style = MacosTheme.typography.caption1,
                                    color = MacosTheme.colorScheme.textTertiary,
                                )
                            }
                            Icon(
                                LucidePanelLeft,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable(
                                        interactionSource = null,
                                        indication = null,
                                        onClick = { columnVisibility = ColumnVisibility.DoubleColumn },
                                    ),
                            )
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
                        TitleBarButtonGroup {
                            TitleBarGroupButton(onClick = {}) {
                                Icon(LucideSettings, modifier = Modifier.size(16.dp))
                            }
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
                    style = MacosTheme.typography.callout,
                    fontWeight = FontWeight.Bold,
                    color = MacosTheme.colorScheme.textPrimary,
                )
                repeat(8) { i ->
                    Text(
                        text = "Content item ${i + 1} — scroll to see glass blur on title bar.",
                        style = MacosTheme.typography.subheadline,
                        color = MacosTheme.colorScheme.textSecondary,
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
        Scaffold(
            titleBar = {
                TitleBar(
                    title = { Text("My App") },
                    actions = {
                        TitleBarButtonGroup {
                            TitleBarGroupButton(onClick = { isDark = !isDark }) {
                                Icon(
                                    if (isDark) LucideSun else LucideMoon,
                                    modifier = Modifier.size(16.dp),
                                )
                            }
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
                    style = MacosTheme.typography.subheadline,
                    fontWeight = FontWeight.SemiBold,
                    color = MacosTheme.colorScheme.textPrimary,
                )
                repeat(6) { i ->
                    Text(
                        text = "Line ${i + 1} — title bar overlays content with frosted glass.",
                        style = MacosTheme.typography.subheadline,
                        color = MacosTheme.colorScheme.textSecondary,
                    )
                }
            }
        }
    }
}

@GalleryExample("Scaffold", "Three Columns")
@Composable
fun ScaffoldThreeColumnsExample() {
    var columnVisibility by remember { mutableStateOf(ColumnVisibility.All) }
    var selectedMailbox by remember { mutableStateOf("Inbox") }
    var selectedMessage by remember { mutableStateOf("Welcome") }

    val sidebarItems = remember {
        listOf(
            SidebarItem(
                "Inbox", onClick = { selectedMailbox = "Inbox" }, icon = LucideFolder,
                group = "Mailboxes",
                children = listOf(
                    SidebarItem("Primary", onClick = { selectedMailbox = "Primary" }, icon = LucideStar),
                    SidebarItem("Updates", onClick = { selectedMailbox = "Updates" }, icon = LucideInfo),
                ),
            ),
            SidebarItem("Sent", onClick = { selectedMailbox = "Sent" }, icon = LucideFolder, group = "Mailboxes"),
            SidebarItem("Trash", onClick = { selectedMailbox = "Trash" }, icon = LucideTrash2, group = "Mailboxes"),
        )
    }

    Card(modifier = Modifier.fillMaxWidth().height(450.dp)) {
        Scaffold(
            columnVisibility = columnVisibility,
            onColumnVisibilityChange = { columnVisibility = it },
            sidebar = {
                Sidebar(
                    items = sidebarItems,
                    activeItem = selectedMailbox,
                    showBorder = false,
                )
            },
            sidebarWidth = ColumnWidth.Flexible(min = 150.dp, ideal = 200.dp, max = 300.dp),
            contentList = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    listOf("Welcome", "Meeting Notes", "Project Update", "Newsletter", "Reminder").forEach { msg ->
                        Text(
                            text = msg,
                            style = MacosTheme.typography.subheadline,
                            fontWeight = if (msg == selectedMessage) FontWeight.Bold else FontWeight.Normal,
                            color = if (msg == selectedMessage) MacosTheme.colorScheme.accent
                            else MacosTheme.colorScheme.textPrimary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedMessage = msg }
                                .padding(8.dp),
                        )
                    }
                }
            },
            contentListWidth = ColumnWidth.Flexible(min = 180.dp, ideal = 220.dp, max = 350.dp),
            titleBar = {
                TitleBar(
                    style = TitleBarStyle.UnifiedCompact,
                    title = { Text(selectedMailbox) },
                )
            },
            titleBarStyle = TitleBarStyle.UnifiedCompact,
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
                    text = selectedMessage,
                    style = MacosTheme.typography.title3,
                    fontWeight = FontWeight.Bold,
                    color = MacosTheme.colorScheme.textPrimary,
                )
                Text(
                    text = "3-column layout with resizable sidebar and content list. " +
                        "Drag the dividers to resize columns. Double-click to reset.",
                    style = MacosTheme.typography.subheadline,
                    color = MacosTheme.colorScheme.textSecondary,
                )
                repeat(6) { i ->
                    Text(
                        text = "Message content line ${i + 1}.",
                        style = MacosTheme.typography.body,
                        color = MacosTheme.colorScheme.textSecondary,
                    )
                }
            }
        }
    }
}

@GalleryExample("Scaffold", "Inspector")
@Composable
fun ScaffoldInspectorExample() {
    var inspectorVisible by remember { mutableStateOf(true) }

    Card(modifier = Modifier.fillMaxWidth().height(400.dp)) {
        Scaffold(
            inspector = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = "Inspector",
                        style = MacosTheme.typography.headline,
                        color = MacosTheme.colorScheme.textPrimary,
                    )
                    Text(
                        text = "Properties and details for the selected item.",
                        style = MacosTheme.typography.subheadline,
                        color = MacosTheme.colorScheme.textSecondary,
                    )
                    repeat(4) { i ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                "Property ${i + 1}",
                                style = MacosTheme.typography.caption1,
                                color = MacosTheme.colorScheme.textTertiary,
                            )
                            Text(
                                "Value ${i + 1}",
                                style = MacosTheme.typography.caption1,
                                color = MacosTheme.colorScheme.textPrimary,
                            )
                        }
                    }
                }
            },
            inspectorVisible = inspectorVisible,
            onInspectorVisibleChange = { inspectorVisible = it },
            titleBar = {
                TitleBar(
                    title = { Text("Document") },
                    actions = {
                        TitleBarButtonGroup {
                            TitleBarGroupButton(onClick = { inspectorVisible = !inspectorVisible }) {
                                Icon(LucideInfo, modifier = Modifier.size(16.dp))
                            }
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
                    text = "Main Content",
                    style = MacosTheme.typography.title3,
                    fontWeight = FontWeight.Bold,
                    color = MacosTheme.colorScheme.textPrimary,
                )
                Text(
                    text = "Right-side inspector panel. Toggle with the info button. " +
                        "Drag the divider to resize.",
                    style = MacosTheme.typography.subheadline,
                    color = MacosTheme.colorScheme.textSecondary,
                )
                repeat(8) { i ->
                    Text(
                        text = "Content line ${i + 1}.",
                        style = MacosTheme.typography.body,
                        color = MacosTheme.colorScheme.textSecondary,
                    )
                }
            }
        }
    }
}

@GalleryExample("Scaffold", "Bottom Bar")
@Composable
fun ScaffoldBottomBarExample() {
    Card(modifier = Modifier.fillMaxWidth().height(350.dp)) {
        Scaffold(
            titleBar = {
                TitleBar(
                    title = { Text("Player") },
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Now Playing: Song Title",
                        style = MacosTheme.typography.caption1,
                        color = MacosTheme.colorScheme.textPrimary,
                    )
                    Text(
                        text = "3:42 / 5:10",
                        style = MacosTheme.typography.caption1,
                        color = MacosTheme.colorScheme.textTertiary,
                    )
                }
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
                    text = "Bottom Bar",
                    style = MacosTheme.typography.title3,
                    fontWeight = FontWeight.Bold,
                    color = MacosTheme.colorScheme.textPrimary,
                )
                Text(
                    text = "Glass bottom bar overlay with status content. " +
                        "Scroll to see the glass blur effect.",
                    style = MacosTheme.typography.subheadline,
                    color = MacosTheme.colorScheme.textSecondary,
                )
                repeat(10) { i ->
                    Text(
                        text = "Track ${i + 1} — Playlist item",
                        style = MacosTheme.typography.body,
                        color = MacosTheme.colorScheme.textSecondary,
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
        ExampleCard(
            title = "Three Columns",
            description = "Mail-style 3-column layout with sidebar, content list, and detail",
            sourceCode = GallerySources.ScaffoldThreeColumnsExample,
        ) { ScaffoldThreeColumnsExample() }
        ExampleCard(
            title = "Inspector",
            description = "Right-side inspector panel with toggle and draggable divider",
            sourceCode = GallerySources.ScaffoldInspectorExample,
        ) { ScaffoldInspectorExample() }
        ExampleCard(
            title = "Bottom Bar",
            description = "Glass bottom bar overlay for status content",
            sourceCode = GallerySources.ScaffoldBottomBarExample,
        ) { ScaffoldBottomBarExample() }
    }
}
