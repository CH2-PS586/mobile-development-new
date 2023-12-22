package com.dicoding.elanhakim.fileManagerAI.view.detail.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.music.MusicResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ItemFilesBinding

class MusicAdapter : RecyclerView.Adapter<MusicItemView>(){

    private val list = ArrayList<MusicResponse>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicItemView {
        val view = ItemFilesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicItemView(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MusicItemView, position: Int) {
        val data = list[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(data) }
    }

    fun setList(music: List<MusicResponse>){
        list.addAll(music)
        if (list.size > 1) notifyItemRangeChanged(0, list.lastIndex) else notifyItemInserted(0)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: MusicResponse)
    }

}