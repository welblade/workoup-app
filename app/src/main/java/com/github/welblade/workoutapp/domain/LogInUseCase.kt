package com.github.welblade.workoutapp.domain

import com.github.welblade.workoutapp.core.UseCase
import com.github.welblade.workoutapp.data.LoginRequest
import com.github.welblade.workoutapp.data.model.LoggedUser
import com.github.welblade.workoutapp.data.repository.LoginUserRepository
import kotlinx.coroutines.flow.Flow

class LogInUseCase(
    private val repository: LoginUserRepository
): UseCase<LoginRequest, LoggedUser>() {

    override suspend fun execute(param: LoginRequest): Flow<LoggedUser> {
        return repository.login(param.email, param.password)
    }
}