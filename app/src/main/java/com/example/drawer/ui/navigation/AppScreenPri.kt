package com.example.drawer.ui.navigation

sealed class AppScreens(val route:String){
    object SplashScreen: AppScreens("Splash Screen")
    object LoginScreen: AppScreens("Login Screen")
    object DrawerScreen: AppScreens("drawer Screen")
    object SignUp : AppScreens("SignUp Screen")
    object ForgotPassword : AppScreens("ForgotPassword Screen")
}