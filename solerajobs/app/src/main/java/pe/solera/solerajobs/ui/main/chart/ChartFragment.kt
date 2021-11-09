package pe.solera.solerajobs.ui.main.chart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.material.datepicker.*
import dagger.hilt.android.AndroidEntryPoint
import pe.solera.core.ConstantsCore
import pe.solera.core.extension.showMaterialDialog
import pe.solera.core.extension.toDate
import pe.solera.core.extension.toDateString
import pe.solera.entity.Project
import pe.solera.solerajobs.BuildConfig
import pe.solera.solerajobs.R
import pe.solera.solerajobs.databinding.FragmentChartBinding
import pe.solera.solerajobs.ui.BaseFragment
import pe.solera.solerajobs.ui.dialog.ChildBottomSheetType
import pe.solera.solerajobs.ui.dialog.HostBottomSheet
import pe.solera.solerajobs.ui.dialog.chart.ProjectDetailFragment
import pe.solera.solerajobs.ui.getDateFromFuture
import pe.solera.solerajobs.ui.validateException

@AndroidEntryPoint
class ChartFragment : BaseFragment(), ChartClickedListener {

    private lateinit var binding: FragmentChartBinding

    private val viewModel : ChartViewModel by viewModels()

    private var detailDialog: HostBottomSheet? = null
    set(value) {
        field?.dismiss()
        field = value
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chart, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStartDateSelector()
        setupEndDateSelector()
        setupRequestButton()
        initBarChart()
        fillChart()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.chartEventLiveData.observe(viewLifecycleOwner) {
            isLoading = it.isLoading
            when(it) {
                ChartEventResult.Loading -> {
                    binding.progressSave.visibility = View.VISIBLE
                }
                is ChartEventResult.SuccessStatistics -> {
                    binding.progressSave.visibility = View.GONE
                    initBarChart()
                    fillChart()
                }
                is ChartEventResult.Error -> requireActivity().validateException(it.ex) {
                    binding.progressSave.visibility = View.GONE
                    requireActivity().showMaterialDialog(
                        message = this
                    )
                }
            }
        }
    }

    private fun initBarChart() {

        //extra settings
        binding.chartView.setPinchZoom(false)
        binding.chartView.setDrawGridBackground(false)
        val xAxis: XAxis = binding.chartView.xAxis
        val yAxis: YAxis = binding.chartView.axisLeft
        //hide grid lines
        binding.chartView.axisLeft.setDrawGridLines(true)
        binding.chartView.xAxis.setDrawGridLines(false)
        binding.chartView.xAxis.setDrawAxisLine(true)
        //remove right y-axis
        binding.chartView.axisRight.isEnabled = false
        //remove legend
        binding.chartView.legend.isEnabled = false
        //remove description label
        binding.chartView.description.isEnabled = false
        //add animation
        binding.chartView.animateY(1000)
        //custom marker added
        binding.chartView.marker = CustomMarkerView(requireContext(), R.layout.custom_marker_chart, this, viewModel.statisticsByDateRange)
        yAxis.textColor = ContextCompat.getColor(requireContext(), R.color.text_main)
        yAxis.textSize = 14f
        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = MyAxisFormatter(viewModel.statisticsByDateRange)
        xAxis.setDrawLabels(true)
        xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.text_main)
        xAxis.textSize = 14f
        xAxis.granularity = 1f
    }

    inner class MyAxisFormatter(private var list: ArrayList<Project>) : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            val label = if (index < list.size) {
                list[index].projectName
            } else String()
            return label
        }
    }

    inner class CustomMarkerView(
        context: Context,
        layout: Int,
        private val listener: ChartClickedListener? = null,
        private val dataToDisplay: ArrayList<Project>
    ) : MarkerView(context, layout) {

        override fun refreshContent(e: Entry?, highlight: Highlight?) {
            try {
                val xAxis = e?.x?.toInt() ?: 0
                listener?.onClick(dataToDisplay[xAxis])
            } catch (e: IndexOutOfBoundsException) { }

            super.refreshContent(e, highlight)
        }

        override fun getOffset(): MPPointF {
            return MPPointF(-(width / 2f), -height.toFloat())
        }
    }

    private fun fillChart() {
        val entries: ArrayList<BarEntry> = ArrayList()
        viewModel.statisticsByDateRange.mapIndexed { index, item ->
            entries.add(BarEntry(index.toFloat(), item.totalWorkedHours.toFloat()))
        }

        val barDataSet = BarDataSet(entries, "")
        barDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)

        val data = BarData(barDataSet)
        binding.chartView.data = data

        binding.chartView.invalidate()
    }

    private fun setupStartDateSelector() {
        binding.tvStartDateSelector.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            builder.setTitleText(BuildConfig.APP_NAME)
            val selectedDate : Long? = viewModel.startDateSelected?.time
            val constraintsBuilder = CalendarConstraints.Builder().apply {
                selectedDate?.let {
                    setOpenAt(it)
                }
            }.build()
            builder.setCalendarConstraints(constraintsBuilder)
            builder.setSelection(selectedDate)
            builder.build().let { picker ->
                picker.addOnPositiveButtonClickListener {
                    viewModel.startDateSelected = it toDate ConstantsCore.DatePattern.yyyyMMdd
                    binding.tvStartDateSelector.text = it toDateString ConstantsCore.DatePattern.ddMMyyyy_slashes
                }
                picker.addOnNegativeButtonClickListener {
                    picker.dismiss()
                }
                picker.show(requireActivity().supportFragmentManager, picker.toString())
            }
        }
    }

    private fun setupEndDateSelector() {
        binding.tvEndDateSelector.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            builder.setTitleText(BuildConfig.APP_NAME)
            val selectedDate : Long? = viewModel.endDateSelected?.time
            val constraintsBuilder = CalendarConstraints.Builder().apply {
                selectedDate?.let {
                    setOpenAt(it)
                }
            }.build()
            builder.setCalendarConstraints(constraintsBuilder)
            builder.setSelection(selectedDate)
            builder.build().let { picker ->
                picker.addOnPositiveButtonClickListener {
                    viewModel.endDateSelected = it toDate ConstantsCore.DatePattern.yyyyMMdd
                    binding.tvEndDateSelector.text = it toDateString ConstantsCore.DatePattern.ddMMyyyy_slashes
                }
                picker.addOnNegativeButtonClickListener {
                    picker.dismiss()
                }
                picker.show(requireActivity().supportFragmentManager, picker.toString())
            }
        }
    }

    private fun setupRequestButton() {
        binding.btnRequest.setOnClickListener {
            if (!isLoading) {
                viewModel.getStatistics()
            }
        }
    }

    override fun onClick(project: Project) {
        detailDialog = HostBottomSheet.Builder()
            .setType(ChildBottomSheetType.ChartDetail)
            .setHideable(true)
            .setFragmentContent(ProjectDetailFragment.bundle(project))
            .buildAndShow(requireActivity().supportFragmentManager)
    }
}