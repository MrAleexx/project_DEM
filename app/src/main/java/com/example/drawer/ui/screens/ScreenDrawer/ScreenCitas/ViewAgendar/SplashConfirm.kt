package com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drawer.R
import kotlinx.coroutines.delay
import androidx.compose.animation.core.Animatable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.drawer.ui.navigation.navigationDrawer.AppScreen

@Composable
fun Splashcheck(navController: NavController) {
    val scale = remember { Animatable(0f) }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 3000,
                easing = FastOutSlowInEasing
            )
        )
        delay(3000L)
        navController.navigate(AppScreen.Reserva.route) {
            popUpTo(AppScreen.SplashCheck.route) {
                inclusive = true
            }
        }
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.check_circle_outline),
            contentDescription = "Check",
            tint = Color.Green,
            modifier = Modifier
                .size(200.dp)
                .scale(scale.value)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "GENIAL", fontWeight = FontWeight.Bold)
        Text(text = "¡ESTAS RESERVADO(A)!", fontWeight = FontWeight.Bold)
        Text(
            text = "Podras ver el resumen de tu cita \nen la sección VER CITA .",
            textAlign = TextAlign.Center
        )
    }
}
