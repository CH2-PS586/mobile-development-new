package com.dicoding.elanhakim.fileManagerAI.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityDetailBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory

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

}