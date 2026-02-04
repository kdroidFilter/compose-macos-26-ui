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
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.switchcomponent.DarwinSwitch
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
        HoverOffsetItem { DarwinSwitch(checked = s1, onCheckedChange = { s1 = it }, label = "Enable notifications") }
        HoverOffsetItem { DarwinSwitch(checked = s2, onCheckedChange = { s2 = it }, label = "Dark mode") }
        HoverOffsetItem { DarwinSwitch(checked = false, onCheckedChange = {}, label = "Disabled", enabled = false) }
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

@Composable
internal fun SwitchPage() {
    GalleryPage("Switch", "A control that toggles between on and off states.") {
        PreviewContainer { SwitchPreview() }

        SectionHeader("Examples")
        ExampleCard(title = "States", sourceCode = GallerySources.SwitchStatesExample) { SwitchStatesExample() }
    }
}
