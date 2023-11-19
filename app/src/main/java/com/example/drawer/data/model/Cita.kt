package com.example.drawer.data.model

data class Cita(
    var id: String? = null,
    var userId: String = "",
    val servicio: String = "",
    val fecha: String = "",
    val hora: String = "",
    val precio: Double = 0.0,
    var estado: String = EstadoCita.PROCESO.name,
    val nota: String? = "",
    val motivoCancelacion: String? = ""
)

enum class EstadoCita {
    PROCESO,
    CANCELADA,
    CONCLUIDA
}
