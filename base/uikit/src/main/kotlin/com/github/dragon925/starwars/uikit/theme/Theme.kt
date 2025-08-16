package com.github.dragon925.starwars.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.dp

private val LightEmpireScheme = lightColorScheme(
    primary = Red500,
    onPrimary = White,
    primaryContainer = Red100,
    onPrimaryContainer = Red700,
    inversePrimary = Red200,
    secondary = Grey900,
    onSecondary = White,
    secondaryContainer = Grey200,
    onSecondaryContainer = Grey900,
    tertiary = Grey700,
    onTertiary = White,
    tertiaryContainer = Grey100,
    onTertiaryContainer = Grey800,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Grey900,
    surfaceVariant = Grey200,
    onSurfaceVariant = Grey600,
    inverseSurface = Grey900,
    inverseOnSurface = White,
    error = Red600,
    onError = White,
    errorContainer = Red100,
    onErrorContainer = Red700,
    outline = Grey400,
    outlineVariant = Grey300,
)

private val DarkEmpireScheme = darkColorScheme(
    primary = Red400,
    onPrimary = White,
    primaryContainer = Red700,
    onPrimaryContainer = Red100,
    inversePrimary = Red700,
    secondary = White,
    onSecondary = Black,
    secondaryContainer = Grey800,
    onSecondaryContainer = Grey400,
    tertiary = Grey500,
    onTertiary = Black,
    tertiaryContainer = Grey800,
    onTertiaryContainer = Grey400,
    background = Black,
    onBackground = White,
    surface = Grey950,
    onSurface = Grey50,
    surfaceVariant = Grey925,
    onSurfaceVariant = Grey400,
    inverseSurface = Grey300,
    inverseOnSurface = Grey900,
    error = Red300,
    onError = Black,
    errorContainer = Red700,
    onErrorContainer = Red100,
    outline = Grey700,
    outlineVariant = Grey800,
)

private val LightRebelScheme = lightColorScheme(
    primary = Blue500,
    onPrimary = White,
    primaryContainer = Blue25,
    onPrimaryContainer = Blue800,
    inversePrimary = Blue300,
    secondary = Yellow500,
    onSecondary = Black,
    secondaryContainer = Yellow100,
    onSecondaryContainer = Yellow800,
    tertiary = Green500,
    onTertiary = White,
    tertiaryContainer = Green100,
    onTertiaryContainer = Green600,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Grey900,
    surfaceVariant = Grey200,
    onSurfaceVariant = Grey600,
    inverseSurface = Grey900,
    inverseOnSurface = White,
    error = Red600,
    onError = White,
    errorContainer = Red100,
    onErrorContainer = Red700,
    outline = Grey400,
    outlineVariant = Grey300,
)

private val DarkRebelScheme = darkColorScheme(
    primary = Blue200,
    onPrimary = Black,
    primaryContainer = Blue700,
    onPrimaryContainer = Blue50,
    inversePrimary = Blue600,
    secondary = Blue100,
    onSecondary = Black,
    secondaryContainer = Blue400,
    onSecondaryContainer = Blue25,
    tertiary = Green200,
    onTertiary = Black,
    tertiaryContainer = Green400,
    onTertiaryContainer = White,
    background = Black,
    onBackground = White,
    surface = Grey950,
    onSurface = Grey50,
    surfaceVariant = Grey925,
    onSurfaceVariant = Grey400,
    inverseSurface = Grey300,
    inverseOnSurface = Grey900,
    error = Red300,
    onError = Black,
    errorContainer = Red700,
    onErrorContainer = Red100,
    outline = GreyBlue500,
    outlineVariant = GreyBlue700,
)

private val CutShapes = Shapes(
    extraSmall = CutCornerShape(4.dp),
    small = CutCornerShape(8.dp),
    medium = CutCornerShape(16.dp),
    large = CutCornerShape(24.dp),
    extraLarge = CutCornerShape(32.dp)
)

@Composable
fun StarWarsTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isEmpire: Boolean = true,
    content: @Composable () -> Unit
) {
    val (themeType, theme) = when {
        isEmpire -> ThemeType.EMPIRE to if (isDarkTheme) DarkEmpireScheme else LightEmpireScheme
        else -> ThemeType.REBEL to if (isDarkTheme) DarkRebelScheme else LightRebelScheme
    }

    val dimens = Dimens()

    CompositionLocalProvider(
        LocalDimens provides dimens,
        LocalThemeType provides themeType,
    ) {
        MaterialTheme(
            colorScheme = theme,
            shapes = if (isEmpire) CutShapes else MaterialTheme.shapes,
            content = content
        )
    }
}

object StarWarsTheme {
    val themeType: ThemeType
        @Composable @ReadOnlyComposable get() = LocalThemeType.current

    val dimens: Dimens
        @Composable @ReadOnlyComposable get() = LocalDimens.current

    val shapes: Shapes
        @Composable @ReadOnlyComposable get() = MaterialTheme.shapes

    val colorScheme: ColorScheme
        @Composable @ReadOnlyComposable get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable @ReadOnlyComposable get() = MaterialTheme.typography
}