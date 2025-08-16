package com.github.dragon925.starwars.uikit.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.github.dragon925.starwars.uikit.theme.StarWarsTheme

@Composable
fun ItemCard(
    title: String,
    modifier: Modifier = Modifier,
    headline: String? = null,
    text: String? = null,
    actions: List<ItemCardAction> = emptyList()
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = StarWarsTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(StarWarsTheme.dimens.spacingXxs2),
            modifier = Modifier.fillMaxWidth()
                .padding(
                    top = StarWarsTheme.dimens.spacingS,
                    bottom = StarWarsTheme.dimens.spacingXxs,
                    start = StarWarsTheme.dimens.spacingS,
                    end = StarWarsTheme.dimens.spacingS,
                )
        ) {
            headline?.let {
                Text(
                    text = it,
                    style = StarWarsTheme.typography.labelMedium,
                    color = StarWarsTheme.colorScheme.primary
                )
            }

            Text(
                text = title,
                style = StarWarsTheme.typography.titleMedium,
                color = StarWarsTheme.colorScheme.onSurface
            )

            text?.let {
                Text(
                    text = it,
                    style = StarWarsTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = StarWarsTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (actions.isNotEmpty()) {
            HorizontalDivider(
                modifier = Modifier.padding(
                    horizontal = StarWarsTheme.dimens.spacingS,
                    vertical = StarWarsTheme.dimens.spacingXxs
                ),
                color = StarWarsTheme.colorScheme.outlineVariant
            )
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    start = StarWarsTheme.dimens.spacingS,
                    end = StarWarsTheme.dimens.spacingS,
                    bottom = StarWarsTheme.dimens.spacingS
                )
                .wrapContentHeight(align = Alignment.Top),
            horizontalArrangement = Arrangement.spacedBy(StarWarsTheme.dimens.spacingXxs),
            verticalArrangement = Arrangement.spacedBy(-StarWarsTheme.dimens.spacingXs)
        ) {
            actions.forEach { (name, onClick) ->
                Chip(label = name, onClick = onClick)
            }
        }
    }
}

@Immutable
data class ItemCardAction(
    val name: String,
    val onClick: () -> Unit = {}
)

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ItemCardEmpirePreview() {
    StarWarsTheme {
        Box(
            modifier = Modifier
                .background(StarWarsTheme.colorScheme.background)
                .padding(StarWarsTheme.dimens.spacingS)
        ) {
            ItemCard(
                title = "A New Hope",
                headline = "Episode 4",
                text = "Release date: 25.05.1977",
                actions = listOf(
                    ItemCardAction("Characters: 18"),
                    ItemCardAction("Species: 5"),
                    ItemCardAction("Planets: 3"),
                    ItemCardAction("Starships: 8"),
                    ItemCardAction("Vehicles: 4"),
                )
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun ItemCardRebelPreview() {
    StarWarsTheme(isEmpire = false) {
        Box(
            modifier = Modifier
                .background(StarWarsTheme.colorScheme.background)
                .padding(StarWarsTheme.dimens.spacingS)
        ) {
            ItemCard(
                title = "A New Hope",
                headline = "Episode 4",
                text = "Release date: 25.05.1977",
                actions = listOf(
                    ItemCardAction("Characters: 18"),
                    ItemCardAction("Species: 5"),
                    ItemCardAction("Planets: 3"),
                    ItemCardAction("Starships: 8"),
                    ItemCardAction("Vehicles: 4"),
                )
            )
        }
    }
}