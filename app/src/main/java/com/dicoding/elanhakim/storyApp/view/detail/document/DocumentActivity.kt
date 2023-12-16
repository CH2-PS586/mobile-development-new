package com.dicoding.elanhakim.storyApp.view.detail.document

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dicoding.elanhakim.storyApp.R
import com.dicoding.elanhakim.storyApp.data.remote.response.user.User
import com.dicoding.elanhakim.storyApp.databinding.ActivityDocumentBinding
import com.dicoding.elanhakim.storyApp.databinding.ActivityMusicBinding
import com.dicoding.elanhakim.storyApp.view.ViewModelFactory
import com.dicoding.elanhakim.storyApp.view.home.HomeViewModel
import com.dicoding.elanhakim.storyApp.view.welcome.WelcomeActivity

class DocumentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDocumentBinding
    private val documentViewModel by viewModels<DocumentViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        documentViewModel.getSessionData().observe(this@DocumentActivity) { user ->
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
            textView3.text = user.accessToken
        }
    }
}