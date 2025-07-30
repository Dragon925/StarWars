package com.github.dragon925.starwars.core.models

data class Page<T : Model>(
    val current: Int = 1,
    val hasPrev: Boolean = false,
    val hasNext: Boolean = false,
    val results: List<T> = emptyList()
)
