package com.example.bibliotech.ui.theme.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bibliotech.model.Libro

@Composable
fun TarjetaLibro(libro: Libro, onVerDetalles: () -> Unit) {
    Card(
        onClick = onVerDetalles,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = libro.titulo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Autor: ${libro.autor}",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "${libro.categoria} | ${libro.anio}",
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (libro.disponible) "Disponible" else "Prestado",
                    fontSize = 14.sp,
                    color = if (libro.disponible) Color(0xFF4CAF50) else Color.Red,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
} 