package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MacosTheme.typography.headline,
        fontWeight = FontWeight.SemiBold,
        color = MacosTheme.colorScheme.textPrimary,
    )
}
