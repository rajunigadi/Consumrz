package dev.raju.consumrz

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel: ViewModel() {

    protected val _error = MutableStateFlow<String>("")
    val error: StateFlow<String> = _error.asStateFlow()

    protected val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Error: ${exception.message}")
    }

    protected var job: Job? = null

    override fun onCleared() {
        job?.cancel()
        super.onCleared()
    }
}