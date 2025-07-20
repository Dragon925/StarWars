package com.github.dragon925.starwars.contract.models

interface PeopleContract : ModelContract {

    /**
     * The name of this person.
     */
    val name: String

    /**
     *  The birth year of the person, using the in-universe standard of BBY or ABY:
     *  - BBY – Before the Battle of Yavin
     *  - ABY – After the Battle of Yavin.
     *  The Battle of Yavin is a battle that occurs at the end of Star Wars episode IV: A New Hope.
     */
    val birthYear: String
    /**
     *  The eye color of this person. Will be "unknown" if not known or "n/a" if the person does not have an eye.
     */
    val eyeColor: String

    /**
     *  The gender of this person. Either "Male", "Female" or "unknown", "n/a" if the person does not have a gender.
     */
    val gender: String

    /**
     *  The hair color of this person. Will be "unknown" if not known or "n/a" if the person does not have hair.
     */
    val hairColor: String

    /**
     *  The height of the person in centimeters.
     */
    val height: String

    /**
     *  The mass of the person in kilograms.
     */
    val mass: String

    /**
     *  The skin color of this person.
     */
    val skinColor: String

    /**
     *  The ID of a planet resource, a planet that this person was born on or inhabits.
     */
    val homeworldId: Int

    /**
     *  A list of film resource IDs that this person has been in.
     */
    val filmIds: List<Int>

    /**
     *  A list of species resource IDs that this person belongs to.
     */
    val speciesIds: List<Int>

    /**
     *  A list of starship resource IDs that this person has piloted.
     */
    val starshipIds: List<Int>

    /**
     *  A list of vehicle resource IDs that this person has piloted.
     */
    val vehicleIds: List<Int>

    /**
     *  The hypermedia id of this resource.
     */
    val id: Int

    /**
     *  The ISO 8601 date format of the time that this resource was created.
     */
    val created: String

    /**
     * the ISO 8601 date format of the time that this resource was edited.
     */
    val edited: String
}