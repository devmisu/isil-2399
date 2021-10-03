package pe.solera.repository.util

import pe.solera.core.ErrorType
import retrofit2.Response
import java.io.IOException

fun <T> Response<T>?.validateResponse(success: (T) -> Unit, error: (IOException) -> Unit) {
    this?.let { nonNullResponse ->
        if (nonNullResponse.isSuccessful) {
            nonNullResponse.body()?.let { correctBody ->
                success(correctBody)
            } ?: kotlin.run {
                error(NullPointerException())
            }
        } else {
            error(ErrorType.returnException(
                nonNullResponse.code(),
                nonNullResponse.message(),
                nonNullResponse.message()
            ))
        }
    } ?: kotlin.run {
        error(NullPointerException())
    }
}