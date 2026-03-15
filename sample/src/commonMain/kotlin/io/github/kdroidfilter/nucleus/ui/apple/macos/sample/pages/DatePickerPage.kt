package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DatePicker
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DatePickerButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DateTimePicker
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.DateTimePickerButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TimePicker
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.TimePickerButton
import io.github.kdroidfilter.nucleus.ui.apple.macos.gallery.GalleryExample
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.ExampleCard
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.GalleryPage
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.SectionHeader
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.ControlSize
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

@GalleryExample("DatePicker", "Date Picker")
@Composable
fun DatePickerBasicExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var date by remember { mutableStateOf(LocalDate(2025, 4, 1)) }
        DatePicker(
            value = date,
            onValueChange = { date = it },
        )
        Text(
            text = "Selected: $date",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textSecondary,
        )
    }
}

@GalleryExample("DatePicker", "Time Picker 12h")
@Composable
fun TimePickerBasicExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var time by remember { mutableStateOf(LocalTime(10, 30)) }
        TimePicker(
            value = time,
            onValueChange = { time = it },
        )
        Text(
            text = "Selected: $time",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textSecondary,
        )
    }
}

@GalleryExample("DatePicker", "Time Picker 24h")
@Composable
fun TimePicker24hExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var time by remember { mutableStateOf(LocalTime(14, 45)) }
        TimePicker(
            value = time,
            onValueChange = { time = it },
            is24Hour = true,
        )
        Text(
            text = "Selected: $time",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textSecondary,
        )
    }
}

@GalleryExample("DatePicker", "DateTime Picker")
@Composable
fun DateTimePickerBasicExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var dateTime by remember { mutableStateOf(LocalDateTime(2025, 4, 1, 10, 30)) }
        DateTimePicker(
            value = dateTime,
            onValueChange = { dateTime = it },
        )
        Text(
            text = "Selected: $dateTime",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textSecondary,
        )
    }
}

@GalleryExample("DatePicker", "Date Button")
@Composable
fun DatePickerButtonExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var date by remember { mutableStateOf(LocalDate(2025, 4, 1)) }
        DatePickerButton(
            value = date,
            onValueChange = { date = it },
        )
        Text(
            text = "Selected: $date",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textSecondary,
        )
    }
}

@GalleryExample("DatePicker", "Time Button")
@Composable
fun TimePickerButtonExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var time by remember { mutableStateOf(LocalTime(10, 30)) }
        TimePickerButton(
            value = time,
            onValueChange = { time = it },
        )
        Text(
            text = "Selected: $time",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textSecondary,
        )
    }
}

@GalleryExample("DatePicker", "Time Button 24h")
@Composable
fun TimePickerButton24hExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var time by remember { mutableStateOf(LocalTime(14, 45)) }
        TimePickerButton(
            value = time,
            onValueChange = { time = it },
            is24Hour = true,
        )
        Text(
            text = "Selected: $time",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textSecondary,
        )
    }
}

@GalleryExample("DatePicker", "DateTime Buttons")
@Composable
fun DateTimePickerButtonExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var dateTime by remember { mutableStateOf(LocalDateTime(2025, 4, 1, 10, 30)) }
        DateTimePickerButton(
            value = dateTime,
            onValueChange = { dateTime = it },
            label = "Event",
        )
        Text(
            text = "Selected: $dateTime",
            style = MacosTheme.typography.caption1,
            color = MacosTheme.colorScheme.textSecondary,
        )
    }
}

@GalleryExample("DatePicker", "States")
@Composable
fun DatePickerStatesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = "Enabled",
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textTertiary,
                modifier = Modifier.widthIn(min = 72.dp),
            )
            var date by remember { mutableStateOf(LocalDate(2025, 4, 1)) }
            DatePickerButton(value = date, onValueChange = { date = it })
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = "Disabled",
                style = MacosTheme.typography.caption1,
                color = MacosTheme.colorScheme.textTertiary,
                modifier = Modifier.widthIn(min = 72.dp),
            )
            DatePickerButton(
                value = LocalDate(2025, 4, 1),
                onValueChange = {},
                enabled = false,
            )
        }
    }
}

@GalleryExample("DatePicker", "Time Button Sizes")
@Composable
fun TimePickerButtonSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        var time by remember { mutableStateOf(LocalTime(10, 30)) }
        for (size in ControlSize.entries) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = size.name,
                    style = MacosTheme.typography.caption1,
                    color = MacosTheme.colorScheme.textTertiary,
                    modifier = Modifier.widthIn(min = 72.dp),
                )
                ControlSize(size) {
                    TimePickerButton(
                        value = time,
                        onValueChange = { time = it },
                    )
                }
            }
        }
    }
}

@GalleryExample("DatePicker", "DateTime Buttons Sizes")
@Composable
fun DateTimePickerButtonSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        var dateTime by remember { mutableStateOf(LocalDateTime(2025, 4, 1, 10, 30)) }
        for (size in ControlSize.entries) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = size.name,
                    style = MacosTheme.typography.caption1,
                    color = MacosTheme.colorScheme.textTertiary,
                    modifier = Modifier.widthIn(min = 72.dp),
                )
                ControlSize(size) {
                    DateTimePickerButton(
                        value = dateTime,
                        onValueChange = { dateTime = it },
                    )
                }
            }
        }
    }
}

@GalleryExample("DatePicker", "Inline Time Picker Sizes")
@Composable
fun TimePickerSizesExample() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var time by remember { mutableStateOf(LocalTime(10, 30)) }
        for (size in ControlSize.entries) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = size.name,
                    style = MacosTheme.typography.caption1,
                    color = MacosTheme.colorScheme.textTertiary,
                )
                ControlSize(size) {
                    TimePicker(
                        value = time,
                        onValueChange = { time = it },
                    )
                }
            }
        }
    }
}

@Composable
internal fun DatePickerPage() {
    GalleryPage("Date Picker", "A macOS-style inline picker for selecting dates and times.") {
        SectionHeader("Compact Buttons")
        ExampleCard(title = "Date Button", sourceCode = GallerySources.DatePickerButtonExample) {
            DatePickerButtonExample()
        }
        ExampleCard(title = "Time Button", sourceCode = GallerySources.TimePickerButtonExample) {
            TimePickerButtonExample()
        }
        ExampleCard(title = "Time Button 24h", sourceCode = GallerySources.TimePickerButton24hExample) {
            TimePickerButton24hExample()
        }
        ExampleCard(title = "Date & Time Buttons", sourceCode = GallerySources.DateTimePickerButtonExample) {
            DateTimePickerButtonExample()
        }

        SectionHeader("Inline Date Picker")
        ExampleCard(title = "Calendar", sourceCode = GallerySources.DatePickerBasicExample) {
            DatePickerBasicExample()
        }

        SectionHeader("Inline Time Picker")
        ExampleCard(title = "12-hour", sourceCode = GallerySources.TimePickerBasicExample) {
            TimePickerBasicExample()
        }
        ExampleCard(title = "24-hour", sourceCode = GallerySources.TimePicker24hExample) {
            TimePicker24hExample()
        }

        SectionHeader("Inline Date & Time Picker")
        ExampleCard(title = "Combined", sourceCode = GallerySources.DateTimePickerBasicExample) {
            DateTimePickerBasicExample()
        }

        SectionHeader("Control Sizes")
        ExampleCard(title = "Time Button Sizes", sourceCode = GallerySources.TimePickerButtonSizesExample) {
            TimePickerButtonSizesExample()
        }
        ExampleCard(title = "DateTime Buttons Sizes", sourceCode = GallerySources.DateTimePickerButtonSizesExample) {
            DateTimePickerButtonSizesExample()
        }
        ExampleCard(title = "Inline Time Picker Sizes", sourceCode = GallerySources.TimePickerSizesExample) {
            TimePickerSizesExample()
        }
        SectionHeader("States")
        ExampleCard(title = "Enabled & Disabled", sourceCode = GallerySources.DatePickerStatesExample) {
            DatePickerStatesExample()
        }
    }
}
