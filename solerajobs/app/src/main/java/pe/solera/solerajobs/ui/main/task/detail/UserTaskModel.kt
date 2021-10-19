package pe.solera.solerajobs.ui.main.task.detail

data class UserTaskModel(
    var hourSelected : Pair<Int, Double> = Pair(0, 0.0),
    var comment: String = String()
)