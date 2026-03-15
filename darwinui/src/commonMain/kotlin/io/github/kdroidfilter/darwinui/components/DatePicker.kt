package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.flow.distinctUntilChanged
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideChevronLeft
import io.github.kdroidfilter.darwinui.icons.LucideChevronRight
import io.github.kdroidfilter.darwinui.theme.DarwinSpringPreset
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import io.github.kdroidfilter.darwinui.theme.darwinSpring
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn

// ===========================================================================
// DatePicker — macOS inline compact date picker (calendar panel)
//
// Sketch: Compact Picker / {Light|Dark} / Date
// Container: 370dp wide, 13dp radius, 16dp horizontal padding
// ===========================================================================

/**
 * A macOS-style inline date picker showing a calendar grid.
 *
 * Displays month header with navigation arrows and a 7-column calendar grid.
 * Matches the iOS/macOS Compact Picker design from the Apple HIG Sketch kit.
 *
 * @param value the currently selected date
 * @param onValueChange called when a new date is selected
 * @param modifier the [Modifier] to be applied
 * @param enabled whether the picker is interactive
 */
@Composable
fun DatePicker(
    value: LocalDate,
    onValueChange: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    var displayedYear by remember(value) { mutableStateOf(value.year) }
    var displayedMonth by remember(value) { mutableStateOf(value.month) }

    PickerContainer(modifier = modifier.then(if (!enabled) Modifier.alpha(0.45f) else Modifier)) {
        CalendarHeader(
            year = displayedYear,
            month = displayedMonth,
            enabled = enabled,
            onPreviousMonth = {
                val (y, m) = previousMonth(displayedYear, displayedMonth)
                displayedYear = y; displayedMonth = m
            },
            onNextMonth = {
                val (y, m) = nextMonth(displayedYear, displayedMonth)
                displayedYear = y; displayedMonth = m
            },
        )
        CalendarMonth(
            year = displayedYear,
            month = displayedMonth,
            selectedDate = value,
            enabled = enabled,
            onDateSelected = onValueChange,
        )
    }
}

// ===========================================================================
// TimePicker — macOS inline compact time picker
//
// Sketch: Compact Picker / {Light|Dark} / Time — 370×52dp
// ===========================================================================

/**
 * A macOS-style inline time picker.
 *
 * Displays a "Time" label with a time value pill and AM/PM segmented control.
 *
 * @param value the currently selected time
 * @param onValueChange called when the time changes
 * @param modifier the [Modifier] to be applied
 * @param enabled whether the picker is interactive
 * @param is24Hour whether to use 24-hour format
 * @param label the label shown on the left side
 */
@Composable
fun TimePicker(
    value: LocalTime,
    onValueChange: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    is24Hour: Boolean = false,
    label: String = "Time",
) {
    PickerContainer(modifier = modifier.then(if (!enabled) Modifier.alpha(0.45f) else Modifier)) {
        TimePickerRow(
            value = value,
            onValueChange = onValueChange,
            is24Hour = is24Hour,
            enabled = enabled,
            label = label,
        )
    }
}

// ===========================================================================
// DateTimePicker — macOS inline compact date + time picker
//
// Sketch: Compact Picker / {Light|Dark} / Date and Time — 370×378dp
// ===========================================================================

/**
 * A macOS-style inline date and time picker.
 *
 * Combines a calendar grid with a time picker row below a separator.
 *
 * @param value the currently selected date and time
 * @param onValueChange called when the value changes
 * @param modifier the [Modifier] to be applied
 * @param enabled whether the picker is interactive
 * @param is24Hour whether to use 24-hour format for the time
 * @param timeLabel the label shown in the time row
 */
@Composable
fun DateTimePicker(
    value: LocalDateTime,
    onValueChange: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    is24Hour: Boolean = false,
    timeLabel: String = "Time",
) {
    var displayedYear by remember(value) { mutableStateOf(value.date.year) }
    var displayedMonth by remember(value) { mutableStateOf(value.date.month) }

    PickerContainer(modifier = modifier.then(if (!enabled) Modifier.alpha(0.45f) else Modifier)) {
        CalendarHeader(
            year = displayedYear,
            month = displayedMonth,
            enabled = enabled,
            onPreviousMonth = {
                val (y, m) = previousMonth(displayedYear, displayedMonth)
                displayedYear = y; displayedMonth = m
            },
            onNextMonth = {
                val (y, m) = nextMonth(displayedYear, displayedMonth)
                displayedYear = y; displayedMonth = m
            },
        )
        CalendarMonth(
            year = displayedYear,
            month = displayedMonth,
            selectedDate = value.date,
            enabled = enabled,
            onDateSelected = { date -> onValueChange(LocalDateTime(date, value.time)) },
        )
        // Separator
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    if (DarwinTheme.colorScheme.isDark) Color.White.copy(alpha = 0.08f)
                    else Color(0xFFC6C6C8)
                ),
        )
        TimePickerRow(
            value = value.time,
            onValueChange = { time -> onValueChange(LocalDateTime(value.date, time)) },
            is24Hour = is24Hour,
            enabled = enabled,
            label = timeLabel,
        )
    }
}

// ===========================================================================
// PickerContainer — shared card container with glass background
// ===========================================================================

@Composable
private fun PickerContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val shape = RoundedCornerShape(13.dp)

    val bgColor = if (isDark) Color(0xFF121212) else Color(0xFFFAFAFA)
    val shadowColor = if (isDark) Color.Black.copy(alpha = 0.16f) else Color.Black.copy(alpha = 0.12f)

    Column(
        modifier = modifier
            .width(370.dp)
            .shadow(
                elevation = 25.dp,
                shape = shape,
                ambientColor = shadowColor,
                spotColor = shadowColor,
            )
            .clip(shape)
            .background(bgColor, shape)
            .padding(horizontal = 16.dp),
    ) {
        content()
    }
}

// ===========================================================================
// CalendarHeader — month/year title + navigation arrows
//
// Sketch: Date Picker Header Collapsed — 326×40dp (338dp with padding)
// Title: "April 2025" 17sp Bold black + ">" chevron 13sp Heavy blue
// Arrows: chevron.left/right blue semibold
// ===========================================================================

@Composable
private fun CalendarHeader(
    year: Int,
    month: Month,
    enabled: Boolean,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {
    val accent = DarwinTheme.colorScheme.accent
    val textPrimary = DarwinTheme.colorScheme.textPrimary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Month Year title
        Text(
            text = "${monthFullName(month)} $year",
            color = textPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.width(4.dp))
        // Disclosure chevron
        Icon(
            imageVector = LucideChevronRight,
            modifier = Modifier.size(13.dp),
            tint = accent,
        )

        Spacer(Modifier.weight(1f))

        // Navigation arrows
        if (enabled) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(
                    imageVector = LucideChevronLeft,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onPreviousMonth,
                        ),
                    tint = accent,
                )
                Icon(
                    imageVector = LucideChevronRight,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = onNextMonth,
                        ),
                    tint = accent,
                )
            }
        }
    }
}

// ===========================================================================
// CalendarMonth — week day headers + date grid
//
// Sketch: Date Picker Month — 350×286dp
// Week labels: SUN-SAT 13sp Bold, tertiary color
// Date cells: 44×44dp, 7 cols spaced at 49dp intervals (5dp gap)
// ===========================================================================

@Composable
private fun CalendarMonth(
    year: Int,
    month: Month,
    selectedDate: LocalDate,
    enabled: Boolean,
    onDateSelected: (LocalDate) -> Unit,
) {
    Column {
        // Week day headers
        DayOfWeekHeaders()

        Spacer(Modifier.height(4.dp))

        // Calendar grid
        CalendarGrid(
            year = year,
            month = month,
            selectedDate = selectedDate,
            enabled = enabled,
            onDateSelected = onDateSelected,
        )

        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun DayOfWeekHeaders() {
    val headerColor = DarwinTheme.colorScheme.textTertiary
    val dayLabels = listOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        dayLabels.forEach { label ->
            Box(
                modifier = Modifier.size(width = 44.dp, height = 18.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label,
                    color = headerColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun CalendarGrid(
    year: Int,
    month: Month,
    selectedDate: LocalDate,
    enabled: Boolean,
    onDateSelected: (LocalDate) -> Unit,
) {
    val today = rememberToday()
    val firstDayOfMonth = LocalDate(year, month, 1)
    val startDayOfWeek = (firstDayOfMonth.dayOfWeek.ordinal + 1) % 7 // Sunday-start
    val daysCount = daysInMonth(month.number, year)
    val totalCells = startDayOfWeek + daysCount
    val weeksCount = (totalCells + 6) / 7

    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        for (week in 0 until weeksCount) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                for (dayOfWeek in 0 until 7) {
                    val cellIndex = week * 7 + dayOfWeek
                    val dayNumber = cellIndex - startDayOfWeek + 1

                    if (dayNumber in 1..daysCount) {
                        val date = LocalDate(year, month, dayNumber)
                        DateCell(
                            day = dayNumber,
                            isSelected = date == selectedDate,
                            isToday = date == today,
                            enabled = enabled,
                            onClick = { onDateSelected(date) },
                        )
                    } else {
                        // Empty cell placeholder
                        Box(Modifier.size(44.dp))
                    }
                }
            }
        }
    }
}

// ===========================================================================
// DateCell — individual day cell
//
// Sketch specs (44×44dp circle):
//   Default:            20sp Medium, primary text
//   Current (today):    20sp Medium, accent blue text
//   Selected:           24sp SemiBold, accent blue text, accent circle at 12% opacity
//   Current+Selected:   24sp SemiBold, white text, solid accent blue circle
// ===========================================================================

@Composable
private fun DateCell(
    day: Int,
    isSelected: Boolean,
    isToday: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val accent = DarwinTheme.colorScheme.accent
    val textPrimary = DarwinTheme.colorScheme.textPrimary

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val isCurrentAndSelected = isToday && isSelected

    val bgColor by animateColorAsState(
        targetValue = when {
            isCurrentAndSelected -> accent
            isSelected -> accent.copy(alpha = 0.12f)
            isHovered && enabled -> accent.copy(alpha = 0.08f)
            else -> Color.Transparent
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "date_cell_bg",
    )

    val textColor = when {
        isCurrentAndSelected -> Color.White
        isSelected || isToday -> accent
        else -> textPrimary
    }

    val fontSize = if (isSelected) 24.sp else 20.sp
    val fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium

    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(bgColor, CircleShape)
            .then(
                if (enabled) {
                    Modifier
                        .hoverable(interactionSource)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = onClick,
                        )
                } else {
                    Modifier
                },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.toString(),
            color = textColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center,
        )
    }
}

// ===========================================================================
// TimePickerRow — inline time picker row
//
// Sketch: Time Picker — 326×52dp
// "Time" label: 17sp Medium, primary text
// Time value pill: capsule bg Fills/Tertiary, text 17sp Medium
// AM/PM: 100×36dp segmented, radius 22dp
// ===========================================================================

@Composable
private fun TimePickerRow(
    value: LocalTime,
    onValueChange: (LocalTime) -> Unit,
    is24Hour: Boolean,
    enabled: Boolean,
    label: String,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val textPrimary = DarwinTheme.colorScheme.textPrimary
    val tertiaryFill = if (isDark) Color(0x3D767680) else Color(0x1F767680)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Label
        Text(
            text = label,
            color = textPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
        )

        Spacer(Modifier.weight(1f))

        // Time value pill
        TimeValuePill(
            value = value,
            is24Hour = is24Hour,
            enabled = enabled,
            onValueChange = onValueChange,
            bgColor = tertiaryFill,
            textColor = textPrimary,
        )

        // AM/PM segmented control (only in 12-hour mode)
        if (!is24Hour) {
            Spacer(Modifier.width(8.dp))
            AmPmSegmentedControl(
                isPm = value.hour >= 12,
                enabled = enabled,
                onToggle = {
                    val newHour = (value.hour + 12) % 24
                    onValueChange(LocalTime(newHour, value.minute))
                },
                bgColor = tertiaryFill,
            )
        }
    }
}

// ===========================================================================
// TimeValuePill — capsule showing "3:00"
//
// Sketch: Time element — 67×34dp, Fills/Tertiary bg, capsule radius
// Text: 17sp Medium, primary color
// ===========================================================================

@Composable
private fun TimeValuePill(
    value: LocalTime,
    is24Hour: Boolean,
    enabled: Boolean,
    onValueChange: (LocalTime) -> Unit,
    bgColor: Color,
    textColor: Color,
) {
    val shape = RoundedCornerShape(percent = 50)

    val displayText = if (is24Hour) {
        "${value.hour.toString().padStart(2, '0')}:${value.minute.toString().padStart(2, '0')}"
    } else {
        val hour12 = when {
            value.hour == 0 -> 12
            value.hour > 12 -> value.hour - 12
            else -> value.hour
        }
        "$hour12:${value.minute.toString().padStart(2, '0')}"
    }

    Box(
        modifier = Modifier
            .height(34.dp)
            .widthIn(min = 67.dp)
            .clip(shape)
            .background(bgColor, shape)
            .then(
                if (enabled) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            // Increment minutes by 1 on click (simple interaction)
                            val newMinute = (value.minute + 1) % 60
                            val newHour = if (value.minute == 59) (value.hour + 1) % 24 else value.hour
                            onValueChange(LocalTime(newHour, newMinute))
                        },
                    )
                } else {
                    Modifier
                },
            )
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = displayText,
            color = textColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

// ===========================================================================
// AmPmSegmentedControl — AM/PM toggle
//
// Sketch: AM-PM — 100×36dp, Fills/Tertiary bg, radius 22dp
// Active segment: 44×28dp, white bg (light) / #6c6c71 (dark), radius 20dp
// Active text: 14sp Bold
// Inactive text: 14sp SemiBold
// ===========================================================================

@Composable
private fun AmPmSegmentedControl(
    isPm: Boolean,
    enabled: Boolean,
    onToggle: () -> Unit,
    bgColor: Color,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val textPrimary = DarwinTheme.colorScheme.textPrimary
    val activeBg = if (isDark) Color(0xFF6C6C71) else Color.White
    val activeShape = RoundedCornerShape(20.dp)
    val containerShape = RoundedCornerShape(22.dp)

    Row(
        modifier = Modifier
            .width(100.dp)
            .height(36.dp)
            .clip(containerShape)
            .background(bgColor, containerShape)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // AM button
        Box(
            modifier = Modifier
                .weight(1f)
                .height(28.dp)
                .clip(activeShape)
                .background(if (!isPm) activeBg else Color.Transparent, activeShape)
                .then(
                    if (enabled) {
                        Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { if (isPm) onToggle() },
                        )
                    } else {
                        Modifier
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "AM",
                color = textPrimary,
                fontSize = 14.sp,
                fontWeight = if (!isPm) FontWeight.Bold else FontWeight.SemiBold,
            )
        }

        // PM button
        Box(
            modifier = Modifier
                .weight(1f)
                .height(28.dp)
                .clip(activeShape)
                .background(if (isPm) activeBg else Color.Transparent, activeShape)
                .then(
                    if (enabled) {
                        Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { if (!isPm) onToggle() },
                        )
                    } else {
                        Modifier
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "PM",
                color = textPrimary,
                fontSize = 14.sp,
                fontWeight = if (isPm) FontWeight.Bold else FontWeight.SemiBold,
            )
        }
    }
}

// ===========================================================================
// WheelTimePicker — iOS-style spinning wheel time picker
//
// Sketch: Wheel / Time Picker — 297×215dp
// Selection indicator: 297×34dp, radius 17dp, Fills/Quaternary bg
// 3 columns: Hour | Minute | AM/PM (or 2 cols in 24h mode)
// Items fade out with distance from center
// ===========================================================================

/**
 * A macOS/iOS-style wheel time picker with scrollable columns.
 *
 * @param value the currently selected time
 * @param onValueChange called when the time changes
 * @param modifier the [Modifier] to be applied
 * @param is24Hour whether to use 24-hour format
 */
@Composable
fun WheelTimePicker(
    value: LocalTime,
    onValueChange: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    is24Hour: Boolean = false,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val containerBg = if (isDark) Color(0xFF121212) else Color(0xFFFAFAFA)
    val indicatorBg = if (isDark) Color(0x2E767680) else Color(0x14747480)
    val indicatorShape = RoundedCornerShape(17.dp)

    val itemHeight = 34.dp
    val visibleItems = 7
    val totalHeight = itemHeight * visibleItems

    val hours = if (is24Hour) (0..23).toList() else (1..12).toList()
    val minutes = (0..59).toList()

    val initialHourIndex = if (is24Hour) {
        value.hour
    } else {
        val h12 = when {
            value.hour == 0 -> 12
            value.hour > 12 -> value.hour - 12
            else -> value.hour
        }
        h12 - 1
    }

    var selectedHour by remember { mutableStateOf(value.hour) }
    var selectedMinute by remember { mutableStateOf(value.minute) }
    var isPm by remember { mutableStateOf(value.hour >= 12) }

    Box(
        modifier = modifier.width(297.dp).height(totalHeight),
        contentAlignment = Alignment.Center,
    ) {
        // Selection indicator bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .clip(indicatorShape)
                .background(indicatorBg, indicatorShape),
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            // Hour column
            WheelColumn(
                items = hours,
                initialIndex = initialHourIndex,
                itemHeight = itemHeight,
                visibleItems = visibleItems,
                fadeBgColor = containerBg,
                onSelectedChanged = { index ->
                    val pickedHour = hours[index]
                    selectedHour = if (is24Hour) {
                        pickedHour
                    } else {
                        to24Hour(pickedHour, isPm)
                    }
                    onValueChange(LocalTime(selectedHour, selectedMinute))
                },
                textForItem = { item ->
                    if (is24Hour) item.toString().padStart(2, '0') else item.toString()
                },
                modifier = Modifier.weight(1f),
            )

            // Minute column
            WheelColumn(
                items = minutes,
                initialIndex = value.minute,
                itemHeight = itemHeight,
                visibleItems = visibleItems,
                fadeBgColor = containerBg,
                onSelectedChanged = { index ->
                    selectedMinute = minutes[index]
                    onValueChange(LocalTime(selectedHour, selectedMinute))
                },
                textForItem = { item -> item.toString().padStart(2, '0') },
                modifier = Modifier.weight(1f),
            )

            // AM/PM column
            if (!is24Hour) {
                val amPmItems = listOf("AM", "PM")
                WheelColumn(
                    items = amPmItems,
                    initialIndex = if (isPm) 1 else 0,
                    itemHeight = itemHeight,
                    visibleItems = visibleItems,
                    fadeBgColor = containerBg,
                    onSelectedChanged = { index ->
                        val newIsPm = index == 1
                        if (newIsPm != isPm) {
                            isPm = newIsPm
                            val hour12 = when {
                                selectedHour == 0 -> 12
                                selectedHour > 12 -> selectedHour - 12
                                selectedHour == 12 -> 12
                                else -> selectedHour
                            }
                            selectedHour = to24Hour(hour12, isPm)
                            onValueChange(LocalTime(selectedHour, selectedMinute))
                        }
                    },
                    textForItem = { it },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

// ===========================================================================
// WheelColumn — single scrollable wheel column with snap and fade
// ===========================================================================

@Composable
private fun <T> WheelColumn(
    items: List<T>,
    initialIndex: Int,
    itemHeight: androidx.compose.ui.unit.Dp,
    visibleItems: Int,
    fadeBgColor: Color,
    onSelectedChanged: (Int) -> Unit,
    textForItem: (T) -> String,
    modifier: Modifier = Modifier,
) {
    val halfVisible = visibleItems / 2
    val totalHeight = itemHeight * visibleItems

    val useVirtualScroll = items.size > visibleItems
    val paddedCount = if (useVirtualScroll) items.size * 1000 else items.size + halfVisible * 2
    val initialScroll = if (useVirtualScroll) {
        (paddedCount / 2) - ((paddedCount / 2) % items.size) + initialIndex - halfVisible
    } else {
        initialIndex
    }

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialScroll.coerceAtLeast(0))
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val textPrimary = DarwinTheme.colorScheme.textPrimary

    // Fade-to-background gradients for top and bottom edges
    val topFade = Brush.verticalGradient(0f to fadeBgColor, 1f to Color.Transparent)
    val bottomFade = Brush.verticalGradient(0f to Color.Transparent, 1f to fadeBgColor)

    val currentCenter by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex + halfVisible
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { currentCenter }
            .distinctUntilChanged()
            .collect { centerIndex ->
                val realIndex = if (useVirtualScroll) {
                    centerIndex % items.size
                } else {
                    (centerIndex - halfVisible).coerceIn(0, items.size - 1)
                }
                onSelectedChanged(realIndex)
            }
    }

    Box(
        modifier = modifier.height(totalHeight),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            modifier = Modifier.height(totalHeight),
        ) {
            items(
                count = paddedCount,
                key = { it },
            ) { index ->
                val isSpacerItem = !useVirtualScroll && (index < halfVisible || index >= halfVisible + items.size)
                val realIndex = if (useVirtualScroll) {
                    index % items.size
                } else {
                    (index - halfVisible).coerceIn(0, items.size - 1)
                }
                val distFromCenter = kotlin.math.abs(index - currentCenter)
                val alpha = when {
                    isSpacerItem -> 0f
                    distFromCenter == 0 -> 1f
                    distFromCenter == 1 -> 0.6f
                    distFromCenter == 2 -> 0.35f
                    else -> 0.15f
                }
                val isSelected = distFromCenter == 0 && !isSpacerItem

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                        .alpha(alpha),
                    contentAlignment = Alignment.Center,
                ) {
                    if (!isSpacerItem) {
                        Text(
                            text = textForItem(items[realIndex]),
                            color = textPrimary,
                            fontSize = if (isSelected) 23.sp else 20.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }

        // Top fade overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight * 2.5f)
                .align(Alignment.TopCenter)
                .background(topFade),
        )
        // Bottom fade overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight * 2.5f)
                .align(Alignment.BottomCenter)
                .background(bottomFade),
        )
    }
}

private fun to24Hour(hour12: Int, isPm: Boolean): Int {
    return when {
        !isPm && hour12 == 12 -> 0
        isPm && hour12 == 12 -> 12
        isPm -> hour12 + 12
        else -> hour12
    }
}

// ===========================================================================
// PickerTriggerButton — capsule trigger that opens a picker in a popup
//
// Sketch: Buttons / Date / Idle — 104×34dp, Fills/Tertiary bg, capsule
//         Buttons / Time / Idle — 90×34dp, same styling
// Idle: text Labels/Primary 17sp Medium
// Selected (expanded): text Accent Blue 17sp Medium
// ===========================================================================

@Composable
private fun PickerTriggerButton(
    text: String,
    expanded: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDark = DarwinTheme.colorScheme.isDark
    val accent = DarwinTheme.colorScheme.accent
    val textPrimary = DarwinTheme.colorScheme.textPrimary
    val tertiaryFill = if (isDark) Color(0x3D767680) else Color(0x1F767680)

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val bgColor by animateColorAsState(
        targetValue = when {
            !enabled -> tertiaryFill.copy(alpha = tertiaryFill.alpha * 0.5f)
            isPressed -> tertiaryFill.copy(alpha = tertiaryFill.alpha * 1.5f)
            isHovered -> tertiaryFill.copy(alpha = tertiaryFill.alpha * 1.2f)
            else -> tertiaryFill
        },
        animationSpec = darwinSpring(DarwinSpringPreset.Snappy),
        label = "picker_trigger_bg",
    )

    val textColor = when {
        !enabled -> textPrimary.copy(alpha = 0.45f)
        expanded -> accent
        else -> textPrimary
    }

    val shape = RoundedCornerShape(percent = 50)

    Box(
        modifier = modifier
            .height(34.dp)
            .clip(shape)
            .background(bgColor, shape)
            .then(
                if (enabled) {
                    Modifier
                        .hoverable(interactionSource)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            role = Role.Button,
                            onClick = onClick,
                        )
                } else {
                    Modifier
                },
            )
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 17.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

// ===========================================================================
// DatePickerButton — capsule trigger that opens a DatePicker in a popup
// ===========================================================================

/**
 * A macOS-style compact date picker button.
 *
 * Displays a capsule trigger showing the formatted date (e.g. "April 2025").
 * When clicked, a calendar popover opens for day selection.
 *
 * @param value the currently selected date
 * @param onValueChange called when a new date is selected
 * @param modifier the [Modifier] to be applied
 * @param enabled whether the picker is enabled
 */
@Composable
fun DatePickerButton(
    value: LocalDate,
    onValueChange: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }
    var anchorSize by remember { mutableStateOf(IntSize.Zero) }

    val triggerText = "${monthFullName(value.month)} ${value.year}"

    Box(modifier = modifier) {
        PickerTriggerButton(
            text = triggerText,
            expanded = expanded,
            enabled = enabled,
            onClick = { if (enabled) expanded = !expanded },
            modifier = Modifier.onSizeChanged { anchorSize = it },
        )

        if (expanded) {
            PickerPopover(
                onDismissRequest = { expanded = false },
                anchorSize = anchorSize,
            ) {
                DatePicker(
                    value = value,
                    onValueChange = onValueChange,
                    enabled = enabled,
                )
            }
        }
    }
}

// ===========================================================================
// TimePickerButton — capsule trigger that opens a TimePicker in a popup
// ===========================================================================

/**
 * A macOS-style compact time picker button.
 *
 * Displays a capsule trigger showing the formatted time (e.g. "8:00 AM").
 * When clicked, a time picker popover opens.
 *
 * @param value the currently selected time
 * @param onValueChange called when the time changes
 * @param modifier the [Modifier] to be applied
 * @param enabled whether the picker is enabled
 * @param is24Hour whether to use 24-hour format
 */
@Composable
fun TimePickerButton(
    value: LocalTime,
    onValueChange: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    is24Hour: Boolean = false,
) {
    var expanded by remember { mutableStateOf(false) }
    var anchorSize by remember { mutableStateOf(IntSize.Zero) }

    val triggerText = formatTime(value, is24Hour)

    Box(modifier = modifier) {
        PickerTriggerButton(
            text = triggerText,
            expanded = expanded,
            enabled = enabled,
            onClick = { if (enabled) expanded = !expanded },
            modifier = Modifier.onSizeChanged { anchorSize = it },
        )

        if (expanded) {
            PickerPopover(
                onDismissRequest = { expanded = false },
                anchorSize = anchorSize,
            ) {
                PickerContainer {
                    WheelTimePicker(
                        value = value,
                        onValueChange = onValueChange,
                        is24Hour = is24Hour,
                    )
                }
            }
        }
    }
}

// ===========================================================================
// DateTimePickerButton — two capsule triggers for date + time
// ===========================================================================

/**
 * A macOS-style compact date and time picker with trigger buttons.
 *
 * Displays two capsule triggers: one for the date and one for the time.
 * Each opens a corresponding picker popover when clicked.
 *
 * @param value the currently selected date and time
 * @param onValueChange called when the value changes
 * @param modifier the [Modifier] to be applied
 * @param enabled whether the picker is enabled
 * @param is24Hour whether to use 24-hour format for the time
 * @param label optional label displayed to the left
 */
@Composable
fun DateTimePickerButton(
    value: LocalDateTime,
    onValueChange: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    is24Hour: Boolean = false,
    label: String? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (label != null) {
            Text(
                text = label,
                color = DarwinTheme.colorScheme.textPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
            )
            Spacer(Modifier.weight(1f))
        }
        DatePickerButton(
            value = value.date,
            onValueChange = { date -> onValueChange(LocalDateTime(date, value.time)) },
            enabled = enabled,
        )
        TimePickerButton(
            value = value.time,
            onValueChange = { time -> onValueChange(LocalDateTime(value.date, time)) },
            enabled = enabled,
            is24Hour = is24Hour,
        )
    }
}

// ===========================================================================
// PickerPopover — popup container for picker trigger buttons
// ===========================================================================

@Composable
private fun PickerPopover(
    onDismissRequest: () -> Unit,
    anchorSize: IntSize,
    content: @Composable () -> Unit,
) {
    val density = androidx.compose.ui.platform.LocalDensity.current
    val gapPx = with(density) { 4.dp.roundToPx() }

    Popup(
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
        offset = IntOffset(0, anchorSize.height + gapPx),
    ) {
        content()
    }
}

// ===========================================================================
// Utility functions
// ===========================================================================

private fun monthFullName(month: Month): String = when (month) {
    Month.JANUARY -> "January"
    Month.FEBRUARY -> "February"
    Month.MARCH -> "March"
    Month.APRIL -> "April"
    Month.MAY -> "May"
    Month.JUNE -> "June"
    Month.JULY -> "July"
    Month.AUGUST -> "August"
    Month.SEPTEMBER -> "September"
    Month.OCTOBER -> "October"
    Month.NOVEMBER -> "November"
    Month.DECEMBER -> "December"
}

private fun formatTime(time: LocalTime, is24Hour: Boolean): String {
    return if (is24Hour) {
        "${time.hour.toString().padStart(2, '0')}:${time.minute.toString().padStart(2, '0')}"
    } else {
        val hour12 = when {
            time.hour == 0 -> 12
            time.hour > 12 -> time.hour - 12
            else -> time.hour
        }
        val amPm = if (time.hour >= 12) "PM" else "AM"
        "$hour12:${time.minute.toString().padStart(2, '0')} $amPm"
    }
}

private fun previousMonth(year: Int, month: Month): Pair<Int, Month> {
    return if (month == Month.JANUARY) {
        Pair(year - 1, Month.DECEMBER)
    } else {
        Pair(year, Month.entries[month.ordinal - 1])
    }
}

private fun nextMonth(year: Int, month: Month): Pair<Int, Month> {
    return if (month == Month.DECEMBER) {
        Pair(year + 1, Month.JANUARY)
    } else {
        Pair(year, Month.entries[month.ordinal + 1])
    }
}

private fun daysInMonth(month: Int, year: Int): Int {
    return when (month) {
        1 -> 31
        2 -> if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) 29 else 28
        3 -> 31
        4 -> 30
        5 -> 31
        6 -> 30
        7 -> 31
        8 -> 31
        9 -> 30
        10 -> 31
        11 -> 30
        12 -> 31
        else -> 30
    }
}

@Composable
private fun rememberToday(): LocalDate {
    return remember {
        kotlin.time.Clock.System.todayIn(TimeZone.currentSystemDefault())
    }
}
