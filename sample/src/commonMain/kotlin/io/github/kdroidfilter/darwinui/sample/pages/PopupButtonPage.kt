package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.MenuPlacement
import io.github.kdroidfilter.darwinui.components.PopupButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinSurface
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

// ===========================================================================
// Preview
// ===========================================================================

@Composable
private fun PopupButtonPreview() {
    val fruits = listOf("McIntosh", "Granny Smith", "Fuji", "Honeycrisp", "Pink Lady")
    var selected by remember { mutableIntStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        PopupButton(
            items = fruits,
            selectedIndex = selected,
            onSelectedChange = { selected = it },
            modifier = Modifier.widthIn(min = 150.dp),
        )
        PopupButton(
            items = fruits,
            selectedIndex = 0,
            onSelectedChange = {},
            enabled = false,
            modifier = Modifier.widthIn(min = 150.dp),
        )
    }
}

// ===========================================================================
// Sizes
// ===========================================================================

@GalleryExample("PopupButton", "Sizes")
@Composable
fun PopupButtonSizesExample() {
    val apples = listOf("McIntosh", "Granny Smith", "Fuji", "Honeycrisp", "Pink Lady")
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                var selected by remember { mutableIntStateOf(0) }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = DarwinTheme.typography.caption1,
                        color = DarwinTheme.colorScheme.textSecondary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    PopupButton(
                        items = apples,
                        selectedIndex = selected,
                        onSelectedChange = { selected = it },
                        modifier = Modifier.widthIn(min = 150.dp),
                    )
                    PopupButton(
                        items = apples,
                        selectedIndex = 0,
                        onSelectedChange = {},
                        enabled = false,
                        modifier = Modifier.widthIn(min = 150.dp),
                    )
                }
            }
        }
    }
}

// ===========================================================================
// States
// ===========================================================================

@GalleryExample("PopupButton", "States")
@Composable
fun PopupButtonStatesExample() {
    val items = listOf("McIntosh", "Granny Smith", "Fuji", "Honeycrisp", "Pink Lady")
    var selected by remember { mutableIntStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Enabled",
                style = DarwinTheme.typography.caption1,
                color = DarwinTheme.colorScheme.textSecondary,
                modifier = Modifier.widthIn(min = 72.dp),
            )
            PopupButton(
                items = items,
                selectedIndex = selected,
                onSelectedChange = { selected = it },
                modifier = Modifier.widthIn(min = 150.dp),
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Disabled",
                style = DarwinTheme.typography.caption1,
                color = DarwinTheme.colorScheme.textSecondary,
                modifier = Modifier.widthIn(min = 72.dp),
            )
            PopupButton(
                items = items,
                selectedIndex = 0,
                onSelectedChange = {},
                enabled = false,
                modifier = Modifier.widthIn(min = 150.dp),
            )
        }
    }
}

// ===========================================================================
// Surface Appearance — Content Area vs Over Glass
// ===========================================================================

@GalleryExample("PopupButton", "Surface")
@Composable
fun PopupButtonSurfaceExample() {
    val items = listOf("McIntosh", "Granny Smith", "Fuji")
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Content Area",
                style = DarwinTheme.typography.caption1,
                color = DarwinTheme.colorScheme.textSecondary,
            )
            DarwinSurface(DarwinSurface.ContentArea) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    var s by remember { mutableIntStateOf(0) }
                    PopupButton(
                        items = items,
                        selectedIndex = s,
                        onSelectedChange = { s = it },
                        modifier = Modifier.widthIn(min = 150.dp),
                    )
                    PopupButton(
                        items = items,
                        selectedIndex = 0,
                        onSelectedChange = {},
                        enabled = false,
                        modifier = Modifier.widthIn(min = 150.dp),
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "Over Glass",
                style = DarwinTheme.typography.caption1,
                color = DarwinTheme.colorScheme.textSecondary,
            )
            DarwinSurface(DarwinSurface.OverGlass) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    var s by remember { mutableIntStateOf(0) }
                    PopupButton(
                        items = items,
                        selectedIndex = s,
                        onSelectedChange = { s = it },
                        modifier = Modifier.widthIn(min = 150.dp),
                    )
                    PopupButton(
                        items = items,
                        selectedIndex = 0,
                        onSelectedChange = {},
                        enabled = false,
                        modifier = Modifier.widthIn(min = 150.dp),
                    )
                }
            }
        }
    }
}

// ===========================================================================
// Placement
// ===========================================================================

@GalleryExample("PopupButton", "Placement")
@Composable
fun PopupButtonPlacementExample() {
    val items = listOf("McIntosh", "Granny Smith", "Fuji")
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (placement in MenuPlacement.entries) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = placement.name,
                    style = DarwinTheme.typography.caption1,
                    color = DarwinTheme.colorScheme.textSecondary,
                    modifier = Modifier.widthIn(min = 100.dp),
                )
                var selected by remember { mutableIntStateOf(0) }
                PopupButton(
                    items = items,
                    selectedIndex = selected,
                    onSelectedChange = { selected = it },
                    placement = placement,
                    modifier = Modifier.widthIn(min = 150.dp),
                )
            }
        }
    }
}

// ===========================================================================
// Custom Items
// ===========================================================================

private data class Fruit(val name: String, val emoji: String)

@GalleryExample("PopupButton", "Custom Items")
@Composable
fun PopupButtonCustomItemsExample() {
    val fruits = listOf(
        Fruit("Apple", ""),
        Fruit("Banana", ""),
        Fruit("Cherry", ""),
        Fruit("Grape", ""),
        Fruit("Mango", ""),
    )
    var selected by remember { mutableIntStateOf(0) }
    PopupButton(
        items = fruits,
        selectedIndex = selected,
        onSelectedChange = { selected = it },
        itemText = { "${it.emoji} ${it.name}" },
        modifier = Modifier.widthIn(min = 180.dp),
    )
}

// ===========================================================================
// PopupButtonPage
// ===========================================================================

@Composable
internal fun PopupButtonPage() {
    GalleryPage("Pop-up Button", "Selection control for choosing from a list of options.") {
        PreviewContainer { PopupButtonPreview() }

        SectionHeader("Sizes")
        ExampleCard(
            title = "All Sizes",
            description = "PopupButton at each ControlSize — enabled and disabled",
            sourceCode = GallerySources.PopupButtonSizesExample,
        ) { PopupButtonSizesExample() }

        SectionHeader("States")
        ExampleCard(
            title = "Enabled vs Disabled",
            sourceCode = GallerySources.PopupButtonStatesExample,
        ) { PopupButtonStatesExample() }

        SectionHeader("Surface Appearance")
        ExampleCard(
            title = "Content Area vs Over Glass",
            sourceCode = GallerySources.PopupButtonSurfaceExample,
        ) { PopupButtonSurfaceExample() }

        SectionHeader("Placement")
        ExampleCard(
            title = "Menu Placement",
            description = "Control where the menu appears relative to the button",
            sourceCode = GallerySources.PopupButtonPlacementExample,
        ) { PopupButtonPlacementExample() }

        SectionHeader("Custom Items")
        ExampleCard(
            title = "Custom Item Display",
            description = "Use itemText to customize how items are displayed",
            sourceCode = GallerySources.PopupButtonCustomItemsExample,
        ) { PopupButtonCustomItemsExample() }
    }
}
