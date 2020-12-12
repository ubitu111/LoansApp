package ru.focusstart.kireev.loansapp.data.repository

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.data.converter.LoanApiToLoanConverter
import ru.focusstart.kireev.loansapp.data.datasource.TokenDataSource
import ru.focusstart.kireev.loansapp.data.network.ApiService
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.repository.LoanDetailInfoRepository

class LoanDetailInfoRepositoryImpl(
    private val apiService: ApiService,
    private val dataSource: TokenDataSource
) : LoanDetailInfoRepository {
    override fun getLoanDetailInfo(loanId: Long): Single<Loan> {
        val token = dataSource.getToken()
        return apiService.getLoanDetailInfo(token, loanId).map {
            LoanApiToLoanConverter.convert(it)
        }
    }
}