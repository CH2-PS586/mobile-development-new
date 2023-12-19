package com.dicoding.elanhakim.fileManagerAI.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.ResultApi
import com.dicoding.elanhakim.fileManagerAI.R
import com.dicoding.elanhakim.fileManagerAI.data.remote.retrofit.RegisterRequest
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivitySignupBinding
import com.dicoding.elanhakim.fileManagerAI.view.login.LoginActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel by viewModels<SignupViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
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

        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditText =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
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
                nameTextView,
                nameEditText,
                emailTextView,
                emailEditText,
                passwordTextView,
                passwordEditText,
                signup
            )
            start()
        }
    }

    private fun setupAction(){
        binding.apply {
            signupButton.setOnClickListener {
                showLoading(true)
                signupViewModel.signup(
                    RegisterRequest(binding.nameEditText.text.toString(), binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
                ).observe(this@SignupActivity) { result ->
                    if (result != null) {
                        when (result) {
                            is ResultApi.Loading -> {
                                showLoading(true)
                            }

                            is ResultApi.Success -> {
                                showLoading(false)
                                clearField()
                                showToast(result.data.message)
                                showDialog(binding.emailEditText.text.toString())
                            }

                            is ResultApi.Error -> {
                                showLoading(false)
                                showToast("User Already Exist")
                            }
                        }
                    }
                }
                clearField()
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun clearField() {
        with(binding) {
            nameEditText.text?.clear()
            emailEditText.text?.clear()
            passwordEditText.text?.clear()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.signupButton.isEnabled = !isLoading

    }

    private fun showDialog(email: String) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.success))
            setMessage(getString(R.string.signup_success, email))
            setPositiveButton(getString(R.string.login)) { dialog, _ ->
                dialog.dismiss()
                val intent =
                    Intent(
                        this@SignupActivity,
                        LoginActivity::class.java
                    )
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
                .create()
                .show()
        }
    }
}
