package com.github.dragon925.starwars.core.usecases.planets

import com.github.dragon925.starwars.core.models.Planet
import com.github.dragon925.starwars.core.repository.PageResult
import com.github.dragon925.starwars.core.utils.PlanetRepository
import kotlinx.coroutines.flow.Flow

class SearchPlanetsUseCase(
    val planetRepository: PlanetRepository
) {

    operator fun invoke(
        query: String,
        page: Int = 1
    ): Flow<PageResult<Planet>> = planetRepository.search(query, page)
}