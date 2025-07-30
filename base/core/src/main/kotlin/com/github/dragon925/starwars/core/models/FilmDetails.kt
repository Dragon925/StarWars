package com.github.dragon925.starwars.core.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class FilmDetails(
    override val id: Int,
    val title: String,
    val episodeNumber: Int,
    val openingCrawl: String,
    val director: String,
    val producer: String,
    val releaseDate: LocalDate,
    val species: List<Species>,
    val starships: List<Starship>,
    val vehicles: List<Vehicle>,
    val characters: List<Character>,
    val planets: List<Planet>,
    override val created: LocalDateTime,
    override val edited: LocalDateTime
) : DetailedModel<Film>
