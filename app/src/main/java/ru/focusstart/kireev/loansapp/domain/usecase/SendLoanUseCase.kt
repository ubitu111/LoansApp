package ru.focusstart.kireev.loansapp.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.entity.LoanRequest
import ru.focusstart.kireev.loansapp.domain.entity.Result
import ru.focusstart.kireev.loansapp.domain.repository.CreateLoanRepository

class SendLoanUseCase(private val createLoanRepository: CreateLoanRepository) {
    operator fun invoke(loanRequest: LoanRequest): Single<Result> =
        createLoanRepository.createLoan(loanRequest)
}