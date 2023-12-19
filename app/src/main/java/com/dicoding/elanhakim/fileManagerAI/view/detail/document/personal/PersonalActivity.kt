package com.dicoding.elanhakim.fileManagerAI.view.detail.document.personal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityPersonalBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.document.DocumentViewModel

class PersonalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalBinding
    private lateinit var personalAdapter: PersonalAdapter
    private val documentViewModel by viewModels<DocumentViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            rvPersonal.apply {
                personalAdapter = PersonalAdapter()
                adapter = personalAdapter
                layoutManager = LinearLayoutManager(this@PersonalActivity)
                addItemDecoration(DividerItemDecoration(this@PersonalActivity, (rvPersonal.layoutManager as LinearLayoutManager).orientation))
            }
        }
        setUpData()
    }

    private fun setUpData(){
        documentViewModel.getSessionData().observe(this@PersonalActivity) {user ->
            documentViewModel.getPersonal(user.accessToken).observe(this) {result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                        personalAdapter.clearList()
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        personalAdapter.setList(result.data)
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