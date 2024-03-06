package com.example.data.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.Balance

@Entity(tableName = "table_balance")
data class BalanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val amount: Double,
    val currency: String
) {
    fun toModel() = Balance(
        amount = amount,
        currency = currency
    )
}

fun List<BalanceEntity>.toModel() =
    this.map { it.toModel() }
