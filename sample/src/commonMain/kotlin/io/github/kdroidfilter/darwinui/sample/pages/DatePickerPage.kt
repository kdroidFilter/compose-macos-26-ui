package io.github.kdroidfilter.darwinui.sample.pages

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
import io.github.kdroidfilter.darwinui.components.DatePicker
import io.github.kdroidfilter.darwinui.components.DatePickerButton
import io.github.kdroidfilter.darwinui.components.DateTimePicker
import io.github.kdroidfilter.darwinui.components.DateTimePickerButton
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.components.TimePicker
import io.github.kdroidfilter.darwinui.components.TimePickerButton
import io.github.kdroidfilter.darwinui.gallery.GalleryExample
import io.github.kdroidfilter.darwinui.sample.gallery.ExampleCard
import io.github.kdroidfilter.darwinui.sample.gallery.GalleryPage
import io.github.kdroidfilter.darwinui.sample.gallery.SectionHeader
import io.github.kdroidfilter.darwinui.sample.gallery.generated.GallerySources
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
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
            style = DarwinTheme.typography.caption1,
            color = DarwinTheme.colorScheme.textSecondary,
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
            style = DarwinTheme.typography.caption1,
            color = DarwinTheme.colorScheme.textSecondary,
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
            style = DarwinTheme.typography.caption1,
            color = DarwinTheme.colorScheme.textSecondary,
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
            style = DarwinTheme.typography.caption1,
            color = DarwinTheme.colorScheme.textSecondary,
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
            style = DarwinTheme.typography.caption1,
            color = DarwinTheme.colorScheme.textSecondary,
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
            style = DarwinTheme.typography.caption1,
            color = DarwinTheme.colorScheme.textSecondary,
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
            style = DarwinTheme.typography.caption1,
            color = DarwinTheme.colorScheme.textSecondary,
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
                style = DarwinTheme.typography.caption1,
                color = DarwinTheme.colorScheme.textTertiary,
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
                style = DarwinTheme.typography.caption1,
                color = DarwinTheme.colorScheme.textTertiary,
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

        SectionHeader("States")
        ExampleCard(title = "Enabled & Disabled", sourceCode = GallerySources.DatePickerStatesExample) {
            DatePickerStatesExample()
        }
    }
}
