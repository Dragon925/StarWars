@file:OptIn(ExperimentalMaterial3Api::class)

package com.github.dragon925.starwars.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.dragon925.starwars.uikit.R
import com.github.dragon925.starwars.uikit.states.SearchFieldState
import com.github.dragon925.starwars.uikit.states.rememberSearchFieldState
import com.github.dragon925.starwars.uikit.theme.StarWarsTheme

@Composable
fun SearchField(
    state: SearchFieldState,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = inputFieldColors(),
    interactionSource: MutableInteractionSource? = null,
) {
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }

    val focused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val textColor = LocalTextStyle.current.color.takeOrElse {
        colors.textColor(enabled, isError = false, focused = focused)
    }

    val trailingIcon = @Composable {
        IconButton(onClick = state::clearQuery) {
            Icon(
                Icons.Filled.Clear,
                contentDescription = stringResource(R.string.clear_description),
                tint = StarWarsTheme.colorScheme.onSurface
            )
        }
    }

    val searchDescription = stringResource(R.string.search_description)
    val expandedSearchDescription = stringResource(R.string.expanded_search_description)

    BasicTextField(
        state = state.textState,
        modifier = modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 56.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { if (it.isFocused) state.expand() }
            .clickable { focusRequester.requestFocus() }
            .semantics {
                contentDescription = searchDescription
                if (state.isExpanded) {
                    stateDescription = expandedSearchDescription
                }
                onClick {
                    focusRequester.requestFocus()
                    true
                }
            },
        enabled = enabled,
        lineLimits = TextFieldLineLimits.SingleLine,
        textStyle = StarWarsTheme.typography.bodyLarge.copy(color = textColor),
        cursorBrush = SolidColor(colors.cursorColor(isError = false)),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        onKeyboardAction = { performedAction ->
            onSearch(state.query)
            state.minimize()
            focusManager.clearFocus()
            performedAction()
        },
        interactionSource = interactionSource,
        decorator = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = state.query,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                placeholder = placeholder,
                trailingIcon = if (state.hasQuery) trailingIcon else null,
                shape = SearchBarDefaults.inputFieldShape,
                colors = colors,
                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(top = 8.dp, bottom = 8.dp),
                container = {
                    Box(Modifier.background(StarWarsTheme.colorScheme.surfaceVariant))
                }
            )
        }
    )
}

@Stable
internal fun TextFieldColors.textColor(
    enabled: Boolean,
    isError: Boolean,
    focused: Boolean,
): Color = when {
    !enabled -> disabledTextColor
    isError -> errorTextColor
    focused -> focusedTextColor
    else -> unfocusedTextColor
}

@Stable
internal fun TextFieldColors.cursorColor(isError: Boolean): Color =
    if (isError) errorCursorColor else cursorColor

@Preview
@Composable
private fun SearchFieldPreview() {
    StarWarsTheme {
        Box(modifier = Modifier
            .background(Color.White)
            .padding(16.dp)) {
            SearchField(
                state = rememberSearchFieldState(),
                onSearch = {},
                placeholder = { Text("Search") }
            )
        }
    }
}