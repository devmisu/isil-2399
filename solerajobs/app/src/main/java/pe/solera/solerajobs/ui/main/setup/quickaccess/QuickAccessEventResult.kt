package pe.solera.solerajobs.ui.main.setup.quickaccess

import pe.solera.entity.QuickAccessForSelection
import pe.solera.solerajobs.ui.util.selectable.SelectableModel

sealed class QuickAccessEventResult(val isLoading: Boolean) {

    object Loading : QuickAccessEventResult(true)
    data class Error(val ex: Exception) : QuickAccessEventResult(false)
    data class SuccessQuickAccess(val items: ArrayList<SelectableModel<QuickAccessForSelection>>) : QuickAccessEventResult(false)
    object SuccessSaveQuickAccess : QuickAccessEventResult(false)
    object SavingQuickAccess: QuickAccessEventResult(false)

}
