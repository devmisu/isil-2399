package pe.solera.solerajobs.ui.main.setup.quickaccess

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.solera.core.extension.launchOnIO
import pe.solera.entity.QuickAccessForSelection
import pe.solera.repository.network.api.setup.SetupNetworkRepository
import pe.solera.solerajobs.ui.util.selectable.SelectableModel
import javax.inject.Inject

@HiltViewModel
class QuickAccessViewModel
@Inject
constructor(
    private val setupNetworkRepository: SetupNetworkRepository
): ViewModel() {

    private var quickAccessEvent: MutableLiveData<QuickAccessEventResult> = MutableLiveData()
    val quickAccessEventLiveData: LiveData<QuickAccessEventResult> get() = quickAccessEvent

    private var projectsToSelect : ArrayList<SelectableModel<QuickAccessForSelection>> = arrayListOf()

    companion object {
        const val MAX_PROJECTS = 4
    }

    fun getQuickAccessListForSelection() {
        quickAccessEvent.value = QuickAccessEventResult.Loading
        launchOnIO(
            doTask = {
                setupNetworkRepository.getQuickAccessForSelection()
            },
            result = {
                it.map { model ->
                    projectsToSelect.add(SelectableModel(model, model.id, isSelected = model.selected))
                }
                quickAccessEvent.value = QuickAccessEventResult.SuccessQuickAccess(projectsToSelect)
            },
            error = {
                quickAccessEvent.value = QuickAccessEventResult.Error(it)
            }
        )
    }

    fun addSelectedProject(errorMsg: (String) -> Unit) {
        projectsToSelect.filter { it.isSelected }.let {
            if (it.size >= MAX_PROJECTS) {
                errorMsg.invoke("No pueden seleccionarse más de 3 proyectos.")
            }
        }
    }

    fun saveSelectedQuickAccessList() {
        quickAccessEvent.value = QuickAccessEventResult.SavingQuickAccess
        launchOnIO(
            doTask = {
                val request = ArrayList<QuickAccessForSelection>()
                projectsToSelect.filter { it.isSelected }.let {
                    it.map { model ->
                        model.model?.let { validModel ->
                            request.add(validModel)
                        }
                    }
                }
                setupNetworkRepository.saveQuickAccessSelected(request)
            },
            result = {
                quickAccessEvent.value = QuickAccessEventResult.SuccessSaveQuickAccess
                quickAccessEvent = MutableLiveData()
            },
            error = {
                quickAccessEvent.value = QuickAccessEventResult.Error(it)
            }
        )
    }
}
