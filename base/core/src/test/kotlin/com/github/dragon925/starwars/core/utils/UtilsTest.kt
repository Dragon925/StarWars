package com.github.dragon925.starwars.core.utils

import org.junit.Assert.*
import org.junit.Test

class UtilsTest {

    @Test
    fun `anyOf returns true when any element matches predicate`() {
        val numbers = listOf(1, 2, 3, 4, 5)

        val result = anyOf(*numbers.toTypedArray()) { it > 3 }

        assertTrue(result)
    }

    @Test
    fun `anyOf returns false when no element matches predicate`() {
        val numbers = listOf(1, 2, 3)

        val result = anyOf(*numbers.toTypedArray()) { it > 3 }

        assertFalse(result)
    }

    @Test
    fun `anyOf returns false for empty input`() {
        val result = anyOf<Int>() { it > 0 }

        assertFalse(result)
    }

    @Test
    fun `firstOf returns first matching element`() {
        val numbers = listOf(1, 2, 3, 4, 5)

        val result = firstOf(*numbers.toTypedArray()) { it > 3 }

        assertEquals(4, result)
    }

    @Test
    fun `firstOf returns null when no element matches predicate`() {
        val numbers = listOf(1, 2, 3)

        val result = firstOf(*numbers.toTypedArray()) { it > 3 }

        assertNull(result)
    }

    @Test
    fun `firstOf returns null for empty input`() {
        val result = firstOf<Int> { it > 0 }

        assertNull(result)
    }

}