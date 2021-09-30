package pe.solera.repository.network.api.app

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("name")
    var name: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("phone")
    var phone: String?
)
