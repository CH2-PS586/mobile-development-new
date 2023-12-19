package com.dicoding.elanhakim.fileManagerAI.view.detail.others

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityOthersBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory

class OthersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOthersBinding
    private lateinit var othersAdapter: OthersAdapter
    private val othersViewModel by viewModels<OthersViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOthersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            rvOthers.apply {
                othersAdapter = OthersAdapter()
                adapter = othersAdapter
                layoutManager = LinearLayoutManager(this@OthersActivity)
                addItemDecoration(DividerItemDecoration(this@OthersActivity, (rvOthers.layoutManager as LinearLayoutManager).orientation))
            }
        }
        setUpData()
    }

    private fun setUpData(){
        othersViewModel.getSessionData().observe(this@OthersActivity) {user ->
            othersViewModel.getOthers(user.accessToken).observe(this) {result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                        othersAdapter.clearList()
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        othersAdapter.setList(result.data)
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