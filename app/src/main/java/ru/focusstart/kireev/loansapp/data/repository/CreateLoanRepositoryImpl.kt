package ru.focusstart.kireev.loansapp.data.repository

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.data.datasource.TokenDataSource
import ru.focusstart.kireev.loansapp.data.network.ApiService
import ru.focusstart.kireev.loansapp.domain.entity.LoanConditions
import ru.focusstart.kireev.loansapp.domain.entity.LoanRequest
import ru.focusstart.kireev.loansapp.domain.entity.Result
import ru.focusstart.kireev.loansapp.domain.repository.CreateLoanRepository

class CreateLoanRepositoryImpl(
    private val api: ApiService,
    private val tokenDataSource: TokenDataSource
) : CreateLoanRepository {

    override fun getLoanConditions(): Single<LoanConditions> {
       val token = tokenDataSource.getToken()
        return api.getLoansConditions(token)
    }

    override fun createLoan(loanRequest: LoanRequest): Single<Result> {
        val token = tokenDataSource.getToken()
        return api.createLoan(token, loanRequest).map {
            Result.SUCCESS
        }
    }
}