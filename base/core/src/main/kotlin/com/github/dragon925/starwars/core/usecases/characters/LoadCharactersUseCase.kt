package com.github.dragon925.starwars.core.usecases.characters

import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.repository.PageResult
import com.github.dragon925.starwars.core.utils.CharacterRepository
import kotlinx.coroutines.flow.Flow

class LoadCharactersUseCase(
    val repository: CharacterRepository
) {

    operator fun invoke(page: Int = 1): Flow<PageResult<Character>> = repository.load(page)
}