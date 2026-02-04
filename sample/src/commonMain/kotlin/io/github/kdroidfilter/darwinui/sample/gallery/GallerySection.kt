package io.github.kdroidfilter.darwinui.sample.gallery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import io.github.kdroidfilter.darwinui.theme.DarwinDuration
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinTween

@Composable
fun GallerySection(
    title: String,
    sourceCode: String,
    content: @Composable () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(DarwinTheme.shapes.large)
            .border(
                width = 1.dp,
                color = DarwinTheme.colors.border,
                shape = DarwinTheme.shapes.large,
            )
            .background(DarwinTheme.colors.card),
    ) {
        // Section title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
        ) {
            DarwinText(
                text = title,
                style = DarwinTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = DarwinTheme.colors.textPrimary,
            )
        }

        // Separator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarwinTheme.colors.border)
                .defaultMinSize(minHeight = 1.dp),
        )

        // Preview area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 80.dp)
                .padding(16.dp),
        ) {
            content()
        }

        // Separator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarwinTheme.colors.border)
                .defaultMinSize(minHeight = 1.dp),
        )

        // Source Code toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DarwinText(
                text = if (expanded) "\u25BC" else "\u25B6",
                style = DarwinTheme.typography.labelSmall,
                color = DarwinTheme.colors.textTertiary,
            )
            DarwinText(
                text = "Source Code",
                style = DarwinTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = DarwinTheme.colors.textSecondary,
            )
        }

        // Expandable code block
        AnimatedVisibility(
            visible = expanded,
            enter = expandVertically(
                animationSpec = darwinTween(DarwinDuration.Normal),
            ) + fadeIn(
                animationSpec = darwinTween(DarwinDuration.Normal),
            ),
            exit = shrinkVertically(
                animationSpec = darwinTween(DarwinDuration.Normal),
            ) + fadeOut(
                animationSpec = darwinTween(DarwinDuration.Normal),
            ),
        ) {
            Box(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                CodeBlock(code = sourceCode)
            }
        }
    }
}
