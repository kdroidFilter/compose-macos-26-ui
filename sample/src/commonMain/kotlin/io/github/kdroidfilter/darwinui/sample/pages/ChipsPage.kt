package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Star
import io.github.kdroidfilter.darwinui.components.AssistChip
import io.github.kdroidfilter.darwinui.components.FilterChip
import io.github.kdroidfilter.darwinui.components.InputChip
import io.github.kdroidfilter.darwinui.components.SuggestionChip
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Chips", "All Types")
@Composable
fun ChipsAllTypesExample() {
    var filterSelected by remember { mutableStateOf(false) }
    var inputSelected by remember { mutableStateOf(true) }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AssistChip(onClick = {}, label = { Text("Assist") })
        AssistChip(
            onClick = {},
            label = { Text("With icon") },
            leadingIcon = { Icon(Lucide.Star) },
        )
        FilterChip(
            selected = filterSelected,
            onClick = { filterSelected = !filterSelected },
            label = { Text("Filter") },
        )
        InputChip(
            selected = inputSelected,
            onClick = { inputSelected = !inputSelected },
            label = { Text("Input") },
        )
        SuggestionChip(onClick = {}, label = { Text("Suggestion") })
    }
}

@OptIn(ExperimentalLayoutApi::class)
@GalleryExample("Chips", "Filter Group")
@Composable
fun ChipsFilterGroupExample() {
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
                label = { Text(option) },
            )
        }
    }
}

@Composable
internal fun ChipsPage() {
    GalleryPage("Chips", "Compact elements that represent an input, attribute, or action.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "All Types",
            description = "AssistChip, FilterChip, InputChip, SuggestionChip",
            sourceCode = GallerySources.ChipsAllTypesExample,
        ) { ChipsAllTypesExample() }
        ExampleCard(
            title = "Filter Group",
            description = "Mutually exclusive filter chips",
            sourceCode = GallerySources.ChipsFilterGroupExample,
        ) { ChipsFilterGroupExample() }
    }
}
