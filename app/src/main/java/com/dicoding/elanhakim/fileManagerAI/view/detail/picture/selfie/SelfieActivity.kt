package com.dicoding.elanhakim.fileManagerAI.view.detail.picture.selfie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivitySelfieBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureViewModel

class SelfieActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelfieBinding
    private lateinit var selfieAdapter: SelfieAdapter
    private val pictureViewModel by viewModels<PictureViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelfieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            rvSelfie.apply {
                selfieAdapter = SelfieAdapter()
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
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}