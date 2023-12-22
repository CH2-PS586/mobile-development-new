package com.dicoding.elanhakim.fileManagerAI.view.detail.picture.friends

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
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityFriendsBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureAdapter
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureViewModel
import com.dicoding.elanhakim.fileManagerAI.view.download.DownloadLabelActivity

class FriendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendsBinding
    private lateinit var friendsAdapter: PictureAdapter
    private val pictureViewModel by viewModels<PictureViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))
        supportActionBar?.title = getString(R.string.friends)

        with(binding) {
            rvFriends.apply {
                friendsAdapter = PictureAdapter()
                adapter = friendsAdapter
                layoutManager = LinearLayoutManager(this@FriendsActivity)
                addItemDecoration(DividerItemDecoration(this@FriendsActivity, (rvFriends.layoutManager as LinearLayoutManager).orientation))
            }
        }
        setUpData()
    }

    private fun setUpData(){
        pictureViewModel.getSessionData().observe(this@FriendsActivity) {user ->
            pictureViewModel.getFriends(user.accessToken).observe(this) {result ->
                when(result) {
                    is ResultApi.Loading -> {
                        showLoading(true)
                        friendsAdapter.clearList()
                    }
                    is ResultApi.Success -> {
                        showLoading(false)
                        friendsAdapter.setList(result.data)
                    }
                    is ResultApi.Error -> {
                        showLoading(false)
                    }
                }
            }
        }
        friendsAdapter.setOnItemClickCallback(object : PictureAdapter.OnItemClickCallback{
            override fun onItemClicked(file: PictureResponse) {
                Intent(this@FriendsActivity, DownloadLabelActivity::class.java).also {
                    it.putExtra(DownloadLabelActivity.NAME, file.filename)
                    it.putExtra(DownloadLabelActivity.CATEGORY, "picture")
                    it.putExtra(DownloadLabelActivity.LABEL, "Friends")
                    startActivity(it)
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}