package com.kjh.dietmanagement.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentJoinBinding

class JoinFragment : Fragment() {

    private lateinit var binding: FragmentJoinBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_join, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickButton()
    }

    private fun onClickButton() {
        // 회원 가입 완료 버튼
        binding.tvJoinButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}