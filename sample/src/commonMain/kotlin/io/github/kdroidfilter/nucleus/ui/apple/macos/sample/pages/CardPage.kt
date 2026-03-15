package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Card
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CardContent
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CardDescription
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CardFooter
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CardHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CardTitle
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PushButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.CodeBlock
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.PreviewContainer
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

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
                color = MacosTheme.colorScheme.mutedForeground,
            )
        }
        CardFooter {
            PushButton(text = "Action", onClick = {})
            PushButton(text = "Cancel", onClick = {})
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
    CardFooter { PushButton(text = "Action", onClick = {}) }
}""")

        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.CardDefaultExample) { CardDefaultExample() }
    }
}
