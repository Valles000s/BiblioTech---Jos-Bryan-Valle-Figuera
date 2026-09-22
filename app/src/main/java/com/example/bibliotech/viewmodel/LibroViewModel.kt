package com.example.bibliotech.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotech.BibliotecaApplication
import com.example.bibliotech.model.Libro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//LibroViewmodel será el encargado de solicitar los datos al Repository

class LibroViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as BibliotecaApplication).libroRepository
    //CREAMOS UN ESTADO QUE ALMACENA LA LISTA DE LIBROS, INICIALMENTE VACIA

    private val _libros = MutableStateFlow<List<Libro>>(emptyList())
    val libros: StateFlow<List<Libro>> = _libros.asStateFlow()

    //Obtenemos el libro sleccionado

    private val _libroSeleccionado = MutableStateFlow<Libro?>(null)
    val libroSeleccionado: StateFlow<Libro?> = _libroSeleccionado.asStateFlow()
    //METODO QUE LLENA EL VIEWMODEL CONSULTAND AL ROOM Y ACTUALIZA EL ESTADO
    //OSEA LLENA LA LISTA *_libros*

    fun cargarLibros() {
        viewModelScope.launch(Dispatchers.IO) {
            _libros.value = repository.obtenerLibros()
        }
    }

    fun insertarLibro(libro: Libro) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertarLibro(libro)
            _libros.value = repository.obtenerLibros()
        }
    }

    fun cargarLibroPorId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _libroSeleccionado.value = repository.obtenerLibroPorId(id)
        }
    }

    fun eliminarLibro(libro: Libro) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.eliminarLibro(libro)
            _libros.value = repository.obtenerLibros()
        }
    }

    //Metodo para actualizar u libro
    fun actualizarLibro(libro: Libro) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.actualizarLibro(libro)
            _libros.value = repository.obtenerLibros()
            _libroSeleccionado.value = repository.obtenerLibroPorId(libro.id)
        }
    }


}
