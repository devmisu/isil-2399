package pe.solera.repository.network.api.chart

import pe.solera.core.EventResult
import pe.solera.entity.Project

interface ChartNetworkRepository {

    suspend fun getStatistics(startDate: String, endDate: String) : EventResult<ArrayList<Project>>

}