package com.dicoding.elanhakim.fileManagerAI.view.detail.document

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.document.DocumentResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ItemFilesBinding

class DocumentAdapter : RecyclerView.Adapter<DocumentItemView>(){

    private val list = ArrayList<DocumentResponse>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentItemView {
        val view = ItemFilesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DocumentItemView(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: DocumentItemView, position: Int) {
        val data = list[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(data) }
    }

    fun setList(document: List<DocumentResponse>){
        list.addAll(document)
        if (list.size > 1) notifyItemRangeChanged(0, list.lastIndex) else notifyItemInserted(0)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: DocumentResponse)
    }

}