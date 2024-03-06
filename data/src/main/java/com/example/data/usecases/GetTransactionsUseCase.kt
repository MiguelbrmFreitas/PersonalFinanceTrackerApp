package com.example.data.usecases

import com.example.data.repository.FinanceTrackingRepository

class GetTransactionsUseCase(private val repository: FinanceTrackingRepository) {

    suspend operator fun invoke(
        limit: Int,
        page: Int
    ) = repository.getTransactions(
        limit = limit,
        page = page
    )

}