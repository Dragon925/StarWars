package com.github.dragon925.starwars.core.usecases.starships

import com.github.dragon925.starwars.core.models.Starship
import com.github.dragon925.starwars.core.utils.CharacterRepository
import com.github.dragon925.starwars.core.utils.DataResult
import com.github.dragon925.starwars.core.utils.DetailedResult
import com.github.dragon925.starwars.core.utils.FilmRepository
import com.github.dragon925.starwars.core.utils.StarshipRepository
import com.github.dragon925.starwars.core.utils.anyOf
import com.github.dragon925.starwars.core.utils.firstOf
import com.github.dragon925.starwars.core.utils.flatMapResult
import com.github.dragon925.starwars.core.utils.getIfSuccess
import com.github.dragon925.starwars.core.utils.toDetailedStarship
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class LoadStarshipDetailsUseCase(
    val starshipRepository: StarshipRepository,
    val filmRepository: FilmRepository,
    val characterRepository: CharacterRepository
) {

    operator fun invoke(id: Int): Flow<DetailedResult<Starship>> = starshipRepository.loadById(id)
        .flatMapResult { starship -> combine(
            characterRepository.loadByIds(starship.pilotIds),
            filmRepository.loadByIds(starship.filmIds),
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
                    else -> DataResult.Success(starship.toDetailedStarship(
                        characters = characters.getIfSuccess(),
                        films = films.getIfSuccess()
                    ))
                }
            }
        }
}