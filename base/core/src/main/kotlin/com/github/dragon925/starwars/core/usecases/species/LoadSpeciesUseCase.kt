package com.github.dragon925.starwars.core.usecases.species

import com.github.dragon925.starwars.core.models.Species
import com.github.dragon925.starwars.core.repository.PageResult
import com.github.dragon925.starwars.core.utils.SpeciesRepository
import kotlinx.coroutines.flow.Flow

class LoadSpeciesUseCase(
    val speciesRepository: SpeciesRepository
) {

    operator fun invoke(page: Int = 1): Flow<PageResult<Species>> = speciesRepository.load(page)
}