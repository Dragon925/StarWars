package com.github.dragon925.starwars.core.usecases.starships

import app.cash.turbine.test
import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.models.Film
import com.github.dragon925.starwars.core.models.Starship
import com.github.dragon925.starwars.core.models.StarshipDetails
import com.github.dragon925.starwars.core.repository.ModelRepository
import com.github.dragon925.starwars.core.utils.DataError
import com.github.dragon925.starwars.core.utils.DataResult
import com.github.dragon925.starwars.core.utils.createEmptyCharacter
import com.github.dragon925.starwars.core.utils.createEmptyFilm
import com.github.dragon925.starwars.core.utils.createEmptyStarship
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoadStarshipDetailsUseCaseTest {

    private lateinit var starshipRepository: ModelRepository<Starship>
    private lateinit var filmRepository: ModelRepository<Film>
    private lateinit var characterRepository: ModelRepository<Character>

    private lateinit var useCase: LoadStarshipDetailsUseCase

    private val baseStarship = createEmptyStarship().copy(
        id = 1,
        name = "Millennium Falcon",
        filmIds = listOf(1, 2)
    )

    private val films = listOf(
        createEmptyFilm().copy(id = 1, title = "A New Hope", episodeNumber = 4),
        createEmptyFilm().copy(id = 2, title = "The Empire Strikes Back", episodeNumber = 5),
    )

    private val characters = listOf(
        createEmptyCharacter().copy(id = 1, name = "Han Solo"),
        createEmptyCharacter().copy(id = 2, name = "Chewbacca")
    )

    @Before
    fun setUp() {
        starshipRepository = mockk(relaxed = true)
        filmRepository = mockk(relaxed = true)
        characterRepository = mockk(relaxed = true)

        useCase = LoadStarshipDetailsUseCase(starshipRepository, filmRepository, characterRepository)
    }

    @Test
    fun `test loading state during data fetching`() = runTest {
        every { starshipRepository.loadById(baseStarship.id) } returns flowOf(
            DataResult.Loading
        )

        useCase(baseStarship.id).test {
            assertTrue(awaitItem() is DataResult.Loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading starship`() = runTest {
        val error = DataError.Network.NOT_FOUND
        every { starshipRepository.loadById(baseStarship.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Error(error)
        )

        useCase(baseStarship.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test successful starship details loading`() = runTest {
        every { starshipRepository.loadById(baseStarship.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseStarship)
        )
        every { filmRepository.loadByIds(baseStarship.filmIds) } returns flowOf(
            DataResult.Success(films)
        )
        every { characterRepository.loadByIds(baseStarship.pilotIds) } returns flowOf(
            DataResult.Success(characters)
        )

        useCase(baseStarship.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Success)

            val details = (result as DataResult.Success).data
            assertTrue(details is StarshipDetails)

            val starshipDetails = details as StarshipDetails
            assertEquals(baseStarship.name, starshipDetails.name)
            assertEquals(films.size, starshipDetails.films.size)
            assertEquals(characters.size, starshipDetails.pilots.size)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading characters`() = runTest {
        val error = DataError.Network.TIMEOUT

        every { starshipRepository.loadById(baseStarship.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseStarship)
        )
        every { filmRepository.loadByIds(baseStarship.filmIds) } returns flowOf(
            DataResult.Success(films)
        )
        every { characterRepository.loadByIds(baseStarship.pilotIds) } returns flowOf(
            DataResult.Error(error)
        )

        useCase(baseStarship.id).test {
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

        every { starshipRepository.loadById(baseStarship.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseStarship)
        )
        every { filmRepository.loadByIds(baseStarship.filmIds) } returns flowOf(
            DataResult.Error(error)
        )
        every { characterRepository.loadByIds(baseStarship.pilotIds) } returns flowOf(
            DataResult.Success(characters)
        )

        useCase(baseStarship.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }
}