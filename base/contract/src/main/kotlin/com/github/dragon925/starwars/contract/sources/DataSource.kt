package com.github.dragon925.starwars.contract.sources

import com.github.dragon925.starwars.contract.models.ModelContract
import com.github.dragon925.starwars.contract.models.PageContract
import kotlinx.coroutines.flow.Flow

sealed interface DataSource<T: ModelContract> {

    fun load(page: Int = 1): Flow<PageContract<T>>

    fun loadById(id: Int): Flow<T?>

    fun search(query: String, page: Int = 1): Flow<PageContract<T>>

    interface Remote<T: ModelContract> : DataSource<T>

    interface Local<T: ModelContract> : DataSource<T> {
        suspend fun save(vararg model: T)
    }
}