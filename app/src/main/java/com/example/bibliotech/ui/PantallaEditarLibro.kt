package com.example.bibliotech.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bibliotech.model.Libro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEditarLibro(
    libro: Libro,
    onGuardar: (Libro) -> Unit,
    onCancelar: () -> Unit
) {
    var titulo by remember { mutableStateOf(libro.titulo) }
    var autor by remember { mutableStateOf(libro.autor) }
    var categoria by remember { mutableStateOf(libro.categoria) }
    var anio by remember { mutableStateOf(libro.anio.toString()) }
    var descripcion by remember { mutableStateOf(libro.descripcion) }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text(text = "Editar Libro", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(color = Color.Black)
                .verticalScroll(state = rememberScrollState())
                .padding(all = 20.dp)
        ) {
            Text(text = "Modificar información del libro", color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))

            val fieldColors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF3B82F6),
                unfocusedBorderColor = Color.White,
                focusedLabelColor = Color(0xFF60A5FA),
                unfocusedLabelColor = Color.LightGray,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )

            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text(text = "Título") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = autor,
                onValueChange = { autor = it },
                label = { Text(text = "Autor") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text(text = "Categoría") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = anio,
                onValueChange = { anio = it },
                label = { Text(text = "Año") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text(text = "Descripción") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val libroEditado = libro.copy(
                        titulo = titulo.trim(),
                        autor = autor.trim(),
                        categoria = categoria.trim(),
                        anio = anio.toIntOrNull() ?: libro.anio,
                        descripcion = descripcion.trim()
                    )
                    onGuardar(libroEditado)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Guardar Cambios")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text(text = "Cancelar")
            }
        }
    }
}
