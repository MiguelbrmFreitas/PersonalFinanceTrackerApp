package com.example.formulamoney.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.data.core.CustomResponse
import com.example.data.model.Balance
import com.example.data.model.Transaction

@Composable
fun MainPageScreen(
    balanceResponse: CustomResponse<Balance>,
    transactionListResponse: CustomResponse<List<Transaction>>
) {
    Surface(
        Modifier.background(Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(horizontal = 28.dp)
        ) {
            BalanceCard(customResponse = balanceResponse)
            TransactionListCard(customResponse = transactionListResponse)
        }
    }
}