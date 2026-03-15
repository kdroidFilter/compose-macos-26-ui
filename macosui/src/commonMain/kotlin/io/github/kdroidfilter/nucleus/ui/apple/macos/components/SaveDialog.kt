package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosDuration
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.macosGlass
import kotlinx.coroutines.delay

// ===========================================================================
// SaveDialog — macOS-native NSSavePanel-style save dialog
// ===========================================================================

private val SaveDialogLabelWidth = 72.dp

/**
 * macOS-native save dialog matching the NSSavePanel sheet layout.
 *
 * Displays a dialog with a title, optional message, labeled form rows for
 * filename, tags, and location, plus optional Delete, Cancel, and Save footer buttons.
 *
 * @param visible Whether the dialog is shown.
 * @param onDismissRequest Called when the user clicks the scrim or presses cancel.
 * @param title The bold heading text displayed at the top of the dialog.
 * @param fileName The current filename value.
 * @param onFileNameChange Called when the filename changes.
 * @param onSave Called when the Save button is clicked.
 * @param modifier Modifier for the dialog panel.
 * @param message Optional description text shown below the title.
 * @param tags The current tags value.
 * @param onTagsChange Called when tags change. Pass null to hide the tags row.
 * @param locations List of location names for the dropdown.
 * @param selectedLocationIndex Currently selected location index.
 * @param onLocationChange Called when location changes. Pass null to hide the row.
 * @param onDelete Called when the Delete button is clicked. Pass null to hide it.
 * @param size The maximum width constraint of the dialog.
 */
@Composable
fun SaveDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    fileName: String,
    onFileNameChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
    message: String? = null,
    tags: String = "",
    onTagsChange: ((String) -> Unit)? = null,
    locations: List<String> = listOf("Desktop"),
    selectedLocationIndex: Int = 0,
    onLocationChange: ((Int, String) -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    size: DialogSize = DialogSize.Large,
) {
    var showPopup by remember { mutableStateOf(false) }
    var animateIn by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        if (visible) {
            showPopup = true
            delay(16)
            animateIn = true
        } else {
            animateIn = false
            delay(MacosDuration.Slow.millis.toLong() + 50)
            showPopup = false
        }
    }

    if (showPopup) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = onDismissRequest,
            properties = PopupProperties(focusable = true),
        ) {
            val colors = MacosTheme.colorScheme
            val isDark = colors.isDark
            val scrimColor = if (isDark) Color.Black.copy(alpha = 0.50f) else Color.Black.copy(alpha = 0.30f)

            // Scrim
            AnimatedVisibility(
                visible = animateIn,
                enter = fadeIn(tween(MacosDuration.Fast.millis)),
                exit = fadeOut(tween(MacosDuration.Slow.millis)),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(scrimColor)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = onDismissRequest,
                        ),
                )
            }

            // Dialog panel
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                AnimatedVisibility(
                    visible = animateIn,
                    enter = fadeIn(tween(MacosDuration.Slow.millis)) +
                            scaleIn(
                                initialScale = 0.95f,
                                transformOrigin = TransformOrigin.Center,
                                animationSpec = tween(MacosDuration.Slow.millis),
                            ) +
                            slideInVertically(
                                initialOffsetY = { 10 },
                                animationSpec = tween(MacosDuration.Slow.millis),
                            ),
                    exit = fadeOut(tween(MacosDuration.Slow.millis)) +
                            scaleOut(
                                targetScale = 0.95f,
                                transformOrigin = TransformOrigin.Center,
                                animationSpec = tween(MacosDuration.Slow.millis),
                            ) +
                            slideOutVertically(
                                targetOffsetY = { 10 },
                                animationSpec = tween(MacosDuration.Slow.millis),
                            ),
                ) {
                    SaveDialogContent(
                        title = title,
                        message = message,
                        fileName = fileName,
                        onFileNameChange = onFileNameChange,
                        onSave = onSave,
                        tags = tags,
                        onTagsChange = onTagsChange,
                        locations = locations,
                        selectedLocationIndex = selectedLocationIndex,
                        onLocationChange = onLocationChange,
                        onDelete = onDelete,
                        onCancel = onDismissRequest,
                        size = size,
                        modifier = modifier,
                    )
                }
            }
        }
    }
}

@Composable
private fun SaveDialogContent(
    title: String,
    message: String?,
    fileName: String,
    onFileNameChange: (String) -> Unit,
    onSave: () -> Unit,
    tags: String,
    onTagsChange: ((String) -> Unit)?,
    locations: List<String>,
    selectedLocationIndex: Int,
    onLocationChange: ((Int, String) -> Unit)?,
    onDelete: (() -> Unit)?,
    onCancel: () -> Unit,
    size: DialogSize,
    modifier: Modifier,
) {
    val colors = MacosTheme.colorScheme
    val isDark = colors.isDark

    val shape = RoundedCornerShape(16.dp)
    val fallbackBg = if (isDark) Color(0xFF262626).copy(alpha = 0.92f)
    else Color.White.copy(alpha = 0.95f)
    val borderColor = if (isDark) Color(0xFF5E5E5E).copy(alpha = 0.6f)
    else Color.Black.copy(alpha = 0.10f)

    Column(
        modifier = modifier
            .widthIn(max = size.maxWidth)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(elevation = 24.dp, shape = shape)
            .macosGlass(shape = shape, fallbackColor = fallbackBg)
            .border(1.dp, borderColor, shape)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { /* consume clicks */ },
            )
            .padding(20.dp),
    ) {
        // Title and message
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = MacosTheme.typography.subheadline,
                color = colors.textPrimary,
                textAlign = TextAlign.Center,
            )
            if (message != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message,
                    style = MacosTheme.typography.caption1,
                    color = colors.textSecondary,
                    textAlign = TextAlign.Center,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Form fields
        Column(verticalArrangement = Arrangement.spacedBy(13.dp)) {
            // Save As row
            SaveDialogRow(label = "Save As:") {
                ControlSize(ControlSize.Small) {
                    TextField(
                        value = fileName,
                        onValueChange = onFileNameChange,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            // Tags row
            if (onTagsChange != null) {
                SaveDialogRow(label = "Tags:") {
                    ControlSize(ControlSize.Small) {
                        TextField(
                            value = tags,
                            onValueChange = onTagsChange,
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }

            // Where row
            if (locations.isNotEmpty() && onLocationChange != null) {
                SaveDialogRow(label = "Where:") {
                    ComboBox(
                        items = locations,
                        selected = selectedLocationIndex,
                        onSelectionChange = onLocationChange,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Footer buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (onDelete != null) {
                PanelDestructiveButton(
                    text = "Delete",
                    onClick = onDelete,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            PanelSecondaryButton(
                text = "Cancel",
                onClick = onCancel,
            )
            Spacer(modifier = Modifier.width(8.dp))
            PanelAccentButton(
                text = "Save",
                onClick = onSave,
            )
        }
    }
}

@Composable
private fun SaveDialogRow(
    label: String,
    content: @Composable () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = label,
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textTertiary,
            textAlign = TextAlign.End,
            modifier = Modifier.width(SaveDialogLabelWidth),
        )
        Spacer(Modifier.width(8.dp))
        Box(Modifier.weight(1f)) { content() }
    }
}
