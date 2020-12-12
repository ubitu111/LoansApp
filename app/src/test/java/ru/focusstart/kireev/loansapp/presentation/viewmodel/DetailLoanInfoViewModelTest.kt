package ru.focusstart.kireev.loansapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.usecase.LoanDetailInfoUseCase
import ru.focusstart.kireev.testrule.TestSchedulerRule

class DetailLoanInfoViewModelTest {
    @get:Rule
    var testRule = TestSchedulerRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val loanDetailInfoUseCase: LoanDetailInfoUseCase = mock()

    private val mockLoan: Loan = mock()

    private val viewModel = DetailLoanInfoViewModel(loanDetailInfoUseCase)

    @Test
    fun `on start loading loan EXPECT loading value is changing`() {
        `when`(loanDetailInfoUseCase.invoke(anyLong())).thenReturn(Single.just(mockLoan))
        val isLoading = viewModel.loading

        viewModel.loadLoan(anyLong())
        val actual = isLoading.value

        assertEquals(false, actual)
    }

    @Test
    fun `on success load loan EXPECT get loan`() {
        `when`(loanDetailInfoUseCase.invoke(anyLong())).thenReturn(Single.just(mockLoan))

        viewModel.loadLoan(anyLong())
        val actual = viewModel.loan.value

        assertEquals(mockLoan, actual)
    }

    @Test
    fun `on failure loading loan conditions with error 401 EXPECT error value is error 401`() {
        `when`(loanDetailInfoUseCase.invoke(anyLong())).thenReturn(Single.error(Throwable("error 401")))

        viewModel.loadLoan(anyLong())
        val actual = viewModel.error.value

        assertEquals(R.string.error_401, actual)
    }

    @Test
    fun `on failure loading loan conditions with error 403 EXPECT error value is error 403`() {
        `when`(loanDetailInfoUseCase.invoke(anyLong())).thenReturn(Single.error(Throwable("error 403")))

        viewModel.loadLoan(anyLong())
        val actual = viewModel.error.value

        assertEquals(R.string.error_403, actual)
    }

    @Test
    fun `on failure loading loan conditions with error 404 EXPECT error value is error 404`() {
        `when`(loanDetailInfoUseCase.invoke(anyLong())).thenReturn(Single.error(Throwable("error 404")))

        viewModel.loadLoan(anyLong())
        val actual = viewModel.error.value

        assertEquals(R.string.error_404, actual)
    }

    @Test
    fun `on failure loading loan conditions with error 504 EXPECT error value is error 504`() {
        `when`(loanDetailInfoUseCase.invoke(anyLong())).thenReturn(Single.error(Throwable("error 504")))

        viewModel.loadLoan(anyLong())
        val actual = viewModel.error.value

        assertEquals(R.string.error_504, actual)
    }

    @Test
    fun `on failure loading loan conditions if no internet EXPECT error value is error no internet`() {
        `when`(loanDetailInfoUseCase.invoke(anyLong())).thenReturn(Single.error(Throwable("No address associated with hostname")))

        viewModel.loadLoan(anyLong())
        val actual = viewModel.error.value

        assertEquals(R.string.error_no_internet, actual)
    }

    @Test
    fun `on failure loading loan conditions with other error EXPECT error value is error server not response`() {
        `when`(loanDetailInfoUseCase.invoke(anyLong())).thenReturn(Single.error(Throwable("other error")))

        viewModel.loadLoan(anyLong())
        val actual = viewModel.error.value

        assertEquals(R.string.error_server_not_response, actual)
    }
}