package com.yuch.listanime.core

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    // Clases de estado de la API
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
