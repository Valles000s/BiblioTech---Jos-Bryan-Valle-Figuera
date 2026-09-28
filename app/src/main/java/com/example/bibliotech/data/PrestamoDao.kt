package com.example.bibliotech.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bibliotech.model.Prestamo
import android.icu.text.MessagePattern.ArgType.SELECT

@Dao
interface PrestamoDao {

    //Aquí se hace el crud y métodos necesarios para trabajar
    //con el ROOM

    @Insert
    fun insertarPrestamo(prestamo: Prestamo)
    @Query("SELECT * FROM Prestamos WHERE devuelto = 0")
    fun obtenerPrestamosActivos(): List<Prestamo>
    @Query("SELECT * FROM Prestamos WHERE id = :id")
    fun obtenerPrestamoPorId(id: Int): Prestamo?

}