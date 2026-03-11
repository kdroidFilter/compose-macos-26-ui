package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Pencil
import com.composables.icons.lucide.Plus
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import androidx.compose.material3.Text as M3Text

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun FabPage() {
    GalleryPage("Floating Action Button", "Darwin PrimaryButton vs Material 3 FloatingActionButton variants.") {
        SectionHeader("Sizes")
        ComparisonSection(
            darwinContent = {
                Text(
                    "Darwin uses PrimaryButton for primary actions — no dedicated FAB.",
                    style = DarwinTheme.typography.bodySmall,
                    color = DarwinTheme.colors.textTertiary,
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PrimaryButton(onClick = {}) { Icon(Lucide.Plus) }
                    PrimaryButton(text = "New note", onClick = {}, leftIcon = { Icon(Lucide.Pencil) })
                }
            },
            materialContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    SmallFloatingActionButton(onClick = {}) { Icon(Lucide.Plus) }
                    FloatingActionButton(onClick = {}) { Icon(Lucide.Plus) }
                    LargeFloatingActionButton(onClick = {}) { Icon(Lucide.Plus) }
                }
            },
        )

        SectionHeader("Extended")
        ComparisonSection(
            darwinContent = {
                PrimaryButton(
                    text = "New note",
                    onClick = {},
                    leftIcon = { Icon(Lucide.Pencil) },
                )
            },
            materialContent = {
                ExtendedFloatingActionButton(
                    onClick = {},
                    icon = { Icon(Lucide.Pencil) },
                    text = { M3Text("New note") },
                )
            },
        )
    }
}
