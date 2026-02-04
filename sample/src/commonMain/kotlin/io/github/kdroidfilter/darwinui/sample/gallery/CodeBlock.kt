package io.github.kdroidfilter.darwinui.sample.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
fun CodeBlock(code: String) {
    SelectionContainer {
        BasicText(
            text = code,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = DarwinTheme.typography.bodySmall.fontSize,
                color = DarwinTheme.colors.textSecondary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clip(DarwinTheme.shapes.medium)
                .background(DarwinTheme.colors.backgroundSubtle)
                .horizontalScroll(rememberScrollState())
                .padding(16.dp),
        )
    }
}
