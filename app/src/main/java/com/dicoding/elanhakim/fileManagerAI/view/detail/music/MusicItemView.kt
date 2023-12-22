package com.dicoding.elanhakim.fileManagerAI.view.detail.music

import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.music.MusicResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ItemFilesBinding

class MusicItemView(private val binding: ItemFilesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(music: MusicResponse){
        binding.apply {
            tvNama.text = music.filename
            tvDate.text = music.updatedAt
        }
    }
}