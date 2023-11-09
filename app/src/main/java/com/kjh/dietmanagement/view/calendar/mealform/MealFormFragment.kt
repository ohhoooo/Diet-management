package com.kjh.dietmanagement.view.calendar.mealform

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentMealFormBinding
import com.kjh.dietmanagement.model.data.Food
import com.kjh.dietmanagement.model.data.MealForm
import com.kjh.dietmanagement.view.common.OnClickInterface
import com.kjh.dietmanagement.view.common.ViewModelFactory
import com.kjh.dietmanagement.viewmodel.MainActivityViewModel
import com.kjh.dietmanagement.viewmodel.MealFormViewModel
import java.io.ByteArrayOutputStream
import java.util.Base64


class MealFormFragment : Fragment(), OnClickInterface {

    private lateinit var adapter: MealFormAdapter
    private lateinit var binding: FragmentMealFormBinding
    private val activityViewModel: MainActivityViewModel by activityViewModels()
    private val viewModel: MealFormViewModel by viewModels { ViewModelFactory() }

    private val args: MealFormFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meal_form, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this.viewLifecycleOwner

        adapter()
        observer()
        onClickButtons()
    }

    // adapter
    private fun adapter() {
        adapter = MealFormAdapter(this)
        binding.recyclerView.adapter = adapter
    }

    // observer
    private fun observer() {
        // 저장
        viewModel.message.observe(viewLifecycleOwner) { message ->
            when (message) {
                "응답 성공" -> {
                    Toast.makeText(requireContext(), "성공적으로 저장하였습니다.", Toast.LENGTH_SHORT).show()
                    activityViewModel.removePhoto()
                    activityViewModel.resetFood()
                    findNavController().navigateUp()
                }
                "응답 실패" -> {
                    Toast.makeText(requireContext(), "응답 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }
                "연결 실패" -> {
                    Toast.makeText(requireContext(), "연결 실패 하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // food
        activityViewModel.food.observe(viewLifecycleOwner) { foodList ->
            adapter.submitList(foodList)
            binding.nutrient = Food(
                "",
                foodList.sumOf { it.calorie },
                foodList.sumOf { it.carbohydrate },
                foodList.sumOf { it.protein },
                foodList.sumOf { it.fat }
            )
        }

        // photo
        activityViewModel.photo.observe(viewLifecycleOwner) {
            with(binding) {
                if (it == Uri.EMPTY) {
                    ivFood.visibility = View.GONE
                    btDelete.visibility = View.GONE
                    btAddImage.visibility = View.VISIBLE
                } else {
                    ivFood.setImageURI(it)
                    ivFood.visibility = View.VISIBLE
                    btDelete.visibility = View.VISIBLE
                    btAddImage.visibility = View.GONE
                }
            }
        }
    }

    // button click
    @RequiresApi(Build.VERSION_CODES.O)
    private fun onClickButtons() {
        // FoodSearchFragment 로 이동
        binding.btAddFood.setOnClickListener {
            val action = MealFormFragmentDirections.actionMealFormFragmentToFoodSearchFragment()
            findNavController().navigate(action)
        }

        // PhotoDialogFragment 로 이동
        binding.btAddImage.setOnClickListener {
            val action = MealFormFragmentDirections.actionMealFormFragmentToPhotoDialogFragment()
            findNavController().navigate(action)
        }

        // 뒤로 가기
        binding.ivArrowBack.setOnClickListener {
            activityViewModel.resetFood()
            activityViewModel.removePhoto()
            findNavController().navigateUp()
        }

        // 이미지 삭제
        binding.btDelete.setOnClickListener {
            activityViewModel.removePhoto()
        }

        // 저장 하기
        binding.btSave.setOnClickListener {
            with(binding) {
                if(groupRadio.checkedRadioButtonId != -1 && etTall.text.toString().isNotBlank() &&
                    etCurrentWeight.text.toString().isNotBlank() && !activityViewModel.food.value.isNullOrEmpty()) {
                    val classification = when (groupRadio.checkedRadioButtonId) {
                        2131296672 -> "아침"
                        2131296673 -> "점심"
                        2131296674 -> "저녁"
                        else -> "간식"
                    }

                    lifecycleScope.launchWhenCreated {
                        viewModel.saveMeal(
                            MealForm(
                                args.weekday,
                                classification,
                                activityViewModel.food.value!!.map { it.name },
                                etCurrentWeight.text.toString().toDouble(),
                                etTall.text.toString().toDouble(),
                                if(activityViewModel.photo.value == null) ""
                                else bitmapToString(
                                    resizeBitmapWithAspectRatio(
                                        getBitmapFromUri(activityViewModel.photo.value!!)!!,
                                        500,
                                        500
                                    )
                                ),
                            )
                        )
                        Log.d("2132132", activityViewModel.photo.value.toString())
                    }
                }
            }
        }
    }

    // Uri -> Bitmap
    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            // URI를 통해 비트맵을 가져옵니다.
            val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun resizeBitmapWithAspectRatio(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height

        val ratioX = maxWidth.toFloat() / originalWidth
        val ratioY = maxHeight.toFloat() / originalHeight

        val scaleFactor = if (ratioX < ratioY) ratioX else ratioY

        val newWidth = (originalWidth * scaleFactor).toInt()
        val newHeight = (originalHeight * scaleFactor).toInt()

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    // Bitmap -> String
    @RequiresApi(Build.VERSION_CODES.O)
    private fun bitmapToString(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream)

        val bytes = stream.toByteArray()

        return Base64.getEncoder().encodeToString(bytes)
    }


    // button click -> position food item remove
    override fun onClick(position: Int) {
        activityViewModel.removeFood(position)
    }
}