package io.github.kdroidfilter.darwinui.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Bell
import com.composables.icons.lucide.Calendar
import com.composables.icons.lucide.ChevronsUpDown
import com.composables.icons.lucide.CircleUser
import com.composables.icons.lucide.Columns3
import com.composables.icons.lucide.CreditCard
import com.composables.icons.lucide.Ellipsis
import com.composables.icons.lucide.Layers
import com.composables.icons.lucide.ListChecks
import com.composables.icons.lucide.Loader
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Menu
import com.composables.icons.lucide.MessageCircle
import com.composables.icons.lucide.MessageSquare
import com.composables.icons.lucide.MousePointerClick
import com.composables.icons.lucide.PanelLeft
import com.composables.icons.lucide.PanelTopOpen
import com.composables.icons.lucide.Scan
import com.composables.icons.lucide.Search
import com.composables.icons.lucide.SlidersHorizontal
import com.composables.icons.lucide.SquareCheck
import com.composables.icons.lucide.Table
import com.composables.icons.lucide.Tag
import com.composables.icons.lucide.TextAlignStart
import com.composables.icons.lucide.TextCursorInput
import com.composables.icons.lucide.ToggleLeft
import com.composables.icons.lucide.TriangleAlert
import com.composables.icons.lucide.Upload
import io.github.kdroidfilter.darwinui.components.SubtleButton
import io.github.kdroidfilter.darwinui.components.SearchField
import io.github.kdroidfilter.darwinui.components.Sidebar
import io.github.kdroidfilter.darwinui.components.SidebarItem
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.ToastHost
import io.github.kdroidfilter.darwinui.components.rememberToastState
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideMoon
import io.github.kdroidfilter.darwinui.icons.LucideSun
import io.github.kdroidfilter.darwinui.sample.pages.AccordionPage
import io.github.kdroidfilter.darwinui.sample.pages.BottomAppBarPage
import io.github.kdroidfilter.darwinui.sample.pages.NavigationRailPage
import io.github.kdroidfilter.darwinui.sample.pages.SegmentedButtonPage
import io.github.kdroidfilter.darwinui.sample.pages.TimePickerPage
import io.github.kdroidfilter.darwinui.sample.pages.AlertPage
import io.github.kdroidfilter.darwinui.sample.pages.AvatarPage
import io.github.kdroidfilter.darwinui.sample.pages.BadgePage
import io.github.kdroidfilter.darwinui.sample.pages.BottomSheetPage
import io.github.kdroidfilter.darwinui.sample.pages.ButtonPage
import io.github.kdroidfilter.darwinui.sample.pages.CardPage
import io.github.kdroidfilter.darwinui.sample.pages.CheckboxPage
import io.github.kdroidfilter.darwinui.sample.pages.ChipsPage
import io.github.kdroidfilter.darwinui.sample.pages.ContextMenuPage
import io.github.kdroidfilter.darwinui.sample.pages.DateSelectPage
import io.github.kdroidfilter.darwinui.sample.pages.DialogPage
import io.github.kdroidfilter.darwinui.sample.pages.DividerPage
import io.github.kdroidfilter.darwinui.sample.pages.DropdownMenuPage
import io.github.kdroidfilter.darwinui.sample.pages.FabPage
import io.github.kdroidfilter.darwinui.sample.pages.IconButtonPage
import io.github.kdroidfilter.darwinui.sample.pages.InputPage
import io.github.kdroidfilter.darwinui.sample.pages.ListItemPage
import io.github.kdroidfilter.darwinui.sample.pages.M3ButtonsPage
import io.github.kdroidfilter.darwinui.sample.pages.MultiSelectPage
import io.github.kdroidfilter.darwinui.sample.pages.NavigationBarPage
import io.github.kdroidfilter.darwinui.sample.pages.PopoverPage
import io.github.kdroidfilter.darwinui.sample.pages.ProgressPage
import io.github.kdroidfilter.darwinui.sample.pages.RadioButtonPage
import io.github.kdroidfilter.darwinui.sample.pages.SearchInputPage
import io.github.kdroidfilter.darwinui.sample.pages.SelectPage
import io.github.kdroidfilter.darwinui.sample.pages.SidebarPage
import io.github.kdroidfilter.darwinui.sample.pages.SkeletonPage
import io.github.kdroidfilter.darwinui.sample.pages.SliderPage
import io.github.kdroidfilter.darwinui.sample.pages.SnackbarPage
import io.github.kdroidfilter.darwinui.sample.pages.SurfacePage
import io.github.kdroidfilter.darwinui.sample.pages.SwitchPage
import io.github.kdroidfilter.darwinui.sample.pages.TablePage
import io.github.kdroidfilter.darwinui.sample.pages.TabsPage
import io.github.kdroidfilter.darwinui.sample.pages.TextAreaPage
import io.github.kdroidfilter.darwinui.sample.pages.ToastPage
import io.github.kdroidfilter.darwinui.sample.pages.TopAppBarPage
import io.github.kdroidfilter.darwinui.sample.pages.TooltipPage
import io.github.kdroidfilter.darwinui.sample.pages.UploadPage
import io.github.kdroidfilter.darwinui.theme.AccentColor
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

// Navigation data — static tuples (id, label, group, icon)
private data class SidebarEntryDef(val id: String, val label: String, val group: String, val icon: ImageVector)

private val sidebarEntryDefs = listOf(
    SidebarEntryDef("button", "Button", "FORM CONTROLS", Lucide.MousePointerClick),
    SidebarEntryDef("input", "Input", "FORM CONTROLS", Lucide.TextCursorInput),
    SidebarEntryDef("textarea", "Textarea", "FORM CONTROLS", Lucide.TextAlignStart),
    SidebarEntryDef("checkbox", "Checkbox", "FORM CONTROLS", Lucide.SquareCheck),
    SidebarEntryDef("switch", "Switch", "FORM CONTROLS", Lucide.ToggleLeft),
    SidebarEntryDef("select", "Select", "FORM CONTROLS", Lucide.ChevronsUpDown),
    SidebarEntryDef("multiselect", "Multi Select", "FORM CONTROLS", Lucide.ListChecks),
    SidebarEntryDef("searchinput", "Search Input", "FORM CONTROLS", Lucide.Search),
    SidebarEntryDef("slider", "Slider", "FORM CONTROLS", Lucide.SlidersHorizontal),
    SidebarEntryDef("dateselect", "Date Select", "FORM CONTROLS", Lucide.Calendar),
    SidebarEntryDef("upload", "Upload", "FORM CONTROLS", Lucide.Upload),
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
    SidebarEntryDef("sidebar", "Sidebar", "NAVIGATION", Lucide.PanelLeft),
    SidebarEntryDef("m3buttons", "M3 Buttons", "MATERIAL 3", Lucide.MousePointerClick),
    SidebarEntryDef("iconbutton", "Icon Button", "MATERIAL 3", Lucide.SquareCheck),
    SidebarEntryDef("fab", "FAB", "MATERIAL 3", Lucide.Layers),
    SidebarEntryDef("topappbar", "Top App Bar", "MATERIAL 3", Lucide.PanelTopOpen),
    SidebarEntryDef("navigationbar", "Navigation Bar", "MATERIAL 3", Lucide.Menu),
    SidebarEntryDef("chips", "Chips", "MATERIAL 3", Lucide.Tag),
    SidebarEntryDef("listitem", "List Item", "MATERIAL 3", Lucide.ListChecks),
    SidebarEntryDef("divider", "Divider", "MATERIAL 3", Lucide.SlidersHorizontal),
    SidebarEntryDef("snackbar", "Snackbar", "MATERIAL 3", Lucide.Bell),
    SidebarEntryDef("bottomsheet", "Bottom Sheet", "MATERIAL 3", Lucide.PanelLeft),
    SidebarEntryDef("radiobutton", "Radio Button", "MATERIAL 3", Lucide.ListChecks),
    SidebarEntryDef("surface", "Surface", "MATERIAL 3", Lucide.Layers),
    SidebarEntryDef("navigationrail", "Navigation Rail", "MATERIAL 3", Lucide.PanelLeft),
    SidebarEntryDef("bottomappbar", "Bottom App Bar", "MATERIAL 3", Lucide.PanelTopOpen),
    SidebarEntryDef("segmentedbutton", "Segmented Button", "MATERIAL 3", Lucide.Columns3),
    SidebarEntryDef("timepicker", "Time Picker", "MATERIAL 3", Lucide.Calendar),
)

@Composable
fun App() {
    var isDark by remember { mutableStateOf(false) }
    var accentColor by remember { mutableStateOf(AccentColor.Blue) }

    DarwinTheme(darkTheme = isDark, accentColor = accentColor) {
        val toastState = rememberToastState()

        Box(modifier = Modifier.fillMaxSize().background(DarwinTheme.colors.background)) {
            var selectedPage by remember { mutableStateOf("button") }
            var searchQuery by remember { mutableStateOf("") }
            var sidebarCollapsed by remember { mutableStateOf(false) }

            Row(modifier = Modifier.fillMaxSize()) {
                // Gallery navigation sidebar using Sidebar
                val query = searchQuery.lowercase().trim()
                val filteredDefs = if (query.isEmpty()) {
                    sidebarEntryDefs
                } else {
                    sidebarEntryDefs.filter { it.label.lowercase().contains(query) }
                }
                val sidebarItems = filteredDefs.map { def ->
                    SidebarItem(
                        label = def.label,
                        onClick = { selectedPage = def.id },
                        icon = def.icon,
                        group = def.group,
                        id = def.id,
                    )
                }

                Box(modifier = Modifier.fillMaxHeight().background(DarwinTheme.colors.backgroundElevated)) {
                    Sidebar(
                        items = sidebarItems,
                        activeItem = selectedPage,
                        showBorder = true,
                        collapsed = sidebarCollapsed,
                        onCollapsedChange = { sidebarCollapsed = it },
                        header = {
                            Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Column {
                                            Text(
                                                text = "Darwin UI",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = DarwinTheme.colors.textPrimary,
                                            )
                                            Text(
                                                text = "Component Docs",
                                                style = DarwinTheme.typography.bodySmall,
                                                color = DarwinTheme.colors.textTertiary,
                                            )
                                        }
                                        SubtleButton(
                                            onClick = { isDark = !isDark },
                                        ) {
                                            Icon(if (isDark) LucideSun else LucideMoon)
                                        }
                                    }
                                    SearchField(
                                        value = searchQuery,
                                        onValueChange = { searchQuery = it },
                                        placeholder = "Search components...",
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                    AccentColorPicker(
                                        selected = accentColor,
                                        onSelect = { accentColor = it },
                                    )
                                    if (filteredDefs.isEmpty()) {
                                        Text(
                                            text = "No results found",
                                            style = DarwinTheme.typography.bodySmall,
                                            color = DarwinTheme.colors.textTertiary,
                                            modifier = Modifier.padding(top = 8.dp),
                                        )
                                    }
                                }
                            }
                        },
                    )
                }

                // Main content area
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                        .padding(40.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    when (selectedPage) {
                        "button" -> ButtonPage()
                        "input" -> InputPage()
                        "searchinput" -> SearchInputPage()
                        "textarea" -> TextAreaPage()
                        "checkbox" -> CheckboxPage()
                        "switch" -> SwitchPage()
                        "select" -> SelectPage()
                        "multiselect" -> MultiSelectPage()
                        "slider" -> SliderPage()
                        "upload" -> UploadPage()
                        "dateselect" -> DateSelectPage()
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
                        "m3buttons" -> M3ButtonsPage()
                        "iconbutton" -> IconButtonPage()
                        "fab" -> FabPage()
                        "topappbar" -> TopAppBarPage()
                        "navigationbar" -> NavigationBarPage()
                        "chips" -> ChipsPage()
                        "listitem" -> ListItemPage()
                        "divider" -> DividerPage()
                        "snackbar" -> SnackbarPage()
                        "bottomsheet" -> BottomSheetPage()
                        "radiobutton" -> RadioButtonPage()
                        "surface" -> SurfacePage()
                        "navigationrail" -> NavigationRailPage()
                        "bottomappbar" -> BottomAppBarPage()
                        "segmentedbutton" -> SegmentedButtonPage()
                        "timepicker" -> TimePickerPage()
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                }
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
        AccentColor.entries.forEach { color ->
            val isSelected = color == selected
            val displayColor = if (DarwinTheme.colors.isDark) color.dark else color.light
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(displayColor, CircleShape)
                    .then(
                        if (isSelected) {
                            Modifier.border(2.dp, DarwinTheme.colors.textPrimary, CircleShape)
                        } else {
                            Modifier
                        }
                    )
                    .clickable { onSelect(color) },
            )
        }
    }
}
