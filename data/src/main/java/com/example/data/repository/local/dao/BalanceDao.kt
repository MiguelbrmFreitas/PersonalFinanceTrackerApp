package com.example.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.repository.local.entity.BalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BalanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBalance(balanceEntity: BalanceEntity)

    @Query("DELETE FROM table_balance")
    suspend fun clearBalance()

    @Update
    suspend fun updateBalance(balanceEntity: BalanceEntity)

    @Query("SELECT * FROM table_balance")
    fun getBalance(): Flow<BalanceEntity>
}