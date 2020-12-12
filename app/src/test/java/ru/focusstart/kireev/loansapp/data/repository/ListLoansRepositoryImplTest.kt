package ru.focusstart.kireev.loansapp.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.Mockito.`when`
import ru.focusstart.kireev.loansapp.data.converter.LoanApiToLoanConverter
import ru.focusstart.kireev.loansapp.data.datasource.TokenDataSource
import ru.focusstart.kireev.loansapp.data.model.LoanApi
import ru.focusstart.kireev.loansapp.data.network.ApiService

class ListLoansRepositoryImplTest {

    private val listLoansApi: List<LoanApi> = listOf(
        LoanApi(0, "", "", 0L, "", 0.0, 0, "", ""),
        LoanApi(0, "", "", 0L, "", 0.0, 0, "", "")
    )

    private val api: ApiService = mock()
    private val dataSource: TokenDataSource = mock()

    private val repository = ListLoansRepositoryImpl(api, dataSource)

    @Test
    fun `on call getAllLoans EXPECT takes token`() {
        `when`(dataSource.getToken()).thenReturn("")
        `when`(api.getAllLoans("")).thenReturn(Single.just(listLoansApi))

        repository.getAllLoans()

        verify(dataSource).getToken()
    }

    @Test
    fun `on call getAllLoans EXPECT complete`() {
        `when`(dataSource.getToken()).thenReturn("")
        `when`(api.getAllLoans("")).thenReturn(Single.just(listLoansApi))

        repository.getAllLoans()
            .test()
            .assertComplete()
    }

    @Test
    fun `on call getAllLoans EXPECT get list loans`() {
        `when`(dataSource.getToken()).thenReturn("")
        `when`(api.getAllLoans("")).thenReturn(Single.just(listLoansApi))
        val expected = listLoansApi.map { LoanApiToLoanConverter.convert(it) }

        repository.getAllLoans()
            .test()
            .assertValue(expected)
    }
}