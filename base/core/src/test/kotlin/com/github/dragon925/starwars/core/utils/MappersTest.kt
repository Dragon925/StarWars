package com.github.dragon925.starwars.core.utils

import app.cash.turbine.test
import com.github.dragon925.starwars.core.models.Character
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import org.junit.Assert.*
import org.junit.Test

class MappersTest {

    // Test mapResult function
    @Test
    fun `mapResult should transform success result`() = runTest {
        val originalData = Character(
            id = 1, name = "Luke", birthYear = "19BBY",
            eyeColor = "blue", gender = "male", hairColor = "blond",
            height = "172", mass = "77", skinColor = "fair",
            homeworldId = 1, filmIds = emptyList(), speciesIds = emptyList(),
            starshipIds = emptyList(), vehicleIds = emptyList(),
            created = LocalDateTime(2023, 1, 1, 0, 0),
            edited = LocalDateTime(2023, 1, 1, 0, 0)
        )

        val expectedResult = "Transformed Luke"

        val flow = flowOf(DataResult.Loading, DataResult.Success(originalData))
            .mapResult { "Transformed ${it.name}" }

        flow.test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Success)
            assertEquals(expectedResult, (result as DataResult.Success<String>).data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `mapResult should preserve error result`() = runTest {
        val error = DataError.Network.UNKNOWN
        val flow = flowOf<DataResult<Any, DataError>>(DataResult.Loading, DataResult.Error(error))
            .mapResult { "Transformed" }

        flow.test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `mapResult should preserve loading state`() = runTest {
        val flow = flowOf<DataResult<Any, DataError>>(DataResult.Loading)
            .mapResult { "Transformed" }

        val result = flow.first()

        assertTrue(result is DataResult.Loading)
    }

    // Test flatMapResult function
    @Test
    fun `flatMapResult should transform success result with nested flow`() = runTest {
        val originalData = Character(
            id = 1, name = "Luke", birthYear = "19BBY",
            eyeColor = "blue", gender = "male", hairColor = "blond",
            height = "172", mass = "77", skinColor = "fair",
            homeworldId = 1, filmIds = emptyList(), speciesIds = emptyList(),
            starshipIds = emptyList(), vehicleIds = emptyList(),
            created = LocalDateTime(2023, 1, 1, 0, 0),
            edited = LocalDateTime(2023, 1, 1, 0, 0)
        )

        val expectedResult = "Transformed Luke"

        val flow = flowOf(DataResult.Loading, DataResult.Success(originalData))
            .flatMapResult {
                flowOf(DataResult.Success("Transformed ${it.name}"))
            }

        flow.test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Success)
            assertEquals(expectedResult, (result as DataResult.Success<String>).data)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `flatMapResult should preserve error result`() = runTest {
        val error = DataError.Network.UNKNOWN
        val flow = flowOf<DataResult<Any, DataError>>(DataResult.Loading, DataResult.Error(error))
            .flatMapResult {
                flowOf(DataResult.Success("Transformed"))
            }

        flow.test {
            assertTrue(awaitItem() is DataResult.Loading)

            val result = awaitItem()
            assertTrue(result is DataResult.Error)
            assertEquals(error, (result as DataResult.Error).error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `flatMapResult should preserve loading state`() = runTest {
        val flow = flowOf<DataResult<Any, DataError>>(DataResult.Loading)
            .flatMapResult {
                flowOf(DataResult.Success("Transformed"))
            }

        val result = flow.first()

        assertTrue(result is DataResult.Loading)
    }

    // Test getIfSuccess function
    @Test
    fun `getIfSuccess should return data for success result`() {
        val data = Character(
            id = 1, name = "Luke", birthYear = "19BBY",
            eyeColor = "blue", gender = "male", hairColor = "blond",
            height = "172", mass = "77", skinColor = "fair",
            homeworldId = 1, filmIds = emptyList(), speciesIds = emptyList(),
            starshipIds = emptyList(), vehicleIds = emptyList(),
            created = LocalDateTime(2023, 1, 1, 0, 0),
            edited = LocalDateTime(2023, 1, 1, 0, 0)
        )

        val result = DataResult.Success(data).getIfSuccess()

        assertEquals(data, result)
    }

    @Test
    fun `getIfSuccess should return default for non-success result`() {
        val error = DataResult.Error(DataError.Network.UNKNOWN)
        val default = Character(
            id = 0, name = "Default", birthYear = "unknown",
            eyeColor = "unknown", gender = "unknown", hairColor = "unknown",
            height = "0", mass = "0", skinColor = "unknown",
            homeworldId = 0, filmIds = emptyList(), speciesIds = emptyList(),
            starshipIds = emptyList(), vehicleIds = emptyList(),
            created = LocalDateTime(2023, 1, 1, 0, 0),
            edited = LocalDateTime(2023, 1, 1, 0, 0)
        )

        val result = error.getIfSuccess(default)

        assertEquals(default, result)
    }

    // Test getIfSuccess with list function
    @Test
    fun `getIfSuccess list should return data for success result`() {
        val data = listOf(
            Character(
                id = 1, name = "Luke", birthYear = "19BBY",
                eyeColor = "blue", gender = "male", hairColor = "blond",
                height = "172", mass = "77", skinColor = "fair",
                homeworldId = 1, filmIds = emptyList(), speciesIds = emptyList(),
                starshipIds = emptyList(), vehicleIds = emptyList(),
                created = LocalDateTime(2023, 1, 1, 0, 0),
                edited = LocalDateTime(2023, 1, 1, 0, 0)
            ),
            Character(
                id = 2, name = "Leia", birthYear = "19BBY",
                eyeColor = "brown", gender = "female", hairColor = "brown",
                height = "160", mass = "56", skinColor = "light",
                homeworldId = 2, filmIds = emptyList(), speciesIds = emptyList(),
                starshipIds = emptyList(), vehicleIds = emptyList(),
                created = LocalDateTime(2023, 1, 1, 0, 0),
                edited = LocalDateTime(2023, 1, 1, 0, 0)
            )
        )

        val result = DataResult.Success(data).getIfSuccess()

        assertEquals(data, result)
    }

    @Test
    fun `getIfSuccess list should return default for non-success result`() {
        val error = DataResult.Error(DataError.Network.UNKNOWN)
        val default = listOf(
            Character(
                id = 0, name = "Default", birthYear = "unknown",
                eyeColor = "unknown", gender = "unknown", hairColor = "unknown",
                height = "0", mass = "0", skinColor = "unknown",
                homeworldId = 0, filmIds = emptyList(), speciesIds = emptyList(),
                starshipIds = emptyList(), vehicleIds = emptyList(),
                created = LocalDateTime(2023, 1, 1, 0, 0),
                edited = LocalDateTime(2023, 1, 1, 0, 0)
            )
        )

        val result = error.getIfSuccess(default)

        assertEquals(default, result)
    }
}