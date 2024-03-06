package com.example.data.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.repository.local.entity.PageWrapperEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PageWrapperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePageWrapper(pageWrapperEntity: PageWrapperEntity)

    @Query("DELETE FROM table_page_wrapper")
    suspend fun clearPageWrapper()

    @Update
    suspend fun updatePageWrapper(pageWrapperEntity: PageWrapperEntity)

    @Query("SELECT * FROM table_page_wrapper WHERE count = :count")
    fun getPageWrapper(count: Int): Flow<PageWrapperEntity>
}