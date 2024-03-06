package com.example.data.repository.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.PageWrapper

@Entity(tableName = "table_page_wrapper")
data class PageWrapperEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val total: Int,
    val count: Int,
    val transactions: List<TransactionEntity>,
    val last: Boolean
) {
    fun toModel() = PageWrapper(
        total = total,
        count = count,
        transactions = transactions.toModel(),
        last = last
    )
}
