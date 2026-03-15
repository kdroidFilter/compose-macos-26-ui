package io.github.kdroidfilter.nucleus.ui.apple.macos.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import io.github.kdroidfilter.nucleus.ui.apple.macos.util.isApplePlatform

@Composable
actual fun MacosFontFamily(): FontFamily =
    if (isApplePlatform) FontFamily.Default else ManropeFontFamily()
