package com.dicoding.elanhakim.fileManagerAI.view.detail.document.work

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
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.document.DocumentResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityWorkBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.document.DocumentAdapter
import com.dicoding.elanhakim.fileManagerAI.view.detail.document.DocumentViewModel
import com.dicoding.elanhakim.fileManagerAI.view.download.DownloadLabelActivity

class WorkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkBinding
    private lateinit var workAdapter: DocumentAdapter
    private val documentViewModel by viewModels<DocumentViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))
        supportActionBar?.title = getString(R.string.work)

        with(binding) {
            rvWork.apply {
                workAdapter = DocumentAdapter()
                adapter = workAdapter
                layoutManager = LinearLayoutManager(this@WorkActivity)
                addItemDecoration(DividerItemDecoration(this@WorkActivity, (rvWork.layoutManager as LinearLayoutManager).orientation))
            }
        }
        setUpData()
    }

    private fun setUpData(){
        documentViewModel.getSessionData().observe(this@WorkActivity) { user ->
            documentViewModel.getWork(user.accessToken).observe(this) {result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                        workAdapter.clearList()
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        workAdapter.setList(result.data)
                    }
                    is ResultApi.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
        workAdapter.setOnItemClickCallback(object : DocumentAdapter.OnItemClickCallback{
            override fun onItemClicked(file: DocumentResponse) {
                Intent(this@WorkActivity, DownloadLabelActivity::class.java).also {
                    it.putExtra(DownloadLabelActivity.NAME, file.filename)
                    it.putExtra(DownloadLabelActivity.CATEGORY, "document")
                    it.putExtra(DownloadLabelActivity.LABEL, "School")
                    startActivity(it)
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}