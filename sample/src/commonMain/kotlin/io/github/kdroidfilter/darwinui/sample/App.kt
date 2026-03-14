package io.github.kdroidfilter.darwinui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.ChevronsUpDown
import com.composables.icons.lucide.CircleDot
import com.composables.icons.lucide.CircleUser
import com.composables.icons.lucide.Columns3
import com.composables.icons.lucide.CreditCard
import com.composables.icons.lucide.Ellipsis
import com.composables.icons.lucide.ListChecks
import com.composables.icons.lucide.LayoutList
import com.composables.icons.lucide.Loader
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.MessageCircle
import com.composables.icons.lucide.MessageSquare
import com.composables.icons.lucide.MousePointerClick
import io.github.kdroidfilter.darwinui.icons.LucidePanelLeft
import com.composables.icons.lucide.PanelTopOpen
import com.composables.icons.lucide.Scan
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.GripVertical
import com.composables.icons.lucide.SlidersHorizontal
import com.composables.icons.lucide.SquareCheck
import com.composables.icons.lucide.SquareDashed
import com.composables.icons.lucide.Table
import com.composables.icons.lucide.Tag
import com.composables.icons.lucide.TextAlignStart
import com.composables.icons.lucide.TextCursorInput
import com.composables.icons.lucide.ToggleLeft
import com.composables.icons.lucide.TriangleAlert
import io.github.kdroidfilter.darwinui.components.ColumnVisibility
import io.github.kdroidfilter.darwinui.components.DarwinScaffold
import io.github.kdroidfilter.darwinui.components.TrackClickBehavior
import io.github.kdroidfilter.darwinui.components.VerticalScrollbar
import io.github.kdroidfilter.darwinui.components.rememberScrollbarState
import io.github.kdroidfilter.darwinui.components.IconButton
import io.github.kdroidfilter.darwinui.components.NavigationButtons
import io.github.kdroidfilter.darwinui.components.Popover
import io.github.kdroidfilter.darwinui.components.PopoverPlacement
import io.github.kdroidfilter.darwinui.components.SearchSuggestionHeader
import io.github.kdroidfilter.darwinui.components.SearchSuggestionItem
import io.github.kdroidfilter.darwinui.components.SearchSuggestionSeparator
import io.github.kdroidfilter.darwinui.components.SegmentedControl
import io.github.kdroidfilter.darwinui.components.Sidebar
import io.github.kdroidfilter.darwinui.components.SidebarItem
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TitleBar
import io.github.kdroidfilter.darwinui.components.ToastHost
import io.github.kdroidfilter.darwinui.components.ToolbarSearchField
import io.github.kdroidfilter.darwinui.components.rememberToastState
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideMoon
import io.github.kdroidfilter.darwinui.icons.LucideSettings
import io.github.kdroidfilter.darwinui.icons.LucideSun
import io.github.kdroidfilter.darwinui.sample.pages.AddressBarPage
import io.github.kdroidfilter.darwinui.sample.pages.AccordionPage
import io.github.kdroidfilter.darwinui.sample.pages.AlertPage
import io.github.kdroidfilter.darwinui.sample.pages.AvatarPage
import io.github.kdroidfilter.darwinui.sample.pages.BadgePage
import io.github.kdroidfilter.darwinui.sample.pages.ButtonPage
import io.github.kdroidfilter.darwinui.sample.pages.CardPage
import io.github.kdroidfilter.darwinui.sample.pages.CheckboxPage
import io.github.kdroidfilter.darwinui.sample.pages.CircularSliderPage
import io.github.kdroidfilter.darwinui.sample.pages.ComboBoxPage
import io.github.kdroidfilter.darwinui.sample.pages.ColorWellPage
import io.github.kdroidfilter.darwinui.sample.pages.ControlSizePage
import io.github.kdroidfilter.darwinui.sample.pages.ContextMenuPage
import io.github.kdroidfilter.darwinui.sample.pages.DialogPage
import io.github.kdroidfilter.darwinui.sample.pages.DropdownMenuPage
import io.github.kdroidfilter.darwinui.sample.pages.MaterialPage
import io.github.kdroidfilter.darwinui.sample.pages.GroupBoxPage
import io.github.kdroidfilter.darwinui.sample.pages.FormPage
import io.github.kdroidfilter.darwinui.sample.pages.GroupedListPage
import io.github.kdroidfilter.darwinui.sample.pages.IconButtonPage
import io.github.kdroidfilter.darwinui.sample.pages.InputPage
import io.github.kdroidfilter.darwinui.sample.pages.MultiSelectPage
import io.github.kdroidfilter.darwinui.sample.pages.PopoverPage
import io.github.kdroidfilter.darwinui.sample.pages.ProgressPage
import io.github.kdroidfilter.darwinui.sample.pages.RadioButtonPage
import io.github.kdroidfilter.darwinui.sample.pages.ScaffoldPage
import io.github.kdroidfilter.darwinui.sample.pages.ScrollbarPage
import io.github.kdroidfilter.darwinui.sample.pages.SearchInputPage
import io.github.kdroidfilter.darwinui.sample.pages.SegmentedControlPage
import io.github.kdroidfilter.darwinui.sample.pages.SidebarPage
import io.github.kdroidfilter.darwinui.sample.pages.SkeletonPage
import io.github.kdroidfilter.darwinui.sample.pages.SliderPage
import io.github.kdroidfilter.darwinui.sample.pages.SwitchPage
import io.github.kdroidfilter.darwinui.sample.pages.TablePage
import io.github.kdroidfilter.darwinui.sample.pages.TabsPage
import io.github.kdroidfilter.darwinui.sample.pages.TextAreaPage
import io.github.kdroidfilter.darwinui.sample.pages.TitleBarPage
import io.github.kdroidfilter.darwinui.sample.pages.ToastPage
import io.github.kdroidfilter.darwinui.sample.pages.TooltipPage
import io.github.kdroidfilter.darwinui.components.Switch
import io.github.kdroidfilter.darwinui.theme.AccentColor
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.VibrantColors
import io.github.kdroidfilter.darwinui.theme.vibrant

// Navigation data
private data class SidebarEntryDef(val id: String, val label: String, val group: String, val icon: ImageVector)

private val sidebarEntryDefs = listOf(
    SidebarEntryDef("button", "Button", "FORM CONTROLS", Lucide.MousePointerClick),
    SidebarEntryDef("iconbutton", "Icon Button", "FORM CONTROLS", Lucide.CircleUser),
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
    SidebarEntryDef("colorwell", "Color Well", "FORM CONTROLS", Lucide.Scan),
    SidebarEntryDef("scrollbar", "Scrollbar", "DATA DISPLAY", Lucide.GripVertical),
    SidebarEntryDef("groupbox", "Group Box", "DATA DISPLAY", Lucide.SquareDashed),
    SidebarEntryDef("groupedlist", "Grouped List", "DATA DISPLAY", Lucide.ListChecks),
    SidebarEntryDef("form", "Form", "DATA DISPLAY", Lucide.LayoutList),
    SidebarEntryDef("badge", "Badge", "DATA DISPLAY", Lucide.Tag),
    SidebarEntryDef("avatar", "Avatar", "DATA DISPLAY", Lucide.CircleUser),
    SidebarEntryDef("card", "Card", "DATA DISPLAY", Lucide.CreditCard),
    SidebarEntryDef("table", "Table", "DATA DISPLAY", Lucide.Table),
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
    SidebarEntryDef("sidebar", "Sidebar", "NAVIGATION", LucidePanelLeft),
    SidebarEntryDef("segmentedcontrol", "Segmented Control", "NAVIGATION", Lucide.Columns3),
    SidebarEntryDef("titlebar", "Title Bar", "NAVIGATION", Lucide.PanelTopOpen),
    SidebarEntryDef("addressbar", "Address Bar", "NAVIGATION", Lucide.Search),
    SidebarEntryDef("scaffold", "Scaffold", "NAVIGATION", LucidePanelLeft),
    SidebarEntryDef("material", "Material", "THEME", Lucide.Scan),
    SidebarEntryDef("controlsize", "Control Size", "THEME", Lucide.SlidersHorizontal),
)

@Composable
fun App() {
    val systemTheme = isSystemInDarkTheme()
    var isDark by remember { mutableStateOf(systemTheme) }
    var accentColor by remember { mutableStateOf(AccentColor.Blue) }
    var sidebarControlSize by remember { mutableStateOf(ControlSize.Regular) }
    var isVibrant by remember { mutableStateOf(false) }

    val baseColorScheme = if (isDark) {
        io.github.kdroidfilter.darwinui.theme.darkColorScheme(accentColor)
    } else {
        io.github.kdroidfilter.darwinui.theme.lightColorScheme(accentColor)
    }
    val colorScheme = if (isVibrant) {
        val vibrant = if (isDark) VibrantColors.dark() else VibrantColors.light()
        baseColorScheme.vibrant(vibrant)
    } else {
        baseColorScheme
    }

    DarwinTheme(darkTheme = isDark, accentColor = accentColor, colorScheme = colorScheme) {
        val toastState = rememberToastState()
        var selectedPage by remember { mutableStateOf("button") }
        var searchQuery by remember { mutableStateOf("") }
        var searchExpanded by remember { mutableStateOf(false) }
        var columnVisibility by remember { mutableStateOf(ColumnVisibility.All) }
        var sidebarCollapsed by remember { mutableStateOf(false) }
        var settingsExpanded by remember { mutableStateOf(false) }

        // Navigation helpers
        val currentIndex = sidebarEntryDefs.indexOfFirst { it.id == selectedPage }
        val canGoBack = currentIndex > 0
        val canGoForward = currentIndex < sidebarEntryDefs.lastIndex
        val currentPageLabel = sidebarEntryDefs.getOrNull(currentIndex)?.label ?: ""

        val sidebarItems = sidebarEntryDefs.map { def ->
            SidebarItem(
                label = def.label,
                onClick = { selectedPage = def.id },
                icon = def.icon,
                group = def.group,
                id = def.id,
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            DarwinScaffold(
                columnVisibility = columnVisibility,
                onColumnVisibilityChange = { columnVisibility = it },
                sidebar = {
                    ControlSize(sidebarControlSize) {
                        Sidebar(
                            items = sidebarItems,
                            activeItem = selectedPage,
                            showBorder = false,
                            collapsed = sidebarCollapsed,
                            onCollapsedChange = { sidebarCollapsed = it },
                            collapsible = true,
                            header = {
                                // Title + toggle — aligns vertically with 52dp title bar
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "Darwin UI",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = DarwinTheme.colorScheme.textPrimary,
                                        )
                                        Text(
                                            text = "Component Docs",
                                            style = DarwinTheme.typography.caption1,
                                            color = DarwinTheme.colorScheme.textTertiary,
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
                    }
                },
                titleBar = {
                    TitleBar(
                        glass = true,
                        navigationActions = {
                            NavigationButtons(
                                onBack = {
                                    if (canGoBack) selectedPage = sidebarEntryDefs[currentIndex - 1].id
                                },
                                onForward = {
                                    if (canGoForward) selectedPage = sidebarEntryDefs[currentIndex + 1].id
                                },
                                backEnabled = canGoBack,
                                forwardEnabled = canGoForward,
                            )
                        },
                        title = { Text(currentPageLabel) },
                        actions = {
                            Popover(
                                expanded = settingsExpanded,
                                onDismissRequest = { settingsExpanded = false },
                                placement = PopoverPlacement.Bottom,
                                trigger = {
                                    IconButton(onClick = { settingsExpanded = !settingsExpanded }) {
                                        Icon(LucideSettings, modifier = Modifier.size(18.dp))
                                    }
                                },
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp).width(220.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    Text(
                                        text = "Accent Color",
                                        style = DarwinTheme.typography.caption1,
                                        fontWeight = FontWeight.SemiBold,
                                        color = DarwinTheme.colorScheme.textSecondary,
                                    )
                                    AccentColorPicker(
                                        selected = accentColor,
                                        onSelect = { accentColor = it },
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = "Vibrant",
                                            style = DarwinTheme.typography.caption1,
                                            fontWeight = FontWeight.SemiBold,
                                            color = DarwinTheme.colorScheme.textSecondary,
                                        )
                                        ControlSize(ControlSize.Mini) {
                                            Switch(
                                                checked = isVibrant,
                                                onCheckedChange = { isVibrant = it },
                                            )
                                        }
                                    }
                                    Text(
                                        text = "Sidebar Icon Size",
                                        style = DarwinTheme.typography.caption1,
                                        fontWeight = FontWeight.SemiBold,
                                        color = DarwinTheme.colorScheme.textSecondary,
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
                            IconButton(onClick = { isDark = !isDark }) {
                                Icon(if (isDark) LucideSun else LucideMoon, modifier = Modifier.size(18.dp))
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
                                        selectedPage = match.id
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
                                                selectedPage = def.id
                                                searchQuery = ""
                                                searchExpanded = false
                                            }) {
                                                Text(def.label)
                                            }
                                        }
                                    }
                                },
                            )
                        },
                    )
                },
            ) { contentPadding ->
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
                    when (selectedPage) {
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
                        "scrollbar" -> ScrollbarPage()
                        "groupbox" -> GroupBoxPage()
                        "groupedlist" -> GroupedListPage()
                        "form" -> FormPage()
                        "badge" -> BadgePage()
                        "avatar" -> AvatarPage()
                        "card" -> CardPage()
                        "table" -> TablePage()
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
                        "colorwell" -> ColorWellPage()
                        "material" -> MaterialPage()
                        "controlsize" -> ControlSizePage()
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                }
                VerticalScrollbar(
                    state = rememberScrollbarState(contentScrollState),
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                    showOnEdgeHover = true,
                    trackClickBehavior = TrackClickBehavior.Jump,
                )
                } // Box
            }

            ToastHost(state = toastState)
        }
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
            val displayColor = if (DarwinTheme.colorScheme.isDark) color.dark else color.light
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(displayColor, CircleShape)
                    .then(
                        if (isSelected) {
                            Modifier.border(2.dp, DarwinTheme.colorScheme.textPrimary, CircleShape)
                        } else {
                            Modifier
                        }
                    )
                    .clickable { onSelect(color) },
            )
        }
    }
}

