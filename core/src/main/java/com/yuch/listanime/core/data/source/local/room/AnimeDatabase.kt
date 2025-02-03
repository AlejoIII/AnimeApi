package com.yuch.listanime.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuch.listanime.core.data.source.local.entity.AnimeEntity

@Database(entities = [AnimeEntity::class], version = 1, exportSchema = false)
abstract class AnimeDatabase : RoomDatabase() {
    // Se crea una instancia de AnimeDao
    abstract fun animeDao(): AnimeDao
}