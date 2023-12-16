package com.dicoding.elanhakim.storyApp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.dicoding.elanhakim.storyApp.R
import com.dicoding.elanhakim.storyApp.data.remote.response.ResultApi
import com.dicoding.elanhakim.storyApp.data.remote.response.file.music.MusicResponseItem
import com.dicoding.elanhakim.storyApp.data.remote.response.user.User
import com.dicoding.elanhakim.storyApp.databinding.ActivityDetailBinding
import com.dicoding.elanhakim.storyApp.view.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun setUpData(){
        detailViewModel.getSessionData().observe(this@DetailActivity) { user ->
            detailViewModel.getMusic(user.accessToken).observe(this) { result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        with(binding){

                        }
                    }

                    else -> {}
                }
            }
        }
    }

//    fun setListFile(listFile: List<MusicResponseItem>){
//        val adapter = ListMusicAdapter()
//        adapter.submitList(listFile)
//        binding.rvDetail.adapter = adapter
//    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}