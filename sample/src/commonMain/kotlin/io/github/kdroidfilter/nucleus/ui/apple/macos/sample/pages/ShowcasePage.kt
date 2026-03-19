package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Accordion
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.AccordionItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.AccordionType
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Avatar
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.AvatarData
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.AvatarGroup
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Badge
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.BadgeVariant
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Card
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CardContent
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CardDescription
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CardHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CardTitle
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CheckBox
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CircularSlider
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ColorWell
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DatePicker
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.LinearProgress
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PageControl
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PushButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PushButtonStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.RadioButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SegmentedControl
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Skeleton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SkeletonCircle
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Slider
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Spinner
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Stepper
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Switcher
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Table
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TableBody
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TableCell
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TableHead
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TableHeaderCell
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TableRow
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Tabs
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TabsContent
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TabsList
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TabsTrigger
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TextField
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Tooltip
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideChevronLeft
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideChevronRight
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideHeart
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSearch
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSettings
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideShare2
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideStar
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ShowcasePage() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        // -- Hero --
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "It's not SwiftUI.",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MacosTheme.colorScheme.textPrimary,
            )
            Text(
                text = "It's Compose.",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MacosTheme.colorScheme.accent,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "A pixel-perfect macOS design system built entirely with Compose Multiplatform.\nNo SwiftUI. No UIKit. Pure Kotlin.",
                style = MacosTheme.typography.title3,
                color = MacosTheme.colorScheme.textSecondary,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Badge(variant = BadgeVariant.Success) { Text("macOS") }
                Badge(variant = BadgeVariant.Info) { Text("Kotlin") }
                Badge(variant = BadgeVariant.Published) { Text("Compose") }
                Badge(variant = BadgeVariant.Warning) { Text("Multiplatform") }
                Badge(variant = BadgeVariant.Default) { Text("50+ Components") }
            }
        }

        // -- Responsive grid --
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val isWide = maxWidth > 700.dp
            if (isWide) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    GridRow {
                        ControlsCard(Modifier.weight(1f).fillMaxHeight())
                        DataInputCard(Modifier.weight(1f).fillMaxHeight())
                    }
                    GridRow {
                        MediaPlayerCard(Modifier.weight(1f).fillMaxHeight())
                        DataTableCard(Modifier.weight(1f).fillMaxHeight())
                    }
                    GridRow {
                        FaqCard(Modifier.weight(1f).fillMaxHeight())
                        StatusCard(Modifier.weight(1f).fillMaxHeight())
                    }
                    GridRow {
                        TabsCard(Modifier.weight(1f).fillMaxHeight())
                        SkeletonCard(Modifier.weight(1f).fillMaxHeight())
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    ControlsCard(Modifier.fillMaxWidth())
                    DataInputCard(Modifier.fillMaxWidth())
                    MediaPlayerCard(Modifier.fillMaxWidth())
                    DataTableCard(Modifier.fillMaxWidth())
                    FaqCard(Modifier.fillMaxWidth())
                    StatusCard(Modifier.fillMaxWidth())
                    TabsCard(Modifier.fillMaxWidth())
                    SkeletonCard(Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun GridRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        content = content,
    )
}

// =============================================================================
// Card 1 — Controls: buttons, switches, checkboxes, radio, segmented, stepper
// =============================================================================

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ControlsCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        CardHeader {
            CardTitle { Text("Controls") }
            CardDescription { Text("Buttons, toggles, selections") }
        }
        CardContent {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                // Buttons row
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    SectionLabel("Push Buttons")
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        PushButton(text = "Default", onClick = {}, style = PushButtonStyle.Default)
                        PushButton(text = "Accent", onClick = {}, style = PushButtonStyle.Accent)
                        PushButton(text = "Secondary", onClick = {}, style = PushButtonStyle.Secondary)
                        PushButton(text = "Destructive", onClick = {}, style = PushButtonStyle.Destructive)
                        PushButton(text = "Neutral", onClick = {}, style = PushButtonStyle.Neutral)
                        PushButton(text = "Borderless", onClick = {}, style = PushButtonStyle.Borderless)
                    }
                }

                // Control sizes
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    SectionLabel("Control Sizes")
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ControlSize(ControlSize.Mini) { PushButton(text = "Mini", onClick = {}) }
                        ControlSize(ControlSize.Small) { PushButton(text = "Small", onClick = {}) }
                        PushButton(text = "Regular", onClick = {})
                        ControlSize(ControlSize.Large) { PushButton(text = "Large", onClick = {}) }
                    }
                }

                // Switches + Checkboxes side by side
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        SectionLabel("Switches")
                        var s1 by remember { mutableStateOf(true) }
                        var s2 by remember { mutableStateOf(false) }
                        var s3 by remember { mutableStateOf(true) }
                        Switcher(checked = s1, onCheckedChange = { s1 = it }, label = "Wi-Fi")
                        Switcher(checked = s2, onCheckedChange = { s2 = it }, label = "Bluetooth")
                        Switcher(checked = s3, onCheckedChange = { s3 = it }, label = "AirDrop")
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        SectionLabel("Checkboxes")
                        var c1 by remember { mutableStateOf(true) }
                        var c2 by remember { mutableStateOf(false) }
                        var c3 by remember { mutableStateOf(true) }
                        CheckBox(checked = c1, onCheckedChange = { c1 = it }, label = "Show hidden files")
                        CheckBox(checked = c2, onCheckedChange = { c2 = it }, label = "File extensions")
                        CheckBox(checked = c3, onCheckedChange = { c3 = it }, label = "Status bar")
                    }
                }

                // Radio + Segmented side by side
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        SectionLabel("Radio Buttons")
                        var radio by remember { mutableStateOf("light") }
                        RadioButton(selected = radio == "light", onClick = { radio = "light" }, label = "Light")
                        RadioButton(selected = radio == "dark", onClick = { radio = "dark" }, label = "Dark")
                        RadioButton(selected = radio == "auto", onClick = { radio = "auto" }, label = "Automatic")
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        SectionLabel("Segmented & Stepper")
                        var seg by remember { mutableStateOf(0) }
                        SegmentedControl(
                            options = listOf("Day", "Week", "Month"),
                            selectedIndex = seg,
                            onSelectedIndexChange = { seg = it },
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        var count by remember { mutableIntStateOf(3) }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                "Quantity: $count",
                                style = MacosTheme.typography.subheadline,
                                color = MacosTheme.colorScheme.textPrimary,
                            )
                            Stepper(
                                onIncrement = { count++ },
                                onDecrement = { if (count > 0) count-- },
                            )
                        }
                    }
                }
            }
        }
    }
}

// =============================================================================
// Card 2 — Data Input: text fields, date picker, sliders, circular sliders, color wells
// =============================================================================

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DataInputCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        CardHeader {
            CardTitle { Text("Data Input") }
            CardDescription { Text("Fields, pickers, sliders") }
        }
        CardContent {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                // Text inputs
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SectionLabel("Text Fields")
                    var name by remember { mutableStateOf("") }
                    var email by remember { mutableStateOf("") }
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        placeholder = { Text("Full name") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Email address") },
                        leadingIcon = { Icon(LucideSearch, modifier = Modifier.size(14.dp)) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }

                // Date picker
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SectionLabel("Date Picker")
                    var date by remember { mutableStateOf(LocalDate(2026, 3, 19)) }
                    DatePicker(value = date, onValueChange = { date = it })
                }

                // Sliders
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SectionLabel("Sliders")
                    var vol by remember { mutableStateOf(65f) }
                    var bright by remember { mutableStateOf(80f) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Volume", style = MacosTheme.typography.caption1, color = MacosTheme.colorScheme.textSecondary)
                        Slider(value = vol, onValueChange = { vol = it }, valueRange = 0f..100f, modifier = Modifier.weight(1f))
                        Text("${vol.toInt()}%", style = MacosTheme.typography.caption1, color = MacosTheme.colorScheme.accent)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text("Brightness", style = MacosTheme.typography.caption1, color = MacosTheme.colorScheme.textSecondary)
                        Slider(value = bright, onValueChange = { bright = it }, valueRange = 0f..100f, modifier = Modifier.weight(1f))
                        Text("${bright.toInt()}%", style = MacosTheme.typography.caption1, color = MacosTheme.colorScheme.accent)
                    }
                }

                // Circular sliders + Color wells
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        SectionLabel("Circular Sliders")
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            var c1 by remember { mutableStateOf(0.3f) }
                            var c2 by remember { mutableStateOf(0.7f) }
                            var c3 by remember { mutableStateOf(0.5f) }
                            ControlSize(ControlSize.Small) {
                                CircularSlider(value = c1, onValueChange = { c1 = it })
                            }
                            CircularSlider(value = c2, onValueChange = { c2 = it })
                            ControlSize(ControlSize.Large) {
                                CircularSlider(value = c3, onValueChange = { c3 = it })
                            }
                        }
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        SectionLabel("Color Wells")
                        var selectedColor by remember { mutableStateOf(0) }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            ColorWell(color = Color(0xFF007AFF), onClick = { selectedColor = 0 }, selected = selectedColor == 0)
                            ColorWell(color = Color(0xFF34C759), onClick = { selectedColor = 1 }, selected = selectedColor == 1)
                            ColorWell(color = Color(0xFFFF9500), onClick = { selectedColor = 2 }, selected = selectedColor == 2)
                            ColorWell(color = Color(0xFFFF3B30), onClick = { selectedColor = 3 }, selected = selectedColor == 3)
                            ColorWell(color = Color(0xFFAF52DE), onClick = { selectedColor = 4 }, selected = selectedColor == 4)
                        }
                    }
                }
            }
        }
    }
}

// =============================================================================
// Card 3 — Media Player (slider, buttons, icons, badges, avatar, progress)
// =============================================================================

@Composable
private fun MediaPlayerCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        CardHeader {
            CardTitle { Text("Now Playing") }
            CardDescription { Text("Media controls & progress") }
        }
        CardContent {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Album art
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MacosTheme.colorScheme.accent.copy(alpha = 0.10f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Avatar(name = "Kotlin Nocturne", size = 56.dp)
                        Column {
                            Text(
                                "Kotlin Nocturne",
                                style = MacosTheme.typography.headline,
                                fontWeight = FontWeight.SemiBold,
                                color = MacosTheme.colorScheme.textPrimary,
                            )
                            Text(
                                "The Compose Ensemble",
                                style = MacosTheme.typography.subheadline,
                                color = MacosTheme.colorScheme.textSecondary,
                            )
                            Badge(variant = BadgeVariant.Info) { Text("Lossless") }
                        }
                    }
                }

                // Progress slider
                var progress by remember { mutableStateOf(35f) }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Slider(value = progress, onValueChange = { progress = it }, valueRange = 0f..100f)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("1:23", style = MacosTheme.typography.caption2, color = MacosTheme.colorScheme.textTertiary)
                        Text("3:45", style = MacosTheme.typography.caption2, color = MacosTheme.colorScheme.textTertiary)
                    }
                }

                // Playback controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Tooltip(text = "Favorite") { PushButton(icon = LucideHeart, onClick = {}, style = PushButtonStyle.BorderlessBezel) }
                    PushButton(icon = LucideChevronLeft, onClick = {}, style = PushButtonStyle.Secondary)
                    PushButton(text = "Play", onClick = {}, style = PushButtonStyle.Accent)
                    PushButton(icon = LucideChevronRight, onClick = {}, style = PushButtonStyle.Secondary)
                    Tooltip(text = "Share") { PushButton(icon = LucideShare2, onClick = {}, style = PushButtonStyle.BorderlessBezel) }
                }

                // Volume
                var volume by remember { mutableStateOf(72f) }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Vol", style = MacosTheme.typography.caption2, color = MacosTheme.colorScheme.textTertiary)
                    Slider(value = volume, onValueChange = { volume = it }, valueRange = 0f..100f, modifier = Modifier.weight(1f))
                }

                // Page control (track indicator)
                var track by remember { mutableStateOf(2) }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    PageControl(pageCount = 8, currentPage = track, onPageSelected = { track = it })
                }
            }
        }
    }
}

// =============================================================================
// Card 4 — Data Table with badges
// =============================================================================

@Composable
private fun DataTableCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        CardHeader {
            CardTitle { Text("Data Table") }
            CardDescription { Text("Structured data with inline status") }
        }
        CardContent {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Table {
                    TableHead {
                        TableRow {
                            TableHeaderCell { Text("Component") }
                            TableHeaderCell { Text("Category") }
                            TableHeaderCell(weight = 0.6f) { Text("Status") }
                        }
                    }
                    TableBody(scrollable = false) {
                        TableRow {
                            TableCell { Text("PushButton") }
                            TableCell { Text("Controls") }
                            TableCell(weight = 0.6f) { Badge(variant = BadgeVariant.Success) { Text("Stable") } }
                        }
                        TableRow {
                            TableCell { Text("DatePicker") }
                            TableCell { Text("Input") }
                            TableCell(weight = 0.6f) { Badge(variant = BadgeVariant.Success) { Text("Stable") } }
                        }
                        TableRow {
                            TableCell { Text("CircularSlider") }
                            TableCell { Text("Input") }
                            TableCell(weight = 0.6f) { Badge(variant = BadgeVariant.Published) { Text("New") } }
                        }
                        TableRow {
                            TableCell { Text("ColorWell") }
                            TableCell { Text("Picker") }
                            TableCell(weight = 0.6f) { Badge(variant = BadgeVariant.Warning) { Text("Beta") } }
                        }
                        TableRow {
                            TableCell { Text("Scaffold") }
                            TableCell { Text("Layout") }
                            TableCell(weight = 0.6f) { Badge(variant = BadgeVariant.Success) { Text("Stable") } }
                        }
                    }
                }

                // Avatar group + spinner
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AvatarGroup(
                        avatars = listOf(
                            AvatarData(name = "Elie G"),
                            AvatarData(name = "Alice M"),
                            AvatarData(name = "Bob C"),
                            AvatarData(name = "Carol D"),
                        ),
                        maxDisplay = 3,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spinner()
                        Text("Syncing...", style = MacosTheme.typography.caption1, color = MacosTheme.colorScheme.textTertiary)
                    }
                }
            }
        }
    }
}

// =============================================================================
// Card 5 — FAQ Accordion
// =============================================================================

@Composable
private fun FaqCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        CardHeader {
            CardTitle { Text("FAQ") }
            CardDescription { Text("Accordion component") }
        }
        CardContent {
            var expandedItem by remember { mutableStateOf<String?>("q1") }
            Accordion(type = AccordionType.Single) {
                AccordionItem(
                    value = "q1",
                    expanded = expandedItem == "q1",
                    onToggle = { expandedItem = if (expandedItem == "q1") null else "q1" },
                    trigger = { Text("Is this really not SwiftUI?") },
                    content = {
                        Text(
                            "100% Compose Multiplatform. Every pixel is drawn by Compose — no UIKit, no SwiftUI, no platform views.",
                            color = MacosTheme.colorScheme.textSecondary,
                        )
                    },
                )
                AccordionItem(
                    value = "q2",
                    expanded = expandedItem == "q2",
                    onToggle = { expandedItem = if (expandedItem == "q2") null else "q2" },
                    trigger = { Text("Which platforms are supported?") },
                    content = {
                        Text(
                            "Android, iOS, Desktop JVM, Web JS, and WASM — all from a single Kotlin codebase.",
                            color = MacosTheme.colorScheme.textSecondary,
                        )
                    },
                )
                AccordionItem(
                    value = "q3",
                    expanded = expandedItem == "q3",
                    onToggle = { expandedItem = if (expandedItem == "q3") null else "q3" },
                    trigger = { Text("Does it support dark mode?") },
                    content = {
                        Text(
                            "Full light and dark mode support with smooth transitions and system accent color integration.",
                            color = MacosTheme.colorScheme.textSecondary,
                        )
                    },
                )
                AccordionItem(
                    value = "q4",
                    expanded = expandedItem == "q4",
                    onToggle = { expandedItem = if (expandedItem == "q4") null else "q4" },
                    trigger = { Text("How many components?") },
                    content = {
                        Text(
                            "50+ components: buttons, inputs, tables, sliders, dialogs, toasts, tooltips, sidebars, and more.",
                            color = MacosTheme.colorScheme.textSecondary,
                        )
                    },
                )
            }
        }
    }
}

// =============================================================================
// Card 6 — Status: progress bars, badges, avatars
// =============================================================================

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StatusCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        CardHeader {
            CardTitle { Text("Project Status") }
            CardDescription { Text("Progress, badges, indicators") }
        }
        CardContent {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Progress bars
                ProgressRow("Components", 52f, 60f, BadgeVariant.Success)
                ProgressRow("Tests", 38f, 60f, BadgeVariant.Warning)
                ProgressRow("Docs", 20f, 60f, BadgeVariant.Info)

                // Indeterminate
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    SectionLabel("Indeterminate")
                    LinearProgress(indeterminate = true)
                }

                // All badge variants
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    SectionLabel("Badge Variants")
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Badge(variant = BadgeVariant.Default) { Text("Default") }
                        Badge(variant = BadgeVariant.Secondary) { Text("Secondary") }
                        Badge(variant = BadgeVariant.Outline) { Text("Outline") }
                        Badge(variant = BadgeVariant.Success) { Text("Success") }
                        Badge(variant = BadgeVariant.Warning) { Text("Warning") }
                        Badge(variant = BadgeVariant.Destructive) { Text("Error") }
                        Badge(variant = BadgeVariant.Info) { Text("Info") }
                        Badge(variant = BadgeVariant.Published) { Text("Published") }
                        Badge(variant = BadgeVariant.Draft) { Text("Draft") }
                    }
                }

                // Release checklist
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    SectionLabel("Checklist")
                    var c1 by remember { mutableStateOf(true) }
                    var c2 by remember { mutableStateOf(true) }
                    var c3 by remember { mutableStateOf(false) }
                    CheckBox(checked = c1, onCheckedChange = { c1 = it }, label = "Code review passed")
                    CheckBox(checked = c2, onCheckedChange = { c2 = it }, label = "CI pipeline green")
                    CheckBox(checked = c3, onCheckedChange = { c3 = it }, label = "Release notes written")
                }
            }
        }
    }
}

@Composable
private fun ProgressRow(label: String, value: Float, max: Float, variant: BadgeVariant) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(label, style = MacosTheme.typography.subheadline, color = MacosTheme.colorScheme.textPrimary)
            Badge(variant = variant) { Text("${value.toInt()}/${max.toInt()}") }
        }
        LinearProgress(value = value, max = max)
    }
}

// =============================================================================
// Card 7 — Tabs with content
// =============================================================================

@Composable
private fun TabsCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        CardHeader {
            CardTitle { Text("Tabs") }
            CardDescription { Text("Tab navigation with content panels") }
        }
        CardContent {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                var selectedTab by remember { mutableStateOf("search") }
                Tabs(selectedTab = selectedTab, onTabSelected = { selectedTab = it }) {
                    TabsList {
                        TabsTrigger(value = "search", icon = { Icon(LucideSearch) }) { Text("Search") }
                        TabsTrigger(value = "favorites", icon = { Icon(LucideStar) }) { Text("Favorites") }
                        TabsTrigger(value = "settings", icon = { Icon(LucideSettings) }) { Text("Settings") }
                    }
                    TabsContent(value = "search") {
                        Column(
                            modifier = Modifier.padding(top = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            var query by remember { mutableStateOf("") }
                            TextField(
                                value = query,
                                onValueChange = { query = it },
                                placeholder = { Text("Search components...") },
                                leadingIcon = { Icon(LucideSearch, modifier = Modifier.size(14.dp)) },
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Text(
                                "Type to search across all 50+ components.",
                                style = MacosTheme.typography.caption1,
                                color = MacosTheme.colorScheme.textSecondary,
                            )
                        }
                    }
                    TabsContent(value = "favorites") {
                        Column(
                            modifier = Modifier.padding(top = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Badge(variant = BadgeVariant.Published) { Text("Button") }
                                Badge(variant = BadgeVariant.Published) { Text("Slider") }
                                Badge(variant = BadgeVariant.Published) { Text("Table") }
                                Badge(variant = BadgeVariant.Published) { Text("Tabs") }
                            }
                            Text(
                                "Your starred components appear here.",
                                style = MacosTheme.typography.caption1,
                                color = MacosTheme.colorScheme.textSecondary,
                            )
                        }
                    }
                    TabsContent(value = "settings") {
                        Column(
                            modifier = Modifier.padding(top = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            var compact by remember { mutableStateOf(false) }
                            Switcher(checked = compact, onCheckedChange = { compact = it }, label = "Compact mode")
                            var seg by remember { mutableStateOf(1) }
                            SegmentedControl(
                                options = listOf("Light", "Dark", "System"),
                                selectedIndex = seg,
                                onSelectedIndexChange = { seg = it },
                            )
                        }
                    }
                }
            }
        }
    }
}

// =============================================================================
// Card 8 — Skeleton loading states
// =============================================================================

@Composable
private fun SkeletonCard(modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        CardHeader {
            CardTitle { Text("Skeleton") }
            CardDescription { Text("Loading placeholders") }
        }
        CardContent {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                // Profile skeleton
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SectionLabel("Profile Loading")
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        SkeletonCircle(size = 48.dp)
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Skeleton(modifier = Modifier.fillMaxWidth(0.6f).height(16.dp))
                            Skeleton(modifier = Modifier.fillMaxWidth(0.4f).height(12.dp))
                        }
                    }
                }

                // Card skeleton
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    SectionLabel("Content Loading")
                    Skeleton(modifier = Modifier.fillMaxWidth().height(80.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Skeleton(modifier = Modifier.width(80.dp).height(32.dp), shape = MacosTheme.shapes.small)
                        Skeleton(modifier = Modifier.width(80.dp).height(32.dp), shape = MacosTheme.shapes.small)
                    }
                }

                // List skeleton
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SectionLabel("List Loading")
                    repeat(3) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            SkeletonCircle(size = 32.dp)
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                Skeleton(modifier = Modifier.fillMaxWidth(0.7f).height(14.dp))
                                Skeleton(modifier = Modifier.fillMaxWidth(0.5f).height(10.dp))
                            }
                        }
                    }
                }

                // Actual vs skeleton comparison
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Real
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        SectionLabel("Loaded")
                        Avatar(name = "Alice", size = 40.dp)
                        Text("Alice M.", style = MacosTheme.typography.caption1, color = MacosTheme.colorScheme.textPrimary)
                        Badge(variant = BadgeVariant.Success) { Text("Active") }
                    }
                    // Skeleton
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        SectionLabel("Loading")
                        SkeletonCircle(size = 40.dp)
                        Skeleton(modifier = Modifier.width(60.dp).height(14.dp))
                        Skeleton(modifier = Modifier.width(50.dp).height(20.dp), shape = MacosTheme.shapes.extraLarge)
                    }
                }
            }
        }
    }
}

// =============================================================================
// Helpers
// =============================================================================

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MacosTheme.typography.caption1,
        fontWeight = FontWeight.SemiBold,
        color = MacosTheme.colorScheme.textSecondary,
    )
}
