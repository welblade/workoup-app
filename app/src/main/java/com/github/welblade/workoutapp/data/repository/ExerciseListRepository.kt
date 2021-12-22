package com.github.welblade.workoutapp.data.repository

import com.github.welblade.workoutapp.data.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseListRepository {
    suspend fun listAll(): Flow<List<Exercise>>
}