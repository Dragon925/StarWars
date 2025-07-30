package com.github.dragon925.starwars.core.models

import kotlinx.datetime.LocalDateTime

data class Species(
    override val id: Int,
    val name: String,
    val classification: String,
    val designation: String,
    val averageHeight: Int,
    val averageLifespan: Int,
    val eyeColors: String,
    val hairColors: String,
    val skinColors: String,
    val language: String,
    val homeworldId: Int?,
    val peopleIds: List<Int>,
    val filmIds: List<Int>,
    override val created: LocalDateTime,
    override val edited: LocalDateTime
) : Model
