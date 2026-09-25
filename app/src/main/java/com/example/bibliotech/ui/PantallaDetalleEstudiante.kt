package com.example.bibliotech.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bibliotech.model.Estudiante

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalleEstudiante(
    estudiante: Estudiante,
    onRegresar: () -> Unit,
    navController: NavHostController? = null,
    onEditar: ((Int) -> Unit)? = null,
    onEliminar: (Estudiante) -> Unit
) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    val mensajeEditado = navController?.currentBackStackEntry
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
                title = { Text(text = "Detalles del Estudiante", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onRegresar) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(Color.Black)
                .padding(all = 20.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Estudiante",
                modifier = Modifier.size(48.dp),
                tint = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${estudiante.nombres} ${estudiante.apellidos}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Carnet: ${estudiante.carnet}",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Grado: ${estudiante.grado}",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Sección: ${estudiante.seccion}",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Estado: ",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                        Text(
                            text = if (estudiante.activo) "Activo" else "Inactivo",
                            color = if (estudiante.activo) Color(0xFF4ADE80) else Color(0xFFEF4444),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (onEditar != null) {
                    Button(
                        onClick = { onEditar(estudiante.id) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                    ) {
                        Text(text = "Editar")
                    }
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
                    title = { Text(text = "Eliminar Estudiante") },
                    text = { Text(text = "¿Estás seguro de que deseas eliminar a este estudiante?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                mostrarDialogo = false
                                onEliminar(estudiante)
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
