package com.kjh.dietmanagement.ui.homedialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentHomeDialogBinding
import com.kjh.dietmanagement.ui.mealform.MealFormActivity

class HomeDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentHomeDialogBinding
    private val args: HomeDialogFragmentArgs by navArgs()

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
        onMoveButton()
        binding.weekDay = args.weekday
    }

    private fun onMoveButton() {
        binding.btAdd.setOnClickListener {
            val intent = Intent(activity, MealFormActivity::class.java)
            startActivity(intent)
        }
    }
}