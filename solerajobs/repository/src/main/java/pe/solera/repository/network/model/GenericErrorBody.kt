package pe.solera.repository.network.model

import com.google.gson.annotations.SerializedName
import pe.solera.core.ConstantsCore

data class GenericErrorBody(
    @SerializedName("message")
    var message: String? = ConstantsCore.Error.Message.generalErrorMessage,
    @SerializedName("devMessage")
    var devMessage: String? = ConstantsCore.Error.Message.generalErrorMessage
)