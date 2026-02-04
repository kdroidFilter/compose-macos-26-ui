package io.github.kdroidfilter.darwinui.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "darwinui",
    ) {
        App()
    }
}
