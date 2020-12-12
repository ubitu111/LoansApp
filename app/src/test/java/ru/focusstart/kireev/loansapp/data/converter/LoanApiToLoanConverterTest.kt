package ru.focusstart.kireev.loansapp.data.converter

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.focusstart.kireev.loansapp.data.model.LoanApi
import ru.focusstart.kireev.loansapp.domain.entity.StateLoan

class LoanApiToLoanConverterTest {

    @Test
    fun `give LoanApi with firstName and lastName EXPECTED loan with fullName`() {
        val loanApi = LoanApi(
            0, "2020-12-11T07:33:57.285Z",
            "Иван", 0L, "Сидоров", 0.0, 0, "", ""
        )

        val actual = LoanApiToLoanConverter.convert(loanApi).fullName

        val expected = "Иван Сидоров"
        assertEquals(expected, actual)
    }

    @Test
    fun `give LoanApi with Api date format EXPECT Loan with needed date format`() {
        val loanApi = LoanApi(
            0, "2020-12-11T07:33:57.285Z",
            "", 0L, "", 0.0, 0, "", ""
        )

        val actual = LoanApiToLoanConverter.convert(loanApi).date

        val expected = "11 дек 2020"
        assertEquals(expected, actual)
    }

    @Test
    fun `give LoanApi with approved state name EXPECT Loan with approved enum`() {
        val loanApi = LoanApi(
            0, "2020-12-11T07:33:57.285Z", "",
            0L, "", 0.0,
            0, "", "APPROVED"
        )

        val actual = LoanApiToLoanConverter.convert(loanApi).state

        val expected = StateLoan.APPROVED
        assertEquals(expected, actual)
    }

    @Test
    fun `give LoanApi with rejected state name EXPECT Loan with rejected enum`() {
        val loanApi = LoanApi(
            0, "2020-12-11T07:33:57.285Z", "",
            0L, "", 0.0,
            0, "", "REJECTED"
        )

        val actual = LoanApiToLoanConverter.convert(loanApi).state

        val expected = StateLoan.REJECTED
        assertEquals(expected, actual)
    }

    @Test
    fun `give LoanApi with registered state name EXPECT Loan with registered enum`() {
        val loanApi = LoanApi(
            0, "2020-12-11T07:33:57.285Z", "",
            0L, "", 0.0,
            0, "", "REGISTERED"
        )

        val actual = LoanApiToLoanConverter.convert(loanApi).state

        val expected = StateLoan.REGISTERED
        assertEquals(expected, actual)
    }
}