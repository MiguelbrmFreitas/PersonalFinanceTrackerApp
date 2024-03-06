package com.example.data.helpers

import com.example.data.model.PageWrapper
import com.example.data.model.Transaction

object TransactionsTestHelper {

    val transactionMock = Transaction(
        title = "Shell Gasoline",
        amount = 12.3,
        currency= "USD",
        id = "4995a89c-af30-41c0-9754-a3bb4a34fe66"
    )

    val pageWrapperMock = PageWrapper(
        total = 1,
        count = 1,
        last = false,
        transactions = listOf(transactionMock)
    )

}