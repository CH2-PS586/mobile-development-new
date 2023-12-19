package com.dicoding.elanhakim.fileManagerAI.view.detail.picture.memes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityMemesBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureViewModel

class MemesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMemesBinding
    private lateinit var memesAdapter: MemesAdapter
    private val pictureViewModel by viewModels<PictureViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            rvMemes.apply {
                memesAdapter = MemesAdapter()
                adapter = memesAdapter
                layoutManager = LinearLayoutManager(this@MemesActivity)
                addItemDecoration(DividerItemDecoration(this@MemesActivity, (rvMemes.layoutManager as LinearLayoutManager).orientation))
            }
        }
        setUpData()
    }

    private fun setUpData(){
        pictureViewModel.getSessionData().observe(this@MemesActivity) {user ->
            pictureViewModel.getMemes(user.accessToken).observe(this) {result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                        memesAdapter.clearList()
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        memesAdapter.setList(result.data)
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