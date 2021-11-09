package pe.solera.solerajobs.ui.main.chart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pe.solera.core.BaseException
import pe.solera.core.ConstantsCore
import pe.solera.core.extension.launchOnIO
import pe.solera.core.extension.toDateString
import pe.solera.entity.Project
import pe.solera.repository.network.api.chart.ChartNetworkRepository
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class ChartViewModel
@Inject
constructor(
    private val chartNetworkRepository: ChartNetworkRepository
): ViewModel() {

    var chartEvent : MutableLiveData<ChartEventResult> = MutableLiveData()
    val chartEventLiveData : LiveData<ChartEventResult> get() = chartEvent

    var startDateSelected : Date? = null
    var endDateSelected : Date? = null

    var statisticsByDateRange: ArrayList<Project> = ArrayList()

    fun getStatistics() {
        chartEvent.value = ChartEventResult.Loading
        if (startDateSelected != null && endDateSelected != null) {
            launchOnIO(
                doTask = {
                    chartNetworkRepository.getStatistics(
                        startDateSelected!!.time.toDateString(ConstantsCore.DatePattern.yyyyMMdd),
                        endDateSelected!!.time.toDateString(ConstantsCore.DatePattern.yyyyMMdd)
                    )
                },
                result = {
                    statisticsByDateRange = it
                    chartEvent.value = ChartEventResult.SuccessStatistics
                },
                error = {
                    chartEvent.value = ChartEventResult.Error(it)
                }
            )
        } else {
            val msg = "Debes ingresar un rango de fecha."
            chartEvent.value = ChartEventResult.Error(BaseException.GeneralException(errorMessage = msg, errorMessageDetail = msg))
        }
    }

}