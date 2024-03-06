package com.example.formulamoney.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.core.RemoteException
import com.example.data.core.CustomResponse
import com.example.data.model.Balance
import com.example.data.model.Transaction
import com.example.data.usecases.GetBalanceUseCase
import com.example.data.usecases.GetTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
): ViewModel() {

    private val _balance = MutableStateFlow<CustomResponse<Balance>>(value = CustomResponse.Loading)
    val balance = _balance.asStateFlow()

    private val _transactions = MutableStateFlow<CustomResponse<List<Transaction>>>(value = CustomResponse.Loading)
    val transactions = _transactions.asStateFlow()

    init {
        getBalance()
        getTransactions()
    }

    private fun getBalance() {
        viewModelScope.launch {
            getBalanceUseCase.invoke()
                .onStart {
                    _balance.value = CustomResponse.Loading
                }.catch {
                    with(RemoteException("Failed to connect")) {
                        _balance.value = CustomResponse.Error(this)
                    }
                }.collect {
                    it.data?.let {
                        _balance.value = CustomResponse.Success(it.toModel())
                    }
                }
        }
    }

    private fun getTransactions() {
        viewModelScope.launch {
            getTransactionsUseCase.invoke(limit = 10, page = 0)
                .onStart {
                    _transactions.value = CustomResponse.Loading
                }.catch {
                    with(RemoteException("Failed to connect")) {
                        _transactions.value = CustomResponse.Error(this)
                    }
                }.collect {
                    it.data?.let {
                        _transactions.value = CustomResponse.Success(it.toModel().transactions)
                    }
                }
        }
    }
}