package com.dicoding.elanhakim.fileManagerAI.view.detail.picture

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.dicoding.elanhakim.fileManagerAI.R
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.user.User
import com.dicoding.elanhakim.fileManagerAI.databinding.ActivityPictureBinding
import com.dicoding.elanhakim.fileManagerAI.view.ViewModelFactory
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.collage.CollageActivity
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.food.FoodActivity
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.friends.FriendsActivity
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.memes.MemesActivity
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.pets.PetsActivity
import com.dicoding.elanhakim.fileManagerAI.view.detail.picture.selfie.SelfieActivity

class PictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPictureBinding

    private val pictureViewModel by viewModels<PictureViewModel>{
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.blue)))
        supportActionBar?.title = getString(R.string.picture)

        pictureViewModel.getSessionData().observe(this@PictureActivity) { user ->
            setUpAction(user)
        }

    }

    private fun setUpAction(user: User){
        with(binding){
            layoutCollage.setOnClickListener {
                val intent = Intent(this@PictureActivity, CollageActivity::class.java)
                startActivity(intent)
            }
            layoutFood.setOnClickListener {
                val intent = Intent(this@PictureActivity, FoodActivity::class.java)
                startActivity(intent)
            }
            layoutFriends.setOnClickListener {
                val intent = Intent(this@PictureActivity, FriendsActivity::class.java)
                startActivity(intent)
            }
            layoutMemes.setOnClickListener {
                val intent = Intent(this@PictureActivity, MemesActivity::class.java)
                startActivity(intent)
            }
            layoutPets.setOnClickListener {
                val intent = Intent(this@PictureActivity, PetsActivity::class.java)
                startActivity(intent)
            }
            layoutSelfie.setOnClickListener {
                val intent = Intent(this@PictureActivity, SelfieActivity::class.java)
                startActivity(intent)
            }

        }
    }
}