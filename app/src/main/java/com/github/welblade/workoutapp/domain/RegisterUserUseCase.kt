package com.github.welblade.workoutapp.domain

import com.github.welblade.workoutapp.core.UseCase
import com.github.welblade.workoutapp.data.RegisterRequest
import com.github.welblade.workoutapp.data.model.LoggedUser
import com.github.welblade.workoutapp.data.repository.LoginUserRepository
import kotlinx.coroutines.flow.Flow

class RegisterUserUseCase(
    private val repository: LoginUserRepository
) : UseCase<RegisterRequest, LoggedUser>(){
    override suspend fun execute(param: RegisterRequest): Flow<LoggedUser> {
        return repository.register(param.name, param.email, param.password)
    }
}