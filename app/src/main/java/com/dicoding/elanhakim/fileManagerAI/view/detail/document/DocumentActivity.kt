package com.dicoding.elanhakim.fileManagerAI.view.detail.document

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.dicoding.elanhakim.fileManagerAI.R
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityDocumentBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.document.personal.PersonalActivity
import com.dicoding.elanhakim.fileManagerAI.view.detail.document.work.WorkActivity
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.PictureViewModel

class DocumentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDocumentBinding

    private val documentViewModel by viewModels<PictureViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))
        supportActionBar?.title = getString(R.string.document)

        documentViewModel.getSessionData().observe(this@DocumentActivity) { user ->
            setUpAction(user)
        }

    }

    private fun setUpAction(user: User){
        with(binding){
            layoutPersonal.setOnClickListener {
                val intent = Intent(this@DocumentActivity, PersonalActivity::class.java)
                startActivity(intent)
            }
            layoutSchool.setOnClickListener {
                val intent = Intent(this@DocumentActivity, WorkActivity::class.java)
                startActivity(intent)
            }
        }
    }
}