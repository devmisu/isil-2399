package pe.solera.repository.util

import com.google.gson.Gson
import pe.solera.core.ErrorType
import pe.solera.repository.network.model.GenericErrorBody
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
            try {
                val gson = Gson()
                val errorJson = nonNullResponse.errorBody()?.string()
                val errorBody = gson.fromJson(errorJson, GenericErrorBody::class.java)
                error(ErrorType.returnException(
                    nonNullResponse.code(),
                    errorBody.message,
                    errorBody.message
                ))
            } catch (ex: Exception) {
                error(ErrorType.returnException(
                    nonNullResponse.code(),
                    nonNullResponse.message(),
                    nonNullResponse.message()
                ))
            }
        }
    } ?: kotlin.run {
        error(NullPointerException())
    }
}