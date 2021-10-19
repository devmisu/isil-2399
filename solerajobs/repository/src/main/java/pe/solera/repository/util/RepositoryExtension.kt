package pe.solera.repository.util

import pe.solera.core.ErrorType
import retrofit2.Response
import java.io.IOException

fun <T> Response<T>?.validateResponse(success: (T) -> Unit, error: (IOException) -> Unit, successWithoutBody: (() -> Unit)? = null) {
    this?.let { nonNullResponse ->
        if (nonNullResponse.isSuccessful) {
            nonNullResponse.body()?.let { correctBody ->
                success(correctBody)
            } ?: kotlin.run {
                if (nonNullResponse.code() == 200 || nonNullResponse.code() == 201) {
                    successWithoutBody?.invoke()
                } else {
                    error(NullPointerException())
                }
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