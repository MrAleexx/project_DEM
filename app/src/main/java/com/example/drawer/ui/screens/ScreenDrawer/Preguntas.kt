package com.example.drawer.ui.screens.ScreenDrawer


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drawer.R
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.Emcabezado


@Composable
fun Preguntas() {
    val questionList = listOf(
        "¿Cuánto tiempo antes de mi cita debo llegar al centro?" to "En el Centro Ecográfico Reina Isabel lo ideal es llegar 30 minutos antes de la cita.",
        "¿Qué debo llevar o hacer antes de una Ecografía?" to "Se debe llevar crema, pañuelo, bolsa, entre otros.",
        "¿Cuáles son nuestros horarios de atención?" to "Los horarios de atención son los siguientes: Lunes a Sábado de 8:00 a.m. a 5:00 p.m.",
        "¿Cuáles son las opciones de pago disponibles?" to "Se puede pagar mediante yape, BCP, BBVA, etc."
    )

    val expandedStates = remember { questionList.map { mutableStateOf(false) } }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, top = 10.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Emcabezado(text = "Reina Isabel")
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = "PREGUNTAS FRECUENTES",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Text(
                text = "Le presentamos un listado de preguntas que pueden ayudar a resolver la mayoría de sus dudas.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.height(5.dp))
        }

        items(questionList.size) { index ->
            val (question, answer) = questionList[index]
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .background(Color(252, 219, 236), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .clickable { expandedStates[index].value = !expandedStates[index].value }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = question,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )

                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(Color(146, 66, 128), shape = RoundedCornerShape(4.dp))
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_expand_more_24),
                            contentDescription = "flecha exp",
                            modifier = Modifier.size(24.dp),
                            tint = Color(252, 219, 236)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (expandedStates[index].value) {
                Text(
                    text = answer,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp),
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}
