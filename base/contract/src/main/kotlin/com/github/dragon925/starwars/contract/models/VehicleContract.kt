package com.github.dragon925.starwars.contract.models

interface VehicleContract : ModelContract {

    /**
     *  The name of this vehicle. The common name, such as "Sand Crawler" or "Speeder bike".
     */
    val name: String

    /**
     *  The model or official name of this vehicle. Such as "All-Terrain Attack Transport".
     */
    val model: String

    /**
     *  The class of this vehicle, such as "Wheeled" or "Repulsorcraft".
     */
    val vehicleClass: String

    /**
     *  The manufacturer of this vehicle. Comma separated if more than one.
     */
    val manufacturer: String

    /**
     *  The length of this vehicle in meters.
     */
    val length: String

    /**
     *  The cost of this vehicle new, in Galactic Credits.
     */
    val costInCredits: String

    /**
     *  The number of personnel needed to run or pilot this vehicle.
     */
    val crew: String

    /**
     *  The number of non-essential people this vehicle can transport.
     */
    val passengers: String

    /**
     *  The maximum speed of this vehicle in the atmosphere.
     */
    val maxAtmosphericSpeed: String

    /**
     *  The maximum number of kilograms that this vehicle can transport.
     */
    val cargoCapacity: String

    /**
     *  The maximum length of time that this vehicle can provide consumables for its entire crew without having to resupply.
     */
    val consumables: String

    /**
     *  A list of Starship ID Resources that this vehicle has piloted.
     */
    val filmIds: List<Int>

    /**
     *  A list of People ID Resources that this vehicle has been piloted by.
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