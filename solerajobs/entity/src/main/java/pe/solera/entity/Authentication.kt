package pe.solera.entity

data class Authentication(
    var user: User,
    var token: String
)