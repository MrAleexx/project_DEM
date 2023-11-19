package com.example.drawer

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.example.drawer.ui.navigation.AppNavigation
import com.example.drawer.ui.theme.DRAWERTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DRAWERTheme {
                // Contenedor de Surface que usa el color de 'fondo' del tema.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppNavigation(this)
                }
            }
        }
    }
}

