package io.github.kdroidfilter.darwinui.sample.gallery

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = DarwinTheme.typography.headline,
        fontWeight = FontWeight.SemiBold,
        color = DarwinTheme.colorScheme.textPrimary,
    )
}
