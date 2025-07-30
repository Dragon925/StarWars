package com.github.dragon925.starwars.core.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class Film(
    override val id: Int,
    val title: String,
    val episodeNumber: Int,
    val openingCrawl: String,
    val director: String,
    val producer: String,
    val releaseDate: LocalDate,
    val speciesIds: List<Int>,
    val starshipIds: List<Int>,
    val vehicleIds: List<Int>,
    val characterIds: List<Int>,
    val planetIds: List<Int>,
    override val created: LocalDateTime,
    override val edited: LocalDateTime
) : Model
