package com.kjh.dietmanagement.view.calendar.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentHomeBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import org.threeten.bp.LocalDate

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.calendarView.apply {
            // Calendar 의 Header 가 표시되는 방법 Custom
            setTitleFormatter(CalendarTitleFormat())
            // 앱 실행 시 오늘 날짜에 focus
            setDateSelected(CalendarDay.today(), true)
            // 달력의 날짜를 누르면 listFragment 로 이동
            setOnDateChangedListener { widget, date, selected ->
                val action = HomeFragmentDirections.actionHomeFragmentToHomeDialogFragment(getWeekday(date))
                findNavController().navigate(action)
            }
        }

        // 테스트(점 찍기) 하기 위해 임의로 구현
        val myDate1 = CalendarDay.from(2023, 11, 30)
        val myDate2 = CalendarDay.from(2023, 11, 20)
        val myDate3 = CalendarDay.from(2023, 11, 10)
        val hashSet = HashSet<CalendarDay>()
        hashSet.add(myDate1)
        hashSet.add(myDate2)
        hashSet.add(myDate3)
        binding.calendarView.addDecorator(DayDisableDecorator(hashSet))
    }

    // Calendar 의 Header 가 표시되는 방법 Custom
    inner class CalendarTitleFormat : TitleFormatter {
        // 화면을 넘길 때 마다 호출된다.
        override fun format(day: CalendarDay?): CharSequence {
            // CalendarDay 는 LocalDate 기반으로 만들어진 클래스
            val getDate = getDate(day!!)
            val calendarBuilder: StringBuilder = StringBuilder().apply {
                append(getDate[0]) // 년
                append(" ") // 공백
                append(getDate[1]) // 월
            }
            return calendarBuilder.toString()
        }
    }

    // 년, 월, 일로 나누어서 반환
    private fun getDate(date: CalendarDay): List<String> {
        return date.date.toString().split("-") // date.date 는 2023-04-21 와 같은 LocalDate 타입을 반환한다.
    }

    // 월, 일, 요일로 나누어서 반환
    private fun getWeekday(day: CalendarDay): String {
        val date = getDate(day)
        val localDate = LocalDate.of(day.year, day.month, day.day)
        return when(localDate.dayOfWeek.value) {
            1 -> getWeekdayString(date, "월")
            2 -> getWeekdayString(date, "화")
            3 -> getWeekdayString(date, "수")
            4 -> getWeekdayString(date, "목")
            5 -> getWeekdayString(date, "금")
            6 -> getWeekdayString(date, "토")
            else -> getWeekdayString(date, "일")
        }
    }

    // 월, 일, 요일을 문장으로 만들어서 반환
    private fun getWeekdayString(date: List<String>, weekDay: String): String {
        return "${date[1]}월 ${date[2]}일 ${weekDay}요일"
    }

    // 음식을 먹은 날짜에 점 표시 하는 클래스
    inner class DayDisableDecorator(private var dates: HashSet<CalendarDay>) : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return dates.contains(day)
        }

        override fun decorate(view: DayViewFacade?) {
            view?.addSpan(DotSpan(9F, Color.parseColor("#FF0000")))
        }
    }
}