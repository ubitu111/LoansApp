package ru.focusstart.kireev.loansapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.focusstart.kireev.loansapp.R
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.usecase.LoanDetailInfoUseCase
import ru.focusstart.kireev.loansapp.presentation.util.SingleLiveEvent

class DetailLoanInfoViewModel(
    private val loanDetailInfoUseCase: LoanDetailInfoUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _loan = SingleLiveEvent<Loan>()
    val loan: LiveData<Loan>
        get() = _loan

    private val _error = SingleLiveEvent<Int>()
    val error: LiveData<Int>
        get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun loadLoan(loanId: Long) {
        _loading.value = true
        compositeDisposable.add(
            loanDetailInfoUseCase(loanId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleSuccessLoading(it)
                }, {
                    handleError(it)
                })
        )
    }

    private fun handleSuccessLoading(loan: Loan) {
        _loading.value = false
        _loan.value = loan
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