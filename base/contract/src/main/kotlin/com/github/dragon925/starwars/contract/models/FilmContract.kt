package com.github.dragon925.starwars.contract.models

interface FilmContract : ModelContract {

    /**
     *  The title of this film
     */
    val title: String

    /**
     *  The episode number of this film
     */
    val episodeNumber: Int

    /**
     *  The opening paragraphs at the beginning of this film
     */
    val openingCrawl: String

    /**
     *  The name of the director of this film.
     */
    val director: String

    /**
     *  The name(s) of the producer(s) of this film. Comma separated.
     */
    val producer: String

    /**
     *  The ISO 8601 date format of film release at original creator country.
     */
    val releaseDate: String

    /**
     * A list of species resource IDs that are in this film.
     */
    val speciesIds: List<Int>

    /**
     *  A list of starship resource IDs that are in this film.
     */
    val starshipIds: List<Int>

    /**
     *  A list of vehicle resource IDs that are in this film.
     */
    val vehicleIds: List<Int>

    /**
     *  A list of people resource IDs that are in this film.
     */
    val characterIds: List<Int>

    /**
     *  A list of planet resource IDs that are in this film.
     */
    val planetIds: List<Int>

    /**
     *  The hypermedia ID of this resource.
     */
    val id: Int

    /**
     *  The ISO 8601 date format of the time that this resource was created.
     */
    val created: String

    /**
     *  The ISO 8601 date format of the time that this resource was edited.
     */
    val edited: String
}