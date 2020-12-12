package ru.focusstart.kireev.loansapp.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.domain.entity.LoanConditions
import ru.focusstart.kireev.loansapp.domain.repository.CreateLoanRepository

class GetLoanConditionsUseCase(private val createLoanRepository: CreateLoanRepository) {
    operator fun invoke(): Single<LoanConditions> = createLoanRepository.getLoanConditions()
}