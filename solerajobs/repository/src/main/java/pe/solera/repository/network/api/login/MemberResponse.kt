package pe.solera.repository.network.api.login

import com.google.gson.annotations.SerializedName

data class MemberResponse(
    @SerializedName("firstName")
    var firstName: String?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("document_number")
    var documentNumber: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("phone")
    var phone: String?
)
