package com.example.drawer.data.model

import java.util.UUID

data class Servicios(
    var id: String? = UUID.randomUUID().toString(),
    val name: String = "",
    val precio: Double? = 0.0,
    val descrip: String = "",
    val imageUrl: String = "",
)
