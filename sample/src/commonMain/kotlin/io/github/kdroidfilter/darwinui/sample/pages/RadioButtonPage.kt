package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.CheckBox
import io.github.kdroidfilter.darwinui.components.RadioButton as DarwinRadioButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.sample.gallery.ComparisonSection
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import androidx.compose.material3.Text as M3Text

@Composable
internal fun RadioButtonPage() {
    GalleryPage("Radio Button", "Darwin RadioButton (iOS-style) vs Material 3 RadioButton.") {
        SectionHeader("Group Selection")
        ComparisonSection(
            darwinContent = {
                val options = listOf("Option A", "Option B", "Option C")
                var selected by remember { mutableStateOf("Option A") }

                Column(
                    modifier = Modifier.selectableGroup(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            DarwinRadioButton(
                                selected = selected == option,
                                onClick = { selected = option },
                            )
                            Text(option)
                        }
                    }
                }
            },
            materialContent = {
                val options = listOf("Option A", "Option B", "Option C")
                var selected by remember { mutableStateOf("Option A") }

                Column(
                    modifier = Modifier.selectableGroup(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    options.forEach { option ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            RadioButton(
                                selected = selected == option,
                                onClick = { selected = option },
                            )
                            M3Text(option)
                        }
                    }
                }
            },
        )

        SectionHeader("Disabled")
        ComparisonSection(
            darwinContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        DarwinRadioButton(selected = true, onClick = {}, enabled = false)
                        Text("Selected (disabled)")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        DarwinRadioButton(selected = false, onClick = {}, enabled = false)
                        Text("Unselected (disabled)")
                    }
                }
            },
            materialContent = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        RadioButton(selected = true, onClick = {}, enabled = false)
                        M3Text("Selected (disabled)")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        RadioButton(selected = false, onClick = {}, enabled = false)
                        M3Text("Unselected (disabled)")
                    }
                }
            },
        )
    }
}
