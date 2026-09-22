package com.example.bibliotech.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.bibliotech.data.librosPrueba
import com.example.bibliotech.model.Libro

data class Prestamo(
    val id: Int,
    val libroId: Int,
    val nombreUsuario: String
)

class BibliotecaViewModel : ViewModel() {
    private val _libros = mutableStateListOf<Libro>().apply { addAll(librosPrueba) }
    val libros: List<Libro> get() = _libros

    private val _prestamos = mutableStateListOf<Prestamo>()
    val prestamos: List<Prestamo> get() = _prestamos

    fun registrarPrestamo(libroId: Int, nombreUsuario: String) {
        val index = _libros.indexOfFirst { it.id == libroId }
        if (index != -1 && _libros[index].disponible) {
            _libros[index] = _libros[index].copy(disponible = false)
            _prestamos.add(
                Prestamo(
                    id = _prestamos.size + 1,
                    libroId = libroId,
                    nombreUsuario = nombreUsuario
                )
            )
        }
    }

    fun getNombreLibro(libroId: Int): String {
        return _libros.find { it.id == libroId }?.titulo ?: "Desconocido"
    }
}
