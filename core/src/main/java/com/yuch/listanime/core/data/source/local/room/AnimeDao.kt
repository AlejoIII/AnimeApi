package com.yuch.listanime.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.yuch.listanime.core.data.source.local.entity.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    // consultamos todos los animes ordenados por ranking
    @Query("SELECT * FROM anime ORDER BY rank ASC")
    fun getTopAnime(): Flow<List<AnimeEntity>>
    // consultamos los animes favoritos
    @Query("SELECT * FROM anime WHERE isFavorite = 1")
    fun getFavoriteAnime(): Flow<List<AnimeEntity>>
    // insertamos los animes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnime(anime: List<AnimeEntity>)
    // actualizamos el anime favorito
    @Update
    fun updateFavoriteAnime(anime: AnimeEntity)
}