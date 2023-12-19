package com.dicoding.elanhakim.fileManagerAI.view.detail.document

import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.document.DocumentResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ItemFilesBinding

class DocumentItemView(private val binding: ItemFilesBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(document: DocumentResponse){
        binding.apply {
            tvNama.text = document.filename
            tvDate.text = document.updatedAt
        }
    }
}