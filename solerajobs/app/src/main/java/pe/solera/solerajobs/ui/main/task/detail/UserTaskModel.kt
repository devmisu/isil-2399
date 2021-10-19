package pe.solera.solerajobs.ui.main.task.detail

import pe.solera.entity.TaskIdentifier
import java.util.*

data class UserTaskModel(
    var hourSelected : Pair<Int, Double> = Pair(0, 0.0),
    var comment: String = String(),
    var clientIdentifier: Pair<Int, TaskIdentifier> = Pair(-1, TaskIdentifier()),
    var projectIdentifier: Pair<Int, TaskIdentifier> = Pair(-1, TaskIdentifier()),
    var requirementIdentifier: Pair<Int, TaskIdentifier> = Pair(-1, TaskIdentifier()),
    var date: Date? = null
)
