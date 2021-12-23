package com.github.welblade.workoutapp.data.repository

import com.github.welblade.workoutapp.core.extension.getDateOnly
import com.github.welblade.workoutapp.data.model.Routine
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*

class RoutineRepositoryImpl(
    private val db: FirebaseFirestore,
    firebaseAuth: FirebaseAuth
): RoutineRepository {
    private val userId = firebaseAuth.currentUser!!.uid
    private val collectionPath = "workout/$userId/routines"
    override suspend fun listAll(): Flow<List<Routine>>  = flow {
        val snapshot = db.collection(collectionPath)
            .whereGreaterThanOrEqualTo("date", Timestamp(Date().getDateOnly()))
            .orderBy("date")
            .get()
            .await()
        snapshot.documents.map {
            document -> document.toObject<Routine>()!!
        }.let {
            emit(it)
        }
    }

    override suspend fun save(routine: Routine) {
        db.collection(collectionPath).add(routine)
    }
}