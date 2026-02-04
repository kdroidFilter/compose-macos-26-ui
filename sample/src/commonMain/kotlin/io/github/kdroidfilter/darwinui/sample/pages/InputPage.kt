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
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.input.DarwinTextField
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
        var successText by remember { mutableStateOf("Valid input") }
        DarwinTextField(value = text, onValueChange = { text = it }, placeholder = "Type something...")
        DarwinTextField(value = errorText, onValueChange = { errorText = it }, placeholder = "Error state", isError = true)
        DarwinTextField(value = successText, onValueChange = { successText = it }, placeholder = "Success state", isSuccess = true)
    }
}

@GalleryExample("Input", "Default")
@Composable
fun InputDefaultExample() {
    var text by remember { mutableStateOf("") }
    DarwinTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = "Enter your name...",
        label = "Name",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("Input", "Error State")
@Composable
fun InputErrorExample() {
    var text by remember { mutableStateOf("") }
    DarwinTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = "Required field",
        label = "Email",
        isError = true,
        supportingText = "This field is required",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("Input", "Success State")
@Composable
fun InputSuccessExample() {
    var text by remember { mutableStateOf("valid@email.com") }
    DarwinTextField(
        value = text,
        onValueChange = { text = it },
        label = "Verified email",
        isSuccess = true,
        supportingText = "Email verified",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("Input", "With Label")
@Composable
fun InputWithLabelExample() {
    var text by remember { mutableStateOf("") }
    DarwinTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = "you@example.com",
        label = "Email address",
        supportingText = "We'll never share your email.",
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@GalleryExample("Input", "Password")
@Composable
fun InputPasswordExample() {
    var password by remember { mutableStateOf("") }
    DarwinTextField(
        value = password,
        onValueChange = { password = it },
        placeholder = "Password",
        label = "Password",
        password = true,
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@Composable
internal fun InputPage() {
    GalleryPage("Input", "A text input field with label, validation states, and password support.") {
        PreviewContainer { InputPreview() }

        SectionHeader("Usage")
        CodeBlock("""var text by remember { mutableStateOf("") }
DarwinTextField(
    value = text,
    onValueChange = { text = it },
    placeholder = "Enter text...",
    label = "Label",
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
            title = "Success State",
            description = "Input with success validation",
            sourceCode = GallerySources.InputSuccessExample,
        ) { InputSuccessExample() }
        ExampleCard(
            title = "Password",
            description = "Password input with visibility toggle",
            sourceCode = GallerySources.InputPasswordExample,
        ) { InputPasswordExample() }
    }
}
