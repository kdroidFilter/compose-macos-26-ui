import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    alias(libs.plugins.kotlinJvm)
}

kotlin {
    jvmToolchain(17)
}

// Native build task for macOS JNI bridge (wallpaper color extraction)
val buildNativeMacOs by tasks.registering(Exec::class) {
    description = "Compiles the Objective-C JNI bridge for wallpaper color extraction (arm64 + x64)"
    group = "build"
    val nativeDir = file("src/main/native/macos")
    val outputDir = file("src/main/resources/macosui/native")
    val checkFile = File(outputDir, "darwin-aarch64/libmacosui_wallpaper_jni.dylib")
    onlyIf { Os.isFamily(Os.FAMILY_MAC) && !checkFile.exists() }
    inputs.dir(nativeDir)
    outputs.dir(outputDir)
    workingDir(nativeDir)
    commandLine("bash", "build.sh")
}

tasks.named("processResources") {
    dependsOn(buildNativeMacOs)
}
