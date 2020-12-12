package ru.focusstart.kireev.loansapp.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import ru.focusstart.kireev.loansapp.data.datasource.TokenDataSource
import ru.focusstart.kireev.loansapp.data.model.LoanApi
import ru.focusstart.kireev.loansapp.data.network.ApiService
import ru.focusstart.kireev.loansapp.domain.entity.LoanConditions
import ru.focusstart.kireev.loansapp.domain.entity.LoanRequest
import ru.focusstart.kireev.loansapp.domain.entity.Result

class CreateLoanRepositoryImplTest {

    private val mockLoanConditions: LoanConditions = mock()
    private val mockLoanRequest: LoanRequest = mock()
    private val mockLoanApi: LoanApi = mock()

    private val api: ApiService = mock()
    private val dataSource: TokenDataSource = mock()

    private val repository = CreateLoanRepositoryImpl(api, dataSource)

    @Test
    fun `on call getLoanConditions EXPECT takes token`() {
        repository.getLoanConditions()

        verify(dataSource).getToken()
    }

    @Test
    fun `on call getLoanConditions EXPECT complete`() {
        `when`(dataSource.getToken()).thenReturn("")
        `when`(api.getLoansConditions(anyString())).thenReturn(Single.just(mockLoanConditions))

        repository.getLoanConditions()
            .test()
            .assertComplete()
    }

    @Test
    fun `on call getLoanConditions EXPECT get loan conditions`() {
        `when`(dataSource.getToken()).thenReturn("")
        `when`(api.getLoansConditions(anyString())).thenReturn(Single.just(mockLoanConditions))

        repository.getLoanConditions()
            .test()
            .assertValue(mockLoanConditions)
    }

    @Test
    fun `on call createLoan EXPECT takes token`() {
        `when`(dataSource.getToken()).thenReturn("")
        `when`(api.createLoan("", mockLoanRequest)).thenReturn(Single.just(mockLoanApi))

        repository.createLoan(mockLoanRequest)

        verify(dataSource).getToken()
    }

    @Test
    fun `on call createLoan EXPECT complete`() {
        `when`(dataSource.getToken()).thenReturn("")
        `when`(api.createLoan("", mockLoanRequest)).thenReturn(Single.just(mockLoanApi))

        repository.createLoan(mockLoanRequest)
            .test()
            .assertComplete()
    }

    @Test
    fun `on call createLoan EXPECT get result success`() {
        `when`(dataSource.getToken()).thenReturn("")
        `when`(api.createLoan("", mockLoanRequest)).thenReturn(Single.just(mockLoanApi))

        repository.createLoan(mockLoanRequest)
            .test()
            .assertValue(Result.SUCCESS)
    }
}