package com.example.drawer.data.model

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import com.example.drawer.data.utils.CloudStorageManager
import com.example.drawer.ui.screens.ScreenDrawer.ScreenAdmin.createImageFile
import kotlinx.coroutines.tasks.await

class ReservaViewModel(private val storage: CloudStorageManager) : ViewModel() {

    var selectedCita by mutableStateOf<Cita?>(null)

    var isNewImageSelected by mutableStateOf(false)

    //Almacena el servicio seleccionado - ViewServicios
    var selectedService by mutableStateOf<Servicios?>(null)

    //Almacena la imagen seleccionado - ViewServicios
    var originalImageUri by mutableStateOf<Uri?>(null)

    private val _selectedImage = MutableLiveData<Uri?>()
    val selectedImage: LiveData<Uri?> = _selectedImage

    // Función para seleccionar una imagen
    fun selectImage(uri: Uri) {
        _selectedImage.value = uri
    }

    fun clearSelectedImage() {
        _selectedImage.value = null
    }

    // Función para cargar una imagen a la nube
    suspend fun uploadImage(context: Context): String {
        var downloadUrl = ""
        _selectedImage.value?.let { uri ->
            val file = context.createImageFile()
            storage.uploadAdminFile(file.name, uri)
            downloadUrl = storage.getAdminStorageReference().child(file.name).downloadUrl.await().toString()
        }
        return downloadUrl
    }


    val state = MutableLiveData(ReservaState())

    fun changeEstadoCita(newEstado: EstadoCita) {
        selectedCita?.let { cita ->
            cita.estado = newEstado.name
            // Aquí actualizas la cita en tu base de datos o donde sea que la estés almacenando
            // Esto dependerá de cómo estés manejando tus datos
        }
    }
    fun changeServico(servicio: String) {
        state.value = state.value?.copy(nombreServicio = servicio)
        Log.d("ReservaViewModel", "nombreServicio cambiado a $servicio")
    }

    fun changeNombre(nombre: String) {
        state.value = state.value?.copy(nombre = nombre)
        Log.d("ReservaViewModel", "nombre cambiado a $nombre")
    }

    fun changeNumero(numero: String) {
        state.value = state.value?.copy(numero = numero)
        Log.d("ReservaViewModel", "numero cambiado a $numero")
    }

    fun changePrecio(precio: Double) {
        state.value = state.value?.copy(precio = precio)
        Log.d("ReservaViewModel", "precio cambiado a $precio")
    }

    fun changeFecha(fecha: String) {
        state.value = state.value?.copy(fecha = fecha)
        Log.d("ReservaViewModel", "fecha cambiada a $fecha")
    }

    fun changeHora(hora: String) {
        state.value = state.value?.copy(hora = hora)
        Log.d("ReservaViewModel", "hora cambiada a $hora")
    }

    fun changeNota(nota: String) {
        state.value = state.value?.copy(nota = nota)
        Log.d("ReservaViewModel", "nota cambiada a $nota")
    }

    fun changeMotivoCancelacion(motivoCancelacion: String) {
        state.value = state.value?.copy(motivoCancelacion = motivoCancelacion)
        Log.d("ReservaViewModel", "motivoCancelacion cambiado a $motivoCancelacion")
    }

    fun changeEstado(estado: EstadoCita) {
        state.value = state.value?.copy(estado = estado.name)
        Log.d("ReservaViewModel", "estado cambiado a ${estado.name}")
    }
}
