package com.example.drawer.ui.screens.ScreensPrincipal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.drawer.R
import com.example.drawer.ui.navigation.AppScreens
import com.example.drawer.data.utils.AuthManager
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(authManager: AuthManager, navController: NavHostController) {
    Scaffold {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.padding(it)) {
                BodyContent(navController, authManager)
            }
        }
    }
}

@Composable
fun BodyContent(navController: NavHostController, authManager: AuthManager) {
    val user: FirebaseUser? = authManager.getCurrentUser()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.fondo1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logoocentro),
                contentDescription = "logo splash",
                modifier = Modifier.size(135.dp)
            )
            Text(
                "RESERVA",
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color(159, 81, 202)
            )
            Text(
                "DESDE",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color(242, 53, 116)
            )
            Text(
                "cualquier lugar",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color(242, 53, 116)
            )
            Image(
                painter = painterResource(R.drawable.p1),
                contentDescription = "Check",
                modifier = Modifier.size(400.dp)
            )

            // Lanzamos un efecto que navega a la pantalla de inicio después de un Delay
            LaunchedEffect(Unit) {
                delay(3000)
                navController.navigate(
                    route = if (user == null) AppScreens.LoginScreen.route else AppScreens.DrawerScreen.route
                ) {
                    popUpTo(AppScreens.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }
}