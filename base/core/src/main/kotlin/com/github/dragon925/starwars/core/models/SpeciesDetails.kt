package com.github.dragon925.starwars.core.models

import kotlinx.datetime.LocalDateTime

data class SpeciesDetails(
    override val id: Int,
    val name: String,
    val classification: String,
    val designation: String,
    val averageHeight: Int,
    val averageLifespan: Int,
    val eyeColors: String,
    val hairColors: String,
    val skinColors: String,
    val language: String,
    val homeworld: Planet?,
    val peoples: List<Character>,
    val films: List<Film>,
    override val created: LocalDateTime,
    override val edited: LocalDateTime
) : DetailedModel<Species>
