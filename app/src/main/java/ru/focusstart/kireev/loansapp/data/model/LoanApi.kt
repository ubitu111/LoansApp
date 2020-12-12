package ru.focusstart.kireev.loansapp.data.model

data class LoanApi(
    val amount: Int,
    val date: String,
    val firstName: String,
    val id: Long,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: String
)