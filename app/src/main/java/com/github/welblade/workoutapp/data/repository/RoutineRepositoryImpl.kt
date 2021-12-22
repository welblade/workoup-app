package com.github.welblade.workoutapp.data.repository

import com.github.welblade.workoutapp.data.model.Routine
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class RoutineRepositoryImpl(
    private val db: FirebaseFirestore
): RoutineRepository {
    override suspend fun listAll(): Flow<List<Routine>>  = flow {
        val snapshot = db.collection("routines")
            .get()
            .await()
        snapshot.documents.map {
            document -> document.toObject<Routine>()!!
        }.let {
            emit(it)
        }
    }

    override suspend fun save(routine: Routine) {
        db.collection("routines").add(routine)
    }
}