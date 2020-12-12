package ru.focusstart.kireev.loansapp.domain.entity

enum class StateLoan(state: String) {
    REGISTERED("REGISTERED"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED")
}