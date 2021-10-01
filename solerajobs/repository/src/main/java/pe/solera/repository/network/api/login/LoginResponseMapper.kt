package pe.solera.repository.network.api.login

import pe.solera.entity.Authentication
import pe.solera.entity.User
import pe.solera.repository.util.EntityMapper

class LoginResponseMapper : EntityMapper<Authentication, LoginResponse> {

    override fun mapToEntity(domainModel: LoginResponse): Authentication {
        val user = User(
            name = domainModel.member?.firstName ?: String(),
            lastName = domainModel.member?.lastName ?: String(),
            email = domainModel.member?.email ?: String(),
            phone = domainModel.member?.phone ?: String()
        )
        val type = domainModel.session?.type ?: String()
        val token = domainModel.session?.token ?: String()
        val authToken = "$type $token"
        return Authentication(user, authToken)
    }
}