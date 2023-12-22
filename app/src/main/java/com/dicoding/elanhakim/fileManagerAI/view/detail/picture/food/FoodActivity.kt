package com.dicoding.elanhakim.fileManagerAI.view.detail.picture.food

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
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityFoodBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureAdapter
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureViewModel
import com.dicoding.elanhakim.fileManagerAI.view.download.DownloadLabelActivity

class FoodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodBinding
    private lateinit var foodAdapter: PictureAdapter
    private val pictureViewModel by viewModels<PictureViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))
        supportActionBar?.title = getString(R.string.food)

        with(binding) {
            rvFood.apply {
                foodAdapter = PictureAdapter()
                adapter = foodAdapter
                layoutManager = LinearLayoutManager(this@FoodActivity)
                addItemDecoration(DividerItemDecoration(this@FoodActivity, (rvFood.layoutManager as LinearLayoutManager).orientation))
            }
        }
        setUpData()
    }

    private fun setUpData(){
        pictureViewModel.getSessionData().observe(this@FoodActivity) {user ->
            pictureViewModel.getFood(user.accessToken).observe(this) {result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                        foodAdapter.clearList()
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        foodAdapter.setList(result.data)
                    }
                    is ResultApi.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
        foodAdapter.setOnItemClickCallback(object : PictureAdapter.OnItemClickCallback{
            override fun onItemClicked(file: PictureResponse) {
                Intent(this@FoodActivity, DownloadLabelActivity::class.java).also {
                    it.putExtra(DownloadLabelActivity.NAME, file.filename)
                    it.putExtra(DownloadLabelActivity.CATEGORY, "picture")
                    it.putExtra(DownloadLabelActivity.LABEL, "Food")
                    startActivity(it)
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}