import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.ksp)
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
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(project(":macosui"))
            implementation(project(":macosui-icons-extended"))
            implementation(project(":gallery-annotations"))
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.compose.nav3)
            implementation(libs.androidx.lifecycle.viewmodel.nav3)
            implementation(libs.kotlinx.datetime)
            implementation(libs.highlights)
            implementation(libs.icons.lucide.cmp)
            implementation(libs.kotlin.math)
            implementation(project(":macos-markdown"))
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.kotlinx.datetime)
            implementation(libs.nucleus.core.runtime)
            implementation(libs.nucleus.darkmode.detector)
            implementation(libs.nucleus.system.color)
        }
        webMain.dependencies {
            implementation(libs.navigation3.browser)
        }
    }
}

// KSP for commonMain — generates once, shared by all targets
dependencies {
    debugImplementation(libs.compose.uiTooling)
    add("kspCommonMainMetadata", project(":gallery-ksp"))
}

// Make generated sources visible to commonMain
kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}

// Ensure KSP runs before compilation
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

android {
    namespace = "io.github.kdroidfilter.nucleus.ui.apple.macos.sample"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.github.kdroidfilter.nucleus.ui.apple.macos.sample"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

compose.desktop {
    application {
        mainClass = "io.github.kdroidfilter.nucleus.ui.apple.macos.sample.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.github.kdroidfilter.nucleus.ui.apple.macos.sample"
            packageVersion = "1.0.0"
        }
    }
}

// Launches the sample app with a patched JVM claiming macOS SDK 26.0,
// enabling Liquid Glass window decorations.
val patchJvm by tasks.registering(Exec::class) {
    group = "compose desktop"
    description = "Patch the JVM binary for macOS SDK 26.0 (Liquid Glass)"
    commandLine("bash", rootProject.file("scripts/run-liquidglass.sh").absolutePath, "-version")
    // The script is idempotent and caches its output
    outputs.upToDateWhen { false }
}

tasks.register<JavaExec>("runLiquidGlass") {
    group = "compose desktop"
    description = "Run the sample app with Liquid Glass enabled (macOS SDK 26.0 spoof)"

    val jvmTarget = kotlin.jvm().compilations.getByName("main")
    dependsOn(jvmTarget.compileTaskProvider, patchJvm)

    mainClass.set("io.github.kdroidfilter.nucleus.ui.apple.macos.sample.MainKt")
    classpath = jvmTarget.output.allOutputs + jvmTarget.runtimeDependencyFiles!!

    val patchedJava = File(
        System.getProperty("user.home"),
        "Library/Caches/macosui/patched-jvm/bin/java",
    )
    executable = patchedJava.absolutePath
}
