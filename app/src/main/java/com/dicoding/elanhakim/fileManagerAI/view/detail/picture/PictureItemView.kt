package com.dicoding.elanhakim.fileManagerAI.view.detail.picture

import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.picture.PictureResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ItemFilesBinding

class PictureItemView(private val binding: ItemFilesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(picture: PictureResponse){
        binding.apply {
            tvNama.text = picture.filename
            tvDate.text = picture.updatedAt
        }
    }
}