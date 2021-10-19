package pe.solera.repository.network.api.task

import pe.solera.entity.UserTask
import pe.solera.entity.UserWorkedDay
import pe.solera.repository.util.EntityMapper

class UserTaskResponseMapper : EntityMapper<UserWorkedDay, WorkedDayResponse> {

    override fun mapToEntity(domainModel: WorkedDayResponse): UserWorkedDay {
        return UserWorkedDay(
            workedHours = domainModel.workedHours ?: 0.0,
            requirements = domainModel.requirements.map { userTaskResponseToEntity(it) }
        )
    }

    fun userTaskResponseToEntity(response: UserTaskResponse) : UserTask {
        return UserTask(
            id = response.id ?: 0,
            clientName = response.clientName ?: String(),
            projectName = response.projectName ?: String(),
            requirementDescription = response.requirementDescription ?: String(),
            projectManager = response.projectManager ?: String(),
            estimateStartDate = response.estimateStartDate ?: String(),
            estimateEndDate = response.estimateEndDate ?: String(),
            estimateHours = response.estimateHours ?: 0.0,
            realHours = response.realHours ?: 0.0,
            comment = response.comment ?: String()
        )
    }
}