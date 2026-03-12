package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.CheckBox
import io.github.kdroidfilter.darwinui.components.GroupBox
import io.github.kdroidfilter.darwinui.components.Slider
import io.github.kdroidfilter.darwinui.components.Switch
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideMoon
import io.github.kdroidfilter.darwinui.icons.LucideSun
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
private fun RadioButton(selected: Boolean, onClick: () -> Unit) {
    val accent = DarwinTheme.colorScheme.accent
    Box(
        modifier = Modifier
            .size(18.dp)
            .border(1.5.dp, if (selected) accent else Color.Gray.copy(alpha = 0.5f), CircleShape)
            .background(if (selected) accent else Color.Transparent, CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        if (selected) {
            Box(modifier = Modifier.size(7.dp).background(Color.White, CircleShape))
        }
    }
}

@Composable
private fun GroupBoxPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GroupBox(label = "Appearance") {
            var darkMode by remember { mutableStateOf(false) }
            var autoTheme by remember { mutableStateOf(true) }
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(if (darkMode) LucideMoon else LucideSun, modifier = Modifier.size(16.dp), tint = DarwinTheme.colorScheme.textSecondary)
                        Text("Dark Mode", style = DarwinTheme.typography.subheadline)
                    }
                    Switch(checked = darkMode, onCheckedChange = { darkMode = it })
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Follow system", style = DarwinTheme.typography.subheadline)
                    Switch(checked = autoTheme, onCheckedChange = { autoTheme = it })
                }
            }
        }

        GroupBox(label = "Text Size") {
            var size by remember { mutableStateOf(14f) }
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text("Size", style = DarwinTheme.typography.caption1, color = DarwinTheme.colorScheme.textTertiary)
                    Text("${size.toInt()}pt", style = DarwinTheme.typography.caption1, color = DarwinTheme.colorScheme.textTertiary)
                }
                Slider(value = size, onValueChange = { size = it }, valueRange = 10f..24f)
            }
        }
    }
}

@GalleryExample("GroupBox", "Switches")
@Composable
fun GroupBoxSwitchesExample() {
    var notifications by remember { mutableStateOf(true) }
    var sounds by remember { mutableStateOf(false) }
    var badges by remember { mutableStateOf(true) }

    GroupBox(label = "Notifications") {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Allow notifications", style = DarwinTheme.typography.subheadline)
                Switch(checked = notifications, onCheckedChange = { notifications = it })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Sounds", style = DarwinTheme.typography.subheadline)
                Switch(checked = sounds, onCheckedChange = { sounds = it })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Badges", style = DarwinTheme.typography.subheadline)
                Switch(checked = badges, onCheckedChange = { badges = it })
            }
        }
    }
}

@GalleryExample("GroupBox", "Checkboxes")
@Composable
fun GroupBoxCheckboxesExample() {
    var opt1 by remember { mutableStateOf(true) }
    var opt2 by remember { mutableStateOf(false) }
    var opt3 by remember { mutableStateOf(true) }

    GroupBox(label = "Export Options") {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CheckBox(checked = opt1, onCheckedChange = { opt1 = it }, label = "Include metadata")
            CheckBox(checked = opt2, onCheckedChange = { opt2 = it }, label = "Compress output")
            CheckBox(checked = opt3, onCheckedChange = { opt3 = it }, label = "Open after export")
        }
    }
}

@GalleryExample("GroupBox", "Radio Buttons")
@Composable
fun GroupBoxRadioExample() {
    val options = listOf("List", "Grid", "Columns")
    var selected by remember { mutableStateOf("List") }

    GroupBox(label = "View Style") {
        Column(
            modifier = Modifier.selectableGroup(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            options.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    RadioButton(selected = selected == option, onClick = { selected = option })
                    Text(option, style = DarwinTheme.typography.subheadline)
                }
            }
        }
    }
}

@GalleryExample("GroupBox", "No Label")
@Composable
fun GroupBoxNoLabelExample() {
    var opt1 by remember { mutableStateOf(false) }
    var opt2 by remember { mutableStateOf(true) }

    GroupBox {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CheckBox(checked = opt1, onCheckedChange = { opt1 = it }, label = "Enable feature flag")
            CheckBox(checked = opt2, onCheckedChange = { opt2 = it }, label = "Use experimental API")
        }
    }
}

@GalleryExample("GroupBox", "Preferences Panel")
@Composable
fun GroupBoxPreferencesPanelExample() {
    var darkMode by remember { mutableStateOf(false) }
    var autoSave by remember { mutableStateOf(true) }
    var fontSize by remember { mutableStateOf(14f) }
    val themes = listOf("System", "Light", "Dark")
    var theme by remember { mutableStateOf("System") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        GroupBox(label = "Appearance") {
            Column(
                modifier = Modifier.selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                themes.forEach { t ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        RadioButton(selected = theme == t, onClick = { theme = t })
                        Text(t, style = DarwinTheme.typography.subheadline)
                    }
                }
            }
        }

        GroupBox(label = "Editor") {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Auto Save", style = DarwinTheme.typography.subheadline)
                    Switch(checked = autoSave, onCheckedChange = { autoSave = it })
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text("Font Size", style = DarwinTheme.typography.subheadline)
                        Text("${fontSize.toInt()}px", style = DarwinTheme.typography.caption1, color = DarwinTheme.colorScheme.textTertiary)
                    }
                    Slider(value = fontSize, onValueChange = { fontSize = it }, valueRange = 10f..24f)
                }
            }
        }
    }
}

@Composable
internal fun GroupBoxPage() {
    GalleryPage("Group Box", "A container that groups related controls with an optional label, following macOS GroupBox / NSBox conventions.") {
        PreviewContainer { GroupBoxPreview() }

        SectionHeader("Examples")
        ExampleCard(
            title = "Switches",
            description = "Grouping toggle switches under a labeled section",
            sourceCode = GallerySources.GroupBoxSwitchesExample,
        ) { GroupBoxSwitchesExample() }
        ExampleCard(
            title = "Checkboxes",
            description = "Grouping related checkboxes with a common label",
            sourceCode = GallerySources.GroupBoxCheckboxesExample,
        ) { GroupBoxCheckboxesExample() }
        ExampleCard(
            title = "Radio Buttons",
            description = "Exclusive option selection within a labeled box",
            sourceCode = GallerySources.GroupBoxRadioExample,
        ) { GroupBoxRadioExample() }
        ExampleCard(
            title = "Without Label",
            description = "GroupBox used purely as a visual container",
            sourceCode = GallerySources.GroupBoxNoLabelExample,
        ) { GroupBoxNoLabelExample() }

        SectionHeader("Composition")
        ExampleCard(
            title = "Preferences Panel",
            description = "Multiple GroupBox sections composing a settings panel",
            sourceCode = GallerySources.GroupBoxPreferencesPanelExample,
        ) { GroupBoxPreferencesPanelExample() }
    }
}
