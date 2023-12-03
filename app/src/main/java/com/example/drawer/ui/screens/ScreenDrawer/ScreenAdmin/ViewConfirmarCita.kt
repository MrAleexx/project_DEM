package com.example.drawer.ui.screens.ScreenDrawer.ScreenAdmin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drawer.data.model.EstadoCita
import com.example.drawer.data.model.ReservaViewModel
import com.example.drawer.data.utils.FirestoreManager
import com.example.drawer.ui.screens.ScreenDrawer.Espacio
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.Emcabezado
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ViewConfirmacionReserva(
    navController: NavController,
    viewModel: ReservaViewModel,
    firestore: FirestoreManager,
    scope: CoroutineScope
) {
    var isCancelarClicked by remember {mutableStateOf(false)}
    val selectedCita = viewModel.selectedCita
    // Comprueba si hay una cita seleccionada
    if (selectedCita != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 26.dp,
                    vertical = 26.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = MaterialTheme.shapes.small),
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                backgroundColor = Color(0xF3FFD5FD)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 16.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Emcabezado(text = "Confirmar cita", modifier = Modifier.fillMaxWidth())
                    Espacio(dp = 15)
                    Text(text = "Servicio: ${selectedCita.servicio}")
                    Espacio(dp = 10)
                    Text(text = "Fecha: ${selectedCita.fecha}")
                    Espacio(dp = 10)
                    Text(text = "Hora: ${selectedCita.hora}")
                    Espacio(dp = 10)
                    Text(text = "Estado: ${selectedCita.estado}")
                    Espacio(dp = 10)
                    // Botón para confirmar la cita
                    Button(onClick = { viewModel.changeEstadoCita(EstadoCita.PROCESO) }) {
                        Text("Confirmar Cita")
                    }

                    // Campo de entrada para el motivo de cancelación
                    val motivoCancelacion = remember { mutableStateOf(TextFieldValue()) }
                    // Botón para cancelar la cita
                    Button(onClick = {
                        viewModel.changeEstadoCita(EstadoCita.CANCELADA)
                        viewModel.changeMotivoCancelacion(motivoCancelacion.value.text)
                        isCancelarClicked = true
                    }) {
                        Text("Cancelar Cita")
                    }
                    if (isCancelarClicked) {
                        OutlinedTextField(
                            value = motivoCancelacion.value,
                            onValueChange = { motivoCancelacion.value = it },
                            label = { Text("Motivo de cancelación") }
                        )
                        viewModel.changeMotivoCancelacion(motivoCancelacion.value.text)
                    }
                }
            }
            
            // Botón para actualizar la cita
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    // Aquí actualizas la cita en la base de datos
                    scope.launch {
                        selectedCita.let { firestore.updateCita(it) }
                    }.invokeOnCompletion {
                        viewModel.changeMotivoCancelacion("")
                        navController.popBackStack()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondaryVariant,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Actualizar")
            }
        }
    }
}

