package com.github.welblade.workoutapp.data.model

import com.google.firebase.Timestamp

data class Routine(
    val name: String = "",
    val description: String = "",
    val date: Timestamp = Timestamp.now(),
    val exercises: List<Exercise> = listOf()
)