package com.github.dragon925.starwars.core.models

import kotlinx.datetime.LocalDateTime

data class StarshipDetails(
    override val id: Int,
    val name: String,
    val model: String,
    val starshipClass: String,
    val manufacturer: String,
    val costInCredits: String,
    val length: String,
    val crew: String,
    val passengers: String,
    val maxAtmosphericSpeed: String,
    val hyperdriveRating: String,
    val mglt: String,
    val cargoCapacity: String,
    val consumables: String,
    val films: List<Film>,
    val pilots: List<Character>,
    override val created: LocalDateTime,
    override val edited: LocalDateTime
) : DetailedModel<Starship>
