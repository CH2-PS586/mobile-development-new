package com.dicoding.elanhakim.fileManagerAI.view.detail.document.school

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivitySchoolBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.document.DocumentViewModel

class SchoolActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySchoolBinding
    private lateinit var schoolAdapter: SchoolAdapter
    private val documentViewModel by viewModels<DocumentViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            rvSchool.apply {
                schoolAdapter = SchoolAdapter()
                adapter = schoolAdapter
                layoutManager = LinearLayoutManager(this@SchoolActivity)
                addItemDecoration(DividerItemDecoration(this@SchoolActivity, (rvSchool.layoutManager as LinearLayoutManager).orientation))
            }
        }
        setUpData()
    }

    private fun setUpData(){
        documentViewModel.getSessionData().observe(this@SchoolActivity) {user ->
            documentViewModel.getSchool(user.accessToken).observe(this) {result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                        schoolAdapter.clearList()
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        schoolAdapter.setList(result.data)
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