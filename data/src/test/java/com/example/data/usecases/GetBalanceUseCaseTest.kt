package com.example.data.usecases

import com.example.data.helpers.BalanceTestHelper.balanceMock
import com.example.data.repository.FinanceTrackingRepository
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class GetBalanceUseCaseTest {

    private val repository = mockk<FinanceTrackingRepository>(relaxed = true)
    private val useCase = GetBalanceUseCase(repository)

    @Test
    fun `should return expected Balance object`() {
        runBlocking {
            // Given
            val expected = balanceMock

            // When
            val result = useCase.invoke()

            // Then
            result.collect {
                assertEquals(expected, it.data)
            }
        }
    }

}