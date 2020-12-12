package ru.focusstart.kireev.loansapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.domain.entity.Result
import ru.focusstart.kireev.loansapp.domain.usecase.LoginUseCase
import ru.focusstart.kireev.loansapp.domain.usecase.RegistrationUseCase
import ru.focusstart.kireev.testrule.TestSchedulerRule

class EntryViewModelTest {
    @get:Rule
    var testRule = TestSchedulerRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockResult: Result = mock()

    private val registrationUseCase: RegistrationUseCase = mock()
    private val loginUseCase: LoginUseCase = mock()

    private val viewModel = EntryViewModel(registrationUseCase, loginUseCase)

    @Test
    fun `on login EXPECT loading value is changing`() {
        `when`(loginUseCase.invoke(anyString(), anyString())).thenReturn(Single.just(mockResult))

        viewModel.login("", "")
        val actual = viewModel.loading.value

        assertEquals(false, actual)
    }

    @Test
    fun `on registration EXPECT loading value is changing`() {
        `when`(registrationUseCase.invoke(anyString(), anyString())).thenReturn(Single.just(mockResult))

        viewModel.registration("", "")
        val actual = viewModel.loading.value

        assertEquals(false, actual)
    }

    @Test
    fun `on success login EXPECT result success`() {
        `when`(loginUseCase.invoke(anyString(), anyString())).thenReturn(Single.just(Result.SUCCESS))

        viewModel.login(anyString(), anyString())
        val actual = viewModel.successLogin.value

        assertEquals(Result.SUCCESS, actual)
    }

    @Test
    fun `on success registration EXPECT result success`() {
        `when`(registrationUseCase.invoke(anyString(), anyString())).thenReturn(Single.just(Result.SUCCESS))

        viewModel.registration(anyString(), anyString())
        val actual = viewModel.successRegistration.value

        assertEquals(Result.SUCCESS, actual)
    }

    @Test
    fun `on login with not valid username EXPECT error value is error email`() {
        `when`(loginUseCase.invoke(anyString(), anyString())).thenReturn(Single.just(Result.LOGIN_ERROR))

        viewModel.login("","")
        val actual = viewModel.error.value

        assertEquals(R.string.error_email, actual)
    }

    @Test
    fun `on login with not valid password EXPECT error value is error password`() {
        `when`(loginUseCase.invoke(anyString(), anyString())).thenReturn(Single.just(Result.PASSWORD_ERROR))

        viewModel.login("","")
        val actual = viewModel.error.value

        assertEquals(R.string.error_password, actual)
    }

    @Test
    fun `on registration with not valid username EXPECT error value is error email`() {
        `when`(registrationUseCase.invoke(anyString(), anyString())).thenReturn(Single.just(Result.LOGIN_ERROR))

        viewModel.registration("","")
        val actual = viewModel.error.value

        assertEquals(R.string.error_email, actual)
    }

    @Test
    fun `on registration with not valid password EXPECT error value is error password`() {
        `when`(registrationUseCase.invoke(anyString(), anyString())).thenReturn(Single.just(Result.PASSWORD_ERROR))

        viewModel.registration("","")
        val actual = viewModel.error.value

        assertEquals(R.string.error_password, actual)
    }

    @Test
    fun `on failure login with error 403 EXPECT error value is error 403`() {
        `when`(loginUseCase.invoke(anyString(), anyString())).thenReturn(Single.error(Throwable("error 403")))

        viewModel.login(anyString(), anyString())
        val actual = viewModel.error.value

        assertEquals(R.string.error_403, actual)
    }

    @Test
    fun `on failure registration with error 403 EXPECT error value is error 403`() {
        `when`(registrationUseCase.invoke(anyString(), anyString())).thenReturn(Single.error(Throwable("error 403")))

        viewModel.registration(anyString(), anyString())
        val actual = viewModel.error.value

        assertEquals(R.string.error_403, actual)
    }

    @Test
    fun `on failure login with error 404 EXPECT error value is error 404`() {
        `when`(loginUseCase.invoke(anyString(), anyString())).thenReturn(Single.error(Throwable("error 404")))

        viewModel.login(anyString(), anyString())
        val actual = viewModel.error.value

        assertEquals(R.string.error_404, actual)
    }

    @Test
    fun `on failure registration with error 404 EXPECT error value is error 404`() {
        `when`(registrationUseCase.invoke(anyString(), anyString())).thenReturn(Single.error(Throwable("error 404")))

        viewModel.registration(anyString(), anyString())
        val actual = viewModel.error.value

        assertEquals(R.string.error_404, actual)
    }

    @Test
    fun `on failure login with error 504 EXPECT error value is error 504`() {
        `when`(loginUseCase.invoke(anyString(), anyString())).thenReturn(Single.error(Throwable("error 504")))

        viewModel.login(anyString(), anyString())
        val actual = viewModel.error.value

        assertEquals(R.string.error_504, actual)
    }

    @Test
    fun `on failure registration with error 504 EXPECT error value is error 504`() {
        `when`(registrationUseCase.invoke(anyString(), anyString())).thenReturn(Single.error(Throwable("error 504")))

        viewModel.registration(anyString(), anyString())
        val actual = viewModel.error.value

        assertEquals(R.string.error_504, actual)
    }

    @Test
    fun `on failure login if no internet EXPECT error value is error no internet`() {
        `when`(loginUseCase.invoke(anyString(), anyString())).thenReturn(Single.error(Throwable("No address associated with hostname")))

        viewModel.login(anyString(), anyString())
        val actual = viewModel.error.value

        assertEquals(R.string.error_no_internet, actual)
    }

    @Test
    fun `on failure registration if no internet EXPECT error value is error no internet`() {
        `when`(registrationUseCase.invoke(anyString(), anyString())).thenReturn(Single.error(Throwable("No address associated with hostname")))

        viewModel.registration(anyString(), anyString())
        val actual = viewModel.error.value

        assertEquals(R.string.error_no_internet, actual)
    }

    @Test
    fun `on failure login with other error EXPECT error value is error server not response`() {
        `when`(loginUseCase.invoke(anyString(), anyString())).thenReturn(Single.error(Throwable("other error")))

        viewModel.login(anyString(), anyString())
        val actual = viewModel.error.value

        assertEquals(R.string.error_server_not_response, actual)
    }

    @Test
    fun `on failure registration with other error EXPECT error value is error server not response`() {
        `when`(registrationUseCase.invoke(anyString(), anyString())).thenReturn(Single.error(Throwable("other error")))

        viewModel.registration(anyString(), anyString())
        val actual = viewModel.error.value

        assertEquals(R.string.error_server_not_response, actual)
    }
}