package com.github.dragon925.starwars.core.models

import kotlinx.datetime.LocalDateTime

sealed interface Model {
    val id: Int
    val created: LocalDateTime
    val edited: LocalDateTime
}