package pe.solera.repository.util

import pe.solera.core.BaseException
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
            nonNullResponse.errorBody()?.let {
                //val baseErrorResponse = it.toBaseErrorResponse()
                error(BaseException.GeneralException(errorMessageDetail = String()))
            } ?: kotlin.run {
                error(NullPointerException())
            }
        }
    } ?: kotlin.run {
        error(NullPointerException())
    }
}