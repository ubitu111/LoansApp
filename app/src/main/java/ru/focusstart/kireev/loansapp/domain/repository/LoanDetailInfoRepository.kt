package ru.focusstart.kireev.loansapp.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.domain.entity.Loan

interface LoanDetailInfoRepository {
    fun getLoanDetailInfo(loanId: Long): Single<Loan>
}