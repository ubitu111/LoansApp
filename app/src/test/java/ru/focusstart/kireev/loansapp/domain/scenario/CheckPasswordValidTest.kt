package ru.focusstart.kireev.loansapp.domain.scenario

import org.junit.Test
import org.junit.Assert.*

class CheckPasswordValidTest {
    @Test
    fun `give empty password EXPECT false`() {
        val passwordValidator = CheckPasswordValid()

        val actual = passwordValidator("")

        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `give short password EXPECT false`() {
        val passwordValidator = CheckPasswordValid()

        val actual = passwordValidator("123456")

        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `give correct password EXPECT true`() {
        val passwordValidator = CheckPasswordValid()

        val actual = passwordValidator("1234567")

        val expected = true
        assertEquals(expected, actual)
    }
}