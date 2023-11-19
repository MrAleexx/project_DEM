package com.example.drawer.ui.screens.ScreenDrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.drawer.R
import com.example.drawer.ui.navigation.navigationDrawer.AppScreen
import com.example.drawer.data.utils.AuthManager

@Composable
fun Reserva(navController: NavHostController, auth: AuthManager) {
    val user = auth.getCurrentUser()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 32.dp, end = 32.dp, top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CustomTexts(
            text = "Bienvenido(a)",
            color = MaterialTheme.colors.secondaryVariant,
            fontSize = 40
        )
        CustomTexts(
            text = if (!user?.displayName.isNullOrEmpty()) "${user?.displayName}" else "",
            color = Color.Black,
            fontSize = 25
        )

        Espacio(dp = 20)
        Image(
            painter = painterResource(id = R.drawable.p1),
            contentDescription = "Imagen Reserva",
            modifier = Modifier
                .size(250.dp)
        )

        Espacio(dp = 15)
        CustomButton(
            onClick = { navController.navigate(AppScreen.AgendarCita.route) },
            title = "Agendar cita",
            icon = R.drawable.calendar,
            description = "Botón agendar aita"
        )
        CustomButton(
            onClick = { navController.navigate(AppScreen.VerCita.route) },
            title = "Ver cita",
            icon = R.drawable.buscar,
            description = "Botón ver cita"
        )
        Espacio(dp = 10)
    }
}

@Composable
fun CustomTexts(text: String, color: Color, fontSize: Int) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        fontSize = fontSize.sp
    )
}

@Composable
fun Espacio(dp: Int) {
    Spacer(modifier = Modifier.size(dp.dp))
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    title: String,
    icon: Int,
    description: String,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondaryVariant)
) {
    Button(
        onClick = onClick,
        colors = colors,
        modifier = modifier
            .width(250.dp)
            .height(60.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = description,
            tint = Color.White,
            modifier = Modifier
                .size(30.dp)
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(3f)
        )
    }
}
