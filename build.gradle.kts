import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.spotless)
}

kotlin {
    val javaVersion = libs.versions.java.get()
    jvmToolchain(javaVersion.toInt())

    js(IR) {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        val webMain by creating {}
        jsMain {}
        wasmJsMain {}

        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.material.icons.extended)
            implementation(libs.compose.adaptive)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.material3.adaptive.navigation.suite)

            implementation(libs.compose.ui.tooling.preview)

            implementation(libs.lifecycle.viewmodel)
            implementation(libs.lifecycle.viewmodel.compose)

            implementation(npm("@js-joda/timezone", "2.3.0"))
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

rootProject.extensions.configure<YarnRootExtension> {
    resolution("serialize-javascript", "7.0.5")
    resolution("webpack", "5.104.1")
    resolution("diff", "8.0.3")
    resolution("qs", "6.15.2")
    resolution("uuid", "11.1.1")
    resolution("webpack-dev-server", "5.2.6")
    resolution("ws", "8.21.0")
}

spotless {
    kotlin {
        ktlint()
        target("**/*.kt")
        targetExclude("build/**/*.kt", "bin/**/*.kt")
    }

    kotlinGradle {
        ktlint()
        target("**/*.kts")
        targetExclude("build/**/*.kts", "bin/**/*.kts")
    }
}
