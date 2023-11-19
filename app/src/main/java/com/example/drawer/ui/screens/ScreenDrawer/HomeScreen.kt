package com.example.drawer.ui.screens.ScreenDrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drawer.R

@Composable
fun HomeScreen() {
    val datos: List<Triple<String, Int, String>> = listOf(
        Triple(
            "Conócenos",
            R.drawable.ce_reinaisabel_,
            "El Centro Ecográfico \"Reina Isabel\" es una destacada institución en el sector de la salud y la atención médica, especializada en medicina diagnóstica y servicios de imágenes médicas. "
        ),
        Triple(
            "Nuestra Obstetra", R.drawable.obstetra_,
            "Licenciada en Obstetricia \n  " +
                    "◉ Katherine Aranda Quispe  \n \nEspecializada en : \n  " +
                    "◉ Ecografía Obstétrica \n" +
                    "  ◉ Control Prenatal \n" +
                    "  ◉ Monitoreo Electrónico Fetal \n" +
                    "  ◉ Métodos Anticonceptivos "
        ),
        Triple("Ubicación", R.drawable.ubicacion, "\n        Calle Augusto B. Leguía #327"),
        Triple(
            "Horario de Atención",
            R.drawable.ce_horario,
            "Lunes a Sábado \n  ◉ 8:00 am - 5:00 pm "
        ),
    )
    LazyColumn(
        contentPadding = PaddingValues(all = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp), // Ajusta el espacio entre elementos
        modifier = Modifier.background(color = Color(0xFFFFFFFF)) // Color blanco en hexadecimal
    ) {
        items(datos) { (item, imageRes, contenido) ->
            ListItemRow(item, imageRes, contenido)
        }
    }
}

@Composable
fun ListItemRow(item: String, imageRes: Int, contenido: String) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(6.dp) // Ancho del rectángulo (barra)
                        .height(32.dp) // Altura del rectángulo (barra)
                        .background(Color(159, 81, 202, 255))
                )

                Spacer(modifier = Modifier.width(35.dp)) // Agrega espacio entre el rectángulo (barra) y el texto

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = item,
                    fontWeight = FontWeight.Bold, // Peso de la fuente del texto (negrita en este caso)
                    fontSize = 25.sp, // Tamaño de la fuente del texto
                    color = Color(159, 81, 202, 255) // Color del texto (negro en este caso)

                )
            }

            Spacer(modifier = Modifier.height(20.dp)) // Agrega espacio entre líneas de texto

            Spacer(modifier = Modifier.height(30.dp)) // Agrega espacio entre el texto y la imagen

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(230.dp)
                    .clip(shape = MaterialTheme.shapes.small)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = contenido,
                fontSize = 18.sp, // Tamaño de la fuente del texto
                color = Color(32, 31, 32, 255),
                textAlign = TextAlign.Justify // Alinea el texto de manera justificada
                // Color del texto (negro en este caso)
            )
        }

    }
}

@Preview
@Composable
fun PreviewGreeting() {
    HomeScreen()
}
