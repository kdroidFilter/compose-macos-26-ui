package io.github.kdroidfilter.darwinui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import io.github.kdroidfilter.darwinui.components.ContentDialog
import io.github.kdroidfilter.darwinui.components.ContentDialogButton
import io.github.kdroidfilter.darwinui.components.DialogSize
import io.github.kdroidfilter.darwinui.components.Text
import io.github.kdroidfilter.darwinui.icons.Icon
import io.github.kdroidfilter.darwinui.icons.LucideCalendar
import io.github.kdroidfilter.darwinui.icons.LucideChevronDown
import io.github.kdroidfilter.darwinui.icons.LucideChevronLeft
import io.github.kdroidfilter.darwinui.icons.LucideChevronRight
import io.github.kdroidfilter.darwinui.theme.Blue500
import io.github.kdroidfilter.darwinui.theme.DarwinTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

// =============================================================================
// Public types
// =============================================================================

/** Whether this is a one-off or repeating event. */
enum class EventType(val label: String) {
    Single("Single Event"),
    Recurring("Recurring Event"),
}

/** Recurrence frequency for recurring events. */
enum class FrequencyType(val label: String) {
    Daily("day"),
    Weekly("week"),
    Monthly("month"),
    Weekends("weekends"),
    SpecificDay("specific day"),
}

/** Date precision level. */
enum class DateMode(val label: String) {
    DateTime("Date & Time"),
    DateOnly("Date only"),
    MonthOnly("Month only"),
    YearOnly("Year only"),
}

/** How time is specified. */
enum class TimeMode(val label: String) {
    None("No time"),
    SpecificTime("Specific time"),
    AllDay("All day"),
}

/** Day of week for [FrequencyType.SpecificDay]. */
enum class DayOfWeekOption(val label: String) {
    Monday("Monday"),
    Tuesday("Tuesday"),
    Wednesday("Wednesday"),
    Thursday("Thursday"),
    Friday("Friday"),
    Saturday("Saturday"),
    Sunday("Sunday"),
}

/** Full configuration produced by the date select dialog. */
data class DateConfig(
    val eventType: EventType = EventType.Single,
    val dateMode: DateMode = DateMode.DateTime,
    val timeMode: TimeMode = TimeMode.SpecificTime,
    val selectedDate: LocalDate? = null,
    val hour: Int = 9,
    val minute: Int = 0,
    val frequency: FrequencyType = FrequencyType.Daily,
    val dayOfWeek: DayOfWeekOption = DayOfWeekOption.Monday,
    val endDate: LocalDate? = null,
    val endHour: Int = 17,
    val endMinute: Int = 0,
)

// =============================================================================
// Color helpers
// =============================================================================

private val Blue400 = Color(0xFF60A5FA)
private val BluePillBg = Blue500.copy(alpha = 0.15f)
private val BluePillBorder = Blue500.copy(alpha = 0.20f)

// =============================================================================
// InlineSelect — blue pill dropdown
// =============================================================================

@Composable
private fun <T> InlineSelect(
    options: List<T>,
    selected: T,
    onSelect: (T) -> Unit,
    labelOf: (T) -> String,
) {
    val colors = DarwinTheme.colors
    val density = LocalDensity.current
    var expanded by remember { mutableStateOf(false) }
    val pillShape = RoundedCornerShape(6.dp)
    var triggerHeightPx by remember { mutableStateOf(0) }

    val interactionSource = remember { MutableInteractionSource() }

    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
    )

    val dropdownShape = RoundedCornerShape(8.dp)
    val dropBg = if (colors.isDark) Color(0xFF1C1C1F) else Color(0xFFFAFAFA)
    val dropBorder = if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    Box {
        // Trigger pill
        Row(
            modifier = Modifier
                .onGloballyPositioned { triggerHeightPx = it.size.height }
                .clip(pillShape)
                .background(BluePillBg, pillShape)
                .border(1.dp, BluePillBorder, pillShape)
                .hoverable(interactionSource)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                ) { expanded = !expanded }
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = labelOf(selected),
                style = DarwinTheme.typography.bodySmall,
                color = Blue400,
                fontWeight = FontWeight.Medium,
            )
            Icon(
                imageVector = LucideChevronDown,
                tint = Blue400,
                modifier = Modifier.size(12.dp).rotate(chevronRotation),
            )
        }

        // Dropdown popup — positioned below trigger, sized by content
        if (expanded) {
            val gapPx = with(density) { 4.dp.roundToPx() }
            Popup(
                offset = IntOffset(0, triggerHeightPx + gapPx),
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true),
            ) {
                Column(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .widthIn(min = 160.dp)
                        .shadow(8.dp, dropdownShape)
                        .clip(dropdownShape)
                        .background(dropBg, dropdownShape)
                        .border(1.dp, dropBorder, dropdownShape)
                        .padding(4.dp),
                ) {
                    options.forEach { option ->
                        val optInteraction = remember { MutableInteractionSource() }
                        val optHovered by optInteraction.collectIsHoveredAsState()
                        val isSelected = option == selected

                        val optBg = when {
                            isSelected -> BluePillBg
                            optHovered -> colors.muted
                            else -> Color.Transparent
                        }
                        val optColor = if (isSelected) Blue400 else colors.textPrimary

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(4.dp))
                                .background(optBg)
                                .hoverable(optInteraction)
                                .clickable(
                                    indication = null,
                                    interactionSource = optInteraction,
                                ) {
                                    onSelect(option)
                                    expanded = false
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                        ) {
                            Text(
                                text = labelOf(option),
                                style = DarwinTheme.typography.bodySmall,
                                color = optColor,
                            )
                        }
                    }
                }
            }
        }
    }
}

// =============================================================================
// DatePickerButton — blue pill that opens a CalendarGrid
// =============================================================================

@Composable
private fun DatePickerButton(
    date: LocalDate?,
    dateMode: DateMode,
    onDateSelected: (LocalDate) -> Unit,
) {
    val density = LocalDensity.current
    var expanded by remember { mutableStateOf(false) }
    val pillShape = RoundedCornerShape(6.dp)
    val interactionSource = remember { MutableInteractionSource() }
    var triggerHeightPx by remember { mutableStateOf(0) }
    var triggerWidthPx by remember { mutableStateOf(0) }

    val label = when {
        date == null -> "pick a date"
        dateMode == DateMode.YearOnly -> "${date.year}"
        dateMode == DateMode.MonthOnly -> "${date.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${date.year}"
        else -> "${date.dayOfMonth} ${date.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)} ${date.year}"
    }

    Box {
        Row(
            modifier = Modifier
                .onGloballyPositioned {
                    triggerHeightPx = it.size.height
                    triggerWidthPx = it.size.width
                }
                .clip(pillShape)
                .background(BluePillBg, pillShape)
                .border(1.dp, BluePillBorder, pillShape)
                .hoverable(interactionSource)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                ) { expanded = !expanded }
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(imageVector = LucideCalendar, tint = Blue400, modifier = Modifier.size(12.dp))
            Text(
                text = label,
                style = DarwinTheme.typography.bodySmall,
                color = Blue400,
                fontWeight = FontWeight.Medium,
            )
        }

        if (expanded) {
            val gapPx = with(density) { 4.dp.roundToPx() }
            val calendarWidthPx = with(density) { 280.dp.roundToPx() }
            val offsetX = (triggerWidthPx - calendarWidthPx) / 2
            Popup(
                offset = IntOffset(offsetX, triggerHeightPx + gapPx),
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true),
            ) {
                CalendarGrid(
                    selectedDate = date,
                    onDateSelected = { d ->
                        onDateSelected(d)
                        expanded = false
                    },
                )
            }
        }
    }
}

// =============================================================================
// CalendarGrid — 7-column month calendar (ISO week, Monday start)
// =============================================================================

@Composable
private fun CalendarGrid(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
) {
    val colors = DarwinTheme.colors
    val panelShape = RoundedCornerShape(10.dp)
    val panelBg = if (colors.isDark) Color(0xFF1C1C1F) else Color(0xFFFAFAFA)
    val panelBorder = if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    val today = remember { Clock.System.todayIn(TimeZone.currentSystemDefault()) }
    var displayMonth by remember { mutableStateOf(selectedDate?.month ?: today.month) }
    var displayYear by remember { mutableStateOf(selectedDate?.year ?: today.year) }

    val weekdayHeaders = listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")

    // Compute the first day of the month and the grid
    val firstOfMonth = LocalDate(displayYear, displayMonth, 1)
    // dayOfWeek: Monday=1..Sunday=7 (ISO)
    // Monday=0..Sunday=6 in ordinal, we want Monday=1..Sunday=7
    val startDayOfWeek = firstOfMonth.dayOfWeek.ordinal + 1
    // Number of days preceding the 1st in the grid
    val leadingBlanks = (startDayOfWeek - 1)

    // Build 42-cell grid (6 rows x 7 cols)
    val gridDates = buildList {
        // Previous month's trailing days
        val prevMonthEnd = firstOfMonth.plus(-1, DateTimeUnit.DAY)
        for (i in leadingBlanks downTo 1) {
            add(prevMonthEnd.plus(-(i - 1), DateTimeUnit.DAY))
        }
        // Current month
        var d = firstOfMonth
        while (d.month == firstOfMonth.month) {
            add(d)
            d = d.plus(1, DateTimeUnit.DAY)
        }
        // Fill remainder
        var nextDay = d
        while (size < 42) {
            add(nextDay)
            nextDay = nextDay.plus(1, DateTimeUnit.DAY)
        }
    }

    Column(
        modifier = Modifier
            .width(280.dp)
            .shadow(8.dp, panelShape)
            .clip(panelShape)
            .background(panelBg, panelShape)
            .border(1.dp, panelBorder, panelShape)
            .padding(12.dp),
    ) {
        // Month navigation header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val navInteraction = remember { MutableInteractionSource() }
            val navHovered by navInteraction.collectIsHoveredAsState()

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .hoverable(navInteraction)
                    .background(if (navHovered) colors.muted else Color.Transparent, RoundedCornerShape(6.dp))
                    .clickable(indication = null, interactionSource = navInteraction) {
                        if (displayMonth.number == 1) {
                            displayMonth = kotlinx.datetime.Month(12)
                            displayYear -= 1
                        } else {
                            displayMonth = kotlinx.datetime.Month(displayMonth.number - 1)
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(imageVector = LucideChevronLeft, tint = colors.textSecondary, modifier = Modifier.size(14.dp))
            }

            Text(
                text = "${displayMonth.name.lowercase().replaceFirstChar { it.uppercase() }} $displayYear",
                style = DarwinTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.textPrimary,
            )

            val navRightInteraction = remember { MutableInteractionSource() }
            val navRightHovered by navRightInteraction.collectIsHoveredAsState()

            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .hoverable(navRightInteraction)
                    .background(if (navRightHovered) colors.muted else Color.Transparent, RoundedCornerShape(6.dp))
                    .clickable(indication = null, interactionSource = navRightInteraction) {
                        if (displayMonth.number == 12) {
                            displayMonth = kotlinx.datetime.Month(1)
                            displayYear += 1
                        } else {
                            displayMonth = kotlinx.datetime.Month(displayMonth.number + 1)
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                Icon(imageVector = LucideChevronRight, tint = colors.textSecondary, modifier = Modifier.size(14.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Weekday headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            weekdayHeaders.forEach { header ->
                Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = header,
                        style = DarwinTheme.typography.labelSmall,
                        color = colors.textTertiary,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Day grid (6 rows)
        for (row in 0 until 6) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                for (col in 0 until 7) {
                    val idx = row * 7 + col
                    val cellDate = gridDates[idx]
                    val isCurrentMonth = cellDate.month == displayMonth && cellDate.year == displayYear
                    val isSelected = cellDate == selectedDate
                    val isToday = cellDate == today

                    val cellInteraction = remember { MutableInteractionSource() }
                    val cellHovered by cellInteraction.collectIsHoveredAsState()

                    val cellBg = when {
                        isSelected -> Blue500
                        cellHovered && isCurrentMonth -> colors.muted
                        else -> Color.Transparent
                    }
                    val textColor = when {
                        isSelected -> Color.White
                        !isCurrentMonth -> colors.textPrimary.copy(alpha = 0.3f)
                        isToday -> Blue400
                        else -> colors.textPrimary
                    }

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(cellBg, RoundedCornerShape(6.dp))
                            .hoverable(cellInteraction)
                            .clickable(indication = null, interactionSource = cellInteraction) {
                                onDateSelected(cellDate)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "${cellDate.dayOfMonth}",
                            style = DarwinTheme.typography.bodySmall,
                            color = textColor,
                            fontWeight = if (isToday || isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        )
                    }
                }
            }
        }
    }
}

// =============================================================================
// TimePickerButton — blue pill with HH:MM popup
// =============================================================================

@Composable
private fun TimePickerButton(
    hour: Int,
    minute: Int,
    onTimeChanged: (Int, Int) -> Unit,
) {
    val colors = DarwinTheme.colors
    val density = LocalDensity.current
    var expanded by remember { mutableStateOf(false) }
    val pillShape = RoundedCornerShape(6.dp)
    val interactionSource = remember { MutableInteractionSource() }
    var triggerHeightPx by remember { mutableStateOf(0) }

    val label = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"

    val popupShape = RoundedCornerShape(8.dp)
    val popBg = if (colors.isDark) Color(0xFF1C1C1F) else Color(0xFFFAFAFA)
    val popBorder = if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.10f)

    Box {
        Row(
            modifier = Modifier
                .onGloballyPositioned { triggerHeightPx = it.size.height }
                .clip(pillShape)
                .background(BluePillBg, pillShape)
                .border(1.dp, BluePillBorder, pillShape)
                .hoverable(interactionSource)
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,
                ) { expanded = !expanded }
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = label,
                style = DarwinTheme.typography.bodySmall,
                color = Blue400,
                fontWeight = FontWeight.Medium,
            )
        }

        if (expanded) {
            val gapPx = with(density) { 4.dp.roundToPx() }
            Popup(
                offset = IntOffset(0, triggerHeightPx + gapPx),
                onDismissRequest = { expanded = false },
                properties = PopupProperties(focusable = true),
            ) {
                var hourText by remember { mutableStateOf(hour.toString().padStart(2, '0')) }
                var minuteText by remember { mutableStateOf(minute.toString().padStart(2, '0')) }

                Row(
                    modifier = Modifier
                        .shadow(8.dp, popupShape)
                        .clip(popupShape)
                        .background(popBg, popupShape)
                        .border(1.dp, popBorder, popupShape)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    val fieldModifier = Modifier
                        .width(40.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(colors.inputBackground, RoundedCornerShape(6.dp))
                        .border(1.dp, colors.inputBorder, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp)

                    BasicTextField(
                        value = hourText,
                        onValueChange = { raw ->
                            val filtered = raw.filter { it.isDigit() }.take(2)
                            hourText = filtered
                            val h = filtered.toIntOrNull()
                            if (h != null && h in 0..23) {
                                onTimeChanged(h, minuteText.toIntOrNull() ?: minute)
                            }
                        },
                        modifier = fieldModifier,
                        textStyle = TextStyle(
                            color = colors.textPrimary,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        cursorBrush = SolidColor(Blue500),
                    )

                    Text(
                        text = ":",
                        style = DarwinTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.textSecondary,
                    )

                    BasicTextField(
                        value = minuteText,
                        onValueChange = { raw ->
                            val filtered = raw.filter { it.isDigit() }.take(2)
                            minuteText = filtered
                            val m = filtered.toIntOrNull()
                            if (m != null && m in 0..59) {
                                onTimeChanged(hourText.toIntOrNull() ?: hour, m)
                            }
                        },
                        modifier = fieldModifier,
                        textStyle = TextStyle(
                            color = colors.textPrimary,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        cursorBrush = SolidColor(Blue500),
                    )
                }
            }
        }
    }
}

// =============================================================================
// QuestionnaireContent — sentence-builder UI using FlowRow
// =============================================================================

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun QuestionnaireContent(
    config: DateConfig,
    onConfigChange: (DateConfig) -> Unit,
) {
    val colors = DarwinTheme.colors

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(colors.muted, RoundedCornerShape(8.dp))
            .border(1.dp, colors.border, RoundedCornerShape(8.dp))
            .padding(16.dp),
    ) {
        when (config.eventType) {
            EventType.Single -> SingleEventQuestionnaire(config, onConfigChange)
            EventType.Recurring -> RecurringEventQuestionnaire(config, onConfigChange)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SingleEventQuestionnaire(
    config: DateConfig,
    onConfigChange: (DateConfig) -> Unit,
) {
    val colors = DarwinTheme.colors
    val textColor = colors.textSecondary

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        // "Schedule a"
        TextChip("Schedule a", textColor)

        // Date mode selector
        InlineSelect(
            options = DateMode.entries.toList(),
            selected = config.dateMode,
            onSelect = { onConfigChange(config.copy(dateMode = it)) },
            labelOf = { it.label },
        )

        when (config.dateMode) {
            DateMode.DateTime -> {
                TextChip("event on", textColor)
                DatePickerButton(
                    date = config.selectedDate,
                    dateMode = config.dateMode,
                    onDateSelected = { onConfigChange(config.copy(selectedDate = it)) },
                )
                TextChip("at", textColor)
                TimePickerButton(
                    hour = config.hour,
                    minute = config.minute,
                    onTimeChanged = { h, m -> onConfigChange(config.copy(hour = h, minute = m)) },
                )
            }

            DateMode.DateOnly -> {
                TextChip("event on", textColor)
                DatePickerButton(
                    date = config.selectedDate,
                    dateMode = config.dateMode,
                    onDateSelected = { onConfigChange(config.copy(selectedDate = it)) },
                )
            }

            DateMode.MonthOnly -> {
                TextChip("event in", textColor)
                DatePickerButton(
                    date = config.selectedDate,
                    dateMode = config.dateMode,
                    onDateSelected = { onConfigChange(config.copy(selectedDate = it)) },
                )
            }

            DateMode.YearOnly -> {
                TextChip("event in", textColor)
                DatePickerButton(
                    date = config.selectedDate,
                    dateMode = config.dateMode,
                    onDateSelected = { onConfigChange(config.copy(selectedDate = it)) },
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecurringEventQuestionnaire(
    config: DateConfig,
    onConfigChange: (DateConfig) -> Unit,
) {
    val colors = DarwinTheme.colors
    val textColor = colors.textSecondary

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextChip("Every", textColor)

        if (config.frequency == FrequencyType.SpecificDay) {
            InlineSelect(
                options = FrequencyType.entries.toList(),
                selected = config.frequency,
                onSelect = { onConfigChange(config.copy(frequency = it)) },
                labelOf = { it.label },
            )

            TextChip("on", textColor)

            InlineSelect(
                options = DayOfWeekOption.entries.toList(),
                selected = config.dayOfWeek,
                onSelect = { onConfigChange(config.copy(dayOfWeek = it)) },
                labelOf = { it.label },
            )
        } else {
            InlineSelect(
                options = FrequencyType.entries.toList(),
                selected = config.frequency,
                onSelect = { onConfigChange(config.copy(frequency = it)) },
                labelOf = { it.label },
            )
        }

        TextChip("starting from", textColor)

        DatePickerButton(
            date = config.selectedDate,
            dateMode = DateMode.DateOnly,
            onDateSelected = { onConfigChange(config.copy(selectedDate = it)) },
        )

        TextChip("at", textColor)

        TimePickerButton(
            hour = config.hour,
            minute = config.minute,
            onTimeChanged = { h, m -> onConfigChange(config.copy(hour = h, minute = m)) },
        )

        TextChip("to", textColor)

        DatePickerButton(
            date = config.endDate,
            dateMode = DateMode.DateOnly,
            onDateSelected = { onConfigChange(config.copy(endDate = it)) },
        )

        TextChip("at", textColor)

        TimePickerButton(
            hour = config.endHour,
            minute = config.endMinute,
            onTimeChanged = { h, m -> onConfigChange(config.copy(endHour = h, endMinute = m)) },
        )
    }
}

/** Simple inline text fragment for the sentence builder. */
@Composable
private fun TextChip(text: String, color: Color) {
    Text(
        text = text,
        style = DarwinTheme.typography.bodySmall,
        color = color,
        modifier = Modifier.padding(vertical = 4.dp),
    )
}

// =============================================================================
// Summary helper
// =============================================================================

private fun formatSummary(config: DateConfig): String {
    val datePart = config.selectedDate?.let { d ->
        when (config.dateMode) {
            DateMode.YearOnly -> "${d.year}"
            DateMode.MonthOnly -> "${d.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${d.year}"
            else -> "${d.dayOfMonth} ${d.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)} ${d.year}"
        }
    } ?: "Not set"

    val timePart = when {
        config.eventType == EventType.Single && (
            config.dateMode == DateMode.MonthOnly ||
                config.dateMode == DateMode.YearOnly ||
                config.dateMode == DateMode.DateOnly
            ) -> ""
        config.timeMode == TimeMode.AllDay -> " (All day)"
        else -> " at ${config.hour.toString().padStart(2, '0')}:${config.minute.toString().padStart(2, '0')}"
    }

    return if (config.eventType == EventType.Single) {
        "$datePart$timePart"
    } else {
        val freqLabel = when (config.frequency) {
            FrequencyType.SpecificDay -> "Every ${config.dayOfWeek.label}"
            else -> "Every ${config.frequency.label}"
        }
        val endPart = config.endDate?.let { e ->
            " to ${e.dayOfMonth} ${e.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)} ${e.year}" +
                    " at ${config.endHour.toString().padStart(2, '0')}:${config.endMinute.toString().padStart(2, '0')}"
        } ?: ""
        "$freqLabel from $datePart$timePart$endPart"
    }
}

// =============================================================================
// DateSelect — main public composable
// =============================================================================

/**
 * Darwin UI Date Select component — a sophisticated date/time selector
 * with single/recurring event support, inline blue-pill dropdowns,
 * a custom calendar grid, and a conversational sentence-builder UI.
 *
 * @param label      Optional label text displayed above the trigger.
 * @param onChange   Callback invoked when the user confirms a configuration.
 * @param initialConfig Optional initial configuration.
 * @param modifier   Modifier applied to the root layout.
 */
@Composable
fun DateSelect(
    label: String? = null,
    onChange: ((DateConfig) -> Unit)? = null,
    initialConfig: DateConfig? = null,
    modifier: Modifier = Modifier,
) {
    val colors = DarwinTheme.colors
    val shapes = DarwinTheme.shapes

    var dialogOpen by remember { mutableStateOf(false) }
    var confirmedConfig by remember { mutableStateOf(initialConfig) }
    var draftConfig by remember { mutableStateOf(initialConfig ?: DateConfig()) }

    // Sync draft when dialog opens
    LaunchedEffect(dialogOpen) {
        if (dialogOpen) {
            draftConfig = confirmedConfig ?: DateConfig()
        }
    }

    Column(modifier = modifier) {
        // Label
        if (label != null) {
            Text(
                text = label,
                style = DarwinTheme.typography.bodySmall,
                color = colors.mutedForeground,
                modifier = Modifier.padding(bottom = 6.dp),
            )
        }

        // Trigger button
        val triggerInteraction = remember { MutableInteractionSource() }
        val triggerHovered by triggerInteraction.collectIsHoveredAsState()

        val triggerBg = when {
            triggerHovered -> if (colors.isDark) Color.White.copy(alpha = 0.10f) else Color.Black.copy(alpha = 0.04f)
            else -> colors.muted
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp)
                .clip(shapes.medium)
                .background(triggerBg, shapes.medium)
                .border(1.dp, colors.border, shapes.medium)
                .hoverable(triggerInteraction)
                .clickable(
                    indication = null,
                    interactionSource = triggerInteraction,
                ) { dialogOpen = true }
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Icon(
                imageVector = LucideCalendar,
                tint = colors.textTertiary,
                modifier = Modifier.size(16.dp),
            )
            Text(
                text = confirmedConfig?.let { formatSummary(it) } ?: "Select date & time...",
                style = DarwinTheme.typography.bodySmall,
                color = if (confirmedConfig != null) colors.textPrimary else colors.textTertiary,
            )
        }

        // Dialog
        ContentDialog(
            title = "Select Date & Time",
            visible = dialogOpen,
            content = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Event type toggle row
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        EventType.entries.forEach { type ->
                            val isSelected = draftConfig.eventType == type
                            val chipInteraction = remember { MutableInteractionSource() }
                            val chipHovered by chipInteraction.collectIsHoveredAsState()

                            val chipBg = when {
                                isSelected -> BluePillBg
                                chipHovered -> colors.muted
                                else -> Color.Transparent
                            }
                            val chipBorder = if (isSelected) BluePillBorder else colors.border
                            val chipColor = if (isSelected) Blue400 else colors.textSecondary

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(chipBg, RoundedCornerShape(6.dp))
                                    .border(1.dp, chipBorder, RoundedCornerShape(6.dp))
                                    .hoverable(chipInteraction)
                                    .clickable(
                                        indication = null,
                                        interactionSource = chipInteraction,
                                    ) {
                                        draftConfig = draftConfig.copy(eventType = type)
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp),
                            ) {
                                Text(
                                    text = type.label,
                                    style = DarwinTheme.typography.bodySmall,
                                    color = chipColor,
                                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                                )
                            }
                        }
                    }

                    // Questionnaire
                    QuestionnaireContent(
                        config = draftConfig,
                        onConfigChange = { draftConfig = it },
                    )

                    // Summary
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Summary",
                            style = DarwinTheme.typography.labelSmall,
                            color = colors.textTertiary,
                        )
                        Text(
                            text = formatSummary(draftConfig),
                            style = DarwinTheme.typography.bodySmall,
                            color = colors.textPrimary,
                        )
                    }
                }
            },
            primaryButtonText = "Confirm",
            closeButtonText = "Cancel",
            onButtonClick = { button ->
                when (button) {
                    ContentDialogButton.Primary -> {
                        confirmedConfig = draftConfig
                        onChange?.invoke(draftConfig)
                        dialogOpen = false
                    }
                    ContentDialogButton.Close -> {
                        dialogOpen = false
                    }
                    else -> {}
                }
            },
            size = DialogSize.ExtraLarge,
        )
    }
}

@Preview
@Composable
private fun DateSelectPreview() {
    DarwinTheme {
        DateSelect(label = "Event date")
    }
}
