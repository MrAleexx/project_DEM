package com.example.drawer.ui.screens.ScreenDrawer.ScreenAdmin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.example.drawer.data.model.Servicios
import com.example.drawer.data.utils.FirestoreManager
import com.example.drawer.ui.navigation.navigationDrawer.AppScreen
import com.example.drawer.ui.screens.ScreenDrawer.Espacio
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.Emcabezado

@Composable
fun ViewServicio(
    navController: NavController,
    firestore: FirestoreManager,
) {
    val servicios = remember { mutableStateOf(listOf<Servicios>()) }
    LaunchedEffect(key1 = true) {
        firestore.getServiciosFlow().collect { serviciosList ->
            servicios.value = serviciosList
        }
    }

    if (servicios.value.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 35.dp)
        ) {
            Espacio(dp = 30)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Emcabezado(text = "View Servicios")
                IconButton(
                    onClick = { navController.navigate(AppScreen.Registrar.route) },
                    modifier = Modifier.weight(0.5f)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Agregar")
                        Espacio(dp = 5)
                        Icon(
                            painter = painterResource(id = R.drawable.add_circle_outline),
                            contentDescription = "Agregar Servicio",
                            tint = Color(252, 146, 181, 130)
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay servicios registrados",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }

    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        ) {
            item {
                Espacio(dp = 30)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Emcabezado(text = "View Servicios")
                    IconButton(
                        onClick = { navController.navigate(AppScreen.Registrar.route) },
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "Agregar")
                            Espacio(dp = 5)
                            Icon(
                                painter = painterResource(id = R.drawable.add_circle_outline),
                                contentDescription = "Agregar Servicio",
                                tint = Color(252, 146, 181, 130)
                            )
                        }
                    }
                }
            }
            items(servicios.value.size) { index ->
                val servicio = servicios.value[index]
                //val imageUrl = gallery[index]

                Spacer(modifier = Modifier.height(15.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 200.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp), elevation = 8.dp
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