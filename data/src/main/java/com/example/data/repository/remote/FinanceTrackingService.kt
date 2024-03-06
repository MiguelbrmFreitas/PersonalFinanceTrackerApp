package com.example.data.repository.remote

import com.example.data.model.Balance
import com.example.data.model.PageWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface FinanceTrackingService {
    @GET("balance")
    suspend fun getBalance(): Balance

    @GET("transactions")
    suspend fun getTransactions(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): PageWrapper
}