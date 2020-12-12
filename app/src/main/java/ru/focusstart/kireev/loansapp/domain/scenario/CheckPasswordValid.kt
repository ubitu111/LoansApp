package ru.focusstart.kireev.loansapp.domain.scenario

class CheckPasswordValid {
    operator fun invoke(password: String): Boolean {
        return password.length > 6
    }
}