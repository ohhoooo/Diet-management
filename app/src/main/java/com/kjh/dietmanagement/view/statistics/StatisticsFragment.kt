package com.kjh.dietmanagement.view.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.tabs.TabLayout
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickButtons()
        initChart()
        prepareChartData(createChartData(), binding.lineChart)
    }

    // 차트 설정
    private fun initChart() {
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
            textSize = 14f
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
            axisMaximum = 5.0f//RANGE.get(0).get(range) as Float // 최댓값
            granularity = 1.0f//RANGE.get(1).get(range) as Float
        }

        // 오른쪽 선 - 선 유무, 데이터 최솟값/최댓값, 색상
        binding.lineChart.axisRight.apply {
            setDrawLabels(false) // label 삭제
            textColor = Color.rgb(163, 163, 163)
            setDrawAxisLine(false)
            axisLineWidth = 2f
            axisMinimum = 0f // 최솟값
            axisMaximum = 5.0f//RANGE.get(0).get(range) as Float // 최댓값
            granularity = 1.0f//RANGE.get(1).get(range) as Float
        }

        /*
        // XAxis에 원하는 String 설정하기 (날짜)
        binding.lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return LABEL.get(range).get(value.toInt())
            }
        }
         */
    }

    // 데이터 생성
    private fun createChartData(): LineData {
        val entry1 = ArrayList<Entry>() // 앱1
        val entry2 = ArrayList<Entry>() // 앱2
        val chartData = LineData()

        // 랜덤 데이터 추출
        for (i in 0..6) {
            val val1 = (Math.random() * 5).toFloat() // 앱1 값
            val val2 = (Math.random() * 5).toFloat() // 앱2 값
            entry1.add(Entry(i.toFloat(), val1))
            entry2.add(Entry(i.toFloat(), val2))
        }

        // 본인 데이터 추이
        val lineDataSet1 = LineDataSet(entry1, "응애1")
        chartData.addDataSet(lineDataSet1)
        lineDataSet1.lineWidth = 3f
        lineDataSet1.circleRadius = 6f
        lineDataSet1.setDrawValues(false)
        lineDataSet1.setDrawCircleHole(true)
        lineDataSet1.setDrawCircles(true)
        lineDataSet1.setDrawValues(true)
        lineDataSet1.setDrawHorizontalHighlightIndicator(false)
        lineDataSet1.setDrawHighlightIndicators(false)
        lineDataSet1.color = Color.rgb(255, 155, 155)
        lineDataSet1.setCircleColor(Color.rgb(255, 155, 155))

        // 목표 데이터
        val lineDataSet2 = LineDataSet(entry2, "응애2")
        chartData.addDataSet(lineDataSet2)
        lineDataSet2.apply {
            lineWidth = 3f
            circleRadius = 6f
            setDrawValues(true)
            setDrawCircleHole(true)
            setDrawCircles(true)
            setDrawHorizontalHighlightIndicator(false)
            setDrawHighlightIndicators(false)
            color = Color.rgb(178, 223, 138)
            setCircleColor(Color.rgb(178, 223, 138))
        }
        chartData.setValueTextSize(10f) // 점 위의 텍스트 크기 조정
        return chartData
    }

    // 차트에 데이터 반영
    private fun prepareChartData(data: LineData, lineChart: LineChart) {
        lineChart.data = data // LineData 전달
        lineChart.invalidate() // LineChart 갱신해 데이터 표시
    }

    private fun onClickButtons() {
        // 탭 버튼
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        prepareChartData(createChartData(), binding.lineChart)
                    }
                    1 -> {
                        prepareChartData(createChartData(), binding.lineChart)
                    }
                    2 -> {
                        prepareChartData(createChartData(), binding.lineChart)
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
                    prepareChartData(createChartData(), binding.lineChart)
                }
                R.id.rb_calorie -> {
                    prepareChartData(createChartData(), binding.lineChart)
                }
                R.id.rb_carbohydrates -> {
                    prepareChartData(createChartData(), binding.lineChart)
                }
                R.id.rb_protein -> {
                    prepareChartData(createChartData(), binding.lineChart)
                }
                R.id.rb_fat -> {
                    prepareChartData(createChartData(), binding.lineChart)
                }
            }
        }
    }
}