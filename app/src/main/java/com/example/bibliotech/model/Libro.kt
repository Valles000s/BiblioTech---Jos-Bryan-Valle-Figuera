package com.example.bibliotech.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * ENTIDAD LIBRO
 * Cada objeto Libro será una fila dentro de la tabla llamada "libros".
 */
@Entity(tableName = "libros")
data class Libro(
    // Llave primaria auto-generada por Room/SQLite
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Título del libro
    val titulo: String,

    // Nombre del autor
    val autor: String,

    // Género o categoría
    val categoria: String,

    // Año de publicación
    val anio: Int,

    // Indica si el libro está disponible para préstamo
    val disponible: Boolean,

    // Breve descripción o resumen del libro
    val descripcion: String
)
