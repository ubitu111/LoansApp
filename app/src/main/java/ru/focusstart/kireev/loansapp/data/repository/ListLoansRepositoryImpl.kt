package ru.focusstart.kireev.loansapp.data.repository

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.data.converter.LoanApiToLoanConverter
import ru.focusstart.kireev.loansapp.data.datasource.TokenDataSource
import ru.focusstart.kireev.loansapp.data.network.ApiService
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.repository.ListLoansRepository

class ListLoansRepositoryImpl(
    private val apiService: ApiService,
    private val dataSource: TokenDataSource
) : ListLoansRepository {
    override fun getAllLoans(): Single<List<Loan>> {
        val token = dataSource.getToken()
        return apiService.getAllLoans(token).map {list ->
            list.map { loan ->
                LoanApiToLoanConverter.convert(loan)
            }
        }
    }
}