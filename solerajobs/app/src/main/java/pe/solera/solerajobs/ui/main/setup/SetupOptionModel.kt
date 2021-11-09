package pe.solera.solerajobs.ui.main.setup

data class SetupOptionModel(
    var imgResource: Int,
    var title: String = String(),
    var description: String = String(),
    var type: SetupOptionType
)

enum class SetupOptionType {
    LOGOUT, QUICK_ACCESS;
}
