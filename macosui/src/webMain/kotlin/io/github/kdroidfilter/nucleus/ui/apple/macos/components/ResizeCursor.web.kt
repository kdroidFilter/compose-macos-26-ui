package io.github.kdroidfilter.nucleus.ui.apple.macos.components

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.fromKeyword

@OptIn(ExperimentalComposeUiApi::class)
internal actual val pointerIconResizeHorizontal: PointerIcon =
    PointerIcon.Companion.fromKeyword("col-resize")
