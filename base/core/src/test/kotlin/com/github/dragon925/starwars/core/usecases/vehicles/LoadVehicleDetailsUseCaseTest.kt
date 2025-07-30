package com.github.dragon925.starwars.core.usecases.vehicles

import app.cash.turbine.test
import com.github.dragon925.starwars.core.models.Character
import com.github.dragon925.starwars.core.models.Film
import com.github.dragon925.starwars.core.models.Vehicle
import com.github.dragon925.starwars.core.models.VehicleDetails
import com.github.dragon925.starwars.core.repository.ModelRepository
import com.github.dragon925.starwars.core.utils.DataError
import com.github.dragon925.starwars.core.utils.DataResult
import com.github.dragon925.starwars.core.utils.createEmptyCharacter
import com.github.dragon925.starwars.core.utils.createEmptyFilm
import com.github.dragon925.starwars.core.utils.createEmptyVehicle
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoadVehicleDetailsUseCaseTest {

    private lateinit var vehicleRepository: ModelRepository<Vehicle>
    private lateinit var filmRepository: ModelRepository<Film>
    private lateinit var characterRepository: ModelRepository<Character>

    private lateinit var useCase: LoadVehicleDetailsUseCase

    private val baseVehicle = createEmptyVehicle().copy(
        id = 1,
        name = "Sand Crawler",
        filmIds = listOf(1),
        pilotIds = listOf(1, 2)
    )

    private val films =  listOf(
        createEmptyFilm().copy(id = 1, "A New Hope")
    )

    private val characters = listOf(
        createEmptyCharacter().copy(id = 1, "Luke Skywalker"),
        createEmptyCharacter().copy(id = 2, "Obi-Wan Kenobi")
    )

    @Before
    fun setUp() {
        vehicleRepository = mockk(relaxed = true)
        filmRepository = mockk(relaxed = true)
        characterRepository = mockk(relaxed = true)

        useCase = LoadVehicleDetailsUseCase(vehicleRepository, filmRepository, characterRepository)
    }

    @Test
    fun `test loading state during data fetching`() = runTest {
        every { vehicleRepository.loadById(baseVehicle.id) } returns flowOf(
            DataResult.Loading
        )

        useCase(baseVehicle.id).test {
            assertTrue(awaitItem() is DataResult.Loading)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading vehicle`() = runTest {
        val error = DataError.Network.NOT_FOUND

        every { vehicleRepository.loadById(baseVehicle.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Error(error)
        )

        useCase(baseVehicle.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test successful vehicle details loading`() = runTest {
        every { vehicleRepository.loadById(baseVehicle.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseVehicle)
        )
        every { filmRepository.loadByIds(baseVehicle.filmIds) } returns flowOf(
            DataResult.Success(films)
        )
        every { characterRepository.loadByIds(baseVehicle.pilotIds) } returns flowOf(
            DataResult.Success(characters)
        )

        useCase(baseVehicle.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Success)

            val details = (result as DataResult.Success).data
            assertTrue(details is VehicleDetails)

            val vehicleDetails = details as VehicleDetails
            assertEquals(baseVehicle.name, vehicleDetails.name)
            assertEquals(films.size, vehicleDetails.films.size)
            assertEquals(characters.size, vehicleDetails.pilots.size)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `test error handling when loading characters`() = runTest {
        val error = DataError.Network.TIMEOUT

        every { vehicleRepository.loadById(baseVehicle.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseVehicle)
        )
        every { filmRepository.loadByIds(baseVehicle.filmIds) } returns flowOf(
            DataResult.Success(films)
        )
        every { characterRepository.loadByIds(baseVehicle.pilotIds) } returns flowOf(
            DataResult.Error(error)
        )

        useCase(baseVehicle.id).test {
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

        every { vehicleRepository.loadById(baseVehicle.id) } returns flowOf(
            DataResult.Loading,
            DataResult.Success(baseVehicle)
        )
        every { filmRepository.loadByIds(baseVehicle.filmIds) } returns flowOf(
            DataResult.Error(error)
        )
        every { characterRepository.loadByIds(baseVehicle.pilotIds) } returns flowOf(
            DataResult.Success(characters)
        )

        useCase(baseVehicle.id).test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)

            cancelAndConsumeRemainingEvents()
        }
    }
}