package com.example.drawer.ui.navigation.navigationDrawer

import com.example.drawer.R

//https://www.flaticon.com/
sealed class AppScreen(var route: String, var icon: Int, var title: String) {
    object Home : AppScreen("home", R.drawable.home, "Inicio")
    object Perfil: AppScreen("Perfil", R.drawable.usuario, "Perfil")
    object Servicios : AppScreen("Servicios", R.drawable.feto, "Servicios")
    object Reserva : AppScreen("Reserva", R.drawable.reserva2, "Reserva tu cita")
    object Preguntas : AppScreen("Preguntas", R.drawable.pregunta, "Preguntas frecuentes")
    object Registrar : AppScreen("Registrar", R.drawable.add_circle_outline, "Registrar Servico"    )
    object ViewServicios : AppScreen("ViewServicios", R.drawable.edit, "Editar Servicios")


    //Ruta de Reservar Cita
    object AgendarCita: AppScreen("Agendar", 1, "Agendar")
    object AgregarInfo: AppScreen("AgregarInfo",1,"Agregar  Informacion")
    object DetalleCita: AppScreen("DetalleCita",1, "Detalle Cita")
    object SplashCheck: AppScreen("SplashCheck",1, "Splash Check")


    object VerCita: AppScreen("Ver", R.drawable.usuario, "Ver Cita")

}
