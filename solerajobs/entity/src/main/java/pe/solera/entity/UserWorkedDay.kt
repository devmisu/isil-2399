package pe.solera.entity

data class UserWorkedDay(
    var workedHours: Double = 0.0,
    var requirements: List<UserTask> = ArrayList()
)
