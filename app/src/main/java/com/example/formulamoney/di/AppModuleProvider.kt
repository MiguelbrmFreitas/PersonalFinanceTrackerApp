package com.example.formulamoney.di

import com.example.formulamoney.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object AppModuleProvider {

    fun loadModules() {
        loadKoinModules(viewModelModule)
    }

    val viewModelModule = module {
        viewModel { MainViewModel(get(), get()) }
    }

}