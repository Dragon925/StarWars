package com.github.dragon925.starwars.core.usecases.films

import app.cash.turbine.test
import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.models.Film
import com.github.dragon925.starwars.core.models.FilmDetails
import com.github.dragon925.starwars.core.models.Planet
import com.github.dragon925.starwars.core.models.Species
import com.github.dragon925.starwars.core.models.Starship
import com.github.dragon925.starwars.core.models.Vehicle
import com.github.dragon925.starwars.core.repository.ModelRepository
import com.github.dragon925.starwars.core.utils.DataError
import com.github.dragon925.starwars.core.utils.DataResult
import com.github.dragon925.starwars.core.utils.createEmptyCharacter
import com.github.dragon925.starwars.core.utils.createEmptyFilm
import com.github.dragon925.starwars.core.utils.createEmptyPlanet
import com.github.dragon925.starwars.core.utils.createEmptySpecies
import com.github.dragon925.starwars.core.utils.createEmptyStarship
import com.github.dragon925.starwars.core.utils.createEmptyVehicle
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoadFilmDetailsUseCaseTest {

    private lateinit var characterRepository: ModelRepository<Character>
    private lateinit var filmRepository: ModelRepository<Film>
    private lateinit var speciesRepository: ModelRepository<Species>
    private lateinit var planetRepository: ModelRepository<Planet>
    private lateinit var starshipRepository: ModelRepository<Starship>
    private lateinit var vehicleRepository: ModelRepository<Vehicle>

    private lateinit var useCase: LoadFilmDetailsUseCase

    private val baseFilm = createEmptyFilm().copy(
        id = 1,
        title = "New Hope",
        episodeNumber = 4,
        speciesIds = listOf(1),
        starshipIds = listOf(1, 2),
        vehicleIds = listOf(1),
        characterIds = listOf(1, 2),
        planetIds = listOf(1)
    )

    private val characters = listOf(
        createEmptyCharacter().copy(id = 1, name = "Luke Skywalker"),
        createEmptyCharacter().copy(id = 2, name = "Obi-Wan Kenobi")
    )

    private val species = listOf(
        createEmptySpecies().copy(id = 1, name = "Human")
    )

    private val planets = listOf(
        createEmptyPlanet().copy(id = 1, name = "Tatooine")
    )

    private val starships = listOf(
        createEmptyStarship().copy(id = 1, name = "X-Wing"),
        createEmptyStarship().copy(id = 2, name = "Y-Wing")
    )

    private val vehicles = listOf(
        createEmptyVehicle().copy(id = 1, name = "Sand Crawler")
    )

    @Before
    fun setUp() {
        characterRepository = mockk(relaxed = true)
        filmRepository = mockk(relaxed = true)
        speciesRepository = mockk(relaxed = true)
        planetRepository = mockk(relaxed = true)
        starshipRepository = mockk(relaxed = true)
        vehicleRepository = mockk(relaxed = true)

        useCase = LoadFilmDetailsUseCase(
            filmRepository,
            characterRepository,
            speciesRepository,
            planetRepository,
            starshipRepository,
            vehicleRepository
        )
    }

    @Test
    fun `test loading state during data fetching`() = runTest {
        every { filmRepository.loadById(baseFilm.id) } returns flowOf(
            DataResult.Loading
        )

        useCase(baseFilm.id).test {
            assertTrue(awaitItem() is DataResult.Loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading film`() = runTest {
        val error = DataError.Network.NOT_FOUND

        every { filmRepository.loadById(baseFilm.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Error(error)
        )

        useCase(baseFilm.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test successful film details loading`() = runTest {
        every { filmRepository.loadById(baseFilm.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseFilm)
        )
        every { characterRepository.loadByIds(baseFilm.characterIds) } returns flowOf(
            DataResult.Success(characters)
        )
        every { speciesRepository.loadByIds(baseFilm.speciesIds) } returns flowOf(
            DataResult.Success(species)
        )
        every { planetRepository.loadByIds(baseFilm.planetIds) } returns flowOf(
            DataResult.Success(planets)
        )
        every { starshipRepository.loadByIds(baseFilm.starshipIds) } returns flowOf(
            DataResult.Success(starships)
        )
        every { vehicleRepository.loadByIds(baseFilm.vehicleIds) } returns flowOf(
            DataResult.Success(vehicles)
        )

        useCase(baseFilm.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Success)

            val details = (result as DataResult.Success).data
            assertTrue(details is FilmDetails)

            val filmDetails = details as FilmDetails

            assertEquals(baseFilm.title, filmDetails.title)
            assertEquals(characters.size, filmDetails.characters.size)
            assertEquals(species.size, filmDetails.species.size)
            assertEquals(planets.size, filmDetails.planets.size)
            assertEquals(starships.size, filmDetails.starships.size)
            assertEquals(vehicles.size, filmDetails.vehicles.size)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading characters`() = runTest {
        val error = DataError.Network.TIMEOUT

        every { filmRepository.loadById(baseFilm.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseFilm)
        )
        every { characterRepository.loadByIds(baseFilm.characterIds) } returns flowOf(
            DataResult.Error(error)
        )
        every { speciesRepository.loadByIds(baseFilm.speciesIds) } returns flowOf(
            DataResult.Success(species)
        )
        every { planetRepository.loadByIds(baseFilm.planetIds) } returns flowOf(
            DataResult.Success(planets)
        )
        every { starshipRepository.loadByIds(baseFilm.starshipIds) } returns flowOf(
            DataResult.Success(starships)
        )
        every { vehicleRepository.loadByIds(baseFilm.vehicleIds) } returns flowOf(
            DataResult.Success(vehicles)
        )

        useCase(baseFilm.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading species`() = runTest {
        val error = DataError.Network.UNKNOWN

        every { filmRepository.loadById(baseFilm.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseFilm)
        )
        every { characterRepository.loadByIds(baseFilm.characterIds) } returns flowOf(
            DataResult.Success(characters)
        )
        every { speciesRepository.loadByIds(baseFilm.speciesIds) } returns flowOf(
            DataResult.Error(error)
        )
        every { planetRepository.loadByIds(baseFilm.planetIds) } returns flowOf(
            DataResult.Success(planets)
        )
        every { starshipRepository.loadByIds(baseFilm.starshipIds) } returns flowOf(
            DataResult.Success(starships)
        )
        every { vehicleRepository.loadByIds(baseFilm.vehicleIds) } returns flowOf(
            DataResult.Success(vehicles)
        )

        useCase(baseFilm.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }
}