package com.example.simple_room_project.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.simple_room_project.data.model.AuthorEntity
import com.example.simple_room_project.data.model.BookEntity
import com.example.simple_room_project.data.model.Converters

//aquí solo van las tablas, no las data clases con las relaciones y los embedded objects
@Database(entities = [AuthorEntity::class, BookEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun libraryDao(): LibraryDao

}