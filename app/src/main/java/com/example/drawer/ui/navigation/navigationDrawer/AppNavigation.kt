package com.example.drawer.ui.navigation.navigationDrawer

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.drawer.data.model.ReservaViewModel
import com.example.drawer.ui.screens.ScreenDrawer.HomeScreen
import com.example.drawer.ui.screens.ScreenDrawer.Perfil
import com.example.drawer.ui.screens.ScreenDrawer.Preguntas
import com.example.drawer.ui.screens.ScreenDrawer.Reserva
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewCitas.VerCita
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.AgregarInfo
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.DetallesCita
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.SelecionarCita
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.Splashcheck
import com.example.drawer.ui.screens.ScreenDrawer.Servicios
import com.example.drawer.data.utils.AuthManager
import com.example.drawer.data.utils.FirestoreManager


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(navController: NavHostController, auth: AuthManager, context: Context, navigation: NavController) {
    val scope = rememberCoroutineScope()
    NavHost(navController, startDestination = AppScreen.Reserva.route) {
        composable(AppScreen.Home.route) {
            HomeScreen()
        }
        composable(AppScreen.Perfil.route) {
            Perfil(auth, navigation)
        }
        composable(AppScreen.Servicios.route) {
            Servicios(navController)
        }
        composable(AppScreen.Reserva.route) {
            Reserva(navController, auth)
        }
        composable(AppScreen.Preguntas.route) {
            Preguntas()
        }
        val viewModelR = ReservaViewModel()
        val firestore = FirestoreManager(context)

        //Navegacion de Reservar Cita
        composable(AppScreen.AgendarCita.route) {
            SelecionarCita( navController, viewModelR)
        }
        composable(AppScreen.AgregarInfo.route) {
            AgregarInfo( navController, viewModelR)
        }
        composable(AppScreen.DetalleCita.route) {
            DetallesCita(navController, firestore, scope, viewModelR)
        }

        composable(AppScreen.SplashCheck.route) {
            Splashcheck(navController)
        }

        //Navegacion de ver citas
        composable(AppScreen.VerCita.route) {
            VerCita(firestore)
        }
    }
}
