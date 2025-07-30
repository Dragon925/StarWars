package com.github.dragon925.starwars.core.usecases.planets

import com.github.dragon925.starwars.core.models.Planet
import com.github.dragon925.starwars.core.utils.CharacterRepository
import com.github.dragon925.starwars.core.utils.DataResult
import com.github.dragon925.starwars.core.utils.DetailedResult
import com.github.dragon925.starwars.core.utils.FilmRepository
import com.github.dragon925.starwars.core.utils.PlanetRepository
import com.github.dragon925.starwars.core.utils.anyOf
import com.github.dragon925.starwars.core.utils.firstOf
import com.github.dragon925.starwars.core.utils.flatMapResult
import com.github.dragon925.starwars.core.utils.getIfSuccess
import com.github.dragon925.starwars.core.utils.toDetailedPlanet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class LoadPlanetDetailsUseCase(
    val planetRepository: PlanetRepository,
    val filmRepository: FilmRepository,
    val characterRepository: CharacterRepository
) {

    operator fun invoke(id: Int): Flow<DetailedResult<Planet>> = planetRepository.loadById(id)
        .flatMapResult { planet -> combine(
            characterRepository.loadByIds(planet.residentIds),
            filmRepository.loadByIds(planet.filmIds),
            ) { characters, films ->
                val isLoading = anyOf(characters, films) {
                    it is DataResult.Loading
                }

                val errorResult = firstOf(characters, films) {
                    it is DataResult.Error
                } as? DataResult.Error

                return@combine when {
                    isLoading -> DataResult.Loading
                    errorResult != null -> errorResult
                    else -> DataResult.Success(planet.toDetailedPlanet(
                        characters = characters.getIfSuccess(),
                        films = films.getIfSuccess()
                    ))
                }
            }
        }
}