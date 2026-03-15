package io.github.kdroidfilter.nucleus.ui.apple.macos.markdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.mikepenz.markdown.model.DefaultMarkdownTypography
import com.mikepenz.markdown.model.MarkdownTypography
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@Composable
fun markdownTypography(
    h1: TextStyle = MacosTheme.typography.title1,
    h2: TextStyle = MacosTheme.typography.title2,
    h3: TextStyle = MacosTheme.typography.title3,
    h4: TextStyle = MacosTheme.typography.headline,
    h5: TextStyle = MacosTheme.typography.subheadline.copy(fontWeight = FontWeight.SemiBold),
    h6: TextStyle = MacosTheme.typography.footnote.copy(fontWeight = FontWeight.SemiBold),
    text: TextStyle = MacosTheme.typography.subheadline,
    code: TextStyle = MacosTheme.typography.footnote.copy(fontFamily = FontFamily.Monospace),
    inlineCode: TextStyle = text.copy(fontFamily = FontFamily.Monospace),
    quote: TextStyle = MacosTheme.typography.subheadline.plus(SpanStyle(fontStyle = FontStyle.Italic)),
    paragraph: TextStyle = MacosTheme.typography.subheadline,
    ordered: TextStyle = MacosTheme.typography.subheadline,
    bullet: TextStyle = MacosTheme.typography.subheadline,
    list: TextStyle = MacosTheme.typography.subheadline,
    textLink: TextLinkStyles = TextLinkStyles(
        style = MacosTheme.typography.subheadline.copy(
            color = MacosTheme.colorScheme.accent,
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.Underline,
        ).toSpanStyle(),
    ),
    table: TextStyle = text,
): MarkdownTypography = DefaultMarkdownTypography(
    h1 = h1,
    h2 = h2,
    h3 = h3,
    h4 = h4,
    h5 = h5,
    h6 = h6,
    text = text,
    quote = quote,
    code = code,
    inlineCode = inlineCode,
    paragraph = paragraph,
    ordered = ordered,
    bullet = bullet,
    list = list,
    textLink = textLink,
    table = table,
)
