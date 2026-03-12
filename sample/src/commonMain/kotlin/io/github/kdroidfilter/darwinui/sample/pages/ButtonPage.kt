package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.ArrowButton
import io.github.kdroidfilter.darwinui.components.DisclosureButton
import io.github.kdroidfilter.darwinui.components.MacNativeAccentButton
import io.github.kdroidfilter.darwinui.components.MacNativeDestructiveButton
import io.github.kdroidfilter.darwinui.components.MacNativeSecondaryButton
import io.github.kdroidfilter.darwinui.components.PanelAccentButton
import io.github.kdroidfilter.darwinui.components.PanelDestructiveButton
import io.github.kdroidfilter.darwinui.components.PanelSecondaryButton
import io.github.kdroidfilter.darwinui.components.PulldownButton
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Pulldown")
@Composable
fun ButtonPulldownExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PulldownButton(text = "Sort by", onClick = {})
        PulldownButton(text = "View", onClick = {})
    }
}

@GalleryExample("Button", "Disclosure")
@Composable
fun ButtonDisclosureExample() {
    var expanded by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DisclosureButton(expanded = expanded, onToggle = { expanded = !expanded })
            Text(if (expanded) "Collapse" else "Expand")
        }
        if (expanded) {
            Text("Hidden content revealed after toggle.", color = DarwinTheme.colors.textSecondary)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Push")
@Composable
fun ButtonPushExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PushButton(text = "Label", onClick = {})
        PushButton(text = "Cancel", onClick = {})
        PushButton(text = "Apply", onClick = {})
        PushButton(text = "Disabled", onClick = {}, enabled = false)
    }
}

@GalleryExample("Button", "Arrow")
@Composable
fun ButtonArrowExample() {
    var value by remember { mutableStateOf(0) }
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ArrowButton(
            onIncrement = { value++ },
            onDecrement = { value-- },
        )
        Text("Value: $value")
    }
}

@GalleryExample("Button", "Compact")
@Composable
fun ButtonAlertSheetExample() {
    Column(
        modifier = Modifier.widthIn(max = 260.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        MacNativeAccentButton(text = "Save", onClick = {})
        MacNativeDestructiveButton(text = "Don't Save", onClick = {})
        MacNativeSecondaryButton(text = "Cancel", onClick = {})
    }
}

@GalleryExample("Button", "Compact Footer")
@Composable
fun ButtonAlertSheetFooterExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        MacNativeDestructiveButton(text = "Delete", onClick = {}, fillWidth = false)
        Spacer(modifier = Modifier.weight(1f))
        MacNativeSecondaryButton(text = "Cancel", onClick = {}, fillWidth = false)
        MacNativeAccentButton(text = "Save", onClick = {}, fillWidth = false)
    }
}

@GalleryExample("Button", "Panel")
@Composable
fun ButtonPanelExample() {
    Column(
        modifier = Modifier.widthIn(max = 260.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        PanelAccentButton(text = "Save", onClick = {}, fillWidth = true)
        PanelDestructiveButton(text = "Delete", onClick = {}, fillWidth = true)
        PanelSecondaryButton(text = "Cancel", onClick = {}, fillWidth = true)
    }
}

@GalleryExample("Button", "Panel Footer")
@Composable
fun ButtonPanelFooterExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        PanelDestructiveButton(text = "Delete", onClick = {})
        Spacer(modifier = Modifier.weight(1f))
        PanelSecondaryButton(text = "Cancel", onClick = {})
        PanelAccentButton(text = "Save", onClick = {})
    }
}

@Composable
internal fun ButtonPage() {
    GalleryPage("Button", "Native macOS button controls with idiomatic Compose APIs.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Pulldown",
            description = "macOS-native pulldown button with glass appearance",
            sourceCode = GallerySources.ButtonPulldownExample,
        ) { ButtonPulldownExample() }
        ExampleCard(
            title = "Disclosure",
            description = "Circular toggle button that reveals or hides content",
            sourceCode = GallerySources.ButtonDisclosureExample,
        ) { ButtonDisclosureExample() }
        ExampleCard(
            title = "Push",
            description = "macOS-native compact push button (bezel/rounded style)",
            sourceCode = GallerySources.ButtonPushExample,
        ) { ButtonPushExample() }
        ExampleCard(
            title = "Arrow",
            description = "Circular stepper button with up/down increment zones",
            sourceCode = GallerySources.ButtonArrowExample,
        ) { ButtonArrowExample() }

        SectionHeader("Compact")
        ExampleCard(
            title = "Compact",
            description = "Pill-shaped compact buttons for dialogs and panels",
            sourceCode = GallerySources.ButtonAlertSheetExample,
        ) { ButtonAlertSheetExample() }
        ExampleCard(
            title = "Compact Footer",
            description = "Horizontal layout: destructive left, cancel + confirm right",
            sourceCode = GallerySources.ButtonAlertSheetFooterExample,
        ) { ButtonAlertSheetFooterExample() }

        SectionHeader("Panel")
        ExampleCard(
            title = "Panel",
            description = "NSSavePanel-style buttons with squarish rounded corners",
            sourceCode = GallerySources.ButtonPanelExample,
        ) { ButtonPanelExample() }
        ExampleCard(
            title = "Panel Footer",
            description = "Horizontal panel footer: delete left, cancel + save right",
            sourceCode = GallerySources.ButtonPanelFooterExample,
        ) { ButtonPanelFooterExample() }
    }
}
