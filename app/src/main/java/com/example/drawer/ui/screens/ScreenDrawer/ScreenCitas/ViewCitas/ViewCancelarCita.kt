package com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewCitas

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drawer.R
import com.example.drawer.data.model.EstadoCita
import com.example.drawer.data.model.ReservaViewModel
import com.example.drawer.data.utils.FirestoreManager
import com.example.drawer.ui.screens.ScreenDrawer.Espacio
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.CustomButton_Small
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.Emcabezado
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CancelarCita(
    navController: NavController,
    viewModel: ReservaViewModel,
    firestore: FirestoreManager,
    scope: CoroutineScope
) {
    val context = LocalContext.current
    val cita = viewModel.selectedCita
    val motivoCancelacion = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 26.dp,
                vertical = 26.dp
            )
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
                Emcabezado(text = "Informacion", modifier = Modifier.fillMaxWidth())
                Espacio(dp = 15)
                Text("Servicio: ${cita?.servicio}")
                Espacio(dp = 10)
                Text("Fecha: ${cita?.fecha}")
                Espacio(dp = 10)
                Text("Hora: ${cita?.hora}")
                Espacio(dp = 10)
                OutlinedTextField(
                    value = motivoCancelacion.value,
                    onValueChange = { motivoCancelacion.value = it },
                    label = { Text("Motivo de cancelación") }
                )
                Espacio(dp = 20)
                CustomButton_Small(
                    title = "Enviar",
                    onClick = {
                        if (motivoCancelacion.value.text.isNotEmpty()) {
                            scope.launch {
                                cita?.motivoCancelacion = motivoCancelacion.value.text
                                cita?.estado = EstadoCita.CANCELADA.name // Cambia el estado a "CANCELADA"
                                cita?.let { firestore.updateCita(it) }
                            }.invokeOnCompletion {
                                motivoCancelacion.value = TextFieldValue("") // Limpia el texto
                                navController.popBackStack()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Por favor, ingrese el motivo de la cancelación.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    Icon = R.drawable.check_line,
                    descripIcon = "Enviar"
                )
                Espacio(dp = 15)
                CustomButton_Small(
                    title = "Cancelar",
                    onClick = { navController.popBackStack() },
                    Icon = R.drawable.close,
                    descripIcon = "Cancelar"
                )
            }
        }
    }
}

