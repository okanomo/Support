package com.example.yard.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BlueLight,
    secondary = BlueMedium,
    tertiary = BlueDark
)

private val LightColorScheme = lightColorScheme(
    primary = BlueMedium,
    secondary = BlueLight,
    tertiary = BlueDark,
    background = ScreenWhite,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = BlueDark,
    onTertiary = Color.White,
    onBackground = BlueDark,
    onSurface = BlueDark
)

@Composable
fun YardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}