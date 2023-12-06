package com.example.drawer.data.utils

import android.content.Context
import android.util.Log
import com.example.drawer.data.model.Cita
import com.example.drawer.data.model.Paciente
import com.example.drawer.data.model.Servicios
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FirestoreManager(context: Context) {
    private val firestore = FirebaseFirestore.getInstance()

    private val auth = AuthManager(context)
    var userId = auth.getCurrentUser()?.uid

    //funciones Collection Citas
    suspend fun addCita(cita: Cita) {
        cita.userId = userId.toString()
        firestore.collection("citas").add(cita).await()
    }

    suspend fun updateCita(cita: Cita) {
        cita.id?.let { id ->
            firestore.collection("citas").document(id).set(cita).await()
        }
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

    fun getAllCitasFlow(): Flow<List<Cita>> = callbackFlow {
        val citasRef = firestore.collection("citas").orderBy("fecha")

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


    //funciones Collection Servicios
    suspend fun addServicio(servicio: Servicios) = coroutineScope {
        val docRef = firestore.collection("servicios").document(servicio.name)
        try {
            docRef.set(servicio).await()
            Log.d("AddServicio", "El servicio se agregó correctamente!")
        } catch (e: Exception) {
            Log.d("AddServicio", "Error al agregar el servicio", e)
        }
    }

    suspend fun updateServicio(servicio: Servicios) {
        val servicioRef = servicio.id?.let { firestore.collection("servicios").document(it) }
        servicioRef?.set(servicio)?.await()
    }

    suspend fun deleteServicio(servicioId: String) {
        val servicioRef = firestore.collection("servicios").document(servicioId)
        servicioRef.delete().await()
    }

    fun getServiciosFlow(): Flow<List<Servicios>> = callbackFlow {
        val serviciosRef = firestore.collection("servicios")

        val subscription = serviciosRef.addSnapshotListener { snapshot, _ ->
            snapshot?.let { querySnapshot ->
                val servicios = mutableListOf<Servicios>()
                for (document in querySnapshot.documents) {
                    val servicio = document.toObject(Servicios::class.java)
                    servicio?.id = document.id
                    servicio?.let { servicios.add(it) }
                }
                trySend(servicios).isSuccess
            }
        }
        awaitClose { subscription.remove() }
    }
}