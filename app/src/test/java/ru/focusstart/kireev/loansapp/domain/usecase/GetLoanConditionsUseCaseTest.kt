package ru.focusstart.kireev.loansapp.domain.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test
import ru.focusstart.kireev.loansapp.domain.entity.LoanConditions
import ru.focusstart.kireev.loansapp.domain.repository.CreateLoanRepository
import ru.focusstart.kireev.testrule.TestSchedulerRule

class GetLoanConditionsUseCaseTest {
    @get:Rule
    var testRule = TestSchedulerRule()

    @Test
    fun `on invoke EXPECT complete`() {
        val conditions: LoanConditions = mock()
        val repository: CreateLoanRepository = mock {
            on { getLoanConditions() } doReturn Single.just(conditions)
        }
        val useCase = GetLoanConditionsUseCase(repository)

        useCase()
            .test()
            .assertComplete()
    }

    @Test
    fun `on invoke EXPECT get loan conditions`() {
        val conditions: LoanConditions = mock()
        val repository: CreateLoanRepository = mock {
            on { getLoanConditions() } doReturn Single.just(conditions)
        }
        val useCase = GetLoanConditionsUseCase(repository)

        useCase()
            .test()
            .assertValue(conditions)
    }
}