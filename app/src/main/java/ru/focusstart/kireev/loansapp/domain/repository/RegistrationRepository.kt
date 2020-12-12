package ru.focusstart.kireev.loansapp.domain.repository

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.domain.entity.Result

interface RegistrationRepository {
    fun registration(username: String, password: String) : Single<Result>
    fun login(username: String, password: String) : Single<Result>
}