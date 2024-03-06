package com.example.data.di

import com.example.data.repository.FinanceTrackingRepository
import com.example.data.repository.local.dao.BalanceDao
import com.example.data.repository.local.dao.PageWrapperDao
import com.example.data.repository.local.database.FinanceTrackingDatabase
import com.example.data.repository.remote.FinanceTrackingService
import com.example.data.usecases.GetBalanceUseCase
import com.example.data.usecases.GetTransactionsUseCase
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

//fun Scope.db() = get<FinanceTrackingDatabase>()

object DataModuleProvider {

    private const val TIME_OUT = 30L
    private const val BASE_URL = "https://8kq890lk50.execute-api.us-east-1.amazonaws.com/prd/accounts/0172bd23-c0da-47d0-a4e0-53a3ad40828f/"

    fun loadModules() {
        loadKoinModules(
            remoteModule +
            repositoryModule +
            databaseModule +
            useCasesModule
        )
    }

    val remoteModule = module {
        single { buildApiService(get()) }

        single { buildRetrofit(get(), BASE_URL) }

        single { buildLoggingInterceptor() }

        single { buildHttpClient(get()) }
    }

    val databaseModule = module {
        single { FinanceTrackingDatabase.getInstance(androidContext()) }
    }

    val repositoryModule = module {
        single { buildRepository(
            apiService = get(),
            financeTrackingDatabase = get()
        ) }
    }

    val useCasesModule = module {
        single { GetBalanceUseCase(get()) }
        single { GetTransactionsUseCase(get()) }
    }

    private fun buildRetrofit(okHttpClient: OkHttpClient, url: String): Retrofit {
        val moshi by lazy {
            val moshiBuilder = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
            moshiBuilder.build()
        }

        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    private fun buildHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private fun buildApiService(retrofit: Retrofit): FinanceTrackingService {
        return retrofit.create(FinanceTrackingService::class.java)
    }

    private fun buildRepository(
        apiService: FinanceTrackingService,
        financeTrackingDatabase: FinanceTrackingDatabase
    ) = FinanceTrackingRepository(
        apiService = apiService,
        financeTrackingDatabase = financeTrackingDatabase
    )

    private fun buildLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

}