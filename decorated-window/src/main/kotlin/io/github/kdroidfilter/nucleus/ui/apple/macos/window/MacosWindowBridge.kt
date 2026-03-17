package io.github.kdroidfilter.nucleus.ui.apple.macos.window

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.logging.Level
import java.util.logging.Logger

private const val LibraryName = "macosui_jni"
private const val ResourcePrefix = "/macosui/native"

object MacosWindowBridge {

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

    @JvmStatic
    external fun nativePerformTitleBarDoubleClick(nsWindowPtr: Long)

    @Suppress("TooGenericExceptionCaught")
    private fun loadLibrary(): Boolean {
        try {
            System.loadLibrary(LibraryName)
            return true
        } catch (_: UnsatisfiedLinkError) { /* fall through */ }

        return try {
            val arch = System.getProperty("os.arch").let {
                if (it == "aarch64" || it == "arm64") "aarch64" else "x64"
            }
            val fileName = "lib$LibraryName.dylib"
            val resourcePath = "$ResourcePrefix/darwin-$arch/$fileName"
            val stream = MacosWindowBridge::class.java.getResourceAsStream(resourcePath)
                ?: throw UnsatisfiedLinkError("Not found in resources: $resourcePath")

            val cacheDir = Path.of(
                System.getProperty("user.home"), "Library", "Caches",
                "macosui", "native", "darwin-$arch",
            )
            Files.createDirectories(cacheDir)
            val target = cacheDir.resolve(fileName)

            stream.use { input ->
                val tmp = Files.createTempFile(cacheDir, LibraryName, ".tmp")
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
            logger.log(Level.WARNING, "Failed to load $LibraryName native library", e)
            false
        }
    }
}
