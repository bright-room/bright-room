package net.brightroom.homepage.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import net.brightroom.homepage.shared.layout.Layout

val LocalAppViewModel = compositionLocalOf<AppViewModel> { error("No AppViewModel provided") }

@Composable
fun App() {
    val viewModel = viewModel { AppViewModel() }

    LaunchedEffect(Unit) {
        viewModel.loadContent()
    }

    CompositionLocalProvider(LocalAppViewModel provides viewModel) {
        Theme(isDarkTheme = viewModel.isDarkTheme) {
            Layout()
        }
    }
}
