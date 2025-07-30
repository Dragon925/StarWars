package com.github.dragon925.starwars.core.utils

import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.models.DetailedModel
import com.github.dragon925.starwars.core.models.Film
import com.github.dragon925.starwars.core.models.Planet
import com.github.dragon925.starwars.core.models.Species
import com.github.dragon925.starwars.core.models.Starship
import com.github.dragon925.starwars.core.models.Vehicle
import com.github.dragon925.starwars.core.repository.ModelRepository


typealias DetailedResult<M> = DataResult<DetailedModel<M>, DataError>

typealias CharacterRepository = ModelRepository<Character>
typealias FilmRepository = ModelRepository<Film>
typealias PlanetRepository = ModelRepository<Planet>
typealias SpeciesRepository = ModelRepository<Species>
typealias StarshipRepository = ModelRepository<Starship>
typealias VehicleRepository = ModelRepository<Vehicle>

inline fun <T> anyOf(vararg values: T, predicate: (T) -> Boolean): Boolean = values.any(predicate)

inline fun <T> firstOf(vararg values: T, predicate: (T) -> Boolean): T? = values.firstOrNull(predicate)