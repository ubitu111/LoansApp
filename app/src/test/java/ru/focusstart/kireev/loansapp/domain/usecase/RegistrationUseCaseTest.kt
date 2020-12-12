package ru.focusstart.kireev.loansapp.domain.usecase

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Single
import org.junit.Rule
import org.junit.Test
import ru.focusstart.kireev.loansapp.domain.entity.Result
import ru.focusstart.kireev.loansapp.domain.repository.RegistrationRepository
import ru.focusstart.kireev.loansapp.domain.scenario.CheckLoginValid
import ru.focusstart.kireev.loansapp.domain.scenario.CheckPasswordValid
import ru.focusstart.kireev.testrule.TestSchedulerRule

class RegistrationUseCaseTest {
    @get:Rule
    var testRule = TestSchedulerRule()

    @Test
    fun `on invoke EXPECT complete`() {
        val repository: RegistrationRepository = mock()
        val passwordValidator: CheckPasswordValid = mock()
        val loginValidator: CheckLoginValid = mock()
        val useCase = RegistrationUseCase(repository, passwordValidator, loginValidator)

        useCase("", "")
            .test()
            .assertComplete()
    }

    @Test
    fun `on invoke with not valid password EXPECT get result password error`() {
        val loginValidator: CheckLoginValid = mock {
            on { invoke(any()) } doReturn true
        }
        val passwordValidator: CheckPasswordValid = mock {
            on { invoke(any()) } doReturn false
        }
        val repository: RegistrationRepository = mock()
        val useCase = RegistrationUseCase(repository, passwordValidator, loginValidator)

        useCase("","")
            .test()
            .assertValue(Result.PASSWORD_ERROR)
    }

    @Test
    fun `on invoke with not valid login EXPECT get result login error`() {
        val loginValidator: CheckLoginValid = mock {
            on { invoke(any()) } doReturn false
        }
        val passwordValidator: CheckPasswordValid = mock {
            on { invoke(any()) } doReturn true
        }
        val repository: RegistrationRepository = mock()
        val useCase = RegistrationUseCase(repository, passwordValidator, loginValidator)

        useCase("","")
            .test()
            .assertValue(Result.LOGIN_ERROR)
    }

    @Test
    fun `on invoke with valid password and login EXPECT get result success`() {
        val loginValidator: CheckLoginValid = mock {
            on { invoke(any()) } doReturn true
        }
        val passwordValidator: CheckPasswordValid = mock {
            on { invoke(any()) } doReturn true
        }
        val repository: RegistrationRepository = mock {
            on { registration(any(), any()) } doReturn Single.just(Result.SUCCESS)
        }
        val useCase = RegistrationUseCase(repository, passwordValidator, loginValidator)

        useCase("","")
            .test()
            .assertValue(Result.SUCCESS)
    }
}