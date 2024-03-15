package com.example.simple_room_project.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class AuthorWithBooks (
    @Embedded
    val author: AuthorEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "authorId"
    )
    val books: List<BookEntity>
)

//Esto habría que meterlo en el DAO
//@Transaction
//@Query("SELECT * FROM AuthorEntity")
//fun getAuthorWithBooks(): List<AuthorWithBooks>