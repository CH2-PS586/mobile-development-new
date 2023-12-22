package com.dicoding.elanhakim.fileManagerAI.view.detail.video

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
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.video.VideoResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityVideoBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.download.DownloadActivity

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding
    private lateinit var videoAdapter: VideoAdapter
    private val videoViewModel by viewModels<VideoViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))
        supportActionBar?.title = getString(R.string.video)

        with(binding) {
            rvVideo.apply {
                videoAdapter = VideoAdapter()
                adapter = videoAdapter
                layoutManager = LinearLayoutManager(this@VideoActivity)
                addItemDecoration(DividerItemDecoration(this@VideoActivity, (rvVideo.layoutManager as LinearLayoutManager).orientation))
            }
        }
        setUpData()
    }

    private fun setUpData(){
        videoViewModel.getSessionData().observe(this@VideoActivity) {user ->
            videoViewModel.getVideo(user.accessToken).observe(this) {result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                        videoAdapter.clearList()
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        videoAdapter.setList(result.data)
                    }
                    is ResultApi.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
        videoAdapter.setOnItemClickCallback(object : VideoAdapter.OnItemClickCallback{
            override fun onItemClicked(video: VideoResponse) {
                Intent(this@VideoActivity, DownloadActivity::class.java).also {
                    it.putExtra(DownloadActivity.NAME, video.filename)
                    it.putExtra(DownloadActivity.CATEGORY, "video")
                    startActivity(it)
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}