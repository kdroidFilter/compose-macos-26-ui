package io.github.kdroidfilter.darwinui.theme

import androidx.compose.runtime.Composable

@Composable
internal actual fun PlatformContextMenuOverride(content: @Composable (() -> Unit)) = content()