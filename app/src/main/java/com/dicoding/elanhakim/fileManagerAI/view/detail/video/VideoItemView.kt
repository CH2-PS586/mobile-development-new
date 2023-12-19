package com.dicoding.elanhakim.fileManagerAI.view.detail.video

import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.video.VideoResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ItemFilesBinding

class VideoItemView(private val binding: ItemFilesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(video: VideoResponse){
        binding.apply {
            tvNama.text = video.filename
            tvDate.text = video.updatedAt
        }
    }
}