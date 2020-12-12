package ru.focusstart.kireev.loansapp.data.repository

import io.reactivex.rxjava3.core.Single
import ru.focusstart.kireev.loansapp.data.datasource.TokenDataSource
import ru.focusstart.kireev.loansapp.data.model.User
import ru.focusstart.kireev.loansapp.data.network.ApiService
import ru.focusstart.kireev.loansapp.domain.entity.Result
import ru.focusstart.kireev.loansapp.domain.repository.RegistrationRepository

class RegistrationRepositoryImpl(
    private val apiService: ApiService,
    private val tokenDataSource: TokenDataSource
) : RegistrationRepository {

    override fun registration(username: String, password: String): Single<Result> {
        return apiService.registrationUser(User(username, password)).map {
            Result.SUCCESS
        }
    }

    override fun login(username: String, password: String): Single<Result> {
        return apiService.login(User(username, password)).map {
            tokenDataSource.saveToken(it)
            Result.SUCCESS
        }
    }

}