package com.dicoding.elanhakim.storyApp.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.elanhakim.storyApp.R
import com.dicoding.elanhakim.storyApp.data.Folder
import com.dicoding.elanhakim.storyApp.data.remote.response.user.User
import com.dicoding.elanhakim.storyApp.view.ViewModelFactory
import com.dicoding.elanhakim.storyApp.databinding.ActivityHomeBinding
import com.dicoding.elanhakim.storyApp.view.detail.DetailActivity
import com.dicoding.elanhakim.storyApp.view.detail.document.DocumentActivity
import com.dicoding.elanhakim.storyApp.view.detail.music.MusicActivity
import com.dicoding.elanhakim.storyApp.view.detail.others.OthersActivity
import com.dicoding.elanhakim.storyApp.view.detail.picture.PictureActivity
import com.dicoding.elanhakim.storyApp.view.detail.video.VideoActivity
import com.dicoding.elanhakim.storyApp.view.login.LoginActivity
import com.dicoding.elanhakim.storyApp.view.welcome.WelcomeActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val list = ArrayList<Folder>()
    private lateinit var user: User

    private val homeViewModel by viewModels<HomeViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar

        homeViewModel.getSessionData().observe(this@HomeActivity) { user ->
            if (!user.isLogin) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                setUpAction(user)
            }
        }

    }

    private fun setUpAction(user: User){
        with(binding){
            tvToken.text = user.accessToken
            layoutDocument.setOnClickListener {
                val intent = Intent(this@HomeActivity, DocumentActivity::class.java)
                startActivity(intent)
            }
            layoutMusic.setOnClickListener {
                val intent = Intent(this@HomeActivity, MusicActivity::class.java)
                startActivity(intent)
            }
            layoutOthers.setOnClickListener {
                val intent = Intent(this@HomeActivity, OthersActivity::class.java)
                startActivity(intent)
            }
            layoutPicture.setOnClickListener {
                val intent = Intent(this@HomeActivity, PictureActivity::class.java)
                startActivity(intent)
            }
            layoutVideo.setOnClickListener {
                val intent = Intent(this@HomeActivity, VideoActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu1 -> {
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.logout))
                    setMessage(getString(R.string.are_you_sure))
                    setPositiveButton(getString(R.string.yes)) { _, _ ->
                        homeViewModel.logout()
                        loginActivity()
                    }
                    create()
                    show()
                }
                true
            }

            R.id.menu2 -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun loginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}