package com.github.dragon925.starwars.contract.models

interface StarshipContract : ModelContract {

    /**
     *  The name of this starship. The common name, such as "Death Star".
     */
    val name: String

    /**
     *  The model or official name of this starship. Such as "T-65 X-wing" or "DS-1 Orbital Battle Station".
     */
    val model: String

    /**
     *  The class of this starship, such as "Starfighter" or "Deep Space Mobile Battlestation".
     */
    val starshipClass: String

    /**
     *  The manufacturer of this starship. Comma separated if more than one.
     */
    val manufacturer: String

    /**
     *  The cost of this starship new, in Galactic Credits.
     */
    val costInCredits: String

    /**
     *  The length of this starship in meters.
     */
    val length: String

    /**
     *  The number of personnel needed to run or pilot this starship.
     */
    val crew: String

    /**
     *  The number of non-essential people this starship can transport.
     */
    val passengers: String

    /**
     *  The maximum speed of this starship in the atmosphere. "N/A" if this starship is incapable of atmospheric flight.
     */
    val maxAtmosphericSpeed: String

    /**
     *  The class of this starships hyperdrive.
     */
    val hyperdriveRating: String

    /**
     *  The Maximum number of Megalights this starship can travel in a standard hour. 
     *  A "Megalight" is a standard unit of distance and has never been defined before within the Star Wars universe. 
     *  This figure is only really useful for measuring the difference in speed of starships. 
     *  We can assume it is similar to AU, the distance between our Sun (Sol) and Earth.
     */
    val mglt: String

    /**
     *  The maximum number of kilograms that this starship can transport.
     */
    val cargoCapacity: String

    /**
     *  The maximum length of time that this starship can provide consumables for its entire crew without having to resupply.
     */
    val consumables: String

    /**
     *  A list of Film ID Resources that this starship has appeared in.
     */
    val filmIds: List<Int>

    /**
     *  A list of People ID Resources that this starship has been piloted by.
     */
    val pilotIds: List<Int>

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