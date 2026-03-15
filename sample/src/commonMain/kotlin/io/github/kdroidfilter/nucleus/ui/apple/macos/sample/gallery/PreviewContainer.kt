package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@Composable
fun PreviewContainer(content: @Composable () -> Unit) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(MacosTheme.shapes.extraLarge)
                .border(
                    width = 1.dp,
                    color = MacosTheme.colorScheme.border.copy(alpha = 0.60f), // border-border/60
                    shape = MacosTheme.shapes.extraLarge,
                ).background(MacosTheme.colorScheme.muted.copy(alpha = 0.20f)),
        // bg-muted/20
    ) {
        Text(
            text = "Preview",
            style = MacosTheme.typography.caption1,
            fontWeight = FontWeight.Medium,
            color = MacosTheme.colorScheme.textTertiary,
            modifier = Modifier.padding(start = 24.dp, top = 20.dp),
        )
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 100.dp)
                    .padding(24.dp),
            contentAlignment = Alignment.Center,
        ) {
            content()
        }
    }
}
