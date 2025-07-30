package com.github.dragon925.starwars.core.utils

import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.models.CharacterDetails
import com.github.dragon925.starwars.core.models.Film
import com.github.dragon925.starwars.core.models.FilmDetails
import com.github.dragon925.starwars.core.models.Planet
import com.github.dragon925.starwars.core.models.PlanetDetails
import com.github.dragon925.starwars.core.models.Species
import com.github.dragon925.starwars.core.models.SpeciesDetails
import com.github.dragon925.starwars.core.models.Starship
import com.github.dragon925.starwars.core.models.StarshipDetails
import com.github.dragon925.starwars.core.models.Vehicle
import com.github.dragon925.starwars.core.models.VehicleDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.transform

inline fun <reified M, reified E : DataError, R> Flow<DataResult<M, E>>.mapResult(
    crossinline mapper: suspend (M) -> R
): Flow<DataResult<R, E>> = transform {
    return@transform when (it) {
        is DataResult.Error<E> -> emit(DataResult.Error(it.error))
        DataResult.Loading -> emit(DataResult.Loading)
        is DataResult.Success<M> -> emit(DataResult.Success(mapper(it.data)))
    }
}

inline fun <reified M, reified E: DataError, R> Flow<DataResult<M, E>>.flatMapResult(
    crossinline mapper: suspend (M) -> Flow<DataResult<R, E>>
): Flow<DataResult<R, E>> = transform {
    return@transform when (it) {
        is DataResult.Error<E> -> emit(DataResult.Error(it.error))
        DataResult.Loading -> emit(DataResult.Loading)
        is DataResult.Success<M> -> emitAll(mapper(it.data))
    }
}

fun Character.toDetailedCharacter(
    homeworld: Planet? = null,
    films: List<Film> = emptyList(),
    species: List<Species> = emptyList(),
    starships: List<Starship> = emptyList(),
    vehicles: List<Vehicle> = emptyList()
) = CharacterDetails(
    id = id,
    name = name,
    birthYear = birthYear,
    eyeColor = eyeColor,
    gender = gender,
    hairColor = hairColor,
    height = height,
    mass = mass,
    skinColor = skinColor,
    created = created,
    edited = edited,
    homeworld = homeworld,
    films = films,
    species = species,
    starships = starships,
    vehicles = vehicles
)

fun Film.toDetailedFilm(
    characters: List<Character> = emptyList(),
    species: List<Species> = emptyList(),
    planets: List<Planet> = emptyList(),
    starships: List<Starship> = emptyList(),
    vehicles: List<Vehicle> = emptyList()
) = FilmDetails(
    id = id,
    title = title,
    episodeNumber = episodeNumber,
    openingCrawl = openingCrawl,
    director = director,
    producer = producer,
    releaseDate = releaseDate,
    created = created,
    edited = edited,
    characters = characters,
    species = species,
    planets = planets,
    starships = starships,
    vehicles = vehicles
)

fun Planet.toDetailedPlanet(
    characters: List<Character> = emptyList(),
    films: List<Film> = emptyList()
) = PlanetDetails(
    id = id,
    name = name,
    diameter = diameter,
    rotationPeriod = rotationPeriod,
    orbitalPeriod = orbitalPeriod,
    gravity = gravity,
    population = population,
    climate = climate,
    terrain = terrain,
    surfaceWater = surfaceWater,
    created = created,
    edited = edited,
    residents = characters,
    films = films
)

fun Species.toDetailedSpecies(
    homeworld: Planet? = null,
    characters: List<Character> = emptyList(),
    films: List<Film> = emptyList()
) = SpeciesDetails(
    id = id,
    name = name,
    classification = classification,
    designation = designation,
    averageHeight = averageHeight,
    averageLifespan = averageLifespan,
    eyeColors = eyeColors,
    hairColors = hairColors,
    skinColors = skinColors,
    language = language,
    created = created,
    edited = edited,
    homeworld = homeworld,
    peoples = characters,
    films = films
)

fun Starship.toDetailedStarship(
    films: List<Film> = emptyList(),
    characters: List<Character> = emptyList()
) = StarshipDetails(
    id = id,
    name = name,
    model = model,
    starshipClass = starshipClass,
    manufacturer = manufacturer,
    costInCredits = costInCredits,
    length = length,
    crew = crew,
    passengers = passengers,
    maxAtmosphericSpeed = maxAtmosphericSpeed,
    hyperdriveRating = hyperdriveRating,
    mglt = mglt,
    cargoCapacity = cargoCapacity,
    consumables = consumables,
    created = created,
    edited = edited,
    films = films,
    pilots = characters
)

fun Vehicle.toDetailedVehicle(
    films: List<Film> = emptyList(),
    characters: List<Character> = emptyList()
) = VehicleDetails(
    id = id,
    name = name,
    model = model,
    vehicleClass = vehicleClass,
    manufacturer = manufacturer,
    costInCredits = costInCredits,
    length = length,
    crew = crew,
    passengers = passengers,
    maxAtmosphericSpeed = maxAtmosphericSpeed,
    cargoCapacity = cargoCapacity,
    consumables = consumables,
    created = created,
    edited = edited,
    films = films,
    pilots = characters
)

fun <M, E: DataError> DataResult<M, E>.getIfSuccess(
    default: M? = null
): M? = if (this is DataResult.Success) data else default

fun <M, E: DataError> DataResult<List<M>, E>.getIfSuccess(
    default: List<M> = emptyList()
): List<M> = if (this is DataResult.Success) data else default