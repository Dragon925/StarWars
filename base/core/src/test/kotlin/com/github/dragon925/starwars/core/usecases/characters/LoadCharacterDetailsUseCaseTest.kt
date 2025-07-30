package com.github.dragon925.starwars.core.usecases.characters

import app.cash.turbine.test
import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.models.CharacterDetails
import com.github.dragon925.starwars.core.models.Film
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoadCharacterDetailsUseCaseTest {

    private lateinit var characterRepository: ModelRepository<Character>
    private lateinit var filmRepository: ModelRepository<Film>
    private lateinit var speciesRepository: ModelRepository<Species>
    private lateinit var planetRepository: ModelRepository<Planet>
    private lateinit var starshipRepository: ModelRepository<Starship>
    private lateinit var vehicleRepository: ModelRepository<Vehicle>
    private lateinit var useCase: LoadCharacterDetailsUseCase

    private val baseCharacter = createEmptyCharacter().copy(
        id = 1,
        name = "Luke Skywalker",
        birthYear = "19BBY",
        eyeColor = "blue",
        gender = "male",
        hairColor = "blond",
        height = "172",
        mass = "77",
        skinColor = "fair",
        homeworldId = 1,
        filmIds = listOf(1, 2),
        speciesIds = listOf(1),
        starshipIds = listOf(1, 2),
        vehicleIds = listOf(1)
    )
    private val films = listOf(
        createEmptyFilm().copy(id = 1, title = "A New Hope", episodeNumber = 4),
        createEmptyFilm().copy(id = 2, title = "The Empire Strikes Back", episodeNumber = 5),
    )

    private val species = listOf(
        createEmptySpecies().copy(id = 1, name = "Human")
    )

    private val homeworld = createEmptyPlanet().copy(id = 1, name = "Tatooine")

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

        useCase = LoadCharacterDetailsUseCase(
            characterRepository,
            filmRepository,
            speciesRepository,
            planetRepository,
            starshipRepository,
            vehicleRepository
        )
    }

    @Test
    fun `test successful character details loading`() = runTest {
        // set up test mocks
        every { characterRepository.loadById(1) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseCharacter)
        )
        every { filmRepository.loadByIds(baseCharacter.filmIds) } returns flowOf(
            DataResult.Success(films)
        )
        every { speciesRepository.loadByIds(baseCharacter.speciesIds) } returns flowOf(
            DataResult.Success(species)
        )
        every { planetRepository.loadById(baseCharacter.homeworldId) } returns flowOf(
            DataResult.Success(homeworld)
        )
        every { starshipRepository.loadByIds(baseCharacter.starshipIds) } returns flowOf(
            DataResult.Success(starships)
        )
        every { vehicleRepository.loadByIds(baseCharacter.vehicleIds) } returns flowOf(
            DataResult.Success(vehicles)
        )

        useCase(baseCharacter.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Success)
            val details = (result as DataResult.Success).data

            assertTrue(details is CharacterDetails)
            val characterDetails = details as CharacterDetails

            assertEquals(baseCharacter.name, characterDetails.name)
            assertEquals(films.size, characterDetails.films.size)
            assertEquals(species.size, characterDetails.species.size)
            assertEquals(homeworld.name, characterDetails.homeworld?.name)
            assertEquals(starships.size, characterDetails.starships.size)
            assertEquals(vehicles.size, characterDetails.vehicles.size)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test loading state during data fetching`() = runTest {
        every { characterRepository.loadById(1) } returns flowOf(DataResult.Loading)

        useCase(baseCharacter.id).test {
            assertTrue(awaitItem() is DataResult.Loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading character`() = runTest {
        val error = DataError.Network.NOT_FOUND

        every { characterRepository.loadById(1) } returns flowOf(
            DataResult.Loading,
            DataResult.Error(error)
        )

        useCase(baseCharacter.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading films`() = runTest {
        val error = DataError.Network.TIMEOUT

        every { characterRepository.loadById(1) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseCharacter)
        )
        every { filmRepository.loadByIds(baseCharacter.filmIds) } returns flowOf(
            DataResult.Error(error)
        )
        every { speciesRepository.loadByIds(baseCharacter.speciesIds) } returns flowOf(
            DataResult.Success(species)
        )
        every { planetRepository.loadById(baseCharacter.homeworldId) } returns flowOf(
            DataResult.Success(homeworld)
        )
        every { starshipRepository.loadByIds(baseCharacter.starshipIds) } returns flowOf(
            DataResult.Success(starships)
        )
        every { vehicleRepository.loadByIds(baseCharacter.vehicleIds) } returns flowOf(
            DataResult.Success(vehicles)
        )

        useCase(baseCharacter.id).test {
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

        every { characterRepository.loadById(1) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseCharacter)
        )
        every { filmRepository.loadByIds(baseCharacter.filmIds) } returns flowOf(
            DataResult.Success(films)
        )
        every { speciesRepository.loadByIds(baseCharacter.speciesIds) } returns flowOf(
            DataResult.Error(error)
        )
        every { planetRepository.loadById(baseCharacter.homeworldId) } returns flowOf(
            DataResult.Success(homeworld)
        )
        every { starshipRepository.loadByIds(baseCharacter.starshipIds) } returns flowOf(
            DataResult.Success(starships)
        )
        every { vehicleRepository.loadByIds(baseCharacter.vehicleIds) } returns flowOf(
            DataResult.Success(vehicles)
        )

        useCase(baseCharacter.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }
}