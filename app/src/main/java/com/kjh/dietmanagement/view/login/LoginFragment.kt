package com.kjh.dietmanagement.view.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentLoginBinding
import com.kjh.dietmanagement.model.data.Login
import com.kjh.dietmanagement.view.common.ViewModelFactory
import com.kjh.dietmanagement.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner

        observer()
        onClickButton()
    }

    // observer
    private fun observer() {
        viewModel.responseString.observe(viewLifecycleOwner) {
            when (it) {
                "로그인 성공" -> {
                    Toast.makeText(requireContext(), "로그인 성공 하였습니다.", Toast.LENGTH_SHORT).show()
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
                "로그인 실패" -> {
                    Toast.makeText(requireContext(), "로그인 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onClickButton() {
        // 로그인 버튼
        binding.tvLoginButton.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                viewModel.loginUser(
                    Login(binding.etId.text.toString(), binding.etPassword.text.toString())
                )
            }
        }

        // 회원 가입 버튼
        binding.tvJoin.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToJoinFragment()
            findNavController().navigate(action)
        }
    }
}