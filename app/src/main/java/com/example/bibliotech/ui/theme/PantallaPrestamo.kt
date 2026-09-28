package com.example.bibliotech.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bibliotech.model.Libro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrestamo(
    libros: List<Libro>,
    onRegistrar: (Int, String) -> Unit,
    onRegresar: () -> Unit
) {
    var libroIdStr by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color(0xFF3B82F6),
        unfocusedBorderColor = Color.White,
        focusedLabelColor = Color(0xFF60A5FA),
        unfocusedLabelColor = Color.LightGray,
        cursorColor = Color.White,
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White
    )

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text(text = "Registrar Préstamo", color = Color.White) },
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
                .verticalScroll(rememberScrollState())
                .padding(all = 20.dp)
        ) {
            Text(
                text = "Ingresa los datos para registrar el préstamo",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = libroIdStr,
                onValueChange = { libroIdStr = it },
                label = { Text("ID del Libro") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = { Text("Nombre del Alumno") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val id = libroIdStr.toIntOrNull()
                    if (id != null && nombreUsuario.isNotBlank()) {
                        val libro = libros.find { it.id == id }
                        if (libro != null) {
                            if (libro.disponible) {
                                onRegistrar(id, nombreUsuario)
                                mensaje = "✓ Préstamo registrado con éxito"
                                libroIdStr = ""
                                nombreUsuario = ""
                            } else {
                                mensaje = "❌ El libro no está disponible"
                            }
                        } else {
                            mensaje = "❌ Libro no encontrado"
                        }
                    } else {
                        mensaje = "⚠️ Por favor, complete los campos correctamente"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Préstamo")
            }

            if (mensaje.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = mensaje,
                    color = if (mensaje.startsWith("✓")) Color(0xFF4ADE80) else Color(0xFFEF4444),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onRegresar,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text("Regresar")
            }
        }
    }
}
