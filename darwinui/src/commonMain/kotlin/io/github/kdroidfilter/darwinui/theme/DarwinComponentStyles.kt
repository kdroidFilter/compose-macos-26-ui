package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ===========================================================================
// 1.1 — Component Style system
//
// Each major component exposes a *Style data class that encapsulates:
//   - Colors  — all color tokens for every interaction state
//   - Metrics — sizing / spacing constants
//
// Styles are provided at composition time via ComponentStyling (see
// DarwinComponentStyling.kt) and accessed through DarwinTheme.componentStyling.
//
// Pattern mirrors Jewel's per-component Style/Colors/Metrics split, with
// the difference that Colors and Metrics are nested inside the Style class
// to avoid top-level naming conflicts with the existing *Defaults API.
// ===========================================================================


// ---------------------------------------------------------------------------
// ButtonStyle
// ---------------------------------------------------------------------------

@Immutable
data class ButtonStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        val background: Color,
        val backgroundHovered: Color,
        val backgroundPressed: Color,
        val backgroundDisabled: Color,
        val content: Color,
        val contentHovered: Color,
        val contentDisabled: Color,
        val border: Color,
        val borderHovered: Color,
        val borderPressed: Color,
        val borderDisabled: Color,
    )

    @Immutable
    data class Metrics(
        /** Minimum height for the regular (default) size. */
        val minHeight: Dp = 22.dp,
        /** Minimum height for the small size variant. */
        val minHeightSmall: Dp = 18.dp,
        /** Minimum height for the large size variant. */
        val minHeightLarge: Dp = 28.dp,
        val horizontalPadding: Dp = 12.dp,
        val cornerSize: Dp = 5.dp,
        val borderWidth: Dp = 1.dp,
    )
}


// ---------------------------------------------------------------------------
// TextFieldStyle
// ---------------------------------------------------------------------------

@Immutable
data class TextFieldStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        val background: Color,
        val backgroundFocused: Color,
        val backgroundDisabled: Color,
        val backgroundError: Color,
        val content: Color,
        val contentDisabled: Color,
        val placeholder: Color,
        val placeholderDisabled: Color,
        val border: Color,
        val borderFocused: Color,
        val borderDisabled: Color,
        val borderError: Color,
        val cursor: Color,
        val cursorError: Color,
        val label: Color,
        val labelDisabled: Color,
        val labelError: Color,
        val icon: Color,
        val iconDisabled: Color,
        val iconError: Color,
    )

    @Immutable
    data class Metrics(
        val minHeightSm: Dp = 26.dp,
        val minHeightMd: Dp = 32.dp,
        val minHeightLg: Dp = 40.dp,
        val horizontalPadding: Dp = 12.dp,
        val cornerSize: Dp = 6.dp,
        val borderWidth: Dp = 1.dp,
        val focusedBorderWidth: Dp = 2.dp,
        val labelBottomPadding: Dp = 6.dp,
        val supportingTextTopPadding: Dp = 4.dp,
    )
}


// ---------------------------------------------------------------------------
// CheckboxStyle
// ---------------------------------------------------------------------------

@Immutable
data class CheckboxStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        val checkedBox: Color,
        val uncheckedBox: Color,
        val disabledCheckedBox: Color,
        val disabledUncheckedBox: Color,
        val checkedBorder: Color,
        val uncheckedBorder: Color,
        val disabledBorder: Color,
        val checkmark: Color,
        val checkmarkDisabled: Color,
    )

    @Immutable
    data class Metrics(
        val size: Dp = 16.dp,
        val cornerSize: Dp = 4.dp,
        val borderWidth: Dp = 1.dp,
        val labelSpacing: Dp = 8.dp,
    )
}


// ---------------------------------------------------------------------------
// RadioButtonStyle
// ---------------------------------------------------------------------------

@Immutable
data class RadioButtonStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        val selectedBackground: Color,
        val unselectedBackground: Color,
        val disabledSelectedBackground: Color,
        val disabledUnselectedBackground: Color,
        val selectedBorder: Color,
        val unselectedBorder: Color,
        val disabledBorder: Color,
        val dot: Color,
        val dotDisabled: Color,
    )

    @Immutable
    data class Metrics(
        val size: Dp = 16.dp,
        val dotSize: Dp = 6.dp,
        val borderWidth: Dp = 1.dp,
        val labelSpacing: Dp = 8.dp,
    )
}


// ---------------------------------------------------------------------------
// SwitchStyle
// ---------------------------------------------------------------------------

@Immutable
data class SwitchStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        val checkedTrack: Color,
        val uncheckedTrack: Color,
        val disabledCheckedTrack: Color,
        val disabledUncheckedTrack: Color,
        val thumb: Color,
        val thumbDisabled: Color,
        val thumbShadow: Color,
    )

    @Immutable
    data class Metrics(
        val trackWidth: Dp = 36.dp,
        val trackHeight: Dp = 22.dp,
        val thumbSize: Dp = 18.dp,
        val thumbPadding: Dp = 2.dp,
    )
}


// ---------------------------------------------------------------------------
// SliderStyle
// ---------------------------------------------------------------------------

@Immutable
data class SliderStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        val activeTrack: Color,
        val inactiveTrack: Color,
        val disabledActiveTrack: Color,
        val disabledInactiveTrack: Color,
        val thumb: Color,
        val thumbBorder: Color,
        val thumbDisabled: Color,
        val tickMark: Color,
        val tickMarkActive: Color,
    )

    @Immutable
    data class Metrics(
        /** Track height for the small size. */
        val trackHeightSm: Dp = 2.dp,
        /** Track height for the default size. */
        val trackHeightMd: Dp = 4.dp,
        /** Track height for the large size. */
        val trackHeightLg: Dp = 6.dp,
        /** Thumb diameter for the small size. */
        val thumbSizeSm: Dp = 14.dp,
        /** Thumb diameter for the default size. */
        val thumbSizeMd: Dp = 18.dp,
        /** Thumb diameter for the large size. */
        val thumbSizeLg: Dp = 22.dp,
        val thumbBorderWidth: Dp = 1.dp,
    )
}


// ---------------------------------------------------------------------------
// TabStyle
// ---------------------------------------------------------------------------

@Immutable
data class TabStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        val selectedBackground: Color,
        val unselectedBackground: Color,
        val hoveredBackground: Color,
        val disabledBackground: Color,
        val selectedContent: Color,
        val unselectedContent: Color,
        val disabledContent: Color,
        val indicator: Color,
        val divider: Color,
    )

    @Immutable
    data class Metrics(
        val containerHeightSm: Dp = 28.dp,
        val containerHeightMd: Dp = 34.dp,
        val containerHeightLg: Dp = 40.dp,
        val indicatorHeight: Dp = 2.dp,
        val horizontalPadding: Dp = 12.dp,
        val tabSpacing: Dp = 2.dp,
        val cornerSize: Dp = 6.dp,
    )
}


// ---------------------------------------------------------------------------
// ComboBoxStyle
// ---------------------------------------------------------------------------

@Immutable
data class ComboBoxStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        val background: Color,
        val backgroundHovered: Color,
        val backgroundDisabled: Color,
        val content: Color,
        val contentDisabled: Color,
        val placeholder: Color,
        val border: Color,
        val borderFocused: Color,
        val borderDisabled: Color,
        val chevron: Color,
        val chevronDisabled: Color,
        val dropdownBackground: Color,
        val dropdownItemSelected: Color,
        val dropdownItemContent: Color,
        val dropdownItemSelectedContent: Color,
    )

    @Immutable
    data class Metrics(
        val minHeight: Dp = 22.dp,
        val horizontalPadding: Dp = 8.dp,
        val cornerSize: Dp = 5.dp,
        val borderWidth: Dp = 1.dp,
        val chevronSize: Dp = 16.dp,
        val dropdownMaxHeight: Dp = 300.dp,
    )
}


// ---------------------------------------------------------------------------
// SegmentedControlStyle
// ---------------------------------------------------------------------------

@Immutable
data class SegmentedControlStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        val track: Color,
        val trackBorder: Color,
        val selectedSegment: Color,
        val selectedSegmentBorder: Color,
        val selectedContent: Color,
        val unselectedContent: Color,
        val disabledSelectedSegment: Color,
        val disabledContent: Color,
    )

    @Immutable
    data class Metrics(
        val containerHeightSm: Dp = 22.dp,
        val containerHeightMd: Dp = 28.dp,
        val containerHeightLg: Dp = 34.dp,
        val horizontalPadding: Dp = 10.dp,
        val cornerSize: Dp = 6.dp,
        val trackPadding: Dp = 2.dp,
        val segmentSpacing: Dp = 2.dp,
    )
}


// ---------------------------------------------------------------------------
// TooltipStyle
// ---------------------------------------------------------------------------

@Immutable
data class TooltipStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        val background: Color,
        val content: Color,
        val border: Color,
    )

    @Immutable
    data class Metrics(
        val cornerSize: Dp = 6.dp,
        val horizontalPadding: Dp = 8.dp,
        val verticalPadding: Dp = 4.dp,
        val borderWidth: Dp = 1.dp,
        val showDelayMs: Int = 500,
        val dismissDelayMs: Int = 0,
    )
}


// ---------------------------------------------------------------------------
// CardStyle
// ---------------------------------------------------------------------------

@Immutable
data class CardStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        val background: Color,
        val backgroundElevated: Color,
        val content: Color,
        val border: Color,
        val shadow: Color,
    )

    @Immutable
    data class Metrics(
        val cornerSize: Dp = 10.dp,
        val borderWidth: Dp = 1.dp,
        val contentPadding: Dp = 16.dp,
        val headerPadding: Dp = 16.dp,
        val footerPadding: Dp = 12.dp,
        val elevation: Dp = 0.dp,
    )
}
