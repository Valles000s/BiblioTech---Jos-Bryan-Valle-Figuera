package com.example.bibliotech.ui.theme.componentes

import android.app.Application
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.bibliotech.BibliotecaApplication
import com.example.bibliotech.ui.BibliotecaViewModel
import com.example.bibliotech.ui.PantallaAgregarLibro
import com.example.bibliotech.ui.PantallaAgregarEstudiante
import com.example.bibliotech.ui.PantallaDetalleLibro
import com.example.bibliotech.ui.PantallaEditarLibro
import com.example.bibliotech.ui.PantallaEstudiantes
import com.example.bibliotech.ui.PantallaPrincipal
import com.example.bibliotech.ui.theme.PantallaCatalogo
import com.example.bibliotech.ui.theme.PantallaLibrosPrestados
import com.example.bibliotech.ui.theme.PantallaPrestamo
import com.example.bibliotech.viewmodel.EstudianteViewModel
import com.example.bibliotech.viewmodel.LibroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navegacion(
    navController: NavHostController,
    bibliotecaViewModel: BibliotecaViewModel = viewModel()
) {
    val app = LocalContext.current.applicationContext as BibliotecaApplication
    val libroViewModel: LibroViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return LibroViewModel(app) as T
            }
        }
    )

    var mensajeGlobal by remember { mutableStateOf<String?>(null) }

    NavHost(
        navController = navController,
        startDestination = "inicio"
    ) {
        composable("inicio") {
            PantallaPrincipal(
                onCatalogo = { navController.navigate("catalogo") },
                onPrestamo = { navController.navigate("prestamo") },
                onPrestados = { navController.navigate("prestados") },
                onEstudiantes = { navController.navigate("estudiantes") }
            )
        }

        composable("catalogo") {
            PantallaCatalogo(
                viewModel = libroViewModel,
                onVerDetalles = { idLibro -> navController.navigate("detalle/$idLibro") },
                onRegresar = { navController.popBackStack() },
                onAgregarLibro = { navController.navigate("agregar") },
                mensaje = mensajeGlobal,
                onMensajeMostrado = { mensajeGlobal = null }
            )
        }

        composable("agregar") {
            PantallaAgregarLibro(
                viewModel = libroViewModel,
                onGuardar = {
                    mensajeGlobal = "✅ Libro guardado con éxito"
                    navController.popBackStack()
                },
                onCancelar = { navController.popBackStack() }
            )
        }

        composable(
            route = "detalle/{idLibro}",
            arguments = listOf(navArgument("idLibro") { type = NavType.IntType })
        ) { backStackEntry ->
            val idLibro = backStackEntry.arguments?.getInt("idLibro")
            val libro by libroViewModel.libroSeleccionado.collectAsState()

            LaunchedEffect(idLibro) {
                if (idLibro != null) {
                    libroViewModel.cargarLibroPorId(idLibro)
                }
            }

            if (libro != null) {
                PantallaDetalleLibro(
                    libro = libro!!,
                    onRegresar = { navController.popBackStack() },
                    navController = navController,
                    onEditar = { id -> 
                        navController.navigate("editar/$id") 
                    },
                    onEliminar = { libroToDelete ->
                        libroViewModel.eliminarLibro(libroToDelete)
                        mensajeGlobal = "🗑️ Libro eliminado con éxito"
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            route = "editar/{idLibro}",
            arguments = listOf(navArgument("idLibro") { type = NavType.IntType })
        ) { backStackEntry ->
            val idLibro = backStackEntry.arguments?.getInt("idLibro")
            val libro by libroViewModel.libroSeleccionado.collectAsState()

            LaunchedEffect(idLibro) {
                if (idLibro != null) {
                    libroViewModel.cargarLibroPorId(idLibro)
                }
            }

            if (libro != null) {
                PantallaEditarLibro(
                    libro = libro!!,
                    onGuardar = { libroEditado ->
                        libroViewModel.actualizarLibro(libroEditado)
                        // Enviamos el mensaje a la pantalla anterior (Detalle)
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("mensaje", "✓ Cambios guardados correctamente")
                        navController.popBackStack()
                    },
                    onCancelar = { navController.popBackStack() }
                )
            }
        }

        composable("prestamo") {
            PantallaPrestamo(
                libros = bibliotecaViewModel.libros,
                onRegistrar = { libroId, nombre ->
                    bibliotecaViewModel.registrarPrestamo(libroId, nombre)
                },
                onRegresar = { navController.popBackStack() }
            )
        }

        composable("prestados") {
            PantallaLibrosPrestados(
                prestamos = bibliotecaViewModel.prestamos,
                getNombreLibro = { id -> bibliotecaViewModel.getNombreLibro(id) },
                onRegresar = { navController.popBackStack() }
            )
        }

        composable("estudiantes") {
            PantallaEstudiantes(
                onRegresar = { navController.popBackStack() },
                onVerDetalles = { },
                onAgregarEstudiante = { navController.navigate("agregarEstudiante") },
                mensaje = mensajeGlobal,
                onMensajeMostrado = { mensajeGlobal = null }
            )
        }

        composable("agregarEstudiante") {
            val estudianteViewModel: EstudianteViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return EstudianteViewModel(app) as T
                    }
                }
            )
            PantallaAgregarEstudiante(
                viewModel = estudianteViewModel,
                onGuardar = {
                    mensajeGlobal = "✅ Estudiante guardado con éxito"
                    navController.popBackStack()
                },
                onCancelar = { navController.popBackStack() }
            )
        }
    }
}
