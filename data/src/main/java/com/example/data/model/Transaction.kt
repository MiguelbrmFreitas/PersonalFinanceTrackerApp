package com.example.data.model

import com.example.data.repository.local.entity.TransactionEntity

data class Transaction(
    val title: String,
    val amount: Double,
    val currency: String,
    val id: String
) {
    fun toEntity() = TransactionEntity(
        title = title,
        amount = amount,
        currency = currency,
        id = id
    )
}

fun List<Transaction>.toEntity() =
    this.map {
        it.toEntity()
    }