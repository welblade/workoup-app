package com.github.welblade.workoutapp.data.model

import com.google.firebase.Timestamp

data class Routine(
    val name: String,
    val description: String,
    val date: Timestamp,
    val exercises: List<Exercise>
)