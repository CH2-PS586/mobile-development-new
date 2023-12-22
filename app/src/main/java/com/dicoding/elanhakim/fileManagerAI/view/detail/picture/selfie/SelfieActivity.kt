package com.dicoding.elanhakim.fileManagerAI.view.detail.picture.selfie

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.elanhakim.fileManagerAI.R
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.picture.PictureResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivitySelfieBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureAdapter
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureViewModel
import com.dicoding.elanhakim.fileManagerAI.view.download.DownloadLabelActivity

class SelfieActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelfieBinding
    private lateinit var selfieAdapter: PictureAdapter
    private val pictureViewModel by viewModels<PictureViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelfieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))
        supportActionBar?.title = getString(R.string.selfie)

        with(binding) {
            rvSelfie.apply {
                selfieAdapter = PictureAdapter()
                adapter = selfieAdapter
                layoutManager = LinearLayoutManager(this@SelfieActivity)
                addItemDecoration(DividerItemDecoration(this@SelfieActivity, (rvSelfie.layoutManager as LinearLayoutManager).orientation))
            }
        }
        setUpData()
    }

    private fun setUpData(){
        pictureViewModel.getSessionData().observe(this@SelfieActivity) {user ->
            pictureViewModel.getSelfie(user.accessToken).observe(this) {result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                        selfieAdapter.clearList()
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        selfieAdapter.setList(result.data)
                    }
                    is ResultApi.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
        selfieAdapter.setOnItemClickCallback(object : PictureAdapter.OnItemClickCallback{
            override fun onItemClicked(file: PictureResponse) {
                Intent(this@SelfieActivity, DownloadLabelActivity::class.java).also {
                    it.putExtra(DownloadLabelActivity.NAME, file.filename)
                    it.putExtra(DownloadLabelActivity.CATEGORY, "picture")
                    it.putExtra(DownloadLabelActivity.LABEL, "Selfie")
                    startActivity(it)
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}