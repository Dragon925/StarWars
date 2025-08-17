package com.github.dragon925.starwars.uikit.models

import androidx.compose.runtime.Immutable

@Immutable
sealed class ItemType {

    @Immutable
    data class Characters(val count: Int) : ItemType()

    @Immutable
    data class Species(val count: Int) : ItemType()

    @Immutable
    data class Films(val count: Int) : ItemType()

    @Immutable
    data class Planets(val count: Int) : ItemType()

    @Immutable
    data class Starships(val count: Int) : ItemType()

    @Immutable
    data class Vehicles(val count: Int) : ItemType()

    @Immutable
    data class Homeworld(val id: Int) : ItemType()
}
