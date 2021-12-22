package com.github.welblade.workoutapp.presentation.common

interface AuthListener {
        fun onStarted()
        fun onSuccess()
        fun onFailure(message: String)
}