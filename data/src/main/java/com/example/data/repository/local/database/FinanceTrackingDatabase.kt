package com.example.data.repository.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.repository.local.converters.PageWrapperConverters
import com.example.data.repository.local.dao.BalanceDao
import com.example.data.repository.local.dao.PageWrapperDao
import com.example.data.repository.local.entity.BalanceEntity
import com.example.data.repository.local.entity.PageWrapperEntity
import com.example.data.repository.local.entity.TransactionEntity

@Database(
    entities = [BalanceEntity::class, PageWrapperEntity::class, TransactionEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(PageWrapperConverters::class)
abstract class FinanceTrackingDatabase : RoomDatabase() {
    abstract fun balanceDao(): BalanceDao
    abstract fun pageWrapperDao(): PageWrapperDao

    companion object {

        @Volatile
        private var INSTANCE: FinanceTrackingDatabase? = null

        fun getInstance(context: Context): FinanceTrackingDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FinanceTrackingDatabase::class.java,
                        "finance_tracking_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                }

                return instance
            }
        }
    }
}