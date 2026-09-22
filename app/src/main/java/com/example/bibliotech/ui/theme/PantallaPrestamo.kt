package com.example.bibliotech.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bibliotech.model.Libro

@Composable
fun PantallaPrestamo(
    libros: List<Libro>,
    onRegistrar: (Int, String) -> Unit,
    onRegresar: () -> Unit
) {
    var libroIdStr by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Registrar préstamo",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = libroIdStr,
            onValueChange = { libroIdStr = it },
            label = { Text("ID del Libro") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = nombreUsuario,
            onValueChange = { nombreUsuario = it },
            label = { Text("Nombre del Alumno") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val id = libroIdStr.toIntOrNull()
                if (id != null && nombreUsuario.isNotBlank()) {
                    val libro = libros.find { it.id == id }
                    if (libro != null) {
                        if (libro.disponible) {
                            onRegistrar(id, nombreUsuario)
                            mensaje = "Préstamo registrado con éxito"
                            libroIdStr = ""
                            nombreUsuario = ""
                        } else {
                            mensaje = "El libro no está disponible"
                        }
                    } else {
                        mensaje = "Libro no encontrado"
                    }
                } else {
                    mensaje = "Por favor, complete los campos correctamente"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar Préstamo")
        }

        if (mensaje.isNotBlank()) {
            Text(
                text = mensaje,
                modifier = Modifier.padding(top = 16.dp),
                color = if (mensaje.contains("éxito")) androidx.compose.ui.graphics.Color.Green else androidx.compose.ui.graphics.Color.Red
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onRegresar,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Regresar")
        }
    }
}
