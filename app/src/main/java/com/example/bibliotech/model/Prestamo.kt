package com.example.bibliotech.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Prestamos",
    foreignKeys = [
        ForeignKey(
            entity = Libro::class,
            parentColumns = ["id"],
            childColumns = ["libroId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Estudiante::class,
            parentColumns = ["id"],
            childColumns = ["estudianteId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Prestamo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //Libro Prestado
    val libroId: Int,
    //Estudiante que lo presta
    val estudianteId: Int,
    //Fecha en que se presta
    val fechaPrestamo: String,
    //Fecha en que se debe devolver
    val fechaDevolucion: String,
    //Estado del préstamo
    val devuelto: Boolean = false
)
