package com.example.drawer.data.utils

import android.content.Context
import com.example.drawer.data.model.Cita
import com.example.drawer.data.model.Paciente
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreManager(context: Context) {
    private val firestore = FirebaseFirestore.getInstance()

    private val auth = AuthManager(context)
    var userId = auth.getCurrentUser()?.uid

    suspend fun addCita(cita: Cita) {
        cita.userId = userId.toString()
        firestore.collection("citas").add(cita).await()
    }


    fun getCitasFlow(): Flow<List<Cita>> = callbackFlow {
        val citasRef = firestore.collection("citas")
            .whereEqualTo("userId", userId).orderBy("fecha")

        val subscription = citasRef.addSnapshotListener { snapshot, _ ->
            snapshot?.let { querySnapshot ->
                val citas = mutableListOf<Cita>()
                for (document in querySnapshot.documents) {
                    val cita = document.toObject(Cita::class.java)
                    cita?.id = document.id
                    cita?.let { citas.add(it) }
                }
                trySend(citas).isSuccess
            }
        }
        awaitClose { subscription.remove() }
    }


    //funciones Collection Paciente
    suspend fun addPaciente(paciente: Paciente) {
        paciente.userId = userId.toString()
        firestore.collection("pacientes").add(paciente).await()
    }


    fun getPacientesFlow(): Flow<List<Paciente>> = callbackFlow {
        val pacientesRef = firestore.collection("pacientes")
            .whereEqualTo("userId", userId)

        val subscription = pacientesRef.addSnapshotListener { snapshot, _ ->
            snapshot?.let { querySnapshot ->
                val pacientes = mutableListOf<Paciente>()
                for (document in querySnapshot.documents) {
                    val paciente = document.toObject(Paciente::class.java)
                    paciente?.id = document.id
                    paciente?.let { pacientes.add(it) }
                }
                trySend(pacientes).isSuccess
            }
        }
        awaitClose { subscription.remove() }
    }
}



