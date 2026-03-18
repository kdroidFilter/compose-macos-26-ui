package io.github.kdroidfilter.nucleus.ui.apple.macos.sample.gallery

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import composemacosui.sample.generated.resources.Res
import composemacosui.sample.generated.resources.jetbrains_mono_bold
import composemacosui.sample.generated.resources.jetbrains_mono_regular
import org.jetbrains.compose.resources.Font

@Composable
fun JetBrainsMonoFontFamily(): FontFamily =
    FontFamily(
        Font(Res.font.jetbrains_mono_regular, weight = FontWeight.Normal),
        Font(Res.font.jetbrains_mono_bold, weight = FontWeight.Bold),
    )
