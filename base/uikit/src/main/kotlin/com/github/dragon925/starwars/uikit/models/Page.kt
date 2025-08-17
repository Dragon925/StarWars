package com.github.dragon925.starwars.uikit.models

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class Page<M: UIModel>(
    val current: Int = 1,
    val hasPrev: Boolean = false,
    val hasNext: Boolean = false,
    val items: ImmutableList<M> = persistentListOf()
) : UIModel
