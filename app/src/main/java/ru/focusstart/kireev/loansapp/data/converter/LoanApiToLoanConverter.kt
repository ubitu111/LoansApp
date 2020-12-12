package ru.focusstart.kireev.loansapp.data.converter

import ru.focusstart.kireev.loansapp.data.model.LoanApi
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.entity.StateLoan
import java.text.SimpleDateFormat
import java.util.*

object LoanApiToLoanConverter {

    fun convert(loanApi: LoanApi): Loan {
        val fullName = "${loanApi.firstName} ${loanApi.lastName}"

        var outputDate = ""
        val cameDate = loanApi.date.split("T")[0]
        if (cameDate.isNotEmpty()) {
            val cameDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = cameDateFormat.parse(cameDate) ?: Date(cameDate)
            outputDate = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
        }


        val state = when (loanApi.state) {
            StateLoan.REJECTED.name -> StateLoan.REJECTED
            StateLoan.APPROVED.name -> StateLoan.APPROVED
            else -> StateLoan.REGISTERED
        }

        return Loan(
            loanApi.amount,
            outputDate,
            fullName,
            loanApi.id,
            loanApi.percent,
            loanApi.period,
            loanApi.phoneNumber,
            state
        )
    }
}