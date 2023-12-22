package com.dicoding.elanhakim.fileManagerAI.view.download

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.dicoding.elanhakim.fileManagerAI.R
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityDownloadLabelBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.home.HomeActivity

class DownloadLabelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDownloadLabelBinding
    private lateinit var fileName: String
    private lateinit var category: String
    private lateinit var label: String

    private val downloadViewModel by viewModels<DownloadViewModel>{
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadLabelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))
        supportActionBar?.title = getString(R.string.download)

        fileName = intent.getStringExtra(NAME).toString()
        category = intent.getStringExtra(CATEGORY).toString()
        label = intent.getStringExtra(LABEL).toString()

        binding.imageView.setImageResource(R.drawable.image_file)
        binding.textView.text = fileName

        binding.button.setOnClickListener {
            setUpAction()
        }

    }

    private fun setUpAction() {
        val file = fileName
        val category = category
        val label = label
        downloadViewModel.getSessionData().observe(this@DownloadLabelActivity) { user ->
            downloadViewModel.downloadFile(user.accessToken, category, label, file)
                .observe(this) { result ->
                    when (result) {
                        is ResultApi.Loading -> {
                            showLoading(true)
                        }

                        is ResultApi.Success -> {
                            showLoading(false)
                            showToast(getString(R.string.download_success))
                            homeIsActivity()
                        }

                        is ResultApi.Error -> {
                            showLoading(false)
                            showToast(getString(R.string.download_failed))
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
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val NAME = "idMove"
        const val CATEGORY = "category"
        const val LABEL = "label"
    }
}