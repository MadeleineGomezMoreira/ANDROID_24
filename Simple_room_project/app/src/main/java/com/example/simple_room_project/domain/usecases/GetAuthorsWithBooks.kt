package com.example.simple_room_project.domain.usecases

import com.example.simple_room_project.data.repositories.LibraryRepository
import javax.inject.Inject

class GetAuthorsWithBooks @Inject constructor(
    private val repository: LibraryRepository,
) {
    operator fun invoke() = repository.getAuthorsWithBooks()
}

