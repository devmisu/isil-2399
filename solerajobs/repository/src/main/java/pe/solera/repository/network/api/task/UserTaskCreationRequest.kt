package pe.solera.repository.network.api.task

data class UserTaskCreationRequest(
    var id_requirement: Int,
    var date: String,
    var hours: Int,
    var comment: String? = null
)