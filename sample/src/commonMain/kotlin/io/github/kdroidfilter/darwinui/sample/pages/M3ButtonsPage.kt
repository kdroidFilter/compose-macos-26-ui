package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.AccentButton
import io.github.kdroidfilter.darwinui.components.DestructiveButton
import io.github.kdroidfilter.darwinui.components.HyperlinkButton
import io.github.kdroidfilter.darwinui.components.InfoButton
import io.github.kdroidfilter.darwinui.components.OutlineButton
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.SecondaryButton
import io.github.kdroidfilter.darwinui.components.SubtleButton
import io.github.kdroidfilter.darwinui.components.SuccessButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.WarningButton
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.Text as M3Text

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun M3ButtonsPage() {
    GalleryPage("Buttons", "Darwin convenience wrappers vs real Material 3 button components.") {
        SectionHeader("Variants")
        ComparisonSection(
            darwinContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PrimaryButton(text = "Primary", onClick = {})
                    SecondaryButton(text = "Secondary", onClick = {})
                    DestructiveButton(text = "Destructive", onClick = {})
                    AccentButton(text = "Accent", onClick = {})
                    SuccessButton(text = "Success", onClick = {})
                    WarningButton(text = "Warning", onClick = {})
                    InfoButton(text = "Info", onClick = {})
                    OutlineButton(text = "Outline", onClick = {})
                    SubtleButton(text = "Ghost", onClick = {})
                    HyperlinkButton(text = "Link", onClick = {})
                }
            },
            materialContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Button(onClick = {}) { M3Text("Filled") }
                    OutlinedButton(onClick = {}) { M3Text("Outlined") }
                    TextButton(onClick = {}) { M3Text("Text") }
                    ElevatedButton(onClick = {}) { M3Text("Elevated") }
                    FilledTonalButton(onClick = {}) { M3Text("Filled Tonal") }
                }
            },
        )

        SectionHeader("Disabled")
        ComparisonSection(
            darwinContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PrimaryButton(text = "Primary", onClick = {}, enabled = false)
                    SecondaryButton(text = "Secondary", onClick = {}, enabled = false)
                    DestructiveButton(text = "Destructive", onClick = {}, enabled = false)
                    OutlineButton(text = "Outline", onClick = {}, enabled = false)
                    SubtleButton(text = "Ghost", onClick = {}, enabled = false)
                }
            },
            materialContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Button(onClick = {}, enabled = false) { M3Text("Filled") }
                    OutlinedButton(onClick = {}, enabled = false) { M3Text("Outlined") }
                    TextButton(onClick = {}, enabled = false) { M3Text("Text") }
                    ElevatedButton(onClick = {}, enabled = false) { M3Text("Elevated") }
                    FilledTonalButton(onClick = {}, enabled = false) { M3Text("Filled Tonal") }
                }
            },
        )

        SectionHeader("Loading")
        ComparisonSection(
            darwinContent = {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PrimaryButton(text = "Loading", onClick = {}, loading = true)
                    PrimaryButton(text = "Saving...", onClick = {}, loading = true, loadingText = "Saving...")
                }
            },
            materialContent = {
                Text(
                    "No built-in loading state in M3 — use Darwin wrappers or add your own CircularProgressIndicator.",
                    style = io.github.kdroidfilter.darwinui.theme.DarwinTheme.typography.bodySmall,
                    color = io.github.kdroidfilter.darwinui.theme.DarwinTheme.colors.textTertiary,
                )
            },
        )
    }
}
