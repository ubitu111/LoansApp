package ru.focusstart.kireev.loansapp.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.repository.ListLoansRepository

class ListLoansUseCase(private val listLoansRep: ListLoansRepository) {
    operator fun invoke(): Single<List<Loan>> = listLoansRep.getAllLoans()
}