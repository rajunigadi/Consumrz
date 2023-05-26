package dev.raju.consumrz

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job

abstract class BaseViewModel: ViewModel() {

    protected val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Error: ${exception.message}")
    }

    protected var job: Job? = null

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}