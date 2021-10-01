package pe.solera.repository.network.api.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("idToken")
    var idToken: String
)