package ru.focusstart.kireev.loansapp.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.domain.entity.LoanConditions
import ru.focusstart.kireev.loansapp.domain.entity.LoanRequest
import ru.focusstart.kireev.loansapp.domain.entity.Result

interface CreateLoanRepository {
    fun getLoanConditions(): Single<LoanConditions>
    fun createLoan(loanRequest: LoanRequest): Single<Result>
}