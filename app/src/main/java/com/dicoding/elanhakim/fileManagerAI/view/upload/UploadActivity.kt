package com.dicoding.elanhakim.fileManagerAI.view.upload

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.net.toUri
import com.dicoding.elanhakim.fileManagerAI.R
import com.dicoding.elanhakim.fileManagerAI.component.reduceFileImage
import com.dicoding.elanhakim.fileManagerAI.component.uriToFile
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityUploadBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.home.HomeActivity
import java.io.File

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private val uploadViewModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var currentFileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { openDirectory() }
        binding.uploadButton.setOnClickListener { uploadFile() }
    }

//    private fun startGallery() {
//        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//    }

//    private val launcherGallery = registerForActivityResult(
//        ActivityResultContracts.PickVisualMedia()
//    ) { uri: Uri? ->
//        if (uri != null) {
//            currentImageUri = uri
//            showImage()
//        } else {
//            Log.d("Photo Picker", "No media selected")
//        }
//    }

//    private fun showImage() {
//        currentImageUri?.let {
//            Log.d("Image URI", "showImage: $it")
//            binding.previewImageView.setImageURI(it)
//        }
//    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // Retrieve the selected file URI from the result
            currentFileUri = result.data?.data
            // Convert the URI to a File object
            val selectedFile = currentFileUri?.let { uri ->
                contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                    val nameIndex = cursor.getColumnIndexOrThrow("_display_name")
                    cursor.moveToFirst()
                    File(cursor.getString(nameIndex))
                }
            }
            // Display information about the selected file
            selectedFile?.let {
                binding.ivPreview.setImageResource(R.drawable.image_file)
                binding.textView.text = it.name
            }
        }
    }

    private fun openDirectory() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        launcher.launch(Intent.createChooser(intent, "Select File"))

    }

//    private fun uploadImage(token: String) {
//        currentImageUri?.let { uri ->
//            val file = uriToFile(uri, this).reduceFileImage()
//            Log.d("Image File", "showImage: ${file.path}")
//            val description = "this is the picture description"
//            showLoading(true)
//
//            uploadViewModel.uploadFile(file, description, token).observe(this) { result ->
//                when(result) {
//                    is ResultApi.Loading -> {
//                        showLoading(true)
//                    }
//                    is ResultApi.Success -> {
//                        showLoading(false)
//                        showToast("File has been Uploaded successfully")
//                        homeIsActivity()
//                    }
//                    is ResultApi.Error -> {
//                        showLoading(false)
//                        showToast("Upload File Failed")
//                    }
//                }
//            }
//        }
//    }

    private fun uploadFile() {
        currentFileUri?.let { uri ->
            val file = uriToFile(uri, this)
            val description = "this is the file description"
            showLoading(true)
            uploadViewModel.getSessionData().observe(this@UploadActivity) { user ->
                uploadViewModel.uploadFile(file, description, user.accessToken).observe(this) { result ->
                    when(result) {
                        is ResultApi.Loading -> {
                            showLoading(true)
                        }
                        is ResultApi.Success -> {
                            showLoading(false)
                            showToast("File has been Uploaded successfully")
                            homeIsActivity()
                        }
                        is ResultApi.Error -> {
                            showLoading(false)
                            showToast("Upload File Failed")
                        }
                    }
                }
            }
        }
    }

    private fun homeIsActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
