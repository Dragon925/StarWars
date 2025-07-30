package com.github.dragon925.starwars.core.usecases.films

import com.github.dragon925.starwars.core.models.Film
import com.github.dragon925.starwars.core.repository.PageResult
import com.github.dragon925.starwars.core.utils.FilmRepository
import kotlinx.coroutines.flow.Flow

class SearchFilmsUseCase(
    val filmRepository: FilmRepository
) {

    operator fun invoke(
        query: String,
        page: Int = 1
    ): Flow<PageResult<Film>> = filmRepository.search(query, page)
}