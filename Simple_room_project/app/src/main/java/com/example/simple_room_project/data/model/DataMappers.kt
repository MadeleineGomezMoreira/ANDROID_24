package com.example.simple_room_project.data.model

import com.example.simple_room_project.domain.model.Author
import com.example.simple_room_project.domain.model.Book

fun AuthorWithBooks.toDomainAuthor(): Author {
    return Author(
        id = author.id,
        name = author.name,
        birthDate = author.birthDate,
        books = books.map { it.toDomainBook() }
    )
}

fun BookEntity.toDomainBook(): Book {
    return Book(
        id = id,
        title = title,
        authorId = authorId
    )
}

fun AuthorEntity.toDomainAuthor(): Author {
    return Author(
        id = id,
        name = name,
        birthDate = birthDate,
        books = emptyList()
    )
}

fun Book.toEntityBook(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        authorId = authorId
    )
}

fun Author.toEntityAuthor(): AuthorEntity {
    return AuthorEntity(
        id = id,
        name = name,
        birthDate = birthDate
    )
}