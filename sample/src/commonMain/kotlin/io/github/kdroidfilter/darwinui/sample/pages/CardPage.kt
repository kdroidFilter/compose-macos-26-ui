package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.PrimaryButton
import io.github.kdroidfilter.darwinui.components.SubtleButton
import io.github.kdroidfilter.darwinui.components.Card
import io.github.kdroidfilter.darwinui.components.CardContent
import io.github.kdroidfilter.darwinui.components.CardDescription
import io.github.kdroidfilter.darwinui.components.CardFooter
import io.github.kdroidfilter.darwinui.components.CardHeader
import io.github.kdroidfilter.darwinui.components.CardTitle
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.CodeBlock
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.PreviewContainer
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Card", "Default")
@Composable
fun CardDefaultExample() {

    Card(modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth()) {
        CardHeader {
            CardTitle { Text("Card Title") }
            CardDescription { Text("This is a description of the card content.") }
        }
        CardContent {
            Text(
                "Cards can contain any content including text, images, and other components.",
                color = DarwinTheme.colors.mutedForeground,
            )
        }
        CardFooter {
            PrimaryButton(text = "Action", onClick = {})
            SubtleButton(text = "Cancel", onClick = {})
        }
    }
}

@Composable
internal fun CardPage() {
    GalleryPage("Card", "Displays a card with header, content, and footer.") {
        PreviewContainer { CardDefaultExample() }

        SectionHeader("Usage")
        CodeBlock("""Card {
    CardHeader {
        CardTitle { Text("Title") }
        CardDescription { Text("Description") }
    }
    CardContent { Text("Content") }
    CardFooter { PrimaryButton(text = "Action", onClick = {}) }
}""")

        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.CardDefaultExample) { CardDefaultExample() }
    }
}
