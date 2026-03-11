package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.kdroidfilter.darwinui.components.OutlineButton
import io.github.kdroidfilter.darwinui.components.DropdownMenu
import io.github.kdroidfilter.darwinui.components.DropdownMenuCheckboxItem
import io.github.kdroidfilter.darwinui.components.DropdownMenuItem
import io.github.kdroidfilter.darwinui.components.DropdownMenuLabel
import io.github.kdroidfilter.darwinui.components.DropdownMenuSeparator
import io.github.kdroidfilter.darwinui.components.DropdownMenuShortcut
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("DropdownMenu", "Default")
@Composable
fun DropdownMenuDefaultExample() {
    var dropdownExpanded by remember { mutableStateOf(false) }
    Box {
        OutlineButton(
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

@GalleryExample("DropdownMenu", "With Labels & Shortcuts")
@Composable
fun DropdownMenuLabelsExample() {
    var dropdownExpanded by remember { mutableStateOf(false) }
    var checkboxState by remember { mutableStateOf(false) }
    Box {
        OutlineButton(
            text = "Actions",
            onClick = { dropdownExpanded = !dropdownExpanded },
        )
        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
        ) {
            DropdownMenuLabel(text = "Actions")
            DropdownMenuItem(
                onClick = { dropdownExpanded = false },
                trailingContent = { DropdownMenuShortcut(text = "Cmd+N") },
            ) { Text("New File") }
            DropdownMenuItem(
                onClick = { dropdownExpanded = false },
                trailingContent = { DropdownMenuShortcut(text = "Cmd+O") },
            ) { Text("Open") }
            DropdownMenuSeparator()
            DropdownMenuLabel(text = "Options")
            DropdownMenuCheckboxItem(checked = checkboxState, onCheckedChange = { checkboxState = it }) { Text("Auto-save") }
            DropdownMenuSeparator()
            DropdownMenuItem(onClick = { dropdownExpanded = false }, enabled = false) { Text("Disabled item") }
        }
    }
}

@Composable
internal fun DropdownMenuPage() {
    GalleryPage("Dropdown Menu", "Displays a menu to the user with a list of actions.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.DropdownMenuDefaultExample) { DropdownMenuDefaultExample() }
        ExampleCard(
            title = "With Labels & Shortcuts",
            sourceCode = GallerySources.DropdownMenuLabelsExample,
        ) { DropdownMenuLabelsExample() }
    }
}
