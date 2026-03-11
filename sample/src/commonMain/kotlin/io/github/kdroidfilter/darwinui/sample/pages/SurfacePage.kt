package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Card
import io.github.kdroidfilter.darwinui.components.CardContent
import io.github.kdroidfilter.darwinui.components.CardHeader
import io.github.kdroidfilter.darwinui.components.CardTitle
import io.github.kdroidfilter.darwinui.components.Surface as DarwinSurface
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import androidx.compose.material3.Text as M3Text

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun SurfacePage() {
    GalleryPage("Surface", "Darwin Card/Surface vs Material 3 Surface container.") {
        SectionHeader("Container Variants")
        ComparisonSection(
            darwinContent = {
                Card {
                    CardHeader {
                        CardTitle { Text("Darwin Card") }
                    }
                    CardContent {
                        Text("Structured container with header/content/footer sections.")
                    }
                }
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    DarwinSurface(
                        modifier = Modifier.size(80.dp),
                        color = DarwinTheme.colorScheme.surface,
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                            Text("Surface", style = DarwinTheme.typography.labelSmall)
                        }
                    }
                    DarwinSurface(
                        modifier = Modifier.size(80.dp),
                        color = DarwinTheme.colorScheme.primaryContainer,
                        shape = DarwinTheme.shapes.large,
                        shadowElevation = 4.dp,
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                            Text("Elevated", style = DarwinTheme.typography.labelSmall)
                        }
                    }
                    DarwinSurface(
                        modifier = Modifier.size(80.dp),
                        color = DarwinTheme.colorScheme.surface,
                        shape = DarwinTheme.shapes.extraLarge,
                        border = BorderStroke(1.dp, DarwinTheme.colorScheme.outline),
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                            Text("Outlined", style = DarwinTheme.typography.labelSmall)
                        }
                    }
                }
            },
            materialContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Surface(modifier = Modifier.size(80.dp), tonalElevation = 0.dp) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                            M3Text("Surface")
                        }
                    }
                    Surface(modifier = Modifier.size(80.dp), tonalElevation = 4.dp) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                            M3Text("Elevated")
                        }
                    }
                    Surface(
                        modifier = Modifier.size(80.dp),
                        tonalElevation = 0.dp,
                        border = BorderStroke(1.dp, androidx.compose.material3.MaterialTheme.colorScheme.outline),
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
                            M3Text("Outlined")
                        }
                    }
                }
            },
        )
    }
}
