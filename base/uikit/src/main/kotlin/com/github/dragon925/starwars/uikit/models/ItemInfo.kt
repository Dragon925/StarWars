package com.github.dragon925.starwars.uikit.models

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ItemInfo(
    val id: Int,
    val title: String,
    val headline: String? = null,
    val text: String? = null,
    val items: ImmutableList<ItemType> = persistentListOf()
) : UIModel
