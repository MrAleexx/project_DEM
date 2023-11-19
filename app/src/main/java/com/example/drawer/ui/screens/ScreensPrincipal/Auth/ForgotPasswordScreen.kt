package com.example.drawer.ui.screens.ScreensPrincipal.Auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drawer.R
import com.example.drawer.ui.navigation.AppScreens
import com.example.drawer.ui.screens.ScreenDrawer.Espacio
import com.example.drawer.data.utils.AuthManager
import com.example.drawer.data.utils.AuthRes
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(auth: AuthManager, navigation: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo2),
            contentDescription = "Fondo inicio",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Card(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo2),
                    contentDescription = "logo inicio",
                    modifier = Modifier.size(160.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Olvidó su contraseña",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 30.sp)
                )
                Spacer(modifier = Modifier.height(50.dp))
                OutlinedTextField(
                    label = { Text(text = "Correo electrónico") },
                    value = email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { email = it })
                Spacer(modifier = Modifier.height(30.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            scope.launch {
                                when (auth.resetPassword(email)) {
                                    is AuthRes.Success -> {
                                        Toast.makeText(
                                            context,
                                            "Correo enviado",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navigation.navigate(AppScreens.LoginScreen.route) {
                                            popUpTo(AppScreens.ForgotPassword.route) {
                                                inclusive = true
                                            }
                                        }
                                    }

                                    is AuthRes.Error -> {
                                        Toast.makeText(
                                            context,
                                            "Error al enviar el correo",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF9F51CA),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Recuperar contraseña")
                    }

                }
                Espacio(dp = 50)
            }
        }
    }
}