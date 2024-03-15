package com.example.simple_room_project.data.repositories

import com.example.simple_room_project.data.local.LibraryDao
import com.example.simple_room_project.data.model.toDomainAuthor
import com.example.simple_room_project.data.model.toDomainBook
import com.example.simple_room_project.data.model.toEntityAuthor
import com.example.simple_room_project.data.model.toEntityBook
import com.example.simple_room_project.domain.model.Author
import com.example.simple_room_project.domain.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LibraryRepository @Inject constructor(
    private val dao: LibraryDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getAuthorsWithBooks(): Flow<List<Author>> {
        return flow {
            emit(dao.getAuthorWithBooks().map { it.toDomainAuthor() })
        }.flowOn(dispatcher)
    }

    fun getAuthors(): Flow<List<Author>> {
        return flow {
            emit(dao.getAuthors().map { it.toDomainAuthor() })
        }.flowOn(dispatcher)
    }

    fun getBooks(): Flow<List<Book>> {
        return flow {
            emit(dao.getBooks().map { it.toDomainBook() })
        }.flowOn(dispatcher)
    }

    fun insertAuthor(author: Author) =
        flow { emit(dao.insertAuthor(author.toEntityAuthor())) }.flowOn(dispatcher)

    fun insertBook(book: Book) =
        flow { emit(dao.insertBook(book.toEntityBook())) }.flowOn(dispatcher)

}