package com.dicoding.elanhakim.storyApp.view.detail.music

import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.storyApp.data.remote.response.file.music.MusicResponseItem
import com.dicoding.elanhakim.storyApp.databinding.ItemFilesBinding

class MusicItemView(private val binding: ItemFilesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(music: MusicResponseItem){
        binding.apply {
            tvNama.text = music.filename
            tvDate.text = music.updatedAt
        }
    }
}