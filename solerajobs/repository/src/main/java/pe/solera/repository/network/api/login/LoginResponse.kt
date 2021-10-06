package pe.solera.repository.network.api.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("session")
    var session: SessionResponse?,
    @SerializedName("user")
    var member: MemberResponse?
)