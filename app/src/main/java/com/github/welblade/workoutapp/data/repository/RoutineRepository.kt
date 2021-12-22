package com.github.welblade.workoutapp.data.repository

import com.github.welblade.workoutapp.data.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    suspend fun listAll(): Flow<List<Routine>>
    suspend fun save(routine: Routine)
}