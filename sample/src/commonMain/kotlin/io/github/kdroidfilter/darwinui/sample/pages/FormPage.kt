package io.github.kdroidfilter.darwinui.sample.pages

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.ChevronRight
import com.composables.icons.lucide.Copy
import com.composables.icons.lucide.Image
import com.composables.icons.lucide.Lucide
import io.github.kdroidfilter.darwinui.components.ArrowButton
import io.github.kdroidfilter.darwinui.components.ComboBox
import io.github.kdroidfilter.darwinui.components.GroupedList
import io.github.kdroidfilter.darwinui.components.GroupedListItem
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.PushButtonStyle
import io.github.kdroidfilter.darwinui.components.PulldownButton
import io.github.kdroidfilter.darwinui.components.PushButton
import io.github.kdroidfilter.darwinui.components.RadioButton
import io.github.kdroidfilter.darwinui.components.Switch
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TextField
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideEllipsis
import io.github.kdroidfilter.darwinui.icons.LucideInfo
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.ControlSize
import io.github.kdroidfilter.darwinui.theme.DarwinTheme

// =====================================================================
// Leading Accessory Options
// =====================================================================

@GalleryExample("Form", "Leading Accessory Options")
@Composable
fun FormLeadingAccessoryExample() {
    var selectedRadio by remember { mutableStateOf(1) }
    val colors = DarwinTheme.colorScheme
    val typography = DarwinTheme.typography

    GroupedList {
        // Title only
        GroupedListItem {
            Text("Title only", style = typography.subheadline, color = colors.textPrimary)
        }

        // Subtitle only
        GroupedListItem {
            Text("Subtitle only", style = typography.caption1, color = colors.textSecondary)
        }

        // Subtitle + Subtitle
        GroupedListItem {
            Column {
                Text("Subtitle", style = typography.caption1, color = colors.textSecondary)
                Text("Subtitle", style = typography.caption1, color = colors.textSecondary)
            }
        }

        // Title + Subtitle
        GroupedListItem {
            Column {
                Text(
                    "Title",
                    style = typography.subheadline,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.textPrimary,
                )
                Text("Subtitle", style = typography.caption1, color = colors.textSecondary)
            }
        }

        // Title + Symbol
        GroupedListItem(
            leading = {
                Icon(Lucide.Copy, modifier = Modifier.size(20.dp), tint = colors.textSecondary)
            },
        ) {
            Text("Title + Symbol", style = typography.subheadline, color = colors.textPrimary)
        }

        // Title + Image
        GroupedListItem(
            leading = { CheckeredImage(size = 28.dp) },
        ) {
            Text("Title + Image", style = typography.subheadline, color = colors.textPrimary)
        }

        // Title + Large icon
        GroupedListItem(
            leading = {
                AppIconBox(size = 36.dp) {
                    Icon(Lucide.Image, modifier = Modifier.size(18.dp), tint = colors.textSecondary)
                }
            },
        ) {
            Text("Title + Large icon", style = typography.subheadline, color = colors.textPrimary)
        }

        // Title + Icon
        GroupedListItem(
            leading = {
                AppIconBox(size = 28.dp) {
                    Icon(Lucide.Image, modifier = Modifier.size(14.dp), tint = colors.textSecondary)
                }
            },
        ) {
            Text("Title + Icon", style = typography.subheadline, color = colors.textPrimary)
        }

        // Disclosure
        GroupedListItem(
            leading = {
                Icon(Lucide.ChevronRight, modifier = Modifier.size(16.dp), tint = colors.textSecondary)
            },
        ) {
            Text("Disclosure", style = typography.subheadline, color = colors.textPrimary)
        }

        // Disclosure + Title + Subtitle
        GroupedListItem(
            leading = {
                Icon(Lucide.ChevronRight, modifier = Modifier.size(16.dp), tint = colors.textSecondary)
            },
        ) {
            Column {
                Text(
                    "Disclosure + Title + Subtitle",
                    style = typography.subheadline,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.textPrimary,
                )
                Text("Subtitle", style = typography.caption1, color = colors.textSecondary)
            }
        }

        // Radio button group
        GroupedListItem {
            Text("Radio button group", style = typography.subheadline, color = colors.textPrimary)
        }

        // Radio button group + subtitle
        GroupedListItem {
            Column {
                Text(
                    "Radio button group + subtitle",
                    style = typography.subheadline,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.textPrimary,
                )
                Text("Subtitle", style = typography.caption1, color = colors.textSecondary)
            }
        }

        // Radio option 1
        GroupedListItem(
            onClick = { selectedRadio = 0 },
            leading = { RadioButton(selected = selectedRadio == 0, onClick = { selectedRadio = 0 }) },
        ) {
            Text("This is a very long radio label", style = typography.subheadline, color = colors.textPrimary)
        }

        // Radio option 2
        GroupedListItem(
            onClick = { selectedRadio = 1 },
            showDivider = false,
            leading = { RadioButton(selected = selectedRadio == 1, onClick = { selectedRadio = 1 }) },
        ) {
            Text("This is a very long radio label", style = typography.subheadline, color = colors.textPrimary)
        }
    }
}

// =====================================================================
// Trailing Accessory Options
// =====================================================================

@GalleryExample("Form", "Trailing Accessory Options")
@Composable
fun FormTrailingAccessoryExample() {
    var toggleState by remember { mutableStateOf(true) }
    var infoToggleState by remember { mutableStateOf(false) }
    var stepperValue by remember { mutableStateOf(300) }
    var textFieldValue by remember { mutableStateOf("Value") }
    var selectedRadio by remember { mutableStateOf(0) }
    var comboBoxSelection by remember { mutableStateOf(0) }

    val colors = DarwinTheme.colorScheme
    val typography = DarwinTheme.typography

    val stepperText = remember(stepperValue) {
        val whole = stepperValue / 100
        val frac = (stepperValue % 100).toString().padStart(2, '0')
        "$whole.$frac"
    }

    GroupedList {
        // Label
        GroupedListItem(
            trailing = { Text("Label", style = typography.subheadline, color = colors.textSecondary) },
        ) {
            Text("Label", style = typography.subheadline, color = colors.textPrimary)
        }

        // Info
        GroupedListItem(
            trailing = {
                PushButton(onClick = {}, style = PushButtonStyle.Borderless) {
                    Icon(LucideInfo, modifier = Modifier.size(16.dp), tint = colors.textSecondary)
                }
            },
        ) {
            Text("Info", style = typography.subheadline, color = colors.textPrimary)
        }

        // Popup
        GroupedListItem(
            trailing = { PulldownButton(text = "Label", onClick = {}) },
        ) {
            Text("Popup", style = typography.subheadline, color = colors.textPrimary)
        }

        // Toggle
        GroupedListItem(
            trailing = { Switch(checked = toggleState, onCheckedChange = { toggleState = it }) },
        ) {
            Text("Toggle", style = typography.subheadline, color = colors.textPrimary)
        }

        // Info Button + Toggle
        GroupedListItem(
            trailing = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PushButton(onClick = {}, style = PushButtonStyle.Borderless) {
                        Icon(LucideInfo, modifier = Modifier.size(16.dp), tint = colors.textSecondary)
                    }
                    Switch(checked = infoToggleState, onCheckedChange = { infoToggleState = it })
                }
            },
        ) {
            Text("Info Button + Toggle", style = typography.subheadline, color = colors.textPrimary)
        }

        // Label + Info Button
        GroupedListItem(
            trailing = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text("Label", style = typography.subheadline, color = colors.textSecondary)
                    PushButton(onClick = {}, style = PushButtonStyle.Borderless) {
                        Icon(LucideInfo, modifier = Modifier.size(16.dp), tint = colors.textSecondary)
                    }
                }
            },
        ) {
            Text("Label + Info Button", style = typography.subheadline, color = colors.textPrimary)
        }

        // Stepper
        GroupedListItem(
            trailing = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    ControlSize(ControlSize.Small) {
                        TextField(
                            value = stepperText,
                            onValueChange = {},
                            singleLine = true,
                            modifier = Modifier.width(64.dp),
                        )
                    }
//                    ArrowButton(
//                        onIncrement = { stepperValue++ },
//                        onDecrement = { if (stepperValue > 0) stepperValue-- },
//                    )
                }
            },
        ) {
            Text("Stepper", style = typography.subheadline, color = colors.textPrimary)
        }

        // Stepper + Label
        GroupedListItem(
            trailing = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    ControlSize(ControlSize.Small) {
                        TextField(
                            value = stepperText,
                            onValueChange = {},
                            singleLine = true,
                            modifier = Modifier.width(64.dp),
                        )
                    }
//                    ArrowButton(
//                        onIncrement = { stepperValue++ },
//                        onDecrement = { if (stepperValue > 0) stepperValue-- },
//                    )
                    Text("seconds", style = typography.caption1, color = colors.textSecondary)
                }
            },
        ) {
            Text("Stepper + Label", style = typography.subheadline, color = colors.textPrimary)
        }

        // Text Field
        GroupedListItem(
            trailing = {
                ControlSize(ControlSize.Small) {
                    TextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it },
                        singleLine = true,
                        modifier = Modifier.width(100.dp),
                    )
                }
            },
        ) {
            Text("Text Field", style = typography.subheadline, color = colors.textPrimary)
        }

        // Radio buttons
        GroupedListItem(
            trailing = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    RadioButton(
                        selected = selectedRadio == 0,
                        onClick = { selectedRadio = 0 },
                        label = "Option 2",
                    )
                    RadioButton(
                        selected = selectedRadio == 1,
                        onClick = { selectedRadio = 1 },
                        label = "Option 1",
                    )
                }
            },
        ) {
            Text("Radio buttons", style = typography.subheadline, color = colors.textPrimary)
        }

        // More button
        GroupedListItem(
            trailing = {
                PushButton(onClick = {}, style = PushButtonStyle.Borderless) {
                    Icon(LucideEllipsis, modifier = Modifier.size(16.dp), tint = colors.textSecondary)
                }
            },
        ) {
            Text("More button", style = typography.subheadline, color = colors.textPrimary)
        }

        // Combo Box
        GroupedListItem(
            trailing = {
                ComboBox(
                    items = listOf("Value", "Option A", "Option B"),
                    selected = comboBoxSelection,
                    onSelectionChange = { index, _ -> comboBoxSelection = index },
                    modifier = Modifier.width(120.dp),
                )
            },
        ) {
            Text("Combo Box", style = typography.subheadline, color = colors.textPrimary)
        }

        // Menu + Button
        GroupedListItem(
            trailing = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    PulldownButton(text = "Full Screen", onClick = {})
                    PushButton(text = "Choose Display...", onClick = {})
                }
            },
        ) {
            Text("Menu + Button", style = typography.subheadline, color = colors.textPrimary)
        }

        // Button
        GroupedListItem(
            showDivider = false,
            trailing = { PushButton(text = "Advanced...", onClick = {}) },
        ) {
            Text("Button", style = typography.subheadline, color = colors.textPrimary)
        }
    }
}

// =====================================================================
// Private helpers
// =====================================================================

@Composable
private fun CheckeredImage(size: Dp) {
    val isDark = DarwinTheme.colorScheme.isDark
    val darkSquare = if (isDark) Color(0xFF555555) else Color(0xFFCCCCCC)
    val lightSquare = if (isDark) Color(0xFF333333) else Color.White

    Canvas(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(4.dp)),
    ) {
        val cellCount = 6
        val cellPx = this.size.width / cellCount
        for (row in 0 until cellCount) {
            for (col in 0 until cellCount) {
                drawRect(
                    color = if ((row + col) % 2 == 0) lightSquare else darkSquare,
                    topLeft = Offset(col * cellPx, row * cellPx),
                    size = Size(cellPx, cellPx),
                )
            }
        }
    }
}

@Composable
private fun AppIconBox(size: Dp, content: @Composable () -> Unit) {
    val bgColor = if (DarwinTheme.colorScheme.isDark) {
        Color.White.copy(alpha = 0.08f)
    } else {
        Color.Black.copy(alpha = 0.05f)
    }
    val shape = RoundedCornerShape(size * 0.22f)

    Box(
        modifier = Modifier
            .size(size)
            .clip(shape)
            .background(bgColor, shape),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

// =====================================================================
// Page
// =====================================================================

@Composable
internal fun FormPage() {
    GalleryPage("Form", "Grouped list row configurations with leading and trailing accessories.") {
        SectionHeader("Leading Accessory Options")
        ExampleCard(
            title = "Leading Accessories",
            description = "Row variants with icons, images, disclosure, and radio buttons",
            sourceCode = GallerySources.FormLeadingAccessoryExample,
        ) { FormLeadingAccessoryExample() }

        SectionHeader("Trailing Accessory Options")
        ExampleCard(
            title = "Trailing Accessories",
            description = "Row variants with labels, toggles, steppers, inputs, and buttons",
            sourceCode = GallerySources.FormTrailingAccessoryExample,
        ) { FormTrailingAccessoryExample() }
    }
}
