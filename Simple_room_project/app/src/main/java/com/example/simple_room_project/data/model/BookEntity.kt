package com.example.simple_room_project.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity (
    @PrimaryKey
    val id: Int,
    val title: String,
    val authorId: Int
)