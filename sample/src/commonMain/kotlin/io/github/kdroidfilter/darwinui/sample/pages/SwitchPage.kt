package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Switcher
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinSurface
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@Composable
private fun SwitchPreview() {
    var s1 by remember { mutableStateOf(true) }
    var s2 by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HoverOffsetItem { Switcher(checked = s1, onCheckedChange = { s1 = it }, label = "Enable notifications") }
        HoverOffsetItem { Switcher(checked = s2, onCheckedChange = { s2 = it }, label = "Dark mode") }
        HoverOffsetItem { Switcher(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false) }
    }
}

@Composable
internal fun HoverOffsetItem(content: @Composable () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val offsetX by animateDpAsState(
        targetValue = if (isHovered) 4.dp else 0.dp,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
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

@GalleryExample("Switch", "Sizes")
@Composable
fun SwitchSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
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
                    var s by remember { mutableStateOf(true) }
                    Switcher(checked = s, onCheckedChange = { s = it }, label = "Toggle")
                }
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
        Switcher(checked = switch1, onCheckedChange = { switch1 = it }, label = "Dark mode")
        Switcher(checked = switch2, onCheckedChange = { switch2 = it }, label = "Notifications")
        Switcher(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false)
    }
}

@GalleryExample("Switch", "Surface")
@Composable
fun SwitchSurfaceExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Content Area (default)
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
                    var s1 by remember { mutableStateOf(true) }
                    var s2 by remember { mutableStateOf(false) }
                    Switcher(checked = s1, onCheckedChange = { s1 = it }, label = "On")
                    Switcher(checked = s2, onCheckedChange = { s2 = it }, label = "Off")
                    Switcher(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false)
                }
            }
        }

        // Over Glass
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
                    var s1 by remember { mutableStateOf(true) }
                    var s2 by remember { mutableStateOf(false) }
                    Switcher(checked = s1, onCheckedChange = { s1 = it }, label = "On")
                    Switcher(checked = s2, onCheckedChange = { s2 = it }, label = "Off")
                    Switcher(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false)
                }
            }
        }
    }
}

@Composable
internal fun SwitchPage() {
    GalleryPage("Switch", "A control that toggles between on and off states.") {
        PreviewContainer { SwitchPreview() }

        SectionHeader("Sizes")
        ExampleCard(title = "All Sizes", sourceCode = GallerySources.SwitchSizesExample) { SwitchSizesExample() }

        SectionHeader("Surface Appearance")
        ExampleCard(title = "Content Area vs Over Glass", sourceCode = GallerySources.SwitchSurfaceExample) { SwitchSurfaceExample() }

        SectionHeader("Examples")
        ExampleCard(title = "States", sourceCode = GallerySources.SwitchStatesExample) { SwitchStatesExample() }
    }
}
