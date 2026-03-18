package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.BoldHighlight
import dev.snipme.highlights.model.ColorHighlight
import dev.snipme.highlights.model.SyntaxLanguage
import dev.snipme.highlights.model.SyntaxThemes
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideCheck
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.LucideCopy
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import kotlinx.coroutines.delay

@Suppress("DEPRECATION")
@Composable
fun CodeBlock(code: String) {
    val jetBrainsMono = JetBrainsMonoFontFamily()
    val clipboardManager = LocalClipboardManager.current
    var copied by remember { mutableStateOf(false) }
    val isDark = MacosTheme.colorScheme.isDark

    val highlightedCode =
        remember(code, isDark) {
            val highlights =
                Highlights
                    .Builder()
                    .code(code)
                    .language(SyntaxLanguage.KOTLIN)
                    .theme(SyntaxThemes.atom(darkMode = isDark))
                    .build()

            buildAnnotatedString {
                append(code)
                for (highlight in highlights.getHighlights()) {
                    when (highlight) {
                        is ColorHighlight -> {
                            addStyle(
                                style = SpanStyle(color = Color(highlight.rgb).copy(alpha = 1f)),
                                start = highlight.location.start,
                                end = highlight.location.end,
                            )
                        }

                        is BoldHighlight -> {
                            addStyle(
                                style = SpanStyle(fontWeight = FontWeight.Bold),
                                start = highlight.location.start,
                                end = highlight.location.end,
                            )
                        }
                    }
                }
            }
        }

    LaunchedEffect(copied) {
        if (copied) {
            delay(2000)
            copied = false
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(MacosTheme.shapes.medium)
                .background(MacosTheme.colorScheme.backgroundSubtle),
    ) {
        // Header with language label and copy button
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "KOTLIN",
                style = MacosTheme.typography.caption2,
                color = MacosTheme.colorScheme.textTertiary,
                fontWeight = FontWeight.Medium,
            )

            Row(
                modifier =
                    Modifier
                        .clip(MacosTheme.shapes.small)
                        .clickable {
                            clipboardManager.setText(AnnotatedString(code))
                            copied = true
                        }.padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimatedContent(
                    targetState = copied,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "copyIcon",
                ) { isCopied ->
                    Image(
                        imageVector = if (isCopied) LucideCheck else LucideCopy,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        colorFilter =
                            ColorFilter.tint(
                                if (isCopied) {
                                    MacosTheme.colorScheme.success
                                } else {
                                    MacosTheme.colorScheme.textTertiary
                                },
                            ),
                    )
                }
                AnimatedContent(
                    targetState = copied,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "copyText",
                ) { isCopied ->
                    Text(
                        text = if (isCopied) "Copied!" else "Copy",
                        style = MacosTheme.typography.caption2,
                        fontWeight = FontWeight.Medium,
                        color =
                            if (isCopied) {
                                MacosTheme.colorScheme.success
                            } else {
                                MacosTheme.colorScheme.textTertiary
                            },
                    )
                }
            }
        }

        // Code content with syntax highlighting
        SelectionContainer {
            BasicText(
                text = highlightedCode,
                style =
                    TextStyle(
                        fontFamily = jetBrainsMono,
                        fontSize = MacosTheme.typography.caption1.fontSize,
                        color = MacosTheme.colorScheme.textSecondary,
                    ),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            )
        }
    }
}
