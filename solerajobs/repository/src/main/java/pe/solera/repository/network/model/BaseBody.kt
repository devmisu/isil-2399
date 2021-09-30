package pe.solera.repository.network.model

import com.google.gson.annotations.SerializedName

data class BaseBody<T>(
    @SerializedName("successMessage")
    var successMessage: String,
    @SerializedName("data")
    var data: T
)