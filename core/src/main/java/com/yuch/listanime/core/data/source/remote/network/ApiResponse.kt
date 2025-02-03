package com.yuch.listanime.core.data.source.remote.network

sealed class ApiResponse<out R> {
    // Clase de éxito que recibe un dato de tipo T y lo devuelve
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
    data object Empty : ApiResponse<Nothing>()
}