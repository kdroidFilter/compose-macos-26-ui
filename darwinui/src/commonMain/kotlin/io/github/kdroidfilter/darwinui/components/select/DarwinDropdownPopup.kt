package io.github.kdroidfilter.darwinui.components.select

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

/**
 * A simple Popup-based dropdown that replaces material3 [DropdownMenu].
 *
 * The popup is shown when [expanded] is `true` and dismissed when the user
 * clicks outside or presses the back/escape key (thanks to focusable properties).
 *
 * @param expanded        Whether the dropdown is currently visible.
 * @param onDismissRequest Callback invoked when the user requests dismissal.
 * @param modifier        Modifier applied to the inner [Column].
 * @param content         Content displayed inside a [Column] scope.
 */
@Composable
fun DarwinDropdownPopup(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    if (!expanded) return

    val shape = DarwinTheme.shapes.large

    Popup(
        alignment = Alignment.BottomStart,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
    ) {
        Column(
            modifier = Modifier
                .shadow(elevation = 8.dp, shape = shape)
                .then(modifier),
            content = content,
        )
    }
}
