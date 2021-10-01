package pe.solera.repository.network.api.login

import com.google.gson.annotations.SerializedName

data class SessionResponse(
    @SerializedName("type")
    var type: String?,
    @SerializedName("token")
    var token: String?
)