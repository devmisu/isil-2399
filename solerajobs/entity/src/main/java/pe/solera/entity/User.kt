package pe.solera.entity

data class User(
    var name: String = String(),
    var lastName: String = String(),
    var email: String = String(),
    var phone: String = String(),
    var job: String = String()
)