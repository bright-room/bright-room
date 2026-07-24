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
    resolution("lodash", "4.18.1")
    resolution("serialize-javascript", "7.0.5")
    resolution("webpack", "5.104.1")
    resolution("diff", "8.0.3")
    resolution("body-parser", "1.20.6")
    resolution("brace-expansion", "2.1.2")
    resolution("engine.io", "6.6.7")
    resolution("fast-uri", "3.1.4")
    resolution("follow-redirects", "1.16.0")
    resolution("http-proxy-middleware", "2.0.10")
    resolution("js-yaml", "4.3.0")
    resolution("launch-editor", "2.14.1")
    resolution("qs", "6.15.2")
    resolution("shell-quote", "1.9.0")
    resolution("tmp", "0.2.7")
    resolution("uuid", "11.1.1")
    resolution("webpack-dev-server", "5.2.6")
    resolution("websocket-driver", "0.7.5")
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
