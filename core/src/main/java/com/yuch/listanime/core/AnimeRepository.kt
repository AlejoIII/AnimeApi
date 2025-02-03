package com.yuch.listanime.core

import com.yuch.listanime.core.data.source.local.LocalDataSource
import com.yuch.listanime.core.domain.model.Anime
import com.yuch.listanime.core.domain.repository.IAnimeRepository
import com.yuch.listanime.core.data.source.remote.RemoteDataSource
import com.yuch.listanime.core.data.source.remote.network.ApiResponse
import com.yuch.listanime.core.data.source.remote.response.AnimeResponse
import com.yuch.listanime.core.utils.AppExecutors
import com.yuch.listanime.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AnimeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IAnimeRepository {
    // Obtenemos los animes de la API y los guardamos en la base de datos local
    override fun getTopAnime(): Flow<Resource<List<Anime>>> =
        object : NetworkBoundResource<List<Anime>, List<AnimeResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<Anime>> {
                return localDataSource.getTopAnime().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }
            // Obtenemos los animes de la API
            override suspend fun createCall(): Flow<ApiResponse<List<AnimeResponse>>> =
                remoteDataSource.getTopAnime()
            // Guardamos los animes de la API en la base de datos local
            override suspend fun saveCallResult(data: List<AnimeResponse>) {
                val animeList = DataMapper.mapResponsesToEntities(data)
                val currentFavorites = localDataSource.getFavoriteAnime().first()

                val updatedAnimeList = animeList.map { animeEntity ->
                    val isFavorite = currentFavorites.find { it.malId == animeEntity.malId }?.isFavorite ?: false
                    animeEntity.isFavorite = isFavorite
                    animeEntity
                }
                localDataSource.insertAnime(updatedAnimeList)
            }

            override fun shouldFetch(data: List<Anime>?): Boolean {
                return data.isNullOrEmpty()
            }

        }.asFlow()
    // Obtenemos los animes favoritos de la base de datos local
    override fun getFavoriteAnime(): Flow<List<Anime>> {
        return localDataSource.getFavoriteAnime().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }
    // Guardamos un anime como favorito en la base de datos local
    override fun setFavoriteAnime(anime: Anime, state: Boolean) {
        val animeEntity = DataMapper.mapDomainToEntity(anime)
        appExecutors.diskIO().execute { localDataSource.setFavoriteAnime(animeEntity, state) }
    }

}