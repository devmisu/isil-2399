package pe.solera.repository.network.api.app

import pe.solera.entity.User
import pe.solera.repository.util.EntityMapper

class UserResponseMapper : EntityMapper<User, UserResponse> {

    override fun mapFromEntity(entity: User): UserResponse {
        return UserResponse(
            name = entity.name,
            email = entity.email,
            phone = entity.phone
        )
    }

    override fun mapToEntity(domainModel: UserResponse): User {
        return User(
            name = domainModel.name ?: String(),
            email = domainModel.email ?: String(),
            phone = domainModel.phone ?: String()
        )
    }
}