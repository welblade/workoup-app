package com.github.welblade.workoutapp.data.model

import com.google.firebase.auth.FirebaseUser

data class LoggedUser(
    val name: String?,
    val email: String,
    val idToken: String
){
    companion object {
        fun fromFirebaseUser(
            firebaseUser: FirebaseUser,
            name: String? = null
        ):LoggedUser {
            return LoggedUser(
                firebaseUser.displayName ?: name,
                firebaseUser.email!!,
                firebaseUser.getIdToken(false).toString()
            )
        }
    }
}
