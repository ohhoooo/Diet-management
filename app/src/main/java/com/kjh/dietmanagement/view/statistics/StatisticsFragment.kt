package com.kjh.dietmanagement.view.statistics

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.tabs.TabLayout
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentStatisticsBinding
import com.kjh.dietmanagement.view.common.ViewModelFactory
import com.kjh.dietmanagement.viewmodel.StatisticsViewModel

class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding
    private val viewModel: StatisticsViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        onClickButtons()



        // 통계 조회
        lifecycleScope.launchWhenCreated {
            viewModel.getStatistics()
        }


    }

    // observer
    private fun observer() {
        // 통계
        viewModel.responseStatistics.observe(viewLifecycleOwner) {
            binding.rbWeight.isChecked = true
            Toast.makeText(requireContext(), "통계 조회에 성공 하였습니다.", Toast.LENGTH_SHORT).show()
        }

        // 오류 메시지
        viewModel.message.observe(viewLifecycleOwner) {
            when (it) {
                "응답 실패" -> {
                    Toast.makeText(requireContext(), "응답에 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }
                "연결 실패" -> {
                    Toast.makeText(requireContext(), "연결에 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onClickButtons() {
        // 탭 버튼
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        createChartData(0, binding.groupRadio.checkedRadioButtonId)
                    }
                    1 -> {
                        createChartData(1, binding.groupRadio.checkedRadioButtonId)
                    }
                    2 -> {
                        createChartData(2, binding.groupRadio.checkedRadioButtonId)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })

        // 라디오 버튼
        binding.groupRadio.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.rb_weight -> {
                    createChartData(binding.tabLayout.selectedTabPosition, R.id.rb_weight)
                }
                R.id.rb_calorie -> {
                    createChartData(binding.tabLayout.selectedTabPosition, R.id.rb_calorie)
                }
                R.id.rb_carbohydrates -> {
                    createChartData(binding.tabLayout.selectedTabPosition, R.id.rb_carbohydrates)
                }
                R.id.rb_protein -> {
                    createChartData(binding.tabLayout.selectedTabPosition, R.id.rb_protein)
                }
                R.id.rb_fat -> {
                    createChartData(binding.tabLayout.selectedTabPosition, R.id.rb_fat)
                }
            }
        }
    }

    // 차트 설정
    private fun initChart(y: Int) {
        // 차트 전체 설정
        binding.lineChart.apply {
            axisRight.isEnabled = true // 짝대기 사용 여부
            axisLeft.isEnabled = false // 짝대기 왼쪽 숫자 사용 여부
            legend.isEnabled = true // 범례 사용 여부
            description.isEnabled = false // 주석
            isDragXEnabled = false   // x축 드래그 여부
            isScaleYEnabled = false // y축 줌 사용 여부
            isScaleXEnabled = false // x축 줌 사용 여부
            extraBottomOffset = 20f // 하단과 범례의 간격
        }

        // 차트 범례
        binding.lineChart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            form = Legend.LegendForm.CIRCLE
            formSize = 10f
            textSize = 13f
            textColor = Color.parseColor("#A3A3A3")
            orientation = Legend.LegendOrientation.HORIZONTAL
            setDrawInside(false)
            yEntrySpace = 5f
            isWordWrapEnabled = true
            xOffset = 0f
            yOffset = 20f
            calculatedLineSizes
        }

        // 아래 선 - 선 유무, 사이즈, 색상, 축 위치 설정
        binding.lineChart.xAxis.apply {
            setDrawAxisLine(false)
            setDrawGridLines(false) // 배경 그리드 라인 세팅
            position = XAxis.XAxisPosition.BOTTOM // x축 데이터 표시 위치
            granularity = 1f // x축 데이터 표시 간격
            textSize = 12f
            textColor = Color.rgb(118, 118, 118)
            spaceMin = 0.3f // Chart 맨 왼쪽 간격 띄우기
            spaceMax = 0.3f // Chart 맨 오른쪽 간격 띄우기
        }

        // 왼쪽 선 - 선 유무, 데이터 최솟값/최댓값, 색상
        binding.lineChart.axisLeft.apply {
            textSize = 14f
            textColor = Color.rgb(163, 163, 163)
            setDrawAxisLine(false)
            axisLineWidth = 2f
            axisMinimum = 0f // 최솟값
            axisMaximum = y.toFloat()//RANGE.get(0).get(range) as Float // 최댓값
            granularity = 1.0f//RANGE.get(1).get(range) as Float
        }

        // 오른쪽 선 - 선 유무, 데이터 최솟값/최댓값, 색상
        binding.lineChart.axisRight.apply {
            setDrawLabels(false) // label 삭제
            textColor = Color.rgb(163, 163, 163)
            setDrawAxisLine(false)
            axisLineWidth = 2f
            axisMinimum = 0f // 최솟값
            axisMaximum = y.toFloat()//5.0f//RANGE.get(0).get(range) as Float // 최댓값
            granularity = 1.0f//RANGE.get(1).get(range) as Float
        }

        // 아래 선에 원하는 String 설정
        binding.lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                Log.d("3t3531451242", value.toString().substring(2, 4) + "/" + value.toString().substring(4, 6))
                return value.toString().substring(2, 4) + "/" + value.toString().substring(4, 6)
            }
        }
    }

    // 데이터 생성
    private fun createChartData(tab: Int, rb: Int) {
        val entry1 = ArrayList<Entry>() // 앱1
        val entry2 = ArrayList<Entry>() // 앱2
        val chartData = LineData()

        val dataType = when (rb) {
            2131296682 -> viewModel.responseStatistics.value?.result?.weight
            2131296676 -> viewModel.responseStatistics.value?.result?.kcal
            2131296677 -> viewModel.responseStatistics.value?.result?.tan
            2131296681 -> viewModel.responseStatistics.value?.result?.dan
            else -> viewModel.responseStatistics.value?.result?.ji
        }

        val dataDate = when (tab) {
            0 -> dataType?.daily
            1 -> dataType?.weekly
            else -> dataType?.monthly
        }

        val recommend = dataType?.recommend
        val yaxis = dataType?.yaxis

        // 데이터 삽입
        if (dataDate != null) {
            for(i in dataDate) {
                val date = (i.date.substring(2,4) + i.date.substring(5,7) + i.date.substring(8,10)).toFloat()
                entry1.add(Entry(date, i.data.toFloat()))
                entry2.add(Entry(date, recommend?.toFloat()!!))
            }
        }

        // 본인 데이터 추이
        val lineDataSet1 = LineDataSet(entry1, "나").apply {
            lineWidth = 3f
            circleRadius = 6f
            setDrawCircleHole(true)
            setDrawCircles(true)
            setDrawValues(true)
            setDrawHorizontalHighlightIndicator(false)
            valueTextSize = 11.0F
            setDrawHighlightIndicators(false)
            color = Color.rgb(255, 155, 155)
            setCircleColor(Color.rgb(255, 155, 155))
        }

        // 목표 데이터
        val lineDataSet2 = LineDataSet(entry2, "추천").apply {
            lineWidth = 3f
            circleRadius = 6f
            setDrawValues(true)
            valueTextSize = 11.0F
            setDrawCircleHole(true)
            setDrawCircles(true)
            setDrawHorizontalHighlightIndicator(false)
            setDrawHighlightIndicators(false)
            color = Color.rgb(178, 223, 138)
            setCircleColor(Color.rgb(178, 223, 138))
        }

        chartData.addDataSet(lineDataSet1)
        chartData.addDataSet(lineDataSet2)

        // 아래 선에 원하는 String 설정
        binding.lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                Log.d("qweqwewqeqweqw", value.toString())
                Log.d("qweqwewqeqweqw", value.toString().substring(2, 4) + "/" + value.toString().substring(4, 6))
                return value.toString().substring(2, 4) + "/" + value.toString().substring(4, 6)
            }
        }
        initChart(yaxis!!)
        Log.d("sfasfsfdffsafaf", chartData.dataSets.toString())


        binding.lineChart.data = chartData
        binding.lineChart.invalidate()
    }
}