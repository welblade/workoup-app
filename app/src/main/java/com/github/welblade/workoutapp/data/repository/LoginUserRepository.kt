package com.github.welblade.workoutapp.data.repository

import com.github.welblade.workoutapp.data.model.LoggedUser
import kotlinx.coroutines.flow.Flow

interface LoginUserRepository {
    suspend fun login(email: String, password: String) : Flow<LoggedUser>
    suspend fun register(name: String, email: String, password: String) : Flow<LoggedUser>
    fun currentUser() : LoggedUser
    fun logout()
}