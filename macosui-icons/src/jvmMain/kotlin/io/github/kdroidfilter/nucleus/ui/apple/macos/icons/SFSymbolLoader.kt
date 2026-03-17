package io.github.kdroidfilter.nucleus.ui.apple.macos.icons

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ColorType
import org.jetbrains.skia.ImageInfo
import java.nio.ByteBuffer
import java.util.concurrent.ConcurrentHashMap

private const val HEADER_SIZE = 8
private const val BYTES_PER_PIXEL = 4

internal object SFSymbolLoader {
    private val cache = ConcurrentHashMap<String, ImageBitmap>()
    private val missed = ConcurrentHashMap.newKeySet<String>()

    fun get(name: String): ImageBitmap? {
        if (name in missed) return null
        cache[name]?.let { return it }
        val bitmap = loadSymbol(name)
        if (bitmap != null) {
            cache[name] = bitmap
        } else {
            missed.add(name)
        }
        return bitmap
    }

    private fun loadSymbol(name: String): ImageBitmap? {
        if (!SFSymbolBridge.isLoaded) return null
        val raw = SFSymbolBridge.nativeLoadSymbol(name) ?: return null
        if (raw.size < HEADER_SIZE) return null
        return try {
            val buffer = ByteBuffer.wrap(raw, 0, HEADER_SIZE)
            val width = buffer.int
            val height = buffer.int
            if (width <= 0 || height <= 0) return null

            val expectedPixels = width * height * BYTES_PER_PIXEL
            if (raw.size - HEADER_SIZE != expectedPixels) return null

            // Extract pixel data (skip 8-byte header)
            val pixels = raw.copyOfRange(HEADER_SIZE, raw.size)

            val info = ImageInfo(width, height, ColorType.RGBA_8888, ColorAlphaType.PREMUL)
            val rowBytes = width * BYTES_PER_PIXEL
            val bitmap = Bitmap()
            bitmap.installPixels(info, pixels, rowBytes)
            bitmap.setImmutable()
            bitmap.asComposeImageBitmap()
        } catch (_: Exception) {
            null
        }
    }
}
