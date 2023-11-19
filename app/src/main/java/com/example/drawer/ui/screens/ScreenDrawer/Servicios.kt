package com.example.drawer.ui.screens.ScreenDrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drawer.R
import com.example.drawer.ui.navigation.navigationDrawer.AppScreen
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.Emcabezado

data class Servicio(val name: String, val price: String, val descripcion: String, val imagenId: Int)

@Composable
fun Servicios(navController: NavController) {
    val servicios = listOf(
        Servicio(
            "ECOGRAFÍAS OBSTÉTRICAS",
            "S/.45.00",
            "Examen clínico para establecer la presencia de un embrión/feto con vida y estimar el tiempo de gestación del embarazo.",
            R.drawable.ecobstetrica
        ),
        Servicio(
            "ECOGRAFÍAS TRANSVAGINALES",
            "S/.50.00",
            "Evalúa los órganos reproductores femeninos y detecta el desarrollo del feto en el útero en una etapa más temprana.",
            R.drawable.ecotransvaginal
        ),
        Servicio(
            "CONTROL PRENATAL",
            "S/.30.00",
            "Garantiza la seguridad de tu bebé y reduce el riesgo de complicaciones.",
            R.drawable.controlpre
        ),
        Servicio(
            "TEST DE EMBARAZO",
            "S/.20.00",
            "Obtén resultados instantáneos y precisos, ¡en solo 10 minutos!",
            R.drawable.testemb
        ),
        Servicio(
            "PAPANICOLAU",
            "S/.45.00",
            "Detecta y previene a tiempo problemas de salud como el cáncer cervicouterino.",
            R.drawable.papanicolau
        ),
        Servicio(
            "RETIRO DE IMPLANTE",
            "S/.30.00",
            "Quita el pequeño dispositivo colocado bajo la piel para dejar de prevenir el embarazo.",
            R.drawable.implante
        ),
        Servicio(
            "PLANIFICACIÓN FAMILIAR",
            "S/.60.00",
            "Ayuda a las parejas a poder elegir cuándo tener hijos, cuántos y apoyo para tomar decisiones informadas sobre su salud reproductiva.",
            R.drawable.planifamiliar
        )
    )

    var servicioExpandido by remember { mutableStateOf(-1) }


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(30.dp))
            Emcabezado(text = "Servicios")
        }

        items(servicios.size) { index ->
            val servicio = servicios[index]

            Spacer(modifier = Modifier.height(15.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable {
                        servicioExpandido = if (servicioExpandido == index) -1 else index
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = servicio.imagenId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )

                    Text(
                        text = "${servicio.name} - ${servicio.price}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 16.dp),
                        textAlign = TextAlign.Center
                    )

                    if (servicioExpandido == index) {
                        Text(
                            text = servicio.descripcion,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(bottom = 16.dp),
                            textAlign = TextAlign.Justify
                        )
                        Button(
                            onClick = {
                                navController.navigate(AppScreen.Reserva.route) {
                                    launchSingleTop = true
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF9F51CA),
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        ) {
                            Text(text = "Agendar Cita")
                        }
                    }
                }
            }
        }
    }
}