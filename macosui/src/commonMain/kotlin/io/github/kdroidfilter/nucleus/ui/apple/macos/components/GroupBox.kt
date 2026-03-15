package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.LocalTextStyle

// ===========================================================================
// GroupBoxColors
// ===========================================================================

@Immutable
class GroupBoxColors(
    val containerColor: Color,
    val contentColor: Color,
    val labelColor: Color,
    val borderColor: Color,
) {
    fun copy(
        containerColor: Color = this.containerColor,
        contentColor: Color = this.contentColor,
        labelColor: Color = this.labelColor,
        borderColor: Color = this.borderColor,
    ) = GroupBoxColors(containerColor, contentColor, labelColor, borderColor)
}

// ===========================================================================
// GroupBoxDefaults
// ===========================================================================

object GroupBoxDefaults {
    @Composable
    fun colors(
        containerColor: Color = MacosTheme.colorScheme.card,
        contentColor: Color = MacosTheme.colorScheme.cardForeground,
        labelColor: Color = MacosTheme.colorScheme.textPrimary,
        borderColor: Color = MacosTheme.colorScheme.borderSubtle,
    ) = GroupBoxColors(containerColor, contentColor, labelColor, borderColor)
}

// ===========================================================================
// GroupBox — macOS-style labeled container
// ===========================================================================

/**
 * A macOS-style group box that visually groups related controls under an
 * optional label. The label appears inside the box at the top, separated by
 * a subtle divider, matching the appearance of NSBox / SwiftUI GroupBox.
 *
 * @param modifier Modifier applied to the outer container.
 * @param label Optional header composable (typically a [Text]).
 * @param colors Colors used for the container, label, and border.
 * @param shape Corner shape of the container.
 * @param content The grouped content.
 */
@Composable
fun GroupBox(
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    colors: GroupBoxColors = GroupBoxDefaults.colors(),
    shape: Shape = MacosTheme.shapes.large,
    content: @Composable ColumnScope.() -> Unit,
) {
    val labelStyle = TextStyle(
        color = colors.labelColor,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        letterSpacing = (-0.01).sp,
    )

    Column(
        modifier = modifier
            .clip(shape)
            .background(colors.containerColor, shape)
            .border(1.dp, colors.borderColor, shape)
            .fillMaxWidth(),
    ) {
        if (label != null) {
            Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 10.dp)) {
                CompositionLocalProvider(LocalTextStyle provides labelStyle) {
                    label()
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.borderColor),
            )
        }
        Column(
            modifier = Modifier.padding(16.dp),
            content = content,
        )
    }
}

/**
 * Convenience overload of [GroupBox] that accepts a plain string label.
 */
@Composable
fun GroupBox(
    label: String,
    modifier: Modifier = Modifier,
    colors: GroupBoxColors = GroupBoxDefaults.colors(),
    shape: Shape = MacosTheme.shapes.large,
    content: @Composable ColumnScope.() -> Unit,
) {
    GroupBox(
        modifier = modifier,
        label = { Text(label) },
        colors = colors,
        shape = shape,
        content = content,
    )
}

@Preview
@Composable
private fun GroupBoxPreview() {
    MacosTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            GroupBox(label = "Appearance") {
                Text("Dark mode")
                Text("Accent color")
            }
        }
    }
}
