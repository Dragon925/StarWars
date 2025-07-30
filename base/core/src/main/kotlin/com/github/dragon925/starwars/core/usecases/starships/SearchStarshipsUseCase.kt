package com.github.dragon925.starwars.core.usecases.starships

import com.github.dragon925.starwars.core.models.Starship
import com.github.dragon925.starwars.core.repository.PageResult
import com.github.dragon925.starwars.core.utils.StarshipRepository
import kotlinx.coroutines.flow.Flow

class SearchStarshipsUseCase(
    val starshipRepository: StarshipRepository
) {

    operator fun invoke(
        query: String,
        page: Int = 1
    ): Flow<PageResult<Starship>> = starshipRepository.search(query, page)
}