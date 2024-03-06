package com.example.data.usecases

import com.example.data.helpers.TransactionsTestHelper.pageWrapperMock
import com.example.data.repository.FinanceTrackingRepository
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class GetTransctionsUseCaseTest {

    private val repository = mockk<FinanceTrackingRepository>(relaxed = true)
    private val useCase = GetTransactionsUseCase(repository)

    @Test
    fun `should return expected PageWrapper object`() {
        runBlocking {
            // Given
            val expected = pageWrapperMock

            // When
            val result = useCase.invoke(
                limit = 10,
                page = 0
            )

            // Then
            result.collect {
                assertEquals(expected, it.data)
            }
        }
    }
}