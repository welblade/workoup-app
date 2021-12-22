package com.github.welblade.workoutapp.data.repository

import com.github.welblade.workoutapp.data.model.Exercise
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ExerciseListRepositoryImpl(
    private val db: FirebaseFirestore
) : ExerciseListRepository {
    override suspend fun listAll(): Flow<List<Exercise>> = flow {
        val snapshot = db.collection("exercises")
            .get()
            .await()
        snapshot.documents.map {
                document -> document.toObject<Exercise>()!!
        }.let {
            emit(it)
        }
    }
}