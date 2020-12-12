package ru.focusstart.kireev.loansapp.domain.entity

data class Loan(
    val amount: Int,
    val date: String,
    val fullName: String,
    val id: Long,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: StateLoan
)