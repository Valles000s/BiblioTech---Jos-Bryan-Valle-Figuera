package com.example.bibliotech.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bibliotech.model.Libro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleLibro(
    libro: Libro,
    onRegresar: () -> Unit,
    navController: NavHostController,
    onEditar: (Int) -> Unit,
    onEliminar: (Libro) -> Unit,
) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    // Podemos observar mensajes que vengan de la pantalla de edición
    val mensajeEditado = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<String?>("mensaje", null)
        ?.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(mensajeEditado?.value) {
        mensajeEditado?.value?.let { texto ->
            snackbarHostState.showSnackbar(texto)
            navController.currentBackStackEntry?.savedStateHandle?.remove<String>("mensaje")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalles del Libro", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(all = 20.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                contentDescription = "Libro",
                modifier = Modifier.height(30.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = libro.titulo,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Autor: ${libro.autor}", color = Color.White)
            Text(text = "Categoría: ${libro.categoria}", color = Color.White)
            Text(text = "Año: ${libro.anio}", color = Color.White)
            Text(text = "Descripción: ${libro.descripcion}", color = Color.White)
            Text(
                text = "Disponible: ${if (libro.disponible) "Sí" else "No"}",
                color = if (libro.disponible) Color.Green else Color.Red
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onEditar(libro.id) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                ) {
                    Text(text = "Editar")
                }

                Button(
                    onClick = { mostrarDialogo = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = "Eliminar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onRegresar,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text(text = "Regresar")
            }

            if (mostrarDialogo) {
                AlertDialog(
                    onDismissRequest = { mostrarDialogo = false },
                    title = { Text(text = "Eliminar Libro") },
                    text = { Text(text = "¿Estás seguro de que deseas eliminar este libro?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                mostrarDialogo = false
                                onEliminar(libro)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(text = "Eliminar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { mostrarDialogo = false }) {
                            Text(text = "Cancelar")
                        }
                    }
                )
            }
        }
    }
}
