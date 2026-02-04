package io.github.kdroidfilter.darwinui.sample

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import io.github.kdroidfilter.darwinui.icons.DarwinIcon
import io.github.kdroidfilter.darwinui.icons.LucideDownload
import io.github.kdroidfilter.darwinui.icons.LucideHeart
import io.github.kdroidfilter.darwinui.icons.LucideMoon
import io.github.kdroidfilter.darwinui.icons.LucidePlus
import io.github.kdroidfilter.darwinui.icons.LucideSettings
import io.github.kdroidfilter.darwinui.icons.LucideShare2
import io.github.kdroidfilter.darwinui.icons.LucideSun
import io.github.kdroidfilter.darwinui.icons.LucideTrash2
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.darwinui.components.accordion.DarwinAccordion
import io.github.kdroidfilter.darwinui.components.accordion.DarwinAccordionItem
import io.github.kdroidfilter.darwinui.components.accordion.DarwinAccordionType
import io.github.kdroidfilter.darwinui.components.alert.DarwinAlertBanner
import io.github.kdroidfilter.darwinui.components.alert.DarwinAlertDialog
import io.github.kdroidfilter.darwinui.components.alert.DarwinAlertType
import io.github.kdroidfilter.darwinui.components.avatar.AvatarData
import io.github.kdroidfilter.darwinui.components.avatar.DarwinAvatar
import io.github.kdroidfilter.darwinui.components.avatar.DarwinAvatarGroup
import io.github.kdroidfilter.darwinui.components.badge.DarwinBadge
import io.github.kdroidfilter.darwinui.components.badge.DarwinBadgeVariant
import io.github.kdroidfilter.darwinui.components.button.DarwinButton
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonSize
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonVariant
import io.github.kdroidfilter.darwinui.components.card.DarwinCard
import io.github.kdroidfilter.darwinui.components.card.DarwinCardContent
import io.github.kdroidfilter.darwinui.components.card.DarwinCardDescription
import io.github.kdroidfilter.darwinui.components.card.DarwinCardFooter
import io.github.kdroidfilter.darwinui.components.card.DarwinCardHeader
import io.github.kdroidfilter.darwinui.components.card.DarwinCardTitle
import io.github.kdroidfilter.darwinui.components.checkbox.DarwinCheckbox
import io.github.kdroidfilter.darwinui.components.closebutton.DarwinCloseButton
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialog
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialogContent
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialogDescription
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialogFooter
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialogHeader
import io.github.kdroidfilter.darwinui.components.dialog.DarwinDialogTitle
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenu
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenuCheckboxItem
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenuItem
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenuLabel
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenuSeparator
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenuShortcut
import io.github.kdroidfilter.darwinui.components.input.DarwinSearchField
import io.github.kdroidfilter.darwinui.components.input.DarwinTextArea
import io.github.kdroidfilter.darwinui.components.input.DarwinTextField
import io.github.kdroidfilter.darwinui.components.popover.DarwinPopover
import io.github.kdroidfilter.darwinui.components.progress.DarwinCircularProgress
import io.github.kdroidfilter.darwinui.components.progress.DarwinLinearProgress
import io.github.kdroidfilter.darwinui.components.progress.DarwinProgressVariant
import io.github.kdroidfilter.darwinui.components.reveal.DarwinRevealOnce
import io.github.kdroidfilter.darwinui.components.select.DarwinMultiSelect
import io.github.kdroidfilter.darwinui.components.select.DarwinSelect
import io.github.kdroidfilter.darwinui.components.select.DarwinSelectOption
import io.github.kdroidfilter.darwinui.components.sidebar.DarwinSidebar
import io.github.kdroidfilter.darwinui.components.sidebar.DarwinSidebarItem
import io.github.kdroidfilter.darwinui.components.sidebar.DarwinSidebarSection
import io.github.kdroidfilter.darwinui.components.skeleton.DarwinSkeleton
import io.github.kdroidfilter.darwinui.components.skeleton.DarwinSkeletonCircle
import io.github.kdroidfilter.darwinui.components.skeleton.DarwinSkeletonText
import io.github.kdroidfilter.darwinui.components.slider.DarwinSlider
import io.github.kdroidfilter.darwinui.components.switchcomponent.DarwinSwitch
import io.github.kdroidfilter.darwinui.components.table.DarwinTable
import io.github.kdroidfilter.darwinui.components.table.DarwinTableBody
import io.github.kdroidfilter.darwinui.components.table.DarwinTableCell
import io.github.kdroidfilter.darwinui.components.table.DarwinTableHead
import io.github.kdroidfilter.darwinui.components.table.DarwinTableHeaderCell
import io.github.kdroidfilter.darwinui.components.table.DarwinTableRow
import io.github.kdroidfilter.darwinui.components.tabs.DarwinTabs
import io.github.kdroidfilter.darwinui.components.tabs.DarwinTabsContent
import io.github.kdroidfilter.darwinui.components.tabs.DarwinTabsList
import io.github.kdroidfilter.darwinui.components.tabs.DarwinTabsTrigger
import io.github.kdroidfilter.darwinui.components.toast.DarwinToastHost
import io.github.kdroidfilter.darwinui.components.toast.DarwinToastState
import io.github.kdroidfilter.darwinui.components.toast.DarwinToastType
import io.github.kdroidfilter.darwinui.components.toast.rememberDarwinToastState
import io.github.kdroidfilter.darwinui.components.tooltip.DarwinTooltip
import io.github.kdroidfilter.darwinui.components.topbar.DarwinTopbar
import io.github.kdroidfilter.darwinui.components.upload.DarwinUpload
import io.github.kdroidfilter.darwinui.components.upload.DarwinUploadFile
import io.github.kdroidfilter.darwinui.components.upload.DarwinUploadVariant
import io.github.kdroidfilter.darwinui.components.dateselect.DarwinDateSelect
import io.github.kdroidfilter.darwinui.components.dateselect.DateConfig
import io.github.kdroidfilter.darwinui.components.window.DarwinWindow
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.CodeBlock
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.Blue500
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinSpring

// Navigation data
private data class SidebarEntry(val id: String, val label: String, val group: String)

private val sidebarEntries = listOf(
    SidebarEntry("button", "Button", "FORM CONTROLS"),
    SidebarEntry("input", "Input", "FORM CONTROLS"),
    SidebarEntry("textarea", "Textarea", "FORM CONTROLS"),
    SidebarEntry("checkbox", "Checkbox", "FORM CONTROLS"),
    SidebarEntry("switch", "Switch", "FORM CONTROLS"),
    SidebarEntry("select", "Select", "FORM CONTROLS"),
    SidebarEntry("multiselect", "Multi Select", "FORM CONTROLS"),
    SidebarEntry("searchinput", "Search Input", "FORM CONTROLS"),
    SidebarEntry("slider", "Slider", "FORM CONTROLS"),
    SidebarEntry("dateselect", "Date Select", "FORM CONTROLS"),
    SidebarEntry("upload", "Upload", "FORM CONTROLS"),
    SidebarEntry("badge", "Badge", "DATA DISPLAY"),
    SidebarEntry("avatar", "Avatar", "DATA DISPLAY"),
    SidebarEntry("card", "Card", "DATA DISPLAY"),
    SidebarEntry("table", "Table", "DATA DISPLAY"),
    SidebarEntry("progress", "Progress", "DATA DISPLAY"),
    SidebarEntry("skeleton", "Skeleton", "DATA DISPLAY"),
    SidebarEntry("alert", "Alert", "FEEDBACK"),
    SidebarEntry("toast", "Toast", "FEEDBACK"),
    SidebarEntry("dialog", "Dialog", "OVERLAYS"),
    SidebarEntry("tooltip", "Tooltip", "OVERLAYS"),
    SidebarEntry("popover", "Popover", "OVERLAYS"),
    SidebarEntry("dropdown", "Dropdown Menu", "OVERLAYS"),
    SidebarEntry("tabs", "Tabs", "NAVIGATION"),
    SidebarEntry("accordion", "Accordion", "NAVIGATION"),
    SidebarEntry("sidebar", "Sidebar", "NAVIGATION"),
    SidebarEntry("window", "Window", "LAYOUT"),
    SidebarEntry("topbar", "Top Bar", "LAYOUT"),
    SidebarEntry("closebutton", "Close Button", "LAYOUT"),
    SidebarEntry("reveal", "Reveal", "EFFECTS"),
)

@Composable
fun App() {
    var isDark by remember { mutableStateOf(true) }

    DarwinTheme(darkTheme = isDark) {
        val toastState = rememberDarwinToastState()

        Box(modifier = Modifier.fillMaxSize().background(DarwinTheme.colors.background)) {
            var selectedPage by remember { mutableStateOf("button") }
            var searchQuery by remember { mutableStateOf("") }

            Row(modifier = Modifier.fillMaxSize()) {
                // Sidebar
                DarwinSidebar(
                    width = 240.dp,
                    header = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Column {
                                    DarwinText(
                                        text = "Darwin UI",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DarwinTheme.colors.textPrimary,
                                    )
                                    DarwinText(
                                        text = "Component Docs",
                                        style = DarwinTheme.typography.bodySmall,
                                        color = DarwinTheme.colors.textTertiary,
                                    )
                                }
                                // Theme toggle
                                DarwinButton(
                                    onClick = { isDark = !isDark },
                                    variant = DarwinButtonVariant.Ghost,
                                    size = DarwinButtonSize.Icon,
                                ) {
                                    DarwinIcon(if (isDark) LucideSun else LucideMoon)
                                }
                            }
                            // Search
                            DarwinSearchField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = "Search components...",
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    },
                ) {
                    val query = searchQuery.lowercase().trim()
                    val filtered = if (query.isEmpty()) sidebarEntries
                    else sidebarEntries.filter { it.label.lowercase().contains(query) }

                    val groups = filtered.groupBy { it.group }
                    for ((group, entries) in groups) {
                        DarwinSidebarSection(title = group) {
                            for (entry in entries) {
                                DarwinSidebarItem(
                                    label = entry.label,
                                    selected = selectedPage == entry.id,
                                    onClick = { selectedPage = entry.id },
                                )
                            }
                        }
                    }

                    if (filtered.isEmpty()) {
                        DarwinText(
                            text = "No results found",
                            style = DarwinTheme.typography.bodySmall,
                            color = DarwinTheme.colors.textTertiary,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
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
                        "tabs" -> TabsPage()
                        "accordion" -> AccordionPage()
                        "sidebar" -> SidebarPage()
                        "window" -> WindowPage()
                        "topbar" -> TopBarPage()
                        "closebutton" -> CloseButtonPage()
                        "reveal" -> RevealPage()
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                }
            }

            DarwinToastHost(state = toastState)
        }
    }
}

// =============================================================================
// Preview composables — show ALL variants together at top of page
// =============================================================================

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ButtonPreview() {
    var loading by remember { mutableStateOf(false) }

    LaunchedEffect(loading) {
        if (loading) {
            delay(2000)
            loading = false
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Text Buttons
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DarwinText("TEXT BUTTONS", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DarwinButton(text = "Primary", onClick = {}, variant = DarwinButtonVariant.Primary)
                DarwinButton(text = "Secondary", onClick = {}, variant = DarwinButtonVariant.Secondary)
                DarwinButton(text = "Outline", onClick = {}, variant = DarwinButtonVariant.Outline)
                DarwinButton(text = "Ghost", onClick = {}, variant = DarwinButtonVariant.Ghost)
                DarwinButton(text = "Destructive", onClick = {}, variant = DarwinButtonVariant.Destructive)
                DarwinButton(
                    text = if (loading) "Loading..." else "Click me",
                    onClick = { loading = true },
                    variant = DarwinButtonVariant.Primary,
                    loading = loading,
                )
            }
        }
        // Icon Buttons
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DarwinText("ICON BUTTONS", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DarwinButton(onClick = {}, variant = DarwinButtonVariant.Primary, size = DarwinButtonSize.Icon) { DarwinIcon(LucidePlus) }
                DarwinButton(onClick = {}, variant = DarwinButtonVariant.Secondary, size = DarwinButtonSize.Icon) { DarwinIcon(LucideSettings) }
                DarwinButton(onClick = {}, variant = DarwinButtonVariant.Outline, size = DarwinButtonSize.Icon) { DarwinIcon(LucideHeart) }
                DarwinButton(onClick = {}, variant = DarwinButtonVariant.Ghost, size = DarwinButtonSize.Icon) { DarwinIcon(LucideShare2) }
                DarwinButton(onClick = {}, variant = DarwinButtonVariant.Destructive, size = DarwinButtonSize.Icon) { DarwinIcon(LucideTrash2) }
            }
        }
        // With Icons
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DarwinText("WITH ICONS", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                DarwinButton(text = "Create New", onClick = {}, variant = DarwinButtonVariant.Primary, leftIcon = { DarwinIcon(LucidePlus, modifier = Modifier.padding(end = 4.dp)) })
                DarwinButton(text = "Download", onClick = {}, variant = DarwinButtonVariant.Secondary, leftIcon = { DarwinIcon(LucideDownload, modifier = Modifier.padding(end = 4.dp)) })
                DarwinButton(text = "Share", onClick = {}, variant = DarwinButtonVariant.Outline, leftIcon = { DarwinIcon(LucideShare2, modifier = Modifier.padding(end = 4.dp)) })
            }
        }
    }
}

@Composable
private fun InputPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth(0.5f)) {
        var text by remember { mutableStateOf("") }
        var errorText by remember { mutableStateOf("") }
        var successText by remember { mutableStateOf("Valid input") }
        DarwinTextField(value = text, onValueChange = { text = it }, placeholder = "Type something...")
        DarwinTextField(value = errorText, onValueChange = { errorText = it }, placeholder = "Error state", isError = true)
        DarwinTextField(value = successText, onValueChange = { successText = it }, placeholder = "Success state", isSuccess = true)
    }
}

@Composable
private fun CheckboxPreview() {
    var c1 by remember { mutableStateOf(false) }
    var c2 by remember { mutableStateOf(true) }
    var c3 by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        DarwinCheckbox(checked = c1, onCheckedChange = { c1 = it }, label = "Unchecked option")
        DarwinCheckbox(checked = c2, onCheckedChange = { c2 = it }, label = "Checked option")
        DarwinCheckbox(checked = c3, onCheckedChange = { c3 = it }, indeterminate = !c3, label = "Indeterminate")
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BadgePreview() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinBadge(variant = DarwinBadgeVariant.Default) { DarwinText("Default") }
        DarwinBadge(variant = DarwinBadgeVariant.Secondary) { DarwinText("Secondary") }
        DarwinBadge(variant = DarwinBadgeVariant.Outline) { DarwinText("Outline") }
        DarwinBadge(variant = DarwinBadgeVariant.Success) { DarwinText("Success") }
        DarwinBadge(variant = DarwinBadgeVariant.Warning) { DarwinText("Warning") }
        DarwinBadge(variant = DarwinBadgeVariant.Destructive) { DarwinText("Error") }
        DarwinBadge(variant = DarwinBadgeVariant.Info) { DarwinText("Info") }
        DarwinBadge(variant = DarwinBadgeVariant.Published) { DarwinText("Published") }
        DarwinBadge(variant = DarwinBadgeVariant.Draft) { DarwinText("Draft") }
        DarwinBadge(variant = DarwinBadgeVariant.Glass) { DarwinText("Glass") }
    }
}

@Composable
private fun ProgressPreview() {
    Column(
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DarwinLinearProgress(value = 65f, max = 100f, showValue = true)
        DarwinLinearProgress(value = 40f, max = 100f, variant = DarwinProgressVariant.Success)
        DarwinLinearProgress(value = 80f, max = 100f, variant = DarwinProgressVariant.Gradient)
        DarwinLinearProgress(indeterminate = true)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            DarwinCircularProgress(value = 75f, max = 100f, showValue = true)
            DarwinCircularProgress(value = 45f, max = 100f, variant = DarwinProgressVariant.Success, showValue = true)
            DarwinCircularProgress(indeterminate = true)
        }
    }
}

@Composable
private fun AlertPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinAlertBanner(message = "This is an informational alert.", title = "Information", type = DarwinAlertType.Info)
        DarwinAlertBanner(message = "Operation completed successfully!", title = "Success", type = DarwinAlertType.Success)
        DarwinAlertBanner(message = "Please review before proceeding.", title = "Warning", type = DarwinAlertType.Warning)
        DarwinAlertBanner(message = "An error occurred.", title = "Error", type = DarwinAlertType.Error)
    }
}

// =============================================================================
// Example functions — annotated with @GalleryExample for KSP source extraction
// =============================================================================

// --- Button ---

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Variants")
@Composable
fun ButtonVariantsExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DarwinButton(text = "Default", onClick = {})
        DarwinButton(text = "Primary", onClick = {}, variant = DarwinButtonVariant.Primary)
        DarwinButton(text = "Secondary", onClick = {}, variant = DarwinButtonVariant.Secondary)
        DarwinButton(text = "Accent", onClick = {}, variant = DarwinButtonVariant.Accent)
        DarwinButton(text = "Success", onClick = {}, variant = DarwinButtonVariant.Success)
        DarwinButton(text = "Warning", onClick = {}, variant = DarwinButtonVariant.Warning)
        DarwinButton(text = "Info", onClick = {}, variant = DarwinButtonVariant.Info)
        DarwinButton(text = "Destructive", onClick = {}, variant = DarwinButtonVariant.Destructive)
        DarwinButton(text = "Outline", onClick = {}, variant = DarwinButtonVariant.Outline)
        DarwinButton(text = "Ghost", onClick = {}, variant = DarwinButtonVariant.Ghost)
        DarwinButton(text = "Link", onClick = {}, variant = DarwinButtonVariant.Link)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Sizes")
@Composable
fun ButtonSizesExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DarwinButton(text = "Small", onClick = {}, size = DarwinButtonSize.Small)
        DarwinButton(text = "Default", onClick = {}, size = DarwinButtonSize.Default)
        DarwinButton(text = "Large", onClick = {}, size = DarwinButtonSize.Large)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "States")
@Composable
fun ButtonStatesExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DarwinButton(text = "Disabled", onClick = {}, enabled = false)
        DarwinButton(text = "Loading", onClick = {}, loading = true)
        DarwinButton(text = "Loading + text", onClick = {}, loading = true, loadingText = "Saving...")
        DarwinButton(text = "Glass", onClick = {}, glass = true)
    }
}

@GalleryExample("Input", "Default")
@Composable
fun InputDefaultExample() {
    var text by remember { mutableStateOf("") }
    DarwinTextField(value = text, onValueChange = { text = it }, placeholder = "Enter your name...", label = "Name", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("Input", "Error State")
@Composable
fun InputErrorExample() {
    var text by remember { mutableStateOf("") }
    DarwinTextField(value = text, onValueChange = { text = it }, placeholder = "Required field", label = "Email", isError = true, supportingText = "This field is required", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("Input", "Success State")
@Composable
fun InputSuccessExample() {
    var text by remember { mutableStateOf("valid@email.com") }
    DarwinTextField(value = text, onValueChange = { text = it }, label = "Verified email", isSuccess = true, supportingText = "Email verified", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("Input", "With Label")
@Composable
fun InputWithLabelExample() {
    var text by remember { mutableStateOf("") }
    DarwinTextField(value = text, onValueChange = { text = it }, placeholder = "you@example.com", label = "Email address", supportingText = "We'll never share your email.", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("Input", "Password")
@Composable
fun InputPasswordExample() {
    var password by remember { mutableStateOf("") }
    DarwinTextField(value = password, onValueChange = { password = it }, placeholder = "Password", label = "Password", password = true, modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("SearchInput", "Default")
@Composable
fun SearchInputDefaultExample() {
    var query by remember { mutableStateOf("") }
    DarwinSearchField(value = query, onValueChange = { query = it }, placeholder = "Search...", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("SearchInput", "With Label")
@Composable
fun SearchInputWithLabelExample() {
    var query by remember { mutableStateOf("") }
    DarwinSearchField(value = query, onValueChange = { query = it }, placeholder = "Search components...", label = "Search", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("TextArea", "Default")
@Composable
fun TextAreaDefaultExample() {
    val maxChars = 200
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth(0.5f)) {
        DarwinTextArea(
            value = text,
            onValueChange = { if (it.length <= maxChars) text = it },
            placeholder = "Write your message here...",
            modifier = Modifier.fillMaxWidth(),
        )
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, end = 2.dp), horizontalArrangement = Arrangement.End) {
            DarwinText(
                text = "${text.length}/$maxChars",
                style = DarwinTheme.typography.caption,
                color = if (text.length >= maxChars) DarwinTheme.colors.destructive else DarwinTheme.colors.textTertiary,
            )
        }
    }
}

@GalleryExample("TextArea", "Error State")
@Composable
fun TextAreaErrorExample() {
    var text by remember { mutableStateOf("") }
    DarwinTextArea(
        value = text,
        onValueChange = { text = it },
        placeholder = "Error state textarea",
        isError = true,
        minLines = 2,
        maxLines = 2,
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("Checkbox", "States")
@Composable
fun CheckboxStatesExample() {
    var checked1 by remember { mutableStateOf(false) }
    var checked2 by remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinCheckbox(checked = checked1, onCheckedChange = { checked1 = it }, label = "Accept terms")
        DarwinCheckbox(checked = checked2, onCheckedChange = { checked2 = it }, label = "Enabled and checked")
        DarwinCheckbox(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false)
        DarwinCheckbox(checked = true, onCheckedChange = {}, indeterminate = true, label = "Indeterminate")
    }
}

@GalleryExample("Checkbox", "Select All")
@Composable
fun CheckboxSelectAllExample() {
    var items by remember { mutableStateOf(listOf(true, false, true)) }
    val allChecked = items.all { it }
    val someChecked = items.any { it } && !allChecked

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinCheckbox(
            checked = allChecked,
            onCheckedChange = { checked -> items = List(3) { checked } },
            indeterminate = someChecked,
            label = "Select all",
        )
        Column(
            modifier = Modifier.padding(start = 24.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items.forEachIndexed { i, checked ->
                DarwinCheckbox(
                    checked = checked,
                    onCheckedChange = { newVal ->
                        items = items.toMutableList().also { it[i] = newVal }
                    },
                    label = "Item ${i + 1}",
                )
            }
        }
    }
}

@GalleryExample("Switch", "States")
@Composable
fun SwitchStatesExample() {
    var switch1 by remember { mutableStateOf(false) }
    var switch2 by remember { mutableStateOf(true) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinSwitch(checked = switch1, onCheckedChange = { switch1 = it }, label = "Dark mode")
        DarwinSwitch(checked = switch2, onCheckedChange = { switch2 = it }, label = "Notifications")
        DarwinSwitch(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false)
    }
}

@GalleryExample("Select", "Default")
@Composable
fun SelectDefaultExample() {
    val options = listOf(DarwinSelectOption("react", "React"), DarwinSelectOption("vue", "Vue"), DarwinSelectOption("angular", "Angular"), DarwinSelectOption("svelte", "Svelte"))
    var selected by remember { mutableStateOf<String?>(null) }
    DarwinSelect(options = options, selectedValue = selected, onValueChange = { selected = it }, label = "Framework", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("Select", "Pre-selected")
@Composable
fun SelectPreselectedExample() {
    val options = listOf(DarwinSelectOption("react", "React"), DarwinSelectOption("vue", "Vue"), DarwinSelectOption("angular", "Angular"), DarwinSelectOption("svelte", "Svelte"))
    var selected by remember { mutableStateOf<String?>("react") }
    DarwinSelect(options = options, selectedValue = selected, onValueChange = { selected = it }, label = "Default Framework", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("MultiSelect", "Default")
@Composable
fun MultiSelectDefaultExample() {
    val options = listOf(DarwinSelectOption("react", "React"), DarwinSelectOption("vue", "Vue"), DarwinSelectOption("angular", "Angular"), DarwinSelectOption("svelte", "Svelte"), DarwinSelectOption("solid", "SolidJS"))
    var selected by remember { mutableStateOf(listOf<String>()) }
    DarwinMultiSelect(options = options, selectedValues = selected, onValuesChange = { selected = it }, label = "Technologies", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("MultiSelect", "Pre-selected")
@Composable
fun MultiSelectPreselectedExample() {
    val options = listOf(DarwinSelectOption("react", "React"), DarwinSelectOption("vue", "Vue"), DarwinSelectOption("angular", "Angular"), DarwinSelectOption("svelte", "Svelte"), DarwinSelectOption("solid", "SolidJS"))
    var selected by remember { mutableStateOf(listOf("react", "vue")) }
    DarwinMultiSelect(options = options, selectedValues = selected, onValuesChange = { selected = it }, label = "Favorites", modifier = Modifier.fillMaxWidth(0.5f))
}

@GalleryExample("Slider", "Volume")
@Composable
fun SliderVolumeExample() {
    var value by remember { mutableStateOf(50f) }
    Column(modifier = Modifier.fillMaxWidth(0.5f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            DarwinText(
                text = "Volume",
                style = DarwinTheme.typography.bodySmall,
                color = DarwinTheme.colors.textTertiary,
            )
            DarwinText(
                text = "${value.toInt()}%",
                style = DarwinTheme.typography.bodySmall,
                color = androidx.compose.ui.graphics.Color(0xFF60A5FA), // blue-400
            )
        }
        DarwinSlider(value = value, onValueChange = { value = it }, min = 0f, max = 100f)
    }
}

@GalleryExample("Slider", "With Value Display")
@Composable
fun SliderWithValueExample() {
    var value by remember { mutableStateOf(50f) }
    Column(modifier = Modifier.fillMaxWidth(0.5f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinText(
            text = "With value display",
            style = DarwinTheme.typography.bodySmall,
            color = DarwinTheme.colors.textTertiary,
        )
        DarwinSlider(value = value, onValueChange = { value = it }, min = 0f, max = 100f, showValue = true)
    }
}

@GalleryExample("Upload", "Default")
@Composable
fun UploadDefaultExample() {
    val files = remember {
        mutableStateListOf(
            DarwinUploadFile(name = "hero-image.jpg", url = "https://example.com/hero.jpg"),
            DarwinUploadFile(name = "product-shot.png", url = "https://example.com/product.png"),
            DarwinUploadFile(name = "banner.jpg", url = "https://example.com/banner.jpg"),
        )
    }
    DarwinUpload(
        files = files,
        onPickFiles = { files.add(DarwinUploadFile(name = "new-image.jpg", isUploading = true, progress = 0.4f)) },
        onRemoveFile = { index -> files.removeAt(index) },
        onClearAll = { files.clear() },
        onSetCover = { index ->
            if (index > 0 && index < files.size) {
                val item = files.removeAt(index)
                files.add(0, item)
            }
        },
        onSwap = { i, j ->
            if (i in files.indices && j in files.indices) {
                val tmp = files[i]
                files[i] = files[j]
                files[j] = tmp
            }
        },
        maxFiles = 4,
        label = "Upload product images",
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Upload", "Compact")
@Composable
fun UploadCompactExample() {
    DarwinUpload(
        files = emptyList(),
        onPickFiles = {},
        variant = DarwinUploadVariant.Compact,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Upload", "Inline")
@Composable
fun UploadInlineExample() {
    DarwinUpload(
        files = emptyList(),
        onPickFiles = {},
        variant = DarwinUploadVariant.Inline,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Badge", "Variants")
@Composable
fun BadgeVariantsExample() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinBadge(variant = DarwinBadgeVariant.Default) { DarwinText("Default") }
        DarwinBadge(variant = DarwinBadgeVariant.Secondary) { DarwinText("Secondary") }
        DarwinBadge(variant = DarwinBadgeVariant.Outline) { DarwinText("Outline") }
        DarwinBadge(variant = DarwinBadgeVariant.Success) { DarwinText("Success") }
        DarwinBadge(variant = DarwinBadgeVariant.Warning) { DarwinText("Warning") }
        DarwinBadge(variant = DarwinBadgeVariant.Destructive) { DarwinText("Error") }
        DarwinBadge(variant = DarwinBadgeVariant.Info) { DarwinText("Info") }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Badge", "Semantic Variants")
@Composable
fun BadgeSemanticVariantsExample() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinBadge(variant = DarwinBadgeVariant.Published) { DarwinText("Published") }
        DarwinBadge(variant = DarwinBadgeVariant.Draft) { DarwinText("Draft") }
        DarwinBadge(variant = DarwinBadgeVariant.Archived) { DarwinText("Archived") }
        DarwinBadge(variant = DarwinBadgeVariant.NewBadge) { DarwinText("New") }
        DarwinBadge(variant = DarwinBadgeVariant.Responded) { DarwinText("Responded") }
        DarwinBadge(variant = DarwinBadgeVariant.Glass) { DarwinText("Glass") }
    }
}

@GalleryExample("Avatar", "Sizes")
@Composable
fun AvatarSizesExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        DarwinAvatar(name = "Alice Smith")
        DarwinAvatar(name = "Bob Jones", size = 48.dp)
        DarwinAvatar(name = "Carol White", size = 56.dp)
        DarwinAvatar(name = "Dan Brown")
    }
}

@GalleryExample("Avatar", "Avatar Group")
@Composable
fun AvatarGroupExample() {
    DarwinAvatarGroup(avatars = listOf(AvatarData(name = "Alice Smith"), AvatarData(name = "Bob Jones"), AvatarData(name = "Carol White"), AvatarData(name = "Dan Brown"), AvatarData(name = "Eve Taylor"), AvatarData(name = "Frank Lee")), maxDisplay = 4)
}

@GalleryExample("Card", "Default")
@Composable
fun CardDefaultExample() {
    // Pixel-perfect match with React previews.tsx CardPreview()
    DarwinCard(modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth()) {
        DarwinCardHeader {
            DarwinCardTitle { DarwinText("Card Title") }
            DarwinCardDescription { DarwinText("This is a description of the card content.") }
        }
        DarwinCardContent {
            DarwinText(
                "Cards can contain any content including text, images, and other components.",
                color = DarwinTheme.colors.mutedForeground,
            )
        }
        DarwinCardFooter {
            DarwinButton(text = "Action", onClick = {}, variant = DarwinButtonVariant.Primary, size = DarwinButtonSize.Small)
            DarwinButton(text = "Cancel", onClick = {}, variant = DarwinButtonVariant.Ghost, size = DarwinButtonSize.Small)
        }
    }
}

@GalleryExample("Card", "Glass")
@Composable
fun CardGlassExample() {
    // Pixel-perfect match with React CardGlassExample from previews.tsx
    DarwinCard(modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth(), glass = true) {
        DarwinCardHeader {
            DarwinCardTitle { DarwinText("Glass Card") }
            DarwinCardDescription { DarwinText("Frosted glass effect") }
        }
        DarwinCardContent {
            DarwinText("Content with glassmorphism styling.", color = DarwinTheme.colors.mutedForeground)
        }
    }
}

@GalleryExample("Table", "Default")
@Composable
fun TableDefaultExample() {
    DarwinTable {
        DarwinTableHead { DarwinTableRow { DarwinTableHeaderCell { DarwinText("Name") }; DarwinTableHeaderCell { DarwinText("Email") }; DarwinTableHeaderCell { DarwinText("Role") }; DarwinTableHeaderCell(weight = 0.5f) { DarwinText("Status") } } }
        DarwinTableBody(scrollable = false) {
            DarwinTableRow { DarwinTableCell { DarwinText("Alice Smith") }; DarwinTableCell { DarwinText("alice@example.com") }; DarwinTableCell { DarwinText("Admin") }; DarwinTableCell(weight = 0.5f) { DarwinBadge(variant = DarwinBadgeVariant.Success) { DarwinText("Active") } } }
            DarwinTableRow { DarwinTableCell { DarwinText("Bob Jones") }; DarwinTableCell { DarwinText("bob@example.com") }; DarwinTableCell { DarwinText("Editor") }; DarwinTableCell(weight = 0.5f) { DarwinBadge(variant = DarwinBadgeVariant.Warning) { DarwinText("Pending") } } }
            DarwinTableRow { DarwinTableCell { DarwinText("Carol White") }; DarwinTableCell { DarwinText("carol@example.com") }; DarwinTableCell { DarwinText("Viewer") }; DarwinTableCell(weight = 0.5f) { DarwinBadge(variant = DarwinBadgeVariant.Archived) { DarwinText("Inactive") } } }
        }
    }
}

@GalleryExample("Progress", "Linear Default")
@Composable
fun ProgressLinearDefaultExample() {
    DarwinLinearProgress(
        value = 65f,
        max = 100f,
        showValue = true,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Linear Success")
@Composable
fun ProgressLinearSuccessExample() {
    DarwinLinearProgress(
        value = 40f,
        max = 100f,
        variant = DarwinProgressVariant.Success,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Linear Gradient")
@Composable
fun ProgressLinearGradientExample() {
    DarwinLinearProgress(
        value = 80f,
        max = 100f,
        variant = DarwinProgressVariant.Gradient,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Linear Indeterminate")
@Composable
fun ProgressLinearIndeterminateExample() {
    DarwinLinearProgress(
        indeterminate = true,
        modifier = Modifier.widthIn(max = 448.dp).fillMaxWidth(),
    )
}

@GalleryExample("Progress", "Circular")
@Composable
fun ProgressCircularExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        DarwinCircularProgress(value = 75f, max = 100f, showValue = true)
        DarwinCircularProgress(value = 45f, max = 100f, variant = DarwinProgressVariant.Success, showValue = true)
        DarwinCircularProgress(indeterminate = true)
    }
}

@GalleryExample("Skeleton", "Card Skeleton")
@Composable
fun SkeletonCardExample() {
    // Matches React preview: avatar + text lines + content block + button row
    Column(
        modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // Avatar row: circle h-12 w-12 + text lines
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            DarwinSkeletonCircle(size = 48.dp) // h-12 w-12
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                DarwinSkeleton(modifier = Modifier.fillMaxWidth(0.75f).height(16.dp)) // h-4 w-3/4
                DarwinSkeleton(modifier = Modifier.fillMaxWidth(0.5f).height(12.dp)) // h-3 w-1/2
            }
        }
        // Content block: h-24 w-full rounded-lg
        DarwinSkeleton(modifier = Modifier.fillMaxWidth().height(96.dp)) // h-24
        // Button row
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DarwinSkeleton(modifier = Modifier.width(80.dp).height(32.dp), shape = DarwinTheme.shapes.small) // h-8 w-20 rounded-md
            DarwinSkeleton(modifier = Modifier.width(80.dp).height(32.dp), shape = DarwinTheme.shapes.small) // h-8 w-20 rounded-md
        }
    }
}

@GalleryExample("Skeleton", "Glass Effect")
@Composable
fun SkeletonGlassExample() {
    // Matches React glass example: circle + 2 text lines
    Column(
        modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DarwinSkeletonCircle(size = 48.dp, glass = true)
        DarwinSkeleton(modifier = Modifier.fillMaxWidth(0.75f).height(16.dp), glass = true)
        DarwinSkeleton(modifier = Modifier.fillMaxWidth(0.5f).height(16.dp), glass = true)
    }
}

@GalleryExample("Alert", "Info")
@Composable
fun AlertInfoExample() { DarwinAlertBanner(message = "This is an informational alert.", title = "Information", type = DarwinAlertType.Info) }

@GalleryExample("Alert", "Success")
@Composable
fun AlertSuccessExample() { DarwinAlertBanner(message = "Operation completed successfully!", title = "Success", type = DarwinAlertType.Success) }

@GalleryExample("Alert", "Warning")
@Composable
fun AlertWarningExample() { DarwinAlertBanner(message = "Please review before proceeding.", title = "Warning", type = DarwinAlertType.Warning) }

@GalleryExample("Alert", "Error")
@Composable
fun AlertErrorExample() { DarwinAlertBanner(message = "An error occurred while processing.", title = "Error", type = DarwinAlertType.Error, onDismiss = {}) }

@GalleryExample("Alert", "Alert Dialog")
@Composable
fun AlertDialogExample() {
    var showAlertDialog by remember { mutableStateOf(false) }
    DarwinButton(text = "Show Alert Dialog", onClick = { showAlertDialog = true }, variant = DarwinButtonVariant.Destructive)
    if (showAlertDialog) { DarwinAlertDialog(open = true, onDismissRequest = { showAlertDialog = false }, title = "Delete item?", message = "This action cannot be undone. Are you sure you want to delete this item?", type = DarwinAlertType.Error, confirmText = "Delete", cancelText = "Cancel", onConfirm = { showAlertDialog = false }, onCancel = { showAlertDialog = false }) }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Toast", "Click to Show")
@Composable
fun ToastClickToShowExample(toastState: DarwinToastState) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinButton(text = "Info Toast", onClick = { toastState.show("This is an info message", title = "Info", type = DarwinToastType.Info) }, variant = DarwinButtonVariant.Info, size = DarwinButtonSize.Small)
        DarwinButton(text = "Success Toast", onClick = { toastState.show("Operation completed!", title = "Success", type = DarwinToastType.Success) }, variant = DarwinButtonVariant.Success, size = DarwinButtonSize.Small)
        DarwinButton(text = "Warning Toast", onClick = { toastState.show("Proceed with caution", title = "Warning", type = DarwinToastType.Warning) }, variant = DarwinButtonVariant.Warning, size = DarwinButtonSize.Small)
        DarwinButton(text = "Error Toast", onClick = { toastState.show("Something went wrong", title = "Error", type = DarwinToastType.Error) }, variant = DarwinButtonVariant.Destructive, size = DarwinButtonSize.Small)
    }
}

@GalleryExample("Dialog", "Default")
@Composable
fun DialogDefaultExample() {
    var showDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        DarwinButton(text = "Open Dialog", onClick = { showDialog = true }, variant = DarwinButtonVariant.Primary)
    }
    DarwinDialog(open = showDialog, onOpenChange = { showDialog = it }) {
        DarwinDialogContent(showCloseButton = true) {
            DarwinDialogHeader {
                DarwinDialogTitle("Confirm Action")
                DarwinDialogDescription("Are you sure you want to proceed? This action cannot be undone.")
            }
            DarwinDialogFooter {
                DarwinButton(text = "Cancel", onClick = { showDialog = false }, variant = DarwinButtonVariant.Ghost)
                DarwinButton(text = "Confirm", onClick = { showDialog = false }, variant = DarwinButtonVariant.Primary)
            }
        }
    }
}

@GalleryExample("Tooltip", "Default")
@Composable
fun TooltipDefaultExample() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        DarwinTooltip(text = "This is a tooltip!") { DarwinButton(text = "Hover me", onClick = {}, variant = DarwinButtonVariant.Outline) }
        DarwinTooltip(text = "Another tooltip with more info") { DarwinButton(text = "More info", onClick = {}, variant = DarwinButtonVariant.Secondary) }
    }
}

@GalleryExample("Popover", "Default")
@Composable
fun PopoverDefaultExample() {
    var popoverExpanded by remember { mutableStateOf(false) }
    DarwinPopover(expanded = popoverExpanded, onDismissRequest = { popoverExpanded = false }, trigger = { DarwinButton(text = "Toggle Popover", onClick = { popoverExpanded = !popoverExpanded }, variant = DarwinButtonVariant.Outline) }) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DarwinText("Popover Content", fontWeight = FontWeight.SemiBold, color = DarwinTheme.colors.textPrimary)
            DarwinText("This is a popover panel with rich content.", color = DarwinTheme.colors.textSecondary)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { DarwinButton(text = "Edit", onClick = { popoverExpanded = false }, variant = DarwinButtonVariant.Secondary, size = DarwinButtonSize.Small); DarwinButton(text = "Copy", onClick = { popoverExpanded = false }, variant = DarwinButtonVariant.Ghost, size = DarwinButtonSize.Small) }
        }
    }
}

@GalleryExample("DropdownMenu", "Default")
@Composable
fun DropdownMenuDefaultExample() {
    var dropdownExpanded by remember { mutableStateOf(false) }; var checkboxState by remember { mutableStateOf(false) }
    DarwinDropdownMenu(expanded = dropdownExpanded, onDismissRequest = { dropdownExpanded = false }, trigger = { DarwinButton(text = "Open Menu", onClick = { dropdownExpanded = !dropdownExpanded }, variant = DarwinButtonVariant.Outline) }) {
        DarwinDropdownMenuLabel(text = "Actions")
        DarwinDropdownMenuItem(onClick = { dropdownExpanded = false }, trailingContent = { DarwinDropdownMenuShortcut(text = "Cmd+N") }) { DarwinText("New File") }
        DarwinDropdownMenuItem(onClick = { dropdownExpanded = false }, trailingContent = { DarwinDropdownMenuShortcut(text = "Cmd+O") }) { DarwinText("Open") }
        DarwinDropdownMenuSeparator()
        DarwinDropdownMenuLabel(text = "Options")
        DarwinDropdownMenuCheckboxItem(checked = checkboxState, onCheckedChange = { checkboxState = it }) { DarwinText("Auto-save") }
        DarwinDropdownMenuSeparator()
        DarwinDropdownMenuItem(onClick = { dropdownExpanded = false }, enabled = false) { DarwinText("Disabled item") }
    }
}

@GalleryExample("Tabs", "Default")
@Composable
fun TabsDefaultExample() {
    var selectedTab by remember { mutableStateOf("tab1") }
    DarwinTabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) {
        DarwinTabsList {
            DarwinTabsTrigger(value = "tab1", selected = selectedTab == "tab1", onClick = { selectedTab = "tab1" }) { DarwinText("Account") }
            DarwinTabsTrigger(value = "tab2", selected = selectedTab == "tab2", onClick = { selectedTab = "tab2" }) { DarwinText("Settings") }
            DarwinTabsTrigger(value = "tab3", selected = selectedTab == "tab3", onClick = { selectedTab = "tab3" }) { DarwinText("Notifications") }
        }
        DarwinTabsContent(value = "tab1", selectedTab = selectedTab) { DarwinCard { DarwinCardContent { DarwinText("Account settings and profile information.", color = DarwinTheme.colors.textSecondary) } } }
        DarwinTabsContent(value = "tab2", selectedTab = selectedTab) { DarwinCard { DarwinCardContent { DarwinText("Application preferences and configuration.", color = DarwinTheme.colors.textSecondary) } } }
        DarwinTabsContent(value = "tab3", selectedTab = selectedTab) { DarwinCard { DarwinCardContent { DarwinText("Notification preferences and history.", color = DarwinTheme.colors.textSecondary) } } }
    }
}

@GalleryExample("Accordion", "Single Mode")
@Composable
fun AccordionSingleModeExample() {
    var expandedItem by remember { mutableStateOf<String?>("item1") }
    DarwinAccordion(type = DarwinAccordionType.Single) {
        DarwinAccordionItem(value = "item1", expanded = expandedItem == "item1", onToggle = { expandedItem = if (expandedItem == "item1") null else "item1" }, trigger = { DarwinText("What is Darwin UI?") }, content = { DarwinText("Darwin UI is a macOS-inspired design system for Compose Multiplatform, recreated from the React darwin-ui library.", color = DarwinTheme.colors.textSecondary) })
        DarwinAccordionItem(value = "item2", expanded = expandedItem == "item2", onToggle = { expandedItem = if (expandedItem == "item2") null else "item2" }, trigger = { DarwinText("Which platforms are supported?") }, content = { DarwinText("Android, iOS, Desktop (JVM), Web (JS), and WASM are all supported through Compose Multiplatform.", color = DarwinTheme.colors.textSecondary) })
        DarwinAccordionItem(value = "item3", expanded = expandedItem == "item3", onToggle = { expandedItem = if (expandedItem == "item3") null else "item3" }, trigger = { DarwinText("Is dark mode supported?") }, content = { DarwinText("Yes! Dark mode is the default theme, matching the original React library.", color = DarwinTheme.colors.textSecondary) })
    }
}

@GalleryExample("Sidebar", "Preview")
@Composable
fun SidebarPreviewExample() {
    var sidebarSelected by remember { mutableStateOf("dashboard") }
    DarwinCard(modifier = Modifier.fillMaxWidth(0.7f).height(350.dp)) {
        Row(modifier = Modifier.fillMaxSize()) {
            DarwinSidebar(width = 180.dp, header = { DarwinText("My App", fontWeight = FontWeight.Bold, color = DarwinTheme.colors.textPrimary) }, footer = { DarwinText("v1.0.0", style = DarwinTheme.typography.labelSmall, color = DarwinTheme.colors.textTertiary) }) {
                DarwinSidebarSection(title = "MAIN") { DarwinSidebarItem(label = "Home", selected = sidebarSelected == "home", onClick = { sidebarSelected = "home" }); DarwinSidebarItem(label = "Dashboard", selected = sidebarSelected == "dashboard", onClick = { sidebarSelected = "dashboard" }); DarwinSidebarItem(label = "Settings", selected = sidebarSelected == "settings", onClick = { sidebarSelected = "settings" }); DarwinSidebarItem(label = "Profile", selected = sidebarSelected == "profile", onClick = { sidebarSelected = "profile" }) }
            }
            Box(modifier = Modifier.weight(1f).fillMaxHeight().padding(16.dp)) { DarwinText(text = "Selected: $sidebarSelected", color = DarwinTheme.colors.textSecondary) }
        }
    }
}

@GalleryExample("Window", "Default")
@Composable
fun WindowDefaultExample() {
    DarwinWindow(title = "Terminal", onClose = {}, modifier = Modifier.fillMaxWidth(0.6f).height(200.dp)) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            DarwinText("$ npm install @pikoloo/darwin-ui", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.textSecondary)
            DarwinText("added 42 packages in 2.3s", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.textTertiary)
            DarwinText("$ npm run dev", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.textSecondary)
            DarwinText("Ready on http://localhost:3000", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.accent)
        }
    }
}

@GalleryExample("Window", "Glass")
@Composable
fun WindowGlassExample() {
    DarwinWindow(title = "Notes", glass = true, modifier = Modifier.fillMaxWidth(0.6f).height(160.dp)) {
        Box(modifier = Modifier.padding(12.dp)) { DarwinText("Glass morphism window with translucent background.", color = DarwinTheme.colors.textSecondary) }
    }
}

@GalleryExample("TopBar", "Default")
@Composable
fun TopBarDefaultExample() {
    DarwinCard(modifier = Modifier.fillMaxWidth(0.7f)) {
        Column {
            DarwinTopbar(title = "Dashboard", actions = { DarwinButton(text = "Settings", onClick = {}, variant = DarwinButtonVariant.Ghost, size = DarwinButtonSize.Small); DarwinButton(text = "Profile", onClick = {}, variant = DarwinButtonVariant.Ghost, size = DarwinButtonSize.Small) })
            Box(modifier = Modifier.fillMaxWidth().height(100.dp).padding(16.dp)) { DarwinText("Main content area", color = DarwinTheme.colors.textSecondary) }
        }
    }
}

@GalleryExample("TopBar", "Glass")
@Composable
fun TopBarGlassExample() {
    DarwinCard(modifier = Modifier.fillMaxWidth(0.7f)) {
        Column {
            DarwinTopbar(title = "Analytics", glass = true, actions = { DarwinButton(text = "Export", onClick = {}, variant = DarwinButtonVariant.Accent, size = DarwinButtonSize.Small) })
            Box(modifier = Modifier.fillMaxWidth().height(100.dp).padding(16.dp)) { DarwinText("Content with glass topbar", color = DarwinTheme.colors.textSecondary) }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("CloseButton", "Sizes")
@Composable
fun CloseButtonSizesExample() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { DarwinCloseButton(onClick = {}, size = 10.dp); DarwinText("10dp", color = DarwinTheme.colors.textTertiary, style = DarwinTheme.typography.bodySmall) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { DarwinCloseButton(onClick = {}, size = 12.dp); DarwinText("12dp (default)", color = DarwinTheme.colors.textTertiary, style = DarwinTheme.typography.bodySmall) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { DarwinCloseButton(onClick = {}, size = 16.dp); DarwinText("16dp", color = DarwinTheme.colors.textTertiary, style = DarwinTheme.typography.bodySmall) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { DarwinCloseButton(onClick = {}, size = 20.dp); DarwinText("20dp", color = DarwinTheme.colors.textTertiary, style = DarwinTheme.typography.bodySmall) }
    }
}

@GalleryExample("Reveal", "Reveal Once")
@Composable
fun RevealOnceExample() {
    var showReveal by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DarwinButton(text = if (showReveal) "Reset" else "Show Reveal", onClick = { showReveal = !showReveal })
        if (showReveal) { DarwinRevealOnce { DarwinCard { DarwinCardContent { DarwinText("This content revealed with a fade + slide animation!", color = DarwinTheme.colors.textSecondary) } } } }
    }
}

// =============================================================================
// Page composables — Header → Preview → Usage → Examples
// =============================================================================

@Composable
private fun ButtonPage() {
    GalleryPage("Button", "A beautiful, accessible button component with native macOS styling.") {
        PreviewContainer { ButtonPreview() }

        SectionHeader("Usage")
        CodeBlock("""DarwinButton(text = "Primary", onClick = {}, variant = DarwinButtonVariant.Primary)
DarwinButton(text = "Secondary", onClick = {}, variant = DarwinButtonVariant.Secondary)
DarwinButton(text = "Destructive", onClick = {}, variant = DarwinButtonVariant.Destructive)""")

        SectionHeader("Examples")
        ExampleCard(title = "Variants", description = "All available button variants", sourceCode = GallerySources.ButtonVariantsExample) { ButtonVariantsExample() }
        ExampleCard(title = "Sizes", description = "Small, default, and large button sizes", sourceCode = GallerySources.ButtonSizesExample) { ButtonSizesExample() }
        ExampleCard(title = "States", description = "Disabled, loading, and glass states", sourceCode = GallerySources.ButtonStatesExample) { ButtonStatesExample() }
    }
}

@Composable
private fun InputPage() {
    GalleryPage("Input", "A text input field with label, validation states, and password support.") {
        PreviewContainer { InputPreview() }

        SectionHeader("Usage")
        CodeBlock("""var text by remember { mutableStateOf("") }
DarwinTextField(
    value = text,
    onValueChange = { text = it },
    placeholder = "Enter text...",
    label = "Label",
)""")

        SectionHeader("Examples")
        ExampleCard(title = "With Label", description = "Input with label and helper text", sourceCode = GallerySources.InputWithLabelExample) { InputWithLabelExample() }
        ExampleCard(title = "Default", sourceCode = GallerySources.InputDefaultExample) { InputDefaultExample() }
        ExampleCard(title = "Error State", description = "Input with error validation", sourceCode = GallerySources.InputErrorExample) { InputErrorExample() }
        ExampleCard(title = "Success State", description = "Input with success validation", sourceCode = GallerySources.InputSuccessExample) { InputSuccessExample() }
        ExampleCard(title = "Password", description = "Password input with visibility toggle", sourceCode = GallerySources.InputPasswordExample) { InputPasswordExample() }
    }
}

@Composable
private fun SearchInputPage() {
    GalleryPage("Search Input", "A text input with a built-in search icon.") {
        SectionHeader("Usage")
        CodeBlock("""var query by remember { mutableStateOf("") }
DarwinSearchField(
    value = query,
    onValueChange = { query = it },
    placeholder = "Search...",
)""")

        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.SearchInputDefaultExample) { SearchInputDefaultExample() }
        ExampleCard(title = "With Label", sourceCode = GallerySources.SearchInputWithLabelExample) { SearchInputWithLabelExample() }
    }
}

@Composable
private fun TextAreaPage() {
    GalleryPage("Textarea", "A multi-line text input for longer form content.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.TextAreaDefaultExample) { TextAreaDefaultExample() }
        ExampleCard(title = "Error State", sourceCode = GallerySources.TextAreaErrorExample) { TextAreaErrorExample() }
    }
}

@Composable
private fun CheckboxPage() {
    GalleryPage("Checkbox", "A control that allows the user to toggle between checked and unchecked.") {
        PreviewContainer { CheckboxPreview() }

        SectionHeader("Examples")
        ExampleCard(title = "States", description = "Checked, unchecked, indeterminate, and disabled", sourceCode = GallerySources.CheckboxStatesExample) { CheckboxStatesExample() }
        ExampleCard(title = "Select All", description = "Parent checkbox with indeterminate state", sourceCode = GallerySources.CheckboxSelectAllExample) { CheckboxSelectAllExample() }
    }
}

@Composable
private fun SwitchPreview() {
    var s1 by remember { mutableStateOf(true) }
    var s2 by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HoverOffsetItem { DarwinSwitch(checked = s1, onCheckedChange = { s1 = it }, label = "Enable notifications") }
        HoverOffsetItem { DarwinSwitch(checked = s2, onCheckedChange = { s2 = it }, label = "Dark mode") }
        HoverOffsetItem { DarwinSwitch(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false) }
    }
}

/** Wraps content with a whileHover={{ x: 4 }} effect matching React's Framer Motion. */
@Composable
private fun HoverOffsetItem(content: @Composable () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val offsetX by animateDpAsState(
        targetValue = if (isHovered) 4.dp else 0.dp,
        animationSpec = tween(durationMillis = 200, easing = androidx.compose.animation.core.FastOutSlowInEasing),
        label = "hoverOffset",
    )
    Box(
        modifier = Modifier
            .offset(x = offsetX)
            .hoverable(interactionSource),
    ) {
        content()
    }
}

@Composable
private fun SwitchPage() {
    GalleryPage("Switch", "A control that toggles between on and off states.") {
        PreviewContainer { SwitchPreview() }

        SectionHeader("Examples")
        ExampleCard(title = "States", sourceCode = GallerySources.SwitchStatesExample) { SwitchStatesExample() }
    }
}

@Composable
private fun SelectPage() {
    GalleryPage("Select", "Displays a list of options for the user to pick from.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.SelectDefaultExample) { SelectDefaultExample() }
        ExampleCard(title = "Pre-selected", sourceCode = GallerySources.SelectPreselectedExample) { SelectPreselectedExample() }
    }
}

@Composable
private fun MultiSelectPage() {
    GalleryPage("Multi Select", "Allows selecting multiple options from a list.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.MultiSelectDefaultExample) { MultiSelectDefaultExample() }
        ExampleCard(title = "Pre-selected", sourceCode = GallerySources.MultiSelectPreselectedExample) { MultiSelectPreselectedExample() }
    }
}

@Composable
private fun SliderPage() {
    GalleryPage("Slider", "An input where the user selects a value from within a given range.") {
        SectionHeader("Examples")
        ExampleCard(title = "Volume", sourceCode = GallerySources.SliderVolumeExample) { SliderVolumeExample() }
        ExampleCard(title = "With Value Display", sourceCode = GallerySources.SliderWithValueExample) { SliderWithValueExample() }
    }
}

@Composable
private fun UploadPage() {
    GalleryPage("Upload", "A file upload component with drag and drop support.") {
        SectionHeader("Usage")
        CodeBlock("""DarwinUpload(
    files = files,
    onPickFiles = { /* open file picker */ },
    onRemoveFile = { index -> files.removeAt(index) },
    onClearAll = { files.clear() },
    onSetCover = { index -> /* move to front */ },
    onSwap = { i, j -> /* swap positions */ },
    maxFiles = 4,
    label = "Upload product images",
    variant = DarwinUploadVariant.Default,
)""")

        SectionHeader("Examples")
        ExampleCard(title = "Default", description = "Full grid with image cards, cover selection, and reordering", sourceCode = GallerySources.UploadDefaultExample) { UploadDefaultExample() }
        ExampleCard(title = "Compact", description = "Smaller dropzone with 2-column grid", sourceCode = GallerySources.UploadCompactExample) { UploadCompactExample() }
        ExampleCard(title = "Inline", description = "Single-row inline upload bar", sourceCode = GallerySources.UploadInlineExample) { UploadInlineExample() }
    }
}

@Composable
private fun BadgePage() {
    GalleryPage("Badge", "Displays a badge or a component that looks like a badge.") {
        PreviewContainer { BadgePreview() }

        SectionHeader("Examples")
        ExampleCard(title = "Variants", description = "Core badge variants", sourceCode = GallerySources.BadgeVariantsExample) { BadgeVariantsExample() }
        ExampleCard(title = "Semantic Variants", description = "Status and category badges", sourceCode = GallerySources.BadgeSemanticVariantsExample) { BadgeSemanticVariantsExample() }
    }
}

@Composable
private fun AvatarPage() {
    GalleryPage("Avatar", "An image element with a fallback for representing the user.") {
        SectionHeader("Examples")
        ExampleCard(title = "Sizes", sourceCode = GallerySources.AvatarSizesExample) { AvatarSizesExample() }
        ExampleCard(title = "Avatar Group", sourceCode = GallerySources.AvatarGroupExample) { AvatarGroupExample() }
    }
}

@Composable
private fun CardPage() {
    GalleryPage("Card", "Displays a card with header, content, and footer.") {
        PreviewContainer { CardDefaultExample() }

        SectionHeader("Usage")
        CodeBlock("""DarwinCard {
    DarwinCardHeader {
        DarwinCardTitle { DarwinText("Title") }
        DarwinCardDescription { DarwinText("Description") }
    }
    DarwinCardContent { DarwinText("Content") }
    DarwinCardFooter { DarwinButton(text = "Action", onClick = {}) }
}""")

        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.CardDefaultExample) { CardDefaultExample() }
        ExampleCard(title = "Glass", description = "Card with glass morphism effect", sourceCode = GallerySources.CardGlassExample) { CardGlassExample() }
    }
}

@Composable
private fun TablePage() {
    GalleryPage("Table", "A responsive table component for displaying tabular data.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.TableDefaultExample) { TableDefaultExample() }
    }
}

@Composable
private fun ProgressPage() {
    GalleryPage("Progress", "Displays an indicator showing the completion progress of a task.") {
        PreviewContainer { ProgressPreview() }

        SectionHeader("Usage")
        CodeBlock("""DarwinLinearProgress(value = 65f, max = 100f, showValue = true)
DarwinCircularProgress(value = 75f, max = 100f, showValue = true)""")

        SectionHeader("Examples")
        ExampleCard(title = "Linear - Default", sourceCode = GallerySources.ProgressLinearDefaultExample) { ProgressLinearDefaultExample() }
        ExampleCard(title = "Linear - Success", sourceCode = GallerySources.ProgressLinearSuccessExample) { ProgressLinearSuccessExample() }
        ExampleCard(title = "Linear - Gradient", sourceCode = GallerySources.ProgressLinearGradientExample) { ProgressLinearGradientExample() }
        ExampleCard(title = "Linear - Indeterminate", sourceCode = GallerySources.ProgressLinearIndeterminateExample) { ProgressLinearIndeterminateExample() }
        ExampleCard(title = "Circular", sourceCode = GallerySources.ProgressCircularExample) { ProgressCircularExample() }
    }
}

@Composable
private fun SkeletonPage() {
    GalleryPage("Skeleton", "Used to show a placeholder while content is loading.") {
        SectionHeader("Usage")
        CodeBlock("""DarwinSkeleton(modifier = Modifier.fillMaxWidth().height(16.dp))
DarwinSkeletonCircle(size = 48.dp)
DarwinSkeleton(modifier = Modifier.height(96.dp), glass = true)""")

        SectionHeader("Examples")
        ExampleCard(title = "Card Skeleton", description = "Avatar, text lines, content block, and button placeholders", sourceCode = GallerySources.SkeletonCardExample) { SkeletonCardExample() }
        ExampleCard(title = "Glass Effect", description = "Skeleton with frosted glass morphism", sourceCode = GallerySources.SkeletonGlassExample) { SkeletonGlassExample() }
    }
}

@Composable
private fun AlertPage() {
    GalleryPage("Alert", "Displays a callout for user attention.") {
        PreviewContainer { AlertPreview() }

        SectionHeader("Examples")
        ExampleCard(title = "Info", sourceCode = GallerySources.AlertInfoExample) { AlertInfoExample() }
        ExampleCard(title = "Success", sourceCode = GallerySources.AlertSuccessExample) { AlertSuccessExample() }
        ExampleCard(title = "Warning", sourceCode = GallerySources.AlertWarningExample) { AlertWarningExample() }
        ExampleCard(title = "Error", sourceCode = GallerySources.AlertErrorExample) { AlertErrorExample() }
        ExampleCard(title = "Alert Dialog", description = "Modal confirmation dialog", sourceCode = GallerySources.AlertDialogExample) { AlertDialogExample() }
    }
}

@Composable
private fun ToastPage(toastState: DarwinToastState) {
    GalleryPage("Toast", "A succinct message that is displayed temporarily.") {
        SectionHeader("Examples")
        ExampleCard(title = "Click to Show", description = "Trigger different toast types", sourceCode = GallerySources.ToastClickToShowExample) { ToastClickToShowExample(toastState) }
    }
}

@Composable
private fun DialogPage() {
    GalleryPage("Dialog", "A modal dialog that interrupts the user with important content.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.DialogDefaultExample) { DialogDefaultExample() }
    }
}

@Composable
private fun TooltipPage() {
    GalleryPage("Tooltip", "A popup that displays information related to an element on hover.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.TooltipDefaultExample) { TooltipDefaultExample() }
    }
}

@Composable
private fun PopoverPage() {
    GalleryPage("Popover", "Displays rich content in a portal, triggered by a button.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.PopoverDefaultExample) { PopoverDefaultExample() }
    }
}

@Composable
private fun DropdownMenuPage() {
    GalleryPage("Dropdown Menu", "Displays a menu to the user with a list of actions.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.DropdownMenuDefaultExample) { DropdownMenuDefaultExample() }
    }
}

@Composable
private fun TabsPage() {
    GalleryPage("Tabs", "A set of layered sections of content, known as tab panels.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.TabsDefaultExample) { TabsDefaultExample() }
    }
}

@Composable
private fun AccordionPage() {
    GalleryPage("Accordion", "A vertically stacked set of interactive headings that reveal content.") {
        SectionHeader("Examples")
        ExampleCard(title = "Single Mode", sourceCode = GallerySources.AccordionSingleModeExample) { AccordionSingleModeExample() }
    }
}

@Composable
private fun SidebarPage() {
    GalleryPage("Sidebar", "A navigation sidebar with sections, items, and optional header/footer.") {
        SectionHeader("Examples")
        ExampleCard(title = "Preview", sourceCode = GallerySources.SidebarPreviewExample) { SidebarPreviewExample() }
    }
}

@Composable
private fun WindowPage() {
    GalleryPage("Window", "A macOS-style window container with traffic light buttons.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.WindowDefaultExample) { WindowDefaultExample() }
        ExampleCard(title = "Glass", description = "Window with glass morphism effect", sourceCode = GallerySources.WindowGlassExample) { WindowGlassExample() }
    }
}

@Composable
private fun TopBarPage() {
    GalleryPage("Top Bar", "A macOS-style top navigation bar with title and actions.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.TopBarDefaultExample) { TopBarDefaultExample() }
        ExampleCard(title = "Glass", description = "Top bar with glass morphism effect", sourceCode = GallerySources.TopBarGlassExample) { TopBarGlassExample() }
    }
}

@Composable
private fun CloseButtonPage() {
    GalleryPage("Close Button", "A macOS-style red close button with hover X icon.") {
        SectionHeader("Examples")
        ExampleCard(title = "Sizes", sourceCode = GallerySources.CloseButtonSizesExample) { CloseButtonSizesExample() }
    }
}

@Composable
private fun RevealPage() {
    GalleryPage("Reveal", "Animate content into view with a fade and slide effect.") {
        SectionHeader("Examples")
        ExampleCard(title = "Reveal Once", sourceCode = GallerySources.RevealOnceExample) { RevealOnceExample() }
    }
}

// --- Date Select ---

@GalleryExample("DateSelect", "Default")
@Composable
fun DateSelectDefaultExample() {
    var selectedConfig by remember { mutableStateOf<DateConfig?>(null) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth()) {
        DarwinDateSelect(
            label = "Select a date",
            onChange = { selectedConfig = it },
        )
        if (selectedConfig != null) {
            DarwinText(
                text = "Selected: ${selectedConfig!!.selectedDate ?: "—"}",
                style = DarwinTheme.typography.bodySmall,
                color = DarwinTheme.colors.textSecondary,
            )
        }
    }
}

@Composable
private fun DateSelectPage() {
    GalleryPage("Date Select", "A sophisticated date/time selector with single and recurring event support.") {
        SectionHeader("Usage")
        CodeBlock("""DarwinDateSelect(
    label = "Select a date",
    onChange = { config -> /* handle config */ },
)""")

        SectionHeader("Examples")
        ExampleCard(title = "Default", description = "Date select with label and change callback", sourceCode = GallerySources.DateSelectDefaultExample) { DateSelectDefaultExample() }
    }
}
