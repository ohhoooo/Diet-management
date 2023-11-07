package com.kjh.dietmanagement.view.calendar.photodialog

import android.Manifest
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kjh.dietmanagement.R
import com.kjh.dietmanagement.databinding.FragmentPhotoDialogBinding
import com.kjh.dietmanagement.viewmodel.PhotoViewModel
import java.text.SimpleDateFormat
import java.util.*

class PhotoDialogFragment : BottomSheetDialogFragment() {

    private lateinit var getTakePicture: ActivityResultLauncher<Uri>
    private lateinit var getContentImage: ActivityResultLauncher<String>
    private lateinit var requestCameraPermission: ActivityResultLauncher<String>
    private lateinit var requestFilePermission: ActivityResultLauncher<Array<String>>
    private lateinit var pictureUri: Uri
    private lateinit var binding: FragmentPhotoDialogBinding
    private val viewModel: PhotoViewModel by activityViewModels()
    private val cameraPermission = Manifest.permission.CAMERA
    private val filePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
        )
    } else {
        arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickButtons()
        getPhoto()
        getPermission()
    }

    // button click
    private fun onClickButtons() {
        // 카메라 열기 버튼을 눌렀을 때
        binding.tvOpenCamera.setOnClickListener {
            requestCameraPermission.launch(cameraPermission)
        }

        // 파일 열기 버튼을 눌렀을 때
        binding.tvOpenAlbum.setOnClickListener {
            requestFilePermission.launch(filePermissions)
        }

        // 취소 버튼을 눌렀을 때
        binding.tvCancel.setOnClickListener {
            dismiss()
        }
    }

    // 사진 가져오기
    private fun getPhoto() {
        // 파일 불러오기
        getContentImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                viewModel.setPhoto(uri)
                dismiss()
            }
        }

        // 사진 찍고 저장 후 가져오기
        getTakePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { isTaken ->
            if(isTaken) {
                pictureUri.let { viewModel.setPhoto(it) }
                dismiss()
            }
        }
    }

    // 권한 휙득하기
    private fun getPermission() {
        // 권한 요청 - 사진
        requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if(result) {
                pictureUri = createImageFile()!!
                getTakePicture.launch(pictureUri)
            }else {
                Toast.makeText(activity, "권한을 허용하셔야 합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 권한 요청 - 파일
        requestFilePermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                results.forEach {
                    if (it.value) {
                        getContentImage.launch("image/*")
                    } else {
                        Toast.makeText(activity, "권한을 허용하셔야 합니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                if (results.count { !it.value } == 0) {
                    getContentImage.launch("image/*")
                } else {
                    Toast.makeText(activity, "권한을 허용하셔야 합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createImageFile(): Uri? {
        val now = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }
}