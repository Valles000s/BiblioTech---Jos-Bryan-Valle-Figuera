package com.example.bibliotech.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bibliotech.model.Estudiante

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEditarEstudiante(
    estudiante: Estudiante,
    onGuardar: (Estudiante) -> Unit,
    onCancelar: () -> Unit
) {
    var carnet by remember { mutableStateOf(estudiante.carnet) }
    var nombres by remember { mutableStateOf(estudiante.nombres) }
    var apellidos by remember { mutableStateOf(estudiante.apellidos) }

    val grados = listOf(
        "1° Bachillerato",
        "2° Bachillerato",
        "3° Bachillerato"
    )
    var grado by remember { mutableStateOf(if (grados.contains(estudiante.grado)) estudiante.grado else grados[0]) }
    var expandirGrado by remember { mutableStateOf(false) }

    val secciones = listOf("A", "B", "C")
    var seccion by remember { mutableStateOf(if (secciones.contains(estudiante.seccion)) estudiante.seccion else secciones[0]) }
    var expandirSeccion by remember { mutableStateOf(false) }

    var activo by remember { mutableStateOf(estudiante.activo) }

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
                title = { Text(text = "Editar Estudiante", color = Color.White) },
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
            Text(
                text = "Modificar información del estudiante",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = carnet,
                onValueChange = { carnet = it },
                label = { Text(text = "Carnet") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nombres,
                onValueChange = { nombres = it },
                label = { Text(text = "Nombres") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text(text = "Apellidos") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors,
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = expandirGrado,
                onExpandedChange = { expandirGrado = !expandirGrado },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = grado,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Grado") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirGrado) },
                    colors = fieldColors,
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandirGrado,
                    onDismissRequest = { expandirGrado = false }
                ) {
                    grados.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                grado = opcion
                                expandirGrado = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = expandirSeccion,
                onExpandedChange = { expandirSeccion = !expandirSeccion },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = seccion,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sección") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirSeccion) },
                    colors = fieldColors,
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandirSeccion,
                    onDismissRequest = { expandirSeccion = false }
                ) {
                    secciones.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                seccion = opcion
                                expandirSeccion = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Estado: ${if (activo) "Activo" else "Inactivo"}",
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = activo,
                    onCheckedChange = { activo = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val estudianteEditado = estudiante.copy(
                        carnet = carnet.trim(),
                        nombres = nombres.trim(),
                        apellidos = apellidos.trim(),
                        grado = grado,
                        seccion = seccion,
                        activo = activo
                    )
                    onGuardar(estudianteEditado)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = carnet.isNotBlank() && nombres.isNotBlank() && apellidos.isNotBlank()
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
