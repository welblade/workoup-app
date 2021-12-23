package com.github.welblade.workoutapp.data.di

import com.github.welblade.workoutapp.data.repository.*
import com.github.welblade.workoutapp.data.service.FirebaseAuthService
import com.github.welblade.workoutapp.data.service.LoginService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DataModules {
    fun load(){
        loadKoinModules(
            firebaseAuthModule()
            + repositoryModule()
            + serviceModule()
            + firestoreModule()
        )
    }

    private fun serviceModule(): Module {
        return module {
            single<LoginService> { FirebaseAuthService(get())  }
        }
    }

    private fun repositoryModule(): Module {
        return module{
            single<LoginUserRepository> { LoginUserRepositoryImpl(get()) }
            single<RoutineRepository> { RoutineRepositoryImpl(get(), get()) }
            single<ExerciseListRepository> { ExerciseListRepositoryImpl(get()) }
        }
    }

    private fun firebaseAuthModule(): Module {
        return module {
            single { FirebaseAuth.getInstance() }
        }
    }

    private fun firestoreModule(): Module {
        return module {
            single { Firebase.firestore }
        }
    }
}