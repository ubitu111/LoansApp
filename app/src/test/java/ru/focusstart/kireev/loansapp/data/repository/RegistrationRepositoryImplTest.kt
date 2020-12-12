package ru.focusstart.kireev.loansapp.data.repository

import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.*
import ru.focusstart.kireev.loansapp.data.datasource.TokenDataSource
import ru.focusstart.kireev.loansapp.data.model.RegisteredUser
import ru.focusstart.kireev.loansapp.data.model.User
import ru.focusstart.kireev.loansapp.data.network.ApiService
import ru.focusstart.kireev.loansapp.domain.entity.Result

class RegistrationRepositoryImplTest {
    private val registeredUser: RegisteredUser = mock()

    private val api: ApiService = mock()
    private val dataSource: TokenDataSource = mock()

    private val repository = RegistrationRepositoryImpl(api, dataSource)

    @Test
    fun `on call registration EXPECT complete`() {
        `when`(api.registrationUser(anyOrNull())).thenReturn(Single.just(registeredUser))

        repository.registration("", "")
            .test()
            .assertComplete()
    }

    @Test
    fun `on call registration EXPECT result success`() {
        `when`(api.registrationUser(anyOrNull())).thenReturn(Single.just(registeredUser))

        repository.registration("", "")
            .test()
            .assertValue(Result.SUCCESS)
    }

    @Test
    fun `on call login EXPECT saves token`() {
        `when`(api.login(anyOrNull())).thenReturn(Single.just(anyString()))

        repository.login("", "")
            .test()

        verify(dataSource).saveToken(anyString())
    }

    @Test
    fun `on call login EXPECT result success`() {
        `when`(api.login(anyOrNull())).thenReturn(Single.just(anyString()))

        repository.login("", "")
            .test()
            .assertValue(Result.SUCCESS)
    }
}