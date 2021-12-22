package com.github.welblade.workoutapp.domain

import com.github.welblade.workoutapp.core.UseCase
import com.github.welblade.workoutapp.data.model.Routine
import com.github.welblade.workoutapp.data.repository.RoutineRepository
import kotlinx.coroutines.flow.flow

class SaveRoutineUseCase(
    private val routineRepository: RoutineRepository
) : UseCase.NoSource<Routine>(){
    override suspend fun execute(param: Routine) = flow {
        routineRepository.save(param)
        emit(Unit)
    }
}