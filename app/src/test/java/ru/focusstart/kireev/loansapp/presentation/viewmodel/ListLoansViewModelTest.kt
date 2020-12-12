package ru.focusstart.kireev.loansapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.usecase.ListLoansUseCase
import ru.focusstart.kireev.testrule.TestSchedulerRule

class ListLoansViewModelTest {
    @get:Rule
    var testRule = TestSchedulerRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val listLoanUseCase: ListLoansUseCase = mock()

    private val mockListLoans: List<Loan> = mock()

    private val viewModel = ListLoansViewModel(listLoanUseCase)

    @Test
    fun `on load loans EXPECT loading value is changing`() {
        `when`(listLoanUseCase.invoke()).thenReturn(Single.just(mockListLoans))

        viewModel.loadLoans()
        val actual = viewModel.loading.value

        assertEquals(false, actual)
    }

    @Test
    fun `on success loading loans EXPECT get list loans`() {
        `when`(listLoanUseCase.invoke()).thenReturn(Single.just(mockListLoans))

        viewModel.loadLoans()
        val actual = viewModel.allLoans.value

        assertEquals(mockListLoans, actual)
    }

    @Test
    fun `on failure loading loan with error 401 EXPECT error value is error 401`() {
        `when`(listLoanUseCase.invoke()).thenReturn(Single.error(Throwable("error 401")))

        viewModel.loadLoans()
        val actual = viewModel.error.value

        assertEquals(R.string.error_401, actual)
    }

    @Test
    fun `on failure loading loan with error 403 EXPECT error value is error 403`() {
        `when`(listLoanUseCase.invoke()).thenReturn(Single.error(Throwable("error 403")))

        viewModel.loadLoans()
        val actual = viewModel.error.value

        assertEquals(R.string.error_403, actual)
    }

    @Test
    fun `on failure loading loan with error 404 EXPECT error value is error 404`() {
        `when`(listLoanUseCase.invoke()).thenReturn(Single.error(Throwable("error 404")))

        viewModel.loadLoans()
        val actual = viewModel.error.value

        assertEquals(R.string.error_404, actual)
    }

    @Test
    fun `on failure loading loan with error 504 EXPECT error value is error 504`() {
        `when`(listLoanUseCase.invoke()).thenReturn(Single.error(Throwable("error 504")))

        viewModel.loadLoans()
        val actual = viewModel.error.value

        assertEquals(R.string.error_504, actual)
    }

    @Test
    fun `on failure loading loan if no internet EXPECT error value is error no internet`() {
        `when`(listLoanUseCase.invoke()).thenReturn(Single.error(Throwable("No address associated with hostname")))

        viewModel.loadLoans()
        val actual = viewModel.error.value

        assertEquals(R.string.error_no_internet, actual)
    }

    @Test
    fun `on failure loading loan with other error EXPECT error value is error server not response`() {
        `when`(listLoanUseCase.invoke()).thenReturn(Single.error(Throwable("other error")))

        viewModel.loadLoans()
        val actual = viewModel.error.value

        assertEquals(R.string.error_server_not_response, actual)
    }
}