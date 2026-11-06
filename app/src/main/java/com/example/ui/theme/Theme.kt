package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MiTierraColorScheme = darkColorScheme(
    primary = BrandGreenLight,
    onPrimary = SurfaceNavyDark,
    primaryContainer = BrandVibrantGreen,
    onPrimaryContainer = TextPrimary,
    secondary = BrandBlueLight,
    onSecondary = TextPrimary,
    secondaryContainer = BrandBlueNavy,
    onSecondaryContainer = TextPrimary,
    tertiary = BrandGold,
    onTertiary = SurfaceNavyDark,
    background = BrandDeepBlueDark,
    onBackground = TextPrimary,
    surface = SurfaceNavyDark,
    onSurface = TextPrimary,
    surfaceVariant = SurfaceCard,
    onSurfaceVariant = TextSecondary,
    outline = BorderSubtle,
    error = BrandVibrantRed,
    onError = TextPrimary
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Radio app features a sleek dark studio theme by default
    dynamicColor: Boolean = false, // Preserve station brand colors (deep blue & vibrant green)
    content: @Composable () -> Unit
) {
    val colorScheme = MiTierraColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = BrandDeepBlueDark.toArgb()
            window.navigationBarColor = BrandDeepBlueDark.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
