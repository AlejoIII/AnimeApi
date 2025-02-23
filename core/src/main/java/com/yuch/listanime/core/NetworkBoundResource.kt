package com.yuch.listanime.core

import com.yuch.listanime.core.data.source.remote.network.ApiResponse
import com.yuch.listanime.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {
    // Obtenemos los animes de la API y los guardamos en la base de datos local
    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        // Obtenemos los animes de la base de datos local
        val dbSource = loadFromDB().first()
        // Si no hay animes en la base de datos local, obtenemos los animes de la API
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())

            when (val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}