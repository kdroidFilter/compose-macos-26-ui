package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.HorizontalDivider as DarwinHorizontalDivider
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.VerticalDivider as DarwinVerticalDivider
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.Text as M3Text

@Composable
internal fun DividerPage() {
    GalleryPage("Divider", "Darwin HorizontalDivider/VerticalDivider vs Material 3 equivalents.") {
        SectionHeader("Horizontal")
        ComparisonSection(
            darwinContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("Section A")
                    DarwinHorizontalDivider()
                    Text("Section B")
                    DarwinHorizontalDivider()
                    Text("Section C")
                }
            },
            materialContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    M3Text("Section A")
                    HorizontalDivider()
                    M3Text("Section B")
                    HorizontalDivider()
                    M3Text("Section C")
                }
            },
        )

        SectionHeader("Vertical")
        ComparisonSection(
            darwinContent = {
                Box(modifier = Modifier.height(48.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(horizontal = 8.dp),
                    ) {
                        Text("Left", modifier = Modifier.padding(vertical = 12.dp))
                        DarwinVerticalDivider()
                        Text("Center", modifier = Modifier.padding(vertical = 12.dp))
                        DarwinVerticalDivider()
                        Text("Right", modifier = Modifier.padding(vertical = 12.dp))
                    }
                }
            },
            materialContent = {
                Box(modifier = Modifier.height(48.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(horizontal = 8.dp),
                    ) {
                        M3Text("Left", modifier = Modifier.padding(vertical = 12.dp))
                        VerticalDivider()
                        M3Text("Center", modifier = Modifier.padding(vertical = 12.dp))
                        VerticalDivider()
                        M3Text("Right", modifier = Modifier.padding(vertical = 12.dp))
                    }
                }
            },
        )
    }
}
