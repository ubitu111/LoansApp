package ru.focusstart.kireev.loansapp.data.datasource

import android.content.Context

class TokenDataSourceImpl(context: Context) : TokenDataSource {
    private val prefsName = "tokens"
    private val preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        val editor = preferences.edit()
        editor.putString(prefsName, token)
        editor.apply()
    }

    override fun getToken(): String {
        return preferences.getString(prefsName, "") ?: ""
    }
}