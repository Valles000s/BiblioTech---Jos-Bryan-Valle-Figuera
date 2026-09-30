package com.example.bibliotech.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotech.BibliotecaApplication
import com.example.bibliotech.model.Estudiante
import com.example.bibliotech.model.Libro
import com.example.bibliotech.model.Prestamo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PrestamosViewModel(application: Application) : AndroidViewModel(application) {

    // ---- Traemos todos los repositorios
    private val prestamosRepository =
        (application as BibliotecaApplication).prestamoRepository

    private val librosRepository =
        (application as BibliotecaApplication).libroRepository

    private val estudiantesRepository =
        (application as BibliotecaApplication).estudianteRepository

    // LIBROS DISPONIBLES
    private val _librosDisponibles =
        MutableStateFlow<List<Libro>>(emptyList())

    val librosDisponibles: StateFlow<List<Libro>> =
        _librosDisponibles.asStateFlow()

    // ESTUDIANTES ACTIVOS
    private val _estudiantesActivos =
        MutableStateFlow<List<Estudiante>>(emptyList())

    val estudiantesActivos: StateFlow<List<Estudiante>> =
        _estudiantesActivos.asStateFlow()

    // PRESTAMOS ACTIVOS
    private val _prestamosActivos =
        MutableStateFlow<List<Prestamo>>(emptyList())

    val prestamosActivos: StateFlow<List<Prestamo>> =
        _prestamosActivos.asStateFlow()

    // SI EL PRESTAMO ESTA GUARDADO
    private val _prestamoGuardado =
        MutableStateFlow<Boolean>(false)

    val prestamoGuardado: StateFlow<Boolean> =
        _prestamoGuardado.asStateFlow()

    // TRAER LOS DATOS AL MOMENTO DE HACER EL REGISTRO
    fun cargarDatos() {
        viewModelScope.launch(Dispatchers.IO) {
            // Obtener todos los libros
            val libros = librosRepository.obtenerLibros()

            // Dejamos únicamente los libros disponibles
            _librosDisponibles.value = libros.filter { it.disponible }

            // Obtener todos los estudiantes
            val estudiantes = estudiantesRepository.obtenerEstudiantes()

            // Dejamos únicamente los estudiantes activos
            _estudiantesActivos.value = estudiantes.filter { it.activo }

            // Obtener todos los préstamos activos
            _prestamosActivos.value = prestamosRepository.obtenerPrestamosActivos()
        }
    }

    // GUARDAR EL PRESTAMO
    fun registrarPrestamo(idLibro: Int, idEstudiante: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            // Buscamos el libro que se ha seleccionado
            val libro = librosRepository.obtenerLibroPorId(idLibro)

            // Buscamos el estudiante que se ha seleccionado
            val estudiante = estudiantesRepository.obtenerEstudiantePorId(idEstudiante)

            // Verificamos que ese libro exista, esté disponible y el estudiante exista
            if (libro == null || !libro.disponible || estudiante == null) {
                return@launch
            }

            // Obtenemos la fecha actual en el instante de guardar préstamo
            val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            // Creamos el nuevo préstamo
            val nuevoPrestamo = Prestamo(
                libroId = idLibro,
                estudianteId = idEstudiante,
                fechaPrestamo = fechaActual,
                fechaDevolucion = "",
                devuelto = false
            )

            // Guardamos el préstamo
            prestamosRepository.insertarPrestamo(nuevoPrestamo)

            // Poner el libro disponible como falso porque se acaba de prestar
            val libroActualizado = libro.copy(disponible = false)
            librosRepository.actualizarLibro(libroActualizado)

            // Actualizar los datos de las listas
            val librosActualizados = librosRepository.obtenerLibros()
            _librosDisponibles.value = librosActualizados.filter { it.disponible }
            _prestamosActivos.value = prestamosRepository.obtenerPrestamosActivos()
            _prestamoGuardado.value = true
        }
    }

    fun resetearEstadoGuardado() {
        _prestamoGuardado.value = false
    }
}

// Alias para mantener compatibilidad con cualquier nombre
typealias PrestamoViewModel = PrestamosViewModel
