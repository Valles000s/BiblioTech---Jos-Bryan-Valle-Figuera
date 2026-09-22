package com.example.bibliotech

import android.app.Application
import com.example.bibliotech.data.BibliotecaDatabase
import com.example.bibliotech.data.DatabaseProvider
import com.example.bibliotech.data.LibroRepository
import com.example.bibliotech.data.EstudianteRepository

/**
 * BibliotecaApplication inicializa la base de datos y el repositorio
 * al arrancar la aplicación.
 */
class BibliotecaApplication : Application() {

    val database: BibliotecaDatabase by lazy {
        DatabaseProvider.getDatabase(this)
    }

    val libroDao
        get() = database.libroDao()

    val libroRepository: LibroRepository by lazy {
        LibroRepository(libroDao)
    }

    val estudianteDao
        get() = database.estudianteDao()

    val estudianteRepository: EstudianteRepository by lazy {
        EstudianteRepository(estudianteDao)
    }
}
