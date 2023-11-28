package com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewCitas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drawer.data.model.Cita
import com.example.drawer.data.model.EstadoCita
import com.example.drawer.R
import com.example.drawer.ui.screens.ScreenDrawer.Espacio
import com.example.drawer.data.utils.FirestoreManager

@Composable
fun VerCita(firestore: FirestoreManager) {

    val citas by firestore.getCitasFlow().collectAsState(initial = emptyList())

    if (citas.isNotEmpty()) {
        LazyColumn(
            Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            citas.forEach {
                item {
                    CitaItem(cita = it)
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No se encontraron \nCitas",
                fontSize = 18.sp, fontWeight = FontWeight.Thin,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CitaItem(cita: Cita) {
    Card(
        modifier = Modifier.padding(6.dp),
        elevation = 5.dp
    ) {
        Row(modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
            ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(3f)
                    .fillMaxWidth()
            ) {
                Row {
                    Text(
                        text = "Estado: ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = if (cita.estado == EstadoCita.PROCESO.name) "En Proceso" else "",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (cita.estado == EstadoCita.PROCESO.name) Color.Blue else Color.Black,
                        fontFamily = FontFamily.SansSerif
                    )
                }
                Espacio(dp = 2)
                Text(
                    text = "Servicio: ${cita.servicio}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
                Espacio(dp = 4)
                Text(
                    text = "Fecha: ${cita.fecha}\nHora: ${cita.hora}",
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                    lineHeight = 15.sp
                )
            }
            Image(
                painter = painterResource(id = R.drawable.feto),
                contentDescription = "Imagen Cita",
                modifier = Modifier
                    .size(150.dp)
                    .weight(1f)
            )
        }
    }
}
