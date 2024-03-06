package com.example.data.usecases

import com.example.data.repository.FinanceTrackingRepository

class GetBalanceUseCase(private val repository: FinanceTrackingRepository) {

    suspend operator fun invoke() = repository.getBalance()

}