package com.github.dragon925.starwars.core.usecases.species

import com.github.dragon925.starwars.core.models.Species
import com.github.dragon925.starwars.core.repository.PageResult
import com.github.dragon925.starwars.core.utils.SpeciesRepository
import kotlinx.coroutines.flow.Flow

class SearchSpeciesUseCase(
    val speciesRepository: SpeciesRepository
) {

    operator fun invoke(
        query: String,
        page: Int = 1
    ): Flow<PageResult<Species>> = speciesRepository.search(query, page)
}