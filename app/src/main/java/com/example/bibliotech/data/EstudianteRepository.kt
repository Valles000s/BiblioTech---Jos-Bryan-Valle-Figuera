package com.example.bibliotech.data

import com.example.bibliotech.model.Estudiante

class EstudianteRepository(private val estudianteDao: EstudianteDao) {

    fun insertarEstudiante(estudiante: Estudiante) = estudianteDao.insertarEstudiante(estudiante)

    fun obtenerEstudiantes() = estudianteDao.obtenerEstudiantes()

    fun obtenerEstudiantePorId(id: Int) = estudianteDao.obtenerEstudiantePorId(id)

    fun actualizarEstudiante(estudiante: Estudiante) = estudianteDao.actualizarEstudiante(estudiante)

    fun eliminarEstudiante(estudiante: Estudiante) = estudianteDao.eliminarEstudiante(estudiante)
}
