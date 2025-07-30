package com.github.dragon925.starwars.core.models

import kotlinx.datetime.LocalDateTime

data class PlanetDetails(
    override val id: Int,
    val name: String,
    val diameter: String,
    val rotationPeriod: String,
    val orbitalPeriod: String,
    val gravity: String,
    val population: String,
    val climate: String,
    val terrain: String,
    val surfaceWater: String,
    val residents: List<Character>,
    val films: List<Film>,
    override val created: LocalDateTime,
    override val edited: LocalDateTime
) : DetailedModel<Planet>
