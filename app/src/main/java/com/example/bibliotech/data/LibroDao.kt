package com.example.bibliotech.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bibliotech.model.Libro

@Dao
interface LibroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertarLibro(libro: Libro): Long

    @Query("SELECT * FROM libros")
    fun obtenerLibros(): List<Libro>

    @Query("SELECT * FROM libros WHERE id = :id")
    fun obtenerLibroPorId(id: Int): Libro?

    @Update
    fun actualizarLibro(libro: Libro)

    @Delete
    fun eliminarLibro(libro: Libro)
}
