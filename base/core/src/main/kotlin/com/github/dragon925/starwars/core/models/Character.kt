package com.github.dragon925.starwars.core.models

import kotlinx.datetime.LocalDateTime

data class Character(
    override val id: Int,
    val name: String,
    val birthYear: String,
    val eyeColor: String,
    val gender: String,
    val hairColor: String,
    val height: String,
    val mass: String,
    val skinColor: String,
    val homeworldId: Int,
    val filmIds: List<Int>,
    val speciesIds: List<Int>,
    val starshipIds: List<Int>,
    val vehicleIds: List<Int>,
    override val created: LocalDateTime,
    override val edited: LocalDateTime
) : Model
