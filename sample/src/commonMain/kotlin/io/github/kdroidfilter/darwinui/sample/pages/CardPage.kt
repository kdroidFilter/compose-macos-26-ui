package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.button.DarwinButton
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonSize
import io.github.kdroidfilter.darwinui.components.button.DarwinButtonVariant
import io.github.kdroidfilter.darwinui.components.card.DarwinCard
import io.github.kdroidfilter.darwinui.components.card.DarwinCardContent
import io.github.kdroidfilter.darwinui.components.card.DarwinCardDescription
import io.github.kdroidfilter.darwinui.components.card.DarwinCardFooter
import io.github.kdroidfilter.darwinui.components.card.DarwinCardHeader
import io.github.kdroidfilter.darwinui.components.card.DarwinCardTitle
import io.github.kdroidfilter.darwinui.components.text.DarwinText
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

    DarwinCard(modifier = Modifier.widthIn(max = 384.dp).fillMaxWidth()) {
        DarwinCardHeader {
            DarwinCardTitle { DarwinText("Card Title") }
            DarwinCardDescription { DarwinText("This is a description of the card content.") }
        }
        DarwinCardContent {
            DarwinText(
                "Cards can contain any content including text, images, and other components.",
                color = DarwinTheme.colors.mutedForeground,
            )
        }
        DarwinCardFooter {
            DarwinButton(text = "Action", onClick = {}, variant = DarwinButtonVariant.Primary, size = DarwinButtonSize.Small)
            DarwinButton(text = "Cancel", onClick = {}, variant = DarwinButtonVariant.Ghost, size = DarwinButtonSize.Small)
        }
    }
}

@Composable
internal fun CardPage() {
    GalleryPage("Card", "Displays a card with header, content, and footer.") {
        PreviewContainer { CardDefaultExample() }

        SectionHeader("Usage")
        CodeBlock("""DarwinCard {
    DarwinCardHeader {
        DarwinCardTitle { DarwinText("Title") }
        DarwinCardDescription { DarwinText("Description") }
    }
    DarwinCardContent { DarwinText("Content") }
    DarwinCardFooter { DarwinButton(text = "Action", onClick = {}) }
}""")

        SectionHeader("Examples")
        ExampleCard(title = "Default", sourceCode = GallerySources.CardDefaultExample) { CardDefaultExample() }
    }
}
