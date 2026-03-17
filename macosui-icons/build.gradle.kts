import org.apache.tools.ant.taskdefs.condition.Os
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.vanniktechMavenPublish)
    alias(libs.plugins.dokka)
}

val ref = System.getenv("GITHUB_REF") ?: ""
val version = if (ref.startsWith("refs/tags/")) {
    val tag = ref.removePrefix("refs/tags/")
    if (tag.startsWith("v")) tag.substring(1) else tag
} else {
    "dev"
}

mavenPublishing {
    coordinates(
        groupId = "io.github.kdroidfilter",
        artifactId = "compose-macos-ui-icons",
        version = version,
    )

    pom {
        name.set("Compose Macos UI Icons")
        description.set(
            "Icon set for Compose Macos UI — a macOS-inspired component library for Compose Multiplatform.",
        )
        inceptionYear.set("2025")
        url.set("https://github.com/kdroidFilter/compose-macos-26-ui")

        licenses {
            license {
                name.set("GPL-3.0")
                url.set("https://www.gnu.org/licenses/gpl-3.0.html")
            }
        }

        developers {
            developer {
                id.set("kdroidfilter")
                name.set("Elie Gambache")
                email.set("elyahou.hadass@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/kdroidFilter/compose-macos-26-ui")
        }
    }

    publishToMavenCentral()
    signAllPublications()
}

kotlin {
    @Suppress("DEPRECATION")
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "MacosUIIcons"
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
        }
    }
}

// Native build task for macOS JNI bridge (SF Symbol loading)
val buildNativeMacOs by tasks.registering(Exec::class) {
    description = "Compiles the Objective-C JNI bridge for SF Symbol loading (arm64 + x64)"
    group = "build"
    val nativeDir = file("src/jvmMain/native/macos")
    val outputDir = file("src/jvmMain/resources/macosui/native")
    val checkFile = File(outputDir, "darwin-aarch64/libmacosui_icons_jni.dylib")
    onlyIf { Os.isFamily(Os.FAMILY_MAC) && !checkFile.exists() }
    inputs.dir(nativeDir)
    outputs.dir(outputDir)
    workingDir(nativeDir)
    commandLine("bash", "build.sh")
}

tasks.named("jvmProcessResources") {
    dependsOn(buildNativeMacOs)
}

android {
    namespace = "io.github.kdroidfilter.nucleus.ui.apple.macos.icons"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
