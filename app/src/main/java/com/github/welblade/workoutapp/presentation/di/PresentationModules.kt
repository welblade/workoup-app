package com.github.welblade.workoutapp.presentation.di

import com.github.welblade.workoutapp.presentation.ui.login.LoginViewModel
import com.github.welblade.workoutapp.presentation.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModules {
    fun load(){
        loadKoinModules(viewModelModules())
    }

    private fun viewModelModules(): Module {
        return module {
            viewModel { LoginViewModel(get())}
            viewModel { RegisterViewModel(get()) }
        }
    }
}