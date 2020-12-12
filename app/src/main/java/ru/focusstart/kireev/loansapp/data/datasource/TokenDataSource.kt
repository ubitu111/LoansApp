package ru.focusstart.kireev.loansapp.data.datasource

interface TokenDataSource {
    fun saveToken(token: String)
    fun getToken(): String
}