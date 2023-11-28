package com.example.drawer.data.utils

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

fun FirebaseUser.saveToFirestore() {
    val firestore = FirebaseFirestore.getInstance()
    val docRef = firestore.collection("users").document(this.uid)

    docRef.get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                Log.d("SaveFireStore", "El documento ya existe!")
            } else {
                val userMap = hashMapOf(
                    "name" to this.displayName,
                    "email" to this.email,
                    "role" to "user"
                )
                docRef.set(userMap)
                    .addOnSuccessListener {
                        Log.d(
                            "SaveFireStore",
                            "El documento se escribió correctamente!"
                        )
                    }
                    .addOnFailureListener { e ->
                        Log.w(
                            "SaveFireStore",
                            "Error al escribir el documento",
                            e
                        )
                    }
            }
        }
        .addOnFailureListener { exception ->
            Log.d("SaveFireStore", "Error al obtener el documento", exception)
        }
}


fun FirebaseUser.getRoleFromFirestore(onComplete: (String) -> Unit) {
    val firestore = FirebaseFirestore.getInstance()
    firestore.collection("users")
        .document(this.uid)
        .get()
        .addOnSuccessListener { document ->
            val role = document.getString("role")
            role?.let { onComplete(it) }
        }
        .addOnFailureListener { e -> Log.w("GetRole", "Error al obtener el rol", e) }
}