package ru.focusstart.kireev.loansapp.domain.usecase

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.repository.LoanDetailInfoRepository

class LoanDetailInfoUseCase(
    private val loanDetailInfoRepository: LoanDetailInfoRepository
) {
    operator fun invoke(loanId: Long): Single<Loan> = loanDetailInfoRepository.getLoanDetailInfo(loanId)
}