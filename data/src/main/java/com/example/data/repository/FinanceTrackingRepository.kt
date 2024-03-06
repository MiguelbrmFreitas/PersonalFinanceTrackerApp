package com.example.data.repository

import com.example.data.core.RemoteException
import com.example.data.core.Resource
import com.example.data.core.networkBoundResource
import com.example.data.repository.local.database.FinanceTrackingDatabase
import com.example.data.repository.local.entity.BalanceEntity
import com.example.data.repository.local.entity.PageWrapperEntity
import com.example.data.repository.remote.FinanceTrackingService
import kotlinx.coroutines.flow.Flow

class FinanceTrackingRepository(
    private val apiService: FinanceTrackingService,
    private val financeTrackingDatabase: FinanceTrackingDatabase
) {

    companion object {
        const val DEFAULT_LIMIT = 10
        const val DEFAULT_PAGE = 0
    }

    private val getBalanceFromDatabase: () -> Flow<BalanceEntity> = {
        financeTrackingDatabase.balanceDao().getBalance()
    }

    private val getTransactionsFromDatabase: () -> Flow<PageWrapperEntity> = {
        financeTrackingDatabase.pageWrapperDao().getPageWrapper(10)
    }

    private val clearAndUpdateBalance: suspend (BalanceEntity) -> Unit = {
        financeTrackingDatabase.balanceDao().clearBalance()
        financeTrackingDatabase.balanceDao().saveBalance(it)
    }

    private val clearAndUpdateTransactions: suspend (PageWrapperEntity) -> Unit = {
        financeTrackingDatabase.pageWrapperDao().clearPageWrapper()
        financeTrackingDatabase.pageWrapperDao().savePageWrapper(it)
    }

    suspend fun getBalance(): Flow<Resource<BalanceEntity>> = networkBoundResource(
        query = { getBalanceFromDatabase() },
        fetch = { apiService.getBalance() },
        saveFetchResult = {
            clearAndUpdateBalance(it.toEntity())
        },
        onError = { RemoteException("/balance request failed") }
    )

    suspend fun getTransactions(
        limit: Int,
        page: Int
    ): Flow<Resource<PageWrapperEntity>> = networkBoundResource(
        query = { getTransactionsFromDatabase() },
        fetch = { apiService.getTransactions(
            limit = limit,
            page = page
        )},
        saveFetchResult = {
            clearAndUpdateTransactions(it.toEntity())
        },
        onError = { RemoteException("/transaction request failed") }
    )

}