package ru.focusstart.kireev.loansapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.domain.entity.LoanConditions
import ru.focusstart.kireev.loansapp.domain.entity.LoanRequest
import ru.focusstart.kireev.loansapp.domain.entity.Result
import ru.focusstart.kireev.loansapp.domain.usecase.GetLoanConditionsUseCase
import ru.focusstart.kireev.loansapp.domain.usecase.SendLoanUseCase
import ru.focusstart.kireev.testrule.TestSchedulerRule

class CreateLoanViewModelTest {
    @get:Rule
    var testRule = TestSchedulerRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockLoanConditions: LoanConditions = mock()

    private val getLoanConditionsUseCase: GetLoanConditionsUseCase = mock()
    private val sendLoanUseCase: SendLoanUseCase = mock()

    private val mockLoanRequest: LoanRequest = mock()

    private val viewModel = CreateLoanViewModel(getLoanConditionsUseCase, sendLoanUseCase)


    @Test
    fun `on start loading loan conditions EXPECT loading value is changing`() {
        `when`(getLoanConditionsUseCase.invoke()).thenReturn(Single.just(mockLoanConditions))
        val isLoading = viewModel.loading

        viewModel.loadLoanConditions()
        val actual = isLoading.value

        assertEquals(false, actual)
    }

    @Test
    fun `on create loan EXPECT loading value is changing`() {
        `when`(sendLoanUseCase.invoke(mockLoanRequest)).thenReturn(Single.just(Result.SUCCESS))
        val isLoading = viewModel.loading

        viewModel.createLoan(mockLoanRequest)
        val actual = isLoading.value

        assertEquals(false, actual)
    }

    @Test
    fun `on success loading loan conditions EXPECT loadLoanConditions get loan conditions`() {
        `when`(getLoanConditionsUseCase.invoke()).thenReturn(Single.just(mockLoanConditions))

        viewModel.loadLoanConditions()
        val actual = viewModel.loanConditions.value

        assertEquals(mockLoanConditions, actual)
    }

    @Test
    fun `on success create loan EXPECT result success`() {
        `when`(sendLoanUseCase.invoke(mockLoanRequest)).thenReturn(Single.just(Result.SUCCESS))

        viewModel.createLoan(mockLoanRequest)
        val actual = viewModel.result.value

        assertEquals(Result.SUCCESS, actual)
    }

    @Test
    fun `on failure loading loan conditions with error 401 EXPECT error value is error 401`() {
        `when`(getLoanConditionsUseCase.invoke()).thenReturn(Single.error(Throwable("error 401")))

        viewModel.loadLoanConditions()
        val actual = viewModel.error.value

        assertEquals(R.string.error_401, actual)
    }

    @Test
    fun `on failure create loan with error 401 EXPECT error value is error 401`() {
        `when`(sendLoanUseCase.invoke(mockLoanRequest)).thenReturn(Single.error(Throwable("error 401")))

        viewModel.createLoan(mockLoanRequest)
        val actual = viewModel.error.value

        assertEquals(R.string.error_401, actual)
    }

    @Test
    fun `on failure loading loan conditions with error 403 EXPECT error value is error 403`() {
        `when`(getLoanConditionsUseCase.invoke()).thenReturn(Single.error(Throwable("error 403")))

        viewModel.loadLoanConditions()
        val actual = viewModel.error.value

        assertEquals(R.string.error_403, actual)
    }

    @Test
    fun `on failure create loan with error 403 EXPECT error value is error 403`() {
        `when`(sendLoanUseCase.invoke(mockLoanRequest)).thenReturn(Single.error(Throwable("error 403")))

        viewModel.createLoan(mockLoanRequest)
        val actual = viewModel.error.value

        assertEquals(R.string.error_403, actual)
    }

    @Test
    fun `on failure loading loan conditions with error 404 EXPECT error value is error 404`() {
        `when`(getLoanConditionsUseCase.invoke()).thenReturn(Single.error(Throwable("error 404")))

        viewModel.loadLoanConditions()
        val actual = viewModel.error.value

        assertEquals(R.string.error_404, actual)
    }

    @Test
    fun `on failure create loan with error 404 EXPECT error value is error 404`() {
        `when`(sendLoanUseCase.invoke(mockLoanRequest)).thenReturn(Single.error(Throwable("error 404")))

        viewModel.createLoan(mockLoanRequest)
        val actual = viewModel.error.value

        assertEquals(R.string.error_404, actual)
    }

    @Test
    fun `on failure loading loan conditions with error 504 EXPECT error value is error 504`() {
        `when`(getLoanConditionsUseCase.invoke()).thenReturn(Single.error(Throwable("error 504")))

        viewModel.loadLoanConditions()
        val actual = viewModel.error.value

        assertEquals(R.string.error_504, actual)
    }

    @Test
    fun `on failure create loan with error 504 EXPECT error value is error 504`() {
        `when`(sendLoanUseCase.invoke(mockLoanRequest)).thenReturn(Single.error(Throwable("error 504")))

        viewModel.createLoan(mockLoanRequest)
        val actual = viewModel.error.value

        assertEquals(R.string.error_504, actual)
    }

    @Test
    fun `on failure loading loan conditions if no internet EXPECT error value is error no internet`() {
        `when`(getLoanConditionsUseCase.invoke()).thenReturn(Single.error(Throwable("No address associated with hostname")))

        viewModel.loadLoanConditions()
        val actual = viewModel.error.value

        assertEquals(R.string.error_no_internet, actual)
    }

    @Test
    fun `on failure create loan if no internet EXPECT error value is error no internet`() {
        `when`(sendLoanUseCase.invoke(mockLoanRequest)).thenReturn(Single.error(Throwable("No address associated with hostname")))

        viewModel.createLoan(mockLoanRequest)
        val actual = viewModel.error.value

        assertEquals(R.string.error_no_internet, actual)
    }

    @Test
    fun `on failure loading loan conditions with other error EXPECT error value is error server not response`() {
        `when`(getLoanConditionsUseCase.invoke()).thenReturn(Single.error(Throwable("other error")))

        viewModel.loadLoanConditions()
        val actual = viewModel.error.value

        assertEquals(R.string.error_server_not_response, actual)
    }

    @Test
    fun `on failure create loan with other error EXPECT error value is error server not response`() {
        `when`(sendLoanUseCase.invoke(mockLoanRequest)).thenReturn(Single.error(Throwable("other error")))

        viewModel.createLoan(mockLoanRequest)
        val actual = viewModel.error.value

        assertEquals(R.string.error_server_not_response, actual)
    }
}