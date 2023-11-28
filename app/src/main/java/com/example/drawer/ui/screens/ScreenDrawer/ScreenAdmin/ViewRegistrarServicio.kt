package com.example.drawer.ui.screens.ScreenDrawer.ScreenAdmin

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.drawer.R
import com.example.drawer.data.model.ReservaViewModel
import com.example.drawer.data.model.Servicios
import com.example.drawer.data.utils.FirestoreManager
import com.example.drawer.ui.screens.ScreenDrawer.Espacio
import com.example.drawer.ui.screens.ScreenDrawer.ScreenCitas.ViewAgendar.Emcabezado
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

const val REQUEST_CODE = 0
@Composable
fun RegistrarServicios(
    viewModel: ReservaViewModel,
    firestore: FirestoreManager,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var servicio by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    // Mueve la declaración de 'gallery' fuera de la función Column
    val selectedImageUri by viewModel.selectedImage.observeAsState()
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                // En lugar de subir la imagen inmediatamente, la guardamos en el ViewModel
                viewModel.selectImage(it)
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
            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp)
                        .weight(1f)
                ) {
                    item {
                        Espacio(dp = 10)
                        Emcabezado(text = "Registrar Servicios")
                        Espacio(dp = 10)
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logoocentro),
                                contentDescription = "logo inicio",
                                modifier = Modifier
                                    .height(150.dp)
                                    .width(300.dp)
                                    .padding(2.dp)
                            )
                            OutlinedTextField(
                                label = { Text(text = "Nombre del servicio") },
                                value = servicio,
                                onValueChange = { servicio = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                label = { Text(text = "Descripción") },
                                value = description,
                                onValueChange = { description = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(170.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                label = { Text(text = "Precio") },
                                value = price,
                                onValueChange = { price = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = {
                                    val permissionCheckResult = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                    )
                                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                        galleryLauncher.launch("image/*")
                                    } else {
                                        val activity = context as? Activity
                                        activity?.let {
                                            ActivityCompat.requestPermissions(
                                                it,
                                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                                REQUEST_CODE
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .width(180.dp)
                                    .height(50.dp)
                            ) {
                                Text(text = "Selecciona una imagen", textAlign = TextAlign.Center)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            if (selectedImageUri != null) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 200.dp)
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    elevation = 8.dp
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp)
                                    ) {
                                        // Vista previa de la imagen seleccionada
                                        selectedImageUri?.let { uri ->
                                            Image(
                                                painter = rememberAsyncImagePainter(model = uri),
                                                contentDescription = "Selected Image",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(150.dp),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
                Button(
                    onClick = {
                        scope.launch {
                            val imageUrl = viewModel.uploadImage(context)
                            val newServicio = Servicios(
                                name = servicio,
                                descrip = description,
                                precio = price.toDoubleOrNull()
                                    ?: 0.0,
                                imageUrl = imageUrl
                            )
                            firestore.addServicio(newServicio)
                        }
                        viewModel.clearSelectedImage()

                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .width(300.dp)
                        .height(60.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp)
                ) {
                    Text("Guardar")
                }
                Espacio(dp = 10)
            }
        }
    }
}
@Composable
fun CoilImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = imageUrl)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                transformations(
                    RoundedCornersTransformation(
                        topLeft = 20f,
                        topRight = 20f,
                        bottomLeft = 20f,
                        bottomRight = 20f
                    )
                )
            }).build()
    )
    Box(modifier = modifier.padding(6.dp)) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
        )
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                // Muestra un indicador de carga mientras la imagen se está cargando
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }

            is AsyncImagePainter.State.Error -> {
                // Muestra un mensaje de error si no se pudo cargar la imagen
                Text("Error al cargar la imagen", Modifier.align(Alignment.Center))
            }

            else -> {}
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}
