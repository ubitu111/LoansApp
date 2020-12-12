package ru.focusstart.kireev.loansapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.domain.entity.LoanConditions
import ru.focusstart.kireev.loansapp.domain.entity.LoanRequest
import ru.focusstart.kireev.loansapp.domain.entity.Result
import ru.focusstart.kireev.loansapp.domain.usecase.GetLoanConditionsUseCase
import ru.focusstart.kireev.loansapp.domain.usecase.SendLoanUseCase
import ru.focusstart.kireev.loansapp.presentation.util.SingleLiveEvent

class CreateLoanViewModel(
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase,
    private val sendLoanUseCase: SendLoanUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _loanConditions = MutableLiveData<LoanConditions>()
    val loanConditions: LiveData<LoanConditions>
        get() = _loanConditions

    private val _result = SingleLiveEvent<Result>()
    val result: LiveData<Result>
        get() = _result

    private val _error = SingleLiveEvent<Int>()
    val error: LiveData<Int>
        get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun loadLoanConditions() {
        _loading.value = true
        compositeDisposable.add(
            getLoanConditionsUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleLoanConditions(it)
                }, {
                    handleError(it)
                })
        )
    }

    fun createLoan(loanRequest: LoanRequest) {
        _loading.value = true
        compositeDisposable.add(
            sendLoanUseCase(loanRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleSuccessCreationLoan(it)
                }, {
                    handleError(it)
                })
        )
    }

    private fun handleSuccessCreationLoan(result: Result) {
        _loading.value = false
        _result.value = result
    }

    private fun handleLoanConditions(loanConditions: LoanConditions) {
        _loading.value = false
        _loanConditions.value = loanConditions
    }

    private fun handleError(t: Throwable) {
        _loading.value = false
        t.message?.let {
            val errorId = when {
                it.contains("401") -> R.string.error_401
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