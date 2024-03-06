package com.example.data.remote

import com.example.data.repository.remote.FinanceTrackingService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.test.assertEquals

class FinanceTrackingServiceTest {

    lateinit var mockWebServer: MockWebServer
    lateinit var apiService: FinanceTrackingService

    private inline fun <reified T> loadFileText(
        caller: T,
        filePath: String
    ): String =
        T::class.java.getResource(filePath)?.readText() ?: throw IllegalArgumentException(
            "$filePath not found. Check if it's on correct resources folder for $caller"
        )

    @Before
    fun setup() {
        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(FinanceTrackingService::class.java)
    }

    @After
    fun finishServer() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Should hit expected endpoint when calling getBalance() from apiService`() {
        runBlocking {
            // When
            mockWebServer.enqueue(MockResponse().setBody(loadFileText(this, "/balance.json")))

            apiService.getBalance()
            val request = mockWebServer.takeRequest()

            // Then
            assertEquals(request.path, "/balance")
        }
    }

    @Test
    fun `Should hit expected endpoint when calling getTransactions() from apiServic`() {
        runBlocking {
            // When
            mockWebServer.enqueue(MockResponse().setBody(loadFileText(this, "/transactions.json")))

            apiService.getTransactions(
                limit = 10,
                page = 0
            )
            val request = mockWebServer.takeRequest()

            // Then
            assertEquals(request.path, "/transactions?limit=10&page=0")
        }
    }

}