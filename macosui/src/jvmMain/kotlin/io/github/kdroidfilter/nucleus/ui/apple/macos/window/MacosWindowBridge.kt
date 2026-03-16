package io.github.kdroidfilter.nucleus.ui.apple.macos.window

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.logging.Level
import java.util.logging.Logger

private const val LIBRARY_NAME = "macosui_jni"
private const val RESOURCE_PREFIX = "/macosui/native"

internal object MacosWindowBridge {

    private val logger = Logger.getLogger(MacosWindowBridge::class.java.simpleName)

    val isLoaded: Boolean = loadLibrary()

    @JvmStatic
    external fun nativeGetNSWindowPtr(window: java.awt.Window): Long

    @JvmStatic
    external fun nativeApplyTitleBar(nsWindowPtr: Long, heightPt: Float): Float

    @JvmStatic
    external fun nativeResetTitleBar(nsWindowPtr: Long)

    @JvmStatic
    external fun nativeRevalidateTitleBar(nsWindowPtr: Long)

    @Suppress("TooGenericExceptionCaught")
    private fun loadLibrary(): Boolean {
        try {
            System.loadLibrary(LIBRARY_NAME)
            return true
        } catch (_: UnsatisfiedLinkError) { /* fall through */ }

        return try {
            val arch = System.getProperty("os.arch").let {
                if (it == "aarch64" || it == "arm64") "aarch64" else "x64"
            }
            val fileName = "lib$LIBRARY_NAME.dylib"
            val resourcePath = "$RESOURCE_PREFIX/darwin-$arch/$fileName"
            val stream = MacosWindowBridge::class.java.getResourceAsStream(resourcePath)
                ?: throw UnsatisfiedLinkError("Not found in resources: $resourcePath")

            val cacheDir = Path.of(
                System.getProperty("user.home"), "Library", "Caches",
                "macosui", "native", "darwin-$arch",
            )
            Files.createDirectories(cacheDir)
            val target = cacheDir.resolve(fileName)

            stream.use { input ->
                val tmp = Files.createTempFile(cacheDir, LIBRARY_NAME, ".tmp")
                Files.copy(input, tmp, StandardCopyOption.REPLACE_EXISTING)
                try {
                    Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE)
                } catch (_: Exception) {
                    Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING)
                }
            }

            System.load(target.toAbsolutePath().toString())
            true
        } catch (e: Exception) {
            logger.log(Level.WARNING, "Failed to load $LIBRARY_NAME native library", e)
            false
        }
    }
}
