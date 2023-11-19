package com.example.drawer.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


import android.util.Log

class ReservaViewModel : ViewModel() {
    val state = MutableLiveData(ReservaState())

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
