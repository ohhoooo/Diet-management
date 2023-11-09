package com.kjh.dietmanagement.view.calendar.homedialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        val date = args.weekday.substring(2..3) + args.weekday.substring(6..7) + args.weekday.substring(10..11)

        observer(date)

        lifecycleScope.launch {
            viewModel.getMeals(date)
        }
    }

    // observer
    private fun observer(date: String) {
        // 식단
        viewModel.responseMeal.observe(viewLifecycleOwner) {
            val adapter = HomeDialogAdapter { type, foodName ->
                AlertDialog.Builder(requireContext())
                    .setTitle("정말로 삭제하시겠습니까?")
                    .setPositiveButton("네") { _, _ ->
                        lifecycleScope.launch {
                            viewModel.removeMeal(date, type, foodName)
                        }
                    }
                    .setNegativeButton("아니오") { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                    .show()
            }
            binding.recyclerView.adapter = adapter
            adapter.submitList(it.mealData)
        }

        // 식단 메세지
        viewModel.messageCheck.observe(viewLifecycleOwner) {
            when (it) {
                "응답 실패" -> {
                    Toast.makeText(requireContext(), "식단 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
                "연결 실패" -> {
                    Toast.makeText(requireContext(), "연결 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 삭제 메세지
        viewModel.messageRemove.observe(viewLifecycleOwner) {
            when (it) {
                "응답 성공" -> {
                    Toast.makeText(requireContext(), "성공적으로 삭제 하였습니다.", Toast.LENGTH_SHORT).show()
                    dialog?.cancel()
                }
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