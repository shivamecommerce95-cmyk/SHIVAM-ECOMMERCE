package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = SapphireBlue,
    onPrimary = CleanWhite,
    secondary = AccentGold,
    onSecondary = DeepSlateNavy,
    tertiary = BrightGold,
    background = DeepSlateNavy,
    surface = SurfaceDarkBlue,
    onBackground = CleanWhite,
    onSurface = CleanWhite
)

private val LightColorScheme = lightColorScheme(
    primary = RoyalBlueMain,
    onPrimary = CleanWhite,
    secondary = SapphireBlue,
    onSecondary = CleanWhite,
    tertiary = AccentGold,
    background = IceBlueBackground,
    surface = CleanWhite,
    onBackground = RoyalBlueMain,
    onSurface = RoyalBlueMain
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // We prioritize our gold-and-blue luxury branding
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
