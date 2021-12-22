package com.github.welblade.workoutapp.domain

import com.github.welblade.workoutapp.core.UseCase
import com.github.welblade.workoutapp.data.model.Exercise
import com.github.welblade.workoutapp.data.repository.ExerciseListRepository
import kotlinx.coroutines.flow.Flow

class ExerciseListUseCase(
    private val exerciseListRepository: ExerciseListRepository
) : UseCase.NoParam<List<Exercise>>(){
    override suspend fun execute(): Flow<List<Exercise>> {
         return exerciseListRepository.listAll()
    }
}