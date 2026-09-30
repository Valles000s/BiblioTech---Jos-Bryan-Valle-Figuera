package com.example.bibliotech.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bibliotech.model.Prestamo

@Dao
interface PrestamoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(prestamo: Prestamo): Long

    @Query("SELECT * FROM Prestamos WHERE devuelto = 0")
    fun obtenerPrestamosActivos(): List<Prestamo>

    @Query("SELECT * FROM Prestamos")
    fun obtenerTodosLosPrestamos(): List<Prestamo>

    @Query("SELECT * FROM Prestamos WHERE id = :id")
    fun obtenerPrestamoPorId(id: Int): Prestamo?

    @Query("SELECT * FROM Prestamos WHERE estudianteId = :estudianteId")
    fun obtenerPrestamosPorEstudiante(estudianteId: Int): List<Prestamo>

    @Update
    fun actualizarPrestamo(prestamo: Prestamo)

    @Query("DELETE FROM Prestamos WHERE id = :id")
    fun eliminarPrestamoPorId(id: Int)
}
