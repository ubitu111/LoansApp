package ru.focusstart.kireev.loansapp.domain.scenario

import androidx.core.util.PatternsCompat

class CheckLoginValid {
    operator fun invoke(login: String): Boolean {
        return if (login.isNotBlank() && login.contains('@')) {
            PatternsCompat.EMAIL_ADDRESS.matcher(login).matches()
        } else {
            false
        }
    }
}