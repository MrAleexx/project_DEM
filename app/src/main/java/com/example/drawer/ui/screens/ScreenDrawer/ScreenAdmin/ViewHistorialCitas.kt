package com.example.drawer.ui.screens.ScreenDrawer.ScreenAdmin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drawer.R
import com.example.drawer.data.model.Cita
import com.example.drawer.data.model.EstadoCita
import com.example.drawer.data.utils.FirestoreManager
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.icons.filled.FilterListOff
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavController
import com.example.drawer.data.model.ReservaViewModel
import com.example.drawer.ui.screens.ScreenDrawer.Espacio


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ViewHistorialCitas(
    firestoreManager: FirestoreManager,
    navController: NavController,
    viewModel: ReservaViewModel
) {
    val citas = remember { mutableStateOf(emptyList<Cita>()) }
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    val selectedEstado = remember { mutableStateOf<EstadoCita?>(null) }
    val dialogState = rememberMaterialDialogState()
    val dropdownMenuExpanded = remember { mutableStateOf(false) }
    val filterByDate = remember { mutableStateOf(true) }


    LaunchedEffect(key1 = firestoreManager) {
        firestoreManager.getAllCitasFlow().collect { citas.value = it }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Botón en la parte superior derecha
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón para quitar los filtros
            if (selectedDate.value != null || selectedEstado.value != null) {
                IconButton(
                    onClick = {
                        selectedDate.value = null  // Restablecer la fecha seleccionada
                        selectedEstado.value = null  // Restablecer el estado seleccionado
                    },
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    Icon(Icons.Filled.FilterListOff, contentDescription = " Quitar filtros")
                }
            }

            // Botón para mostrar el diálogo de filtrado
            IconButton(
                onClick = { dialogState.show() },  // Mostrar el diálogo de filtrado
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Icon(Icons.Filled.FilterList, contentDescription = "Filtrar")
            }
        }

        // Filtrar las citas por la fecha y el estado seleccionados
        val filteredCitas = citas.value.filter { cita ->
            if (filterByDate.value) {
                selectedDate.value == null || LocalDate.parse(
                    cita.fecha,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                ) == selectedDate.value
            } else {
                selectedEstado.value == null || cita.estado == selectedEstado.value!!.name
            }
        }


        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            val groupedCitas = filteredCitas.groupBy { it.fecha }
            groupedCitas.forEach { (fecha, citasDelDia) ->
                stickyHeader {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF1B7D5)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = fecha, style = MaterialTheme.typography.h6)
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(3.dp),
                            color = Color(0xFFBB6BB8)
                        )
                    }
                }
                items(citasDelDia) { cita ->
                    Card(
                        modifier = Modifier
                            .height(170.dp)
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .clickable {
                                // Aquí guardas la cita completa que se ha seleccionado
                                viewModel.selectedCita = cita
                                // Aquí navegas a la pantalla "ViewConfirmacionReserva"
                                navController.navigate("ViewConfirmacionReserva")
                            },
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
                            }
                        }
                    }
                }
            }
        }

        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton("Confirmar") {
                }
            }) {
            // RadioButton para seleccionar el tipo de filtro
            Column(
                modifier = Modifier.padding(10.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = filterByDate.value,
                        onClick = { filterByDate.value = true })
                    Text("Filtrar por fecha")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = !filterByDate.value,
                        onClick = { filterByDate.value = false })
                    Text("Filtrar por estado")
                }
            }
            // DatePicker y DropdownMenu se muestran en función del tipo de filtro seleccionado
            if (filterByDate.value) {
                datepicker { newDate ->
                    selectedDate.value = newDate
                }
            } else {
                ExposedDropdownMenuBox(
                    expanded = dropdownMenuExpanded.value,
                    onExpandedChange = { dropdownMenuExpanded.value = it }
                ) {
                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = selectedEstado.value?.name ?: "Seleccionar estado",
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.clickable { dropdownMenuExpanded.value = true }
                        )
                        DropdownMenu(
                            expanded = dropdownMenuExpanded.value,
                            onDismissRequest = { dropdownMenuExpanded.value = false }
                        ) {
                            EstadoCita.values().forEach { estado ->
                                DropdownMenuItem(onClick = {
                                    selectedEstado.value = estado
                                    dropdownMenuExpanded.value = false
                                }) {
                                    Text(text = estado.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

