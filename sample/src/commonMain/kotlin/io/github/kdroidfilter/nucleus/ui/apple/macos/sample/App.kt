package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import com.composables.icons.lucide.Monitor
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.drop
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Calendar
import com.composables.icons.lucide.ChevronsUpDown
import com.composables.icons.lucide.CircleDot
import com.composables.icons.lucide.CircleUser
import com.composables.icons.lucide.Columns3
import com.composables.icons.lucide.CreditCard
import com.composables.icons.lucide.Ellipsis
import com.composables.icons.lucide.Github
import com.composables.icons.lucide.GripVertical
import com.composables.icons.lucide.Layers
import com.composables.icons.lucide.LayoutList
import com.composables.icons.lucide.ListChecks
import com.composables.icons.lucide.Loader
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.MessageCircle
import com.composables.icons.lucide.MessageSquare
import com.composables.icons.lucide.MousePointerClick
import com.composables.icons.lucide.PanelTopOpen
import com.composables.icons.lucide.Scan
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.Ruler
import com.composables.icons.lucide.SlidersHorizontal
import com.composables.icons.lucide.SquareCheck
import com.composables.icons.lucide.SquareDashed
import com.composables.icons.lucide.Table
import com.composables.icons.lucide.Tag
import com.composables.icons.lucide.TextAlignStart
import com.composables.icons.lucide.TextCursorInput
import com.composables.icons.lucide.ToggleLeft
import com.composables.icons.lucide.TriangleAlert
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColumnVisibility
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColumnWidth
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.NavigationButtons
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Popover
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PopoverPlacement
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Scaffold
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SearchSuggestionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SearchSuggestionItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SearchSuggestionSeparator
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SegmentedControl
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Sidebar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SidebarItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Switch
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBarButtonGroup
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TitleBarGroupButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ToastHost
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ToolbarSearchField
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TrackClickBehavior
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.VerticalScrollbar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.rememberScrollbarState
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.rememberToastState
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideHome
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideMoon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSettings
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSun
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.RadixPanelLeft
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.AccordionPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.AddressBarPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.AlertPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.AvatarPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.BadgePage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.ButtonPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.CardPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.CheckboxPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.CircularSliderPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.ColorWellPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.ComboBoxPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.ContextMenuPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.ControlSizePage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.DatePickerPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.DialogPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.DropdownMenuPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.FormPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.GettingStartedPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.GroupBoxPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.GroupedListPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.HomePage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.IconButtonPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.IconsPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.InputPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.LicensePage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.MaterialPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.MultiSelectPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.PageControlPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.PopoverPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.PopupButtonPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.ProgressPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.RadioButtonPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.ScaffoldPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.ScrollbarPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.SearchInputPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.SegmentedControlPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.SidebarPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.SkeletonPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.SurfacePage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.SliderPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.StepperPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.SwitchPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.TablePage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.TabsPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.TextAreaPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.TitleBarPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.ToastPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.TooltipPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.AccentColor
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.GlassType
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.VibrantColors
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.vibrant

internal enum class ThemeMode { System, Light, Dark }

// Navigation data
internal data class SidebarEntryDef(val id: String, val label: String, val group: String, val icon: ImageVector)

internal val sidebarEntryDefs = listOf(
    SidebarEntryDef("home", "Home", "GENERAL", LucideHome),
    SidebarEntryDef("getting-started", "Getting Started", "GENERAL", Lucide.LayoutList),
    SidebarEntryDef("license", "License", "GENERAL", Lucide.Tag),
    SidebarEntryDef("button", "Button", "FORM CONTROLS", Lucide.MousePointerClick),
    SidebarEntryDef("iconbutton", "Icon Button", "FORM CONTROLS", Lucide.CircleDot),
    SidebarEntryDef("input", "Input", "FORM CONTROLS", Lucide.TextCursorInput),
    SidebarEntryDef("textarea", "Textarea", "FORM CONTROLS", Lucide.TextAlignStart),
    SidebarEntryDef("checkbox", "Checkbox", "FORM CONTROLS", Lucide.SquareCheck),
    SidebarEntryDef("radiobutton", "Radio Button", "FORM CONTROLS", Lucide.CircleDot),
    SidebarEntryDef("switch", "Switch", "FORM CONTROLS", Lucide.ToggleLeft),
    SidebarEntryDef("combobox", "Combo Box", "FORM CONTROLS", Lucide.ChevronsUpDown),
    SidebarEntryDef("multiselect", "Multi Select", "FORM CONTROLS", Lucide.ListChecks),
    SidebarEntryDef("searchinput", "Search Input", "FORM CONTROLS", Lucide.Search),
    SidebarEntryDef("slider", "Slider", "FORM CONTROLS", Lucide.SlidersHorizontal),
    SidebarEntryDef("circularslider", "Circular Slider", "FORM CONTROLS", Lucide.Loader),
    SidebarEntryDef("stepper", "Stepper", "FORM CONTROLS", Lucide.ChevronsUpDown),
    SidebarEntryDef("popupbutton", "Pop-up Button", "FORM CONTROLS", Lucide.ChevronsUpDown),
    SidebarEntryDef("datepicker", "Date Picker", "FORM CONTROLS", Lucide.Calendar),
    SidebarEntryDef("colorwell", "Color Wells & Pickers", "FORM CONTROLS", Lucide.Scan),
    SidebarEntryDef("scrollbar", "Scrollbar", "DATA DISPLAY", Lucide.GripVertical),
    SidebarEntryDef("groupbox", "Group Box", "DATA DISPLAY", Lucide.SquareDashed),
    SidebarEntryDef("groupedlist", "Grouped List", "DATA DISPLAY", Lucide.ListChecks),
    SidebarEntryDef("form", "Form", "DATA DISPLAY", Lucide.LayoutList),
    SidebarEntryDef("badge", "Badge", "DATA DISPLAY", Lucide.Tag),
    SidebarEntryDef("avatar", "Avatar", "DATA DISPLAY", Lucide.CircleUser),
    SidebarEntryDef("surface", "Surface", "DATA DISPLAY", Lucide.Layers),
    SidebarEntryDef("card", "Card", "DATA DISPLAY", Lucide.CreditCard),
    SidebarEntryDef("table", "Table", "DATA DISPLAY", Lucide.Table),
    SidebarEntryDef("pagecontrol", "Page Control", "DATA DISPLAY", Lucide.CircleDot),
    SidebarEntryDef("progress", "Progress", "DATA DISPLAY", Lucide.Loader),
    SidebarEntryDef("skeleton", "Skeleton", "DATA DISPLAY", Lucide.Scan),
    SidebarEntryDef("alert", "Alert", "FEEDBACK", Lucide.TriangleAlert),
    SidebarEntryDef("toast", "Toast", "FEEDBACK", Lucide.Bell),
    SidebarEntryDef("dialog", "Dialog", "OVERLAYS", Lucide.MessageSquare),
    SidebarEntryDef("tooltip", "Tooltip", "OVERLAYS", Lucide.MessageCircle),
    SidebarEntryDef("popover", "Popover", "OVERLAYS", Lucide.PanelTopOpen),
    SidebarEntryDef("dropdown", "Dropdown Menu", "OVERLAYS", Lucide.Menu),
    SidebarEntryDef("contextmenu", "Context Menu", "OVERLAYS", Lucide.Ellipsis),
    SidebarEntryDef("tabs", "Tabs", "NAVIGATION", Lucide.Columns3),
    SidebarEntryDef("accordion", "Accordion", "NAVIGATION", Lucide.ChevronsUpDown),
    SidebarEntryDef("sidebar", "Sidebar", "NAVIGATION", RadixPanelLeft),
    SidebarEntryDef("segmentedcontrol", "Segmented Control", "FORM CONTROLS", Lucide.Columns3),
    SidebarEntryDef("titlebar", "Title Bar", "NAVIGATION", Lucide.PanelTopOpen),
    SidebarEntryDef("addressbar", "Address Bar", "NAVIGATION", Lucide.Search),
    SidebarEntryDef("scaffold", "Scaffold", "NAVIGATION", RadixPanelLeft),
    SidebarEntryDef("icons", "Icons", "THEME", Lucide.Scan),
    SidebarEntryDef("material", "Material", "THEME", Lucide.Scan),
    SidebarEntryDef("controlsize", "Control Size", "THEME", Lucide.SlidersHorizontal),
)

@Composable
fun App() {
    var themeMode by remember { mutableStateOf(ThemeMode.System) }
    val systemDark = isSystemDarkMode()
    val isDark = when (themeMode) {
        ThemeMode.System -> systemDark
        ThemeMode.Light -> false
        ThemeMode.Dark -> true
    }
    val systemRawColor = systemAccentRawColor()
    var overriddenAccent by remember { mutableStateOf<AccentColor?>(null) }
    val accentColor = overriddenAccent ?: AccentColor.Blue
    var sidebarControlSize by remember { mutableStateOf(ControlSize.Regular) }
    var isVibrant by remember { mutableStateOf(false) }
    var glassType by remember { mutableStateOf(GlassType.Regular) }

    val baseColorScheme = if (isDark) {
        io.github.kdroidfilter.nucleus.ui.apple.macos.theme.darkColorScheme(accentColor)
    } else {
        io.github.kdroidfilter.nucleus.ui.apple.macos.theme.lightColorScheme(accentColor)
    }
    // Override accent with exact system color when no manual override is set
    val withSystemColor = if (systemRawColor != null && overriddenAccent == null) {
        baseColorScheme.copy(
            accent = systemRawColor,
            info = systemRawColor,
            ring = systemRawColor,
            inputFocusBorder = systemRawColor,
            surfaceTint = systemRawColor,
            tertiary = systemRawColor,
        )
    } else {
        baseColorScheme
    }
    val colorScheme = if (isVibrant) {
        val vibrant = if (isDark) VibrantColors.dark() else VibrantColors.light()
        withSystemColor.vibrant(vibrant, accentColor)
    } else {
        withSystemColor
    }

    MacosTheme(darkTheme = isDark, accentColor = accentColor, colorScheme = colorScheme, glassType = glassType) {
        val toastState = rememberToastState()

        // Navigation state — back stack + forward stack for browser-like history
        val backStack = remember { mutableStateListOf<AppNavKey>(HomeScreen) }
        val forwardStack = remember { mutableStateListOf<AppNavKey>() }
        val nav = remember { NavigationState(backStack, forwardStack) }

        // On web, ChronologicalBrowserNavigation syncs URL ↔ back stack
        // On other platforms, this is a no-op
        BrowserNavigation(backStack)

        var searchQuery by remember { mutableStateOf("") }
        var searchExpanded by remember { mutableStateOf(false) }
        var settingsExpanded by remember { mutableStateOf(false) }
        var inspectorVisible by remember { mutableStateOf(false) }

        // Navigation helpers
        val currentPageLabel = sidebarEntryDefs.firstOrNull { it.id == nav.currentPageId }?.label ?: ""

        val sidebarItems = sidebarEntryDefs.map { def ->
            SidebarItem(
                label = def.label,
                onClick = { nav.navigateTo(def.id) },
                icon = def.icon,
                group = def.group,
                id = def.id,
            )
        }

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val isCompact = maxWidth < 600.dp
            var columnVisibility by remember {
                mutableStateOf(if (isCompact) ColumnVisibility.DoubleColumn else ColumnVisibility.All)
            }

            LaunchedEffect(isCompact) {
                if (isCompact) {
                    snapshotFlow { nav.currentKey }
                        .drop(1)
                        .collect { columnVisibility = ColumnVisibility.DoubleColumn }
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    columnVisibility = columnVisibility,
                    onColumnVisibilityChange = { columnVisibility = it },
                    sidebarWidth = ColumnWidth.Fixed(240.dp),
                    pushContent = isCompact,
                    inspector = {
                        InspectorSizePreview(pageId = nav.currentPageId)
                    },
                    inspectorVisible = inspectorVisible,
                    onInspectorVisibleChange = { inspectorVisible = it },
                    inspectorWidth = ColumnWidth.Fixed(300.dp),
                    sidebar = {
                        ControlSize(sidebarControlSize) {
                            Sidebar(
                                items = sidebarItems,
                                activeItem = nav.currentPageId,
                                showBorder = false,
                            )
                        }
                    },
                    titleBar = {
                        TitleBar(
                            glass = true,
                            showsTitle = !isCompact,
                            pushActionsOnExpand = isCompact,
                            navigationActionsMinWidth = if (isCompact) 0.dp else 80.dp,
                            navigationActions = if (!isCompact) {
                                {
                                    NavigationButtons(
                                        onBack = { nav.goBack() },
                                        onForward = { nav.goForward() },
                                        backEnabled = nav.canGoBack,
                                        forwardEnabled = nav.canGoForward,
                                    )
                                }
                            } else ({}),
                            title = { Text(currentPageLabel) },
                            titleAlignment = Alignment.CenterStart,
                            actions = {
                                val uriHandler = LocalUriHandler.current
                                TitleBarButtonGroup {
                                    TitleBarGroupButton(onClick = {
                                        uriHandler.openUri("https://github.com/kdroidFilter/compose-macos-26-ui")
                                    }) {
                                        Icon(Lucide.Github, modifier = Modifier.size(14.dp))
                                    }
                                }
                                TitleBarButtonGroup {
                                    TitleBarGroupButton(onClick = {
                                        themeMode = when (themeMode) {
                                            ThemeMode.System -> ThemeMode.Light
                                            ThemeMode.Light -> ThemeMode.Dark
                                            ThemeMode.Dark -> ThemeMode.System
                                        }
                                    }) {
                                        Icon(
                                            when (themeMode) {
                                                ThemeMode.System -> Lucide.Monitor
                                                ThemeMode.Light -> LucideSun
                                                ThemeMode.Dark -> LucideMoon
                                            },
                                            modifier = Modifier.size(14.dp),
                                        )
                                    }
                                    Popover(
                                        expanded = settingsExpanded,
                                        onDismissRequest = { settingsExpanded = false },
                                        placement = PopoverPlacement.Bottom,
                                        trigger = {
                                            TitleBarGroupButton(onClick = { settingsExpanded = !settingsExpanded }) {
                                                Icon(LucideSettings, modifier = Modifier.size(14.dp))
                                            }
                                        },
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(16.dp).width(220.dp),
                                            verticalArrangement = Arrangement.spacedBy(12.dp),
                                        ) {
                                            Text(
                                                text = "Accent Color",
                                                style = MacosTheme.typography.caption1,
                                                fontWeight = FontWeight.SemiBold,
                                                color = MacosTheme.colorScheme.textSecondary,
                                            )
                                            AccentColorPicker(
                                                selected = accentColor,
                                                onSelect = { overriddenAccent = it },
                                            )
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Text(
                                                    text = "Vibrant",
                                                    style = MacosTheme.typography.caption1,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = MacosTheme.colorScheme.textSecondary,
                                                )
                                                ControlSize(ControlSize.Mini) {
                                                    Switch(
                                                        checked = isVibrant,
                                                        onCheckedChange = { isVibrant = it },
                                                    )
                                                }
                                            }
                                            Text(
                                                text = "Glass Type",
                                                style = MacosTheme.typography.caption1,
                                                fontWeight = FontWeight.SemiBold,
                                                color = MacosTheme.colorScheme.textSecondary,
                                            )
                                            ControlSize(ControlSize.Small) {
                                                SegmentedControl(
                                                    options = listOf("Regular", "Tinted"),
                                                    selectedIndex = GlassType.entries.indexOf(glassType),
                                                    onSelectedIndexChange = { glassType = GlassType.entries[it] },
                                                )
                                            }
                                            Text(
                                                text = "Sidebar Icon Size",
                                                style = MacosTheme.typography.caption1,
                                                fontWeight = FontWeight.SemiBold,
                                                color = MacosTheme.colorScheme.textSecondary,
                                            )
                                            val sizeOptions = listOf(ControlSize.Small, ControlSize.Regular, ControlSize.Large)
                                            ControlSize(ControlSize.Small) {
                                                SegmentedControl(
                                                    options = listOf("S", "M", "L"),
                                                    selectedIndex = sizeOptions.indexOf(sidebarControlSize),
                                                    onSelectedIndexChange = { sidebarControlSize = sizeOptions[it] },
                                                )
                                            }
                                        }
                                    }
                                }
                                ToolbarSearchField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    expanded = searchExpanded,
                                    onExpandedChange = { searchExpanded = it },
                                    expandedWidth = 240.dp,
                                    placeholder = "Search components...",
                                    onSearch = { query ->
                                        val match = sidebarEntryDefs.firstOrNull {
                                            it.label.lowercase().contains(query.lowercase().trim())
                                        }
                                        if (match != null) {
                                            nav.navigateTo(match.id)
                                            searchQuery = ""
                                            searchExpanded = false
                                        }
                                    },
                                    suggestions = {
                                        val query = searchQuery.lowercase().trim()
                                        val matches = sidebarEntryDefs.filter {
                                            it.label.lowercase().contains(query)
                                        }
                                        matches.groupBy { it.group }.entries.forEachIndexed { index, (group, items) ->
                                            if (index > 0) SearchSuggestionSeparator()
                                            SearchSuggestionHeader(group)
                                            items.forEach { def ->
                                                SearchSuggestionItem(onClick = {
                                                    nav.navigateTo(def.id)
                                                    searchQuery = ""
                                                    searchExpanded = false
                                                }) {
                                                    Text(def.label)
                                                }
                                            }
                                        }
                                    },
                                )
                                TitleBarButtonGroup {
                                    TitleBarGroupButton(onClick = { inspectorVisible = !inspectorVisible }) {
                                        Icon(Lucide.Ruler, modifier = Modifier.size(14.dp))
                                    }
                                }
                            },
                        )
                    },
                ) { contentPadding ->
                    when (val currentKey = nav.currentKey) {
                        is HomeScreen -> {
                            ScrollablePageContent(contentPadding) {
                                HomePage(onNavigate = { nav.navigateTo(it) })
                            }
                        }
                        is PageScreen -> {
                            ScrollablePageContent(contentPadding) {
                                PageContent(currentKey.id, toastState)
                            }
                        }
                    }
                }

                if (isCompact) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        shape = MacosTheme.shapes.extraLarge,
                    ) {
                        NavigationButtons(
                            onBack = { nav.goBack() },
                            onForward = { nav.goForward() },
                            backEnabled = nav.canGoBack,
                            forwardEnabled = nav.canGoForward,
                        )
                    }
                }

                ToastHost(state = toastState)
            }
        }
    }
}

@Composable
private fun ScrollablePageContent(
    contentPadding: androidx.compose.foundation.layout.PaddingValues,
    content: @Composable () -> Unit,
) {
    val contentScrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(contentScrollState)
                .padding(contentPadding)
                .padding(horizontal = 40.dp, vertical = 40.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            content()
            Spacer(modifier = Modifier.height(48.dp))
        }
        // topInset keeps the scrollbar thumb below the title bar glass overlay
        VerticalScrollbar(
            state = rememberScrollbarState(contentScrollState),
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            showOnEdgeHover = true,
            trackClickBehavior = TrackClickBehavior.Jump,
            topInset = contentPadding.calculateTopPadding(),
        )
    }
}

@Composable
private fun PageContent(
    pageId: String,
    toastState: io.github.kdroidfilter.nucleus.ui.apple.macos.components.ToastState,
) {
    when (pageId) {
        "getting-started" -> GettingStartedPage()
        "license" -> LicensePage()
        "button" -> ButtonPage()
        "iconbutton" -> IconButtonPage()
        "input" -> InputPage()
        "searchinput" -> SearchInputPage()
        "textarea" -> TextAreaPage()
        "checkbox" -> CheckboxPage()
        "radiobutton" -> RadioButtonPage()
        "switch" -> SwitchPage()
        "combobox" -> ComboBoxPage()
        "multiselect" -> MultiSelectPage()
        "slider" -> SliderPage()
        "circularslider" -> CircularSliderPage()
        "stepper" -> StepperPage()
        "scrollbar" -> ScrollbarPage()
        "groupbox" -> GroupBoxPage()
        "groupedlist" -> GroupedListPage()
        "form" -> FormPage()
        "badge" -> BadgePage()
        "avatar" -> AvatarPage()
        "surface" -> SurfacePage()
        "card" -> CardPage()
        "table" -> TablePage()
        "pagecontrol" -> PageControlPage()
        "progress" -> ProgressPage()
        "skeleton" -> SkeletonPage()
        "alert" -> AlertPage()
        "toast" -> ToastPage(toastState)
        "dialog" -> DialogPage()
        "tooltip" -> TooltipPage()
        "popover" -> PopoverPage()
        "dropdown" -> DropdownMenuPage()
        "contextmenu" -> ContextMenuPage()
        "tabs" -> TabsPage()
        "accordion" -> AccordionPage()
        "sidebar" -> SidebarPage()
        "segmentedcontrol" -> SegmentedControlPage()
        "titlebar" -> TitleBarPage()
        "addressbar" -> AddressBarPage()
        "scaffold" -> ScaffoldPage()
        "popupbutton" -> PopupButtonPage()
        "datepicker" -> DatePickerPage()
        "colorwell" -> ColorWellPage()
        "icons" -> IconsPage()
        "material" -> MaterialPage()
        "controlsize" -> ControlSizePage()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AccentColorPicker(
    selected: AccentColor,
    onSelect: (AccentColor) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        AccentColor.entries.filter { it != AccentColor.Teal }.forEach { color ->
            val isSelected = color == selected
            val displayColor = if (MacosTheme.colorScheme.isDark) color.dark else color.light
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(displayColor, CircleShape)
                    .then(
                        if (isSelected) {
                            Modifier.border(2.dp, MacosTheme.colorScheme.textPrimary, CircleShape)
                        } else {
                            Modifier
                        }
                    )
                    .clickable { onSelect(color) },
            )
        }
    }
}

@Composable
internal expect fun BrowserNavigation(backStack: androidx.compose.runtime.snapshots.SnapshotStateList<AppNavKey>)

@Composable
internal expect fun isSystemDarkMode(): Boolean

@Composable
internal expect fun systemAccentRawColor(): androidx.compose.ui.graphics.Color?
