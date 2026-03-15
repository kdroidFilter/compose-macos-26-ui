package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

/**
 * A simple Popup-based dropdown that replaces material3 [DropdownMenu].
 *
 * The popup is shown when [expanded] is `true` and dismissed when the user
 * clicks outside or presses the back/escape key (thanks to focusable properties).
 *
 * @param expanded           Whether the dropdown is currently visible.
 * @param onDismissRequest   Callback invoked when the user requests dismissal.
 * @param anchorWidthPx      Width of the trigger/anchor in pixels, used to size the dropdown.
 * @param anchorHeightPx     Height of the trigger/anchor in pixels, used to offset the dropdown below it.
 * @param matchAnchorWidth   When true the dropdown matches the anchor width exactly;
 *                           when false it is slightly narrower (inset by 8 dp).
 * @param placement          Where the menu appears relative to the anchor.
 * @param modifier           Modifier applied to the inner [Column].
 * @param content            Content displayed inside a [Column] scope.
 */
@Composable
fun DropdownPopup(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    anchorWidthPx: Int,
    anchorHeightPx: Int,
    matchAnchorWidth: Boolean = true,
    placement: MenuPlacement = MenuPlacement.Below,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (!expanded) return

    val shape = MacosTheme.shapes.large
    val density = LocalDensity.current
    val insetPx = if (matchAnchorWidth) 0 else with(density) { 8.dp.roundToPx() }
    val dropdownWidthDp = with(density) { (anchorWidthPx - insetPx).coerceAtLeast(0).toDp() }
    val gapPx = with(density) { 4.dp.roundToPx() }
    val horizontalOffsetPx = insetPx / 2

    val positionProvider = remember(placement, gapPx, horizontalOffsetPx) {
        DropdownMenuPositionProvider(placement, gapPx, horizontalOffsetPx)
    }

    Popup(
        popupPositionProvider = positionProvider,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
    ) {
        Column(
            modifier = Modifier
                .width(dropdownWidthDp)
                .shadow(elevation = 8.dp, shape = shape)
                .then(modifier),
            content = content,
        )
    }
}
