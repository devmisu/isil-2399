package pe.solera.repository.network.model

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("results")
    var results: List<T>
)