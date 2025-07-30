package com.github.dragon925.starwars.core.usecases.species

import app.cash.turbine.test
import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.models.Film
import com.github.dragon925.starwars.core.models.Planet
import com.github.dragon925.starwars.core.models.Species
import com.github.dragon925.starwars.core.models.SpeciesDetails
import com.github.dragon925.starwars.core.repository.ModelRepository
import com.github.dragon925.starwars.core.utils.DataError
import com.github.dragon925.starwars.core.utils.DataResult
import com.github.dragon925.starwars.core.utils.createEmptyCharacter
import com.github.dragon925.starwars.core.utils.createEmptyFilm
import com.github.dragon925.starwars.core.utils.createEmptyPlanet
import com.github.dragon925.starwars.core.utils.createEmptySpecies
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoadSpeciesDetailsUseCaseTest {

    private lateinit var characterRepository: ModelRepository<Character>
    private lateinit var filmRepository: ModelRepository<Film>
    private lateinit var speciesRepository: ModelRepository<Species>
    private lateinit var planetRepository: ModelRepository<Planet>

    private lateinit var useCase: LoadSpeciesDetailsUseCase

    private val baseSpecies = createEmptySpecies().copy(
        id = 1,
        name = "Human",
        peopleIds = listOf(1, 2),
        filmIds = listOf(1, 2),
        homeworldId = 1
    )

    private val characters = listOf(
        createEmptyCharacter().copy(id = 1, name = "Luke Skywalker"),
        createEmptyCharacter().copy(id = 2, name = "Obi-Wan Kenobi")
    )

    private val films = listOf(
        createEmptyFilm().copy(id = 1, title = "A New Hope", episodeNumber = 4),
        createEmptyFilm().copy(id = 2, title = "The Empire Strikes Back", episodeNumber = 5),
    )

    private val homeworld = createEmptyPlanet().copy(id = 1, name = "Tatooine")

    @Before
    fun setUp() {
        characterRepository = mockk()
        filmRepository = mockk()
        speciesRepository = mockk()
        planetRepository = mockk()

        useCase = LoadSpeciesDetailsUseCase(
            speciesRepository, filmRepository, characterRepository, planetRepository
        )
    }

    @Test
    fun `test loading state during data fetching`() = runTest {
        every { speciesRepository.loadById(baseSpecies.id) } returns flowOf(
            DataResult.Loading
        )

        useCase(baseSpecies.id).test {
            assertTrue(awaitItem() is DataResult.Loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading species`() = runTest {
        val error = DataError.Network.NOT_FOUND

        every { speciesRepository.loadById(baseSpecies.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Error(error)
        )

        useCase(baseSpecies.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test successful species details loading`() = runTest {
        every { speciesRepository.loadById(baseSpecies.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseSpecies)
        )
        every { characterRepository.loadByIds(baseSpecies.peopleIds) } returns flowOf(
            DataResult.Success(characters)
        )
        every { filmRepository.loadByIds(baseSpecies.filmIds) } returns flowOf(
            DataResult.Success(films)
        )
        every { planetRepository.loadById(baseSpecies.homeworldId!!) } returns flowOf(
            DataResult.Success(homeworld)
        )

        useCase(baseSpecies.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Success)

            val details = (result as DataResult.Success).data
            assertTrue(details is SpeciesDetails)

            val speciesDetails = details as SpeciesDetails
            assertEquals(baseSpecies.name, speciesDetails.name)
            assertEquals(characters.size, speciesDetails.peoples.size)
            assertEquals(films.size, speciesDetails.films.size)
            assertEquals(homeworld, speciesDetails.homeworld)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test successful species details loading with null homeworld`() = runTest {
        every { speciesRepository.loadById(baseSpecies.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseSpecies.copy(homeworldId = null))
        )
        every { characterRepository.loadByIds(baseSpecies.peopleIds) } returns flowOf(
            DataResult.Success(characters)
        )
        every { filmRepository.loadByIds(baseSpecies.filmIds) } returns flowOf(
            DataResult.Success(films)
        )

        useCase(baseSpecies.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Success)

            val details = (result as DataResult.Success).data
            assertTrue(details is SpeciesDetails)

            val speciesDetails = details as SpeciesDetails
            assertEquals(baseSpecies.name, speciesDetails.name)
            assertEquals(characters.size, speciesDetails.peoples.size)
            assertEquals(films.size, speciesDetails.films.size)
            assertNull(speciesDetails.homeworld)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading characters`() = runTest {
        val error = DataError.Network.TIMEOUT

        every { speciesRepository.loadById(baseSpecies.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseSpecies)
        )
        every { characterRepository.loadByIds(baseSpecies.peopleIds) } returns flowOf(
            DataResult.Error(error)
        )
        every { filmRepository.loadByIds(baseSpecies.filmIds) } returns flowOf(
            DataResult.Success(films)
        )
        every { planetRepository.loadById(baseSpecies.homeworldId!!) } returns flowOf(
            DataResult.Success(homeworld)
        )

        useCase(baseSpecies.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading films`() = runTest {
        val error = DataError.Network.UNKNOWN

        every { speciesRepository.loadById(baseSpecies.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseSpecies)
        )
        every { characterRepository.loadByIds(baseSpecies.peopleIds) } returns flowOf(
            DataResult.Success(characters)
        )
        every { filmRepository.loadByIds(baseSpecies.filmIds) } returns flowOf(
            DataResult.Error(error)
        )
        every { planetRepository.loadById(baseSpecies.homeworldId!!) } returns flowOf(
            DataResult.Success(homeworld)
        )

        useCase(baseSpecies.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }
}