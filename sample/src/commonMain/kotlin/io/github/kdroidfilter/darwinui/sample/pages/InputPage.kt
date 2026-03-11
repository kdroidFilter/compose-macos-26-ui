package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TextField
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.CodeBlock
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources

@Composable
private fun InputPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth(0.5f)) {
        var text by remember { mutableStateOf("") }
        var errorText by remember { mutableStateOf("") }
        TextField(value = text, onValueChange = { text = it }, placeholder = { Text("Type something...") })
        TextField(value = errorText, onValueChange = { errorText = it }, placeholder = { Text("Error state") }, isError = true)
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
    }
}
