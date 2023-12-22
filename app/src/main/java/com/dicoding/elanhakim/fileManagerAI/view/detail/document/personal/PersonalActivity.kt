package com.dicoding.elanhakim.fileManagerAI.view.detail.document.personal

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
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityPersonalBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.document.DocumentAdapter
import com.dicoding.elanhakim.fileManagerAI.view.detail.document.DocumentViewModel
import com.dicoding.elanhakim.fileManagerAI.view.download.DownloadLabelActivity

class PersonalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalBinding
    private lateinit var personalAdapter: DocumentAdapter
    private val documentViewModel by viewModels<DocumentViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))
        supportActionBar?.title = getString(R.string.personal)

        with(binding) {
            rvPersonal.apply {
                personalAdapter = DocumentAdapter()
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
        personalAdapter.setOnItemClickCallback(object : DocumentAdapter.OnItemClickCallback{
            override fun onItemClicked(file: DocumentResponse) {
                Intent(this@PersonalActivity, DownloadLabelActivity::class.java).also {
                    it.putExtra(DownloadLabelActivity.NAME, file.filename)
                    it.putExtra(DownloadLabelActivity.CATEGORY, "document")
                    it.putExtra(DownloadLabelActivity.LABEL, "Personal")
                    startActivity(it)
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}