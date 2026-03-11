package io.github.kdroidfilter.darwinui.sample.pages

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.TextArea
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("TextArea", "Default")
@Composable
fun TextAreaDefaultExample() {
    val maxChars = 200
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth(0.5f)) {
        TextArea(
            value = text,
            onValueChange = { if (it.length <= maxChars) text = it },
            placeholder = "Write your message here...",
            modifier = Modifier.fillMaxWidth(),
        )
        Row(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, end = 2.dp), horizontalArrangement = Arrangement.End) {
            Text(
                text = "${text.length}/$maxChars",
                style = DarwinTheme.typography.bodySmall,
                color = if (text.length >= maxChars) DarwinTheme.colors.destructive else DarwinTheme.colors.textTertiary,
            )
        }
    }
}

@GalleryExample("TextArea", "Error State")
@Composable
fun TextAreaErrorExample() {
    var text by remember { mutableStateOf("") }
    TextArea(
        value = text,
        onValueChange = { text = it },
        placeholder = "Error state textarea",
        isError = true,
        minLines = 2,
        maxLines = 2,
        modifier = Modifier.fillMaxWidth(0.5f),
    )
}

@Composable
internal fun TextAreaPage() {
    GalleryPage("Textarea", "A multi-line text input for longer form content.") {
        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.TextAreaDefaultExample) { TextAreaDefaultExample() }
        ExampleCard(title = "Error State", sourceCode = GallerySources.TextAreaErrorExample) { TextAreaErrorExample() }
    }
}
