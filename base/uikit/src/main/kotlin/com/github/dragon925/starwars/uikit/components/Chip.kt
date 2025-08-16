package com.github.dragon925.starwars.uikit.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.dragon925.starwars.uikit.theme.StarWarsTheme

@Composable
fun Chip(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    SuggestionChip(
        label = {
            Text(
                text = label,
                style = StarWarsTheme.typography.labelLarge,
                color = StarWarsTheme.colorScheme.onSurface
            )
        },
        onClick = onClick,
        modifier = modifier,
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ChipEmpirePreview() {
    StarWarsTheme {
        Box(Modifier.background(StarWarsTheme.colorScheme.surfaceVariant)) {
            Chip(
                label = "Chip",
                onClick = {},
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ChipRebelPreview() {
    StarWarsTheme(isEmpire = false) {
        Box(Modifier.background(StarWarsTheme.colorScheme.surfaceVariant)) {
            Chip(
                label = "Chip",
                onClick = {},
            )
        }
    }
}