package com.github.dragon925.starwars.core.utils

import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.models.Film
import com.github.dragon925.starwars.core.models.Planet
import com.github.dragon925.starwars.core.models.Species
import com.github.dragon925.starwars.core.models.Starship
import com.github.dragon925.starwars.core.models.Vehicle
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

val dateTime = LocalDateTime(2023, 1, 1, 0, 0)
val date = LocalDate(2023, 1, 1)


fun createEmptyCharacter() = Character(
    id = 0,
    name = "",
    birthYear = "",
    eyeColor = "",
    gender = "",
    hairColor = "",
    height = "",
    mass = "",
    skinColor = "",
    homeworldId = 0,
    filmIds = emptyList(),
    speciesIds = emptyList(),
    starshipIds = emptyList(),
    vehicleIds = emptyList(),
    created = dateTime,
    edited = dateTime
)

fun createEmptyFilm() = Film(
    id = 0,
    title = "",
    episodeNumber = 0,
    openingCrawl = "",
    director = "",
    producer = "",
    releaseDate = date,
    speciesIds = emptyList(),
    starshipIds = emptyList(),
    vehicleIds = emptyList(),
    characterIds = emptyList(),
    planetIds = emptyList(),
    created = dateTime,
    edited = dateTime
)

fun createEmptySpecies() = Species(
    id = 0,
    name = "",
    classification = "",
    designation = "",
    averageHeight = 0,
    averageLifespan = 0,
    eyeColors = "",
    hairColors = "",
    skinColors = "",
    language = "",
    homeworldId = null,
    peopleIds = emptyList(),
    filmIds = emptyList(),
    created = dateTime,
    edited = dateTime
)

fun createEmptyPlanet() = Planet(
    id = 0,
    name = "",
    diameter = "",
    rotationPeriod = "",
    orbitalPeriod = "",
    gravity = "",
    population = "",
    climate = "",
    terrain = "",
    surfaceWater = "",
    residentIds = emptyList(),
    filmIds = emptyList(),
    created = dateTime,
    edited = dateTime
)

fun createEmptyStarship() = Starship(
    id = 1,
    name = "X-Wing",
    model = "",
    starshipClass = "",
    manufacturer = "",
    costInCredits = "",
    length = "",
    crew = "",
    passengers = "",
    maxAtmosphericSpeed = "",
    hyperdriveRating = "",
    mglt = "",
    cargoCapacity = "",
    consumables = "",
    filmIds = emptyList(),
    pilotIds = emptyList(),
    created = dateTime,
    edited = dateTime
)

fun createEmptyVehicle() = Vehicle(
    id = 1,
    name = "Sand Crawler",
    model = "",
    vehicleClass = "",
    manufacturer = "",
    length = "",
    costInCredits = "",
    crew = "",
    passengers = "",
    maxAtmosphericSpeed = "",
    cargoCapacity = "",
    consumables = "",
    filmIds = emptyList(),
    pilotIds = emptyList(),
    created = dateTime,
    edited = dateTime
)