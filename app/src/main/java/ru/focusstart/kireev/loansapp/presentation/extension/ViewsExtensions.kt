package ru.focusstart.kireev.loansapp.presentation.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

object ViewsExtensions {

    fun View.makeSnackbar(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
    }
}