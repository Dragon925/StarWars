package com.github.dragon925.starwars.core.models

import kotlinx.datetime.LocalDateTime

data class Vehicle(
    override val id: Int,
    val name: String,
    val model: String,
    val vehicleClass: String,
    val manufacturer: String,
    val length: String,
    val costInCredits: String,
    val crew: String,
    val passengers: String,
    val maxAtmosphericSpeed: String,
    val cargoCapacity: String,
    val consumables: String,
    val filmIds: List<Int>,
    val pilotIds: List<Int>,
    override val created: LocalDateTime,
    override val edited: LocalDateTime
) : Model
