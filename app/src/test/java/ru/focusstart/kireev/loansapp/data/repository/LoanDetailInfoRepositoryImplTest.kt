package ru.focusstart.kireev.loansapp.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import ru.focusstart.kireev.loansapp.data.converter.LoanApiToLoanConverter
import ru.focusstart.kireev.loansapp.data.datasource.TokenDataSource
import ru.focusstart.kireev.loansapp.data.model.LoanApi
import ru.focusstart.kireev.loansapp.data.network.ApiService

class LoanDetailInfoRepositoryImplTest {
    private val mockLoanApi: LoanApi =
        LoanApi(0, "", "", 0L, "", 0.0, 0, "", "")

    private val api: ApiService = mock()
    private val dataSource: TokenDataSource = mock()

    private val repository = LoanDetailInfoRepositoryImpl(api, dataSource)

    @Test
    fun `on call getLoanDetailInfo EXPECT takes token`() {
        `when`(dataSource.getToken()).thenReturn("")
        `when`(api.getLoanDetailInfo(anyString(), anyLong())).thenReturn(Single.just(mockLoanApi))

        repository.getLoanDetailInfo(0L)

        verify(dataSource).getToken()
    }

    @Test
    fun `on call getLoanDetailInfo EXPECT complete`() {
        `when`(dataSource.getToken()).thenReturn("")
        `when`(api.getLoanDetailInfo(anyString(), anyLong())).thenReturn(Single.just(mockLoanApi))

        repository.getLoanDetailInfo(0L)
            .test()
            .assertComplete()

    }
    @Test
    fun `on call getLoanDetailInfo EXPECT get loan`() {
        `when`(dataSource.getToken()).thenReturn("")
        `when`(api.getLoanDetailInfo(anyString(), anyLong())).thenReturn(Single.just(mockLoanApi))
        val expected = LoanApiToLoanConverter.convert(mockLoanApi)

        repository.getLoanDetailInfo(0L)
            .test()
            .assertValue(expected)

    }
}