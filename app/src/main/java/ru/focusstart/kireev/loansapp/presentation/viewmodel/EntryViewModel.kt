package ru.focusstart.kireev.loansapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.domain.entity.Result
import ru.focusstart.kireev.loansapp.domain.usecase.LoginUseCase
import ru.focusstart.kireev.loansapp.domain.usecase.RegistrationUseCase
import ru.focusstart.kireev.loansapp.presentation.util.SingleLiveEvent

class EntryViewModel(
    private val registrationUseCase: RegistrationUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private var compositeDisposable = CompositeDisposable()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _successRegistration = SingleLiveEvent<Result>()
    val successRegistration: LiveData<Result>
        get() = _successRegistration

    private val _successLogin = SingleLiveEvent<Result>()
    val successLogin: LiveData<Result>
        get() = _successLogin

    private val _error = SingleLiveEvent<Int>()
    val error: LiveData<Int>
        get() = _error

    fun registration(username: String, password: String) {
        _loading.value = true
        compositeDisposable.add(
            registrationUseCase(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleRegistrationResult(it)
                }, {
                    handleThrowable(it)
                })
        )
    }

    fun login(username: String, password: String) {
        _loading.value = true
        compositeDisposable.add(
            loginUseCase(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleLoginResult(it)
                }, {
                    handleThrowable(it)
                })
        )
    }

    private fun handleRegistrationResult(result: Result) {
        _loading.value = false
        when (result) {
            Result.SUCCESS -> _successRegistration.value = result
            Result.LOGIN_ERROR -> _error.value = R.string.error_email
            Result.PASSWORD_ERROR -> _error.value = R.string.error_password
        }

    }

    private fun handleLoginResult(result: Result) {
        _loading.value = false
        when (result) {
            Result.SUCCESS -> _successLogin.value = result
            Result.LOGIN_ERROR -> _error.value = R.string.error_email
            Result.PASSWORD_ERROR -> _error.value = R.string.error_password
        }
    }

    private fun handleThrowable(t: Throwable) {
        _loading.value = false
        t.message?.let {
            val errorId = when {
                it.contains("400") -> R.string.error_400
                it.contains("403") -> R.string.error_403
                it.contains("404") -> R.string.error_404
                it.contains("504") -> R.string.error_504
                it.contains("No address associated with hostname") -> R.string.error_no_internet
                else -> R.string.error_server_not_response
            }
            _error.value = errorId
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}