package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.darwinui.components.AlertDialog
import io.github.kdroidfilter.darwinui.components.AlertType
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.SmallDialog
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

@GalleryExample("Dialog", "Small Dialog")
@Composable
fun SmallDialogExample() {
    var showDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        PushButton(text = "Open Dialog", onClick = { showDialog = true })
    }
    SmallDialog(
        visible = showDialog,
        onDismissRequest = { showDialog = false },
        title = "Save Changes",
        confirmText = "Save",
        onConfirm = { showDialog = false },
        cancelText = "Cancel",
        onCancel = { showDialog = false },
    ) {
        Text(
            text = "Review your changes before saving. You can cancel to go back.",
            style = DarwinTheme.typography.bodySmall,
            color = DarwinTheme.colors.textSecondary,
        )
    }
}

@GalleryExample("Dialog", "Small Dialog with Destructive")
@Composable
fun SmallDialogDestructiveExample() {
    var showDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        PushButton(text = "Open Delete Dialog", onClick = { showDialog = true })
    }
    SmallDialog(
        visible = showDialog,
        onDismissRequest = { showDialog = false },
        title = "Manage Document",
        confirmText = "Save",
        onConfirm = { showDialog = false },
        cancelText = "Cancel",
        onCancel = { showDialog = false },
        destructiveText = "Delete",
        onDestructive = { showDialog = false },
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("Name", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.textTertiary)
                Text("Untitled Document", style = DarwinTheme.typography.bodySmall)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("Location", style = DarwinTheme.typography.bodySmall, color = DarwinTheme.colors.textTertiary)
                Text("Desktop", style = DarwinTheme.typography.bodySmall)
            }
        }
    }
}

@GalleryExample("Dialog", "Alert Dialog — Save")
@Composable
fun AlertDialogSaveExample() {
    var showAlertDialog by remember { mutableStateOf(false) }
    PushButton(text = "Show Save Alert", onClick = { showAlertDialog = true })
    if (showAlertDialog) {
        AlertDialog(
            open = true,
            onDismissRequest = { showAlertDialog = false },
            title = "Save the messages, draft, and attachments?",
            message = "This message has not been sent. You can save it later.",
            confirmText = "Save",
            destructiveText = "Don't Save",
            cancelText = "Cancel",
            onConfirm = { showAlertDialog = false },
            onDestructive = { showAlertDialog = false },
            onCancel = { showAlertDialog = false },
        )
    }
}

@GalleryExample("Dialog", "Alert Dialog — Destructive")
@Composable
fun AlertDialogDestructiveExample() {
    var showAlertDialog by remember { mutableStateOf(false) }
    PushButton(text = "Show Delete Alert", onClick = { showAlertDialog = true })
    if (showAlertDialog) {
        AlertDialog(
            open = true,
            onDismissRequest = { showAlertDialog = false },
            title = "\"Document\" will be deleted immediately.",
            message = "You can't undo this action.",
            type = AlertType.Error,
            confirmText = "OK",
            destructiveText = "Delete",
            cancelText = "Cancel",
            onConfirm = { showAlertDialog = false },
            onDestructive = { showAlertDialog = false },
            onCancel = { showAlertDialog = false },
        )
    }
}

@GalleryExample("Dialog", "Alert Dialog — Simple")
@Composable
fun AlertDialogSimpleExample() {
    var showAlertDialog by remember { mutableStateOf(false) }
    PushButton(text = "Show Simple Alert", onClick = { showAlertDialog = true })
    if (showAlertDialog) {
        AlertDialog(
            open = true,
            onDismissRequest = { showAlertDialog = false },
            title = "The application is not responding.",
            message = "Do you want to wait or force quit?",
            confirmText = "OK",
            cancelText = "Cancel",
            onConfirm = { showAlertDialog = false },
            onCancel = { showAlertDialog = false },
        )
    }
}

@Composable
internal fun DialogPage() {
    GalleryPage("Dialog", "A modal dialog that interrupts the user with important content.") {
        SectionHeader("Small Dialog")
        ExampleCard(
            title = "Simple",
            description = "macOS-native small dialog with cancel and confirm",
            sourceCode = GallerySources.SmallDialogExample,
        ) { SmallDialogExample() }
        ExampleCard(
            title = "With Destructive",
            description = "Small dialog with destructive action on the left",
            sourceCode = GallerySources.SmallDialogDestructiveExample,
        ) { SmallDialogDestructiveExample() }

        SectionHeader("Alert Dialog")
        ExampleCard(
            title = "Save Alert",
            description = "macOS-native save confirmation with 3 buttons",
            sourceCode = GallerySources.AlertDialogSaveExample,
        ) { AlertDialogSaveExample() }
        ExampleCard(
            title = "Destructive Alert",
            description = "macOS-native delete confirmation",
            sourceCode = GallerySources.AlertDialogDestructiveExample,
        ) { AlertDialogDestructiveExample() }
        ExampleCard(
            title = "Simple Alert",
            description = "macOS-native simple alert with 2 buttons",
            sourceCode = GallerySources.AlertDialogSimpleExample,
        ) { AlertDialogSimpleExample() }
    }
}
