package com.github.dragon925.starwars.uikit.models

import androidx.compose.runtime.Immutable

@Immutable
data class DataState<Model: UIModel, Error: UIError>(
    val isLoading: Boolean = false,
    val data: Model? = null,
    val error: Error? = null
) {
    val isError get() = error != null
    val isCorrect get() = isLoading || isError || data != null
}
