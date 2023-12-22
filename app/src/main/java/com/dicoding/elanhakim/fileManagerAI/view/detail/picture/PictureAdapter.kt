package com.dicoding.elanhakim.fileManagerAI.view.detail.picture

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.picture.PictureResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ItemFilesBinding

class PictureAdapter : RecyclerView.Adapter<PictureItemView>(){

    private val list = ArrayList<PictureResponse>()
    private var onItemClickCallback: PictureAdapter.OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: PictureAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureItemView {
        val view = ItemFilesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PictureItemView(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PictureItemView, position: Int) {
        val data = list[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(data) }
    }

    fun setList(picture: List<PictureResponse>){
        list.addAll(picture)
        if (list.size > 1) notifyItemRangeChanged(0, list.lastIndex) else notifyItemInserted(0)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: PictureResponse)
    }

}