package io.github.kdroidfilter.darwinui.sample.gallery

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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

private enum class ExampleTab { Preview, Code }

@Composable
fun ExampleCard(
    title: String,
    description: String = "",
    sourceCode: String,
    content: @Composable () -> Unit,
) {
    var activeTab by remember { mutableStateOf(ExampleTab.Preview) }

    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(DarwinTheme.shapes.large)
                .border(
                    width = 1.dp,
                    color = DarwinTheme.colorScheme.border,
                    shape = DarwinTheme.shapes.large,
                ).background(DarwinTheme.colorScheme.card),
    ) {
        // Header: title + description + tabs
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // Title + description
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = title,
                    style = DarwinTheme.typography.caption1,
                    fontWeight = FontWeight.SemiBold,
                    color = DarwinTheme.colorScheme.textPrimary,
                )
                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        style = DarwinTheme.typography.caption1,
                        color = DarwinTheme.colorScheme.textTertiary,
                    )
                }
            }

            // Tabs: Preview | Code
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                modifier =
                    Modifier
                        .clip(DarwinTheme.shapes.small)
                        .background(DarwinTheme.colorScheme.backgroundSubtle)
                        .padding(2.dp),
            ) {
                TabButton(
                    text = "Preview",
                    selected = activeTab == ExampleTab.Preview,
                    onClick = { activeTab = ExampleTab.Preview },
                )
                TabButton(
                    text = "Code",
                    selected = activeTab == ExampleTab.Code,
                    onClick = { activeTab = ExampleTab.Code },
                )
            }
        }

        // Separator
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(DarwinTheme.colorScheme.border)
                    .defaultMinSize(minHeight = 1.dp),
        )

        // Content area: Preview or Code
        AnimatedContent(
            targetState = activeTab,
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "exampleTab",
        ) { tab ->
            when (tab) {
                ExampleTab.Preview -> {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 80.dp)
                                .padding(16.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        content()
                    }
                }

                ExampleTab.Code -> {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                    ) {
                        CodeBlock(code = sourceCode)
                    }
                }
            }
        }
    }
}

@Composable
private fun TabButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .clip(DarwinTheme.shapes.extraSmall)
                .then(
                    if (selected) {
                        Modifier.background(DarwinTheme.colorScheme.card)
                    } else {
                        Modifier
                    },
                ).clickable(onClick = onClick)
                .padding(horizontal = 12.dp, vertical = 6.dp),
    ) {
        Text(
            text = text,
            style = DarwinTheme.typography.caption2,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
            color = if (selected) DarwinTheme.colorScheme.textPrimary else DarwinTheme.colorScheme.textTertiary,
        )
    }
}
