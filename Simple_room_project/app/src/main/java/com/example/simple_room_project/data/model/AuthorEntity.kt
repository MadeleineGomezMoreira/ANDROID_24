package com.example.simple_room_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "authors")
data class AuthorEntity (
    @PrimaryKey
    val id: Int,
    val name: String,
    val birthDate: LocalDate,
)