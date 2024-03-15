package com.example.simple_room_project.domain.model

import java.time.LocalDate

data class Author (
    val id: Int,
    val name: String,
    val birthDate: LocalDate,
    val books: List<Book>
)