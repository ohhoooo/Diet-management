package com.kjh.dietmanagement.view.ranking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentRankingBinding
import com.kjh.dietmanagement.view.common.ViewModelFactory
import com.kjh.dietmanagement.viewmodel.RankingViewModel

class RankingFragment : Fragment() {

    private lateinit var binding: FragmentRankingBinding
    private val viewModel: RankingViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ranking, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner

        // 랭킹 조회
        lifecycleScope.launchWhenCreated {
            viewModel.checkRanking()
        }

        observer()
    }

    // observer
    private fun observer() {
        // 랭킹
        viewModel.responseRanking.observe(viewLifecycleOwner) {
            // 내 등수
            binding.rank = it
            // 전체 등수
            val adapter = RankingAdapter()
            binding.recyclerview.adapter = adapter
            adapter.submitList(it.list)
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
}