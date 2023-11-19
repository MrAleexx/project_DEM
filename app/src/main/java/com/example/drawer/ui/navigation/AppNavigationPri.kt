package com.example.drawer.ui.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.drawer.ui.screens.ScreensPrincipal.Auth.ForgotPasswordScreen
import com.example.drawer.ui.screens.ScreensPrincipal.Auth.LoginScreen
import com.example.drawer.ui.screens.ScreensPrincipal.Auth.SignUpScreen
import com.example.drawer.ui.screens.ScreensPrincipal.DrawerScreen
import com.example.drawer.ui.screens.ScreensPrincipal.SplashScreen
import com.example.drawer.data.utils.AuthManager

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(context: Context, navController: NavHostController = rememberNavController()) {

    val authManager = AuthManager(context)
    NavHost( navController, AppScreens.SplashScreen.route) {

        composable(AppScreens.SplashScreen.route) {
            SplashScreen(
                authManager,navController
            )
        }
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(
                authManager, navController
            )
        }
        composable(AppScreens.DrawerScreen.route) {
            DrawerScreen(
                authManager, navController
            )
        }
        composable(AppScreens.SignUp.route) {
            SignUpScreen(
                authManager, navController
            )
        }
        composable(AppScreens.ForgotPassword.route) {
            ForgotPasswordScreen(
                authManager, navController
            )
        }
    }
}
