package com.github.dragon925.starwars.core.utils

sealed interface DataError {

    enum class Network : DataError {
        NO_CONNECTION, TIMEOUT, NOT_FOUND, UNKNOWN
    }

    enum class Local : DataError {
        NOT_FOUND, UNKNOWN
    }
}