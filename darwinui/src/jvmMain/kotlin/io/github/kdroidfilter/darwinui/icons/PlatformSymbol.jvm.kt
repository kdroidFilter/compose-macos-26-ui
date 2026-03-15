package io.github.kdroidfilter.darwinui.icons

import androidx.compose.ui.graphics.ImageBitmap
import io.github.kdroidfilter.darwinui.util.isApplePlatform

internal actual fun loadPlatformSymbol(name: String): ImageBitmap? {
    if (!isApplePlatform) return null
    return SFSymbolLoader.get(name)
}
