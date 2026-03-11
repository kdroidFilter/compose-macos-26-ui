package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.InputChip
import androidx.compose.material3.SuggestionChip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Star
import io.github.kdroidfilter.darwinui.components.AssistChip as DarwinAssistChip
import io.github.kdroidfilter.darwinui.components.Badge
import io.github.kdroidfilter.darwinui.components.BadgeVariant
import io.github.kdroidfilter.darwinui.components.FilterChip as DarwinFilterChip
import io.github.kdroidfilter.darwinui.components.InputChip as DarwinInputChip
import io.github.kdroidfilter.darwinui.components.SuggestionChip as DarwinSuggestionChip
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import androidx.compose.material3.Text as M3Text

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ChipsPage() {
    GalleryPage("Chips", "Darwin chip components vs Material 3 chip components.") {
        SectionHeader("All Types")
        ComparisonSection(
            darwinContent = {
                var filterSelected by remember { mutableStateOf(false) }
                var inputSelected by remember { mutableStateOf(true) }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    DarwinAssistChip(onClick = {}, label = { Text("Assist") })
                    DarwinAssistChip(
                        onClick = {},
                        label = { Text("With icon") },
                        leadingIcon = { Icon(Lucide.Star) },
                    )
                    DarwinFilterChip(
                        selected = filterSelected,
                        onClick = { filterSelected = !filterSelected },
                        label = { Text("Filter") },
                    )
                    DarwinInputChip(
                        selected = inputSelected,
                        onClick = { inputSelected = !inputSelected },
                        label = { Text("Input") },
                    )
                    DarwinSuggestionChip(onClick = {}, label = { Text("Suggestion") })
                }
            },
            materialContent = {
                var filterSelected by remember { mutableStateOf(false) }
                var inputSelected by remember { mutableStateOf(true) }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    AssistChip(onClick = {}, label = { M3Text("Assist") })
                    AssistChip(
                        onClick = {},
                        label = { M3Text("With icon") },
                        leadingIcon = { Icon(Lucide.Star) },
                    )
                    FilterChip(
                        selected = filterSelected,
                        onClick = { filterSelected = !filterSelected },
                        label = { M3Text("Filter") },
                    )
                    InputChip(
                        selected = inputSelected,
                        onClick = { inputSelected = !inputSelected },
                        label = { M3Text("Input") },
                    )
                    SuggestionChip(onClick = {}, label = { M3Text("Suggestion") })
                }
            },
        )

        SectionHeader("Filter Group")
        ComparisonSection(
            darwinContent = {
                val options = listOf("All", "Active", "Paused", "Archived")
                var selected by remember { mutableStateOf("All") }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    options.forEach { option ->
                        DarwinFilterChip(
                            selected = selected == option,
                            onClick = { selected = option },
                            label = { Text(option) },
                        )
                    }
                }
            },
            materialContent = {
                val options = listOf("All", "Active", "Paused", "Archived")
                var selected by remember { mutableStateOf("All") }

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    options.forEach { option ->
                        FilterChip(
                            selected = selected == option,
                            onClick = { selected = option },
                            label = { M3Text(option) },
                        )
                    }
                }
            },
        )
    }
}
