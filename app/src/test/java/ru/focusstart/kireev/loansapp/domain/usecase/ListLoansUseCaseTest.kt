package ru.focusstart.kireev.loansapp.domain.usecase

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.repository.ListLoansRepository
import ru.focusstart.kireev.testrule.TestSchedulerRule

class ListLoansUseCaseTest {
    @get:Rule
    var testRule = TestSchedulerRule()

    @Test
    fun `on invoke EXPECT complete`() {
        val listLoan: List<Loan> = mock()
        val repository: ListLoansRepository = mock {
            on { getAllLoans() } doReturn  Single.just(listLoan)
        }
        val useCase = ListLoansUseCase(repository)

        useCase()
            .test()
            .assertComplete()
    }

    @Test
    fun `on invoke EXPECT get list loans`() {
        val listLoan: List<Loan> = mock()
        val repository: ListLoansRepository = mock {
            on { getAllLoans() } doReturn  Single.just(listLoan)
        }
        val useCase = ListLoansUseCase(repository)

        useCase()
            .test()
            .assertValue(listLoan)
    }
}