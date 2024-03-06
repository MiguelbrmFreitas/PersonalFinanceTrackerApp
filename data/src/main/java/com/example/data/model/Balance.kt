package com.example.data.model

import com.example.data.repository.local.entity.BalanceEntity

data class Balance (
    val amount: Double,
    val currency: String
) {
    fun toEntity() = BalanceEntity(
        amount = amount,
        currency = currency
    )
}