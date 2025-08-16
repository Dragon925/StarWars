@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.dragon925.starwars.uikit.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.dragon925.starwars.uikit.R
import com.github.dragon925.starwars.uikit.states.SearchFieldState
import com.github.dragon925.starwars.uikit.states.rememberSearchFieldState
import com.github.dragon925.starwars.uikit.theme.StarWarsTheme
import com.github.dragon925.starwars.uikit.theme.ThemeType

@Composable
fun TopSearchAppBar(
    state: SearchFieldState,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    onSearch: (String) -> Unit = {},
    onActionClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Surface(
                modifier = Modifier.fillMaxWidth()
                    .height(56.dp)
                    .padding(
                        start = StarWarsTheme.dimens.spacingS
                    ),
                shape = StarWarsTheme.shapes.medium,
                color = StarWarsTheme.colorScheme.surfaceVariant
            ) {
                SearchField(
                    state = state,
                    onSearch = onSearch,
                    modifier = Modifier.fillMaxSize(),
                    placeholder = placeholder?.let { @Composable {
                        Text(
                            text = it,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = StarWarsTheme.typography.bodyLarge
                        )
                    } }
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_star_wars_logo),
                tint = StarWarsTheme.colorScheme.onBackground,
                contentDescription = null
            )
        },
        actions = {
            IconButton(
                onClick = onActionClick
            ) {
                val icon = when (StarWarsTheme.themeType) {
                    ThemeType.EMPIRE -> painterResource(R.drawable.ic_empire)
                    ThemeType.REBEL -> painterResource(R.drawable.ic_rebel)
                }
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = StarWarsTheme.colorScheme.onSurface
                )
            }
        }
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun TopSearchAppBarEmpirePreview() {
    StarWarsTheme {
        TopSearchAppBar(
            state = rememberSearchFieldState(""),
            placeholder = "Search"
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun TopSearchAppBarRebelPreview() {
    StarWarsTheme(isEmpire = false) {
        TopSearchAppBar(rememberSearchFieldState("Sjjj"))
    }
}