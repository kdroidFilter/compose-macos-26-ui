plugins {
    alias(libs.plugins.kotlinJvm)
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:${libs.versions.ksp.get()}")
}
