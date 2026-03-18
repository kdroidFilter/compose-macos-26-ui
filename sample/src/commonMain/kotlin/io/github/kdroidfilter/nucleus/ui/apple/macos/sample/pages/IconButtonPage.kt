package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.ArrowButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.IconButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.IconButtonDefaults
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.IconButtonRole
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.IconButtonStyle
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icons
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.CodeBlock
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.PreviewContainer
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@Composable
private fun IconButtonPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(icon = Icons.Heart, onClick = {}, style = IconButtonStyle.Bordered)
        IconButton(icon = Icons.Star, onClick = {}, style = IconButtonStyle.BorderedProminent)
        IconButton(icon = Icons.Share2, onClick = {}, style = IconButtonStyle.Borderless)
        IconButton(icon = Icons.Trash2, onClick = {}, style = IconButtonStyle.Bordered, role = IconButtonRole.Destructive)
        IconButton(icon = Icons.Trash2, onClick = {}, style = IconButtonStyle.BorderedProminent, role = IconButtonRole.Destructive)
        IconButton(icon = Icons.Trash2, onClick = {}, style = IconButtonStyle.Borderless, role = IconButtonRole.Destructive)
    }
}

@GalleryExample("IconButton", "Styles")
@Composable
fun IconButtonStylesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(icon = Icons.Heart, onClick = {}, style = IconButtonStyle.Bordered)
            IconButton(icon = Icons.Heart, onClick = {}, style = IconButtonStyle.Bordered, enabled = false)
            Text("Bordered", style = MacosTheme.typography.caption1, color = MacosTheme.colorScheme.textSecondary)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(icon = Icons.Star, onClick = {}, style = IconButtonStyle.BorderedProminent)
            IconButton(icon = Icons.Star, onClick = {}, style = IconButtonStyle.BorderedProminent, enabled = false)
            Text("Bordered Prominent", style = MacosTheme.typography.caption1, color = MacosTheme.colorScheme.textSecondary)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(icon = Icons.Share2, onClick = {}, style = IconButtonStyle.Borderless)
            IconButton(icon = Icons.Share2, onClick = {}, style = IconButtonStyle.Borderless, enabled = false)
            Text("Borderless", style = MacosTheme.typography.caption1, color = MacosTheme.colorScheme.textSecondary)
        }
    }
}

@GalleryExample("IconButton", "Destructive")
@Composable
fun IconButtonDestructiveExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(icon = Icons.Trash2, onClick = {}, style = IconButtonStyle.Bordered, role = IconButtonRole.Destructive)
            IconButton(icon = Icons.Trash2, onClick = {}, style = IconButtonStyle.BorderedProminent, role = IconButtonRole.Destructive)
            IconButton(icon = Icons.Trash2, onClick = {}, style = IconButtonStyle.Borderless, role = IconButtonRole.Destructive)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(icon = Icons.Trash2, onClick = {}, style = IconButtonStyle.Bordered, role = IconButtonRole.Destructive, enabled = false)
            IconButton(icon = Icons.Trash2, onClick = {}, style = IconButtonStyle.BorderedProminent, role = IconButtonRole.Destructive, enabled = false)
            IconButton(icon = Icons.Trash2, onClick = {}, style = IconButtonStyle.Borderless, role = IconButtonRole.Destructive, enabled = false)
        }
    }
}

@GalleryExample("IconButton", "Sizes")
@Composable
fun IconButtonSizesExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                IconButton(icon = Icons.Heart, onClick = {}, style = IconButtonStyle.Bordered)
            }
        }
    }
}

@GalleryExample("IconButton", "Custom Colors")
@Composable
fun IconButtonCustomColorsExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            icon = Icons.Heart,
            onClick = {},
            colors = IconButtonDefaults.colors(
                backgroundColor = Color(0xFFE91E63),
                contentColor = Color.White,
            ),
        )
        IconButton(
            icon = Icons.Star,
            onClick = {},
            colors = IconButtonDefaults.colors(
                backgroundColor = Color(0xFFFF9500),
                contentColor = Color.White,
            ),
        )
        IconButton(
            icon = Icons.Share2,
            onClick = {},
            colors = IconButtonDefaults.colors(
                backgroundColor = Color(0xFF34C759),
                contentColor = Color.White,
            ),
        )
        IconButton(
            icon = Icons.Heart,
            onClick = {},
            enabled = false,
            colors = IconButtonDefaults.colors(
                backgroundColor = Color(0xFFE91E63),
                contentColor = Color.White,
            ),
        )
    }
}

@GalleryExample("IconButton", "Arrow")
@Composable
fun IconButtonArrowExample() {
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

@GalleryExample("IconButton", "Arrow Surface")
@Composable
fun IconButtonArrowSurfaceExample() {
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

@Composable
internal fun IconButtonPage() {
    GalleryPage("Icon Button", "Circular icon-only buttons for toolbar and content area actions.") {
        PreviewContainer { IconButtonPreview() }

        SectionHeader("Usage")
        CodeBlock("""IconButton(icon = Icons.Heart, onClick = {})
IconButton(icon = Icons.Star, onClick = {}, style = IconButtonStyle.BorderedProminent)
IconButton(icon = Icons.Trash2, onClick = {}, role = IconButtonRole.Destructive)""")

        SectionHeader("Styles")
        ExampleCard(
            title = "All Styles",
            description = "Bordered, Bordered Prominent, and Borderless with idle and disabled states",
            sourceCode = GallerySources.IconButtonStylesExample,
        ) { IconButtonStylesExample() }

        SectionHeader("Examples")
        ExampleCard(
            title = "Destructive",
            description = "All styles with destructive role",
            sourceCode = GallerySources.IconButtonDestructiveExample,
        ) { IconButtonDestructiveExample() }
        SectionHeader("Custom Colors")
        ExampleCard(
            title = "Custom Colors",
            description = "Icon buttons with fully custom background and icon colors",
            sourceCode = GallerySources.IconButtonCustomColorsExample,
        ) { IconButtonCustomColorsExample() }

        SectionHeader("Arrow Button")
        ExampleCard(
            title = "Arrow Button — Sizes",
            description = "Circular popup chevron button at each ControlSize",
            sourceCode = GallerySources.IconButtonArrowExample,
        ) { IconButtonArrowExample() }

        ExampleCard(
            title = "Arrow Button — Surface",
            description = "Arrow button adapts disabled opacity per surface",
            sourceCode = GallerySources.IconButtonArrowSurfaceExample,
        ) { IconButtonArrowSurfaceExample() }
    }
}
