package ru.focusstart.kireev.loansapp.domain.entity

data class LoanConditions(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
)
