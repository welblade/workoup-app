package com.github.welblade.workoutapp.domain.di

import com.github.welblade.workoutapp.domain.LogInUseCase
import com.github.welblade.workoutapp.domain.RegisterUserUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModules {
    fun load(){
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { LogInUseCase(get()) }
            factory { RegisterUserUseCase(get()) }
        }
    }
}