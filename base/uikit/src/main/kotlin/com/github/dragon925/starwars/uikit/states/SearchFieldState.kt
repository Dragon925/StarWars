package com.github.dragon925.starwars.uikit.states

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable

@Stable
class SearchFieldState internal constructor(
    textFieldState: TextFieldState,
    expanded: Boolean
) {

    constructor(
        initialText: String = "",
        initialExpanded: Boolean = false
    ) : this(TextFieldState(initialText), initialExpanded)

    private var expanded: Boolean by mutableStateOf(expanded)

    internal val textState = textFieldState

    val isExpanded: Boolean by derivedStateOf { this.expanded }
    val hasQuery: Boolean by derivedStateOf { textState.text.isNotEmpty() }

    val query: String get() = textState.text.toString()

    fun expand() {
        expanded = true
    }

    fun minimize() {
        expanded = false
    }

    fun clearQuery() {
        textState.clearText()
    }

    companion object {
        object Saver : androidx.compose.runtime.saveable.Saver<SearchFieldState, Any> {

            override fun SaverScope.save(value: SearchFieldState): Any = listOf(
                value.isExpanded,
                with(TextFieldState.Saver) { save(value.textState) }
            )

            override fun restore(value: Any): SearchFieldState {
                val (expanded, savedTextFieldState) = value as List<*>
                return SearchFieldState(
                    with(TextFieldState.Saver) { restore(savedTextFieldState!!) }!!,
                    expanded as Boolean
                )
            }
        }
    }
}

@Composable
fun rememberSearchFieldState(
    initialText: String = "",
    initialExpanded: Boolean = false
): SearchFieldState = rememberSaveable(saver = SearchFieldState.Companion.Saver) {
    SearchFieldState(initialText, initialExpanded)
}
