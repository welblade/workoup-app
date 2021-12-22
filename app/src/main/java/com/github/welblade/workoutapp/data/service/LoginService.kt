package com.github.welblade.workoutapp.data.service

import com.github.welblade.workoutapp.data.model.LoggedUser
import kotlinx.coroutines.flow.Flow

interface LoginService {
    suspend fun loginWithEmailAndPassword(email: String, password: String): Flow<LoggedUser>
    suspend fun createUser(name: String, email: String, password: String): Flow<LoggedUser>
    fun logout()
    fun getCurrentUser(): LoggedUser
}