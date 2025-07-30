package com.github.dragon925.starwars.core.usecases.species

import com.github.dragon925.starwars.core.models.Species
import com.github.dragon925.starwars.core.utils.CharacterRepository
import com.github.dragon925.starwars.core.utils.DataResult
import com.github.dragon925.starwars.core.utils.DetailedResult
import com.github.dragon925.starwars.core.utils.FilmRepository
import com.github.dragon925.starwars.core.utils.PlanetRepository
import com.github.dragon925.starwars.core.utils.SpeciesRepository
import com.github.dragon925.starwars.core.utils.anyOf
import com.github.dragon925.starwars.core.utils.firstOf
import com.github.dragon925.starwars.core.utils.flatMapResult
import com.github.dragon925.starwars.core.utils.getIfSuccess
import com.github.dragon925.starwars.core.utils.toDetailedSpecies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

class LoadSpeciesDetailsUseCase(
    val speciesRepository: SpeciesRepository,
    val filmRepository: FilmRepository,
    val characterRepository: CharacterRepository,
    val planetRepository: PlanetRepository
) {

    operator fun invoke(id: Int): Flow<DetailedResult<Species>> = speciesRepository.loadById(id)
        .flatMapResult { species -> combine(
            characterRepository.loadByIds(species.peopleIds),
            filmRepository.loadByIds(species.filmIds),
            species.homeworldId?.let {
                planetRepository.loadById(it)
            } ?: flowOf(DataResult.Success(null))
            ) { characters, films, homeworld ->
                val isLoading = anyOf(characters, species, homeworld) {
                    it is DataResult.Loading
                }

                val errorResult = firstOf(characters, films, homeworld) {
                    it is DataResult.Error
                } as? DataResult.Error

                return@combine when {
                    isLoading -> DataResult.Loading
                    errorResult != null -> errorResult
                    else -> DataResult.Success(species.toDetailedSpecies(
                        homeworld = homeworld.getIfSuccess(),
                        characters = characters.getIfSuccess(),
                        films = films.getIfSuccess()
                    ))
                }
            }
        }
}