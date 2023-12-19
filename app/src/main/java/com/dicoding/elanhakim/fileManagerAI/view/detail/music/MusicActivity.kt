package com.dicoding.elanhakim.fileManagerAI.view.detail.music

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityMusicBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory

class MusicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMusicBinding
    private lateinit var musicAdapter: MusicAdapter
    private val musicViewModel by viewModels<MusicViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            rvMusic.apply {
                musicAdapter = MusicAdapter()
                adapter = musicAdapter
                layoutManager = LinearLayoutManager(this@MusicActivity)
                addItemDecoration(DividerItemDecoration(this@MusicActivity, (rvMusic.layoutManager as LinearLayoutManager).orientation))
            }
        }
        setUpData()
    }

    private fun setUpData(){
        musicViewModel.getSessionData().observe(this@MusicActivity) {user ->
            musicViewModel.getMusic(user.accessToken).observe(this) {result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                        musicAdapter.clearList()
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        musicAdapter.setList(result.data)
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