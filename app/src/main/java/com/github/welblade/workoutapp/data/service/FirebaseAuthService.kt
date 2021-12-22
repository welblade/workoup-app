package com.github.welblade.workoutapp.data.service

import android.util.Log
import com.github.welblade.workoutapp.core.exception.LoginServiceException
import com.github.welblade.workoutapp.data.model.LoggedUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class FirebaseAuthService(
    private val firebaseAuth: FirebaseAuth
): LoginService {
    override suspend fun loginWithEmailAndPassword(email: String, password: String): Flow<LoggedUser> = flow {
        var loggedUser: LoggedUser? = null

        val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

        if(result is Throwable){
            throw result
        }

        result.user?.let {
            loggedUser = LoggedUser.fromFirebaseUser(it)
            emit(loggedUser!!)
        }
    }

    override suspend fun createUser(name: String, email: String, password: String) = flow {
        var createdLoggedUser: LoggedUser? = null
        val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        if(result is Throwable){
            throw result
        }
        val receivedUser = result.user!!
        setUserName(receivedUser, name)
        createdLoggedUser = LoggedUser.fromFirebaseUser(receivedUser, name)

        emit(createdLoggedUser)
    }

    private fun setUserName(user: FirebaseUser, name: String) {
        val request = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        runBlocking {
            user.updateProfile(request).addOnCompleteListener {
                if (!it.isSuccessful) {
                    throw it.exception!!
                }
            }
        }
    }

    override fun logout() = firebaseAuth.signOut()

    override fun getCurrentUser() = LoggedUser.fromFirebaseUser(firebaseAuth.currentUser!!)
}