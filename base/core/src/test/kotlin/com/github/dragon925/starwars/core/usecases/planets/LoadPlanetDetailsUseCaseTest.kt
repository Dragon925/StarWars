package com.github.dragon925.starwars.core.usecases.planets

import app.cash.turbine.test
import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.models.Film
import com.github.dragon925.starwars.core.models.Planet
import com.github.dragon925.starwars.core.models.PlanetDetails
import com.github.dragon925.starwars.core.repository.ModelRepository
import com.github.dragon925.starwars.core.utils.DataError
import com.github.dragon925.starwars.core.utils.DataResult
import com.github.dragon925.starwars.core.utils.createEmptyCharacter
import com.github.dragon925.starwars.core.utils.createEmptyFilm
import com.github.dragon925.starwars.core.utils.createEmptyPlanet
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoadPlanetDetailsUseCaseTest {

    private lateinit var planetRepository: ModelRepository<Planet>
    private lateinit var filmRepository: ModelRepository<Film>
    private lateinit var characterRepository: ModelRepository<Character>

    private lateinit var useCase: LoadPlanetDetailsUseCase

    private val basePlanet = createEmptyPlanet().copy(
        id = 1,
        name = "Tatooine",
        filmIds = listOf(1, 2),
        residentIds = listOf(1)
    )

    private val films = listOf(
        createEmptyFilm().copy(id = 1, title = "A New Hope", episodeNumber = 4),
        createEmptyFilm().copy(id = 2, title = "The Empire Strikes Back", episodeNumber = 5),
    )

    private val characters = listOf(
        createEmptyCharacter().copy(id = 1, name = "Luke Skywalker")
    )

    @Before
    fun setUp() {
        planetRepository = mockk(relaxed = true)
        filmRepository = mockk(relaxed = true)
        characterRepository = mockk(relaxed = true)

        useCase = LoadPlanetDetailsUseCase(
            planetRepository,
            filmRepository,
            characterRepository
        )
    }

    @Test
    fun `test loading state during data fetching`() = runTest {
        every { planetRepository.loadById(basePlanet.id) } returns flowOf(
            DataResult.Loading
        )

        useCase(basePlanet.id).test {
            assertTrue(awaitItem() is DataResult.Loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading planet`() = runTest {
        val error = DataError.Network.NOT_FOUND

        every { planetRepository.loadById(basePlanet.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Error(error)
        )

        useCase(basePlanet.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test successful planet details loading`() = runTest {
        every { planetRepository.loadById(basePlanet.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(basePlanet)
        )
        every { filmRepository.loadByIds(basePlanet.filmIds) } returns flowOf(
            DataResult.Success(films)
        )
        every { characterRepository.loadByIds(basePlanet.residentIds) } returns flowOf(
            DataResult.Success(characters)
        )

        useCase(basePlanet.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Success)

            val details = (result as DataResult.Success).data
            assertTrue(details is PlanetDetails)

            val planetDetails = details as PlanetDetails

            assertEquals(basePlanet.name, planetDetails.name)
            assertEquals(films.size, planetDetails.films.size)
            assertEquals(characters.size, planetDetails.residents.size)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading characters`() = runTest {
        val error = DataError.Network.TIMEOUT

        every { planetRepository.loadById(basePlanet.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(basePlanet)
        )
        every { filmRepository.loadByIds(basePlanet.filmIds) } returns flowOf(
            DataResult.Success(films)
        )
        every { characterRepository.loadByIds(basePlanet.residentIds) } returns flowOf(
            DataResult.Error(error)
        )

        useCase(basePlanet.id).test {
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

        every { planetRepository.loadById(basePlanet.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(basePlanet)
        )
        every { filmRepository.loadByIds(basePlanet.filmIds) } returns flowOf(
            DataResult.Error(error)
        )
        every { characterRepository.loadByIds(basePlanet.residentIds) } returns flowOf(
            DataResult.Success(characters)
        )

        useCase(basePlanet.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }

}