package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.AlertDialog
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.AlertDialogButtonLayout
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.AlertType
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.PushButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SaveDialog
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.SmallDialog
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme

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
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textSecondary,
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
                Text("Name", style = MacosTheme.typography.caption1, color = MacosTheme.colorScheme.textTertiary)
                Text("Untitled Document", style = MacosTheme.typography.caption1)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("Location", style = MacosTheme.typography.caption1, color = MacosTheme.colorScheme.textTertiary)
                Text("Desktop", style = MacosTheme.typography.caption1)
            }
        }
    }
}

@GalleryExample("Dialog", "Save Dialog")
@Composable
fun SaveDialogExample() {
    var showDialog by remember { mutableStateOf(false) }
    var fileName by remember { mutableStateOf("Untitled") }
    var tags by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf(0) }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        PushButton(text = "Open Save Dialog", onClick = { showDialog = true })
    }
    SaveDialog(
        visible = showDialog,
        onDismissRequest = { showDialog = false },
        title = "Do you want to keep this new document \"Untitled\"?",
        message = "You can choose to save your changes, or delete this document immediately. You can't undo this action.",
        fileName = fileName,
        onFileNameChange = { fileName = it },
        onSave = { showDialog = false },
        tags = tags,
        onTagsChange = { tags = it },
        locations = listOf("Desktop", "Documents", "Downloads"),
        selectedLocationIndex = selectedLocation,
        onLocationChange = { index, _ -> selectedLocation = index },
    )
}

@GalleryExample("Dialog", "Save Dialog with Delete")
@Composable
fun SaveDialogWithDeleteExample() {
    var showDialog by remember { mutableStateOf(false) }
    var fileName by remember { mutableStateOf("Report.pdf") }
    var selectedLocation by remember { mutableStateOf(0) }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        PushButton(text = "Open Save Dialog with Delete", onClick = { showDialog = true })
    }
    SaveDialog(
        visible = showDialog,
        onDismissRequest = { showDialog = false },
        title = "Do you want to keep this new document \"Report.pdf\"?",
        message = "You can choose to save your changes, or delete this document immediately. You can't undo this action.",
        fileName = fileName,
        onFileNameChange = { fileName = it },
        onSave = { showDialog = false },
        locations = listOf("Desktop", "Documents"),
        selectedLocationIndex = selectedLocation,
        onLocationChange = { index, _ -> selectedLocation = index },
        onDelete = { showDialog = false },
    )
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

@GalleryExample("Dialog", "Alert Dialog — Side by Side")
@Composable
fun AlertDialogSideBySideExample() {
    var showAlertDialog by remember { mutableStateOf(false) }
    PushButton(text = "Show Side by Side Alert", onClick = { showAlertDialog = true })
    if (showAlertDialog) {
        AlertDialog(
            open = true,
            onDismissRequest = { showAlertDialog = false },
            title = "Save the messages, draft, and attachments?",
            message = "This message has not been sent. You can save it later.",
            confirmText = "Save",
            cancelText = "Cancel",
            onConfirm = { showAlertDialog = false },
            onCancel = { showAlertDialog = false },
            buttonLayout = AlertDialogButtonLayout.SideBySide,
        )
    }
}

@GalleryExample("Dialog", "Small Dialog — Custom Animation")
@Composable
fun SmallDialogCustomAnimationExample() {
    var showDialog by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        PushButton(text = "Open Slide-In Dialog", onClick = { showDialog = true })
    }
    SmallDialog(
        visible = showDialog,
        onDismissRequest = { showDialog = false },
        title = "Custom Animation",
        confirmText = "Done",
        onConfirm = { showDialog = false },
        cancelText = "Cancel",
        onCancel = { showDialog = false },
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = spring(dampingRatio = 0.7f, stiffness = 300f),
        ) + fadeIn(tween(300)),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(250),
        ) + fadeOut(tween(250)),
    ) {
        Text(
            text = "This dialog slides in from the bottom with a spring animation.",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textSecondary,
        )
    }
}

@GalleryExample("Dialog", "Alert Dialog — Custom Animation")
@Composable
fun AlertDialogCustomAnimationExample() {
    var showAlertDialog by remember { mutableStateOf(false) }
    PushButton(text = "Show Bounce Alert", onClick = { showAlertDialog = true })
    if (showAlertDialog) {
        AlertDialog(
            open = true,
            onDismissRequest = { showAlertDialog = false },
            title = "Custom Animation",
            message = "This alert uses a bouncy spring scale animation.",
            confirmText = "OK",
            cancelText = "Cancel",
            onConfirm = { showAlertDialog = false },
            onCancel = { showAlertDialog = false },
            enter = scaleIn(
                initialScale = 0.5f,
                animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
            ) + fadeIn(tween(200)),
            exit = scaleOut(
                targetScale = 0.8f,
                animationSpec = tween(150),
            ) + fadeOut(tween(150)),
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

        SectionHeader("Save Dialog")
        ExampleCard(
            title = "Save Dialog",
            description = "macOS-native NSSavePanel-style save dialog with form fields",
            sourceCode = GallerySources.SaveDialogExample,
        ) { SaveDialogExample() }
        ExampleCard(
            title = "With Delete",
            description = "Save dialog with destructive delete action",
            sourceCode = GallerySources.SaveDialogWithDeleteExample,
        ) { SaveDialogWithDeleteExample() }

        SectionHeader("Custom Animations")
        ExampleCard(
            title = "Slide-In Dialog",
            description = "Small dialog with custom slide-in spring animation",
            sourceCode = GallerySources.SmallDialogCustomAnimationExample,
        ) { SmallDialogCustomAnimationExample() }
        ExampleCard(
            title = "Bounce Alert",
            description = "Alert dialog with bouncy spring scale animation",
            sourceCode = GallerySources.AlertDialogCustomAnimationExample,
        ) { AlertDialogCustomAnimationExample() }

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
        ExampleCard(
            title = "Side by Side Alert",
            description = "macOS-native alert with side-by-side buttons",
            sourceCode = GallerySources.AlertDialogSideBySideExample,
        ) { AlertDialogSideBySideExample() }
    }
}
