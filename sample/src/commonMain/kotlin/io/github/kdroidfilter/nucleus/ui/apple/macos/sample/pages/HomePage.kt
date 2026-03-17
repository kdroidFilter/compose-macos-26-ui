package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.icons.lucide.Github
import com.composables.icons.lucide.Lucide
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Badge
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.BadgeVariant
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Card
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.CardContent
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PushButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.icons.Icon
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.NucleusAtom
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun HomePage(onNavigate: (String) -> Unit) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Logo
        NucleusAtom(atomSize = 180.dp)

        // Title block
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Nucleus UI",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MacosTheme.colorScheme.textPrimary,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Native Design System for Compose Multiplatform",
                style = MacosTheme.typography.title3,
                color = MacosTheme.colorScheme.textSecondary,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Badge(variant = BadgeVariant.Success) { Text("macOS") }
                Badge(variant = BadgeVariant.Default) { Text("GTK — Coming Soon") }
                Badge(variant = BadgeVariant.Default) { Text("iOS — Coming Soon") }
                Badge(variant = BadgeVariant.Info) { Text("Kotlin Multiplatform") }
                Badge(variant = BadgeVariant.Published) { Text("Compose") }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Action buttons
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PushButton(onClick = { onNavigate("button") }) {
                Text("Explore Components")
            }
            PushButton(onClick = {
                uriHandler.openUri("https://github.com/kdroidFilter/compose-macos-26-ui")
            }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    Icon(Lucide.Github, modifier = Modifier.size(14.dp))
                    Text("GitHub")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Feature cards
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val isCompact = maxWidth < 600.dp
            if (isCompact) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    FeatureCard(
                        title = "50+ Components",
                        description = "Buttons, inputs, dialogs, navigation, data display and more — all following macOS HIG.",
                        modifier = Modifier.fillMaxWidth(),
                    )
                    FeatureCard(
                        title = "Native Feel",
                        description = "Vibrancy, glass effects, accent colors and smooth spring animations out of the box.",
                        modifier = Modifier.fillMaxWidth(),
                    )
                    FeatureCard(
                        title = "Multiplatform",
                        description = "Targets Android, iOS, Desktop JVM, and Web (JS + WASM) from a single codebase.",
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                ) {
                    FeatureCard(
                        title = "50+ Components",
                        description = "Buttons, inputs, dialogs, navigation, data display and more — all following macOS HIG.",
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                    )
                    FeatureCard(
                        title = "Native Feel",
                        description = "Vibrancy, glass effects, accent colors and smooth spring animations out of the box.",
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                    )
                    FeatureCard(
                        title = "Multiplatform",
                        description = "Targets Android, iOS, Desktop JVM, and Web (JS + WASM) from a single codebase.",
                        modifier = Modifier.weight(1f).fillMaxHeight(),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Author
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "Built by",
                style = MacosTheme.typography.subheadline,
                color = MacosTheme.colorScheme.textTertiary,
            )
            Text(
                text = "Elie Gambache",
                style = MacosTheme.typography.subheadline,
                color = MacosTheme.colorScheme.accent,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    uriHandler.openUri("https://eliegambache.kdroidfilter.com/")
                },
            )
        }
    }
}

@Composable
private fun FeatureCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        CardContent {
            Column(
                modifier = Modifier.fillMaxHeight().padding(20.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = title,
                    style = MacosTheme.typography.headline,
                    fontWeight = FontWeight.SemiBold,
                    color = MacosTheme.colorScheme.textPrimary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = description,
                    style = MacosTheme.typography.subheadline,
                    color = MacosTheme.colorScheme.textSecondary,
                )
            }
        }
    }
}
