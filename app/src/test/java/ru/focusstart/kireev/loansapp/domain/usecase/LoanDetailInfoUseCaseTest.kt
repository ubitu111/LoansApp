package ru.focusstart.kireev.loansapp.domain.usecase

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.repository.LoanDetailInfoRepository
import ru.focusstart.kireev.testrule.TestSchedulerRule

class LoanDetailInfoUseCaseTest {
    @get:Rule
    var testRule = TestSchedulerRule()

    @Test
    fun `on invoke EXPECT complete`() {
        val loan: Loan = mock()
        val repository: LoanDetailInfoRepository = mock {
            on { getLoanDetailInfo(any()) } doReturn Single.just(loan)
        }
        val useCase = LoanDetailInfoUseCase(repository)

        useCase(any())
            .test()
            .assertComplete()
    }

    @Test
    fun `on invoke EXPECT get loan`() {
        val loan: Loan = mock()
        val repository: LoanDetailInfoRepository = mock {
            on { getLoanDetailInfo(any()) } doReturn Single.just(loan)
        }
        val useCase = LoanDetailInfoUseCase(repository)

        useCase(any())
            .test()
            .assertValue(loan)
    }
}