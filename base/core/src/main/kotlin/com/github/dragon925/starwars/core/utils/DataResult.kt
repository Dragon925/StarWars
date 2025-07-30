package com.github.dragon925.starwars.core.utils

sealed class DataResult<out D, out E : DataError> {

    data object Loading : DataResult<Nothing, Nothing>()
    data class Success<D>(val data: D) : DataResult<D, Nothing>()
    data class Error<E : DataError>(val error: E) : DataResult<Nothing, E>()
}