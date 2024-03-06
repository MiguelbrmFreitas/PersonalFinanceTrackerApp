package com.example.formulamoney.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.core.CustomResponse
import com.example.data.model.Balance
import com.example.formulamoney.R
import com.example.formulamoney.ext.showToast
import com.example.formulamoney.ext.toFormattedStringCurrency

@Composable
fun BalanceCard(customResponse: CustomResponse<Balance>) {
    Column(
        modifier = Modifier
            .padding(top = 28.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.account_balance_title),
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        when(customResponse) {
            is CustomResponse.Success -> {
                val balance = customResponse.result
                Text(
                    text = stringResource(id = R.string.account_balance_value,
                        balance.amount.toFormattedStringCurrency()
                    ),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            is CustomResponse.Loading -> {
                CircularProgressIndicator()
            }
            else -> {
                val context = LocalContext.current
                context.showToast(stringResource(id = R.string.error_message))
            }
        }
    }
}