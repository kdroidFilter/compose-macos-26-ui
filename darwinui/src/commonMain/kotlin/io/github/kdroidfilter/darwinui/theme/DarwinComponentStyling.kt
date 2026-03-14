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
    val stepper: StepperStyle,
    val progress: ProgressStyle = ProgressStyle(),
    val sidebar: SidebarStyle = SidebarStyle(),
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
        stepper = defaultStepperStyle(colorScheme),
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

private fun defaultTextFieldStyle(cs: ColorScheme): TextFieldStyle {
    val isDark = cs.isDark
    return TextFieldStyle(
        colors = TextFieldStyle.Colors(
            // Content Area — Sketch: "Content Area/Input Fields"
            background = if (isDark) Color(0xFF2C2C2E) else Color.White,
            backgroundDisabled = if (isDark) Color(0xFF2C2C2E).copy(alpha = 0.5f) else Color.White.copy(alpha = 0.5f),
            border = Color.Black.copy(alpha = if (isDark) 0.30f else 0.08f),
            borderDisabled = Color.Black.copy(alpha = if (isDark) 0.15f else 0.04f),
            // Over-glass — Sketch: "Over-Glass/Input Fields"
            overGlassBackground = Color.White.copy(alpha = if (isDark) 0.08f else 0.10f),
            overGlassFocusedBackground = if (isDark) Color(0xFF3A3A3C) else Color(0xFFD9D9D9),
            overGlassDisabledBackground = Color.White.copy(alpha = if (isDark) 0.04f else 0.05f),
            // Content
            text = cs.textPrimary,
            textDisabled = cs.textTertiary,
            placeholder = cs.textTertiary,
            cursor = cs.accent,
            // Label & error
            label = cs.textPrimary,
            labelDisabled = cs.textTertiary,
            errorBorder = cs.destructive,
            // Icon
            icon = cs.textTertiary,
            iconDisabled = cs.textTertiary.copy(alpha = 0.5f),
        ),
    )
}

private fun defaultCheckboxStyle(cs: ColorScheme): CheckboxStyle {
    val fillBase = if (cs.isDark) Color.White else Color.Black

    return CheckboxStyle(
        colors = CheckboxStyle.Colors(
            // Active window fills
            checkedFill = cs.accent,
            uncheckedFill = fillBase.copy(alpha = if (cs.isDark) 0.10f else 0.05f),
            pressedOverlay = fillBase.copy(alpha = 0.15f),
            disabledCheckedFill = cs.accent.copy(alpha = 0.5f),

            // Inactive window fills
            inactiveCheckedFill = fillBase.copy(alpha = 0.13f),
            inactiveCheckedPressedFill = fillBase.copy(alpha = 0.20f),
            inactiveDisabledFill = fillBase.copy(alpha = if (cs.isDark) 0.10f else 0.05f),

            // Indicators
            checkmark = Color.White,
            inactiveCheckmark = if (cs.isDark) Color(0xFFAEAEB2) else Color(0xFF4C4C4C),
            inactiveDisabledCheckmark = if (cs.isDark) Color(0xFF636366) else Color(0xFFC6C6C6),

            // Disabled
            disabledAlpha = 0.5f,
        ),
    )
}

private fun defaultRadioButtonStyle(cs: ColorScheme): RadioButtonStyle {
    // Light mode uses translucent black fills; dark mode uses translucent white
    val fillBase = if (cs.isDark) Color.White else Color.Black

    return RadioButtonStyle(
        colors = RadioButtonStyle.Colors(
            // Active window fills
            checkedFill = cs.accent,
            uncheckedFill = fillBase.copy(alpha = if (cs.isDark) 0.10f else 0.05f),
            pressedOverlay = fillBase.copy(alpha = 0.15f),
            disabledCheckedFill = cs.accent.copy(alpha = 0.5f),

            // Inactive window fills
            inactiveCheckedFill = fillBase.copy(alpha = 0.13f),
            inactiveCheckedPressedFill = fillBase.copy(alpha = 0.20f),
            inactiveDisabledFill = fillBase.copy(alpha = if (cs.isDark) 0.10f else 0.05f),

            // Indicators
            dot = Color.White,
            inactiveDot = if (cs.isDark) Color(0xFFAEAEB2) else Color(0xFF4C4C4C),
            inactiveDisabledDot = if (cs.isDark) Color(0xFF636366) else Color(0xFFC6C6C6),

            // Disabled
            disabledAlpha = 0.5f,
        ),
    )
}

private fun defaultSwitchStyle(cs: ColorScheme): SwitchStyle {
    val offTrack = if (cs.isDark) Color(0xFF3A3A3C) else Color(0xFFD1D1D6)
    // Inactive window: accent-colored tracks become neutral gray
    val inactiveOnTrack = if (cs.isDark) Color(0xFF636366) else Color(0xFFAEAEB2)
    val inactiveOffTrack = if (cs.isDark) Color(0xFF3A3A3C) else Color(0xFFD1D1D6)

    return SwitchStyle(
        colors = SwitchStyle.Colors(
            // Active window track colors
            onTrack = cs.accent,
            offTrack = offTrack,
            mixedTrack = cs.accent,

            // Inactive window track colors
            inactiveOnTrack = inactiveOnTrack,
            inactiveOffTrack = inactiveOffTrack,
            inactiveMixedTrack = inactiveOnTrack,

            // Thumb
            thumb = Color.White,
            thumbShadow = Color.Black.copy(alpha = 0.15f),

            // Track-level state indicators (macOS 26)
            onIndicator = Color.White,
            offIndicator = if (cs.isDark) Color(0xFF8E8E93) else Color(0xFFC6C6C6),
            mixedIndicator = Color.White,
        ),
    )
}

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

private fun defaultSegmentedControlStyle(cs: ColorScheme): SegmentedControlStyle {
    val fillBase = if (cs.isDark) Color.White else Color.Black
    val track = fillBase.copy(alpha = if (cs.isDark) 0.10f else 0.05f)
    // Inactive selected: light = black 13%, dark = white 20%
    val inactiveContentArea = fillBase.copy(alpha = if (cs.isDark) 0.20f else 0.13f)
    // Over-glass inactive: light = black 10%, dark = white 15%
    val inactiveOverGlass = fillBase.copy(alpha = if (cs.isDark) 0.15f else 0.10f)
    val shared = SegmentedControlStyle.Colors(
        track = track,
        selectedSegment = cs.accent,
        selectedContent = Color.White,
        unselectedContent = cs.textPrimary,
        pressedOverlay = fillBase.copy(alpha = 0.15f),
        disabledContent = cs.textTertiary,
        separatorColor = cs.textQuaternary,
        inactiveSelectedSegment = inactiveContentArea,
        inactiveSelectedContent = cs.textPrimary,
    )
    return SegmentedControlStyle(
        contentArea = shared,
        overGlass = shared.copy(
            inactiveSelectedSegment = inactiveOverGlass,
        ),
    )
}

private fun defaultStepperStyle(cs: ColorScheme): StepperStyle {
    val fillBase = if (cs.isDark) Color.White else Color.Black
    return StepperStyle(
        colors = StepperStyle.Colors(
            // Sketch: Content Area/01 - Bordered Neutral/Off, 01 - Idle = #0000000d
            background = fillBase.copy(alpha = if (cs.isDark) 0.08f else 0.05f),
            // Sketch: Content Area/01 - Bordered Neutral/Off, 03 - Clicked = #00000026
            backgroundPressed = fillBase.copy(alpha = 0.15f),
            // Sketch: Content Area/01 - Bordered Neutral/Off, 04 - Disabled = #0000000a
            backgroundDisabled = fillBase.copy(alpha = if (cs.isDark) 0.06f else 0.04f),
            // Sketch: Over-Glass/01 - Bordered Neutral/Off, 01 - Idle = #0000000d
            overGlassBackground = fillBase.copy(alpha = if (cs.isDark) 0.08f else 0.05f),
            // Sketch: Over-Glass/01 - Bordered Neutral/Off, 03 - Clicked = #00000026
            overGlassBackgroundPressed = fillBase.copy(alpha = 0.15f),
            // Sketch: Over-Glass/01 - Bordered Neutral/Off, 04 - Disabled = #00000008
            overGlassBackgroundDisabled = fillBase.copy(alpha = if (cs.isDark) 0.05f else 0.03f),
            // Sketch: Label Colors/Light/1 Primary = #000000d9
            arrow = cs.textPrimary,
            // Sketch: Label Colors/Light/3 Tertiary = #00000040
            arrowDisabled = cs.textTertiary,
            // Sketch: Label Colors/Light Vibrant/3 Tertiary = #bfbfbfff
            separator = if (cs.isDark) Color(0xFF636366) else Color(0xFFBFBFBF),
        ),
    )
}

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
