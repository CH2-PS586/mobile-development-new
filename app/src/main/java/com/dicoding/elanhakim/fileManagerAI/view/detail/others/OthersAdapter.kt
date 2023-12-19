package com.dicoding.elanhakim.fileManagerAI.view.detail.others

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.elanhakim.fileManagerAI.data.remote.response.file.others.OthersResponse
import com.dicoding.elanhakim.fileManagerAI.databinding.ItemFilesBinding

class OthersAdapter : RecyclerView.Adapter<OthersItemView>(){

    private val list = ArrayList<OthersResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OthersItemView {
        val view = ItemFilesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OthersItemView(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: OthersItemView, position: Int) {
        val data = list[position]
        holder.bind(data)
    }

    fun setList(others: List<OthersResponse>){
        list.addAll(others)
        if (list.size > 1) notifyItemRangeChanged(0, list.lastIndex) else notifyItemInserted(0)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

}