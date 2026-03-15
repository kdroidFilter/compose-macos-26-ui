package io.github.kdroidfilter.nucleus.ui.apple.macos.markdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.MarkdownColors
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@Composable
fun markdownColor(
    text: Color = MacosTheme.colorScheme.textPrimary,
    codeBackground: Color = MacosTheme.colorScheme.surfaceContainerHigh,
    inlineCodeBackground: Color = codeBackground,
    dividerColor: Color = MacosTheme.colorScheme.outlineVariant,
    tableBackground: Color = MacosTheme.colorScheme.surfaceContainerLow,
): MarkdownColors = DefaultMarkdownColors(
    text = text,
    codeBackground = codeBackground,
    inlineCodeBackground = inlineCodeBackground,
    dividerColor = dividerColor,
    tableBackground = tableBackground,
)
