package com.kjh.dietmanagement.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentLoginBinding
import com.kjh.dietmanagement.model.Login
import com.kjh.dietmanagement.model.ResponseLogin
import com.kjh.dietmanagement.network.ApiClient
import retrofit2.Call
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

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

        onClickButton()
    }

    private fun onClickButton() {
        // 로그인 버튼
        binding.tvLoginButton.setOnClickListener {
            ApiClient.create().loginUser(Login(binding.etId.text.toString(), binding.etPassword.text.toString())).enqueue(object : retrofit2.Callback<ResponseLogin> {
                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    if (response.isSuccessful) {
                        Log.d("Response Yes: ", response.body().toString())
                        Toast.makeText(requireContext(), "로그인 성공 하였습니다.", Toast.LENGTH_SHORT).show()
                        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                        findNavController().navigate(action)
                    } else {
                        Log.d("Response NO: ", response.body()?.message.toString())
                        Toast.makeText(requireContext(), "로그인 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    Log.d("Response onFailure: ", t.localizedMessage)
                    Toast.makeText(requireContext(), "로그인 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // 회원 가입 버튼
        binding.tvJoin.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToJoinFragment()
            findNavController().navigate(action)
        }
    }
}