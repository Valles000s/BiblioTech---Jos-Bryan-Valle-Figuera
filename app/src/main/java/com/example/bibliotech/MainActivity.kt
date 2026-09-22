package com.example.bibliotech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.bibliotech.data.librosPrueba
import com.example.bibliotech.ui.theme.componentes.Navegacion
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as BibliotecaApplication
        val repository = app.libroRepository

        lifecycleScope.launch(Dispatchers.IO) {

            if (repository.obtenerLibros().isEmpty()) {
                librosPrueba.forEach { libro ->
                    repository.insertarLibro(libro)
                }
            }

            val librosGuardados = repository.obtenerLibros()

            println("LIBROS EN ROOM: ${librosGuardados.size}")

            librosGuardados.forEach {
                println("Libro: ${it.id} - ${it.titulo}")
            }
        }

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Navegacion(navController = navController)
                }
            }
        }
    }
}
