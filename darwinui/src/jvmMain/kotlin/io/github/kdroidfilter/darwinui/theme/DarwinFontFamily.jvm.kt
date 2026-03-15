package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import io.github.kdroidfilter.darwinui.util.isApplePlatform

@Composable
actual fun DarwinFontFamily(): FontFamily =
    if (isApplePlatform) FontFamily.Default else ManropeFontFamily()
