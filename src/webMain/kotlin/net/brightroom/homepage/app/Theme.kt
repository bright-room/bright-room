package net.brightroom.homepage.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import net.brightroom.homepage.shared.theme.appTypography
import net.brightroom.homepage.shared.theme.changeColorScheme

@Composable
fun Theme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = changeColorScheme(isDarkTheme),
        typography = appTypography(),
        content = content,
    )
}
