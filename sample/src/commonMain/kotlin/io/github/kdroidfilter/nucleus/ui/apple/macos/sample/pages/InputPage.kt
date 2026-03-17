package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TextField
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TextFieldDefaults
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideSearch
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideInfo
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideCircleCheck
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideTriangleAlert
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.Surface
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.CodeBlock
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.PreviewContainer
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources

@Composable
private fun InputPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth(0.5f)) {
        var text by remember { mutableStateOf("") }
        var errorText by remember { mutableStateOf("") }
        TextField(value = text, onValueChange = { text = it }, placeholder = { Text("Type something...") })
        TextField(value = errorText, onValueChange = { errorText = it }, placeholder = { Text("Error state") }, isError = true)
    }
}

@GalleryExample("Input", "Sizes")
@Composable
fun InputSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (size in ControlSize.entries) {
            ControlSize(size) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(0.6f),
                ) {
                    Text(
                        text = size.name,
                        style = MacosTheme.typography.caption1,
                        color = MacosTheme.colorScheme.textSecondary,
                        modifier = Modifier.widthIn(min = 72.dp),
                    )
                    var text by remember { mutableStateOf("") }
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = { Text("Enter text...") },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@GalleryExample("Input", "Default")
@Composable
fun InputDefaultExample() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Enter your name...") },
        label = { Text("Name") },
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("Input", "Error State")
@Composable
fun InputErrorExample() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Required field") },
        label = { Text("Email") },
        isError = true,
        supportingText = { Text("This field is required") },
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("Input", "With Label")
@Composable
fun InputWithLabelExample() {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("you@example.com") },
        label = { Text("Email address") },
        supportingText = { Text("We'll never share your email.") },
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("Input", "Password")
@Composable
fun InputPasswordExample() {
    var password by remember { mutableStateOf("") }
    TextField(
        value = password,
        onValueChange = { password = it },
        placeholder = { Text("Password") },
        label = { Text("Password") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("Input", "With Icons")
@Composable
fun InputWithIconsExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth(0.5f)) {
        var search by remember { mutableStateOf("") }
        TextField(
            value = search,
            onValueChange = { search = it },
            placeholder = { Text("Search...") },
            leadingIcon = { Icon(LucideSearch, modifier = Modifier.size(14.dp)) },
        )
        var info by remember { mutableStateOf("") }
        TextField(
            value = info,
            onValueChange = { info = it },
            placeholder = { Text("With trailing icon") },
            label = { Text("Info") },
            trailingIcon = { Icon(LucideInfo, modifier = Modifier.size(14.dp)) },
        )
        var both by remember { mutableStateOf("") }
        TextField(
            value = both,
            onValueChange = { both = it },
            placeholder = { Text("Both icons") },
            leadingIcon = { Icon(LucideSearch, modifier = Modifier.size(14.dp)) },
            trailingIcon = { Icon(LucideCircleCheck, modifier = Modifier.size(14.dp)) },
        )
    }
}

@GalleryExample("Input", "Disabled")
@Composable
fun InputDisabledExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth(0.5f)) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Empty disabled") },
            enabled = false,
        )
        TextField(
            value = "Disabled with value",
            onValueChange = {},
            enabled = false,
        )
    }
}

@GalleryExample("Input", "Surface Variants")
@Composable
fun InputSurfaceVariantsExample() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        // Content Area (default)
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
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = { Text("Placeholder") },
                    )
                    TextField(value = "With value", onValueChange = {})
                    TextField(value = "", onValueChange = {}, placeholder = { Text("Disabled") }, enabled = false)
                }
            }
        }

        // Over Glass
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
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = { Text("Placeholder") },
                    )
                    TextField(value = "With value", onValueChange = {})
                    TextField(value = "", onValueChange = {}, placeholder = { Text("Disabled") }, enabled = false)
                }
            }
        }
    }
}

@GalleryExample("Input", "Custom Colors")
@Composable
fun InputCustomColorsExample() {
    val accent = MacosTheme.colorScheme.accent
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth(0.5f)) {
        var q1 by remember { mutableStateOf("") }
        TextField(
            value = q1,
            onValueChange = { q1 = it },
            placeholder = { Text("Accent-tinted input") },
            label = { Text("Accent") },
            colors = TextFieldDefaults.colors(
                backgroundColor = accent.copy(alpha = 0.10f),
                borderColor = accent.copy(alpha = 0.30f),
                cursorColor = accent,
                labelColor = accent,
            ),
        )
        var q2 by remember { mutableStateOf("") }
        TextField(
            value = q2,
            onValueChange = { q2 = it },
            placeholder = { Text("Green input") },
            label = { Text("Green") },
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
internal fun InputPage() {
    GalleryPage("Input", "A text input field with label, validation states, and password support.") {
        PreviewContainer { InputPreview() }

        SectionHeader("Usage")
        CodeBlock("""var text by remember { mutableStateOf("") }
TextField(
    value = text,
    onValueChange = { text = it },
    placeholder = { Text("Enter text...") },
    label = { Text("Label") },
)""")

        SectionHeader("Sizes")
        ExampleCard(
            title = "All Sizes",
            description = "TextField at each ControlSize level",
            sourceCode = GallerySources.InputSizesExample,
        ) { InputSizesExample() }

        SectionHeader("Examples")
        ExampleCard(
            title = "With Label",
            description = "Input with label and helper text",
            sourceCode = GallerySources.InputWithLabelExample,
        ) { InputWithLabelExample() }
        ExampleCard(
            title = "Default",
            sourceCode = GallerySources.InputDefaultExample,
        ) { InputDefaultExample() }
        ExampleCard(
            title = "Error State",
            description = "Input with error validation",
            sourceCode = GallerySources.InputErrorExample,
        ) { InputErrorExample() }
        ExampleCard(
            title = "Password",
            description = "Password input with visibility toggle",
            sourceCode = GallerySources.InputPasswordExample,
        ) { InputPasswordExample() }
        ExampleCard(
            title = "Disabled",
            description = "Disabled text fields with and without value",
            sourceCode = GallerySources.InputDisabledExample,
        ) { InputDisabledExample() }

        SectionHeader("Icons")
        ExampleCard(
            title = "Leading & Trailing Icons",
            description = "Text fields with leading icon, trailing icon, or both",
            sourceCode = GallerySources.InputWithIconsExample,
        ) { InputWithIconsExample() }

        SectionHeader("Custom Colors")
        ExampleCard(
            title = "Custom Colors",
            description = "Text fields with custom background, border, text and label colors",
            sourceCode = GallerySources.InputCustomColorsExample,
        ) { InputCustomColorsExample() }

        SectionHeader("Surface Variants")
        ExampleCard(
            title = "Content Area vs Over Glass",
            description = "Side-by-side comparison of surface variants",
            sourceCode = GallerySources.InputSurfaceVariantsExample,
        ) { InputSurfaceVariantsExample() }
    }
}
