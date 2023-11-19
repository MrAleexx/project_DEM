package com.example.drawer.ui.screens.ScreensPrincipal

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.drawer.R
import com.example.drawer.ui.navigation.navigationDrawer.AppScreen
import com.example.drawer.ui.navigation.navigationDrawer.Navigation
import com.example.drawer.data.utils.AuthManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DrawerScreen(auth: AuthManager, navigation: NavController) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val context = LocalContext.current


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope, scaffoldState) },
        drawerBackgroundColor = MaterialTheme.colors.onBackground,
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        },
        backgroundColor = MaterialTheme.colors.onPrimary,
        content = {
            Box(modifier = Modifier.padding(it)) {
                Navigation(navController, auth, context, navigation)
            }
        },
        //bottomBar = { MyBottomBar(navController) }
    )
}


@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = {
            Text(
                text = "",
                fontSize = 18.sp,
                color = MaterialTheme.colors.secondary
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menú",
                    tint = MaterialTheme.colors.secondary
                )
            }
        },
        backgroundColor = Color(0xFFFC92B5),
        contentColor = MaterialTheme.colors.surface
    )
}

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController) {
    val items = listOf(
        AppScreen.Home,
        AppScreen.Perfil,
        AppScreen.Servicios,
        AppScreen.Reserva,
        AppScreen.Preguntas
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.primaryVariant
                    )
                ),
                shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(17.dp)
        ) {
            // Contenido del Drawer

            // Encabezado
            Image(
                painter = painterResource(id = R.drawable.logoocentro),
                contentDescription = R.string.perfil.toString(),
                modifier = Modifier
                    .height(230.dp)
                    .fillMaxWidth()
                    .padding(2.dp)
            )

            // Espacio intermedio
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .background(color = Color(0xFFBB6BB8))
                    .padding(horizontal = 5.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Lista de Items de navegación
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { item ->
                DrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })

                // Agregar espacio entre elementos
                Spacer(modifier = Modifier.height(14.dp))
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        // Contenido adicional en la parte inferior del Drawer
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color(252, 146, 181, 130))
        ) {
            Text(
                text = "",
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}


@Composable
fun DrawerItem(item: AppScreen, selected: Boolean, onItemClick: (AppScreen) -> Unit) {
    val background =
        if (selected) MaterialTheme.colors.primary else MaterialTheme.colors.primaryVariant
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .background(background)
            .padding(start = 10.dp)
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(35.dp)
                .width(35.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}

/*
@Composable
fun MyBottomBar(navController: NavHostController) {
    val listItems = listOf(
        AppScreen.Servicios,
        AppScreen.Reserva,
        AppScreen.Perfil
    )
    BottomNavigation(
        backgroundColor = Color(0xFFFC92B5)
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        listItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // volver a seleccionar el mismo elemento
                        // Evite múltiples copias del mismo destino cuando
                        launchSingleTop = true
                        // Restaurar estado al volver a seleccionar un elemento previamente
                        // seleccionado
                        restoreState = true
                    }
                },
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(30.dp)
                            .width(30.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = MaterialTheme.colors.surface,
            )
        }
    }
}*/
