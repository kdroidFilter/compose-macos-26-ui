package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.kdroidfilter.darwinui.components.button.DarwinButton
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonVariant
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenu
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenuCheckboxItem
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenuItem
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenuLabel
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenuSeparator
import io.github.kdroidfilter.darwinui.components.dropdown.DarwinDropdownMenuShortcut
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@GalleryExample("DropdownMenu", "Default")
@Composable
fun DropdownMenuDefaultExample() {
    var dropdownExpanded by remember { mutableStateOf(false) }
    DarwinDropdownMenu(
        expanded = dropdownExpanded,
        onDismissRequest = { dropdownExpanded = false },
        trigger = {
            DarwinButton(
                text = "Open Menu",
                onClick = { dropdownExpanded = !dropdownExpanded },
                variant = DarwinButtonVariant.Outline,
            )
        },
    ) {
        DarwinDropdownMenuItem(onClick = { dropdownExpanded = false }) { DarwinText("Profile") }
        DarwinDropdownMenuItem(onClick = { dropdownExpanded = false }) { DarwinText("Settings") }
        DarwinDropdownMenuItem(onClick = { dropdownExpanded = false }) { DarwinText("Preferences") }
        DarwinDropdownMenuSeparator()
        DarwinDropdownMenuItem(onClick = { dropdownExpanded = false }, destructive = true) { DarwinText("Log out") }
    }
}

@GalleryExample("DropdownMenu", "With Labels & Shortcuts")
@Composable
fun DropdownMenuLabelsExample() {
    var dropdownExpanded by remember { mutableStateOf(false) }
    var checkboxState by remember { mutableStateOf(false) }
    DarwinDropdownMenu(
        expanded = dropdownExpanded,
        onDismissRequest = { dropdownExpanded = false },
        trigger = {
            DarwinButton(
                text = "Actions",
                onClick = { dropdownExpanded = !dropdownExpanded },
                variant = DarwinButtonVariant.Outline,
            )
        },
    ) {
        DarwinDropdownMenuLabel(text = "Actions")
        DarwinDropdownMenuItem(
            onClick = { dropdownExpanded = false },
            trailingContent = { DarwinDropdownMenuShortcut(text = "Cmd+N") },
        ) { DarwinText("New File") }
        DarwinDropdownMenuItem(
            onClick = { dropdownExpanded = false },
            trailingContent = { DarwinDropdownMenuShortcut(text = "Cmd+O") },
        ) { DarwinText("Open") }
        DarwinDropdownMenuSeparator()
        DarwinDropdownMenuLabel(text = "Options")
        DarwinDropdownMenuCheckboxItem(checked = checkboxState, onCheckedChange = { checkboxState = it }) { DarwinText("Auto-save") }
        DarwinDropdownMenuSeparator()
        DarwinDropdownMenuItem(onClick = { dropdownExpanded = false }, enabled = false) { DarwinText("Disabled item") }
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
