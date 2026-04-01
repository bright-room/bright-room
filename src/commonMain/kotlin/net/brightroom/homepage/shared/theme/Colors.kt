package net.brightroom.homepage.shared.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// Dark theme colors (based on JSX reference)
private val DarkAccent = Color(0xFFF0C040)
private val DarkBackground = Color(0xFF0A0A0C)
private val DarkSurface = Color(0xFF1E1E24)
private val DarkSurfaceContainer = Color(0xFF16161A)
private val DarkSurfaceContainerHigh = Color(0xFF1C1C22)
private val DarkOnBackground = Color(0xFFE8E6E3)
private val DarkOnSurfaceVariant = Color(0xFF8A8A96)
private val DarkOutline = Color(0xFF2A2A32)
private val DarkOutlineVariant = Color(0xFF3A3A44)

// Light theme colors
private val LightAccent = Color(0xFFC8960A)
private val LightBackground = Color(0xFFF8F7F4)
private val LightSurface = Color(0xFFEAE8E4)
private val LightSurfaceContainer = Color(0xFFFFFFFF)
private val LightSurfaceContainerHigh = Color(0xFFF0EFEC)
private val LightOnBackground = Color(0xFF1A1A1E)
private val LightOnSurfaceVariant = Color(0xFF5A5A66)
private val LightOutline = Color(0xFFD8D6D0)
private val LightOutlineVariant = Color(0xFFC0BEB8)

// Semantic colors
val AccentGreen = Color(0xFF4ADE80)
val AccentBlue = Color(0xFF60A5FA)
val AccentPink = Color(0xFFF472B6)

fun changeColorScheme(isDarkTheme: Boolean): ColorScheme =
    if (isDarkTheme) {
        darkColorScheme(
            primary = DarkAccent,
            onPrimary = DarkBackground,
            background = DarkBackground,
            onBackground = DarkOnBackground,
            surface = DarkSurface,
            onSurface = DarkOnBackground,
            onSurfaceVariant = DarkOnSurfaceVariant,
            surfaceContainer = DarkSurfaceContainer,
            surfaceContainerHigh = DarkSurfaceContainerHigh,
            outline = DarkOutline,
            outlineVariant = DarkOutlineVariant,
            inverseSurface = LightBackground,
            inverseOnSurface = LightOnBackground,
        )
    } else {
        lightColorScheme(
            primary = LightAccent,
            onPrimary = Color.White,
            background = LightBackground,
            onBackground = LightOnBackground,
            surface = LightSurface,
            onSurface = LightOnBackground,
            onSurfaceVariant = LightOnSurfaceVariant,
            surfaceContainer = LightSurfaceContainer,
            surfaceContainerHigh = LightSurfaceContainerHigh,
            outline = LightOutline,
            outlineVariant = LightOutlineVariant,
            inverseSurface = DarkBackground,
            inverseOnSurface = DarkOnBackground,
        )
    }
