package com.example.drawer.data.utils

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class CloudStorageManager {
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    fun getAdminStorageReference(): StorageReference {
        return storageRef.child("adminPhotos")
    }

    suspend fun uploadAdminFile(fileName: String, filePath: Uri) {
        val fileRef = getAdminStorageReference().child(fileName)
        val uploadTask = fileRef.putFile(filePath)
        uploadTask.await()
    }
}
