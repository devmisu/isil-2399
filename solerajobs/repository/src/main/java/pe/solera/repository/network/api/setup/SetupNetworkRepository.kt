package pe.solera.repository.network.api.setup

import pe.solera.core.EventResult
import pe.solera.entity.QuickAccessForSelection

interface SetupNetworkRepository {

    suspend fun getQuickAccessForSelection() : EventResult<ArrayList<QuickAccessForSelection>>

    suspend fun saveQuickAccessSelected(list: ArrayList<QuickAccessForSelection>) : EventResult<Boolean>

}