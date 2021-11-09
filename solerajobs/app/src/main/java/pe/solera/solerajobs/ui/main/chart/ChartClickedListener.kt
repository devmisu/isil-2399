package pe.solera.solerajobs.ui.main.chart

import pe.solera.entity.Project

interface ChartClickedListener {
    fun onClick(project: Project)
}