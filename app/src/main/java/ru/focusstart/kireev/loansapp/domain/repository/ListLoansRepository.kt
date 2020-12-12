package ru.focusstart.kireev.loansapp.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.domain.entity.Loan

interface ListLoansRepository {
    fun getAllLoans(): Single<List<Loan>>
}