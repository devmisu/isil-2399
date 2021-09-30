package pe.solera.repository.network.model

import com.google.gson.annotations.SerializedName
import pe.solera.core.BaseException
import pe.solera.core.ConstantsCore
import pe.solera.core.ErrorType

data class ErrorBody(
    @SerializedName("errorCode")
    var code: Int? = ConstantsCore.defaultCode,
    @SerializedName("errorMessage")
    var message: String? = ConstantsCore.empty,
    @SerializedName("errorMessageDetail")
    var detail: String? = ConstantsCore.empty
) {
    fun getException() : BaseException {
        return ErrorType.returnException(
            this.code,
            this.message,
            this.detail
        )
    }
}