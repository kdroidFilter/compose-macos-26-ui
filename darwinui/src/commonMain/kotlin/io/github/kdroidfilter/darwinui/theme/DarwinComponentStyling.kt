package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ===========================================================================
// 1.1 — ComponentStyling registry
//
// ComponentStyling is a single holder for all component default styles.
// It is constructed from a ColorScheme + GlobalMetrics at composition time
// inside DarwinTheme, and provided via LocalDarwinComponentStyling.
//
// Access through DarwinTheme.componentStyling.
// ===========================================================================

/**
 * Holds the default [*Style] instance for every Darwin UI component.
 *
 * Downstream components read their style from here when no explicit style
 * is provided by the caller, enabling single-point theme customisation:
 *
 * ```kotlin
 * DarwinTheme(
 *     componentStyling = DefaultComponentStyling(colorScheme).copy(
 *         button = myCustomButtonStyle,
 *     )
 * ) { … }
 * ```
 */
@Immutable
data class ComponentStyling(
    val defaultButton: ButtonStyle,
    val outlinedButton: ButtonStyle,
    val textButton: ButtonStyle,
    val textField: TextFieldStyle,
    val checkbox: CheckboxStyle,
    val radioButton: RadioButtonStyle,
    val switch: SwitchStyle,
    val slider: SliderStyle,
    val tab: TabStyle,
    val comboBox: ComboBoxStyle,
    val segmentedControl: SegmentedControlStyle,
    val tooltip: TooltipStyle,
    val card: CardStyle,
)

// ---------------------------------------------------------------------------
// Factory
// ---------------------------------------------------------------------------

/**
 * Creates the default [ComponentStyling] for the given [colorScheme].
 *
 * Called inside [DarwinTheme] so that component styles always reflect the
 * active colour scheme and accent colour.
 */
fun defaultComponentStyling(colorScheme: ColorScheme): ComponentStyling {
    val isDark = colorScheme.isDark
    val accent = colorScheme.accent

    return ComponentStyling(
        defaultButton = defaultButtonStyle(colorScheme),
        outlinedButton = outlinedButtonStyle(colorScheme),
        textButton = textButtonStyle(colorScheme),
        textField = defaultTextFieldStyle(colorScheme),
        checkbox = defaultCheckboxStyle(colorScheme),
        radioButton = defaultRadioButtonStyle(colorScheme),
        switch = defaultSwitchStyle(colorScheme),
        slider = defaultSliderStyle(colorScheme),
        tab = defaultTabStyle(colorScheme),
        comboBox = defaultComboBoxStyle(colorScheme),
        segmentedControl = defaultSegmentedControlStyle(colorScheme),
        tooltip = defaultTooltipStyle(colorScheme),
        card = defaultCardStyle(colorScheme),
    )
}

// ---------------------------------------------------------------------------
// Per-component default factories
// ---------------------------------------------------------------------------

private fun defaultButtonStyle(cs: ColorScheme) = ButtonStyle(
    colors = ButtonStyle.Colors(
        background = cs.primary,
        backgroundHovered = if (cs.isDark) Color(0xFFE8E8E8) else Color(0xFF101010),
        backgroundPressed = cs.primary.copy(alpha = 0.85f),
        backgroundDisabled = cs.primary.copy(alpha = 0.5f),
        content = cs.onPrimary,
        contentHovered = cs.onPrimary,
        contentDisabled = cs.onPrimary.copy(alpha = 0.5f),
        border = cs.border.copy(alpha = 0.5f),
        borderHovered = cs.border,
        borderPressed = cs.border.copy(alpha = 0.3f),
        borderDisabled = cs.border.copy(alpha = 0.3f),
    ),
)

private fun outlinedButtonStyle(cs: ColorScheme) = ButtonStyle(
    colors = ButtonStyle.Colors(
        background = Color.Transparent,
        backgroundHovered = cs.muted,
        backgroundPressed = cs.muted.copy(alpha = 0.7f),
        backgroundDisabled = Color.Transparent,
        content = cs.textPrimary,
        contentHovered = cs.textPrimary,
        contentDisabled = cs.textTertiary,
        border = cs.border,
        borderHovered = cs.borderStrong,
        borderPressed = cs.border,
        borderDisabled = cs.border.copy(alpha = 0.5f),
    ),
)

private fun textButtonStyle(cs: ColorScheme) = ButtonStyle(
    colors = ButtonStyle.Colors(
        background = Color.Transparent,
        backgroundHovered = cs.muted,
        backgroundPressed = cs.muted,
        backgroundDisabled = Color.Transparent,
        content = cs.accent,
        contentHovered = cs.accent,
        contentDisabled = cs.textTertiary,
        border = Color.Transparent,
        borderHovered = Color.Transparent,
        borderPressed = Color.Transparent,
        borderDisabled = Color.Transparent,
    ),
)

private fun defaultTextFieldStyle(cs: ColorScheme) = TextFieldStyle(
    colors = TextFieldStyle.Colors(
        background = cs.inputBackground,
        backgroundFocused = cs.inputFocusBackground,
        backgroundDisabled = cs.inputBackground.copy(alpha = 0.5f),
        backgroundError = cs.inputBackground,
        content = cs.textPrimary,
        contentDisabled = cs.textTertiary,
        placeholder = cs.textTertiary,
        placeholderDisabled = cs.textTertiary.copy(alpha = 0.5f),
        border = cs.inputBorder,
        borderFocused = cs.inputFocusBorder,
        borderDisabled = cs.inputBorder.copy(alpha = 0.5f),
        borderError = cs.destructive,
        cursor = cs.inputFocusBorder,
        cursorError = cs.destructive,
        label = cs.textPrimary,
        labelDisabled = cs.textTertiary,
        labelError = cs.destructive,
        icon = cs.textTertiary,
        iconDisabled = cs.textTertiary.copy(alpha = 0.5f),
        iconError = cs.destructive,
    ),
)

private fun defaultCheckboxStyle(cs: ColorScheme) = CheckboxStyle(
    colors = CheckboxStyle.Colors(
        checkedBox = cs.accent,
        uncheckedBox = if (cs.isDark) Color(0xFF27272A) else Color.White,
        disabledCheckedBox = cs.accent.copy(alpha = 0.5f),
        disabledUncheckedBox = (if (cs.isDark) Color(0xFF27272A) else Color.White).copy(alpha = 0.5f),
        checkedBorder = cs.accent,
        uncheckedBorder = if (cs.isDark) Color(0xFF52525B) else Color.Black.copy(alpha = 0.20f),
        disabledBorder = (if (cs.isDark) Color(0xFF52525B) else Color.Black.copy(alpha = 0.20f)).copy(alpha = 0.5f),
        checkmark = Color.White,
        checkmarkDisabled = Color.White.copy(alpha = 0.7f),
    ),
)

private fun defaultRadioButtonStyle(cs: ColorScheme) = RadioButtonStyle(
    colors = RadioButtonStyle.Colors(
        selectedBackground = cs.accent,
        unselectedBackground = if (cs.isDark) Color(0xFF27272A) else Color.White,
        disabledSelectedBackground = cs.accent.copy(alpha = 0.5f),
        disabledUnselectedBackground = (if (cs.isDark) Color(0xFF27272A) else Color.White).copy(alpha = 0.5f),
        selectedBorder = cs.accent,
        unselectedBorder = if (cs.isDark) Color(0xFF52525B) else Color.Black.copy(alpha = 0.20f),
        disabledBorder = Color.Black.copy(alpha = 0.10f),
        dot = Color.White,
        dotDisabled = Color.White.copy(alpha = 0.7f),
    ),
)

private fun defaultSwitchStyle(cs: ColorScheme) = SwitchStyle(
    colors = SwitchStyle.Colors(
        checkedTrack = cs.accent,
        uncheckedTrack = if (cs.isDark) Color(0xFF3A3A3C) else Color(0xFFD1D1D6),
        disabledCheckedTrack = cs.accent.copy(alpha = 0.5f),
        disabledUncheckedTrack = (if (cs.isDark) Color(0xFF3A3A3C) else Color(0xFFD1D1D6)).copy(alpha = 0.5f),
        thumb = Color.White,
        thumbDisabled = Color.White.copy(alpha = 0.7f),
        thumbShadow = Color.Black.copy(alpha = 0.15f),
    ),
)

private fun defaultSliderStyle(cs: ColorScheme) = SliderStyle(
    colors = SliderStyle.Colors(
        activeTrack = cs.accent,
        inactiveTrack = if (cs.isDark) Color(0xFF3A3A3C) else Color(0xFFD1D1D6),
        disabledActiveTrack = cs.accent.copy(alpha = 0.5f),
        disabledInactiveTrack = (if (cs.isDark) Color(0xFF3A3A3C) else Color(0xFFD1D1D6)).copy(alpha = 0.5f),
        thumb = Color.White,
        thumbBorder = Color.Black.copy(alpha = 0.15f),
        thumbDisabled = Color.White.copy(alpha = 0.7f),
        tickMark = if (cs.isDark) Color(0xFF636366) else Color(0xFFAEAEB2),
        tickMarkActive = cs.onAccent,
    ),
)

private fun defaultTabStyle(cs: ColorScheme) = TabStyle(
    colors = TabStyle.Colors(
        selectedBackground = if (cs.isDark) Color(0xFF3A3A3C) else Color.White,
        unselectedBackground = Color.Transparent,
        hoveredBackground = if (cs.isDark) Color(0xFF2C2C2E) else Color.Black.copy(alpha = 0.05f),
        disabledBackground = Color.Transparent,
        selectedContent = cs.textPrimary,
        unselectedContent = cs.textSecondary,
        disabledContent = cs.textTertiary,
        indicator = cs.accent,
        divider = cs.border,
    ),
)

private fun defaultComboBoxStyle(cs: ColorScheme) = ComboBoxStyle(
    colors = ComboBoxStyle.Colors(
        background = cs.inputBackground,
        backgroundHovered = if (cs.isDark) Color(0xFF3A3A3C) else Color(0xFFF4F4F5),
        backgroundDisabled = cs.inputBackground.copy(alpha = 0.5f),
        content = cs.textPrimary,
        contentDisabled = cs.textTertiary,
        placeholder = cs.textTertiary,
        border = cs.inputBorder,
        borderFocused = cs.inputFocusBorder,
        borderDisabled = cs.inputBorder.copy(alpha = 0.5f),
        chevron = cs.textTertiary,
        chevronDisabled = cs.textTertiary.copy(alpha = 0.5f),
        dropdownBackground = cs.popover,
        dropdownItemSelected = cs.accent.copy(alpha = 0.15f),
        dropdownItemContent = cs.textPrimary,
        dropdownItemSelectedContent = cs.accent,
    ),
)

private fun defaultSegmentedControlStyle(cs: ColorScheme) = SegmentedControlStyle(
    colors = SegmentedControlStyle.Colors(
        track = if (cs.isDark) Color(0xFF2C2C2E) else Color.Black.copy(alpha = 0.06f),
        trackBorder = cs.border,
        selectedSegment = if (cs.isDark) Color(0xFF636366) else Color.White,
        selectedSegmentBorder = cs.border,
        selectedContent = cs.textPrimary,
        unselectedContent = cs.textSecondary,
        disabledSelectedSegment = (if (cs.isDark) Color(0xFF636366) else Color.White).copy(alpha = 0.5f),
        disabledContent = cs.textTertiary,
    ),
)

private fun defaultTooltipStyle(cs: ColorScheme) = TooltipStyle(
    colors = TooltipStyle.Colors(
        background = if (cs.isDark) Color(0xFF3A3A3C) else Color(0xFFF2F2F7),
        content = cs.textPrimary,
        border = cs.border,
    ),
)

private fun defaultCardStyle(cs: ColorScheme) = CardStyle(
    colors = CardStyle.Colors(
        background = cs.card,
        backgroundElevated = cs.backgroundElevated,
        content = cs.cardForeground,
        border = cs.border,
        shadow = Color.Black.copy(alpha = if (cs.isDark) 0.3f else 0.08f),
    ),
)

// ---------------------------------------------------------------------------
// CompositionLocal
// ---------------------------------------------------------------------------

val LocalDarwinComponentStyling = staticCompositionLocalOf {
    defaultComponentStyling(lightColorScheme())
}
