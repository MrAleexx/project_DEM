package com.example.drawer.ui.screens.ScreensPrincipal.Auth

import android.content.Context
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.IconButton
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.drawer.R
import com.example.drawer.ui.screens.ScreenDrawer.Espacio
import com.example.drawer.data.utils.AuthManager
import com.example.drawer.data.utils.AuthRes
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(auth: AuthManager, NavController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val passVisible = remember { mutableStateOf(false) }

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
                    text = "Crear Cuenta",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 30.sp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                /*OutlinedTextField(
                    label = { Text(text = "Nombre") },
                    value = name,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { name = it })*/
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    label = { Text(text = "Correo electrónico") },
                    value = email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { email = it })

                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Contraseña") },
                    visualTransformation = if (passVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passVisible.value = !passVisible.value }) {
                            VerContra(passVisible.value)
                        }
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(30.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            scope.launch {
                                signUp(email, password, auth, context, NavController)
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
                        Text(text = "Registrarse")
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
                ClickableText(
                    text = AnnotatedString("¿Ya tienes cuenta? Inicia sesión"),
                    onClick = {
                        NavController.popBackStack()
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        textDecoration = TextDecoration.Underline,
                        color = Color.DarkGray
                    )
                )
                Espacio(dp = 50)
            }
        }
    }
}


private suspend fun signUp(
    email: String,
    password: String,
    auth: AuthManager,
    context: Context,
    navigation: NavController
) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        when (val result = auth.createUserWithEmailAndPassword(email, password)) {
            is AuthRes.Success -> {
                Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                navigation.popBackStack()
            }

            is AuthRes.Error -> {
                Toast.makeText(context, "Error SignUp: ${result.errorMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    } else {
        Toast.makeText(context, "Existen campos vacios", Toast.LENGTH_SHORT).show()
    }
}