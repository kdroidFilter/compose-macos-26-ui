package io.github.kdroidfilter.nucleus.ui.apple.macos.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Inspector
import io.github.kdroidfilter.nucleus.ui.apple.macos.components.Text
import io.github.kdroidfilter.nucleus.ui.apple.macos.theme.MacosTheme
import io.github.kdroidfilter.nucleus.ui.apple.macos.sample.pages.*

/**
 * Page IDs that have control-size variant previews in the inspector.
 */
private val sizeVariantPageIds = setOf(
    "button", "iconbutton", "input", "searchinput", "checkbox", "radiobutton",
    "switch", "combobox", "multiselect", "slider", "circularslider", "stepper",
    "popupbutton", "datepicker", "colorwell", "tabs", "progress", "popover",
    "avatar", "scrollbar", "material", "surface",
)

/**
 * Returns true if the given page has control-size variant previews.
 */
fun pageHasSizeVariants(pageId: String): Boolean = pageId in sizeVariantPageIds

/**
 * Inspector content that renders the current page's size variant preview.
 */
@Composable
fun InspectorSizePreview(pageId: String) {
    Inspector {
        if (pageHasSizeVariants(pageId)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = "Size Variants",
                    style = MacosTheme.typography.headline,
                    fontWeight = FontWeight.SemiBold,
                    color = MacosTheme.colorScheme.textPrimary,
                )
                SizeVariantContent(pageId)
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "No size variants for this component",
                    style = MacosTheme.typography.subheadline,
                    color = MacosTheme.colorScheme.textTertiary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun SizeVariantContent(pageId: String) {
    when (pageId) {
        "button" -> ButtonSizesExample()
        "iconbutton" -> IconButtonSizesExample()
        "input" -> InputSizesExample()
        "searchinput" -> SearchInputSizesExample()
        "checkbox" -> CheckboxSizesExample()
        "radiobutton" -> RadioButtonSizesExample()
        "switch" -> SwitchSizesExample()
        "combobox" -> ComboBoxSizesExample()
        "multiselect" -> MultiSelectSizesExample()
        "slider" -> SliderSizesExample()
        "circularslider" -> CircularSliderSizesExample()
        "stepper" -> {
            SectionLabel("Standalone")
            StepperNoFieldSizesExample()
            SectionLabel("Outside Field")
            StepperOutsideFieldSizesExample()
            SectionLabel("Inside Field")
            StepperInsideFieldSizesExample()
        }
        "popupbutton" -> PopupButtonSizesExample()
        "datepicker" -> {
            SectionLabel("Time Button")
            TimePickerButtonSizesExample()
            SectionLabel("DateTime Button")
            DateTimePickerButtonSizesExample()
            SectionLabel("Inline Time Picker")
            TimePickerSizesExample()
        }
        "colorwell" -> ColorWellSizesExample()
        "tabs" -> TabsSizesExample()
        "progress" -> ProgressSizesExample()
        "popover" -> PopoverSizesExample()
        "avatar" -> AvatarSizesExample()
        "scrollbar" -> ScrollbarControlSizesExample()
        "material" -> MaterialSizesExample()
        "surface" -> SurfaceGlassSizesExample()
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MacosTheme.typography.subheadline,
        fontWeight = FontWeight.SemiBold,
        color = MacosTheme.colorScheme.textSecondary,
        modifier = Modifier.padding(top = 8.dp),
    )
}
