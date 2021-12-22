package com.github.welblade.workoutapp.domain

import com.github.welblade.workoutapp.core.UseCase
import com.github.welblade.workoutapp.data.model.Routine
import com.github.welblade.workoutapp.data.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow

class ListRoutinesUseCase(
    private val routineRepository: RoutineRepository
): UseCase.NoParam<List<Routine>>()
{
    override suspend fun execute(): Flow<List<Routine>> {
        return routineRepository.listAll()
    }
}