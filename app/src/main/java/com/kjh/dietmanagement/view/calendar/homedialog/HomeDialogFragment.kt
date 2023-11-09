package com.kjh.dietmanagement.view.calendar.homedialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentHomeDialogBinding
import com.kjh.dietmanagement.view.common.ViewModelFactory
import com.kjh.dietmanagement.viewmodel.HomeDialogViewModel
import kotlinx.coroutines.launch

class HomeDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentHomeDialogBinding
    private val args: HomeDialogFragmentArgs by navArgs()
    private val viewModel: HomeDialogViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.weekDay = args.weekday.substring(6)
        binding.lifecycleOwner = this.viewLifecycleOwner
        onClickButton()
        observer()

        lifecycleScope.launch {
            viewModel.getMeals(
                args.weekday.substring(2..3) + args.weekday.substring(6..7) + args.weekday.substring(10..11)
            )
        }
    }

    // observer
    private fun observer() {
        // 식단
        viewModel.responseMeal.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "응답 성공 하였습니다.", Toast.LENGTH_SHORT).show()
            val adapter = HomeDialogAdapter {

            }
            binding.recyclerView.adapter = adapter
            adapter.submitList(it.mealData)
        }

        // 메세지
        viewModel.messageCheck.observe(viewLifecycleOwner) {
            when (it) {
                "응답 실패" -> {
                    Toast.makeText(requireContext(), "응답 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }
                "연결 실패" -> {
                    Toast.makeText(requireContext(), "연결 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // MealFormFragment 로 이동
    private fun onClickButton() {
        binding.btAdd.setOnClickListener {
            val date = args.weekday.substring(2..3) + args.weekday.substring(6..7) + args.weekday.substring(10..11)
            val action = HomeDialogFragmentDirections.actionHomeDialogFragmentToMealFormFragment(date)
            findNavController().navigate(action)
        }
    }
}