package com.example.data.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.Transaction

@Entity(tableName = "table_transaction")
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val amount: Double,
    val currency: String,
) {
    fun toModel() = Transaction(
        id = id,
        title = title,
        amount = amount,
        currency = currency
    )
}

fun List<TransactionEntity>.toModel() =
    this.map {
        it.toModel()
    }.toList()
