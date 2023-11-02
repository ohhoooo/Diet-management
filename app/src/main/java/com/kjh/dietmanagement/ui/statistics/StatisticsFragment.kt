package com.kjh.dietmanagement.ui.statistics

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
import com.github.mikephil.charting.formatter.ValueFormatter
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

        initLineChart()
        prepareChartData(createChartData(2), binding.lineChart)
    }

    private fun initLineChart() {
        binding.lineChart.extraBottomOffset = 15f // 간격
        binding.lineChart.description.isEnabled = false // chart 밑에 description 표시 유무

        // 차트의 범례
        with(binding.lineChart.legend) {
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

        // XAxis (아래쪽) - 선 유무, 사이즈, 색상, 축 위치 설정
        with(binding.lineChart.xAxis) {
            setDrawAxisLine(false)
            setDrawGridLines(false)
            position = XAxis.XAxisPosition.BOTTOM // x축 데이터 표시 위치
            granularity = 1f
            textSize = 14f
            textColor = Color.rgb(118, 118, 118)
            spaceMin = 0.3f // Chart 맨 왼쪽 간격 띄우기
            spaceMax = 0.3f // Chart 맨 오른쪽 간격 띄우기
        }

        // YAxis(Right) (왼쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
        with(binding.lineChart.axisLeft) {
            textSize = 14f
            textColor = Color.rgb(163, 163, 163)
            setDrawAxisLine(false)
            axisLineWidth = 2f
            axisMinimum = 0f // 최솟값
            axisMaximum = 5.0f//RANGE.get(0).get(range) as Float // 최댓값
            granularity = 1.0f//RANGE.get(1).get(range) as Float
        }

        // YAxis(Left) (오른쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
        with(binding.lineChart.axisRight) {
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

    private fun createChartData(range: Int): LineData {
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
        lineDataSet1.setDrawHorizontalHighlightIndicator(false)
        lineDataSet1.setDrawHighlightIndicators(false)
        lineDataSet1.color = Color.rgb(255, 155, 155)
        lineDataSet1.setCircleColor(Color.rgb(255, 155, 155))

        // 목표 데이터
        val lineDataSet2 = LineDataSet(entry2, "응애2")
        chartData.addDataSet(lineDataSet2)
        lineDataSet2.lineWidth = 3f
        lineDataSet2.circleRadius = 6f
        lineDataSet2.setDrawValues(false)
        lineDataSet2.setDrawCircleHole(true)
        lineDataSet2.setDrawCircles(true)
        lineDataSet2.setDrawHorizontalHighlightIndicator(false)
        lineDataSet2.setDrawHighlightIndicators(false)
        lineDataSet2.color = Color.rgb(178, 223, 138)
        lineDataSet2.setCircleColor(Color.rgb(178, 223, 138))

        chartData.setValueTextSize(15f)
        return chartData
    }

    private fun prepareChartData(data: LineData, lineChart: LineChart) {
        lineChart.data = data // LineData 전달
        lineChart.invalidate() // LineChart 갱신해 데이터 표시
    }
}