@file:Suppress("OPT_IN_IS_NOT_ENABLED")
@file:OptIn(ExperimentalMaterialApi::class)

package com.example.drawer.ui.screens.ScreensPrincipal.Auth

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
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
import com.example.drawer.ui.navigation.AppScreens
import com.example.drawer.data.utils.AuthManager
import com.example.drawer.data.utils.AuthRes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(auth: AuthManager, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val passVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (val account =
            auth.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))) {
            is AuthRes.Success -> {
                val credential = GoogleAuthProvider.getCredential(account.data.idToken, null)
                scope.launch {
                    val fireUser = auth.signInWithGoogleCredential(credential)
                    if (fireUser != null) {
                        Toast.makeText(context, "Bienvenido(a)", Toast.LENGTH_SHORT).show()
                        navController.navigate(AppScreens.DrawerScreen.route) {
                            popUpTo(AppScreens.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
            }
            is AuthRes.Error -> {
                Toast.makeText(context, "Error: ${account.errorMessage}", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, "Error desconocido", Toast.LENGTH_SHORT).show()
            }
        }
    }

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
            Box(modifier = Modifier.fillMaxSize()) {
                ClickableText(
                    text = AnnotatedString("¿No tienes una cuenta? Regístrate"),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(40.dp),
                    onClick = {
                        navController.navigate(AppScreens.SignUp.route)
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        textDecoration = TextDecoration.Underline,
                        color = Color.DarkGray
                    )
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
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
                    text = "BIENVENIDO(A)",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 30.sp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                OutlinedTextField(
                    label = { Text(text = "Correo electrónico") },
                    value = email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { email = it })
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = {  password = it },
                    label = { Text(text = "Contraseña") },
                    visualTransformation = if (passVisible.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passVisible.value = !passVisible.value }) {
                            VerContra(passVisible.value)
                        }
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                            scope.launch {
                                emailPassSignIn(email, password, auth, context, navController)
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
                        Text(text = "Iniciar Sesión".uppercase())
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                ClickableText(
                    text = AnnotatedString("¿Olvidaste tu contraseña?"),
                    onClick = {
                        navController.navigate(AppScreens.ForgotPassword.route)
                    },
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        textDecoration = TextDecoration.Underline,
                        color = Color.DarkGray
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))
                Text(text = "-------- o --------", style = TextStyle(color = Color.Gray))
                Spacer(modifier = Modifier.height(25.dp))
                SocialMediaButton(
                    onClick = {
                        scope.launch {
                            auth.signInWithGoogle(googleSignInLauncher)
                        }
                    },
                    text = "Continuar con Google",
                    icon = R.drawable.ic_google,
                    color = Color(0xFFF1F1F1)
                )
            }
        }
    }
}

private suspend fun emailPassSignIn(
    email: String,
    password: String,
    auth: AuthManager,
    context: Context,
    navigation: NavController
) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        when (val result = auth.signInWithEmailAndPassword(email, password)) {
            is AuthRes.Success -> {
                navigation.navigate(AppScreens.DrawerScreen.route) {
                    popUpTo(AppScreens.LoginScreen.route) {
                        inclusive = true
                    }
                }
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

@Composable
fun SocialMediaButton(onClick: () -> Unit, text: String, icon: Int, color: Color) {
    var click by remember { mutableStateOf(false) }
    Surface(
        onClick = onClick,
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp)
            .clickable { click = !click },
        shape = RoundedCornerShape(50),
        border = BorderStroke(width = 1.dp, color = Color.Gray),
        color = color
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                modifier = Modifier.size(24.dp),
                contentDescription = text,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color.Black)
            click = true
        }
    }
}

@Composable
fun VerContra(passVisible: Boolean) {
    if (passVisible) Icon(
        painter = painterResource(id = R.drawable.visibility_off),
        contentDescription = "Visible"
    )
    else Icon(
        painter = painterResource(id = R.drawable.visibility ),
        contentDescription = "Visible"
    )
}