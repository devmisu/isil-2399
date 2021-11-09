package pe.solera.solerajobs.ui.main.setup.quickaccess

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.solera.entity.Project
import pe.solera.solerajobs.ui.util.selectable.SelectableModel
import javax.inject.Inject

@HiltViewModel
class QuickAccessViewModel
@Inject
constructor(

): ViewModel() {

    var projects: ArrayList<Project> = arrayListOf(
        Project("1", "Solera reuniones 123"),
        Project("2", "Solera reuniones 124"),
        Project("3", "Solera reuniones 125"),
        Project("4", "Solera reuniones 126"),
        Project("5", "Solera reuniones 127"),
    )

    var projectsToSelect : ArrayList<SelectableModel<Project>> = arrayListOf()

    companion object {
        const val MAX_PROJECTS = 4
    }

    init {
        projects.map {
            projectsToSelect.add(SelectableModel(it, it.id))
        }
    }

    fun addSelectedProject(errorMsg: (String) -> Unit) {
        projectsToSelect.filter { it.isSelected }.let {
            if (it.size >= MAX_PROJECTS) {
                errorMsg.invoke("No pueden seleccionarse más de 3 proyectos.")
            }
        }
    }

}
