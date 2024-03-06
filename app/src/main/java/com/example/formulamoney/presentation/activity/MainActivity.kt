package com.example.formulamoney.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.formulamoney.presentation.components.MainPageScreen
import com.example.formulamoney.presentation.theme.FormulaMoneyTheme
import com.example.formulamoney.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val balanceResponse by mainViewModel.balance.collectAsState()
            val transactionListResponse by mainViewModel.transactions.collectAsState()

            FormulaMoneyTheme {
                MainPageScreen(
                    balanceResponse = balanceResponse,
                    transactionListResponse =transactionListResponse
                )
            }
        }
    }
}
