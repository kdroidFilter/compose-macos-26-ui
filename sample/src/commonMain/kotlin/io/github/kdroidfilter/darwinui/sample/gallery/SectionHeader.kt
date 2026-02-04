package io.github.kdroidfilter.darwinui.sample.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
fun SectionHeader(title: String) {
    DarwinText(
        text = title,
        style = DarwinTheme.typography.headlineSmall,
        fontWeight = FontWeight.SemiBold,
        color = DarwinTheme.colors.textPrimary,
    )
}
