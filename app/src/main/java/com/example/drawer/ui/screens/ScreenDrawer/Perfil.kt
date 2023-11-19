package com.example.drawer.ui.screens.ScreenDrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.drawer.R
import com.example.drawer.ui.navigation.AppScreens
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.Emcabezado
import com.example.drawer.data.utils.AuthManager

private var welcomeMessage by mutableStateOf("Bienvenido(a)")

@Composable
fun Perfil( auth: AuthManager, navigation: NavController) {
    val user = auth.getCurrentUser()
    var showDialog by remember { mutableStateOf(false) }
    val onLogoutConfirmed: () -> Unit = {
        auth.signOut()
        navigation.navigate(AppScreens.LoginScreen.route) {
            popUpTo(AppScreens.DrawerScreen.route) {
                inclusive = true
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Espacio(dp = 20)
        Emcabezado(text = "Perfil")
        Spacer(modifier = Modifier.height(8.dp))

        if (user?.photoUrl != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(user.photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagen",
                placeholder = painterResource(id = R.drawable.profile),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(90.dp)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "Foto de perfil por defecto",
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (!user?.displayName.isNullOrEmpty()) "${user?.displayName}" else welcomeMessage,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (!user?.email.isNullOrEmpty()) "${user?.email}" else "Anónimo",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
        if (showDialog) {
            LogoutDialog(onConfirmLogout = {
                onLogoutConfirmed()
                showDialog = false
            }, onDismiss = { showDialog = false })
        }

        Spacer(Modifier.weight(1f))
        Button(
            onClick = {
                showDialog = true
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
            Text(text = "Cerrar Sesión")
        }
        Espacio(dp = 10)
    }
}


@Composable
fun LogoutDialog(onConfirmLogout: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        modifier = Modifier.padding(5.dp),
        onDismissRequest = onDismiss,
        title = { Text("Cerrar sesión") },
        text = { Text("¿Estás seguro que deseas cerrar sesión?") },
        confirmButton = {
            Button(
                onClick = onConfirmLogout,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondaryVariant)
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondaryVariant)
            ) {
                Text("Cancelar")
            }
        }
    )
}