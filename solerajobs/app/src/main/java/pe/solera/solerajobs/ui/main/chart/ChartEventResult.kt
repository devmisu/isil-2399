package pe.solera.solerajobs.ui.main.chart

sealed class ChartEventResult(val isLoading: Boolean) {

    object Loading : ChartEventResult(true)
    data class Error(val ex: Exception) : ChartEventResult(false)
    object SuccessStatistics : ChartEventResult(false)

}