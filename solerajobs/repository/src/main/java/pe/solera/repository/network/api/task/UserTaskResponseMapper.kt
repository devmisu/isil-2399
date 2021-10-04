package pe.solera.repository.network.api.task

import pe.solera.entity.UserTask
import pe.solera.repository.util.EntityMapper

class UserTaskResponseMapper : EntityMapper<UserTask, UserTaskResponse> {

    override fun mapToEntity(domainModel: UserTaskResponse): UserTask {
        return UserTask(
            projectName = domainModel.projectName ?: String(),
            requirement = domainModel.requirement ?: String(),
            hours = domainModel.hours ?: 0.0
        )
    }
}