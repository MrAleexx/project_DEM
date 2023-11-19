package com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.drawer.data.model.ReservaViewModel
import com.example.drawer.R
import com.example.drawer.ui.navigation.navigationDrawer.AppScreen
import com.example.drawer.ui.screens.ScreenDrawer.Espacio
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgregarInfo(
    navController: NavController,
    viewModelR: ReservaViewModel
) {
    val state by viewModelR.state.observeAsState()

    val context = LocalContext.current
    var pickedDate by remember { mutableStateOf(LocalDate.now()) }
    var pickedTime by remember { mutableStateOf(LocalTime.NOON) }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("dd/MM/yyyy", Locale.getDefault())
                .format(pickedDate)
        }
    }

    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm a", Locale.getDefault())
                .format(pickedTime)
        }
    }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {
                Toast.makeText(
                    context,
                    "fecha correcta",
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Seleccione una fecha"
        ) {
            pickedDate = it
            viewModelR.changeFecha(formattedDate)
        }
    }
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "Ok") {
                Toast.makeText(
                    context,
                    "hora correcta",
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(text = "Cancel")
        }
    ) {
        timepicker(
            initialTime = LocalTime.NOON,
            title = "Seleccione una hora"
        ) {
            pickedTime = it
            viewModelR.changeHora(formattedTime)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                Emcabezado(text = "Informacion")
                Espacio(dp = 15)
                OutlinedTextField(
                    value = state?.nombre ?: "",
                    onValueChange = { viewModelR.changeNombre(it) },
                    label = { Text("Nombre Completo") }
                )
                Espacio(dp = 10)
                OutlinedTextField(
                    value = state?.numero ?: "",
                    onValueChange = { viewModelR.changeNumero(it) },
                    label = { Text("Número") }
                )
                Espacio(dp = 10)
                OutlinedTextField(
                    value = state?.fecha ?: "",
                    onValueChange = {},
                    label = { Text("Fecha") },
                    trailingIcon = {
                        IconButton(onClick = { dateDialogState.show() }) {
                            Image(
                                painter = painterResource(id = R.drawable.calendar),
                                contentDescription = "Seleccionar calendario"
                            )
                        }
                    }
                )
                Espacio(dp = 10)
                OutlinedTextField(
                    value = state?.hora ?: "",
                    onValueChange = { viewModelR.changeHora(it) },
                    label = { Text("Hora") },
                    trailingIcon = {
                        IconButton(onClick = { timeDialogState.show() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.time),
                                contentDescription = "Selecionar Hora"
                            )
                        }
                    }
                )
                Espacio(dp = 20)
                CustomButton_Small(
                    title = "Validar",
                    onClick = {
                        navController.navigate(AppScreen.DetalleCita.route)
                    },
                    Icon = R.drawable.check_line,
                    descripIcon = "Validar"
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

@Composable
fun CustomButton_Small(
    title: String,
    onClick: () -> Unit,
    Icon: Int,
    descripIcon: String,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondaryVariant),
        modifier = Modifier
            .width(140.dp)
            .height(40.dp)
    ) {
        Icon(
            painter = painterResource(id = Icon),
            contentDescription = descripIcon,
            tint = Color.White,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(3f)
        )
    }
}

