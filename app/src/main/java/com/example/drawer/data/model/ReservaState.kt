package com.example.drawer.data.model

data class ReservaState(
    var nombreServicio: String = "",
    var nombre: String = "",
    val numero: String = "",
    val precio: Double = 0.0,
    val fecha: String = "",
    val hora: String = "",
    val nota: String = "",
    val motivoCancelacion: String = "",
    val estado: String = EstadoCita.PROCESO.name
)