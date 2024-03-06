package com.example.formulamoney.presentation.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.data.core.CustomResponse
import com.example.data.model.Transaction
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.formulamoney.R
import com.example.formulamoney.ext.showToast

@Composable
fun TransactionListCard(customResponse: CustomResponse<List<Transaction>>) {

    when(customResponse) {
        is CustomResponse.Success -> {
            val transactionList = customResponse.result
            LazyColumn(
                Modifier
                    .padding(top = 32.dp)
                    .fillMaxHeight()
            ) {
                items(transactionList) { transaction ->
                    TransactionCard(transaction)
                }
            }
        }
        is CustomResponse.Loading -> {
            CircularProgressIndicator()
        }
        is CustomResponse.Error -> {
            val context = LocalContext.current
            context.showToast(stringResource(id = R.string.error_message))
        }
    }


}