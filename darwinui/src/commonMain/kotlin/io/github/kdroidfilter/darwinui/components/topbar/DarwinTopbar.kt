package io.github.kdroidfilter.darwinui.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import io.github.kdroidfilter.darwinui.components.text.DarwinText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

/**
 * macOS-style top navigation bar.
 *
 * A full-width horizontal bar positioned at the top of the content area,
 * providing a title and optional navigation icon and action buttons.
 *
 * @param title The text displayed in the topbar. Uses [DarwinTheme.typography.titleMedium]
 *   style with [DarwinTheme.colors.textPrimary] color.
 * @param modifier Modifier to be applied to the topbar container.
 * @param navigationIcon Optional composable rendered at the start of the bar
 *   (e.g., a back button or menu icon).
 * @param actions Optional composable rendered at the end of the bar
 *   (e.g., action buttons). Receives [RowScope] for horizontal arrangement.
 */
@Composable
fun DarwinTopbar(
    title: String = "",
    modifier: Modifier = Modifier,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    val colors = DarwinTheme.colors
    val typography = DarwinTheme.typography
    val backgroundColor = colors.backgroundElevated

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor),
    ) {
        // ---- Main row ----
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Navigation icon
            if (navigationIcon != null) {
                navigationIcon()
                Spacer(modifier = Modifier.width(12.dp))
            }

            // Title
            DarwinText(
                text = title,
                style = typography.titleMedium,
                color = colors.textPrimary,
                modifier = Modifier.weight(1f),
            )

            // Actions
            if (actions != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    content = actions,
                )
            }
        }

        // ---- Bottom border ----
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(1.dp)
                .background(colors.border),
        )
    }
}
