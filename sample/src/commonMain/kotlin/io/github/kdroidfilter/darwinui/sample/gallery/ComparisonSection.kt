package io.github.kdroidfilter.darwinui.sample.gallery

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@Composable
fun ComparisonSection(
    darwinContent: @Composable ColumnScope.() -> Unit,
    materialContent: (@Composable ColumnScope.() -> Unit)? = null,
    sourceCode: String = "",
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        if (materialContent != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                ComparisonPane(
                    title = "Darwin",
                    modifier = Modifier.weight(1f),
                    content = darwinContent,
                )
                ComparisonPane(
                    title = "Material 3",
                    modifier = Modifier.weight(1f),
                    content = materialContent,
                )
            }
        } else {
            ComparisonPane(
                title = "Darwin",
                modifier = Modifier.fillMaxWidth(),
                content = darwinContent,
            )
        }

        if (sourceCode.isNotEmpty()) {
            var expanded by remember { mutableStateOf(false) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .clip(DarwinTheme.shapes.large)
                    .border(1.dp, DarwinTheme.colorScheme.border, DarwinTheme.shapes.large)
                    .background(DarwinTheme.colorScheme.card),
            ) {
                // Toggle header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                        .background(DarwinTheme.colorScheme.backgroundSubtle)
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Darwin Code",
                        style = DarwinTheme.typography.caption1,
                        fontWeight = FontWeight.SemiBold,
                        color = DarwinTheme.colorScheme.textSecondary,
                    )
                    Text(
                        text = if (expanded) "Hide" else "Show",
                        style = DarwinTheme.typography.caption2,
                        color = DarwinTheme.colorScheme.textTertiary,
                    )
                }

                AnimatedVisibility(
                    visible = expanded,
                    enter = expandVertically(),
                    exit = shrinkVertically(),
                ) {
                    Column {
                        // Separator
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(DarwinTheme.colorScheme.border)
                                .defaultMinSize(minHeight = 1.dp),
                        )
                        Box(modifier = Modifier.padding(12.dp)) {
                            CodeBlock(code = sourceCode)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ComparisonPane(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .clip(DarwinTheme.shapes.large)
            .border(1.dp, DarwinTheme.colorScheme.border, DarwinTheme.shapes.large)
            .background(DarwinTheme.colorScheme.card),
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarwinTheme.colorScheme.backgroundSubtle)
                .padding(horizontal = 16.dp, vertical = 10.dp),
        ) {
            Text(
                text = title,
                style = DarwinTheme.typography.caption1,
                fontWeight = FontWeight.SemiBold,
                color = DarwinTheme.colorScheme.textSecondary,
            )
        }
        // Separator
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarwinTheme.colorScheme.border)
                .defaultMinSize(minHeight = 1.dp),
        )
        // Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 80.dp)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = content,
        )
    }
}
