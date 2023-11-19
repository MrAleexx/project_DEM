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
import com.example.drawer.ui.navigation.navigationDrawer.AppScreen
import com.example.drawer.ui.screens.ScreenDrawer.Espacio

@Composable
fun SelecionarCita(
    navController: NavController,
    viewModelR: ReservaViewModel
) {
    val preciosServicios = mapOf(
        "ECOGRAFÍA OBSTETRICA" to 100.0,
        "ECOGRAFÍA TRANSVAGINAL" to 120.0,
        "CONTROL PRENATAL" to 80.0,
        "TEST DE EMBARAZO" to 50.0,
        "PAPANICOLAU" to 70.0,
        "RETIRO DE IMPLANTE" to 150.0,
        "PLANIFICACIÓN FAMILIAR" to 200.0
    )

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
        preciosServicios.forEach { preciosServicios ->
            Espacio(dp = 7)
            Button(
                onClick = {
                    viewModelR.changeServico(preciosServicios.key)
                    viewModelR.changePrecio(preciosServicios.value)
                    navController.navigate(AppScreen.AgregarInfo.route)
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.textButtonColors(Color(0xF3FFD5FD))
            ) {
                Text(
                    text = preciosServicios.key,
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
fun Emcabezado(text: String) {
    Row(
        modifier = Modifier
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
            modifier = Modifier
                .fillMaxWidth(),
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = MaterialTheme.colors.secondaryVariant
        )
    }
}
