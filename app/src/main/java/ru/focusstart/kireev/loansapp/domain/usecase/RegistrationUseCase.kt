package ru.focusstart.kireev.loansapp.domain.usecase

import android.util.Patterns
import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.domain.entity.Result
import ru.focusstart.kireev.loansapp.domain.repository.RegistrationRepository
import ru.focusstart.kireev.loansapp.domain.scenario.CheckLoginValid
import ru.focusstart.kireev.loansapp.domain.scenario.CheckPasswordValid

class RegistrationUseCase(
    private val registrationRepository: RegistrationRepository,
    private val checkPasswordValid: CheckPasswordValid,
    private val checkLoginValid: CheckLoginValid
) {

    operator fun invoke(
        username: String,
        password: String
    ): Single<Result> {
        return if (!checkLoginValid(username)) {
            Single.just(Result.LOGIN_ERROR)
        } else if (!checkPasswordValid(password)) {
            Single.just(Result.PASSWORD_ERROR)
        } else {
            registrationRepository.registration(username, password)
        }
    }
}