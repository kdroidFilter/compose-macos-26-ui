package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ===========================================================================
// 1.1 — Component Style system
//
// Each major component exposes a *Style data class that encapsulates:
//   - Colors  — all color tokens for every interaction state
//   - Metrics — sizing / spacing constants
//
// Styles are provided at composition time via ComponentStyling (see
// ComponentStyling.kt) and accessed through MacosTheme.componentStyling.
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
        val minHeight: Dp = 24.dp,
        /** Minimum height for the small size variant. */
        val minHeightSmall: Dp = 20.dp,
        /** Minimum height for the large size variant. */
        val minHeightLarge: Dp = 28.dp,
        val horizontalPadding: Dp = 12.dp,
        val cornerSize: Dp = 6.dp,
        val borderWidth: Dp = 1.dp,
    ) {
        fun minHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 16.dp
            ControlSize.Small -> minHeightSmall
            ControlSize.Regular -> minHeight
            ControlSize.Large -> minHeightLarge
            ControlSize.ExtraLarge -> 36.dp
        }

        fun horizontalPaddingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 6.dp
            ControlSize.Small -> 8.dp
            ControlSize.Regular -> horizontalPadding
            ControlSize.Large -> 14.dp
            ControlSize.ExtraLarge -> 16.dp
        }

        fun cornerSizeFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 4.dp
            ControlSize.Small -> 5.dp
            ControlSize.Regular -> cornerSize
            ControlSize.Large -> 14.dp
            ControlSize.ExtraLarge -> 18.dp
        }
    }
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
        // Content Area
        val background: Color,
        val backgroundDisabled: Color,
        val border: Color,
        val borderDisabled: Color,
        // Over-glass
        val overGlassBackground: Color,
        val overGlassFocusedBackground: Color,
        val overGlassDisabledBackground: Color,
        // Content
        val text: Color,
        val textDisabled: Color,
        val placeholder: Color,
        val cursor: Color,
        // Label & error
        val label: Color,
        val labelDisabled: Color,
        val errorBorder: Color,
        // Icon
        val icon: Color,
        val iconDisabled: Color,
    )

    /** Sketch: Text Field / Content Area & Over-glass — all 5 sizes */
    @Immutable
    data class Metrics(
        val labelBottomPadding: Dp = 6.dp,
        val supportingTextTopPadding: Dp = 4.dp,
    ) {
        /** Sketch heights: Mn=16, Sm=20, Md=24, Lg=28, XL=36 */
        fun heightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 16.dp
            ControlSize.Small -> 20.dp
            ControlSize.Regular -> 24.dp
            ControlSize.Large -> 28.dp
            ControlSize.ExtraLarge -> 36.dp
        }

        /** Sketch corner radii: Mn=4, Sm=5, Md=6, Lg=7, XL=9 */
        fun cornerRadiusFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 4.dp
            ControlSize.Small -> 5.dp
            ControlSize.Regular -> 6.dp
            ControlSize.Large -> 7.dp
            ControlSize.ExtraLarge -> 9.dp
        }

        /** Sketch left padding: Mn=6, Sm=6, Md=8, Lg=8, XL=10 */
        fun startPaddingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 6.dp
            ControlSize.Small -> 6.dp
            ControlSize.Regular -> 8.dp
            ControlSize.Large -> 8.dp
            ControlSize.ExtraLarge -> 10.dp
        }

        /** Sketch right padding: Mn=2, Sm=2.5, Md=4, Lg=6, XL=8 */
        fun endPaddingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 2.dp
            ControlSize.Small -> 2.5.dp
            ControlSize.Regular -> 4.dp
            ControlSize.Large -> 6.dp
            ControlSize.ExtraLarge -> 8.dp
        }
    }
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
        // Active window fills
        val checkedFill: Color,
        val uncheckedFill: Color,
        val pressedOverlay: Color,
        val disabledCheckedFill: Color,

        // Inactive window fills
        val inactiveCheckedFill: Color,
        val inactiveCheckedPressedFill: Color,
        val inactiveDisabledFill: Color,

        // Indicators
        val checkmark: Color,
        val inactiveCheckmark: Color,
        val inactiveDisabledCheckmark: Color,

        // Disabled
        val disabledAlpha: Float,
    )

    @Immutable
    data class Metrics(
        val size: Dp = 16.dp,
        val cornerSize: Dp = 5.5.dp,
        val labelSpacing: Dp = 5.dp,
    ) {
        fun sizeFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 12.dp
            ControlSize.Small -> 14.dp
            ControlSize.Regular -> size
            ControlSize.Large -> 18.dp
            ControlSize.ExtraLarge -> 18.dp
        }

        fun cornerSizeFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 3.5.dp
            ControlSize.Small -> 4.5.dp
            ControlSize.Regular -> cornerSize
            ControlSize.Large -> 6.5.dp
            ControlSize.ExtraLarge -> 6.5.dp
        }

        fun labelSpacingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 3.dp
            ControlSize.Small -> 4.dp
            ControlSize.Regular -> labelSpacing
            ControlSize.Large -> 5.dp
            ControlSize.ExtraLarge -> 7.dp
        }
    }
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
        // Active window fills
        val checkedFill: Color,
        val uncheckedFill: Color,
        val pressedOverlay: Color,
        val disabledCheckedFill: Color,

        // Inactive window fills
        val inactiveCheckedFill: Color,
        val inactiveCheckedPressedFill: Color,
        val inactiveDisabledFill: Color,

        // Indicators
        val dot: Color,
        val inactiveDot: Color,
        val inactiveDisabledDot: Color,

        // Disabled
        val disabledAlpha: Float,
    )

    @Immutable
    data class Metrics(
        val size: Dp = 16.dp,
        val dotSize: Dp = 5.dp,
        val borderWidth: Dp = 1.dp,
        val labelSpacing: Dp = 3.dp,
    ) {
        fun sizeFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 12.dp
            ControlSize.Small -> 14.dp
            ControlSize.Regular -> size
            ControlSize.Large -> 18.dp
            ControlSize.ExtraLarge -> 18.dp
        }

        fun dotSizeFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 4.dp
            ControlSize.Small -> 5.dp
            ControlSize.Regular -> dotSize
            ControlSize.Large -> 5.dp
            ControlSize.ExtraLarge -> 5.dp
        }

        fun labelSpacingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 3.dp
            ControlSize.Small -> 3.dp
            ControlSize.Regular -> labelSpacing
            ControlSize.Large -> 5.dp
            ControlSize.ExtraLarge -> 7.dp
        }
    }
}


// ---------------------------------------------------------------------------
// SwitchStyle
// ---------------------------------------------------------------------------

@Immutable
data class SwitchStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    /**
     * Color tokens for the Switch component covering all Apple states:
     * - Toggle state: On / Off / Mixed (indeterminate)
     * - Window state: Active (foreground) / Inactive (background)
     * - Interaction state: Idle / Pressed / Disabled
     *
     * Disabled state is handled via alpha in the component rather than
     * separate color tokens, matching Apple's approach.
     */
    @Immutable
    data class Colors(
        // Active window — track colors by toggle state
        val onTrack: Color,
        val offTrack: Color,
        val mixedTrack: Color,

        // Inactive window — track colors by toggle state
        val inactiveOnTrack: Color,
        val inactiveOffTrack: Color,
        val inactiveMixedTrack: Color,

        // Thumb
        val thumb: Color,
        val thumbShadow: Color,

        // Track-level state indicators (macOS 26 style)
        val onIndicator: Color,
        val offIndicator: Color,
        val mixedIndicator: Color,
    )

    @Immutable
    data class Metrics(
        val trackWidth: Dp = 54.dp,
        val trackHeight: Dp = 24.dp,
        val thumbWidth: Dp = 32.dp,
        val thumbHeight: Dp = 20.dp,
        val thumbPadding: Dp = 2.dp,
        val disabledAlpha: Float = 0.5f,
    ) {
        fun trackWidthFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 36.dp
            ControlSize.Small -> 44.dp
            ControlSize.Regular -> trackWidth
            ControlSize.Large -> 64.dp
            ControlSize.ExtraLarge -> 80.dp
        }

        fun trackHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 16.dp
            ControlSize.Small -> 20.dp
            ControlSize.Regular -> trackHeight
            ControlSize.Large -> 28.dp
            ControlSize.ExtraLarge -> 36.dp
        }

        fun thumbWidthFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 21.dp
            ControlSize.Small -> 26.dp
            ControlSize.Regular -> thumbWidth
            ControlSize.Large -> 37.dp
            ControlSize.ExtraLarge -> 47.dp
        }

        fun thumbHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 13.dp
            ControlSize.Small -> 16.dp
            ControlSize.Regular -> thumbHeight
            ControlSize.Large -> 23.dp
            ControlSize.ExtraLarge -> 29.dp
        }

        fun thumbPaddingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 1.5.dp
            ControlSize.Small -> 2.dp
            ControlSize.Regular -> thumbPadding
            ControlSize.Large -> 2.5.dp
            ControlSize.ExtraLarge -> 3.5.dp
        }
    }
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
        val thumbBorderWidth: Dp = 1.dp,
    ) {
        fun trackHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 4.dp
            ControlSize.Small -> 4.dp
            ControlSize.Regular -> 6.dp
            ControlSize.Large -> 6.dp
            ControlSize.ExtraLarge -> 6.dp
        }

        fun thumbWidthFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 16.dp
            ControlSize.Small -> 18.dp
            ControlSize.Regular -> 20.dp
            ControlSize.Large -> 24.dp
            ControlSize.ExtraLarge -> 24.dp
        }

        fun thumbHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 12.dp
            ControlSize.Small -> 14.dp
            ControlSize.Regular -> 16.dp
            ControlSize.Large -> 20.dp
            ControlSize.ExtraLarge -> 20.dp
        }

        fun thumbCornerRadiusFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 7.dp
            ControlSize.Small -> 7.dp
            ControlSize.Regular -> 8.dp
            ControlSize.Large -> 10.dp
            ControlSize.ExtraLarge -> 10.dp
        }

        fun totalHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 16.dp
            ControlSize.Small -> 20.dp
            ControlSize.Regular -> 24.dp
            ControlSize.Large -> 28.dp
            ControlSize.ExtraLarge -> 36.dp
        }
    }
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
    ) {
        fun containerHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 24.dp
            ControlSize.Small -> containerHeightSm
            ControlSize.Regular -> containerHeightMd
            ControlSize.Large -> containerHeightLg
            ControlSize.ExtraLarge -> 48.dp
        }

        fun triggerHPaddingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 6.dp
            ControlSize.Small -> 10.dp
            ControlSize.Regular -> 12.dp
            ControlSize.Large -> 16.dp
            ControlSize.ExtraLarge -> 20.dp
        }

        fun triggerVPaddingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 2.dp
            ControlSize.Small -> 4.dp
            ControlSize.Regular -> 6.dp
            ControlSize.Large -> 8.dp
            ControlSize.ExtraLarge -> 10.dp
        }

        fun iconSizeFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 12.dp
            ControlSize.Small -> 14.dp
            ControlSize.Regular -> 16.dp
            ControlSize.Large -> 18.dp
            ControlSize.ExtraLarge -> 20.dp
        }
    }
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
        val minWidth: Dp = 120.dp,
        val minHeight: Dp = 24.dp,
        val horizontalPadding: Dp = 8.dp,
        val cornerSize: Dp = 5.dp,
        val borderWidth: Dp = 1.dp,
        val chevronSize: Dp = 16.dp,
        val dropdownMaxHeight: Dp = 300.dp,
    ) {
        fun minHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 16.dp
            ControlSize.Small -> 20.dp
            ControlSize.Regular -> minHeight
            ControlSize.Large -> 28.dp
            ControlSize.ExtraLarge -> 36.dp
        }
    }
}


// ---------------------------------------------------------------------------
// SegmentedControlStyle
// ---------------------------------------------------------------------------

enum class SegmentedControlVariant {
    ContentArea,
    OverGlass,
}

@Immutable
data class SegmentedControlStyle(
    val contentArea: Colors,
    val overGlass: Colors,
    val metrics: Metrics = Metrics(),
) {
    fun colorsFor(variant: SegmentedControlVariant): Colors = when (variant) {
        SegmentedControlVariant.ContentArea -> contentArea
        SegmentedControlVariant.OverGlass -> overGlass
    }

    @Immutable
    data class Colors(
        val track: Color,
        val selectedSegment: Color,
        val selectedContent: Color,
        val unselectedContent: Color,
        val pressedOverlay: Color,
        val disabledContent: Color,
        val separatorColor: Color,
        val inactiveSelectedSegment: Color,
        val inactiveSelectedContent: Color,
    )

    @Immutable
    data class Metrics(
        val separatorWidth: Dp = 1.dp,
    ) {
        fun containerHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 16.dp
            ControlSize.Small -> 20.dp
            ControlSize.Regular -> 24.dp
            ControlSize.Large -> 28.dp
            ControlSize.ExtraLarge -> 36.dp
        }

        fun cornerRadiusFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 4.dp
            ControlSize.Small -> 5.dp
            ControlSize.Regular -> 6.dp
            ControlSize.Large -> 14.dp
            ControlSize.ExtraLarge -> 18.dp
        }

        fun separatorVerticalPaddingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 3.dp
            ControlSize.Small -> 4.dp
            ControlSize.Regular -> 5.dp
            ControlSize.Large -> 5.dp
            ControlSize.ExtraLarge -> 8.dp
        }
    }
}


// ---------------------------------------------------------------------------
// ProgressStyle
// ---------------------------------------------------------------------------

@Immutable
data class ProgressStyle(
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Metrics(
        val heightSm: Dp = 6.dp,
        val heightMd: Dp = 10.dp,
        val heightLg: Dp = 14.dp,
        val spinnerSizeSm: Dp = 22.dp,
        val spinnerSizeLg: Dp = 30.dp,
    ) {
        fun heightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 4.dp
            ControlSize.Small -> heightSm
            ControlSize.Regular -> heightMd
            ControlSize.Large -> heightLg
            ControlSize.ExtraLarge -> 18.dp
        }

        fun spinnerSizeFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 16.dp
            ControlSize.Small -> spinnerSizeSm
            ControlSize.Regular -> spinnerSizeSm
            ControlSize.Large -> spinnerSizeLg
            ControlSize.ExtraLarge -> 38.dp
        }
    }
}


// ---------------------------------------------------------------------------
// SidebarStyle
// ---------------------------------------------------------------------------

@Immutable
data class SidebarStyle(
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Metrics(
        /** Sketch: Small=18dp, Medium=22dp, Large=24dp */
        val iconDpSm: Dp = 18.dp,
        val iconDpMd: Dp = 22.dp,
        val iconDpLg: Dp = 24.dp,
        /** Sketch: Small=24dp, Medium=32dp, Large=40dp */
        val itemHeightSm: Dp = 24.dp,
        val itemHeightMd: Dp = 32.dp,
        val itemHeightLg: Dp = 40.dp,
    ) {
        fun iconDpFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 14.dp
            ControlSize.Small -> iconDpSm
            ControlSize.Regular -> iconDpMd
            ControlSize.Large -> iconDpLg
            ControlSize.ExtraLarge -> 26.dp
        }

        fun itemHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 20.dp
            ControlSize.Small -> itemHeightSm
            ControlSize.Regular -> itemHeightMd
            ControlSize.Large -> itemHeightLg
            ControlSize.ExtraLarge -> 44.dp
        }

        /** Sketch: content starts at 16dp from sidebar edge for all sizes */
        fun hPaddingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 8.dp
            ControlSize.Small -> 10.dp
            ControlSize.Regular -> 10.dp
            ControlSize.Large -> 10.dp
            ControlSize.ExtraLarge -> 12.dp
        }

        /** Sketch: 4dp gap between icon and label for all sizes */
        fun iconGapFor(controlSize: ControlSize): Dp = 4.dp

        fun itemSpacingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 0.dp
            ControlSize.Small -> 0.dp
            ControlSize.Regular -> 0.dp
            ControlSize.Large -> 0.dp
            ControlSize.ExtraLarge -> 0.dp
        }

        /** Sketch: Large=43dp, Medium=39dp, Small=34dp */
        fun groupHeaderHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 28.dp
            ControlSize.Small -> 34.dp
            ControlSize.Regular -> 39.dp
            ControlSize.Large -> 43.dp
            ControlSize.ExtraLarge -> 48.dp
        }

        /** Sketch: Large=13sp, Medium=11sp, Small=11sp */
        fun headerFontSizeFor(controlSize: ControlSize): Int = when (controlSize) {
            ControlSize.Mini -> 10
            ControlSize.Small -> 11
            ControlSize.Regular -> 11
            ControlSize.Large -> 13
            ControlSize.ExtraLarge -> 14
        }

        /** Sketch: Large=15sp, Medium=13sp, Small=11sp */
        fun itemFontSizeFor(controlSize: ControlSize): Int = when (controlSize) {
            ControlSize.Mini -> 10
            ControlSize.Small -> 11
            ControlSize.Regular -> 13
            ControlSize.Large -> 15
            ControlSize.ExtraLarge -> 17
        }

        /** Sketch: Level 0 indent=0, Level 1=10dp, Level 2+=12dp per level */
        fun indentFor(level: Int): Dp = when (level) {
            0 -> 0.dp
            1 -> 10.dp
            else -> (10 + (level - 1) * 12).dp
        }
    }
}


// ---------------------------------------------------------------------------
// StepperStyle
// ---------------------------------------------------------------------------

@Immutable
data class StepperStyle(
    val colors: Colors,
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Colors(
        // Content Area — button half backgrounds
        val background: Color,
        val backgroundPressed: Color,
        val backgroundDisabled: Color,
        // Over-glass — button half backgrounds
        val overGlassBackground: Color,
        val overGlassBackgroundPressed: Color,
        val overGlassBackgroundDisabled: Color,
        // Arrow / chevron
        val arrow: Color,
        val arrowDisabled: Color,
        // Separator
        val separator: Color,
    )

    @Immutable
    data class Metrics(
        val disabledAlpha: Float = 0.5f,
    ) {
        fun widthFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 13.dp
            ControlSize.Small -> 17.dp
            ControlSize.Regular -> 20.dp
            ControlSize.Large -> 23.dp
            ControlSize.ExtraLarge -> 30.dp
        }

        fun heightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 16.dp
            ControlSize.Small -> 20.dp
            ControlSize.Regular -> 24.dp
            ControlSize.Large -> 28.dp
            ControlSize.ExtraLarge -> 36.dp
        }

        fun cornerRadiusFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 4.dp
            ControlSize.Small -> 5.dp
            ControlSize.Regular -> 6.dp
            ControlSize.Large -> 7.dp
            ControlSize.ExtraLarge -> 9.dp
        }

        // Sketch: TextField width in StepperField composites is always 60dp
        val fieldWidth: Dp = 60.dp

        // Gap between TextField and Stepper in Outside layout
        val fieldGap: Dp = 4.dp
    }
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


// ---------------------------------------------------------------------------
// TimePickerStyle
// ---------------------------------------------------------------------------

@Immutable
data class TimePickerStyle(
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Metrics(
        val disabledAlpha: Float = 0.45f,
    ) {
        // -- Inline row (TimePicker / DateTimePicker) -------------------------

        /** Height of the inline TimePickerRow */
        fun rowHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 32.dp
            ControlSize.Small -> 40.dp
            ControlSize.Regular -> 52.dp
            ControlSize.Large -> 60.dp
            ControlSize.ExtraLarge -> 72.dp
        }

        // -- Pill / trigger button -------------------------------------------

        /** Height of the time value pill and trigger buttons */
        fun pillHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 20.dp
            ControlSize.Small -> 26.dp
            ControlSize.Regular -> 34.dp
            ControlSize.Large -> 40.dp
            ControlSize.ExtraLarge -> 48.dp
        }

        /** Minimum width of the time value pill */
        fun pillMinWidthFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 42.dp
            ControlSize.Small -> 52.dp
            ControlSize.Regular -> 67.dp
            ControlSize.Large -> 78.dp
            ControlSize.ExtraLarge -> 92.dp
        }

        /** Horizontal padding inside pills and trigger buttons */
        fun pillHorizontalPaddingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 6.dp
            ControlSize.Small -> 8.dp
            ControlSize.Regular -> 12.dp
            ControlSize.Large -> 14.dp
            ControlSize.ExtraLarge -> 16.dp
        }

        /** Font size for labels and pill text */
        fun labelFontSizeFor(controlSize: ControlSize): TextUnit = when (controlSize) {
            ControlSize.Mini -> 11.sp
            ControlSize.Small -> 13.sp
            ControlSize.Regular -> 17.sp
            ControlSize.Large -> 20.sp
            ControlSize.ExtraLarge -> 22.sp
        }

        // -- AM/PM segmented control -----------------------------------------

        /** Width of the AM/PM segmented control */
        fun amPmWidthFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 60.dp
            ControlSize.Small -> 76.dp
            ControlSize.Regular -> 100.dp
            ControlSize.Large -> 120.dp
            ControlSize.ExtraLarge -> 144.dp
        }

        /** Height of the AM/PM segmented control */
        fun amPmHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 22.dp
            ControlSize.Small -> 28.dp
            ControlSize.Regular -> 36.dp
            ControlSize.Large -> 42.dp
            ControlSize.ExtraLarge -> 50.dp
        }

        /** Corner radius of the AM/PM container */
        fun amPmCornerRadiusFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 11.dp
            ControlSize.Small -> 14.dp
            ControlSize.Regular -> 22.dp
            ControlSize.Large -> 21.dp
            ControlSize.ExtraLarge -> 25.dp
        }

        /** Inner padding of the AM/PM container */
        fun amPmPaddingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 2.dp
            ControlSize.Small -> 3.dp
            ControlSize.Regular -> 4.dp
            ControlSize.Large -> 5.dp
            ControlSize.ExtraLarge -> 6.dp
        }

        /** Height of the AM/PM active pill indicator */
        fun amPmPillHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 18.dp
            ControlSize.Small -> 22.dp
            ControlSize.Regular -> 28.dp
            ControlSize.Large -> 32.dp
            ControlSize.ExtraLarge -> 38.dp
        }

        /** Corner radius of the AM/PM pill */
        fun amPmPillCornerRadiusFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 9.dp
            ControlSize.Small -> 11.dp
            ControlSize.Regular -> 14.dp
            ControlSize.Large -> 16.dp
            ControlSize.ExtraLarge -> 19.dp
        }

        /** Font size for AM/PM text */
        fun amPmFontSizeFor(controlSize: ControlSize): TextUnit = when (controlSize) {
            ControlSize.Mini -> 10.sp
            ControlSize.Small -> 11.sp
            ControlSize.Regular -> 14.sp
            ControlSize.Large -> 16.sp
            ControlSize.ExtraLarge -> 18.sp
        }

        // -- Spacing ----------------------------------------------------------

        /** Spacing between pill and AM/PM, between trigger buttons */
        fun spacingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 4.dp
            ControlSize.Small -> 6.dp
            ControlSize.Regular -> 8.dp
            ControlSize.Large -> 10.dp
            ControlSize.ExtraLarge -> 12.dp
        }

        // -- Wheel picker -----------------------------------------------------

        /** Width of the wheel picker container */
        fun wheelWidthFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 200.dp
            ControlSize.Small -> 240.dp
            ControlSize.Regular -> 297.dp
            ControlSize.Large -> 340.dp
            ControlSize.ExtraLarge -> 400.dp
        }

        /** Height of each wheel item row */
        fun wheelItemHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 22.dp
            ControlSize.Small -> 26.dp
            ControlSize.Regular -> 34.dp
            ControlSize.Large -> 40.dp
            ControlSize.ExtraLarge -> 48.dp
        }

        /** Corner radius of the wheel selection indicator */
        fun wheelIndicatorCornerFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 11.dp
            ControlSize.Small -> 13.dp
            ControlSize.Regular -> 17.dp
            ControlSize.Large -> 20.dp
            ControlSize.ExtraLarge -> 24.dp
        }

        /** Number of visible items in the wheel */
        fun wheelVisibleItemsFor(controlSize: ControlSize): Int = when (controlSize) {
            ControlSize.Mini -> 5
            ControlSize.Small -> 5
            ControlSize.Regular -> 7
            ControlSize.Large -> 7
            ControlSize.ExtraLarge -> 9
        }

        /** Font size for the selected wheel item */
        fun wheelSelectedFontSizeFor(controlSize: ControlSize): TextUnit = when (controlSize) {
            ControlSize.Mini -> 15.sp
            ControlSize.Small -> 18.sp
            ControlSize.Regular -> 23.sp
            ControlSize.Large -> 27.sp
            ControlSize.ExtraLarge -> 32.sp
        }

        /** Font size for unselected wheel items */
        fun wheelUnselectedFontSizeFor(controlSize: ControlSize): TextUnit = when (controlSize) {
            ControlSize.Mini -> 13.sp
            ControlSize.Small -> 16.sp
            ControlSize.Regular -> 20.sp
            ControlSize.Large -> 24.sp
            ControlSize.ExtraLarge -> 28.sp
        }
    }
}

// ===========================================================================
// DatePickerStyle — calendar date picker sizing
// ===========================================================================

@Immutable
data class DatePickerStyle(
    val metrics: Metrics = Metrics(),
) {
    @Immutable
    data class Metrics(
        val disabledAlpha: Float = 0.45f,
    ) {
        // -- Container --------------------------------------------------------

        /** Width of the calendar container */
        fun containerWidthFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 230.dp
            ControlSize.Small -> 290.dp
            ControlSize.Regular -> 370.dp
            ControlSize.Large -> 430.dp
            ControlSize.ExtraLarge -> 510.dp
        }

        /** Horizontal padding inside the container */
        fun containerPaddingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 8.dp
            ControlSize.Small -> 12.dp
            ControlSize.Regular -> 16.dp
            ControlSize.Large -> 20.dp
            ControlSize.ExtraLarge -> 24.dp
        }

        /** Corner radius of the container */
        fun containerCornerRadiusFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 8.dp
            ControlSize.Small -> 10.dp
            ControlSize.Regular -> 13.dp
            ControlSize.Large -> 16.dp
            ControlSize.ExtraLarge -> 18.dp
        }

        // -- Calendar header --------------------------------------------------

        /** Height of the header row */
        fun headerHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 26.dp
            ControlSize.Small -> 32.dp
            ControlSize.Regular -> 40.dp
            ControlSize.Large -> 48.dp
            ControlSize.ExtraLarge -> 56.dp
        }

        /** Font size for the month/year title */
        fun headerTitleFontSizeFor(controlSize: ControlSize): TextUnit = when (controlSize) {
            ControlSize.Mini -> 11.sp
            ControlSize.Small -> 14.sp
            ControlSize.Regular -> 17.sp
            ControlSize.Large -> 20.sp
            ControlSize.ExtraLarge -> 24.sp
        }

        /** Size of the title chevron icon */
        fun headerChevronSizeFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 9.dp
            ControlSize.Small -> 11.dp
            ControlSize.Regular -> 13.dp
            ControlSize.Large -> 16.dp
            ControlSize.ExtraLarge -> 18.dp
        }

        /** Size of the navigation arrow icons */
        fun navArrowSizeFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 14.dp
            ControlSize.Small -> 16.dp
            ControlSize.Regular -> 20.dp
            ControlSize.Large -> 24.dp
            ControlSize.ExtraLarge -> 28.dp
        }

        /** Spacing between navigation arrows */
        fun navArrowSpacingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 10.dp
            ControlSize.Small -> 12.dp
            ControlSize.Regular -> 16.dp
            ControlSize.Large -> 20.dp
            ControlSize.ExtraLarge -> 24.dp
        }

        // -- Day cells --------------------------------------------------------

        /** Size of each day cell (square) */
        fun dayCellSizeFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 26.dp
            ControlSize.Small -> 34.dp
            ControlSize.Regular -> 44.dp
            ControlSize.Large -> 52.dp
            ControlSize.ExtraLarge -> 62.dp
        }

        /** Font size for unselected day numbers */
        fun dayFontSizeFor(controlSize: ControlSize): TextUnit = when (controlSize) {
            ControlSize.Mini -> 12.sp
            ControlSize.Small -> 16.sp
            ControlSize.Regular -> 20.sp
            ControlSize.Large -> 24.sp
            ControlSize.ExtraLarge -> 28.sp
        }

        /** Font size for the selected day number */
        fun daySelectedFontSizeFor(controlSize: ControlSize): TextUnit = when (controlSize) {
            ControlSize.Mini -> 14.sp
            ControlSize.Small -> 18.sp
            ControlSize.Regular -> 24.sp
            ControlSize.Large -> 28.sp
            ControlSize.ExtraLarge -> 32.sp
        }

        // -- Day-of-week headers ----------------------------------------------

        /** Height of the day-of-week header row cells */
        fun dayHeaderHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 12.dp
            ControlSize.Small -> 14.dp
            ControlSize.Regular -> 18.dp
            ControlSize.Large -> 22.dp
            ControlSize.ExtraLarge -> 26.dp
        }

        /** Font size for day-of-week labels */
        fun dayHeaderFontSizeFor(controlSize: ControlSize): TextUnit = when (controlSize) {
            ControlSize.Mini -> 8.sp
            ControlSize.Small -> 10.sp
            ControlSize.Regular -> 13.sp
            ControlSize.Large -> 15.sp
            ControlSize.ExtraLarge -> 18.sp
        }

        // -- Month / Year grid ------------------------------------------------

        /** Width of month/year grid cells */
        fun gridCellWidthFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 44.dp
            ControlSize.Small -> 56.dp
            ControlSize.Regular -> 72.dp
            ControlSize.Large -> 84.dp
            ControlSize.ExtraLarge -> 100.dp
        }

        /** Height of month/year grid cells */
        fun gridCellHeightFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 26.dp
            ControlSize.Small -> 32.dp
            ControlSize.Regular -> 40.dp
            ControlSize.Large -> 48.dp
            ControlSize.ExtraLarge -> 56.dp
        }

        /** Font size for month/year grid cell text */
        fun gridCellFontSizeFor(controlSize: ControlSize): TextUnit = when (controlSize) {
            ControlSize.Mini -> 10.sp
            ControlSize.Small -> 12.sp
            ControlSize.Regular -> 14.sp
            ControlSize.Large -> 17.sp
            ControlSize.ExtraLarge -> 20.sp
        }

        /** Corner radius of month/year grid cells */
        fun gridCellCornerRadiusFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 5.dp
            ControlSize.Small -> 6.dp
            ControlSize.Regular -> 8.dp
            ControlSize.Large -> 10.dp
            ControlSize.ExtraLarge -> 12.dp
        }

        /** Vertical spacing between grid rows */
        fun gridRowSpacingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 4.dp
            ControlSize.Small -> 6.dp
            ControlSize.Regular -> 8.dp
            ControlSize.Large -> 10.dp
            ControlSize.ExtraLarge -> 12.dp
        }

        // -- Calendar grid spacing --------------------------------------------

        /** Vertical spacing between calendar day rows */
        fun calendarRowSpacingFor(controlSize: ControlSize): Dp = when (controlSize) {
            ControlSize.Mini -> 1.dp
            ControlSize.Small -> 1.dp
            ControlSize.Regular -> 2.dp
            ControlSize.Large -> 3.dp
            ControlSize.ExtraLarge -> 4.dp
        }
    }
}
