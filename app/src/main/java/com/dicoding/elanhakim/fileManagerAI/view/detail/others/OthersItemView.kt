package com.dicoding.elanhakim.fileManagerAI.view.detail.others

import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.others.OthersResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ItemFilesBinding

class OthersItemView(private val binding: ItemFilesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(others: OthersResponse){
        binding.apply {
            tvNama.text = others.filename
            tvDate.text = others.updatedAt
        }
    }
}