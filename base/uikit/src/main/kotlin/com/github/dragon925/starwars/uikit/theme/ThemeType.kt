package com.github.dragon925.starwars.uikit.theme

import androidx.compose.runtime.staticCompositionLocalOf

enum class ThemeType {
    EMPIRE, REBEL
}

internal val LocalThemeType = staticCompositionLocalOf { ThemeType.EMPIRE }