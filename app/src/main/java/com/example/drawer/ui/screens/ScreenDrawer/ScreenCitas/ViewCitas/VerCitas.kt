package com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewCitas

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drawer.data.model.Cita
import com.example.drawer.data.model.EstadoCita
import com.example.drawer.R
import com.example.drawer.data.model.ReservaViewModel
import com.example.drawer.ui.screens.ScreenDrawer.Espacio
import com.example.drawer.data.utils.FirestoreManager
import com.example.drawer.ui.navigation.navigationDrawer.AppScreen
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VerCita(
    navController: NavController,
    firestore: FirestoreManager,
    viewModel: ReservaViewModel
) {
    val citas by firestore.getCitasFlow().collectAsState(initial = emptyList())
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    val dialogState = rememberMaterialDialogState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Botón en la parte superior derecha
        IconButton(
            onClick = { dialogState.show() },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp, end = 16.dp, start = 16.dp)
        ) {
            Icon(Icons.Filled.FilterList, contentDescription = "Filtrar")
        }

        // Tu contenido existente
        val filteredCitas = if (selectedDate.value != null) {
            citas.filter { cita ->
                LocalDate.parse(
                    cita.fecha,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                ) == selectedDate.value
            }
        } else {
            citas
        }

        if (filteredCitas.isNotEmpty()) {
            LazyColumn(
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                filteredCitas.forEach {
                    item {
                        CitaItem(cita = it, navController, viewModel)
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

        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton("Ok")
            }) {
            datepicker { newDate ->
                selectedDate.value = newDate
            }
        }
    }
}


@Composable
fun CitaItem(cita: Cita, navController: NavController, viewModel: ReservaViewModel) {
    Card(
        modifier = Modifier
            .height(170.dp)
            .padding(6.dp),
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
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
                        text = when (cita.estado) {
                            EstadoCita.PENDIENTE.name -> "Pendiente"
                            EstadoCita.PROCESO.name -> "En proceso"
                            EstadoCita.CANCELADA.name -> "Cancelado"
                            EstadoCita.CONCLUIDA.name -> "Finalizado"
                            else -> ""
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (cita.estado) {
                            EstadoCita.PENDIENTE.name -> Color.DarkGray
                            EstadoCita.PROCESO.name -> Color.Blue
                            EstadoCita.CANCELADA.name -> Color.Red
                            EstadoCita.CONCLUIDA.name -> Color.Green
                            else -> Color.Black
                        },
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
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.feto),
                    contentDescription = "Imagen Cita",
                    modifier = Modifier
                        .width(150.dp)
                        .height(100.dp)
                )
                Box {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                                append("Cancelar cita")
                            }
                        },
                        modifier = Modifier.clickable {
                            viewModel.selectedCita = cita
                            navController.navigate(AppScreen.CancelarCita.route)
                        }
                    )
                    Divider(color = Color.Black, modifier = Modifier.padding(top = 18.dp))
                }

            }
        }
    }
}
