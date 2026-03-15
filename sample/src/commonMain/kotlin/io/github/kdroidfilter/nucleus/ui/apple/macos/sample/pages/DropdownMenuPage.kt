package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.MenuPlacement
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PushButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DropdownMenu
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DropdownMenuCheckboxItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DropdownMenuItem
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DropdownMenuLabel
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DropdownMenuSeparator
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DropdownMenuSubMenu
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("DropdownMenu", "Default")
@Composable
fun DropdownMenuDefaultExample() {
    var dropdownExpanded by remember { mutableStateOf(false) }
    Box {
        PushButton(
            text = "Open Menu",
            onClick = { dropdownExpanded = !dropdownExpanded },
        )
        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
        ) {
            DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("Profile") }
            DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("Settings") }
            DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("Preferences") }
            DropdownMenuSeparator()
            DropdownMenuItem(onClick = { dropdownExpanded = false }, destructive = true) { Text("Log out") }
        }
    }
}

@GalleryExample("DropdownMenu", "With Labels")
@Composable
fun DropdownMenuLabelsExample() {
    var dropdownExpanded by remember { mutableStateOf(false) }
    var checkboxState by remember { mutableStateOf(false) }
    Box {
        PushButton(
            text = "Actions",
            onClick = { dropdownExpanded = !dropdownExpanded },
        )
        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
        ) {
            DropdownMenuLabel(text = "Actions")
            DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("New File") }
            DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("Open") }
            DropdownMenuSeparator()
            DropdownMenuLabel(text = "Options")
            DropdownMenuCheckboxItem(checked = checkboxState, onCheckedChange = { checkboxState = it }) { Text("Auto-save") }
            DropdownMenuSeparator()
            DropdownMenuItem(onClick = { dropdownExpanded = false }, enabled = false) { Text("Disabled item") }
        }
    }
}

@GalleryExample("DropdownMenu", "With Submenus")
@Composable
fun DropdownMenuSubMenuExample() {
    var dropdownExpanded by remember { mutableStateOf(false) }
    Box {
        PushButton(
            text = "Open Menu",
            onClick = { dropdownExpanded = !dropdownExpanded },
        )
        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
        ) {
            DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("New File") }
            DropdownMenuSubMenu(
                submenuContent = {
                    DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("From Clipboard") }
                    DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("From File...") }
                    DropdownMenuSubMenu(
                        submenuContent = {
                            DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("PNG") }
                            DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("JPEG") }
                            DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("SVG") }
                        },
                    ) { Text("From Template") }
                },
            ) { Text("Import") }
            DropdownMenuSeparator()
            DropdownMenuSubMenu(
                submenuContent = {
                    DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("PDF") }
                    DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("HTML") }
                    DropdownMenuItem(onClick = { dropdownExpanded = false }) { Text("Markdown") }
                },
            ) { Text("Export As") }
            DropdownMenuSeparator()
            DropdownMenuItem(onClick = { dropdownExpanded = false }, destructive = true) { Text("Delete") }
        }
    }
}

@GalleryExample("DropdownMenu", "Placement")
@Composable
fun DropdownMenuPlacementExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (placement in MenuPlacement.entries) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = placement.name,
                    style = MacosTheme.typography.caption1,
                    color = MacosTheme.colorScheme.textSecondary,
                    modifier = Modifier.widthIn(min = 100.dp),
                )
                var expanded by remember { mutableStateOf(false) }
                Box {
                    PushButton(
                        text = "Open",
                        onClick = { expanded = !expanded },
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        placement = placement,
                    ) {
                        DropdownMenuItem(onClick = { expanded = false }) { Text("Item 1") }
                        DropdownMenuItem(onClick = { expanded = false }) { Text("Item 2") }
                        DropdownMenuItem(onClick = { expanded = false }) { Text("Item 3") }
                    }
                }
            }
        }
    }
}

@Composable
internal fun DropdownMenuPage() {
    GalleryPage("Dropdown Menu", "Displays a menu to the user with a list of actions.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.DropdownMenuDefaultExample) { DropdownMenuDefaultExample() }
        ExampleCard(
            title = "With Labels",
            sourceCode = GallerySources.DropdownMenuLabelsExample,
        ) { DropdownMenuLabelsExample() }
        ExampleCard(
            title = "With Submenus",
            description = "Nested submenus with hover-to-open behavior",
            sourceCode = GallerySources.DropdownMenuSubMenuExample,
        ) { DropdownMenuSubMenuExample() }
        SectionHeader("Placement")
        ExampleCard(
            title = "Menu Placement",
            description = "Control where the menu appears relative to the trigger",
            sourceCode = GallerySources.DropdownMenuPlacementExample,
        ) { DropdownMenuPlacementExample() }
    }
}
