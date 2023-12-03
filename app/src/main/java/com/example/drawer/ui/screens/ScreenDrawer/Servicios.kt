package com.example.drawer.ui.screens.ScreenDrawer

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drawer.data.model.Servicios
import com.example.drawer.data.utils.FirestoreManager
import com.example.drawer.ui.navigation.navigationDrawer.AppScreen
import com.example.drawer.ui.screens.ScreenDrawer.ScreenAdmin.CoilImage
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.Emcabezado

@Composable
fun Servicios(navController: NavController, firestore: FirestoreManager) {

    var servicioExpandido by remember { mutableIntStateOf(-1) }
    val servicios = remember { mutableStateOf(listOf<Servicios>()) }
    LaunchedEffect(key1 = true) {
        firestore.getServiciosFlow().collect { serviciosList ->
            servicios.value = serviciosList
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp)
    ) {

        item {
            Spacer(modifier = Modifier.height(30.dp))
            Emcabezado(text = "Servicios")
        }

        items(servicios.value.size) { index ->
            val servicio = servicios.value[index]

            Spacer(modifier = Modifier.height(15.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable {
                        servicioExpandido = if (servicioExpandido == index) -1 else index
                    }, elevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    CoilImage(
                        imageUrl = servicio.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "${servicio.name} - $/.${servicio.precio}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 16.dp),
                        textAlign = TextAlign.Center
                    )

                    if (servicioExpandido == index) {
                        Text(
                            text = servicio.descrip,
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