package com.example.formulamoney.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.Transaction
import com.example.formulamoney.ext.toFormattedTransactionItem
import com.example.formulamoney.presentation.theme.LineDividerColor

@Composable
fun TransactionCard(transaction: Transaction) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = transaction.title,
                fontWeight = FontWeight.Bold
            )
            Text(text = transaction.amount.toFormattedTransactionItem())
        }
        Divider(
            color = LineDividerColor,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}