package pe.solera.core.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import pe.solera.core.EventResult

typealias DoTask<T> = suspend CoroutineScope.() -> EventResult<T>
typealias SuccessResult<T> = (success: T) -> Unit
typealias ErrorResult = ((Exception) -> Unit)?

fun <T> ViewModel.launchOnIO(
    doTask: DoTask<T>,
    result: SuccessResult<T>,
    error: ErrorResult = null
) : Job {
    return viewModelScope.launch {
        try {
            withContext(Dispatchers.IO) {
                doTask()
            }.also {
                when(it) {
                    is EventResult.Success -> result(it.value)
                    is EventResult.Error -> error?.invoke(it.exception)
                }
            }
        } catch (ex: Exception) {
            println("Ocurrió un error ${ex.message}")
            error?.invoke(ex)
        }
    }
}