package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import composedarwinui.darwinui.generated.resources.Res
import composedarwinui.darwinui.generated.resources.manrope_bold
import composedarwinui.darwinui.generated.resources.manrope_medium
import composedarwinui.darwinui.generated.resources.manrope_regular
import composedarwinui.darwinui.generated.resources.manrope_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun ManropeFontFamily(): FontFamily = FontFamily(
    Font(Res.font.manrope_regular, FontWeight.Normal),
    Font(Res.font.manrope_medium, FontWeight.Medium),
    Font(Res.font.manrope_semibold, FontWeight.SemiBold),
    Font(Res.font.manrope_bold, FontWeight.Bold),
)

/**
 * Darwin UI typography scale — macOS Human Interface Guidelines naming.
 *
 * Reference: developer.apple.com/design/human-interface-guidelines/typography
 */
@Immutable
data class DarwinTypography(
    /** Large Title — 34sp Bold. Top-level window/screen headers. */
    val largeTitle: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 41.sp,
        letterSpacing = 0.sp,
    ),

    /** Title 1 — 28sp Regular. Prominent page/section title. */
    val title1: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.sp,
    ),

    /** Title 2 — 22sp Regular. Secondary section title. */
    val title2: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
    ),

    /** Title 3 — 20sp Regular. Tertiary section title / card header. */
    val title3: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.sp,
    ),

    /** Headline — 17sp SemiBold. Emphasized label (dialog title, list section header). */
    val headline: TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
    ),

    /** Body — 17sp Regular. Default reading text. */
    val body: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
    ),

    /** Callout — 16sp Regular. Secondary descriptive text. */
    val callout: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.sp,
    ),

    /** Subheadline — 15sp Regular. Compact label text. */
    val subheadline: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
    ),

    /** Footnote — 13sp Regular. De-emphasised supplementary text. */
    val footnote: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.sp,
    ),

    /** Caption 1 — 12sp Regular. Small annotation or metadata text. */
    val caption1: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.sp,
    ),

    /** Caption 2 — 11sp Regular. Smallest readable text, use sparingly. */
    val caption2: TextStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 13.sp,
        letterSpacing = 0.sp,
    ),
) {
    internal fun withFontFamily(fontFamily: FontFamily): DarwinTypography = copy(
        largeTitle = largeTitle.copy(fontFamily = fontFamily),
        title1 = title1.copy(fontFamily = fontFamily),
        title2 = title2.copy(fontFamily = fontFamily),
        title3 = title3.copy(fontFamily = fontFamily),
        headline = headline.copy(fontFamily = fontFamily),
        body = body.copy(fontFamily = fontFamily),
        callout = callout.copy(fontFamily = fontFamily),
        subheadline = subheadline.copy(fontFamily = fontFamily),
        footnote = footnote.copy(fontFamily = fontFamily),
        caption1 = caption1.copy(fontFamily = fontFamily),
        caption2 = caption2.copy(fontFamily = fontFamily),
    )
}

val LocalDarwinTypography = staticCompositionLocalOf { DarwinTypography() }
