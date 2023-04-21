package com.kjh.dietmanagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.kjh.dietmanagement.databinding.FragmentFoodDiaryBinding

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import org.threeten.bp.LocalDate

class FoodDiaryFragment : Fragment() {

    private lateinit var binding: FragmentFoodDiaryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_food_diary, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.apply {
            setTitleFormatter(CalendarTitleFormat()) // Calendar 의 Header 가 표시되는 방법 Custom
        }
    }

    // Calendar 의 Header 가 표시되는 방법 Custom
    inner class CalendarTitleFormat : TitleFormatter {
        override fun format(day: CalendarDay?): CharSequence { // 화면을 넘길 때 마다 호출된다.
            // CalendarDay 클래스는 LocalDate 기반으로 만들어진 클래스다
            val inputText: LocalDate = day!!.date // 2023-04-21 와 같은 LocalDate 타입을 반환한다.
            val calendarHeaderElements = inputText.toString().split("-")
            val calendarHeaderBuilder: StringBuilder = StringBuilder()
            calendarHeaderBuilder.append(calendarHeaderElements[0]) // 년
                .append(" ") // 공백
                .append(calendarHeaderElements[1]) // 월
            return calendarHeaderBuilder.toString()
        }
    }
}