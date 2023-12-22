package com.dicoding.elanhakim.fileManagerAI.view.detail.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.video.VideoResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ItemFilesBinding

class VideoAdapter : RecyclerView.Adapter<VideoItemView>(){

    private val list = ArrayList<VideoResponse>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoItemView {
        val view = ItemFilesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoItemView(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VideoItemView, position: Int) {
        val data = list[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(data) }
    }

    fun setList(video: List<VideoResponse>){
        list.addAll(video)
        if (list.size > 1) notifyItemRangeChanged(0, list.lastIndex) else notifyItemInserted(0)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: VideoResponse)
    }

}