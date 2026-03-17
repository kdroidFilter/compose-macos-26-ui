package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TextArea
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TextFieldDefaults
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@GalleryExample("TextArea", "Default")
@Composable
fun TextAreaDefaultExample() {
    val maxChars = 200
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth(0.5f)) {
        TextArea(
            value = text,
            onValueChange = { if (it.length <= maxChars) text = it },
            placeholder = { Text("Write your message here...") },
            modifier = Modifier.fillMaxWidth(),
        )
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, end = 2.dp), horizontalArrangement = Arrangement.End) {
            Text(
                text = "${text.length}/$maxChars",
                style = MacosTheme.typography.caption1,
                color = if (text.length >= maxChars) MacosTheme.colorScheme.destructive else MacosTheme.colorScheme.textTertiary,
            )
        }
    }
}

@GalleryExample("TextArea", "With Label")
@Composable
fun TextAreaWithLabelExample() {
    var text by remember { mutableStateOf("") }
    TextArea(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Describe the issue...") },
        label = { Text("Description") },
        supportingText = { Text("Provide as much detail as possible.") },
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("TextArea", "Error State")
@Composable
fun TextAreaErrorExample() {
    var text by remember { mutableStateOf("") }
    TextArea(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Required field") },
        label = { Text("Notes") },
        isError = true,
        supportingText = { Text("This field is required") },
        minLines = 2,
        maxLines = 4,
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("TextArea", "Disabled")
@Composable
fun TextAreaDisabledExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth(0.5f)) {
        TextArea(
            value = "",
            onValueChange = {},
            placeholder = { Text("Empty disabled") },
            enabled = false,
            minLines = 2,
            maxLines = 2,
        )
        TextArea(
            value = "This content cannot be edited.",
            onValueChange = {},
            enabled = false,
            minLines = 2,
            maxLines = 2,
        )
    }
}

@GalleryExample("TextArea", "Surface Variants")
@Composable
fun TextAreaSurfaceVariantsExample() {
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
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    var text by remember { mutableStateOf("") }
                    TextArea(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = { Text("Placeholder") },
                        minLines = 2,
                        maxLines = 4,
                    )
                    TextArea(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Disabled") },
                        enabled = false,
                        minLines = 2,
                        maxLines = 2,
                    )
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
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    var text by remember { mutableStateOf("") }
                    TextArea(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = { Text("Placeholder") },
                        minLines = 2,
                        maxLines = 4,
                    )
                    TextArea(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Disabled") },
                        enabled = false,
                        minLines = 2,
                        maxLines = 2,
                    )
                }
            }
        }
    }
}

@GalleryExample("TextArea", "Custom Colors")
@Composable
fun TextAreaCustomColorsExample() {
    val accent = MacosTheme.colorScheme.accent
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth(0.5f)) {
        var t1 by remember { mutableStateOf("") }
        TextArea(
            value = t1,
            onValueChange = { t1 = it },
            placeholder = { Text("Accent-tinted textarea") },
            label = { Text("Accent") },
            minLines = 2,
            maxLines = 4,
            colors = TextFieldDefaults.colors(
                backgroundColor = accent.copy(alpha = 0.10f),
                borderColor = accent.copy(alpha = 0.30f),
                cursorColor = accent,
                labelColor = accent,
            ),
        )
        var t2 by remember { mutableStateOf("") }
        TextArea(
            value = t2,
            onValueChange = { t2 = it },
            placeholder = { Text("Green textarea") },
            label = { Text("Green") },
            minLines = 2,
            maxLines = 4,
            colors = TextFieldDefaults.colors(
                backgroundColor = Color(0xFF34C759).copy(alpha = 0.10f),
                borderColor = Color(0xFF34C759).copy(alpha = 0.30f),
                textColor = Color(0xFF34C759),
                labelColor = Color(0xFF34C759),
                cursorColor = Color(0xFF34C759),
            ),
        )
    }
}

@Composable
internal fun TextAreaPage() {
    GalleryPage("Textarea", "A multi-line text input for longer form content.") {
        SectionHeader("Examples")
        ExampleCard(
            title = "Default",
            description = "TextArea with character counter",
            sourceCode = GallerySources.TextAreaDefaultExample,
        ) { TextAreaDefaultExample() }
        ExampleCard(
            title = "With Label",
            description = "TextArea with label and supporting text",
            sourceCode = GallerySources.TextAreaWithLabelExample,
        ) { TextAreaWithLabelExample() }
        ExampleCard(
            title = "Error State",
            description = "TextArea with error validation",
            sourceCode = GallerySources.TextAreaErrorExample,
        ) { TextAreaErrorExample() }
        ExampleCard(
            title = "Disabled",
            description = "Disabled text areas with and without value",
            sourceCode = GallerySources.TextAreaDisabledExample,
        ) { TextAreaDisabledExample() }

        SectionHeader("Custom Colors")
        ExampleCard(
            title = "Custom Colors",
            description = "Text areas with custom background, border, text and label colors",
            sourceCode = GallerySources.TextAreaCustomColorsExample,
        ) { TextAreaCustomColorsExample() }

        SectionHeader("Surface Variants")
        ExampleCard(
            title = "Content Area vs Over Glass",
            description = "Side-by-side comparison of surface variants",
            sourceCode = GallerySources.TextAreaSurfaceVariantsExample,
        ) { TextAreaSurfaceVariantsExample() }
    }
}
