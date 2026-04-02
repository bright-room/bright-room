@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
@file:OptIn(InternalResourceApi::class)

package net.brightroom.homepage.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.viewmodel.compose.viewModel
import net.brightroom.homepage.shared.layout.Layout
import org.jetbrains.compose.resources.ComposeEnvironment
import org.jetbrains.compose.resources.DensityQualifier
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.LanguageQualifier
import org.jetbrains.compose.resources.LocalComposeEnvironment
import org.jetbrains.compose.resources.RegionQualifier
import org.jetbrains.compose.resources.ResourceEnvironment
import org.jetbrains.compose.resources.ThemeQualifier

val LocalAppViewModel = compositionLocalOf<AppViewModel> { error("No AppViewModel provided") }

@Composable
fun App() {
    val viewModel = viewModel { AppViewModel() }

    LaunchedEffect(Unit) {
        viewModel.loadContent()
    }

    val density = LocalDensity.current
    val languageQualifier =
        if (viewModel.isJapanese) LanguageQualifier("ja") else LanguageQualifier("en")
    val composeEnvironment =
        remember(languageQualifier, density) {
            object : ComposeEnvironment {
                @Composable
                override fun rememberEnvironment(): ResourceEnvironment =
                    ResourceEnvironment(
                        language = languageQualifier,
                        region = RegionQualifier(""),
                        theme = ThemeQualifier.selectByValue(viewModel.isDarkTheme),
                        density = DensityQualifier.selectByDensity(density.density),
                    )
            }
        }

    CompositionLocalProvider(
        LocalAppViewModel provides viewModel,
        LocalComposeEnvironment provides composeEnvironment,
    ) {
        Theme(isDarkTheme = viewModel.isDarkTheme) {
            Layout()
        }
    }
}
