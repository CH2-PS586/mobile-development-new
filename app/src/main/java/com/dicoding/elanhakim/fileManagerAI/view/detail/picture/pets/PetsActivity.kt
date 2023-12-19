package com.dicoding.elanhakim.fileManagerAI.view.detail.picture.pets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityPetsBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureViewModel

class PetsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetsBinding
    private lateinit var petsAdapter: PetsAdapter
    private val pictureViewModel by viewModels<PictureViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            rvPets.apply {
                petsAdapter = PetsAdapter()
                adapter = petsAdapter
                layoutManager = LinearLayoutManager(this@PetsActivity)
                addItemDecoration(DividerItemDecoration(this@PetsActivity, (rvPets.layoutManager as LinearLayoutManager).orientation))
            }
        }
        setUpData()
    }

    private fun setUpData(){
        pictureViewModel.getSessionData().observe(this@PetsActivity) {user ->
            pictureViewModel.getPets(user.accessToken).observe(this) {result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                        petsAdapter.clearList()
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        petsAdapter.setList(result.data)
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