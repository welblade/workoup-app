package com.github.welblade.workoutapp.data.model

import com.google.firebase.Timestamp

data class Routine(
    private val name: String,
    private val description: String,
    private val date: Timestamp,
    private val exercises: List<Exercise>
)