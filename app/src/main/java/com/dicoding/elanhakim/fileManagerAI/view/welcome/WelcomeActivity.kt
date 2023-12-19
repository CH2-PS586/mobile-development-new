package com.dicoding.elanhakim.fileManagerAI.view.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.dicoding.elanhakim.fileManagerAI.view.login.LoginActivity
import com.dicoding.elanhakim.fileManagerAI.view.signup.SignupActivity
import com.dicoding.elanhakim.fileManagerAI.view.home.HomeActivity
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private val welcomeViewModel: WelcomeViewModel by viewModels{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            startDelay = 100
        }.start()

        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val desc = ObjectAnimator.ofFloat(binding.descTextView, View.ALPHA, 1f).setDuration(100)

        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }

        AnimatorSet().apply {
            playSequentially(title, desc, together)
            start()
        }
    }

    private fun startAuthActivity(showLogin: Boolean) {
        val intent = if (showLogin) {
            Intent(this, LoginActivity::class.java)
        } else {
            Intent(this, SignupActivity::class.java)
        }
        startActivity(intent)
    }

    private fun loginHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun setupAction() {
        binding.apply {
            loginButton.setOnClickListener {
                startAuthActivity(true)
            }
            signupButton.setOnClickListener {
                startAuthActivity(false)
            }
        }

        welcomeViewModel.getSession().observe(this) {
            if (it.isLogin && it.accessToken.isNotEmpty()) {
                loginHome()
                finish()
            }
        }
    }

}