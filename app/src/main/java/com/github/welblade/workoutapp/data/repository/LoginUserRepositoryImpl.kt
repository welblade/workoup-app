package com.github.welblade.workoutapp.data.repository

import com.github.welblade.workoutapp.data.service.LoginService

class LoginUserRepositoryImpl(
    private val loginService: LoginService
): LoginUserRepository {
    override suspend fun login(email: String, password: String)
        = loginService.loginWithEmailAndPassword(email, password)

    override suspend fun register(name: String, email: String, password: String)
        = loginService.createUser(name, email, password)

    override fun currentUser() = loginService.getCurrentUser()

    override fun logout() = loginService.logout()
}