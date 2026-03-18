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
import com.composables.icons.lucide.ArrowUpDown
import com.composables.icons.lucide.Box
import com.composables.icons.lucide.ChevronDown
import com.composables.icons.lucide.Component
import com.composables.icons.lucide.Navigation
import com.composables.icons.lucide.Paintbrush
import com.composables.icons.lucide.Palette
import com.composables.icons.lucide.RectangleHorizontal
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.ShieldAlert
import com.composables.icons.lucide.SquareMousePointer
import com.composables.icons.lucide.Star
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColumnVisibility
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.LocalExampleCardBackgroundColor
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
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SidebarDefaults
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
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideMoon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSettings
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSun
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.SystemIcon
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
internal data class SidebarEntryDef(val id: String, val label: String, val group: String, val icon: SystemIcon)

internal val sidebarEntryDefs = listOf(
    // General
    SidebarEntryDef("home", "Home", "GENERAL", Icons.Home),
    SidebarEntryDef("getting-started", "Getting Started", "GENERAL", SystemIcon("list.bullet.rectangle", Lucide.LayoutList)),
    SidebarEntryDef("license", "License", "GENERAL", Icons.Tag),

    // Controls — Buttons
    SidebarEntryDef("button", "Button", "CONTROLS", SystemIcon("cursorarrow.click", Lucide.MousePointerClick)),
    SidebarEntryDef("iconbutton", "Icon Button", "CONTROLS", SystemIcon("square.and.arrow.up.on.square", Lucide.SquareMousePointer)),

    // Controls — Text input
    SidebarEntryDef("input", "Input", "CONTROLS", SystemIcon("character.cursor.ibeam", Lucide.TextCursorInput)),
    SidebarEntryDef("textarea", "Textarea", "CONTROLS", SystemIcon("text.alignleft", Lucide.TextAlignStart)),
    SidebarEntryDef("searchinput", "Search Input", "CONTROLS", Icons.Search),

    // Controls — Selection
    SidebarEntryDef("checkbox", "Checkbox", "CONTROLS", SystemIcon("checkmark.square", Lucide.SquareCheck)),
    SidebarEntryDef("radiobutton", "Radio Button", "CONTROLS", SystemIcon("circle.inset.filled", Lucide.CircleDot)),
    SidebarEntryDef("switch", "Switch", "CONTROLS", SystemIcon("switch.2", Lucide.ToggleLeft)),
    SidebarEntryDef("segmentedcontrol", "Segmented Control", "CONTROLS", SystemIcon("rectangle.split.3x1", Lucide.Columns3)),
    SidebarEntryDef("combobox", "Combo Box", "CONTROLS", Icons.ChevronsUpDown),
    SidebarEntryDef("popupbutton", "Pop-up Button", "CONTROLS", Icons.ChevronDown),
    SidebarEntryDef("multiselect", "Multi Select", "CONTROLS", SystemIcon("checklist", Lucide.ListChecks)),

    // Controls — Value input
    SidebarEntryDef("slider", "Slider", "CONTROLS", SystemIcon("slider.horizontal.3", Lucide.SlidersHorizontal)),
    SidebarEntryDef("circularslider", "Circular Slider", "CONTROLS", SystemIcon("dial.min", Lucide.Loader)),
    SidebarEntryDef("stepper", "Stepper", "CONTROLS", SystemIcon("plusminus", Lucide.ArrowUpDown)),
    SidebarEntryDef("datepicker", "Date Picker", "CONTROLS", Icons.Calendar),
    SidebarEntryDef("colorwell", "Color Wells & Pickers", "CONTROLS", SystemIcon("paintpalette", Lucide.Palette)),

    // Layout & Containers
    SidebarEntryDef("form", "Form", "LAYOUT", SystemIcon("list.bullet.rectangle", Lucide.LayoutList)),
    SidebarEntryDef("groupbox", "Group Box", "LAYOUT", SystemIcon("rectangle.dashed", Lucide.SquareDashed)),
    SidebarEntryDef("card", "Card", "LAYOUT", SystemIcon("rectangle.on.rectangle", Lucide.CreditCard)),
    SidebarEntryDef("surface", "Surface", "LAYOUT", SystemIcon("square.stack", Lucide.Layers)),
    SidebarEntryDef("accordion", "Accordion", "LAYOUT", Icons.ChevronsUpDown),
    SidebarEntryDef("scaffold", "Scaffold", "LAYOUT", Icons.PanelLeft),

    // Collections
    SidebarEntryDef("groupedlist", "Grouped List", "COLLECTIONS", SystemIcon("checklist", Lucide.ListChecks)),
    SidebarEntryDef("table", "Table", "COLLECTIONS", SystemIcon("tablecells", Lucide.Table)),

    // Navigation
    SidebarEntryDef("tabs", "Tabs", "NAVIGATION", SystemIcon("rectangle.split.3x1", Lucide.Columns3)),
    SidebarEntryDef("sidebar", "Sidebar", "NAVIGATION", Icons.PanelLeft),
    SidebarEntryDef("titlebar", "Title Bar", "NAVIGATION", SystemIcon("macwindow", Lucide.PanelTopOpen)),
    SidebarEntryDef("addressbar", "Address Bar", "NAVIGATION", Icons.Search),
    SidebarEntryDef("pagecontrol", "Page Control", "NAVIGATION", SystemIcon("circle.inset.filled", Lucide.CircleDot)),
    SidebarEntryDef("scrollbar", "Scrollbar", "NAVIGATION", SystemIcon("arrow.up.and.down", Lucide.GripVertical)),

    // Status & Feedback
    SidebarEntryDef("badge", "Badge", "STATUS & FEEDBACK", Icons.Tag),
    SidebarEntryDef("avatar", "Avatar", "STATUS & FEEDBACK", SystemIcon("person.circle", Lucide.CircleUser)),
    SidebarEntryDef("progress", "Progress", "STATUS & FEEDBACK", SystemIcon("progress.indicator", Lucide.Loader)),
    SidebarEntryDef("skeleton", "Skeleton", "STATUS & FEEDBACK", SystemIcon("rectangle.fill", Lucide.RectangleHorizontal)),
    SidebarEntryDef("alert", "Alert", "STATUS & FEEDBACK", Icons.TriangleAlert),
    SidebarEntryDef("toast", "Toast", "STATUS & FEEDBACK", SystemIcon("bell", Lucide.Bell)),

    // Overlays
    SidebarEntryDef("dialog", "Dialog", "OVERLAYS", SystemIcon("bubble.left.and.bubble.right", Lucide.MessageSquare)),
    SidebarEntryDef("popover", "Popover", "OVERLAYS", SystemIcon("macwindow", Lucide.PanelTopOpen)),
    SidebarEntryDef("tooltip", "Tooltip", "OVERLAYS", SystemIcon("text.bubble", Lucide.MessageCircle)),
    SidebarEntryDef("dropdown", "Dropdown Menu", "OVERLAYS", SystemIcon("line.3.horizontal", Lucide.Menu)),
    SidebarEntryDef("contextmenu", "Context Menu", "OVERLAYS", Icons.Ellipsis),

    // Theme & Design
    SidebarEntryDef("icons", "Icons", "THEME", Icons.Star),
    SidebarEntryDef("material", "Material", "THEME", SystemIcon("paintpalette", Lucide.Palette)),
    SidebarEntryDef("controlsize", "Control Size", "THEME", SystemIcon("slider.horizontal.3", Lucide.SlidersHorizontal)),
)

private fun buildSidebarItems(onNavigate: (String) -> Unit): List<SidebarItem> {
    fun entry(id: String) = sidebarEntryDefs.first { it.id == id }
    fun leaf(id: String) = entry(id).let { SidebarItem(it.label, onClick = { onNavigate(it.id) }, icon = it.icon, id = it.id) }

    fun group(label: String, icon: SystemIcon, ids: List<String>) = SidebarItem(
        label = label,
        onClick = {},
        icon = icon,
        children = ids.map { leaf(it) },
        initiallyExpanded = false,
    )

    return listOf(
        leaf("home"),
        leaf("getting-started"),
        leaf("license"),

        group("Controls", SystemIcon("slider.horizontal.below.rectangle", Lucide.Component), listOf(
            "button", "iconbutton",
            "input", "textarea", "searchinput",
            "checkbox", "radiobutton", "switch", "segmentedcontrol",
            "combobox", "popupbutton", "multiselect",
            "slider", "circularslider", "stepper",
            "datepicker", "colorwell",
        )),
        group("Layout", SystemIcon("rectangle.3.group", Lucide.Box), listOf(
            "form", "groupbox", "card", "surface", "accordion", "scaffold",
        )),
        group("Collections", Icons.Folder, listOf(
            "groupedlist", "table",
        )),
        group("Navigation", SystemIcon("arrow.triangle.turn.up.right.diamond", Lucide.Navigation), listOf(
            "tabs", "sidebar", "titlebar", "addressbar", "pagecontrol", "scrollbar",
        )),
        group("Status & Feedback", SystemIcon("bell.badge", Lucide.ShieldAlert), listOf(
            "badge", "avatar", "progress", "skeleton", "alert", "toast",
        )),
        group("Overlays", SystemIcon("bubble.left.and.bubble.right", Lucide.MessageSquare), listOf(
            "dialog", "popover", "tooltip", "dropdown", "contextmenu",
        )),
        group("Theme", SystemIcon("paintbrush", Lucide.Paintbrush), listOf(
            "icons", "material", "controlsize",
        )),
    )
}

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
        CompositionLocalProvider(LocalExampleCardBackgroundColor provides Color.Transparent) {
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

        val sidebarItems = remember {
            buildSidebarItems { id -> nav.navigateTo(id) }
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
                    sidebarWidth = ColumnWidth.Flexible(min = 180.dp, ideal = 240.dp, max = 350.dp),
                    pushContent = isCompact,
                    dismissPanelsOnContentTap = isCompact,
                    inspector = {
                        InspectorSizePreview(pageId = nav.currentPageId)
                    },
                    inspectorVisible = inspectorVisible,
                    onInspectorVisibleChange = { inspectorVisible = it },
                    inspectorWidth = ColumnWidth.Flexible(min = 200.dp, ideal = 300.dp, max = 450.dp),
                    sidebar = {
                        val accentColor = MacosTheme.colorScheme.accent
                        val customItemColors = SidebarDefaults.itemColors(
                            activeBackgroundColor = accentColor,
                            activeContentColor = Color.White,
                        )
                        ControlSize(sidebarControlSize) {
                            Sidebar(
                                items = sidebarItems,
                                activeItem = nav.currentPageId,
                                showBorder = false,
                                itemColors = customItemColors,
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
    }}
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
