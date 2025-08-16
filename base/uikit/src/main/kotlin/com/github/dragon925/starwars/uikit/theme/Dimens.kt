package com.github.dragon925.starwars.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class Dimens(
    val spacingXxs2: Dp = 2.dp,
    val spacingXxs: Dp = 4.dp,
    val spacingXs: Dp = 8.dp,
    val spacingS: Dp = 12.dp,
    val spacingM: Dp = 16.dp,
    val spacingL: Dp = 20.dp,
    val spacingXl: Dp = 24.dp,
    val spacingXxl: Dp = 28.dp,
    val spacingXxl2: Dp = 32.dp
)

internal val LocalDimens = staticCompositionLocalOf { Dimens() }