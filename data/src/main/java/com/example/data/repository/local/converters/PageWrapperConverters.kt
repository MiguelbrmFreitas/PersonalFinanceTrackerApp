package com.example.data.repository.local.converters

import androidx.room.TypeConverter
import com.example.data.repository.local.entity.TransactionEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class PageWrapperConverters {

    @TypeConverter
    fun fromJsonString(jsonString: String): List<TransactionEntity>? {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(Array<TransactionEntity>::class.java)

        return jsonAdapter.fromJson(jsonString)?.toList()
    }

    @TypeConverter
    fun toJsonString(transactions: List<TransactionEntity>): String? {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(Array<TransactionEntity>::class.java)

        return jsonAdapter.toJson(transactions.toTypedArray())
    }
}