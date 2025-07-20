package com.github.dragon925.starwars.contract.models

interface PlanetContract : ModelContract {

    /**
     * The name of this planet.
     */
    val name: String

    /**
     * The diameter of this planet in kilometers.
     */
    val diameter: String

    /**
     * The number of standard hours it takes for this planet to complete a single rotation on its axis.
     */
    val rotationPeriod: String

    /**
     * The number of standard days it takes for this planet to complete a single orbit of its local star.
     */
    val orbitalPeriod: String

    /**
     * A number denoting the gravity of this planet, where "1" is normal or 1 standard G. "2" is twice or 2 standard Gs. "0.5" is half or 0.5 standard Gs.
     */
    val gravity: String

    /**
     * The average population of sentient beings inhabiting this planet.
     */
    val population: String

    /**
     * The climate of this planet. Comma separated if diverse.
     */
    val climate: String

    /**
     * The terrain of this planet. Comma separated if diverse.
     */
    val terrain: String

    /**
     * The percentage of the planet surface that is naturally occurring water or bodies of water.
     */
    val surfaceWater: String

    /**
     * A list of People ID Resources that live on this planet.
     */
    val residentIds: List<Int>

    /**
     * A list of Film ID Resources that this planet has appeared in.
     */
    val filmIds: List<Int>

    /**
     * The hypermedia ID of this resource.
     */
    val id: Int

    /**
     * The ISO 8601 date format of the time that this resource was created.
     */
    val created: String

    /**
     * The ISO 8601 date format of the time that this resource was edited.
     */
    val edited: String

}