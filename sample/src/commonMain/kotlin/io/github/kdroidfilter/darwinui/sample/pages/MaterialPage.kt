package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.GlassMaterialSize
import io.github.kdroidfilter.darwinui.theme.darwinGlassMaterial

@Composable
private fun ColorfulBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF8E8E93),
                        Color.White,
                        Color(0xFFFFCC00),
                        Color(0xFFFF8D28),
                        Color(0xFFFF383C),
                    ),
                ),
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@GalleryExample("Material", "Sizes")
@Composable
fun MaterialSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GlassMaterialSize.entries.forEach { size ->
            val shape = when (size) {
                GlassMaterialSize.Small -> RoundedCornerShape(60.dp)
                GlassMaterialSize.Medium -> RoundedCornerShape(20.dp)
                GlassMaterialSize.Large -> RoundedCornerShape(20.dp)
            }
            val height = when (size) {
                GlassMaterialSize.Small -> 48.dp
                GlassMaterialSize.Medium -> 120.dp
                GlassMaterialSize.Large -> 200.dp
            }
            ColorfulBackground(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .darwinGlassMaterial(shape = shape, materialSize = size),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = size.name,
                        style = DarwinTheme.typography.headline,
                        color = DarwinTheme.colorScheme.textPrimary,
                    )
                }
            }
        }
    }
}

@GalleryExample("Material", "Tinted")
@Composable
fun MaterialTintedExample() {
    val accent = DarwinTheme.colorScheme.accent
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GlassMaterialSize.entries.forEach { size ->
            val shape = when (size) {
                GlassMaterialSize.Small -> RoundedCornerShape(60.dp)
                GlassMaterialSize.Medium -> RoundedCornerShape(20.dp)
                GlassMaterialSize.Large -> RoundedCornerShape(20.dp)
            }
            val height = when (size) {
                GlassMaterialSize.Small -> 48.dp
                GlassMaterialSize.Medium -> 120.dp
                GlassMaterialSize.Large -> 200.dp
            }
            ColorfulBackground(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .darwinGlassMaterial(
                            shape = shape,
                            materialSize = size,
                            tintColor = accent,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "${size.name} + Tinted",
                        style = DarwinTheme.typography.headline,
                        color = DarwinTheme.colorScheme.textPrimary,
                    )
                }
            }
        }
    }
}

@GalleryExample("Material", "Comparison")
@Composable
fun MaterialComparisonExample() {
    val shape = RoundedCornerShape(20.dp)
    ColorfulBackground(modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "Regular",
                    style = DarwinTheme.typography.caption1,
                    color = Color.White,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .darwinGlassMaterial(shape = shape, materialSize = GlassMaterialSize.Medium),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Medium", color = DarwinTheme.colorScheme.textPrimary)
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "Tinted",
                    style = DarwinTheme.typography.caption1,
                    color = Color.White,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .darwinGlassMaterial(
                            shape = shape,
                            materialSize = GlassMaterialSize.Medium,
                            tintColor = DarwinTheme.colorScheme.accent,
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Medium", color = DarwinTheme.colorScheme.textPrimary)
                }
            }
        }
    }
}

@Composable
internal fun MaterialPage() {
    GalleryPage(
        "Material",
        "Liquid Glass materials from macOS 26 — frosted glass panels at Small, Medium, and Large sizes.",
    ) {
        PreviewContainer {
            val shape = RoundedCornerShape(20.dp)
            ColorfulBackground(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .darwinGlassMaterial(shape = shape, materialSize = GlassMaterialSize.Medium),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Liquid Glass",
                        style = DarwinTheme.typography.title1,
                        color = DarwinTheme.colorScheme.textPrimary,
                    )
                }
            }
        }

        SectionHeader("Sizes")
        ExampleCard(
            title = "Small / Medium / Large",
            description = "Three material tiers with increasing blur radius",
            sourceCode = GallerySources.MaterialSizesExample,
        ) { MaterialSizesExample() }

        SectionHeader("Tinted")
        ExampleCard(
            title = "Accent Tinted Materials",
            description = "Glass materials with accent color overlay",
            sourceCode = GallerySources.MaterialTintedExample,
        ) { MaterialTintedExample() }

        SectionHeader("Comparison")
        ExampleCard(
            title = "Regular vs Tinted",
            description = "Side-by-side comparison",
            sourceCode = GallerySources.MaterialComparisonExample,
        ) { MaterialComparisonExample() }
    }
}
