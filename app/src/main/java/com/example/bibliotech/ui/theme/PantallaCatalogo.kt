package com.example.bibliotech.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bibliotech.ui.theme.componentes.TarjetaLibro
import com.example.bibliotech.viewmodel.LibroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCatalogo(
    viewModel: LibroViewModel,
    onVerDetalles: (Int) -> Unit,
    onRegresar: () -> Unit,
    onAgregarLibro: () -> Unit,
    mensaje: String? = null,
    onMensajeMostrado: () -> Unit = {}
) {
    val libros by viewModel.libros.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    
    LaunchedEffect(Unit) {
        viewModel.cargarLibros()
    }

    LaunchedEffect(mensaje) {
        mensaje?.let {
            snackbarHostState.showSnackbar(it)
            onMensajeMostrado()
        }
    }
    
    var textoBusqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    
    val categorias = remember(libros) {
        listOf("Todos") + libros.map { it.categoria }.distinct()
    }
    
    val librosFiltrados = libros.filter { libro ->
        val coincideTexto = libro.titulo.contains(textoBusqueda, ignoreCase = true) ||
                           libro.autor.contains(textoBusqueda, ignoreCase = true)
        val coincideCategoria = (categoriaSeleccionada == "Todos" || libro.categoria == categoriaSeleccionada)
        coincideTexto && coincideCategoria
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButtonPosition = FabPosition.Start,
        floatingActionButton = {
            FloatingActionButton(onClick = onAgregarLibro, containerColor = Color.DarkGray) {
                Text(text = "+", color = Color.White, fontSize = 24.sp)
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(text = "Catálogo de Libros") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = padding)
                .padding(all = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.LibraryBooks,
                    contentDescription = "Icono Catálogo",
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "Catálogo de libros",
                    fontSize = 24.sp
                )
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(items = categorias) { categoria ->
                    FilterChip(
                        selected = categoriaSeleccionada == categoria,
                        onClick = { categoriaSeleccionada = categoria },
                        label = { Text(text = categoria) }
                    )
                }
            }

            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                label = { Text(text = "Buscar libro o autor") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Icono Buscar"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            if (librosFiltrados.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.LibraryBooks,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No se encontraron libros",
                        fontWeight = FontWeight.Bold,
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(items = librosFiltrados) { libro ->
                        TarjetaLibro(
                            libro = libro,
                            onVerDetalles = { onVerDetalles(libro.id) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRegresar,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Regresar")
                }
            }
        }
    }
}
