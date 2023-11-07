package com.kjh.dietmanagement.view.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.kjh.dietmanagement.databinding.FragmentJoinBinding
import com.kjh.dietmanagement.model.data.Join
import com.kjh.dietmanagement.view.common.ViewModelFactory
import com.kjh.dietmanagement.viewmodel.JoinViewModel
import java.util.regex.Pattern

class JoinFragment : Fragment() {

    private lateinit var binding: FragmentJoinBinding
    private val viewModel: JoinViewModel by viewModels { ViewModelFactory() }

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

        init()
        observer()
        onClickButton()
    }

    private fun init() {
        // 텍스트 변화 관찰
        with(binding) {
            etId.addTextChangedListener(JoinTextWatcher())
            etPassword.addTextChangedListener(JoinTextWatcher())
            etPasswordVerification.addTextChangedListener(JoinTextWatcher())
            etNickname.addTextChangedListener(JoinTextWatcher())
            etAge.addTextChangedListener(JoinTextWatcher())
            etTall.addTextChangedListener(JoinTextWatcher())
            etCurrentWeight.addTextChangedListener(JoinTextWatcher())
            etTargetWeight.addTextChangedListener(JoinTextWatcher())
        }
    }

    // observer
    private fun observer() {
        // 로그인
        viewModel.responseString.observe(viewLifecycleOwner) {
            when (it) {
                "응답 성공" -> {
                    Toast.makeText(requireContext(), "회원가입 성공 하였습니다.", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
                "응답 실패" -> {
                    Toast.makeText(requireContext(), "응답 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }
                "통신 실패" -> {
                    Toast.makeText(requireContext(), "통신 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onClickButton() {
        // 남자 성별 버튼
        binding.rbMale.setOnClickListener { joinButtonEnabled() }

        // 여자 성별 버튼
        binding.rbFemale.setOnClickListener { joinButtonEnabled() }

        // 뒤로 가기 버튼
        binding.ivBackButton.setOnClickListener { findNavController().navigateUp() }

        // 회원 가입 완료 버튼
        binding.tvJoinButton.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                viewModel.joinUser(
                    Join(
                        binding.etId.text.toString(), binding.etNickname.text.toString(),
                        binding.etPassword.text.toString(), if (binding.rbMale.isChecked) "남" else "여",
                        binding.etAge.text.toString(), binding.etCurrentWeight.text.toString().toDouble(),
                        binding.etTall.text.toString().toDouble(), binding.etTargetWeight.text.toString().toDouble(),
                    )
                )
            }
        }
    }

    // 회원 가입 버튼 활성화 여부
    private fun joinButtonEnabled() {
        binding.tvJoinButton.isEnabled = regularExpression()
    }

    // editText 입력 형식 검증
    private fun regularExpression(): Boolean {
        // 정규식 아이디: 영문자로 시작하는 영문자 또는 숫자 6~20자
        val idPattern = Pattern.compile("^[a-z]+[a-z0-9]{5,19}$")
        // 정규식 비밀번호: 8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합
        val passwordPattern = Pattern.compile("^.*(?=^.{8,16}$)(?=.*[0-9])(?=.*[a-zA-z])(?=.*[!@#$%^&+=]).*$")
        // 정규식 닉네임: 2자 이상 16자 이하, 영어 또는 숫자 또는 한글로 구성
        val nicknamePattern = Pattern.compile("^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$")

        // 검증
        with(binding) {
            val idMatcher = idPattern.matcher(etId.text.toString())
            val passwordMatcher = passwordPattern.matcher(etPassword.text.toString())
            val passwordVerificationMatcher = passwordPattern.matcher(etPasswordVerification.text.toString())
            val nicknameMatcher = nicknamePattern.matcher(etNickname.text.toString())

            return idMatcher.matches() && passwordMatcher.matches() && passwordVerificationMatcher.matches()
                    && nicknameMatcher.matches() && (rbMale.isChecked || rbFemale.isChecked) && etAge.text.toString().length == 8
                    && etTall.text.toString().isNotEmpty() && etCurrentWeight.text.toString().isNotEmpty() && etTargetWeight.text.toString().isNotEmpty()
                    && etPassword.text.toString() == etPasswordVerification.text.toString()
        }
    }

    inner class JoinTextWatcher: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { joinButtonEnabled() }
        override fun afterTextChanged(p0: Editable?) { }
    }
}