package ru.focusstart.kireev.loansapp.domain.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test
import ru.focusstart.kireev.loansapp.domain.entity.LoanRequest
import ru.focusstart.kireev.loansapp.domain.entity.Result
import ru.focusstart.kireev.loansapp.domain.repository.CreateLoanRepository
import ru.focusstart.kireev.testrule.TestSchedulerRule

class SendLoanUseCaseTest {
    @get:Rule
    var testRule = TestSchedulerRule()

    @Test
    fun `on invoke EXPECT complete`() {
        val loanRequest: LoanRequest = mock()
        val repository: CreateLoanRepository = mock {
            on { createLoan(loanRequest) } doReturn Single.just(Result.SUCCESS)
        }
        val useCase = SendLoanUseCase(repository)

        useCase(loanRequest)
            .test()
            .assertComplete()
    }

    @Test
    fun `on invoke EXPECT get result success`() {
        val loanRequest: LoanRequest = mock()
        val repository: CreateLoanRepository = mock {
            on { createLoan(loanRequest) } doReturn Single.just(Result.SUCCESS)
        }
        val useCase = SendLoanUseCase(repository)

        useCase(loanRequest)
            .test()
            .assertValue(Result.SUCCESS)
    }
}