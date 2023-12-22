package com.dicoding.elanhakim.fileManagerAI.view.home

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.dicoding.elanhakim.fileManagerAI.R
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityHomeBinding
import com.dicoding.elanhakim.fileManagerAI.view.detail.document.DocumentActivity
import com.dicoding.elanhakim.fileManagerAI.view.detail.music.MusicActivity
import com.dicoding.elanhakim.fileManagerAI.view.detail.others.OthersActivity
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureActivity
import com.dicoding.elanhakim.fileManagerAI.view.detail.video.VideoActivity
import com.dicoding.elanhakim.fileManagerAI.view.login.LoginActivity
import com.dicoding.elanhakim.fileManagerAI.view.upload.UploadActivity
import com.dicoding.elanhakim.fileManagerAI.view.welcome.WelcomeActivity


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val homeViewModel by viewModels<HomeViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))

        homeViewModel.getSessionData().observe(this@HomeActivity) { user ->
            if (!user.isLogin) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                binding.tvUser.text = getString(R.string.greeting, user.username)
                setUpAction(user)
            }
        }

    }

    private fun setUpAction(user: User){
        with(binding){
            buttonUpload.setOnClickListener {
                val intent = Intent(this@HomeActivity, UploadActivity::class.java)
                startActivity(intent)
            }
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