package com.example.simple_room_project.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.simple_room_project.data.model.AuthorEntity
import com.example.simple_room_project.data.model.AuthorWithBooks
import com.example.simple_room_project.data.model.BookEntity

interface LibraryDao {

    @Transaction
    @Query("SELECT * FROM authors")
    suspend fun getAuthorWithBooks(): List<AuthorWithBooks>

    @Query("SELECT * FROM authors")
    suspend fun getAuthors(): List<AuthorEntity>

    @Query("SELECT * FROM books")
    suspend fun getBooks(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthor(author: AuthorEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: BookEntity)


}