package com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drawer.data.model.ReservaViewModel
import com.example.drawer.R
import com.example.drawer.data.model.Servicios
import com.example.drawer.data.utils.FirestoreManager
import com.example.drawer.ui.navigation.navigationDrawer.AppScreen
import com.example.drawer.ui.screens.ScreenDrawer.Espacio

@Composable
fun SelecionarCita(
    navController: NavController,
    viewModelR: ReservaViewModel,
    firestore: FirestoreManager
) {
    val servicios = remember { mutableStateOf(listOf<Servicios>()) }
    LaunchedEffect(key1 = true) {
        firestore.getServiciosFlow().collect { serviciosList ->
            servicios.value = serviciosList
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 26.dp,
                vertical = 26.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Emcabezado(text = "Seleciona un servicio")
        Espacio(dp = 10)
        servicios.value.forEach { servicios ->
            Espacio(dp = 7)
            Button(
                onClick = {
                    viewModelR.changeServico(servicios.name)
                    viewModelR.changePrecio(servicios.precio ?: 0.0)
                    navController.navigate(AppScreen.AgregarInfo.route)
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.textButtonColors(Color(0xF3FFD5FD))
            ) {
                Text(
                    text = servicios.name,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(6f)
                )
                Box(
                    Modifier
                        .size(35.dp)
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colors.secondaryVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = "flecha a la derecha",
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
            }
        }
    }
}

@Composable
fun Emcabezado(text: String, modifier:  Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(6.dp)
                .height(32.dp)
                .background(MaterialTheme.colors.secondaryVariant)
        )
        Spacer(modifier = Modifier.width(35.dp))
        Text(
            modifier = Modifier,
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = MaterialTheme.colors.secondaryVariant
        )
    }
}
