package io.github.kdroidfilter.darwinui.theme

import androidx.compose.ui.state.ToggleableState

// ===========================================================================
// 1.2 — Component State Hierarchy
//
// Mirrors Jewel's InteractiveComponentState hierarchy. Provides a clean,
// typed contract for every interactive Darwin UI component.
// ===========================================================================

/**
 * Base state interface for all interactive Darwin UI components.
 *
 * Every interactive component (button, input, checkbox…) implements this interface
 * to expose a consistent set of state flags used for driving animations and
 * appearance changes.
 *
 * @property isActive  Whether the host window/panel is currently active (front-most).
 *                     When false, controls render in their "inactive" appearance
 *                     (macOS grays out controls in background windows).
 * @property isEnabled Whether the component accepts user interaction.
 * @property isHovered Whether the pointer is currently over the component.
 * @property isPressed Whether the component is being pressed.
 */
interface InteractiveComponentState {
    val isActive: Boolean
    val isEnabled: Boolean
    val isHovered: Boolean
    val isPressed: Boolean
}

/**
 * State for components that can receive keyboard focus (text fields, buttons, sliders…).
 */
interface FocusableComponentState : InteractiveComponentState {
    val isFocused: Boolean
}

/**
 * State for components that can be selected (list items, tabs, tree nodes…).
 */
interface SelectableComponentState : InteractiveComponentState {
    val isSelected: Boolean
}

/**
 * State for focusable components that can also be selected.
 * Typical use: selectable list rows, tree nodes.
 */
interface FocusableSelectableComponentState : FocusableComponentState, SelectableComponentState

/**
 * State for toggleable components (checkbox, switch, radio button).
 *
 * @property toggleableState The tri-state value (On / Off / Indeterminate).
 * @property isSelected      True when [toggleableState] is [ToggleableState.On].
 * @property isIndeterminate True when [toggleableState] is [ToggleableState.Indeterminate].
 * @property isSelectedOrIndeterminate True when either [isSelected] or [isIndeterminate].
 */
interface ToggleableComponentState {
    val toggleableState: ToggleableState
    val isEnabled: Boolean

    val isSelected: Boolean
        get() = toggleableState == ToggleableState.On

    val isIndeterminate: Boolean
        get() = toggleableState == ToggleableState.Indeterminate

    val isSelectedOrIndeterminate: Boolean
        get() = isSelected || isIndeterminate
}
