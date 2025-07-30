package com.github.dragon925.starwars.core.models

import kotlinx.datetime.LocalDateTime

data class CharacterDetails(
    override val id: Int,
    val name: String,
    val birthYear: String,
    val eyeColor: String,
    val gender: String,
    val hairColor: String,
    val height: String,
    val mass: String,
    val skinColor: String,
    val homeworld: Planet?,
    val films: List<Film>,
    val species: List<Species>,
    val starships: List<Starship>,
    val vehicles: List<Vehicle>,
    override val created: LocalDateTime,
    override val edited: LocalDateTime
) : DetailedModel<Character>
