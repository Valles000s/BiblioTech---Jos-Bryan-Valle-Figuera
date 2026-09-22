package com.example.bibliotech.data

import com.example.bibliotech.model.Libro

val librosPrueba = listOf(
    Libro(
        id = 1,
        titulo = "El principito",
        autor = "Antoine de Saint-Exupéry",
        categoria = "Infantil",
        anio = 1943,
        disponible = true,
        descripcion = "Una fábula poética y filosófica que relata la historia de un joven príncipe que viaja de asteroide en asteroide."
    ),
    Libro(
        id = 2,
        titulo = "1984",
        autor = "George Orwell",
        categoria = "Novela",
        anio = 1949,
        disponible = true,
        descripcion = "Una novela distópica que explora los peligros del totalitarismo y la vigilancia gubernamental."
    ),
    Libro(
        id = 3,
        titulo = "Cien años de soledad",
        autor = "Gabriel García Márquez",
        categoria = "Realismo Mágico",
        anio = 1967,
        disponible = false,
        descripcion = "La épica historia de la familia Buendía en el pueblo ficticio de Macondo."
    ),
    Libro(
        id = 4,
        titulo = "Don Quijote de la Mancha",
        autor = "Miguel de Cervantes",
        categoria = "Novela",
        anio = 1605,
        disponible = true,
        descripcion = "Las aventuras de un hidalgo que, tras leer demasiados libros de caballería, decide hacerse caballero andante."
    ),
    Libro(
        id = 5,
        titulo = "El Hobbit",
        autor = "J.R.R. Tolkien",
        categoria = "Fantasía",
        anio = 1937,
        disponible = true,
        descripcion = "El viaje inesperado de Bilbo Bolsón para recuperar un tesoro custodiado por el dragón Smaug."
    )
)
