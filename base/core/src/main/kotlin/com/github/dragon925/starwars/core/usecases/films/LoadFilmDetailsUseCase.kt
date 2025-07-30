package com.github.dragon925.starwars.core.usecases.films

import com.github.dragon925.starwars.core.models.Film
import com.github.dragon925.starwars.core.utils.CharacterRepository
import com.github.dragon925.starwars.core.utils.DataResult
import com.github.dragon925.starwars.core.utils.DetailedResult
import com.github.dragon925.starwars.core.utils.FilmRepository
import com.github.dragon925.starwars.core.utils.PlanetRepository
import com.github.dragon925.starwars.core.utils.SpeciesRepository
import com.github.dragon925.starwars.core.utils.StarshipRepository
import com.github.dragon925.starwars.core.utils.VehicleRepository
import com.github.dragon925.starwars.core.utils.anyOf
import com.github.dragon925.starwars.core.utils.firstOf
import com.github.dragon925.starwars.core.utils.flatMapResult
import com.github.dragon925.starwars.core.utils.getIfSuccess
import com.github.dragon925.starwars.core.utils.toDetailedFilm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class LoadFilmDetailsUseCase(
    val filmRepository: FilmRepository,
    val characterRepository: CharacterRepository,
    val speciesRepository: SpeciesRepository,
    val planetRepository: PlanetRepository,
    val starshipRepository: StarshipRepository,
    val vehicleRepository: VehicleRepository
) {

    operator fun invoke(id: Int): Flow<DetailedResult<Film>> = filmRepository.loadById(id)
        .flatMapResult { film -> combine(
            characterRepository.loadByIds(film.characterIds),
            speciesRepository.loadByIds(film.speciesIds),
            planetRepository.loadByIds(film.planetIds),
            starshipRepository.loadByIds(film.starshipIds),
            vehicleRepository.loadByIds(film.vehicleIds)
            ) { characters, species, planets, starships, vehicles ->
                val isLoading = anyOf(characters, species, planets, starships, vehicles) {
                    it is DataResult.Loading
                }

                val errorResult = firstOf(characters, species, planets, starships, vehicles) {
                    it is DataResult.Error
                } as? DataResult.Error

                return@combine when {
                    isLoading -> DataResult.Loading
                    errorResult != null -> errorResult
                    else -> DataResult.Success(film.toDetailedFilm(
                        characters = characters.getIfSuccess(),
                        species = species.getIfSuccess(),
                        planets = planets.getIfSuccess(),
                        starships = starships.getIfSuccess(),
                        vehicles = vehicles.getIfSuccess()
                    ))
                }
            }
        }
}