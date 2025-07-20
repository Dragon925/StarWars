package com.github.dragon925.starwars.contract.models

interface SpeciesContract : ModelContract {

    /**
     *  The name of this species.
     */
    val name: String

    /**
     *  The classification of this species, such as "mammal" or "reptile".
     */
    val classification: String

    /**
     *  The designation of this species, such as "sentient".
     */
    val designation: String

    /**
     *  The average height of this species in centimeters.
     */
    val averageHeight: String

    /**
     *  The average lifespan of this species in years.
     */
    val averageLifespan: String

    /**
     *  A comma-separated string of common eye colors for this species, "none" if this species does not typically have eyes.
     */
    val eyeColors: String

    /**
     *  A comma-separated string of common hair colors for this species, "none" if this species does not typically have hair.
     */
    val hairColors: String

    /**
     *  A comma-separated string of common skin colors for this species, "none" if this species does not typically have skin.
     */
    val skinColors: String

    /**
     *  The language commonly spoken by this species.
     */
    val language: String

    /**
     *  The ID of a planet resource, a planet that this species originates from.
     */
    val homeworldId: Int?

    /**
     *  A list of People ID Resources that are a part of this species.
     */
    val peopleIds: List<Int>

    /**
     *  A list of Film ID Resources that this species has appeared in.
     */
    val filmIds: List<Int>

    /**
     *  The hypermedia ID of this resource.
     */
    val id: String

    /**
     *  The ISO 8601 date format of the time that this resource was created.
     */
    val created: String

    /**
     *  The ISO 8601 date format of the time that this resource was edited.
     */
    val edited: String
}