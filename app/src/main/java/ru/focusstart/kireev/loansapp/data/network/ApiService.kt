package ru.focusstart.kireev.loansapp.data.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.*
import ru.focusstart.kireev.loansapp.data.model.*
import ru.focusstart.kireev.loansapp.domain.entity.Loan
import ru.focusstart.kireev.loansapp.domain.entity.LoanConditions
import ru.focusstart.kireev.loansapp.domain.entity.LoanRequest

interface ApiService {
    @POST("registration")
    fun registrationUser(
        @Body user: User
    ): Single<RegisteredUser>

    @POST("login")
    fun login(
        @Body user: User
    ): Single<String>

    @GET("loans/all")
    fun getAllLoans(
        @Header("Authorization") token: String
    ): Single<List<LoanApi>>

    @GET("loans/conditions")
    fun getLoansConditions(
        @Header("Authorization") token: String
    ): Single<LoanConditions>

    @POST("loans")
    fun createLoan(
        @Header("Authorization") token: String,
        @Body loanRequest: LoanRequest
    ): Single<LoanApi>

    @GET("loans/{id}")
    fun getLoanDetailInfo(
        @Header("Authorization") token: String,
        @Path("id") loanId: Long
    ): Single<LoanApi>
}