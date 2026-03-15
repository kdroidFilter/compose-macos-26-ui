package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
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
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ArrowButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PushButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PushButtonStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideDownload
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideHeart
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideMoon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideShare2
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideStar
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideStarOff
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSun
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideTrash2
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.PreviewContainer
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

// ===========================================================================
// Preview
// ===========================================================================

@Composable
private fun ButtonPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PushButton(text = "Default", onClick = {}, style = PushButtonStyle.Default)
            PushButton(text = "Secondary", onClick = {}, style = PushButtonStyle.Secondary)
            PushButton(text = "Destructive", onClick = {}, style = PushButtonStyle.Destructive)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PushButton(text = "Neutral", onClick = {}, style = PushButtonStyle.Neutral)
            PushButton(text = "Borderless", onClick = {}, style = PushButtonStyle.Borderless)
            PushButton(text = "Bezel", onClick = {}, style = PushButtonStyle.BorderlessBezel)
            ArrowButton(onClick = {})
        }
    }
}

// ===========================================================================
// Sizes
// ===========================================================================

@GalleryExample("Button", "Sizes")
@Composable
fun ButtonSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = MacosTheme.typography.caption1,
                        color = MacosTheme.colorScheme.textSecondary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    PushButton(text = "Label", onClick = {})
                    PushButton(text = "Disabled", onClick = {}, enabled = false)
                }
            }
        }
    }
}

// ===========================================================================
// Push Button — Bordered Default
// ===========================================================================

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Bordered Default")
@Composable
fun ButtonBorderedDefaultExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PushButton(text = "Label", onClick = {}, style = PushButtonStyle.Default)
        PushButton(text = "Disabled", onClick = {}, style = PushButtonStyle.Default, enabled = false)
    }
}

// ===========================================================================
// Push Button — Bordered Colored (toggleable accent)
// ===========================================================================

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Bordered Colored")
@Composable
fun ButtonBorderedColoredExample() {
    var selected by remember { mutableStateOf(false) }
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PushButton(
            text = if (selected) "On" else "Off",
            onClick = { selected = !selected },
            style = PushButtonStyle.Accent,
            selected = selected,
        )
        PushButton(text = "Disabled", onClick = {}, style = PushButtonStyle.Accent, enabled = false)
    }
}

// ===========================================================================
// Push Button — Bordered Destructive
// ===========================================================================

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Bordered Destructive")
@Composable
fun ButtonBorderedDestructiveExample() {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PushButton(text = "Delete", onClick = {}, style = PushButtonStyle.Destructive)
        PushButton(text = "Disabled", onClick = {}, style = PushButtonStyle.Destructive, enabled = false)
    }
}

// ===========================================================================
// Push Button — Bordered Secondary
// ===========================================================================

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Bordered Secondary")
@Composable
fun ButtonBorderedSecondaryExample() {
    var selected by remember { mutableStateOf(false) }
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PushButton(
            text = if (selected) "On" else "Off",
            onClick = { selected = !selected },
            style = PushButtonStyle.Secondary,
            selected = selected,
        )
        PushButton(text = "Label", onClick = {}, style = PushButtonStyle.Secondary)
        PushButton(text = "Disabled", onClick = {}, style = PushButtonStyle.Secondary, enabled = false)
    }
}

// ===========================================================================
// Push Button — Borderless (On state shown by label change)
// ===========================================================================

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Borderless")
@Composable
fun ButtonBorderlessExample() {
    var starred by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(false) }
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            PushButton(
                text = if (starred) "Starred" else "Star",
                icon = if (starred) LucideStar else LucideStarOff,
                onClick = { starred = !starred },
                style = PushButtonStyle.Borderless,
            )
            PushButton(
                text = if (darkMode) "Dark" else "Light",
                icon = if (darkMode) LucideMoon else LucideSun,
                onClick = { darkMode = !darkMode },
                style = PushButtonStyle.Borderless,
            )
            PushButton(text = "Label", onClick = {}, style = PushButtonStyle.Borderless)
            PushButton(text = "Disabled", onClick = {}, style = PushButtonStyle.Borderless, enabled = false)
        }
        Text(
            text = "No visual change on toggle — only the icon and label text switch.",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textSecondary,
        )
    }
}

// ===========================================================================
// Push Button — Borderless (On state shown by bezel)
// ===========================================================================

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Borderless Bezel")
@Composable
fun ButtonBorderlessBezelExample() {
    var selected by remember { mutableStateOf(false) }
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PushButton(
            text = if (selected) "On" else "Off",
            onClick = { selected = !selected },
            style = PushButtonStyle.BorderlessBezel,
            selected = selected,
        )
        PushButton(text = "Label", onClick = {}, style = PushButtonStyle.BorderlessBezel)
        PushButton(text = "Disabled", onClick = {}, style = PushButtonStyle.BorderlessBezel, enabled = false)
        PushButton(
            text = "Disabled On",
            onClick = {},
            style = PushButtonStyle.BorderlessBezel,
            selected = true,
            enabled = false,
        )
    }
}

// ===========================================================================
// Push Button — Neutral
// ===========================================================================

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Neutral")
@Composable
fun ButtonNeutralExample() {
    var selected by remember { mutableStateOf(false) }
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PushButton(
            text = if (selected) "On" else "Off",
            onClick = { selected = !selected },
            style = PushButtonStyle.Neutral,
            selected = selected,
        )
        PushButton(text = "Label", onClick = {}, style = PushButtonStyle.Neutral)
        PushButton(text = "Disabled", onClick = {}, style = PushButtonStyle.Neutral, enabled = false)
    }
}

// ===========================================================================
// Push Button — Icon + Text / Icon Only
// ===========================================================================

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Button", "Icon")
@Composable
fun ButtonIconExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = size.name,
                        style = MacosTheme.typography.caption1,
                        color = MacosTheme.colorScheme.textSecondary,
                        modifier = Modifier.widthIn(min = 72.dp).align(Alignment.CenterVertically),
                    )
                    PushButton(
                        text = "Download",
                        icon = LucideDownload,
                        onClick = {},
                        style = PushButtonStyle.Default,
                    )
                    PushButton(
                        text = "Share",
                        icon = LucideShare2,
                        onClick = {},
                        style = PushButtonStyle.Secondary,
                    )
                    PushButton(
                        icon = LucideHeart,
                        onClick = {},
                        style = PushButtonStyle.BorderlessBezel,
                    )
                    PushButton(
                        text = "Delete",
                        icon = LucideTrash2,
                        onClick = {},
                        style = PushButtonStyle.Destructive,
                    )
                }
            }
        }
    }
}

// ===========================================================================
// Surface Appearance — Content Area vs Over Glass
// ===========================================================================

@GalleryExample("Button", "Surface")
@Composable
fun ButtonSurfaceExample() {
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
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textSecondary,
            )
            Surface(Surface.ContentArea) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    PushButton(text = "Default", onClick = {}, style = PushButtonStyle.Default)
                    PushButton(text = "Secondary", onClick = {}, style = PushButtonStyle.Secondary)
                    PushButton(text = "Destructive", onClick = {}, style = PushButtonStyle.Destructive)
                    PushButton(text = "Neutral", onClick = {}, style = PushButtonStyle.Neutral)
                    PushButton(text = "Borderless", onClick = {}, style = PushButtonStyle.Borderless)
                    PushButton(text = "Bezel", onClick = {}, style = PushButtonStyle.BorderlessBezel)
                    PushButton(text = "Disabled", onClick = {}, style = PushButtonStyle.Default, enabled = false)
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
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textSecondary,
            )
            Surface(Surface.OverGlass) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    PushButton(text = "Default", onClick = {}, style = PushButtonStyle.Default)
                    PushButton(text = "Secondary", onClick = {}, style = PushButtonStyle.Secondary)
                    PushButton(text = "Destructive", onClick = {}, style = PushButtonStyle.Destructive)
                    PushButton(text = "Neutral", onClick = {}, style = PushButtonStyle.Neutral)
                    PushButton(text = "Borderless", onClick = {}, style = PushButtonStyle.Borderless)
                    PushButton(text = "Bezel", onClick = {}, style = PushButtonStyle.BorderlessBezel)
                    PushButton(text = "Disabled", onClick = {}, style = PushButtonStyle.Default, enabled = false)
                }
            }
        }
    }
}

// ===========================================================================
// Arrow Button — Sizes
// ===========================================================================

@GalleryExample("Button", "Arrow")
@Composable
fun ButtonArrowExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = size.name,
                        style = MacosTheme.typography.caption1,
                        color = MacosTheme.colorScheme.textSecondary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    ArrowButton(onClick = {})
                    ArrowButton(onClick = {}, enabled = false)
                }
            }
        }
    }
}

// ===========================================================================
// Arrow Button — Surface Appearance (Content Area vs Over Glass)
// ===========================================================================

@GalleryExample("Button", "Arrow Surface")
@Composable
fun ButtonArrowSurfaceExample() {
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
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textSecondary,
            )
            Surface(Surface.ContentArea) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ArrowButton(onClick = {})
                    ArrowButton(onClick = {}, enabled = false)
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
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textSecondary,
            )
            Surface(Surface.OverGlass) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ArrowButton(onClick = {})
                    ArrowButton(onClick = {}, enabled = false)
                }
            }
        }
    }
}

// ===========================================================================
// ButtonPage — organized exactly like Sketch Buttons page
// ===========================================================================

@Composable
internal fun ButtonPage() {
    GalleryPage("Button", "Push buttons and arrow buttons — macOS 26 Liquid Glass style.") {
        PreviewContainer { ButtonPreview() }

        SectionHeader("Sizes")
        ExampleCard(
            title = "All Sizes",
            description = "PushButton at each ControlSize level",
            sourceCode = GallerySources.ButtonSizesExample,
        ) { ButtonSizesExample() }

        SectionHeader("Bordered Default")
        ExampleCard(
            title = "Bordered Default",
            description = "Solid accent-filled button — primary action",
            sourceCode = GallerySources.ButtonBorderedDefaultExample,
        ) { ButtonBorderedDefaultExample() }

        SectionHeader("Bordered Colored")
        ExampleCard(
            title = "Bordered Colored",
            description = "Solid accent-filled toggle button",
            sourceCode = GallerySources.ButtonBorderedColoredExample,
        ) { ButtonBorderedColoredExample() }

        SectionHeader("Bordered Destructive")
        ExampleCard(
            title = "Bordered Destructive",
            description = "Red-tinted background for destructive actions",
            sourceCode = GallerySources.ButtonBorderedDestructiveExample,
        ) { ButtonBorderedDestructiveExample() }

        SectionHeader("Bordered Secondary")
        ExampleCard(
            title = "Bordered Secondary",
            description = "Accent-tinted background with accent text",
            sourceCode = GallerySources.ButtonBorderedSecondaryExample,
        ) { ButtonBorderedSecondaryExample() }

        SectionHeader("Borderless (Label Change)")
        ExampleCard(
            title = "Borderless — Label Change",
            description = "Transparent background, on/off only changes the label text",
            sourceCode = GallerySources.ButtonBorderlessExample,
        ) { ButtonBorderlessExample() }

        SectionHeader("Borderless (Bezel)")
        ExampleCard(
            title = "Borderless — Bezel",
            description = "Transparent when off, accent-filled bezel when on, hover/press overlays",
            sourceCode = GallerySources.ButtonBorderlessBezelExample,
        ) { ButtonBorderlessBezelExample() }

        SectionHeader("Neutral")
        ExampleCard(
            title = "Neutral",
            description = "Gray background, neutral text, darker fill on toggle",
            sourceCode = GallerySources.ButtonNeutralExample,
        ) { ButtonNeutralExample() }

        SectionHeader("Icon + Text")
        ExampleCard(
            title = "Icon Buttons",
            description = "Leading icon with text, or icon-only — sizing adapts to ControlSize",
            sourceCode = GallerySources.ButtonIconExample,
        ) { ButtonIconExample() }

        SectionHeader("Surface Appearance")
        ExampleCard(
            title = "Content Area vs Over Glass",
            description = "Buttons adapt their press overlay and disabled opacity per surface",
            sourceCode = GallerySources.ButtonSurfaceExample,
        ) { ButtonSurfaceExample() }

        SectionHeader("Arrow Button")
        ExampleCard(
            title = "Arrow Button — Sizes",
            description = "Circular popup chevron button at each ControlSize",
            sourceCode = GallerySources.ButtonArrowExample,
        ) { ButtonArrowExample() }

        ExampleCard(
            title = "Arrow Button — Surface",
            description = "Arrow button adapts disabled opacity per surface",
            sourceCode = GallerySources.ButtonArrowSurfaceExample,
        ) { ButtonArrowSurfaceExample() }
    }
}
