package ru.focusstart.kireev.loansapp.domain.scenario

import org.junit.Test
import org.junit.Assert.*

class CheckLoginValidTest {

    @Test
    fun `give blank login EXPECT false`() {
        val loginValidator = CheckLoginValid()

        val actual = loginValidator("")

        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `give login without '@' EXPECT false`() {
        val loginValidator = CheckLoginValid()

        val actual = loginValidator("someLogin")

        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `give login without top level domain name EXPECT false`() {
        val loginValidator = CheckLoginValid()

        val actual = loginValidator("someLogin@some")

        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `give correct login EXPECT true`() {
        val loginValidator = CheckLoginValid()

        val actual = loginValidator("someLogin@some.ru")

        val expected = true
        assertEquals(expected, actual)
    }
}