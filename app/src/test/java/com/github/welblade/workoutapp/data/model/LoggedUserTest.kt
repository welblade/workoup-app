package com.github.welblade.workoutapp.data.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class LoggedUserTest {
    private val user = LoggedUser("nome", "email", "uhasuajkjmnasudi")
    @Test
    fun getName() {
        assertEquals("nome", user.name)
    }

    @Test
    fun getEmail() {
        assertEquals("email", user.email)
    }

    @Test
    fun getIdToken() {
        assertEquals("uhasuajkjmnasudi", user.idToken)
    }
}