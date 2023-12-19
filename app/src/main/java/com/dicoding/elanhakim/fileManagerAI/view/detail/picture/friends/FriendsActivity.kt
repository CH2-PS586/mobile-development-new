package com.dicoding.elanhakim.fileManagerAI.view.detail.picture.friends

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityFriendsBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureViewModel

class FriendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendsBinding
    private lateinit var friendsAdapter: FriendsAdapter
    private val pictureViewModel by viewModels<PictureViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            rvFriends.apply {
                friendsAdapter = FriendsAdapter()
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
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}