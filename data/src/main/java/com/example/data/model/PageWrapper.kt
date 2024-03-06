package com.example.data.model

import com.example.data.repository.local.entity.PageWrapperEntity
import com.squareup.moshi.Json

data class PageWrapper(
    val total: Int,
    val count: Int,
    @Json(name = "data") val transactions: List<Transaction>,
    val last: Boolean
) {
    fun toEntity() = PageWrapperEntity(
        total = total,
        count = count,
        transactions = transactions.toEntity(),
        last = last
    )
}


