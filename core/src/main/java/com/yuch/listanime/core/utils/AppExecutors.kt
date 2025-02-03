package com.yuch.listanime.core.utils

import androidx.annotation.VisibleForTesting

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors @VisibleForTesting constructor(
    private val diskIO: Executor
) {
    // Constructor de la clase AppExecutors
    constructor() : this(
        Executors.newSingleThreadExecutor()
    )
    // Función mainThread
    fun diskIO(): Executor = diskIO
}

