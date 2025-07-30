package com.github.dragon925.starwars.core.usecases.characters

import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.repository.PageResult
import com.github.dragon925.starwars.core.utils.CharacterRepository
import kotlinx.coroutines.flow.Flow

class SearchCharactersUseCase(
    val characterRepository: CharacterRepository
) {

    operator fun invoke(
        query: String,
        page: Int = 1
    ): Flow<PageResult<Character>> = characterRepository.search(query, page)
}