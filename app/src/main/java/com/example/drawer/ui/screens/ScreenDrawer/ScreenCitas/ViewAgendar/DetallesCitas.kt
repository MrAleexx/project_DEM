package com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drawer.data.model.Cita
import com.example.drawer.data.model.Paciente
import com.example.drawer.data.model.ReservaViewModel
import com.example.drawer.R
import com.example.drawer.ui.navigation.navigationDrawer.AppScreen
import com.example.drawer.ui.screens.ScreenDrawer.CustomButton
import com.example.drawer.ui.screens.ScreenDrawer.Espacio
import com.example.drawer.data.utils.FirestoreManager
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetallesCita(
    navController: NavController,
    firestore: FirestoreManager,
    scope: CoroutineScope,
    viewModelR: ReservaViewModel
) {
    val state by viewModelR.state.observeAsState()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val selectedDate = LocalDate.parse(state?.fecha ?: "", formatter)
    val dateMinusWeek = selectedDate.minusWeeks(1)
    val diaPolitica = dateMinusWeek.format(formatter)
    val dialogState = remember { MaterialDialogState() }
    val dialogNotaState = remember { MaterialDialogState() }

    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(
                text = "Guardar",
                textStyle = TextStyle(
                    color = MaterialTheme.colors.secondaryVariant,
                    fontWeight = FontWeight.Bold
                )
            ) {
            }
            negativeButton(
                text = "Cancelar",
                textStyle = TextStyle(
                    color = MaterialTheme.colors.secondaryVariant,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = state?.nombre ?: "",
                onValueChange = { viewModelR.changeNombre(it) },
                label = { Text("Nuevo Nombre") }
            )
            OutlinedTextField(
                value = state?.numero ?: "",
                onValueChange = { viewModelR.changeNumero(it) },
                label = { Text("Nuevo Número") }
            )
        }
    }

    MaterialDialog(
        dialogState = dialogNotaState,
        buttons = {
            positiveButton(
                text = "Guardar",
                textStyle = TextStyle(
                    color = MaterialTheme.colors.secondaryVariant,
                    fontWeight = FontWeight.Bold
                )
            )
            negativeButton(
                text = "Cancelar",
                textStyle = TextStyle(
                    color = MaterialTheme.colors.secondaryVariant,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = state?.nota ?: "",
                onValueChange = { viewModelR.changeNota(it) },
                label = { Text("Agrega Nota") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(10.dp)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
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
                    )
            ) {
                Emcabezado(text = "Detalles")
                Espacio(dp = 15)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = MaterialTheme.shapes.small),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 16.dp
                            ), horizontalArrangement = Arrangement.Center
                    ) {
                        Column(modifier = Modifier.weight(3f)) {
                            Text(text = "INFORMACION PERSONAL: ", fontWeight = FontWeight.Bold)
                            Text(text = "Nombre: ${state?.nombre ?: ""}")
                            Text(text = "Celular: ${state?.numero ?: ""}")
                        }
                        IconButton(onClick = { dialogState.show() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit),
                                contentDescription = "Editar datos personales"
                            )
                        }

                    }
                }
                Espacio(dp = 10)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = MaterialTheme.shapes.small),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 16.dp
                            )
                    ) {
                        Text(text = "POLITICA DE CANCELACIÓN", fontWeight = FontWeight.Bold)
                        Text(
                            text = "La reserva se puede cancelar hasta el $diaPolitica",
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Espacio(dp = 10)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = MaterialTheme.shapes.small),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 16.dp
                            )
                    ) {
                        Text(text = "NOTAS DE RESERVA", fontWeight = FontWeight.Bold)
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = { dialogNotaState.show() }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.add_circle_outline),
                                    contentDescription = "Agregar nota"
                                )
                            }
                            // Si la nota no es nula, muestra la nota. De lo contrario, muestra "Añadir nota"
                            if (state?.nota != "") {
                                Text(
                                    text = state?.nota ?: "",
                                    maxLines = 2, // Limita el texto a 2 líneas
                                    overflow = TextOverflow.Ellipsis // Agrega puntos suspensivos al final si el texto es demasiado largo
                                )
                            } else {
                                Text(text = "Añadir nota")
                            }
                        }
                    }
                }

                Espacio(dp = 10)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = MaterialTheme.shapes.small),
                    elevation = 8.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 16.dp
                            )
                    ) {
                        Row {
                            Column(modifier = Modifier.weight(3f)) {
                                Text(text = "RESUMEN:", fontWeight = FontWeight.Bold)
                                Text(text = "Servicio: ${state?.nombreServicio ?: ""}")
                                Text(text = "Fecha: ${state?.fecha ?: ""} ")
                                Text(text = "A las ${state?.hora ?: ""}")
                            }
                            Text(text = "")
                            Text(text = "S/.${state?.precio ?: ""}")
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp),
                            color = Color.Black
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "TOTAL:", fontWeight = FontWeight.Bold)
                            Text(text = "S/.${state?.precio ?: ""}", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        CustomButton(
            onClick = {
                scope.launch {
                    val newCita = Cita(
                        servicio = state?.nombreServicio ?: "",
                        precio = state?.precio ?: 0.0,
                        fecha = state?.fecha ?: "",
                        hora = state?.hora ?: "",
                        nota = state?.hora ?: ""
                    )
                    val newPaciente = Paciente(
                        nameAll = state?.nombre ?: "",
                        celular = state?.numero ?: ""
                    )
                    firestore.addCita(newCita)
                    firestore.addPaciente(newPaciente)
                }.invokeOnCompletion {
                    navController.navigate(AppScreen.SplashCheck.route) {
                        popUpTo(AppScreen.DetalleCita.route) {
                            inclusive = true
                        }
                    }
                }
            },
            title = "Crear cita",
            icon = R.drawable.calendar_check,
            description = "Crear cita"
        )
    }
}
