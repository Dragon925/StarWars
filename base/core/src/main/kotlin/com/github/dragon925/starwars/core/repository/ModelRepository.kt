package com.github.dragon925.starwars.core.repository

import com.github.dragon925.starwars.core.models.Model
import com.github.dragon925.starwars.core.models.Page
import com.github.dragon925.starwars.core.utils.DataError
import com.github.dragon925.starwars.core.utils.DataResult
import kotlinx.coroutines.flow.Flow

typealias PageResult<M> = DataResult<Page<M>, DataError>
typealias ModelResult<M> = DataResult<M, DataError>
typealias ModelListResult<M> = DataResult<List<M>, DataError>

interface ModelRepository<M : Model> {

    fun load(page: Int = 1): Flow<PageResult<M>>

    fun loadById(id: Int): Flow<ModelResult<M>>

    fun loadByIds(ids: List<Int>): Flow<ModelListResult<M>>

    fun search(query: String, page: Int = 1): Flow<PageResult<M>>
}