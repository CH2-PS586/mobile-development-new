package com.dicoding.elanhakim.fileManagerAI.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.R
import com.dicoding.elanhakim.fileManagerAI.view.home.HomeActivity
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
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

        val signup = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val messageTextView =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditText =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditText =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(
                title,
                messageTextView,
                emailTextView,
                emailEditText,
                passwordTextView,
                passwordEditText,
                signup
            )
            start()
        }
    }

    private fun setupAction() {
        binding.apply {
            loginButton.setOnClickListener{
                showLoading(true)
                loginViewModel.login(emailEditText.text.toString(), passwordEditText.text.toString()).observe(this@LoginActivity) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultApi.Loading -> {
                                showLoading(true)
                            }
                            is ResultApi.Success -> {
                                showLoading(false)
                                loginViewModel.saveSession(result.data.loginResult)
                                showToast(result.data.message)
                                homeixActivity()
                            }
                            is ResultApi.Error -> {
                                showLoading(false)
                                showDialog(result.error)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun homeixActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        Toast.makeText(this, "Selamat Datang", Toast.LENGTH_SHORT).show()
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showDialog(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.error))
            setMessage(message)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loginButton.isEnabled = !isLoading
    }
}